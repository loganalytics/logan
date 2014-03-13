package oracle.saas.logan.view.rule;

import java.util.Hashtable;
import java.util.List;

import javax.faces.event.ActionEvent;

import javax.naming.CommunicationException;
import javax.naming.Context;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import oracle.adf.view.rich.component.rich.data.RichTable;
import oracle.adf.view.rich.component.rich.input.RichInputText;

import oracle.saas.logan.model.persistance.rule.EmLoganRule;
import oracle.saas.logan.model.session.rule.LoganRuleSession;
import oracle.saas.logan.util.AdfUtil;
import oracle.saas.logan.util.LoganLibUiUtil;
import oracle.saas.logan.util.UiUtil;
import oracle.saas.logan.view.AbstractLoganLibBean;

import org.apache.myfaces.trinidad.event.SelectionEvent;

public class LoganLibRulesBean extends AbstractLoganLibBean{
    private static final String ruleTable_Id = "pc1:t1";
    
    private EmLoganRule loganRule;
    private List<LoganRuleBean> allRules= null;
    
    private LoganRuleBean selectedRule;

    private boolean selectedRuleIsSystem;
    private Integer selectedRuleId;
    private String selectedRuleName;
    private boolean canDelete;
    
    private RichTable tableRule;
    
    public LoganLibRulesBean() {
        super();
//        rb = UiUtil.getLoganBundle();
        allRules = LogRuleDAO.getAllLogRules();
    }
    
    public List<LoganRuleBean> getLoganRuleList(){    
        return allRules;
    }


    public void setSelectedRuleId(Integer selectedRuleId) {
        this.selectedRuleId = selectedRuleId;
    }

    public Integer getSelectedRuleId() {
        return selectedRuleId;
    }

    @Override
    public void handleSearch(ActionEvent ae) {
        // TODO Implement this method
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
    
    public void logRuleRowSelected(SelectionEvent selectionEvent)
    {
        setSelectedRuleData();
    }
    
    private void setSelectedRuleData()
    {
        RichTable parsersTable = (RichTable)AdfUtil.findComponent(ruleTable_Id);
        if(parsersTable != null)
        {
            List rows = LoganLibUiUtil.getRowSelectedFromUiTable(parsersTable);
            if(rows != null && rows.size() > 0)
            {
                selectedRule = (LoganRuleBean)rows.get(0);
                Integer isSys = selectedRule.isIsSystem()?1:0;
                selectedRuleIsSystem = (isSys != null && isSys.intValue() == 1);
                selectedRuleName = selectedRule.getDname();
                selectedRuleId = selectedRule.getRuleId().intValue(); 
                canDelete = true;
            }
        }
    }
}
