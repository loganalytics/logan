package oracle.saas.logan.view.rule;

import oracle.saas.logan.model.persistance.rule.EmLoganRule;

public class LoganRuleBean {
    public LoganRuleBean() {
        super();
    }
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
    
    public LoganRuleBean(Number ruleId, String ruleIname, String ruleDname,
                         String author, String logType, String targetType, Number severity,
                         boolean isSystem){
                             
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
    
    public LoganRuleBean(EmLoganRule lp)
    {
        initialize(lp);
    }
    
    private void initialize(EmLoganRule pRow){
        if(pRow != null){
            this.ruleId = pRow.getRuleId();
            this.iname = pRow.getRuleIname();
            this.dname = pRow.getRuleDname();
            this.author = pRow.getRuleAuthor();
            this.logType = pRow.getRuleSrctypeIname();
            this.targetType = pRow.getRuleTargetType();
            this.severity = pRow.getRuleSeverity();
            this.description = pRow.getRuleDescription();
            this.rationale = pRow.getRuleRationale();
        }
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

    public Number getSeverity() {
        return severity;
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
}
