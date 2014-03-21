package oracle.saas.logan.model.persistance.rule;

import java.io.Serializable;

import java.util.Date;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import oracle.saas.logan.model.persistance.EmLoganRuleSourceMap;
import oracle.saas.logan.model.persistance.EmLoganSource;

@Entity
@NamedQueries({ 
        @NamedQuery(name = "EmLoganRule.findAll", query = "select o from EmLoganRule o"),
        @NamedQuery(name = "EmLoganRule.findByRuleId",  query = "select o from EmLoganRule o where o.ruleId = :ruleId"),
        @NamedQuery(name = "EmLoganRule.getMaxId", query="select max(o.ruleId) from EmLoganRule o")})
@Table(name = "EM_LOGAN_RULE")
@IdClass(EmLoganRulePK.class)
public class EmLoganRule implements Serializable {
    private static final long serialVersionUID = 828839271505060484L;
    @Column(name = "RULE_ACTION_CENTRALIZED", nullable = false)
    private Integer ruleActionCentralized;
    @Column(name = "RULE_ACTION_EVENT", nullable = false)
    private Integer ruleActionEvent;
    @Column(name = "RULE_ACTION_EVENT_BUNDLE", nullable = false)
    private Integer ruleActionEventBundle;
    @Column(name = "RULE_ACTION_EVENT_BUNDLETIME", nullable = false)
    private Integer ruleActionEventBundletime;
    @Column(name = "RULE_ACTION_OBSERVATION", nullable = false)
    private Integer ruleActionObservation;
    @Column(name = "RULE_ACTION_RULEMETRIC", nullable = false)
    private Integer ruleActionRulemetric;
    @Column(name = "RULE_AUTHOR", length = 128)
    private String ruleAuthor;
    @Column(name = "RULE_CRITICAL_EDIT_VERSION")
    private Integer ruleCriticalEditVersion;
    @Column(name = "RULE_DESCRIPTION", length = 4000)
    private String ruleDescription;
    @Column(name = "RULE_DESCRIPTION_NLSID", length = 64)
    private String ruleDescriptionNlsid;
    @Column(name = "RULE_DNAME", nullable = false, length = 128)
    private String ruleDname;
    @Column(name = "RULE_DNAME_NLSID", length = 64)
    private String ruleDnameNlsid;
    @Column(name = "RULE_EDIT_VERSION")
    private Integer ruleEditVersion;
    @Column(name = "RULE_ID", nullable = false, unique = true)
    private Integer ruleId;
    @Id
    @Column(name = "RULE_INAME", nullable = false, length = 128)
    private String ruleIname;
    @Column(name = "RULE_IS_SYSTEM", nullable = false)
    private Integer ruleIsSystem;
    @Column(name = "RULE_LAST_UPDATED_BY", length = 128)
    private String ruleLastUpdatedBy;
    @Temporal(TemporalType.DATE)
    @Column(name = "RULE_LAST_UPDATED_DATE")
    private Date ruleLastUpdatedDate;
    @Column(name = "RULE_OWNER", length = 128)
    private String ruleOwner;
    @Column(name = "RULE_RATIONALE", length = 4000)
    private String ruleRationale;
    @Column(name = "RULE_RATIONALE_NLSID", length = 64)
    private String ruleRationaleNlsid;
    @Column(name = "RULE_SEVERITY", nullable = false)
    private Integer ruleSeverity;
    @Id
    @Column(name = "RULE_SRCTYPE_INAME", nullable = false, length = 128)
    private String ruleSrctypeIname;
    @Id
    @Column(name = "RULE_TARGET_TYPE", nullable = false, length = 64)
    private String ruleTargetType;
    
    public EmLoganRule() {
    }

    public EmLoganRule(Integer ruleActionCentralized, Integer ruleActionEvent, Integer ruleActionEventBundle,
                       Integer ruleActionEventBundletime, Integer ruleActionObservation, Integer ruleActionRulemetric,
                       String ruleAuthor, Integer ruleCriticalEditVersion, String ruleDescription,
                       String ruleDescriptionNlsid, String ruleDname, String ruleDnameNlsid, Integer ruleEditVersion,
                       Integer ruleId, String ruleIname, Integer ruleIsSystem, String ruleLastUpdatedBy,
                       Date ruleLastUpdatedDate, String ruleOwner, String ruleRationale, String ruleRationaleNlsid,
                       Integer ruleSeverity, String ruleSrctypeIname, String ruleTargetType) {
        this.ruleActionCentralized = ruleActionCentralized;
        this.ruleActionEvent = ruleActionEvent;
        this.ruleActionEventBundle = ruleActionEventBundle;
        this.ruleActionEventBundletime = ruleActionEventBundletime;
        this.ruleActionObservation = ruleActionObservation;
        this.ruleActionRulemetric = ruleActionRulemetric;
        this.ruleAuthor = ruleAuthor;
        this.ruleCriticalEditVersion = ruleCriticalEditVersion;
        this.ruleDescription = ruleDescription;
        this.ruleDescriptionNlsid = ruleDescriptionNlsid;
        this.ruleDname = ruleDname;
        this.ruleDnameNlsid = ruleDnameNlsid;
        this.ruleEditVersion = ruleEditVersion;
        this.ruleId = ruleId;
        this.ruleIname = ruleIname;
        this.ruleIsSystem = ruleIsSystem;
        this.ruleLastUpdatedBy = ruleLastUpdatedBy;
        this.ruleLastUpdatedDate = ruleLastUpdatedDate;
        this.ruleOwner = ruleOwner;
        this.ruleRationale = ruleRationale;
        this.ruleRationaleNlsid = ruleRationaleNlsid;
        this.ruleSeverity = ruleSeverity;
        this.ruleSrctypeIname = ruleSrctypeIname;
        this.ruleTargetType = ruleTargetType;
    }

    public Integer getRuleActionCentralized() {
        return ruleActionCentralized;
    }

    public void setRuleActionCentralized(Integer ruleActionCentralized) {
        this.ruleActionCentralized = ruleActionCentralized;
    }

    public Integer getRuleActionEvent() {
        return ruleActionEvent;
    }

    public void setRuleActionEvent(Integer ruleActionEvent) {
        this.ruleActionEvent = ruleActionEvent;
    }

    public Integer getRuleActionEventBundle() {
        return ruleActionEventBundle;
    }

    public void setRuleActionEventBundle(Integer ruleActionEventBundle) {
        this.ruleActionEventBundle = ruleActionEventBundle;
    }

    public Integer getRuleActionEventBundletime() {
        return ruleActionEventBundletime;
    }

    public void setRuleActionEventBundletime(Integer ruleActionEventBundletime) {
        this.ruleActionEventBundletime = ruleActionEventBundletime;
    }

    public Integer getRuleActionObservation() {
        return ruleActionObservation;
    }

    public void setRuleActionObservation(Integer ruleActionObservation) {
        this.ruleActionObservation = ruleActionObservation;
    }

    public Integer getRuleActionRulemetric() {
        return ruleActionRulemetric;
    }

    public void setRuleActionRulemetric(Integer ruleActionRulemetric) {
        this.ruleActionRulemetric = ruleActionRulemetric;
    }

    public String getRuleAuthor() {
        return ruleAuthor;
    }

    public void setRuleAuthor(String ruleAuthor) {
        this.ruleAuthor = ruleAuthor;
    }

    public Integer getRuleCriticalEditVersion() {
        return ruleCriticalEditVersion;
    }

    public void setRuleCriticalEditVersion(Integer ruleCriticalEditVersion) {
        this.ruleCriticalEditVersion = ruleCriticalEditVersion;
    }

    public String getRuleDescription() {
        return ruleDescription;
    }

    public void setRuleDescription(String ruleDescription) {
        this.ruleDescription = ruleDescription;
    }

    public String getRuleDescriptionNlsid() {
        return ruleDescriptionNlsid;
    }

    public void setRuleDescriptionNlsid(String ruleDescriptionNlsid) {
        this.ruleDescriptionNlsid = ruleDescriptionNlsid;
    }

    public String getRuleDname() {
        return ruleDname;
    }

    public void setRuleDname(String ruleDname) {
        this.ruleDname = ruleDname;
    }

    public String getRuleDnameNlsid() {
        return ruleDnameNlsid;
    }

    public void setRuleDnameNlsid(String ruleDnameNlsid) {
        this.ruleDnameNlsid = ruleDnameNlsid;
    }

    public Integer getRuleEditVersion() {
        return ruleEditVersion;
    }

    public void setRuleEditVersion(Integer ruleEditVersion) {
        this.ruleEditVersion = ruleEditVersion;
    }

    public Integer getRuleId() {
        return ruleId;
    }

    public void setRuleId(Integer ruleId) {
        this.ruleId = ruleId;
    }

    public String getRuleIname() {
        return ruleIname;
    }

    public void setRuleIname(String ruleIname) {
        this.ruleIname = ruleIname;
    }

    public Integer getRuleIsSystem() {
        return ruleIsSystem;
    }

    public void setRuleIsSystem(Integer ruleIsSystem) {
        this.ruleIsSystem = ruleIsSystem;
    }

    public String getRuleLastUpdatedBy() {
        return ruleLastUpdatedBy;
    }

    public void setRuleLastUpdatedBy(String ruleLastUpdatedBy) {
        this.ruleLastUpdatedBy = ruleLastUpdatedBy;
    }

    public Date getRuleLastUpdatedDate() {
        return ruleLastUpdatedDate;
    }

    public void setRuleLastUpdatedDate(Date ruleLastUpdatedDate) {
        this.ruleLastUpdatedDate = ruleLastUpdatedDate;
    }

    public String getRuleOwner() {
        return ruleOwner;
    }

    public void setRuleOwner(String ruleOwner) {
        this.ruleOwner = ruleOwner;
    }

    public String getRuleRationale() {
        return ruleRationale;
    }

    public void setRuleRationale(String ruleRationale) {
        this.ruleRationale = ruleRationale;
    }

    public String getRuleRationaleNlsid() {
        return ruleRationaleNlsid;
    }

    public void setRuleRationaleNlsid(String ruleRationaleNlsid) {
        this.ruleRationaleNlsid = ruleRationaleNlsid;
    }

    public Integer getRuleSeverity() {
        return ruleSeverity;
    }

    public void setRuleSeverity(Integer ruleSeverity) {
        this.ruleSeverity = ruleSeverity;
    }

    public String getRuleSrctypeIname() {
        return ruleSrctypeIname;
    }

    public void setRuleSrctypeIname(String ruleSrctypeIname) {
        this.ruleSrctypeIname = ruleSrctypeIname;
    }

    public String getRuleTargetType() {
        return ruleTargetType;
    }

    public void setRuleTargetType(String ruleTargetType) {
        this.ruleTargetType = ruleTargetType;
    }
}
