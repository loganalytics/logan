package oracle.saas.logan.view.source;

import java.util.List;

import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;

import oracle.saas.logan.util.LoganLibUiUtil;

import org.apache.myfaces.trinidad.event.DisclosureEvent;

public class LoganLibSourcesBean {
    
    protected static final String match_ALL = "ALL";
    protected static final String match_ANY = "ANY";

    protected String matchRadioSelectValue = match_ALL;
    
    protected String selectedtargetType;

    protected List<SelectItem> targetTypesList;


    public LoganLibSourcesBean() {
        super();
    }
    
    
    public void logSourcesTabDisclosed(DisclosureEvent disclosureEvent)
    {
        //TODO initilize table -facade
//        LoganAMImpl am = LoganLibUiUtil.getAppModule();
//        EmLoganSourceVOImpl vo = am.getEmLoganSourceVO();
//        vo.executeQuery();
//        if(vo.hasNext())
//            vo.first();
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
    
    
}
