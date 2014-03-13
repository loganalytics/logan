package oracle.saas.logan.view.rule;

import java.util.List;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;
import oracle.jbo.Row;
import oracle.jbo.domain.Number;

import oracle.saas.logan.util.LoganLibUiUtil;
import oracle.saas.logan.util.UiUtil;

public class LoganRuleDetailsBean {
    
    private List<SelectItem> detailsValidationErrorMessages;
    private static final String RuleId = "RuleId";
    private static final String RuleIname = "RuleIname";
    private static final String RuleDname = "RuleDname";
    private static final String RuleAuthor = "RuleAuthor";
    private static final String RuleSrctypeIname = "RuleSrctypeIname";
    private static final String RuleTargetType = "RuleTargetType";
    private static final String RuleSeverity = "RuleSeverity";
    private static final String RuleDescription = "RuleDescription";
    private static final String RuleRationale = "RuleRationale";
    
    private static final String RuleIsSystem = "RuleIsSystem";
    
    private Number ruleId;
    private String iname;
    private String dname;
    private String author;
    private String logType;
    private String targetType;
    private Number severity;
    private String description;
    private String rationale;

    private boolean isSystem;
    
    private List<SelectItem> ruleSeverityList;
    private List<SelectItem> ruleLogTypesList;
    private List<SelectItem> ruleTgtTypesList;

    
    public LoganRuleDetailsBean(Number ruleId, String ruleIname, String ruleDname,
                                String author, String logType, String targetType, Number severity,
                                String description, String rationale,
                                boolean isSystem)
    {
        super();
        this.ruleId = ruleId;
        this.iname = ruleIname;
        this.dname = ruleDname;
        this.author = author;
        this.logType = logType;
        this.targetType = targetType;        
        this.severity = severity;
        this.description = description;
        this.rationale = rationale;
        
        this.isSystem = isSystem;
    }

    public LoganRuleDetailsBean(Row detailsContextRow)
    {
        super();
        if (detailsContextRow != null)
        {
            ruleId = (Number) detailsContextRow.getAttribute(RuleId);
            iname = (String) detailsContextRow.getAttribute(RuleIname);
            dname = (String) detailsContextRow.getAttribute(RuleDname);
            author = (String) detailsContextRow.getAttribute(RuleAuthor);
            logType = (String) detailsContextRow.getAttribute(RuleSrctypeIname);
            targetType = (String) detailsContextRow.getAttribute(RuleTargetType);            
            severity = (Number) detailsContextRow.getAttribute(RuleSeverity);            
            description = (String) detailsContextRow.getAttribute(RuleDescription);
            rationale = (String) detailsContextRow.getAttribute(RuleRationale);
                        
            Number isSys = (Number) detailsContextRow.getAttribute(RuleIsSystem);
            if (isSys != null && isSys.intValue() == 1)
            {
                isSystem = true;
            }
        }
    }

    public Object clone(){
        return new LoganRuleDetailsBean(ruleId, iname, dname, author,
                                        logType, targetType, severity,
                                        description, rationale,
                                        isSystem);
    }

    public LoganRuleDetailsBean(){
        super();
    }


    public void setDetailsValidationErrorMessages(List<SelectItem> detailsValidationErrorMessages) {
        this.detailsValidationErrorMessages = detailsValidationErrorMessages;
    }

    public List<SelectItem> getDetailsValidationErrorMessages() {
        return detailsValidationErrorMessages;
    }

    public void setRuleId(Number ruleId) {
        this.ruleId = ruleId;
    }

    public Number getRuleId() {
        return ruleId;
    }

    public void setIname(String iname) {
        this.iname = iname;
    }

    public String getIname() {
        return iname;
    }

    public void setDname(String dname) {
        this.dname = dname;
    }

    public String getDname() {
        return dname;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getAuthor() {
        return author;
    }

    public void setLogType(String logType) {
        this.logType = logType;
    }

    public String getLogType() {
        return logType;
    }

    public void setTargetType(String targetType) {
        this.targetType = targetType;
    }

    public String getTargetType() {
        return targetType;
    }

    public void setSeverity(Number severity) {
        this.severity = severity;
    }

    public String getSeverity() {
        return ""+severity;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setRationale(String rationale) {
        this.rationale = rationale;
    }

    public String getRationale() {
        return rationale;
    }

    public void setIsSystem(boolean isSystem) {
        this.isSystem = isSystem;
    }

    public boolean isIsSystem() {
        return isSystem;
    }
    
    public void severityValueChanged(ValueChangeEvent valueChangeEvent)
    {
        this.setSeverity(new Number(new Long(valueChangeEvent.getNewValue().toString())));
    }
    
    public List<SelectItem> getRuleSeverityList()
    {
        if (ruleSeverityList == null)
        {
            ruleSeverityList = LoganLibUiUtil.getRuleSeverityList(UiUtil.getLoganBundle());
        }
        return ruleSeverityList;
    }
    
    public String getLogTypeDisplayName()
    {
        String ltDis = getLogType();
        return getLogTypeDisplayName(ltDis);
    }
    
    public String getLogTypeDisplayName(String ltDis)
    {
        List<SelectItem> ltnd = getRuleLogTypesList();
        if (ltnd != null)
        {
            for (SelectItem si: ltnd)
            {
                if (si.getValue().equals(ltDis))
                {
                    ltDis = si.getLabel();
                    break;
                }
            }
        }
        return ltDis;
    }
    
    public List<SelectItem> getRuleLogTypesList()
    {
        if (ruleLogTypesList == null)
        {
            ruleLogTypesList = LoganLibUiUtil.getSourceTypesList();
        }
        return ruleLogTypesList;
    }

    
}
