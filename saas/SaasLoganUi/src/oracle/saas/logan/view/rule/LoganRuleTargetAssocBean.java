package oracle.saas.logan.view.rule;


import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import java.util.Map;

import javax.faces.event.ActionEvent;

import oracle.adf.view.rich.component.rich.data.RichTable;
import oracle.adf.view.rich.event.DialogEvent;
import oracle.adf.view.rich.event.PopupFetchEvent;
import oracle.jbo.domain.Number;
import oracle.saas.logan.util.AdfUtil;
import oracle.saas.logan.util.LoganLibUiUtil;
import oracle.saas.logan.util.UiUtil;
import oracle.saas.logan.view.KeyValuePairObj;

import org.apache.myfaces.trinidad.component.UIXCommand;
import org.apache.myfaces.trinidad.event.ReturnEvent;
import org.apache.myfaces.trinidad.event.SelectionEvent;

public class LoganRuleTargetAssocBean {
    
    private Number ruleId;
    
    private String ruleName;
    
    private List<LoganRuleTargetAssoc> targetsRef = new ArrayList<LoganRuleTargetAssoc>();
    private List<LoganRuleTargetAssoc> targetsCur = new ArrayList<LoganRuleTargetAssoc>(); // current target list
    
    private boolean noSelection = true;
    private String selectedTxndetais;
    private Map<String, Boolean> mapTargetStatus = new HashMap<String, Boolean>();
    
    private static final String uiRemoveButtonId = "emT:pc:rmvTlbBtn";
    private static final String uiSetParamOverrBtnId = "emT:pc:ctb1";
    private static final String uiEnableButtonId =
        "emT:pc:bulkEnableTblBtn";
    private static final String uiDisableButtonId =
        "emT:pc:bulkDisableTblBtn";
    private static final String uiStartButtonId = "emT:pc:startcoll";
    private static final String uiStopButtonId = "emT:pc:stopcoll";
    private static final String uiTargetsTableId = "emT:pc:hosttbl";
    
    private List<LoganRuleParamOverride> allParamsForRuleSources;
    private List<LoganRuleParamOverride> paramsOverrideForTarget;
    
    private boolean updateTargetStatus = false;
    private boolean updateMECollection = false;

    
    public LoganRuleTargetAssocBean() {
        super();
    }
    
    public void setRuleId(Number ruleId)
    {
        this.ruleId = ruleId;
    }

    public Number getRuleId()
    {
        return ruleId;
    }
    
    public void forceRefresh(){
        this.targetsRef =
                LoganLibUiUtil.loadLogRuleTargets(this.getRuleId());
        LoganLibUiUtil.addPPR("hosttbl");
        if (targetsRef != null && targetsRef.size() > 0)
        {
            for (LoganRuleTargetAssoc assoc: targetsRef)
            {
                targetsCur.add((LoganRuleTargetAssoc) assoc.clone());
            }
        }
    }
    
    public String getTargetAssocHeader(){
        String msg =
            UiUtil.getString(UiUtil.S_UI_MSG_CLASS, "TARGET_ASSOC_HDR",
                             new Object[]
                { ruleName });

        return msg;
    }
    
    public List<LoganRuleParamOverride> getParamsOverrideForTarget(){
        return paramsOverrideForTarget;
    }
    
    public String finalConfirmOK(){
        List<KeyValuePairObj> rks = new ArrayList<KeyValuePairObj>(1);
        rks.add(new KeyValuePairObj(LoganRuleTargetAssoc.ParentFK_RuleId, this.ruleId));

        return "return-to-logan-lib";
    }
    
    public boolean isRemoveDisabled(){
        return this.isNoSelection();
    }
    
    public boolean isNoSelection(){
        List selected =
            LoganLibUiUtil.getRowSelectedFromUiTable(uiTargetsTableId);
        if (selected != null && selected.size() > 0)
            this.noSelection = false;
        else
            this.noSelection = true;
        return noSelection;
    }
    
    /**
     * Return from target selector.
     * @param returnEvent
     */
    public void returnFromTargetSelector(ReturnEvent returnEvent){
//        try{
//            TargetSelectorUIBean bean =
//                (TargetSelectorUIBean) returnEvent.getReturnValue();
//            if (!(bean == null || bean.getSelectedTargets() == null))
//            {
//                List<TargetSelectorTarget> targets =
//                    bean.getSelectedTargets();
//                Collections.reverse(targets); // adf will display each new element at top of table, so reverse the list
//                addTarget(targets);
//                refreshTable();
//            }
//        }
//        catch (Exception e)
//        {
//            s_log.warning("Exeption: ", e);
//        }
    }
    
    /**
     * This method is called on Add Button for selecting host target to be added
     * @method prepareTargetSelector
     * @param event
     */
    public void prepareTargetSelector(ActionEvent event){
//        try{
//            TargetSelectorUIBean targetSelectorUIBean =
//                new TargetSelectorUIBean();
//            TargetSelectorSearchBean targetSelectorSearchBean =
//                targetSelectorUIBean.getSearchBean();
//            List<String> targetTypesToChooseFrom =
//                new ArrayList<String>(1);
//            targetTypesToChooseFrom.add(targetType);
//            targetSelectorSearchBean.setTargetTypesToChooseFrom(targetTypesToChooseFrom);
//
//            TargetSelectorUtils.setTargetSelectorParameters(targetSelectorUIBean);
//
//            UIXCommand command = (UIXCommand) event.getSource();
//            command.setAction(new EMActionOutcome("dialog:launchTargetSelector"));
//        }
//        catch (Exception e)
//        {
//            s_log.warning("Exeption: ", e);
//        }
    }
    public void setTargetParameterOverrLaunched(PopupFetchEvent popupFetchEvent){
    }
    
    public String getSelectedTxndetais(){
        return selectedTxndetais;
    }
    
    public void removeTarget(DialogEvent dialogEvent){
    }
    
    public boolean isBulkApplyOptionsEnabled(){
        if (this.isNoSelection()) {
            return true;
        }
        else if (this.isEnable()) {
            return true;
        }
        else
            return false;
    }
    
    private boolean isEnable(){
        List selected =
            LoganLibUiUtil.getRowSelectedFromUiTable(uiTargetsTableId);
        boolean isEnabled = false;
        if (selected != null && selected.size() > 0)
        {
            for (Object o: selected)
            {
                LoganRuleTargetAssoc row = (LoganRuleTargetAssoc) o;
                isEnabled = row.isIsEnabled();
            }
        }
        return isEnabled;
    }
    
    public void handleEnable(ActionEvent dialogEvent){
        setEnable(true);
    }
    
    private void setEnable(boolean enabled){
        List selected =
            LoganLibUiUtil.getRowSelectedFromUiTable(uiTargetsTableId);
        if (selected != null && selected.size() > 0)
        {
            for (Object o: selected)
            {
                LoganRuleTargetAssoc row = (LoganRuleTargetAssoc) o;
                row.setIsEnabled(enabled);
                updateTargetStatus = true;
                mapTargetStatus.put(row.getTargetGuid(), enabled);
    //                LoganLibUiUtil.updateAssoTargetStatus(row.getRuleId(), row.getTargetGuid(), enabled);
            }
            refreshTable();
            refreshButtons();
        }
    }
    
    private void refreshTable(){
        LoganLibUiUtil.addPPR("hosttbl");
    }
    
    private void refreshButtons()    {
//        AdfUtil.addPartialTarget(uiRemoveButtonId);
//        AdfUtil.addPartialTarget(uiSetParamOverrBtnId);
//        AdfUtil.addPartialTarget(uiEnableButtonId);
//        AdfUtil.addPartialTarget(uiDisableButtonId);
//        AdfUtil.addPartialTarget(uiStartButtonId);
//        AdfUtil.addPartialTarget(uiStopButtonId);
    }
    
    public boolean isBulkApplyOptionsDisabled(){
        if (this.isNoSelection()) {
            return true;
        }
        else if (this.isEnable()) {
            return false;
        }
        else
            return true;
    }
    
    public void handleDisable(ActionEvent dialogEvent){
        setEnable(false);
    }
    
    public boolean isCollStarted(){
//        List<LoganRuleTargetAssoc> selected =
//            (List<LoganRuleTargetAssoc>) LoganLibUiUtil.getRowSelectedFromUiTable(uiTargetsTableId);
//        boolean createme = LoganLibUiUtil.hasRuleMetric(ruleId);
//        if ((selected == null || selected.size() != 1) ||
//            (selected.get(0) == null ||
//             selected.get(0).getMestatus() == null) || !createme) // new not set
//            return true; // disable
//        else
//            return (selected.get(0).getMestatusVal() == 0 ||
//                    selected.get(0).getMestatusVal() ==
//                    1); // pending or successfully started
        
        return false;
    }
    
    public void handleStartColl(ActionEvent actionEvent){
        List<LoganRuleTargetAssoc> selected =
            (List<LoganRuleTargetAssoc>) LoganLibUiUtil.getRowSelectedFromUiTable(uiTargetsTableId);
        if (selected == null || selected.size() != 1)
            return;

        LoganRuleTargetAssoc selRow = selected.get(0);

        Number newstatus = new Number(0);
        updateCollection(selRow, newstatus);
    }
    
    private void updateCollection(LoganRuleTargetAssoc selRow,
                                  Number newstatus){
        selRow.setMestatus(newstatus); // pending undeploy
        selRow.updateMEStatus(selRow.getRuleId(), selRow.getTargetGuid(),
                              newstatus);
        updateMECollection = true;
        this.refreshTable();
        this.refreshButtons();
    }
    
    public boolean isCollStopped(){
//        List<LoganRuleTargetAssoc> selected =
//            (List<LoganRuleTargetAssoc>) LoganLibUiUtil.getRowSelectedFromUiTable(uiTargetsTableId);
//        boolean createme = LoganLibUiUtil.hasRuleMetric(ruleId);
//        if ((selected == null || selected.size() != 1) ||
//            (selected.get(0) == null ||
//             selected.get(0).getMestatus() == null) || !createme) // new not set
//            return true; // disable
//        else
//            return (selected.get(0).getMestatusVal() == 3 ||
//                    selected.get(0).getMestatusVal() ==
//                    4); // pending or successfully stopped
        return false;
    }
    
    public void handleStopColl(ActionEvent actionEvent){
        List<LoganRuleTargetAssoc> selected =
            (List<LoganRuleTargetAssoc>) LoganLibUiUtil.getRowSelectedFromUiTable(uiTargetsTableId);
        if (selected == null || selected.size() != 1)
            return;

        LoganRuleTargetAssoc selRow = selected.get(0);

        Number newstatus = new Number(3);
        updateCollection(selRow, newstatus);
    }
    
    public String getTotalRows(){
        Object obj = AdfUtil.findComponent(uiTargetsTableId);
        RichTable table = (RichTable) obj;
        return table.getRowCount() + "";
    }

    public List<LoganRuleTargetAssoc> getTargetsCur(){
        return targetsCur;
    }
    
    public void targetSelected(SelectionEvent selectionEvent){
    }
}
