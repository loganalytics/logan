/* $Header: emcore/jsrc/core/CoreLoganalyticsUi/src/oracle/sysman/core/loganalytics/ui/source/LogSourceFieldRefsViewBean.java /st_emgc_pt-13.1mstr/7 2014/01/10 01:08:59 rban Exp $ */

/* Copyright (c) 2002, 2014, Oracle and/or its affiliates. 
All rights reserved.*/

/*
   DESCRIPTION
   Log analytics Source field reference view bean

   PRIVATE CLASSES
    <list of private classes defined - with one-line descriptions>

   NOTES
    <other useful comments, qualificationsf, etc.>
   
    MODIFIED    (MM/DD/YY)
    rban        01/02/14 - show field display name
    vivsharm    01/04/13 - log Analytics
 */

package oracle.saas.logan.view.source;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;
import javax.faces.validator.ValidatorException;

import oracle.adf.view.rich.component.rich.data.RichTable;
import oracle.adf.view.rich.event.DialogEvent;
import oracle.adf.view.rich.event.PopupFetchEvent;

import oracle.jbo.Row;
import oracle.jbo.domain.Number;

import oracle.saas.logan.util.InterPageMessageBean;
import oracle.saas.logan.util.LoganLibUiUtil;
import oracle.saas.logan.util.Logger;
import oracle.saas.logan.util.UiUtil;
import oracle.saas.logan.view.BaseLoganSourceViewBean;
import oracle.saas.logan.view.ILoganSourceDataBean;
import oracle.saas.logan.view.KeyValuePairObj;
import oracle.saas.logan.view.LaunchContextModeBean;

import org.apache.myfaces.trinidad.event.DisclosureEvent;
import org.apache.myfaces.trinidad.event.SelectionEvent;


public class LogSourceFieldRefsViewBean extends BaseLoganSourceViewBean
{
    private LoganSourceFieldRefsDataBean fieldsRefDataBean;
    private LoganSourceFieldRefsDataBean fieldsRefDataBeanRef;
    private LoganSourceExtFieldsBean extFieldsBean;
    private LoganSourceDetailsBean sourceCurrentDataBean;
    
    private LaunchContextModeBean modeBean;
    
    private String launchMode;
    private Number sourceId;
    
    private String searchRefDname;
    private String searchRefBaseFieldIname;
    private String searchRefExtFieldIname;
    private List<LoganSourceFieldReference> fieldReferenceSearchResults;
        
    private LoganSourceFieldReference currAddFieldRef; 
        //new LoganSourceFieldReference();
    private LoganSourceFieldReference currSelFieldRef; 
        //new LoganSourceFieldReference();
    private LoganSourceFieldReference searchSelFieldRef;
    private String currSelExistingFieldRef;
    private String currCSVListData;
    private String addCSVListData;

    private List<SelectItem> searchExtfs;
    private List<SelectItem> commonFields;
    private List<SelectItem> baseFieldsForSearch;
    
    private List<KeyValuePairObj> sqlAddQuerySampleOutput = new ArrayList<KeyValuePairObj>();
    private List<KeyValuePairObj> sqlCurrQuerySampleOutput = new ArrayList<KeyValuePairObj>();

    List<LoganSourceFieldRefContent> refRC = 
        new ArrayList<LoganSourceFieldRefContent>();
        
    private String addLookupContentRadioValue = "new";
    private String currLookupContentRadioValue = "new";
    
    private static final String SOURCE_ID = "sourceId";
    private static final Logger s_log =
        Logger.getLogger(LogSourceFieldRefsViewBean.class.getName());
    
    public LogSourceFieldRefsViewBean()
    {
        super();
        Map ipmParams =
            InterPageMessageBean.getCurrentInstance().getParams();
        launchMode = LoganLibUiUtil.getModeForTrain();
        modeBean = new LaunchContextModeBean(launchMode);
        String srId = (String) ipmParams.get(SOURCE_ID);
        if(srId != null){
            sourceId = new Number(Integer.parseInt(srId));
            Row contextRow = LoganLibUiUtil.getCurrentLogSourceRow(sourceId);
            
            extFieldsBean = instantiateExtFiledsBean();
            extFieldsBean.initExtFields(sourceId, modeBean);
            extFieldsBean.initModeSpecificData(modeBean);
            
            fieldsRefDataBean = instantiateFieldRefsDataBean();
            fieldsRefDataBean.initFieldRefs(sourceId, modeBean);
            fieldsRefDataBean.setExtFieldsBean(extFieldsBean);
            fieldsRefDataBean.initModeSpecificData(modeBean);
            
            fieldsRefDataBeanRef = (LoganSourceFieldRefsDataBean)fieldsRefDataBean.clone();
            
            try
            {
                sourceCurrentDataBean = new LoganSourceDetailsBean(contextRow);
            }
            catch(IllegalArgumentException ew)
            {
                if(s_log.isFineEnabled())
                    s_log.fine("Error init:: LoganSourceDetailsBean :: Failed with Error: ", ew);
            }
            
            if(sourceCurrentDataBean.isIsSystemDefinedSource())
                modeBean.setModeToReadOnly();
            
            fieldsRefDataBean.setSourceCurrentDataBean(sourceCurrentDataBean);
        }
    }
    
    public LoganSourceFieldRefsDataBean instantiateFieldRefsDataBean()
    {
        return new LoganSourceFieldRefsDataBean();
    }
    
    public LoganSourceExtFieldsBean instantiateExtFiledsBean()
    {
        return new LoganSourceExtFieldsBean();
    }

    public boolean validateViewBeanData()
    {
        if(this.isReadOnlyMode())
            return true;
        List<LoganSourceFieldReference> refs = 
            this.fieldsRefDataBean.getReferences();
        if (refs != null && refs.size() > 0)
        {
            // TODO: validate all the refs
        }
        return true;
    }

    public void setCurrReferenceContent(List<LoganSourceFieldRefContent> rc)
    {
        currSelFieldRef.setRefContentNameValuePairs(rc);
    }

    public List<LoganSourceFieldRefContent> getCurrReferenceContent()
    {
        return currSelFieldRef.getRefContentNameValuePairs();
    }

    public void setAddReferenceContent(List<LoganSourceFieldRefContent> rc)
    {
        currAddFieldRef.setRefContentNameValuePairs(rc);
    }

    public List<LoganSourceFieldRefContent> getAddReferenceContent()
    {
        return currAddFieldRef.getRefContentNameValuePairs();
    }
    
    public void deleteFieldReference(ActionEvent actionEvent)
    {
        List<LoganSourceFieldReference> lr = getFieldReferences();
        if(lr != null)
        {
            lr.remove(this.currSelFieldRef);
        }
    }

    public void existingFieldRefsSelected(SelectionEvent selectionEvent)
    {
        RichTable iT = (RichTable) selectionEvent.getComponent();
        // TODO: multiselect
        this.searchSelFieldRef = (LoganSourceFieldReference) LoganLibUiUtil.getSelectedIPRowForTable(iT);
        searchSelFieldRef.setSrefmapRefIname(searchSelFieldRef.getRefIname());
        searchSelFieldRef.setNewFR(false);
    }

    public void fieldRefSelected(SelectionEvent selectionEvent)
    {
        RichTable iT = (RichTable) selectionEvent.getComponent();
        this.currSelFieldRef = (LoganSourceFieldReference) LoganLibUiUtil.getSelectedIPRowForTable(iT);
    }

    public void setfieldsRefDataBean(LoganSourceFieldRefsDataBean fieldsRefDataBean)
    {
        this.fieldsRefDataBean = fieldsRefDataBean;
    }

    public LoganSourceFieldRefsDataBean getfieldsRefDataBean()
    {
        return fieldsRefDataBean;
    }

    public void setCurrSelFieldRef(LoganSourceFieldReference currSelFieldRef)
    {
        this.currSelFieldRef = currSelFieldRef;
    }

    public LoganSourceFieldReference getCurrSelFieldRef()
    {
        return currSelFieldRef;
    }
    
    public List<LoganSourceFieldReference> getFieldReferenceSearchResults()
    {
        return this.fieldReferenceSearchResults;
    }
    
    public void setFieldReferenceSearchResults(List<LoganSourceFieldReference> rList)
    {
        this.fieldReferenceSearchResults = rList;
    }
    
    public List<LoganSourceFieldReference> getFieldReferences()
    {
        return this.fieldsRefDataBean.getReferences();
    }
    
    public void setFieldReferences(List<LoganSourceFieldReference> rList)
    {
        this.fieldsRefDataBean.setReferences(rList);
    }

    public void launchAddNewRefPopup(PopupFetchEvent popupFetchEvent)
    {
        String launchBtnId = popupFetchEvent.getLaunchSourceClientId();
        
        currAddFieldRef = new LoganSourceFieldReference();
        currAddFieldRef.setNewFR(true);
        currAddFieldRef.setRefLookupType(LoganSourceFieldReference.NVP_CHOICE);
        currAddFieldRef.setSrefmapSourceId(sourceId);
        List<SelectItem> cf = getCommonFields();
        if(cf != null && cf.size() > 0)
        {
            String baseFieldSelectedDefault = (String)cf.get(0).getValue();
            currAddFieldRef.setRefBaseFieldIname(baseFieldSelectedDefault);
            currAddFieldRef.setRefBaseFieldDname(getBaseFieldDnameFromIname(baseFieldSelectedDefault));
            List<SelectItem> ef = getAddExtFields();
            if(ef != null && ef.size() > 0)
            {
                String iname = (String)ef.get(0).getValue();
                String dname = (String)ef.get(0).getLabel();
                currAddFieldRef.setRefExtFieldIname(iname);
                currAddFieldRef.setRefExtFieldDname(dname);
            }
        }
        addCSVListData = null;
    }
    
    public void launchAddExistingRefPopup(PopupFetchEvent popupFetchEvent)
    {
        // DO NOTHING
    }
    
    public void launchEditRefPopup(PopupFetchEvent popupFetchEvent)
    {
        currCSVListData = currSelFieldRef.getRefNVPContentForEdit();
        if(currSelFieldRef.isNvpType() && !currSelFieldRef.isNewFR())
        {
            List<LoganSourceFieldRefContent> rcbef = 
                currSelFieldRef.getRefContentNameValuePairs();
            if(rcbef != null && rcbef.size() > 0)
            {
                refRC.clear();
                for(LoganSourceFieldRefContent sfrc : rcbef)
                {
                    refRC.add((LoganSourceFieldRefContent)sfrc.clone());
                }
            }
        }
    }
    
    public boolean isCurrFieldRefNew()
    {
        return currSelFieldRef.isNewFR();
    }
        
    public List<SelectItem> getAddExtFields()
    {
        String baseFieldSelected = getAddRefBaseFieldIname();
        return getExtFieldsForBase(baseFieldSelected);
    }

    public List<SelectItem> getCurrExtFields()
    {        
        String baseFieldSelected = getCurrRefBaseFieldIname();
        return getExtFieldsForBase(baseFieldSelected);
    }

    /**
     * All Extfields that are defined for this Source.
     * @return
     */
    public List<SelectItem> getSearchExtFields()
    {
        if(searchExtfs != null)
            return searchExtfs;
        
        searchExtfs = getAllExtFields();
        return searchExtfs;
    }
    
    private List<SelectItem> getAllExtFields()
    {
        List<SelectItem> alle = null;
        if(extFieldsBean != null) 
        {
            List<LoganSourceExtFieldsSetting> extfs = extFieldsBean.getSettings();
            if(extfs != null && extfs.size() > 0)
            {
                alle = new ArrayList<SelectItem>(extfs.size());
                alle.add(new SelectItem("", ""));
                for(LoganSourceExtFieldsSetting e : extfs)
                {
                    alle.add(new SelectItem(e.getSeffNewFieldIname(),
                                e.getSeffNewFieldDname()));
                }
            }
        }
        return alle;
    }
    
    private List<SelectItem> getExtFieldsForBase(String baseFieldSelected)
    {
        List<SelectItem> extf = Collections.emptyList();
        if (baseFieldSelected != null)
        {
            if(extFieldsBean != null)
            {
                List<LoganSourceExtFieldsSetting> extfs = extFieldsBean.getSettings();
                if(extfs != null && extfs.size() > 0)
                {
                    extf = new ArrayList<SelectItem>();
                    for(LoganSourceExtFieldsSetting e : extfs)
                    {
                        if(baseFieldSelected.equals(e.getSefBaseFieldIname()))
                        {
                            extf.add(new SelectItem(e.getSeffNewFieldIname(),
                                                    e.getSeffNewFieldDname())); 
                        } 
                    }
                }
            }
        }
        return extf;
    }

    public void handleAddExistingRefDialog(DialogEvent dialogEvent)
    {
        if (dialogEvent.getOutcome().name().equals("ok"))
        {
            List<LoganSourceFieldReference> lr = getFieldReferences();
            if(lr == null)
            {
                lr = new ArrayList<LoganSourceFieldReference>();
                setFieldReferences(lr);
            }
            if(searchSelFieldRef != null &&
               !lr.contains(this.searchSelFieldRef))
            {
                lr.add(searchSelFieldRef);
                searchSelFieldRef.setNewFR(false);
                searchSelFieldRef.setSrefmapSourceId(sourceId);
                this.currSelFieldRef = this.searchSelFieldRef;
            }
        }
    }

    public void handleAddNewRefDialog(DialogEvent dialogEvent)
    {
        if (dialogEvent.getOutcome().name().equals("ok"))
        {
            List<LoganSourceFieldReference> lr = getFieldReferences();
            if(lr == null)
            {
                lr = new ArrayList<LoganSourceFieldReference>();
                setFieldReferences(lr);
            }
            if(!lr.contains(this.currAddFieldRef))
                lr.add(this.currAddFieldRef);
            this.currAddFieldRef.setupRefLookupTypeDisplayAndContent();
            this.currAddFieldRef.setNewFR(true);
            this.currSelFieldRef = this.currAddFieldRef;
        }
    }

    public void handleEditRefDialog(DialogEvent dialogEvent)
    {
        
        //TODO JPA
//        if (dialogEvent.getOutcome().name().equals("ok"))
//        {
//            this.currSelFieldRef.setupRefLookupTypeDisplayAndContent();
//            if(this.currSelFieldRef.isNewFR())
//                return;
//            else // we are editing an existing field ref
//            {
//                List<LoganSourceFieldRefContent> curRC = 
//                    this.currSelFieldRef.getRefContentNameValuePairs();
//                List<IPersistDataOpsHandler> rList = LoganLibUiUtil.getIPersistList(refRC);
//                List<IPersistDataOpsHandler> cList = LoganLibUiUtil.getIPersistList(curRC);
//                LoganLibUiUtil.persistChildObjectData(rList, cList,
//                                                      false, true);
//
//                try
//                {
//                    // Commit the changes in transaction
//                    LoganLibUiUtil.getAppModule().getTransaction().postChanges();
//                    LoganLibUiUtil.getAppModule().getTransaction().commit();
//    
//                    if(s_log.isFinestEnabled())
//                        s_log.finest("Field Reference View BEAN Edit RefContent : Saved");
//                }
//                catch(Exception e)
//                {
//                    s_log.warning("Unable to save Field RefContent changes: ", e);
//                    DCBindingContainer bc = 
//                        (DCBindingContainer) BindingContext.getCurrent().getCurrentBindingsEntry();
//                    bc.reportException(e);
//                    LoganLibUiUtil.getAppModule().getTransaction().rollback();            
//                }
//            }
//        }
    }
    
    public void handleOk(ActionEvent actionEvent){
        boolean readOnlyMode = modeBean.isReadOnlyMode();
        if (readOnlyMode) // SHOW DETAILS
            return;
        
        saveFieldsRef();
    }
    
    public void saveFieldsRef(){
        super.save(fieldsRefDataBeanRef, modeBean);
    }

    public String getBaseFieldDnameFromIname(String iname)
    {
        String dname = null;
        if(iname == null)
            return dname;
        List<SelectItem> cfsi = getCommonFields();
        if(cfsi != null && cfsi.size() > 0)
        {
            for(SelectItem si : cfsi)
            {
                if(iname.equals(si.getValue()))
                {
                    dname = si.getLabel();
                    break;
                }
            }
        }
        return dname;
    }
    
    public void searchRefBaseFieldChanged(ValueChangeEvent valueChangeEvent)
    {
        String nwval = (String)valueChangeEvent.getNewValue();
        searchRefBaseFieldIname = nwval;
        if(searchRefBaseFieldIname != null && searchRefBaseFieldIname.trim().length() > 0)
            searchExtfs = getExtFieldsForBase(searchRefBaseFieldIname);
        else
            searchExtfs = getAllExtFields();
    }
    
    public void addRefBaseFieldChanged(ValueChangeEvent valueChangeEvent)
    {
        String nwval = (String)valueChangeEvent.getNewValue();
        currAddFieldRef.setRefBaseFieldIname(nwval);
        currAddFieldRef.setRefBaseFieldDname(getBaseFieldDnameFromIname(nwval));
        List<SelectItem> ef = getAddExtFields();
        String iname = null;
        String dname = null;
        if(ef != null && ef.size() > 0)
        {
            iname = (String)ef.get(0).getValue();
            //get label as dname and setting it to the field ref
            dname = ef.get(0).getLabel();
        }
        currAddFieldRef.setRefExtFieldIname(iname);
        currAddFieldRef.setRefExtFieldDname(dname);
    }

    public void currRefBaseFieldChanged(ValueChangeEvent valueChangeEvent)
    {
        String nwval = (String)valueChangeEvent.getNewValue();
        currSelFieldRef.setRefBaseFieldIname(nwval);
        currSelFieldRef.setRefBaseFieldDname(getBaseFieldDnameFromIname(nwval));
        List<SelectItem> ef = getCurrExtFields();
        String iname = null;
        String dname = null;
        if(ef != null && ef.size() > 0)
        {
            iname = (String)ef.get(0).getValue();
            //get label as dname and setting it to the field ref
            dname = ef.get(0).getLabel();
        }
        currSelFieldRef.setRefExtFieldIname(iname);
        currSelFieldRef.setRefExtFieldDname(dname);
    }
    
    public void addRefExtendFieldChanged(ValueChangeEvent valueChangeEvent)
    {
        //TODO JPA
//        String nwval = (String)valueChangeEvent.getNewValue();
//        currAddFieldRef.setRefExtFieldIname(nwval);
//        currAddFieldRef.setRefExtFieldDname(LoganLibUiUtil.getFieldDnameByIname(nwval));        
    }
    
    public void currRefExtendFieldChanged(ValueChangeEvent valueChangeEvent)
    {
        //TODO JPA
//        String nwval = (String)valueChangeEvent.getNewValue();
//        currSelFieldRef.setRefExtFieldIname(nwval);
//        currSelFieldRef.setRefExtFieldDname(LoganLibUiUtil.getFieldDnameByIname(nwval));
    }
    
    public Number getAddRefId()
    {
        return this.currAddFieldRef.getRefId();
    }
    
    public Number getCurrRefId()
    {
        return this.currSelFieldRef.getRefId();
    }
    
    public String getAddRefIname()
    {
        return this.currAddFieldRef.getRefIname();
    }
    
    public String getCurrRefIname()
    {
        return this.currSelFieldRef.getRefIname();
    }
    
    public void setAddRefIname(String refIname)
    {
        this.currAddFieldRef.setRefDname(refIname);
        this.currAddFieldRef.setRefIname(refIname);
        this.currAddFieldRef.setSrefmapRefIname(refIname);
    }
    
    public void setCurrRefIname(String refIname)
    {
        this.currSelFieldRef.setRefDname(refIname);
        this.currSelFieldRef.setRefIname(refIname);
        this.currSelFieldRef.setSrefmapRefIname(refIname);
    }
    
    public String getAddRefDname()
    {
        return this.currAddFieldRef.getRefDname();
    }
    
    public void setAddRefDname(String refDname)
    {
        this.currAddFieldRef.setRefDname(refDname);
        this.currAddFieldRef.setRefIname(refDname);
        this.currAddFieldRef.setSrefmapRefIname(refDname);
    }
    
    public String getCurrRefDname()
    {
        return this.currSelFieldRef.getRefDname();
    }
    
    public void setCurrRefDname(String refDname)
    {
        this.currSelFieldRef.setRefDname(refDname);
        this.currSelFieldRef.setRefIname(refDname);
        this.currSelFieldRef.setSrefmapRefIname(refDname);
    }
    
    public String getAddRefBaseFieldDname()
    {
        if(this.currAddFieldRef == null)
            return  null;
        return getBaseFieldDnameFromIname(this.currAddFieldRef.getRefBaseFieldIname());
    }
    
    public String getCurrRefBaseFieldDname()
    {
        if(this.currSelFieldRef == null)
            return  null;
        return getBaseFieldDnameFromIname(this.currSelFieldRef.getRefBaseFieldIname());
    }
    
    public String getAddRefBaseFieldIname()
    {
        return this.currAddFieldRef.getRefBaseFieldIname();
    }
    
    public String getCurrRefBaseFieldIname()
    {
        return this.currSelFieldRef.getRefBaseFieldIname();
    }
    
    public void setAddRefBaseFieldIname(String refBaseFieldIname)
    {
        this.currAddFieldRef.setRefBaseFieldIname(refBaseFieldIname);
        this.currAddFieldRef.setRefBaseFieldDname(getBaseFieldDnameFromIname(refBaseFieldIname));
    }    
    
    public void setCurrRefBaseFieldIname(String refBaseFieldIname)
    {
        this.currSelFieldRef.setRefBaseFieldIname(refBaseFieldIname);
        this.currSelFieldRef.setRefBaseFieldDname(getBaseFieldDnameFromIname(refBaseFieldIname));
    }    

    public void searchExistingFieldRefs(ActionEvent actionEvent)
    {
        //TODO JPA
//        this.fieldReferenceSearchResults = LoganLibUiUtil.searchLogSourceFieldRefs(searchRefDname,
//                                                    searchRefBaseFieldIname,
//                                                    searchRefExtFieldIname,
//                                                    this.getSearchExtFields());
    }

    /**
     * Validate csv data for name-value pairs for field ref content.
     * @param csvData
     * @param refcRefIname
     * @param refcId
     * @return rcnvp
     */
    public List<LoganSourceFieldRefContent> validateCSVForNVP(String csvData, 
                                                              String refcRefIname,
                                                              Number refcId)
    {
        List<LoganSourceFieldRefContent> rcnvp = 
            new ArrayList<LoganSourceFieldRefContent>();
        String[] csvp = csvData.split("\n");
        if(csvp != null && csvp.length > 0)
        {
            for(String nv : csvp)
            {
                int firstIndexOfComma = nv.indexOf(",");
                if(firstIndexOfComma < 0 || firstIndexOfComma >= (nv.length() - 1))
                {
                    String errm = UiUtil.getUiString("ERROR_CSV_LIST_EXPECTED");
                    FacesMessage msg = new FacesMessage(errm);
                    msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                    throw new ValidatorException(msg);
                    
                }
                String refcName = nv.substring(0, firstIndexOfComma);
                String refcValue = nv.substring(firstIndexOfComma + 1);
                rcnvp.add(new LoganSourceFieldRefContent(refcRefIname, refcId,
                                                   refcName, refcValue));
            }
        }
        return rcnvp;
    }

    public void validateCSVForNVPEdit(FacesContext facesContext,
                                  UIComponent uIComponent, Object object)
    {
        String csvData = (object != null? object.toString(): null);
        String refcRefIname = this.getCurrRefIname();
        Number refcId = this.getCurrRefId();
        List<LoganSourceFieldRefContent> rcnvp = validateCSVForNVP(csvData,
                                                                   refcRefIname,
                                                                   refcId);
        this.currCSVListData = csvData;
        this.currSelFieldRef.setRefContentNameValuePairs(rcnvp);
    }

    public void validateCSVForNVPNew(FacesContext facesContext,
                                  UIComponent uIComponent, Object object)
    {
        String csvData = (object != null? object.toString(): null);
        String refcRefIname = this.getAddRefIname();
        Number refcId = this.getAddRefId();
        List<LoganSourceFieldRefContent> rcnvp = validateCSVForNVP(csvData,
                                                                   refcRefIname,
                                                                   refcId);
        this.addCSVListData = csvData;
        this.currAddFieldRef.setRefContentNameValuePairs(rcnvp);
    }

    public void validateRefInameDoesNotExist(FacesContext facesContext,
                                             UIComponent uIComponent,
                                             Object object)
    {
        //TODO JPA
//        String rinam = (object != null? object.toString(): null);
//        if (LoganLibUiUtil.existsFieldRefIname(rinam))
//        {
//            String errm = UiUtil.getUiString("FIELD_REF_EXISTS_ERR");
//            FacesMessage msg = new FacesMessage(errm);
//            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
//            throw new ValidatorException(msg);
//        }
    }

    public boolean isNoAddExtFields()
    {
        return (getAddExtFields() == null || getAddExtFields().size() == 0);
    }

    public boolean isNoExtFields()
    {
        return (getCurrExtFields() == null || getCurrExtFields().size() == 0);
    }

    public boolean isNoSearchExtFields()
    {
        return (getSearchExtFields() == null || getSearchExtFields().size() == 0);
    }
    
    public void lookupTypeValueChangedInAdd(ValueChangeEvent valueChangeEvent)
    {
        String newVal = (String)valueChangeEvent.getNewValue();
        setAddRefLookupType(newVal);
    }
    
    public void lookupTypeValueChanged(ValueChangeEvent valueChangeEvent)
    {
        String newVal = (String)valueChangeEvent.getNewValue();
        setCurrRefLookupType(newVal);
    }
    
    public boolean isReadOnlyMode()
    {
        return modeBean.isReadOnlyMode();
    }
    
    public List<SelectItem> getCommonFields()
    {
        //TODO JPA
//        if (commonFields == null)
//        {
//            commonFields = LoganLibUiUtil.getBaseCommonFieldsForSource(sourceId);
//        }
//        return commonFields;
        return null;
    }
    
    public List<SelectItem> getBaseFieldsForSearch()
    {
        //TODO JPA
//        if (baseFieldsForSearch == null)
//        {
//            List<SelectItem> cf = LoganLibUiUtil.getBaseCommonFieldsList();
//            if(cf != null)
//            {
//                baseFieldsForSearch = new ArrayList<SelectItem>(cf.size()+1);
//                baseFieldsForSearch.add(new SelectItem("", ""));
//                baseFieldsForSearch.addAll(cf);
//            }
//            else
//            {
//                baseFieldsForSearch = new ArrayList<SelectItem>(1);
//                baseFieldsForSearch.add(new SelectItem("", ""));
//            }
//        }
//        return baseFieldsForSearch;
        return null;
    }
    
    public void newFieldDnameValueChanged(ValueChangeEvent valueChangeEvent)
    {
        String newVal = (String)valueChangeEvent.getNewValue();
        String currNewFieldDname = this.getCurrRefNewFieldDname();
        
        if (currNewFieldDname != null) {
            if (!currNewFieldDname.equalsIgnoreCase(newVal))
                validateFieldDname(newVal); 
            //set newFieldIname to be null for new added field
            this.currSelFieldRef.setRefNewFieldIname(null);
        }
    }
    
    public void addNewFieldDnameValueChanged(ValueChangeEvent valueChangeEvent)
    {
        String newVal = (String)valueChangeEvent.getNewValue();
        validateFieldDname(newVal);
    }
    
    /**
     * Check for duplicated field display name.
     * @param feildDname
     */
    private void validateFieldDname(String feildDname) {
        //TODO JPA
//        boolean valid = LoganLibUiUtil.isValidFieldName(feildDname);
//        String errMsg = null;
//        
//        if (!valid) {
//            errMsg = UiUtil.getUiString("REFS_FIELD_NAME_DUPLICATED_ERR");
//        }
//        
//        if (errMsg != null)
//        {
//            FacesMessage msg = new FacesMessage(errMsg);
//            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
//            throw new ValidatorException(msg);
//        }
    }
    
    public void fieldRefsTabDisclosed(DisclosureEvent disclosureEvent)
    {
        new LogSourceFieldRefsViewBean();
    }

    public String getCurrSelExistingFieldRef()
    {
        return this.currSelExistingFieldRef;
    }
    
    public void setCurrSelExistingFieldRef(String currSelExistingFieldRef)
    {
        this.currSelExistingFieldRef = currSelExistingFieldRef;
    }

    public String getAddRefExtFieldIname()
    {
        return this.currAddFieldRef.getRefExtFieldIname();
    }

    public String getCurrRefExtFieldIname()
    {
        return this.currSelFieldRef.getRefExtFieldIname();
    }
    
    public void setAddRefExtFieldIname(String refExtFieldIname)
    {
        this.currAddFieldRef.setRefExtFieldIname(refExtFieldIname);
    }
    
    public void setCurrRefExtFieldIname(String refExtFieldIname)
    {
        this.currSelFieldRef.setRefExtFieldIname(refExtFieldIname);
    }
    
    public String getAddRefNewFieldDname()
    {
        return this.currAddFieldRef.getRefNewFieldDname();
    }
    
    public String getCurrRefNewFieldDname()
    {
        return this.currSelFieldRef.getRefNewFieldDname();
    }
    
    public void setAddRefNewFieldDname(String refNewFieldDname)
    {
        this.currAddFieldRef.setRefNewFieldDname(refNewFieldDname);
    }
    
    public void setCurrRefNewFieldDname(String refNewFieldDname)
    {
        this.currSelFieldRef.setRefNewFieldDname(refNewFieldDname);
    }
    
    public String getAddRefLookupType()
    {
        return ""+this.currAddFieldRef.getRefLookupType();
    }
    
    public String getCurrRefLookupType()
    {
        return ""+this.currSelFieldRef.getRefLookupType();
    }
    
    public void setAddRefLookupType(String refLookupType)
    {
        this.currAddFieldRef.setRefLookupType(new Number(Integer.parseInt(refLookupType)));
        
    }

    public void setCurrRefLookupType(String refLookupType)
    {
        this.currSelFieldRef.setRefLookupType(new Number(Integer.parseInt(refLookupType)));
        
    }

    public void setCurrCSVListData(String currCSVListData)
    {
        this.currCSVListData = currCSVListData;
    }
    
    public void setAddRefRepositoryQuery(String refRepositoryQuery)
    {
        this.currAddFieldRef.setRefRepositoryQuery(refRepositoryQuery);
    }
    
    public void setCurrRefRepositoryQuery(String refRepositoryQuery)
    {
        this.currSelFieldRef.setRefRepositoryQuery(refRepositoryQuery);
    }

    public String getAddRefRepositoryQuery()
    {
        return this.currAddFieldRef.getRefRepositoryQuery();
    }

    public String getCurrRefRepositoryQuery()
    {
        return this.currSelFieldRef.getRefRepositoryQuery();
    }

    public String getCurrCSVListData()
    {
        return currCSVListData;
    }

    public String getAddContentDisplayDetailed()
    {
        return this.currAddFieldRef.getContentDisplayDetailed();
    }

    public String getCurrContentDisplayDetailed()
    {
        return this.currSelFieldRef.getContentDisplayDetailed();
    }

    public void setAddSqlQuerySampleOutput(List<KeyValuePairObj> sqlQuerySampleOutput)
    {
        this.sqlAddQuerySampleOutput = sqlQuerySampleOutput;
    }

    public List<KeyValuePairObj> getAddSqlQuerySampleOutput()
    {
        return sqlAddQuerySampleOutput;
    }

    public void setCurrSqlQuerySampleOutput(List<KeyValuePairObj> sqlQuerySampleOutput)
    {
        this.sqlCurrQuerySampleOutput = sqlQuerySampleOutput;
    }

    public List<KeyValuePairObj> getCurrSqlQuerySampleOutput()
    {
        return sqlCurrQuerySampleOutput;
    }

    public void executeAddSampleSQLQuery(ActionEvent actionEvent)
    {
        // TODO: Add event code here...
    }

    public void executeCurrSampleSQLQuery(ActionEvent actionEvent)
    {
        // TODO: Add event code here...
    }

    public void setAddLookupContentRadioValue(String lookupContentRadioValue)
    {
        this.addLookupContentRadioValue = lookupContentRadioValue;
    }

    public String getAddLookupContentRadioValue()
    {
        return addLookupContentRadioValue;
    }

    public void setCurrLookupContentRadioValue(String lookupContentRadioValue)
    {
        this.currLookupContentRadioValue = lookupContentRadioValue;
    }

    public String getCurrLookupContentRadioValue()
    {
        return currLookupContentRadioValue;
    }

    public void addLookupContentRadioChanged(ValueChangeEvent valueChangeEvent)
    {
        addLookupContentRadioValue = (String)valueChangeEvent.getNewValue();
    }

    public void currLookupContentRadioChanged(ValueChangeEvent valueChangeEvent)
    {
        currLookupContentRadioValue = (String)valueChangeEvent.getNewValue();
    }

    public void showRefContentDetails(PopupFetchEvent popupFetchEvent)
    {
        // Add event code here...
    }

    public void setSearchRefDname(String searchRefDname)
    {
        this.searchRefDname = searchRefDname;
    }

    public String getSearchRefDname()
    {
        return searchRefDname;
    }

    public void setSearchRefBaseFieldIname(String searchRefBaseFieldIname)
    {
        this.searchRefBaseFieldIname = searchRefBaseFieldIname;
    }

    public String getSearchRefBaseFieldIname()
    {
        return searchRefBaseFieldIname;
    }
    
    public void setSearchRefExtFieldIname(String searchRefExtFieldIname)
    {
        this.searchRefExtFieldIname = searchRefExtFieldIname;
    }

    public String getSearchRefExtFieldIname()
    {
        return searchRefExtFieldIname;
    }

    public void setAddCSVListData(String addCSVListData)
    {
        this.addCSVListData = addCSVListData;
    }

    public String getAddCSVListData()
    {
        return addCSVListData;
    }

    public void setModeBean(LaunchContextModeBean modeBean) {
        this.modeBean = modeBean;
    }

    public LaunchContextModeBean getModeBean() {
        return modeBean;
    }

    public void setfieldsRefDataBeanRef(LoganSourceFieldRefsDataBean fieldsRefDataBeanRef) {
        this.fieldsRefDataBeanRef = fieldsRefDataBeanRef;
    }

    public LoganSourceFieldRefsDataBean getfieldsRefDataBeanRef() {
        return fieldsRefDataBeanRef;
    }

    public ILoganSourceDataBean getSourceDataBean() {
        return fieldsRefDataBean;
    }

    public void setSourceDataBean(ILoganSourceDataBean tsdb) {
        this.fieldsRefDataBean = (LoganSourceFieldRefsDataBean)tsdb;
    }

    public String getName() {
        return sourceCurrentDataBean.getName();
    }

    public void setSourceCurrentDataBean(LoganSourceDetailsBean sourceCurrentDataBean) {
        this.sourceCurrentDataBean = sourceCurrentDataBean;
    }

    public LoganSourceDetailsBean getSourceCurrentDataBean() {
        return sourceCurrentDataBean;
    }
}
