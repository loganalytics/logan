package oracle.saas.logan.view.source;

/* Copyright (c) 2002, 2014, Oracle and/or its affiliates. 
All rights reserved.*/

/*
   DESCRIPTION
   Log analytics library page data bean for Soruces

   PRIVATE CLASSES
    <list of private classes defined - with one-line descriptions>

   NOTES
    <other useful comments, qualificationsf, etc.>

    MODIFIED    (MM/DD/YY)
    minglei     03/20/14 - migrated to standalone JPA version
    zudeng      12/25/13 - ui import xml
    vivsharm    01/11/13 - move common func to AbstractLoganLibBean
    vivsharm    01/04/13 - log Analytics
 */

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;

import oracle.adf.view.rich.component.rich.data.RichTable;
import oracle.adf.view.rich.context.AdfFacesContext;
import oracle.adf.view.rich.event.DialogEvent;
import oracle.adf.view.rich.event.PopupFetchEvent;

import oracle.saas.logan.model.client.LoganSessionBeanProxy;
import oracle.saas.logan.model.persistance.EmLoganMetaSourceType;
import oracle.saas.logan.model.persistance.EmLoganRuleSourceMap;
import oracle.saas.logan.model.persistance.EmLoganSource;
import oracle.saas.logan.model.persistance.EmLoganSourcePattern1;
import oracle.saas.logan.model.persistance.EmTargetTypes;
import oracle.saas.logan.model.session.source.LoganSourcePatternSession;
import oracle.saas.logan.model.session.source.LoganSourceSessionBean;
import oracle.saas.logan.util.AdfUtil;
import oracle.saas.logan.util.InterPageMessageBean;
import oracle.saas.logan.util.LoganLibUiUtil;
import oracle.saas.logan.util.UiUtil;

import org.apache.myfaces.trinidad.event.DisclosureEvent;
import org.apache.myfaces.trinidad.event.SelectionEvent;

/**
 *  @version $Header: emcore/jsrc/core/CoreLoganalyticsUi/src/oracle/sysman/core/loganalytics/ui/LoganLibSourcesBean.java /st_emgc_pt-13.1mstr/6 2014/02/20 21:56:45 zudeng Exp $
 *  @author  vivsharm
 *  @since   release specific (what release of product did this appear in)
 */
public class LoganLibSourcesBean {

    protected static final String match_ALL = "ALL";
    protected static final String match_ANY = "ANY";

    protected String matchRadioSelectValue = match_ALL;

    protected String selectedtargetType;
    private Number selectedSourceId;
    private String selectedSourceName;


    private LoganLibSourcePojo selectedSourceEntityPojo;
    
    private boolean selectedSourceIsSystem;

    private String p_source;
    private String p_desc;
    boolean isInited = false;


    protected List<SelectItem> targetTypesList;
    private List<LoganLibSourcePojo> sources;

    private List<EmLoganRuleSourceMap> rsmaps;

    private static final String sourceTable_Id = "emT:pan1:srcs";

    public LoganLibSourcesBean() {
        super();
    }

    /**
     * Using JPA query to initlize the table
     *
     * */
    public void logSourcesTabDisclosed(DisclosureEvent disclosureEvent) {
        initSearch();
    }

    private void initSearch() {
        if (isInited) {
            return;
        }

        List<Object[]> entities = LoganSessionBeanProxy.getEmLoganSourceFindSources();
        if (entities != null && entities.size() > 0) {
            if (rsmaps == null) {
                rsmaps = LoganSessionBeanProxy.getEmLoganRuleSourceMap();
            }
            if (sources == null) {
                sources = new ArrayList<LoganLibSourcePojo>();
                isInited = true;
                for (Object[] objs : entities) {
                    EmLoganSource emLoganSource = (EmLoganSource)objs[0];
                    EmLoganMetaSourceType emLoganMetaSourceType = (EmLoganMetaSourceType)objs[1];
                    EmTargetTypes emTargetTypes = (EmTargetTypes)objs[2];

                    Integer sourceId = emLoganSource.getSourceId();
                    LoganLibSourcePojo source = new LoganLibSourcePojo();
                    source.setSourceAuthor(emLoganSource.getSourceAuthor());
                    source.setSourceId(sourceId);
                    source.setSourceDescription(emLoganSource.getSourceDescription());
                    source.setSourceDname(emLoganSource.getSourceDname());
                    source.setSourceIname(emLoganSource.getSourceIname());
                    source.setSourceIsSystem(emLoganSource.getSourceIsSystem());
                    source.setSourceLastUpdatedDate(emLoganSource.getSourceLastUpdatedDate());
                    source.setSrcTargetType(emTargetTypes.getTargetType());
                    source.setSrcTargetTypeDisplayNls(emTargetTypes.getTypeDisplayName());
                    source.setSrctypeDname(emLoganMetaSourceType.getSrctypeDname());
                    source.setSrctypeIname(emLoganMetaSourceType.getSrctypeIname());
                    source.setSourceIsSecureContent(emLoganSource.getSourceIsSecureContent());
                    source.setSourceEditVersion(emLoganSource.getSourceEditVersion());
                    source.setSourceCriticalEditVersion(emLoganSource.getSourceCriticalEditVersion());
                    
                    int count = 0;
                    if (sourceId != null && rsmaps.size() > 0) {
                        for (EmLoganRuleSourceMap rsmap : rsmaps) {
                            if (rsmap.getRsSourceId() != null && rsmap.getRsSourceId().equals(sourceId)) {
                                count++;
                            }
                        }
                    }
                    source.setRulesUsing(count);

                    sources.add(source);
                }

            }
        }
    }


    public void matchRadioValueChanged(ValueChangeEvent valueChangeEvent) {
        String mrnv = (String)valueChangeEvent.getNewValue();
        if (mrnv != null && mrnv.trim().length() > 0) {
            matchRadioSelectValue = mrnv;
        }
    }


    public void targetTypeValueChanged(ValueChangeEvent valueChangeEvent) {
        this.selectedtargetType = (String)valueChangeEvent.getNewValue();
    }

    public List<SelectItem> getTargetTypesList() {
        targetTypesList = LoganLibUiUtil.getTargetTypesList();
        return targetTypesList;
    }


    /**
     * @param ae
     */
    public void handleFilterSearch(ActionEvent ae) {
        sources = null; //force refresh
        boolean isMatchAll = matchRadioSelectValue.equals(match_ALL) ? true : false;
        String ttype = selectedtargetType;
        if(selectedtargetType != null && selectedtargetType.equals("ALL"))
        {
            ttype=null;
        }
        
        List<Object[]> entities =
            LoganSessionBeanProxy.getEmLoganSourceFilteredSourceList(ttype, null, p_source, p_desc,
                                                                     isMatchAll);
        if (entities != null && entities.size() > 0) {
            rsmaps = LoganSessionBeanProxy.getEmLoganRuleSourceMap();
            sources = new ArrayList<LoganLibSourcePojo>();

            for (Object[] objs : entities) {
                EmLoganSource emLoganSource = (EmLoganSource)objs[0];
                EmLoganMetaSourceType emLoganMetaSourceType = (EmLoganMetaSourceType)objs[1];
                EmTargetTypes emTargetTypes = (EmTargetTypes)objs[2];
                
                Integer sourceId = emLoganSource.getSourceId();
                LoganLibSourcePojo source = new LoganLibSourcePojo();
                source.setSourceAuthor(emLoganSource.getSourceAuthor());
                source.setSourceId(sourceId);
                source.setSourceDescription(emLoganSource.getSourceDescription());
                source.setSourceDname(emLoganSource.getSourceDname());
                source.setSourceIname(emLoganSource.getSourceIname());
                source.setSourceIsSystem(emLoganSource.getSourceIsSystem());
                source.setSourceLastUpdatedDate(emLoganSource.getSourceLastUpdatedDate());
                source.setSrcTargetType(emTargetTypes.getTargetType());
                source.setSrcTargetTypeDisplayNls(emTargetTypes.getTypeDisplayName());
                source.setSrctypeDname(emLoganMetaSourceType.getSrctypeDname());
                source.setSrctypeIname(emLoganMetaSourceType.getSrctypeIname());
                source.setSourceIsSecureContent(emLoganSource.getSourceIsSecureContent());
                source.setSourceEditVersion(emLoganSource.getSourceEditVersion());
                source.setSourceCriticalEditVersion(emLoganSource.getSourceCriticalEditVersion());

                int count = 0;
                if (sourceId != null && rsmaps.size() > 0) {
                    for (EmLoganRuleSourceMap rsmap : rsmaps) {
                        if (rsmap.getRsSourceId() != null && rsmap.getRsSourceId().equals(sourceId)) {
                            count++;
                        }
                    }
                }
                source.setRulesUsing(count);

                sources.add(source);
            }
        }
    }


    public boolean isCreateLikeDisabled() {
        // check if table has multiple or no rows selected then disabled is true
        return LoganLibUiUtil.hasUserSelectedMultipleOrZeroRows(getTabDataTableHandle());
    }

    /**
     * @return
     */
    public boolean isShowDetailsDisabled() {
        // check if table has multiple or no rows selected then disabled is true
        return LoganLibUiUtil.hasUserSelectedMultipleOrZeroRows(getTabDataTableHandle());
    }

    /**
     * @return
     */
    public boolean isEditDisabled() {
        // check if table has multiple or no rows selected then disabled is true
        return LoganLibUiUtil.hasUserSelectedMultipleOrZeroRows(getTabDataTableHandle());
    }

    /**
     * @return
     */
    public boolean isDeleteDisabled() {
        // check if table has multiple or no rows selected then disabled is true
        return LoganLibUiUtil.hasUserSelectedZeroRows(getTabDataTableHandle());
    }


    public int getEstimatedRowCount() {
        if (sources == null) {
            return 0;
        } else {
            return sources.size();
        }
    }

    /**
     * Get the handle to the Main data table on the tab
     * @return
     */
    public RichTable getTabDataTableHandle() {
        return (RichTable)AdfUtil.findComponent(sourceTable_Id);
    }

    public String getSelectedSourceId() {
        return selectedSourceId.toString();
    }

    public String getSelectedSourceName() {
        return selectedSourceName;
    }


    public void sourceSelectionListener(SelectionEvent selectionEvent) {
        RichTable iT = (RichTable)selectionEvent.getSource();
        setSelectedSourceData(iT);
    }


    private void setSelectedSourceData()
    {
           setSelectedSourceData(null);
    }
    
    
    /**
     * Remove source and related patterns, JPA version
     * */
    public void handleSourceDelete(DialogEvent dialogEvent)
    {
        if (dialogEvent.getOutcome().name().equals("ok") && !isSelectedSourceIsSystem())
        {
            if(selectedSourceId == null)
            {
                return; 
            }
            LoganSourceSessionBean srcFacade = LoganSessionBeanProxy.getEmLoganSourceFacade();
            List<EmLoganSource>  remEntities = srcFacade.getEmLoganSourceFindBySourceId(selectedSourceId.intValue());
            if(remEntities != null && remEntities.size() > 0)
            {
                for(EmLoganSource remEntity:remEntities)
                {
                    srcFacade.removeEntity(remEntity);
                }
            }
            
            //ensure the clean remove
            LoganSourcePatternSession ptnFacade = LoganSessionBeanProxy.getEmLoganSourcePatternFacade();
            List<EmLoganSourcePattern1> relatedPatterns = ptnFacade.getEmLoganSourcePatternFindAllBySourceId(selectedSourceId.intValue());
            if(relatedPatterns != null && relatedPatterns.size() > 0)
            {
                for(EmLoganSourcePattern1 remEntity:relatedPatterns)
                {
                    ptnFacade.removeEmLoganSourcePattern1(remEntity);
                }
            }
            //force refresh table
            handleFilterSearch(null);                                 
            AdfFacesContext.getCurrentInstance().addPartialTarget(getTabDataTableHandle());
        }
    }
    
    public void setSelectedSourceIsSystem(boolean selectedSourceIsSystem)
    {
        this.selectedSourceIsSystem = selectedSourceIsSystem;
    }

    public boolean isSelectedSourceIsSystem()
    {
        return selectedSourceIsSystem;
    }
    
    /**
     * JPA pojo version
     * */
    private void setSelectedSourceData(RichTable iT) {
        if (iT == null) {
            iT = getTabDataTableHandle();
        }
        LoganLibSourcePojo data = null;
        if (iT != null) {
            org.apache.myfaces.trinidad.model.RowKeySet rowKeySet = iT.getSelectedRowKeys();
            if (rowKeySet != null) {
                Iterator iter = rowKeySet.iterator();
                //For that the table is single selected, or below code should be updated. -ming
                if (iter != null && iter.hasNext()) {
                    iT.setRowKey(iter.next());
                    data = (LoganLibSourcePojo)iT.getRowData();
                    selectedSourceId = data.getSourceId();
                    selectedSourceName = data.getSourceDname();
                    Integer isSys =  data.getSourceIsSystem();
                    selectedSourceIsSystem = (isSys != null && isSys.intValue() == 1);
                    selectedSourceEntityPojo = data;
                }
            }
        }
    }

    /**
     * @param popupFetchEvent
     */
    public void createLikePopupFetchListener(PopupFetchEvent popupFetchEvent) {
        setSelectedSourceData();
        selectedSourceName = UiUtil.getUiString("COPY_OF") + " " + selectedSourceName;
    }

    public void deletePopupFetchListener(PopupFetchEvent popupFetchEvent) {
        setSelectedSourceData();
    }

    public void sourceCreateLikeListener(DialogEvent dialogEvent) {
        if (dialogEvent.getOutcome().name().equals("ok")) {
            Map ipmParams = InterPageMessageBean.getCurrentInstance().getParams();
            ipmParams.put("create_like_logan_source_name", selectedSourceName);
            ipmParams.put("mode", "CREATE_LIKE");
            ipmParams.put("sourceId", selectedSourceId);
        }
    }


    public void setMatchRadioSelectValue(String matchRadioSelectValue) {
        this.matchRadioSelectValue = matchRadioSelectValue;
    }

    public String getMatchRadioSelectValue() {
        return matchRadioSelectValue;
    }

    public void setSelectedtargetType(String selectedtargetType) {
        this.selectedtargetType = selectedtargetType;
    }

    public String getSelectedtargetType() {
        return selectedtargetType;
    }

    public void setSources(List<LoganLibSourcePojo> sources) {
        this.sources = sources;
    }

    public List<LoganLibSourcePojo> getSources() {
        if (!isInited) {
            initSearch();
        }
        return sources;
    }


    public void setP_source(String p_source) {
        this.p_source = p_source;
    }

    public String getP_source() {
        return p_source;
    }

    public void setP_desc(String p_desc) {
        this.p_desc = p_desc;
    }

    public String getP_desc() {
        return p_desc;
    }
    
    public LoganLibSourcePojo getSelectedSourceEntityPojo() {
        return selectedSourceEntityPojo;
    }

}
