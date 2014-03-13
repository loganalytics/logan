package oracle.saas.logan.view.source;

import java.util.ArrayList;
import java.util.List;

import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;

import oracle.saas.logan.model.client.LoganSessionBeanProxy;
import oracle.saas.logan.model.persistance.EmLoganMetaSourceType;
import oracle.saas.logan.model.persistance.EmLoganRuleSourceMap;
import oracle.saas.logan.model.persistance.EmLoganSource;
import oracle.saas.logan.model.persistance.EmTargetTypes;
import oracle.saas.logan.util.LoganLibUiUtil;

import org.apache.myfaces.trinidad.event.DisclosureEvent;

public class LoganLibSourcesBean {
    
    protected static final String match_ALL = "ALL";
    protected static final String match_ANY = "ANY";

    protected String matchRadioSelectValue = match_ALL;
    
    protected String selectedtargetType;

    protected List<SelectItem> targetTypesList;
    private List<LoganLibSourcePojo> sources;

    private List<EmLoganRuleSourceMap> rsmaps;
        
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
