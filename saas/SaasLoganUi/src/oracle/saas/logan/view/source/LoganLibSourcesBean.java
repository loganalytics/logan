package oracle.saas.logan.view.source;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;

import oracle.adf.view.rich.component.rich.data.RichTable;
import oracle.adf.view.rich.event.DialogEvent;
import oracle.adf.view.rich.event.PopupFetchEvent;

import oracle.saas.logan.model.client.LoganSessionBeanProxy;
import oracle.saas.logan.model.persistance.EmLoganMetaSourceType;
import oracle.saas.logan.model.persistance.EmLoganRuleSourceMap;
import oracle.saas.logan.model.persistance.EmLoganSource;
import oracle.saas.logan.model.persistance.EmTargetTypes;
import oracle.saas.logan.util.AdfUtil;
import oracle.saas.logan.util.InterPageMessageBean;
import oracle.saas.logan.util.LoganLibUiUtil;
import oracle.saas.logan.util.UiUtil;

import org.apache.myfaces.trinidad.event.DisclosureEvent;
import org.apache.myfaces.trinidad.event.SelectionEvent;

public class LoganLibSourcesBean {
    
    protected static final String match_ALL = "ALL";
    protected static final String match_ANY = "ANY";

    protected String matchRadioSelectValue = match_ALL;
    
    protected String selectedtargetType;
    private Number selectedSourceId;
    private String selectedSourceName;

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
    public void logSourcesTabDisclosed(DisclosureEvent disclosureEvent)
    {
        List<Object[]> entities = LoganSessionBeanProxy.getEmLoganSourceFindSources();
        if(entities!= null && entities.size()>0)
        {
            if(rsmaps == null)
            {
                rsmaps = LoganSessionBeanProxy.getEmLoganRuleSourceMap();
            }
            sources = null;//force refresh 
            if(sources == null)
            {
                sources = new ArrayList<LoganLibSourcePojo>();
                
                for( Object[] objs:  entities)
                {
                    EmLoganSource emLoganSource = (EmLoganSource)objs[0];
                    EmLoganMetaSourceType emLoganMetaSourceType = (EmLoganMetaSourceType)objs[1];
                    EmTargetTypes emTargetTypes = (EmTargetTypes)objs[2];
                    
                    LoganLibSourcePojo source = new LoganLibSourcePojo();
                    source.setSourceAuthor(emLoganSource.getSourceAuthor());
                    source.setSourceId(emLoganSource.getSourceId());
                    source.setSourceDescription(emLoganSource.getSourceDescription());
                    source.setSourceDname(emLoganSource.getSourceDname());
                    source.setSourceIsSystem(emLoganSource.getSourceIsSystem());
                    source.setSourceLastUpdatedDate(emLoganSource.getSourceLastUpdatedDate());
                    source.setSrcTargetTypeDisplayNls(emLoganSource.getSourceDescriptionNlsid());
                    source.setSrctypeDname(emLoganMetaSourceType.getSrctypeDname());
                    Integer sourceId = emLoganSource.getSourceId();
                    int count  = 0;
                    if(sourceId != null && rsmaps.size() > 0 )
                    {
                        for(EmLoganRuleSourceMap rsmap :rsmaps)
                        {
                            if(rsmap.getRsSourceId() != null && rsmap.getRsSourceId().equals(sourceId))
                            {
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
    
    
    public void matchRadioValueChanged(ValueChangeEvent valueChangeEvent)
    {
        String mrnv = (String) valueChangeEvent.getNewValue();
        if (mrnv != null && mrnv.trim().length() > 0)
        {
            matchRadioSelectValue = mrnv;
        }
    }
    
    
    public void targetTypeValueChanged(ValueChangeEvent valueChangeEvent)
    {
        this.selectedtargetType = (String) valueChangeEvent.getNewValue();
    }
    
    public List<SelectItem> getTargetTypesList()
    {
        //cache the select list
        if (targetTypesList == null)
        {
            targetTypesList = LoganLibUiUtil.getTargetTypesList();
        }
        return targetTypesList;
    }
    
    
    /**
     * @param ae
     */
    public void handleSearch(ActionEvent ae)
    {
        
        //TODO  handle - facade
//        LoganAMImpl am = LoganLibUiUtil.getAppModule();
//        EmLoganSourceVOImpl sourceVO =
//            (EmLoganSourceVOImpl) am.findViewObject("EmLoganSourceVO");
//
//        sourceVO.removeApplyViewCriteriaName("EmLoganSourceALLVOCriteria");
//        sourceVO.removeApplyViewCriteriaName("EmLoganSourceANYVOCriteria");
//        RichInputText sourceName = getSourceInputText();
//        String sNameFilter = (String) sourceName.getValue();
//        if (sNameFilter != null)
//        {
//            sNameFilter = sNameFilter.trim();
//            if (sNameFilter.length() == 0)
//                sNameFilter = null;
//        }
//        RichInputText sourceDesc = getSourceDescInputText();
//        String sDescFilter = (String) sourceDesc.getValue();
//        if (sDescFilter != null)
//        {
//            sDescFilter = sDescFilter.trim();
//            if (sDescFilter.length() == 0)
//                sDescFilter = null;
//        }
//
//        if (match_ALL.equals(matchRadioSelectValue))
//        {
//            // use "EmLoganSourceALLVOCriteria"
//            sourceVO.setApplyViewCriteriaName("EmLoganSourceALLVOCriteria",
//                                              false);
//        }
//        else if (match_ANY.equals(matchRadioSelectValue))
//        {
//            // use "EmLoganSourceANYVOCriteria"
//            sourceVO.setApplyViewCriteriaName("EmLoganSourceANYVOCriteria",
//                                              false);
//        }
//        sourceVO.setSourceTargetTypeVar(selectedtargetType);
//        if (selectedtargetType != null &&
//            (selectedtargetType.trim().length() == 0 ||
//             selectedtargetType.equals(match_ALL)))
//        {
//            sourceVO.setSourceTargetTypeVar(null);
//        }
//        sourceVO.setsourceNameVar(sNameFilter);
//        sourceVO.setSourceDescVar(sDescFilter);
//        sourceVO.executeQuery();
//        if (sourceVO.hasNext())
//        {
//            sourceVO.first();
//        }
    }
    
    
    
    public boolean isCreateLikeDisabled()
    {
        // check if table has multiple or no rows selected then disabled is true
        return LoganLibUiUtil.hasUserSelectedMultipleOrZeroRows(getTabDataTableHandle());
    }

    /**
     * @return
     */
    public boolean isShowDetailsDisabled()
    {
        // check if table has multiple or no rows selected then disabled is true
        return LoganLibUiUtil.hasUserSelectedMultipleOrZeroRows(getTabDataTableHandle());
    }

    /**
     * @return
     */
    public boolean isEditDisabled()
    {
        // check if table has multiple or no rows selected then disabled is true
        return LoganLibUiUtil.hasUserSelectedMultipleOrZeroRows(getTabDataTableHandle());
    }

    /**
     * @return
     */
    public boolean isDeleteDisabled()
    {
        // check if table has multiple or no rows selected then disabled is true
        return LoganLibUiUtil.hasUserSelectedZeroRows(getTabDataTableHandle());
    }   
    
    

    /**
     * Get the handle to the Main data table on the tab
     * @return
     */
    public RichTable getTabDataTableHandle()
    {
        return (RichTable) AdfUtil.findComponent(sourceTable_Id);
    }
    
    public String getSelectedSourceId()
    {
        return selectedSourceId.toString();
    }
    
    public String getSelectedSourceName()
    {
        return selectedSourceName;
    }
    
    
    public void sourceSelectionListener(SelectionEvent selectionEvent)
    {
        RichTable iT = (RichTable) selectionEvent.getSource();
        setSelectedSourceData( iT );
    }
    
    
    private void setSelectedSourceData()
    {
        setSelectedSourceData();
    }
    
    private void setSelectedSourceData( RichTable iT )
    {   
        if(iT == null)
        {
            iT = getTabDataTableHandle();    
        }
        LoganLibSourcePojo data = null;
        if (iT != null)
        {
            org.apache.myfaces.trinidad.model.RowKeySet rowKeySet = iT.getSelectedRowKeys();
            if (rowKeySet != null)
            {
                Iterator iter = rowKeySet.iterator();
                if (iter != null && iter.hasNext())
                {
                    iT.setRowKey(iter.next());
                    data = (LoganLibSourcePojo) iT.getRowData();
                    selectedSourceId = data.getSourceId();
                    selectedSourceName = data.getSourceDname();
                }
            }
        }
    }

    /**
     * @param popupFetchEvent
     */
    public void createLikePopupFetchListener(PopupFetchEvent popupFetchEvent)
    {
        setSelectedSourceData();
        selectedSourceName = UiUtil.getUiString("COPY_OF") + " " + selectedSourceName;
    }

    public void deletePopupFetchListener(PopupFetchEvent popupFetchEvent)
    {
        setSelectedSourceData();
    }

    public void sourceCreateLikeListener(DialogEvent dialogEvent)
    {
        if (dialogEvent.getOutcome().name().equals("ok"))
        {
            Map ipmParams =
                InterPageMessageBean.getCurrentInstance().getParams();
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
        if(sources == null)
        {
            logSourcesTabDisclosed(null);
        }
        return sources;
    }



}
