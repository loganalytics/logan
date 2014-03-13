package oracle.saas.logan.view.source;

import java.util.List;

import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;

import oracle.adf.view.rich.component.rich.data.RichTable;
import oracle.adf.view.rich.component.rich.input.RichInputText;

import oracle.saas.logan.util.LoganLibUiUtil;

import oracle.saas.logan.view.AbstractLoganLibBean;

import org.apache.myfaces.trinidad.event.DisclosureEvent;

public class LoganLibSourcesBean extends AbstractLoganLibBean{


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
    
    
    public void handleSearch(ActionEvent ae)
    {
    }
    
    @Override
    public RichTable getTabDataTableHandle() {
        // TODO Implement this method
        return null;
    }

    @Override
    public RichInputText getSearchNameInputText() {
        // TODO Implement this method
        return null;
    }

    @Override
    public RichInputText getSearchDescInputText() {
        // TODO Implement this method
        return null;
    }
}
