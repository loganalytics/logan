/* $Header: emcore/jsrc/core/CoreLoganalyticsUi/src/oracle/sysman/core/loganalytics/ui/source/LoganSourceExtFieldsViewBean.java /st_emgc_pt-13.1mstr/9 2014/01/10 01:08:58 rban Exp $ */

/* Copyright (c) 2002, 2014, Oracle and/or its affiliates. 
All rights reserved.*/

/*
   DESCRIPTION
   Log analytics Source Wizard extended fields page view scope bean

   PRIVATE CLASSES
    <list of private classes defined - with one-line descriptions>

   NOTES
    <other useful comments, qualificationsf, etc.>
   
    MODIFIED    (MM/DD/YY)
    rban        01/02/14 - add extend field for observation
    vivsharm    01/04/13 - log Analytics
 */

/**
 * @version $Header: emcore/jsrc/core/CoreLoganalyticsUi/src/oracle/sysman/core/loganalytics/ui/source/LoganSourceExtFieldsViewBean.java /st_emgc_pt-13.1mstr/9 2014/01/10 01:08:58 rban Exp $
 * @author vivsharm
 * @since release specific (what release of product did this appear in)
 */

package oracle.saas.logan.view.source;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;
import javax.faces.validator.ValidatorException;

import oracle.adf.view.rich.component.rich.RichPopup;
import oracle.adf.view.rich.component.rich.data.RichTable;
import oracle.adf.view.rich.component.rich.input.RichInputText;
import oracle.adf.view.rich.component.rich.layout.RichPanelGroupLayout;
import oracle.adf.view.rich.component.rich.nav.RichCommandButton;
import oracle.adf.view.rich.component.rich.nav.RichCommandMenuItem;
import oracle.adf.view.rich.event.DialogEvent;
import oracle.adf.view.rich.event.PopupFetchEvent;
import oracle.adf.view.rich.model.AutoSuggestUIHints;
import oracle.adf.view.rich.model.NumberRange;
import oracle.adf.view.rich.render.ClientEvent;

import oracle.jbo.Row;
import oracle.jbo.domain.Number;

import oracle.saas.logan.util.AdfUtil;
import oracle.saas.logan.util.EMExecutionContext;
import oracle.saas.logan.util.InterPageMessageBean;
import oracle.saas.logan.util.LoganLibUiUtil;
import oracle.saas.logan.util.Logger;
import oracle.saas.logan.util.UiUtil;
import oracle.saas.logan.view.BaseLoganSourceViewBean;
import oracle.saas.logan.view.ILoganSourceDataBean;
import oracle.saas.logan.view.LaunchContextModeBean;

import org.apache.myfaces.trinidad.context.RequestContext;
import org.apache.myfaces.trinidad.event.DisclosureEvent;
import org.apache.myfaces.trinidad.event.SelectionEvent;


public class LoganSourceExtFieldsViewBean extends BaseLoganSourceViewBean
{
    private LaunchContextModeBean modeBean;
    
    private String launchMode;
    private Number sourceId;
    private String sourceName;
    
    private LoganSourceExtFieldsBean extFieldsBean;
    private LoganSourceExtFieldsBean extFieldsBeanRef;

    private LoganSourceExtFieldsDefn currExtFieldsDefn;
    
    private LoganSavedRegexBean currSelSavedRegexBean;
    private List<LoganSavedRegexBean> savedRegexesForCurrSelText;

    private List<LoganSavedRegexBean> unSavedRegExps =
        new ArrayList<LoganSavedRegexBean>();

    private String currSelExtBaseFieldDname;
    private String currSelExtFieldExpression;

    private String textLeftOfHighlighted;
    private String highlightedText;
    private String textRightOfHighlighted;

    private boolean spaceBeforeHighlight;
    private boolean spaceAfterHighlight;

    private String highlightStartCmpId;
    private int fromIdxOfHighlighted = 0;
    private int toIdxOfHighlighted = 0;
    private String highlightedRange;
    
    private int usingSavedRegxAliasStep;

    private String currSelRawLogText;

    private List<String> tokenToRegexOptions;

    private boolean createExtf = true;
    private boolean newExtF = true;
    private boolean wrapText = true;
    private boolean obsMode = false;

    private static final String USE_EXPR_AS_EXTF = "extf";
    private static final String USE_EXPR_AS_REGX = "regx";

    private String useExrAs = USE_EXPR_AS_EXTF;

    private boolean renderSaveResultPanel;
    
    private String currFieldName;
    private String currFieldDname;
    private String currFieldNameExpr;

    private String searchRegexpWhichMatches;
    private String searchRegexpAlias;
    private String searchRegexp;
    private List<String> suggestions;
    private List<SelectItem> suggestionSIs;
    private String addRegexpWhichMatches;
    private String addRegexpAlias;
    private String addRegexp;
    private String addRegexpDesc;
    private String addTestRegexpOn;
    private String addTestRegexpResult;
    
    private LoganSourceDetailsBean sourceCurrentDataBean;
    
    private LoganSourceExtFieldsSetting extFieldSettingsSelRow;

    private static final String DEFN_TABLE_UI_ID = "emT:sbv2:pc1:t2";
    private static final String SAVED_REGEXP_USAGE_IDENTIFIER = ":@";
    private static final String PLAIN_REGEXP_USAGE_IDENTIFIER = ":";

    private static final String EXPR_BUILDER_REGEX_IT_ID = "emT:sbv2:pc1:it5";
    private static final String EXPR_BUILDER_EXTF_ALIAS_IT_ID = "emT:sbv2:pc1:it16";    
    private static final String EXPR_DEFN_PANEL_ID = "emT:sbv2:pc1:pgl24";
    private static final String EXPR_SLIDER_PANEL_ID = "emT:sbv2:pc1:pgl25";
    private static final String REGEX_SAVE_TEST_PANEL_ID = "emT:sbv2:pgl19";
    
    private static final String OBS_EXPR_BUILDER_REGEX_IT_ID = "sv1:it5";
    private static final String OBS_EXPR_BUILDER_EXTF_ALIAS_IT_ID = "sv1:it16";    
    private static final String OBS_EXPR_DEFN_PANEL_ID = "sv1:pgl24";
    private static final String OBS_EXPR_SLIDER_PANEL_ID = "sv1:pgl25";
    private static final String OBS_REGEX_SAVE_TEST_PANEL_ID = "sv1:pgl19";
    
    private static final String SOURCE_ID = "sourceId";
    private static final Logger s_log =
        Logger.getLogger(LoganSourceExtFieldsViewBean.class.getName());
    private RichPopup usregpop;
    private RichPopup contpop;
    private RichPopup defnpop;
    private RichPopup testerrpop;
    private RichPopup optionspop;
    
    private List<SelectItem> commonFields;
    private List<SelectItem> extFieldDataTypes;
    private List<SelectItem> validExtFields;

    public LoganSourceExtFieldsViewBean()
    {
        super();
        Map ipmParams =
            InterPageMessageBean.getCurrentInstance().getParams();
        launchMode = LoganLibUiUtil.getModeForTrain();
        modeBean = new LaunchContextModeBean(launchMode);
        String srId = (String) ipmParams.get(SOURCE_ID);
        if (srId != null) 
        {
            sourceId = new Number(Integer.parseInt(srId));
            Row contextRow = LoganLibUiUtil.getCurrentLogSourceRow(sourceId);
            
            extFieldsBean = instantiateExtFiledsBean();
            extFieldsBean.initExtFields(sourceId, modeBean);
            extFieldsBean.initModeSpecificData(modeBean);
            extFieldsBeanRef = (LoganSourceExtFieldsBean)extFieldsBean.clone();
            
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
        }

        if(s_log.isFinestEnabled())
            s_log.finest("inited LogSourceExtFieldsSViewBean");
    }

    public LoganSourceExtFieldsBean instantiateExtFiledsBean()
    {
        return new LoganSourceExtFieldsBean();
    }

    /**
     * Validate extend field bean data, including extend field definition, 
     * regex and whether it works for parsing fields from regex.
     * @return boolean
     */
    public boolean validateViewBeanData()
    {        
        if (this.isReadOnlyMode())
            return true;
        List<LoganSourceExtFieldsDefn> definitions = getDefinitions();
        Set<String> extfs = new HashSet<String>();
        if (definitions != null && definitions.size() > 0)
        {
            this.extFieldsBean.clearSettings();
            for (LoganSourceExtFieldsDefn defn: definitions)
            {
                String sReg = defn.getSefRegex();
                String bf = defn.getPrimaryKeyAsString();
                if(!extfs.add(bf))
                {
                    String errm =
                        UiUtil.getUiString("LOG_SOURCE_EXTF_DEFN_DUP_FIELD_ERR");
                    FacesMessage msg = new FacesMessage(errm);
                    msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                    throw new ValidatorException(msg);       
                }
                if(s_log.isFinestEnabled())
                    s_log.finest("ABOUT to validate EXT Field Defn -> "+sReg);
                String errm =
                    LoganSourceExtFieldsDefn.validateRegex(sReg, UiUtil.getLoganBundle());
                if (errm != null)
                {
                    FacesMessage msg = new FacesMessage(errm);
                    msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                    throw new ValidatorException(msg);
                }
                Map<String, LoganSourceExtFieldsSetting> exFieldsmap =
                    new HashMap<String, LoganSourceExtFieldsSetting>();
                List<LoganSourceExtFieldsSetting> fl = defn.getFields();
                if (fl != null)
                {
                    for (LoganSourceExtFieldsSetting f: fl)
                    {
                        exFieldsmap.put(f.getSeffNewFieldIname(), 
                                        (LoganSourceExtFieldsSetting)f.clone());
                    }
                }
                defn.clearFields();
                defn.parseExtractFieldsFromRegex(sReg, exFieldsmap,
                                                 extFieldsBean.getSavedRegularExpMap(),
                                                 unSavedRegExps);
                if (s_log.isFinestEnabled())
                    s_log.finest("validation success");
            }
            this.extFieldsBean.refreshSettingsForSource();
        }
        if (this.unSavedRegExps != null && this.unSavedRegExps.size() > 0)
        {
            showUnsavedRegexpsPopup();
            return false;
        }
        return true;
    }

    /**
     * Set selected extend field expression and initialize high light range
     */
    private void setCurrSelExprAndRaw()
    {
        if (currExtFieldsDefn != null)
        {
            this.currSelExtBaseFieldDname =
                    currExtFieldsDefn.getSefBaseFieldDname();
            this.currSelExtFieldExpression =
                    currExtFieldsDefn.getDisplayRegex();
            this.textRightOfHighlighted = currSelExtFieldExpression;
            if (currSelExtFieldExpression != null)
            {
                initHigtLightRange();
                setHighlightTextRange();
            }
            this.currSelRawLogText =
                    currExtFieldsDefn.getSefBaseFieldLogText();
            if (this.currSelRawLogText == null)
            {
                this.currSelRawLogText = this.currSelExtFieldExpression;
                currExtFieldsDefn.setSefBaseFieldLogText(currSelRawLogText);
            }
        }
    }

    /**
     * Set current selected extend field definition 
     */
    private void setCurrentSelectedExtfieldDefn()
    {
        //TODO JPA
//        this.currExtFieldsDefn =
//                (LoganSourceExtFieldsDefn) LoganLibUiUtil.getSelectedIPRowForTable(DEFN_TABLE_UI_ID);
//        setCurrSelExprAndRaw();
    }

    /**
     * Fire when select extend field definition
     */
    public void extFieldDefnSelected(SelectionEvent selectionEvent)
    {
        //TODO JPA
//        RichTable iT = (RichTable) selectionEvent.getComponent();
//        this.currExtFieldsDefn =
//                (LoganSourceExtFieldsDefn) LoganLibUiUtil.getSelectedIPRowForTable(iT);
//        setCurrSelExprAndRaw();
    }

    /**
     * Fire when changed base field name for extend field definition
     */
    public void defnFielFieldTypeChanged(ValueChangeEvent valueChangeEvent)
    {
        //TODO JPA
//        String selectedValue = (String) valueChangeEvent.getNewValue();
//        if (currExtFieldsDefn == null)
//            setCurrentSelectedExtfieldDefn();
//        if (currExtFieldsDefn != null)
//        {
//            currExtFieldsDefn.setSefBaseFieldIname(selectedValue);
//            List<SelectItem> commonF = this.getCommonFields();
//            String dName = LoganLibUiUtil.getSelectedDisplayName(commonF, selectedValue);
//            currExtFieldsDefn.setSefBaseFieldDname(dName);
//        }
    }
    
    /**
     * Fire when changed exted field name for extend field definition
     */
    public void extFieldTypeChanged(ValueChangeEvent valueChangeEvent)
    {
        //TODO JPA
//        String selectedValue = (String) valueChangeEvent.getNewValue();
//        currFieldName = selectedValue;
//        currFieldDname = LoganLibUiUtil.getFieldDnameByIname(selectedValue);
    }

    /**
     * Validate extend field definition regrex
     */
    public void extFieldDefnValidator(FacesContext facesContext,
                                      UIComponent uIComponent,
                                      Object object)
    {
        String extFieldDefnRegex =
            (object != null? object.toString(): null);
        String errm =
            LoganSourceExtFieldsDefn.validateRegex(extFieldDefnRegex,UiUtil.getLoganBundle());
        if (errm != null)
        {
            FacesMessage msg = new FacesMessage(errm);
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(msg);
        }
    }

    /**
     * Fire when changed extend field definition
     */
    public void extFieldDefnValueChanged(ValueChangeEvent valueChangeEvent)
    {
        if (currExtFieldsDefn == null)
            setCurrentSelectedExtfieldDefn();
    }

    /**
     * Fire when disclosed extend field setting and validate view data
     */
    public void extFieldSettingsTabDisclosed(DisclosureEvent disclosureEvent)
    {
        validateViewBeanData();
    }

    /**
     * Validate if field definition is empty
     * 
     * @return true if no definition
     */
    public boolean isFieldDefinitionsEmpty()
    {
        List<LoganSourceExtFieldsDefn> fs = getDefinitions();
        if (fs == null || fs.size() == 0)
            return true;
        return false;
    }

    public void settingDataTypeChanged(ValueChangeEvent valueChangeEvent)
    {
        // Add event code here...
    }

    /**
     * Fire when add new extend field definition, initialize parameters, extend 
     * fields and show ext field defn popup.
     * 
     */
    public void addExtFieldDefn(ActionEvent actionEvent)
    {
        //TODO JPA
//        String author = EMExecutionContext.getExecutionContext().getEMUser();
//        currExtFieldsDefn = new LoganSourceExtFieldsDefn(sourceId, null,
//                                null, null, null, null, null, null,
//                                null, author,
//                                null);
//        this.newExtF = true;
//        List<SelectItem> commonF = getCommonFields();
//        if (commonF != null && commonF.size() > 0)
//        {
//            String baseFieldIname = "" + (commonF.get(0)).getValue();
//            currSelExtBaseFieldDname =
//                    LoganLibUiUtil.getSelectedDisplayName(commonF,
//                                                          baseFieldIname);
//            currExtFieldsDefn.setSefBaseFieldIname(baseFieldIname);
//            currExtFieldsDefn.setSefBaseFieldDname(currSelExtBaseFieldDname);
//        }
//        
//        initialParamForDefn();
//        initialValidExtFields();
//        
//        RichPopup.PopupHints ph = new RichPopup.PopupHints();
//        defnpop.show(ph);
    }
    
    /**
     * Initialize parameters for extend field definition
     */
    public void initialParamForDefn(){
        currSelExtFieldExpression = null;
        currSelRawLogText = null;
        this.textLeftOfHighlighted = null;
        this.highlightedText = null;
        fromIdxOfHighlighted = 0;
        toIdxOfHighlighted = 0;
        highlightedRange = null;
        textLeftOfHighlighted = null;
        textRightOfHighlighted = null;
        highlightedText = null; 
    }

    /**
     * Remove selected extend field definition
     */
    public void removeExtFieldDefn(ActionEvent actionEvent)
    {
        if (currExtFieldsDefn == null)
            setCurrentSelectedExtfieldDefn();
        if (currExtFieldsDefn != null)
        {
            extFieldsBean.removeExtFieldDefn(currExtFieldsDefn);
            currExtFieldsDefn = null;
        }
    }
    
    /**
     * Add new regular expression
     */
    public void addNewRegExp(ActionEvent actionEvent)
    {
        useExrAs =  USE_EXPR_AS_REGX;
        addRegexpWhichMatches = highlightedText;
    }

    /**
     * Add regular expression to saved list and partial approprate compnent.
     * @param dialogEvent
     */
    public void addRegexpToSavedList(DialogEvent dialogEvent)
    {
        if (dialogEvent.getOutcome().name().equals("ok"))
        {
            if (getSavedRegularExpList() == null)
                setSavedRegularExpList(new ArrayList<LoganSavedRegexBean>());
            String regexAuthor = EMExecutionContext.getExecutionContext().getEMUser();
            currSelSavedRegexBean =
                    new LoganSavedRegexBean(regexAuthor, addRegexp,
                                            addRegexpDesc, null,
                                            addRegexpAlias, null,
                                            addRegexpAlias, new Number(0));
            Map<String, LoganSavedRegexBean> lsrbm = getSavedRegularExpMap();
            validateSavedRegex(lsrbm);
            if(!lsrbm.containsKey(addRegexpAlias))
            {
                getSavedRegularExpList().add(currSelSavedRegexBean);
                lsrbm.put(addRegexpAlias, currSelSavedRegexBean);
                
                if(savedRegexesForCurrSelText != null)
                {
                    savedRegexesForCurrSelText.add(currSelSavedRegexBean);
                }
                if(USE_EXPR_AS_REGX.equals(useExrAs))
                    currFieldNameExpr = currSelSavedRegexBean.getRegexContent();
            }
            renderSaveResultPanel = true;
            pprAddRegexTestSavePanel();
        }
    }

    /**
     * Validate the saved regrex, display error for duplicated regex value.
     * 
     * @param lsrbm saved regrex map
     */
    private void validateSavedRegex(Map<String, LoganSavedRegexBean> lsrbm){
        if (lsrbm != null){
            for (Map.Entry<String, LoganSavedRegexBean> entry : lsrbm.entrySet()){
                String regexAlias = entry.getKey();
                String regex = entry.getValue().getRegexContent();
                if (regex.equals(currSelSavedRegexBean.getRegexContent())){
                    UiUtil.displayErrorMsg(UiUtil.getLoganBundle(), "ERRORHDR", "EXPRESSION_DUPLICATED_ERR");
                }
            }
        }
    }
    
    /**
     * Partial saved regrex panel after test and add new one
     */
    private void pprAddRegexTestSavePanel()
    {
        RichPanelGroupLayout pglST = null;
        if (this.isObsMode())
            pglST = (RichPanelGroupLayout) AdfUtil.findComponent(OBS_REGEX_SAVE_TEST_PANEL_ID);
        else
            pglST = (RichPanelGroupLayout) AdfUtil.findComponent(REGEX_SAVE_TEST_PANEL_ID);
        
        if (pglST != null)
            RequestContext.getCurrentInstance().addPartialTarget(pglST);        
    }

    /**
     * Replace the field name expression with new regex
     */
    public void replaceWithNewRegexp(DialogEvent dialogEvent)
    {
        if (dialogEvent.getOutcome().name().equals("ok"))
        {
            if(USE_EXPR_AS_REGX.equals(useExrAs))
            {
              currFieldNameExpr = addRegexp;
              updateExtfTokenWithExprOrRegexp();
            }
        }
    }
    
    /**
     * Fire when review saved regular expression, clear unsaved regex and update setting.
     */
    public void reviewSaveRegExpDialogListener(DialogEvent dialogEvent)
    {
        for (LoganSavedRegexBean saveR: unSavedRegExps)
        {
            getSavedRegularExpList().add(saveR);
            getSavedRegularExpMap().put(saveR.getRegexDname(), saveR);
        }
        unSavedRegExps.clear();
        this.extFieldsBean.updateSettingsWithRegexContent();
    }

    /**
     * Show unsaved regex popup.
     */
    public void showUnsavedRegexpsPopup()
    {
        RichPopup.PopupHints hints = new RichPopup.PopupHints();
        this.getUsregpop().show(hints);
    }

    /**
     * Listener of extend field definition dialog. Initial high light start 
     * from 0 to length/10 and set high light text range, and will save 
     * extend field definition for observation page.
     * @param dialogEvent
     */
    public void extFieldDefnDialogListener(DialogEvent dialogEvent)
    {
        if (dialogEvent.getOutcome().name().equals("ok"))
        {
            if (currExtFieldsDefn != null)
            {
                currSelExtFieldExpression =
                        this.textLeftOfHighlighted + this.highlightedText +
                        this.textRightOfHighlighted;
                if (currSelExtFieldExpression != null)
                {
                    initHigtLightRange();
                    setHighlightTextRange();
                }
                currExtFieldsDefn.setSefRegex(currSelExtFieldExpression);
                this.extFieldsBean.refreshSettingsForSource();
            }
            currExtFieldsDefn.setSefBaseFieldLogText(currSelRawLogText);
            if(isNewExtF()){
                extFieldsBean.addExtFieldDefn(currExtFieldsDefn);
            }
            this.validateViewBeanData();
            
            //Save changes if in observation mode
            if (this.isObsMode()){
                this.saveExtFields();
            }
        }
    }

    /**
     * Fire when right click high light text, initialize for extedn field.
     */
    public void extFieldTokenRightClickListener(ActionEvent actionEvent)
    {
        RichCommandMenuItem rcmb =
            (RichCommandMenuItem) actionEvent.getSource();
        if (rcmb != null)
        {
            useExrAs = USE_EXPR_AS_REGX;
            createExtf = (rcmb.getId().equals("cmi2")? true: false);
            if (createExtf)
            {
                currFieldNameExpr = "";
                useExrAs = USE_EXPR_AS_EXTF;
            }
        }
    }

    /**
     * Partial extend field definition for the popup window .
     */
    private void pprExprBuilderInputTexts()
    {
        RichPanelGroupLayout pExtfDefn = null;
        RichPanelGroupLayout pSlider = null;
        
        if (this.isObsMode()){
            pExtfDefn = (RichPanelGroupLayout) AdfUtil.findComponent(OBS_EXPR_DEFN_PANEL_ID);
            pSlider = (RichPanelGroupLayout) AdfUtil.findComponent(OBS_EXPR_SLIDER_PANEL_ID);
        } else {
            pExtfDefn = (RichPanelGroupLayout) AdfUtil.findComponent(EXPR_DEFN_PANEL_ID);
            pSlider = (RichPanelGroupLayout) AdfUtil.findComponent(EXPR_SLIDER_PANEL_ID);  
        }
       
        if (pExtfDefn != null)
            RequestContext.getCurrentInstance().addPartialTarget(pExtfDefn);

        if (pSlider != null)
            RequestContext.getCurrentInstance().addPartialTarget(pSlider);
    }

    /**
     * Use regex or alias for extend field, a saved regex can be used as alias.
     * 
     * @param dialogEvent listener
     */
    public void useRegexoOrAliasDialogListener(DialogEvent dialogEvent)
    {
        if (USE_EXPR_AS_EXTF.equals(useExrAs))
        {
            useRegexpAliasInExtfDefn();
            usingSavedRegxAliasStep = 2;
        }
        else
        {
            useRegexpInExtfDefn();
        }
        savedRegexesForCurrSelText = null;
    }

    /**
     * Use regex regex content for extend field definition.
     */
    public void useRegexpInExtfDefn()
    {
        if (currSelSavedRegexBean != null)
        {
            String selectedRegexp =
                currSelSavedRegexBean.getRegexContent();
            if(currSelSavedRegexBean != null)
            {
                currFieldNameExpr = selectedRegexp;
                updateExtfTokenWithExprOrRegexp();
            }
            
        }
    }

    /**
     * Use regex regex alias for extend field definition.
     */
    public void useRegexpAliasInExtfDefn()
    {
        if (currSelSavedRegexBean != null)
        {
            String selectedRegexpAlias =
                currSelSavedRegexBean.getRegexDname();
            setExprString(selectedRegexpAlias, false);
        }
    }

    /**
     * Set regular expression 
     * 
     * @param expr given  expression
     * @param isRegexp true to using regex otherwise alias
     */
    private void setExprString(String expr, boolean isRegexp)
    {
        String itId = null;
        if (this.isObsMode()) {
            itId = (isRegexp ? OBS_EXPR_BUILDER_REGEX_IT_ID : OBS_EXPR_BUILDER_EXTF_ALIAS_IT_ID);
        } else {
            itId = (isRegexp ? EXPR_BUILDER_REGEX_IT_ID : EXPR_BUILDER_EXTF_ALIAS_IT_ID);
        }
        
        RichInputText extfExpr = (RichInputText) AdfUtil.findComponent(itId);
        if (extfExpr != null && expr != null)
        {
            if (currFieldNameExpr == null)
            {
                if (extfExpr.getValue() != null)
                    currFieldNameExpr = "" + extfExpr.getValue();
                else
                    currFieldNameExpr = "";
            }
            currFieldNameExpr = expr;
        }
    }

    /**
     * Set extend field tokens, higt light selected text.
     */
    private void setExtfExprTokens()
    {
        if (this.currSelExtFieldExpression != null &&
            this.currSelExtFieldExpression.trim().length() > 0)
        {
            if (currSelExtFieldExpression != null)
            {
                initHigtLightRange();
                setHighlightTextRange();
            }
        }
    }
    
    private void initHigtLightRange(){
        int expLength = currSelExtFieldExpression.length();
        fromIdxOfHighlighted = 0;
        toIdxOfHighlighted = expLength / 10;
    }

    /**
     * Extend field definitin popup lisener, initialize default definition if 
     * not new create.
     * @param e 
     */
    public void extFieldDefnPopupLaunched(PopupFetchEvent e)
    {
        if (!isNewExtF()){
            setCurrentSelectedExtfieldDefn();
            setExtfExprTokens();
        }
    }

    /**
     * Initialize saved regex popup.
     * @param popupFetchEvent 
     */
    public void savedregexSelectPopupLaunched(PopupFetchEvent popupFetchEvent)
    {
        usingSavedRegxAliasStep = 1;
        searchMatchingRegExpr(highlightedText);
        searchRegexpWhichMatches = highlightedText;
    }
    
    public void searchRegexpsWhichMatch(ActionEvent actionEvent)
    {
        searchMatchingRegExpr(searchRegexpWhichMatches);
    }
    
    /**
     * Search regex that matches given text.
     * @param textWhichRegexpShouldMatch 
     */
    private void searchMatchingRegExpr(String textWhichRegexpShouldMatch)
    {
        List<LoganSavedRegexBean> fromList = getSavedRegularExpList();
        savedRegexesForCurrSelText = new ArrayList<LoganSavedRegexBean>();
        if(fromList != null && textWhichRegexpShouldMatch != null)
        {
            for(LoganSavedRegexBean lsrb : fromList)
            {
                if(textWhichRegexpShouldMatch.matches(lsrb.getRegexContent()))
                {
                    savedRegexesForCurrSelText.add(lsrb);
                }
            }
        }        
    }

    /**
     * Fire when select a saved regex.
     * @param selectionEvent 
     */
    public void savedRegexpRowSelected(SelectionEvent selectionEvent)
    {
        currSelSavedRegexBean = null;
        RichTable sregs = (RichTable) selectionEvent.getSource();
        List selRows = LoganLibUiUtil.getRowSelectedFromUiTable(sregs);
        if (selRows != null && selRows.size() > 0)
        {
            currSelSavedRegexBean = (LoganSavedRegexBean) selRows.get(0);
        }
    }

    /**
     * Given a suggext regex which matches sample text.
     * @param actionEvent 
     */
    public void suggestRegExpToMatch(ActionEvent actionEvent)
    {
        //TODO JPA
//        suggestions = 
//            LoganLibUiUtil.getRegexOptionsFor(getAddRegexpWhichMatches());
//        suggestionSIs = new ArrayList<SelectItem>();
//        if(suggestions != null)
//        {
//            addRegexp = suggestions.get(0);
//            for(String s : suggestions)
//            {
//                suggestionSIs.add(new SelectItem(s,s));
//            }
//        }
    }

    public List suggestRegexps(FacesContext facesContext,
                               AutoSuggestUIHints autoSuggestUIHints)
    {
        return suggestionSIs;
    }
    
    /**
     * Using saved regex for extend field, updated extend field token and field name.
     * @param dialogEvent 
     */
    public void savedRegExpSelected(DialogEvent dialogEvent)
    {
        if (dialogEvent.getOutcome().name().equals("ok"))
        {
            updateExtfTokenWithExprOrRegexp();
            
            if (validExtFields != null && validExtFields.size() > 0){
                Iterator<SelectItem> iter = validExtFields.iterator();
                while (iter.hasNext()){
                    if (iter.next().getValue().equals(this.currFieldName))
                    {
                        iter.remove();
                        break;
                    }
                }
                
                currFieldName = (String)(validExtFields.get(0)).getValue();
                currFieldDname = validExtFields.get(0).getLabel();
            } else {
                currFieldName = "";
                currFieldDname = "";
            }
        }
    }

    /**
     * Enclose the field name and expression with curly braces
     * Ex.<br>
     * output regex = "sample string with {FIELD1} and {FIELD2:@Client_ID}";<br>
     *
     */
    private void updateExtfTokenWithExprOrRegexp()
    {
        StringBuilder sb = new StringBuilder();
        if (textLeftOfHighlighted != null)
            sb.append(this.textLeftOfHighlighted);
        if (spaceBeforeHighlight)
            sb.append(" ");
        if (USE_EXPR_AS_EXTF.equals(useExrAs))
        {
            sb.append("{").append(currFieldDname);
            if (currFieldNameExpr != null &&
                currFieldNameExpr.trim().length() > 0)
            {
                if(usingSavedRegxAliasStep == 2)
                    sb.append(SAVED_REGEXP_USAGE_IDENTIFIER);
                else
                    sb.append(PLAIN_REGEXP_USAGE_IDENTIFIER);
                usingSavedRegxAliasStep = 0;
                sb.append(currFieldNameExpr);
            }
            sb.append("}");
        }
        else
        {
            sb.append(currFieldNameExpr);
        }
        if (spaceAfterHighlight)
            sb.append(" ");
        if (textRightOfHighlighted != null)
            sb.append(this.textRightOfHighlighted);

        currSelExtFieldExpression = sb.toString();
        setDefaultHighlightText();
    }
    
    /**
     * Set default hightlight text
     */
    private void setDefaultHighlightText()
    {
        currFieldNameExpr = "";
        initHigtLightRange();
        setHighlightTextRange();
        pprExprBuilderInputTexts();
    }

    /**
     * Replace token with given regex expression.
     * @param actionEvent
     */
    public void replaceTokenWithGivenRegexp(ActionEvent actionEvent)
    {
        RichCommandMenuItem cmiOpttion =
            (RichCommandMenuItem) actionEvent.getSource();
        if (cmiOpttion != null)
        {
            currFieldNameExpr = cmiOpttion.getText();
            useExrAs = USE_EXPR_AS_REGX;
            updateExtfTokenWithExprOrRegexp();
        }
    }

    /**
     * Update extend field expression when sample log text changed.
     * @param valueChangeEvent
     */
    public void sampleRawLogTextChanged(ValueChangeEvent valueChangeEvent)
    {
        String newValue = (String) valueChangeEvent.getNewValue();
        this.currExtFieldsDefn.setSefBaseFieldLogText(newValue);
        if (isNewExtF()){
            this.currSelExtFieldExpression = newValue;
        }
    }

    /**
     * Update extend field expression while applying sample log text.
     * @param actionEvent
     */
    public void applyExtFieldDefn(ActionEvent actionEvent)
    {
        String rawSample =
            currExtFieldsDefn.getSefBaseFieldLogText();
        if (this.currSelExtFieldExpression == null)
            currExtFieldsDefn.setSefRegex(rawSample);
        this.currSelExtFieldExpression = rawSample;

        RichCommandButton applyBtn =
            (RichCommandButton) actionEvent.getSource();
        if (applyBtn != null)
        {
            RequestContext.getCurrentInstance().addPartialTarget(applyBtn);
        }
        setDefaultHighlightText();
    }

    public void exprBuilderPopupLaunched(PopupFetchEvent popupFetchEvent)
    {
        if (USE_EXPR_AS_EXTF.equals(useExrAs))
        {
            currFieldNameExpr = "";
        }
    }

    /**
     * Initial high light start from 0 to length/10, set high light text range
     * and load valid extend fields for definition.
     *
     */
    public void editExtFieldDefn(ActionEvent actionEvent)
    {
        if (this.currSelExtFieldExpression == null)
        {
            this.currSelExtFieldExpression = currSelRawLogText;
            currExtFieldsDefn.setSefRegex(currSelRawLogText);
        }
        this.currSelExtFieldExpression = currSelRawLogText;
        if (currSelExtFieldExpression != null)
        {
            initHigtLightRange();
            setHighlightTextRange();
        }
        this.setNewExtF(false);
        currExtFieldsDefn.setSefBaseFieldLogText(currSelRawLogText);

        this.textLeftOfHighlighted = null;
        this.highlightedText = null;
        this.textRightOfHighlighted = currSelExtFieldExpression;
        setExtfExprTokens();
        initialValidExtFields();
        
        RichPopup.PopupHints ph = new RichPopup.PopupHints();
        defnpop.show(ph);
    }
    
    /**
     * Test if the regex matches given value.
     * 
     * @param actionEvent
     */
    public void testRegexp(ActionEvent actionEvent)
    {
        if(addRegexpWhichMatches == null || 
           addRegexpWhichMatches.trim().length() == 0 ||
           addRegexp == null || 
           addRegexp.trim().length() == 0)
        {
            RichPopup.PopupHints ph = new RichPopup.PopupHints();
            testerrpop.show(ph);
        }
        else
        {
            addTestRegexpResult = (addRegexpWhichMatches.matches(addRegexp)? "Y": "N");            
        }
        renderSaveResultPanel = false;
        pprAddRegexTestSavePanel();
    }

    /**
     * Launch for adding new regex expression.
     * @param popupFetchEvent
     */
    public void addNewRegexpPopupLaunched(PopupFetchEvent popupFetchEvent)
    {
        addRegexpWhichMatches = null;
        if(searchRegexpWhichMatches != null && searchRegexpWhichMatches.trim().length() > 0 )
            addRegexpWhichMatches = searchRegexpWhichMatches;
        else
        {
            if(highlightedText != null && highlightedText.trim().length() > 0 )
                addRegexpWhichMatches = highlightedText;
        }
    }

    public void highlightSliderRangeChanged(ValueChangeEvent valueChangeEvent)
    {
        NumberRange range = (NumberRange) valueChangeEvent.getNewValue();
        fromIdxOfHighlighted = ((Long) range.getMinimum()).intValue();
        if (fromIdxOfHighlighted >= currSelExtFieldExpression.length())
            fromIdxOfHighlighted = currSelExtFieldExpression.length() - 1;
        toIdxOfHighlighted = ((Long) range.getMaximum()).intValue();
        if (toIdxOfHighlighted >= currSelExtFieldExpression.length())
            toIdxOfHighlighted = currSelExtFieldExpression.length() - 1;
        setHighlightTextRange();
    }

    /**
     * Set start and end index for extend field expression and set high light text.
     * @param ce
     * @param startOffset
     * @param endOffset
     */
    private void setClientEventToAndFromIndexes(ClientEvent ce, 
                                                int startOffset,
                                                int endOffset)
    {
        if(ce != null)
        {
            Double fidx = (Double) ce.getParameters().get("anchorOffset");
            Double eidx = (Double) ce.getParameters().get("focusOffset");
            if(fidx == eidx)
                return;
            fromIdxOfHighlighted = fidx.intValue() + startOffset; 
            toIdxOfHighlighted = eidx.intValue() + endOffset;
            
            if (fromIdxOfHighlighted > toIdxOfHighlighted){
                //switch index number if select to highlight text form end to begin
                int num = fromIdxOfHighlighted;
                fromIdxOfHighlighted = toIdxOfHighlighted;
                toIdxOfHighlighted = num;
            }
            
            if (fromIdxOfHighlighted >= currSelExtFieldExpression.length())
                fromIdxOfHighlighted = currSelExtFieldExpression.length() - 1;
            if (toIdxOfHighlighted >= currSelExtFieldExpression.length())
                toIdxOfHighlighted = currSelExtFieldExpression.length() - 1; 
            setHighlightTextRange();
            pprExprBuilderInputTexts();
        }
    }


    public void handleExtfHighlightStart(ClientEvent ce)
    {
        highlightStartCmpId = ce.getComponent().getId();
    }

    public void handleExtfTextSelectionOnLeft(ClientEvent clientEvent)
    {
        int startOffset = 0;
        int endOffset = -1;
        if(highlightStartCmpId != null)
        {
            if(highlightStartCmpId.indexOf("cl1") >= 0)
            {
                // the end index is from highlighted text
                endOffset = fromIdxOfHighlighted;
            }
            else if(highlightStartCmpId.indexOf("ot19") >= 0)
            {
                // the end index is from textOnRight
                endOffset = toIdxOfHighlighted;
            }
        }
        setClientEventToAndFromIndexes(clientEvent, startOffset, endOffset);
    }

    public void handleExtfTextSelectionAtCenter(ClientEvent clientEvent)
    {
        int startOffset = 0;
        int endOffset = fromIdxOfHighlighted;
        if(highlightStartCmpId != null)
        {
            if(highlightStartCmpId.indexOf("ot19") >= 0)
            {
                startOffset = fromIdxOfHighlighted;
                endOffset = toIdxOfHighlighted;
            }
            else if(highlightStartCmpId.indexOf("cl1") >= 0)
            {
                startOffset = fromIdxOfHighlighted;
                endOffset = fromIdxOfHighlighted;
            }
        }
        setClientEventToAndFromIndexes(clientEvent, startOffset, endOffset);
    }

    public void handleExtfTextSelectionOnRight(ClientEvent clientEvent)
    {
        int startOffset = toIdxOfHighlighted+1;
        int endOffset = toIdxOfHighlighted;
        if(highlightStartCmpId != null)
        {
            if(highlightStartCmpId.indexOf("ot18") >= 0)
            {
                startOffset = 0;
            }
            else if(highlightStartCmpId.indexOf("cl1") >= 0)
            {
                startOffset = fromIdxOfHighlighted;
            }
        }
        setClientEventToAndFromIndexes(clientEvent, startOffset, endOffset);
    }
    
    /**
     * Set high light text range, the text will wrap with at length 70, and the 
     * highlighted range is from the start index to end which we have from context
     */
    private void setHighlightTextRange()
    {
        //TODO JPA
//        int chars = 0;
//        if (currSelExtFieldExpression != null)
//        {
//            chars = currSelExtFieldExpression.length();
//        }
//        
//        if (chars > 70){
//            wrapText = true;
//        } else {
//            wrapText = false;
//        }
//
//        highlightedRange = fromIdxOfHighlighted + "," + toIdxOfHighlighted;
//        textLeftOfHighlighted =
//                currSelExtFieldExpression.substring(0, fromIdxOfHighlighted);
//        spaceBeforeHighlight =
//                (Character.isWhitespace(currSelExtFieldExpression.charAt(fromIdxOfHighlighted)));
//        
//        if (fromIdxOfHighlighted - 1 >= 0)
//        {
//            spaceBeforeHighlight = (spaceBeforeHighlight || 
//              Character.isWhitespace(currSelExtFieldExpression.charAt(fromIdxOfHighlighted - 1)));
//        }
//        
//        textRightOfHighlighted =
//                currSelExtFieldExpression.substring(toIdxOfHighlighted + 1);
//        spaceAfterHighlight =
//                (Character.isWhitespace(currSelExtFieldExpression.charAt(toIdxOfHighlighted)));
//        
//        if (toIdxOfHighlighted < currSelExtFieldExpression.length() - 1)
//        {
//            spaceAfterHighlight = (spaceAfterHighlight || 
//                Character.isWhitespace(currSelExtFieldExpression.charAt(toIdxOfHighlighted + 1)));
//        }
//        
//        highlightedText =
//                currSelExtFieldExpression.substring(fromIdxOfHighlighted,
//                                                    toIdxOfHighlighted + 1);
//        tokenToRegexOptions =
//                LoganLibUiUtil.getRegexOptionsFor(highlightedText);
    }
        
    public void setDefinitions(List<LoganSourceExtFieldsDefn> definitions)
    {
        extFieldsBean.setDefinitions(definitions);
    }

    public List<LoganSourceExtFieldsDefn> getDefinitions()
    {
        return extFieldsBean.getDefinitions();
    }

    public void setSettings(List<LoganSourceExtFieldsSetting> settings)
    {
        extFieldsBean.setSettings(settings);
    }

    public List<LoganSourceExtFieldsSetting> getSettings()
    {
        return extFieldsBean.getSettings();
    }

    public void setCurrExtFieldsDefn(LoganSourceExtFieldsDefn currSelectedExtFieldsDefn)
    {
        this.currExtFieldsDefn = currSelectedExtFieldsDefn;
    }

    public LoganSourceExtFieldsDefn getCurrExtFieldsDefn()
    {
        return currExtFieldsDefn;
    }
    
    public void setExtFieldsBean(LoganSourceExtFieldsBean extFieldsBean)
    {
        this.extFieldsBean = extFieldsBean;
    }

    public LoganSourceExtFieldsBean getExtFieldsBean()
    {
        return extFieldsBean;
    }

    public boolean isReadOnlyMode()
    {
        return modeBean.isReadOnlyMode();
    }
    
    /**
     * Get common fields that using by the source
     * @return commonFields
     *              List of fields
     */
    public List<SelectItem> getCommonFields()
    {
        //TODO JPA
//        if (commonFields == null)
//        {
//            commonFields = LoganLibUiUtil.getBaseCommonFieldsForSource(sourceId);
//        }
        return commonFields;
    }
    
    public void setValidExtFields(List<SelectItem> validExtFields) {
        this.validExtFields = validExtFields;
    }
    
    public List<SelectItem> getValidExtFields()
    {
        return validExtFields;
    }
    
    /**
     * initial valid extended fields in the drop down that is type 1 and has 
     * not already been used by this source, also set default field name. 
     *
     */
    public void initialValidExtFields()
    {
        //TODO JPA
//        List<SelectItem> baseCommonFields = LoganLibUiUtil.getBaseCommonFieldsList();
//        List<LoganSourceExtFieldsSetting> settings = this.getSettings();
//        List<SelectItem> commonF = getCommonFields();
//        
//        if (baseCommonFields != null && baseCommonFields.size() > 0)
//        {
//            Iterator<SelectItem> iter = baseCommonFields.iterator();
//            
//            while (iter.hasNext()){
//                String fieldIname = (String)iter.next().getValue();
//                boolean flag = true;
//                
//                if (settings != null && settings.size() > 0) {
//                    for (LoganSourceExtFieldsSetting setting : settings){
//                        String field = setting.getSeffNewFieldIname();
//                        if (fieldIname.equals(field)){
//                            iter.remove();
//                            flag = false;
//                            break;
//                        }
//                    }
//                }
//                
//                if (commonF != null && commonF.size() > 0 && flag){
//                    for (SelectItem item : commonF){
//                        if (fieldIname.equals(item.getValue())){
//                            iter.remove();
//                            break;
//                        }
//                    }
//                } 
//            }
//     
//            validExtFields = baseCommonFields;
//            if (validExtFields != null && validExtFields.size() > 0) {
//                currFieldName = (String)(validExtFields.get(0)).getValue();
//                currFieldDname = validExtFields.get(0).getLabel();
//            }
//        }
    }
    
    /**
     * handle adding extend field definition from observation.
     * figure out if it is field obs field name from page then initial the popup 
     * data for extend field definition
     * throw error message if selected column is not field.
     * 
     * @param event
     */
    public void handleResultTableContextMenuOperation(ClientEvent event)
    {
        //TODO JPA
//        String fieldObsName = (String) event.getParameters().get("fieldName");    
//        String fieldValue = (String) event.getParameters().get("fieldValue");      
//        this.sourceName = (String) event.getParameters().get("sourceName");
//        String fieldObsFieldname = null;
//        
//        if (fieldObsName.indexOf("OBS_FIELD_") == 0) {
//            fieldObsFieldname = fieldObsName.substring(10).toUpperCase();
//        } else {
//            //throw warning message if not a field
//            UiUtil.displayErrorMsg(UiUtil.getLoganBundle(), "ERRORHDR", "COLUMN_NOT_FIELD_ERROR");
//            return;
//        }
//        
//        this.setObsMode(true);
//        this.setModeBean(new LaunchContextModeBean("EDIT"));
//        
//        sourceId = this.getSourceIdByName(sourceName);
//        
//        extFieldsBean = instantiateExtFiledsBean();
//        extFieldsBean.initExtFields(sourceId, modeBean);
//        extFieldsBean.initModeSpecificData(modeBean);
//        extFieldsBeanRef = (LoganSourceExtFieldsBean)extFieldsBean.clone();
//        
//        initialParamForDefn();
//        
//        currExtFieldsDefn = new LoganSourceExtFieldsDefn();
//        currExtFieldsDefn.setSefSourceId(sourceId);
//        
//        this.currSelRawLogText = fieldValue;
//        this.currSelExtFieldExpression = fieldValue;
//
//        String[] fieldName = LoganLibUiUtil.getFieldNameByObsFieldName(fieldObsFieldname);
//        currExtFieldsDefn.setSefBaseFieldIname(fieldName[0]);
//        currExtFieldsDefn.setSefBaseFieldDname(fieldName[1]);
//        
//        initialValidExtFields();
//        setDefaultHighlightText();
//        
//        showExtFieldPopup(); 
    }
    
    /**
     * get source id by using source name.
     * 
     * @param sourceName
     * @return sourceId
     */
    public Number getSourceIdByName(String sourceName)
    {
        if (sourceName == null)
            return null;


//TODO JPA
//        Number sourceId = null;
//        LoganAMImpl am = LoganLibUiUtil.getAppModule();
//        EMViewObjectImpl defVO = am.getEmLoganSourceVO();
//        defVO.setApplyViewCriteriaName("EmLoganSourceVOCriteria",
//                                       false);
//        // set the bind var value
//        defVO.ensureVariableManager().setVariableValue("sourceNameVar",
//                                                       sourceName);
//        defVO.executeQuery();
//        RowSetIterator vorsetItrMy =
//            defVO.createRowSetIterator(null); // do not use the getRowSetIterator() method
//        while (vorsetItrMy.hasNext())
//        {
//            Row vorow = vorsetItrMy.next();
//            sourceId = (Number) vorow.getAttribute("SourceId");
//
//        }
//        
//        vorsetItrMy.closeRowSetIterator(); 

        return sourceId;
    }
    
    private void showExtFieldPopup()
    {         
        AdfUtil.addScript("showExtFieldPopup();");    
    }
    
    public List<SelectItem> getExtFieldDataTypes()
    {
        //TODO JPA
//        extFieldDataTypes = LoganLibUiUtil.getSelectItemListFromKeys(LoganSourceExtFieldsSetting.getDataTypeSOCList(),
//                                                         UiUtil.getLoganBundle());
        return extFieldDataTypes;
    }
    
    public void setExtFieldDataTypes(List<SelectItem> extFieldDataTypes)
    {
        this.extFieldDataTypes = extFieldDataTypes;
    }
    
    public void extFieldsTabDisclosed(DisclosureEvent disclosureEvent)
    {
        new LoganSourceExtFieldsViewBean();
    }
    
    public void setModeBean(LaunchContextModeBean modeBean) {
        this.modeBean = modeBean;
    }

    public LaunchContextModeBean getModeBean() {
        return modeBean;
    }

    public void setExtFieldsBeanRef(LoganSourceExtFieldsBean extFieldsBeanRef) {
        this.extFieldsBeanRef = extFieldsBeanRef;
    }

    public LoganSourceExtFieldsBean getExtFieldsBeanRef() {
        return extFieldsBeanRef;
    }
    
    public void handleOk(ActionEvent actionEvent){
        boolean readOnlyMode = modeBean.isReadOnlyMode();
        if (readOnlyMode) // SHOW DETAILS
            return;
        
        saveExtFields();
    }
    
    public void saveExtFields(){
        super.save(extFieldsBeanRef, modeBean);
    }

    public ILoganSourceDataBean getSourceDataBean() {
        return extFieldsBean;
    }

    public void setSourceDataBean(ILoganSourceDataBean tsdb) {
        this.extFieldsBean = (LoganSourceExtFieldsBean)tsdb;
    }

    public String getName() {
        if (this.isObsMode())
            return this.getSourceName();
        else
            return this.sourceCurrentDataBean.getName();
    }

    public void setSourceCurrentDataBean(LoganSourceDetailsBean sourceCurrentDataBean) {
        this.sourceCurrentDataBean = sourceCurrentDataBean;
    }

    public LoganSourceDetailsBean getSourceCurrentDataBean() {
        return sourceCurrentDataBean;
    }

    public void metricKeyChanged(ValueChangeEvent valueChangeEvent)
    {
        Boolean selectedValue = (Boolean) valueChangeEvent.getNewValue();
        
        if (selectedValue.booleanValue() == true && extFieldSettingsSelRow != null)
            extFieldSettingsSelRow.setMetricVEligible(false);   
        
    }
    
    public void metricValueChanged(ValueChangeEvent valueChangeEvent)
    {
        Boolean selectedValue = (Boolean) valueChangeEvent.getNewValue();
    
        if (selectedValue.booleanValue() == true && extFieldSettingsSelRow != null)
            extFieldSettingsSelRow.setMetricKEligible(false);
            
    }

    public void selectedSettings(SelectionEvent selectionEvent)
    {
        //TODO JPA
//        RichTable iT = (RichTable) selectionEvent.getComponent();
//        this.extFieldSettingsSelRow = (LoganSourceExtFieldsSetting) LoganLibUiUtil.getSelectedIPRowForTable(iT);
    }
    
    public void setCurrSelExtFieldExpression(String currSelExtFieldExpression)
    {
        this.currSelExtFieldExpression = currSelExtFieldExpression;
    }

    public String getCurrSelExtFieldExpression()
    {
        return currSelExtFieldExpression;
    }

    public void setSavedRegularExpList(List<LoganSavedRegexBean> savedRegularExpList)
    {
        this.extFieldsBean.setSavedRegularExpList(savedRegularExpList);
    }

    public List<LoganSavedRegexBean> getSavedRegularExpList()
    {
        return extFieldsBean.getSavedRegularExpList();
    }

    public void setSavedRegularExpMap(Map<String, LoganSavedRegexBean> savedRegularExpMap)
    {
        this.extFieldsBean.setSavedRegularExpMap(savedRegularExpMap);
    }

    public Map<String, LoganSavedRegexBean> getSavedRegularExpMap()
    {
        return extFieldsBean.getSavedRegularExpMap();
    }

    public void setCurrSelSavedRegexBean(LoganSavedRegexBean currSelSavedRegexBean)
    {
        this.currSelSavedRegexBean = currSelSavedRegexBean;
    }

    public LoganSavedRegexBean getCurrSelSavedRegexBean()
    {
        return currSelSavedRegexBean;
    }

    public void setUsregpop(RichPopup usregpop)
    {
        this.usregpop = usregpop;
    }

    public RichPopup getUsregpop()
    {
        return usregpop;
    }

    public void setUnSavedRegExps(List<LoganSavedRegexBean> unSavedRegExps)
    {
        this.unSavedRegExps = unSavedRegExps;
    }

    public List<LoganSavedRegexBean> getUnSavedRegExps()
    {
        return unSavedRegExps;
    }
    
    public void setCreateExtf(boolean createExtf)
    {
        this.createExtf = createExtf;
    }

    public boolean isCreateExtf()
    {
        return createExtf;
    }

    public void setCurrFieldNameExpr(String currFieldNameExpr)
    {
        this.currFieldNameExpr = currFieldNameExpr;
    }

    public String getCurrFieldNameExpr()
    {
        return currFieldNameExpr;
    }
    
    public void setCurrSelRawLogText(String currSelRawLogText)
    {
        this.currSelRawLogText = currSelRawLogText;
    }

    public String getCurrSelRawLogText()
    {
        return currSelRawLogText;
    }

    public void setUseExrAs(String useExrAs)
    {
        this.useExrAs = useExrAs;
    }

    public String getUseExrAs()
    {
        return useExrAs;
    }

    public void setSpaceBeforeHighlight(boolean spaceBeforeHighlight)
    {
        this.spaceBeforeHighlight = spaceBeforeHighlight;
    }

    public boolean isSpaceBeforeHighlight()
    {
        return spaceBeforeHighlight;
    }

    public void setSpaceAfterHighlight(boolean spaceAfterHighlight)
    {
        this.spaceAfterHighlight = spaceAfterHighlight;
    }

    public boolean isSpaceAfterHighlight()
    {
        return spaceAfterHighlight;
    }

    public void setHighlightedRange(String highlightedRange)
    {
        this.highlightedRange = highlightedRange;
    }

    public String getHighlightedRange()
    {
        return highlightedRange;
    }

    public void setContpop(RichPopup contpop)
    {
        this.contpop = contpop;
    }

    public RichPopup getContpop()
    {
        return contpop;
    }

    public void setDefnpop(RichPopup defnpop)
    {
        this.defnpop = defnpop;
    }

    public RichPopup getDefnpop()
    {
        return defnpop;
    }

    public void setSearchRegexpWhichMatches(String searchRegexpWhichMatches)
    {
        this.searchRegexpWhichMatches = searchRegexpWhichMatches;
    }

    public String getSearchRegexpWhichMatches()
    {
        return searchRegexpWhichMatches;
    }

    public void setSearchRegexpAlias(String searchRegexpAlias)
    {
        this.searchRegexpAlias = searchRegexpAlias;
    }

    public String getSearchRegexpAlias()
    {
        return searchRegexpAlias;
    }

    public void setSearchRegexp(String searchRegexp)
    {
        this.searchRegexp = searchRegexp;
    }

    public String getSearchRegexp()
    {
        return searchRegexp;
    }

    public void searchSavedRegularExpressions(ActionEvent actionEvent)
    {
        // // TODO:Add event code here...
    }

    public void setAddRegexpWhichMatches(String addRegexpWhichMatches)
    {
        this.addRegexpWhichMatches = addRegexpWhichMatches;
    }

    public String getAddRegexpWhichMatches()
    {
        if(addRegexpWhichMatches == null && highlightedText != null)
            addRegexpWhichMatches = highlightedText;
        return addRegexpWhichMatches;
    }

    public void setAddRegexpAlias(String addRegexpAlias)
    {
        this.addRegexpAlias = addRegexpAlias;
    }

    public String getAddRegexpAlias()
    {
        return addRegexpAlias;
    }

    public void setAddRegexp(String addRegexp)
    {
        this.addRegexp = addRegexp;
    }

    public String getAddRegexp()
    {
        return addRegexp;
    }

    public void setAddTestRegexpOn(String addTestRegexpOn)
    {
        this.addTestRegexpOn = addTestRegexpOn;
    }

    public String getAddTestRegexpOn()
    {
        return addTestRegexpOn;
    }

    public void setAddTestRegexpResult(String addTestRegexpResult)
    {
        this.addTestRegexpResult = addTestRegexpResult;
    }

    public String getAddTestRegexpResult()
    {
        return addTestRegexpResult;
    }

    public void setAddRegexpDesc(String addRegexpDesc)
    {
        this.addRegexpDesc = addRegexpDesc;
    }

    public String getAddRegexpDesc()
    {
        return addRegexpDesc;
    }

    public List<String> getTokenToRegexOptions()
    {
        return tokenToRegexOptions;
    }

    public void setCurrFieldName(String currFieldName)
    {
        this.currFieldName = currFieldName;
    }

    public String getCurrFieldName()
    {
        return currFieldName;
    }

    public void setCurrSelExtBaseFieldDname(String currSelExtBaseFieldDname)
    {
        this.currSelExtBaseFieldDname = currSelExtBaseFieldDname;
    }

    public String getCurrSelExtBaseFieldDname()
    {
        return currSelExtBaseFieldDname;
    }
    
    public String getAddRefBaseFieldIname()
    {
        return this.currExtFieldsDefn.getSefBaseFieldIname();
    }
    
    public void setAddRefBaseFieldIname(String sefBaseFieldIname)
    {
        this.currExtFieldsDefn.setSefBaseFieldIname(sefBaseFieldIname);
    }
    
    public String getAddRefBaseFieldDname()
    {
        return this.currExtFieldsDefn.getSefBaseFieldDname();
    }
    
    public void setAddRefBaseFieldDname(String sefBaseFieldDname)
    {
        this.currExtFieldsDefn.setSefBaseFieldDname(sefBaseFieldDname);
    }

    public void setTextLeftOfHighlighted(String textLeftOfHighlighted)
    {
        this.textLeftOfHighlighted = textLeftOfHighlighted;
    }

    public String getTextLeftOfHighlighted()
    {
        return textLeftOfHighlighted;
    }

    public void setHighlightedText(String highlightedText)
    {
        this.highlightedText = highlightedText;
    }

    public String getHighlightedText()
    {
        return highlightedText;
    }

    public void setTextRightOfHighlighted(String textRightOfHighlighted)
    {
        this.textRightOfHighlighted = textRightOfHighlighted;
    }

    public String getTextRightOfHighlighted()
    {
        return textRightOfHighlighted;
    }

    public void setFromIdxOfHighlighted(int fromIdxOfHighlighted)
    {
        this.fromIdxOfHighlighted = fromIdxOfHighlighted;
    }

    public int getFromIdxOfHighlighted()
    {
        return fromIdxOfHighlighted;
    }

    public void setToIdxOfHighlighted(int toIdxOfHighlighted)
    {
        this.toIdxOfHighlighted = toIdxOfHighlighted;
    }

    public int getToIdxOfHighlighted()
    {
        return toIdxOfHighlighted;
    }

    public void setSavedRegexesForCurrSelText(List<LoganSavedRegexBean> savedRegexesForCurrSelText)
    {
        this.savedRegexesForCurrSelText = savedRegexesForCurrSelText;
    }

    public List<LoganSavedRegexBean> getSavedRegexesForCurrSelText()
    {
        return savedRegexesForCurrSelText;
    }

    public void setTesterrpop(RichPopup testerrpop)
    {
        this.testerrpop = testerrpop;
    }

    public RichPopup getTesterrpop()
    {
        return testerrpop;
    }

    public void setRenderSaveResultPanel(boolean renderSaveResultPanel)
    {
        this.renderSaveResultPanel = renderSaveResultPanel;
    }

    public boolean isRenderSaveResultPanel()
    {
        return renderSaveResultPanel;
    }

    public void setOptionspop(RichPopup optionspop)
    {
        this.optionspop = optionspop;
    }

    public RichPopup getOptionspop()
    {
        return optionspop;
    }

    public void setNewExtF(boolean newExtF) {
        this.newExtF = newExtF;
    }

    public boolean isNewExtF() {
        return newExtF;
    }

    public void setWrapText(boolean wrapText) {
        this.wrapText = wrapText;
    }

    public boolean isWrapText() {
        return wrapText;
    }

    public void setSourceId(Number sourceId) {
        this.sourceId = sourceId;
    }

    public Number getSourceId() {
        return sourceId;
    }

    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
    }

    public String getSourceName() {
        return sourceName;
    }

    public void setObsMode(boolean obsMode) {
        this.obsMode = obsMode;
    }

    public boolean isObsMode() {
        return obsMode;
    }

    public void setCurrFieldDname(String currFieldDname) {
        this.currFieldDname = currFieldDname;
    }

    public String getCurrFieldDname() {
        return currFieldDname;
    }
}
