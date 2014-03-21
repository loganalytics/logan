package oracle.saas.logan.model.persistance.rule;

import java.io.Serializable;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@NamedQueries({
              @NamedQuery(name = "EmLoganRuleTargetAssoc.findAll", query = "select o from EmLoganRuleTargetAssoc o") })
@Table(name = "EM_LOGAN_RULE_TARGET_ASSOC")
@IdClass(EmLoganRuleTargetAssocPK.class)
public class EmLoganRuleTargetAssoc implements Serializable {
    private static final long serialVersionUID = 6926096231781925344L;
    @Column(name = "ASSOC_CRITICAL_EDIT_VERSION")
    private Integer assocCriticalEditVersion;
    @Column(name = "ASSOC_EDIT_VERSION")
    private Integer assocEditVersion;
    @Column(name = "ASSOC_IS_CUSTOMIZED", nullable = false)
    private Integer assocIsCustomized;
    @Column(name = "ASSOC_IS_ENABLED", nullable = false)
    private Integer assocIsEnabled;
    @Temporal(TemporalType.DATE)
    @Column(name = "ASSOC_LAST_ATTEMPT_DATE")
    private Date assocLastAttemptDate;
    @Id
    @Column(name = "ASSOC_RULE_ID", nullable = false)
    private Integer assocRuleId;
    @Column(name = "ASSOC_STATUS", nullable = false)
    private Integer assocStatus;
    @Id
    @Column(name = "ASSOC_TARGET_GUID", nullable = false)
    private byte[] assocTargetGuid;

    public EmLoganRuleTargetAssoc() {
    }

    public EmLoganRuleTargetAssoc(Integer assocCriticalEditVersion, Integer assocEditVersion, Integer assocIsCustomized,
                                  Integer assocIsEnabled, Date assocLastAttemptDate, Integer assocRuleId,
                                  Integer assocStatus) {
        this.assocCriticalEditVersion = assocCriticalEditVersion;
        this.assocEditVersion = assocEditVersion;
        this.assocIsCustomized = assocIsCustomized;
        this.assocIsEnabled = assocIsEnabled;
        this.assocLastAttemptDate = assocLastAttemptDate;
        this.assocRuleId = assocRuleId;
        this.assocStatus = assocStatus;
    }

    public Integer getAssocCriticalEditVersion() {
        return assocCriticalEditVersion;
    }

    public void setAssocCriticalEditVersion(Integer assocCriticalEditVersion) {
        this.assocCriticalEditVersion = assocCriticalEditVersion;
    }

    public Integer getAssocEditVersion() {
        return assocEditVersion;
    }

    public void setAssocEditVersion(Integer assocEditVersion) {
        this.assocEditVersion = assocEditVersion;
    }

    public Integer getAssocIsCustomized() {
        return assocIsCustomized;
    }

    public void setAssocIsCustomized(Integer assocIsCustomized) {
        this.assocIsCustomized = assocIsCustomized;
    }

    public Integer getAssocIsEnabled() {
        return assocIsEnabled;
    }

    public void setAssocIsEnabled(Integer assocIsEnabled) {
        this.assocIsEnabled = assocIsEnabled;
    }

    public Date getAssocLastAttemptDate() {
        return assocLastAttemptDate;
    }

    public void setAssocLastAttemptDate(Date assocLastAttemptDate) {
        this.assocLastAttemptDate = assocLastAttemptDate;
    }

    public Integer getAssocRuleId() {
        return assocRuleId;
    }

    public void setAssocRuleId(Integer assocRuleId) {
        this.assocRuleId = assocRuleId;
    }

    public Integer getAssocStatus() {
        return assocStatus;
    }

    public void setAssocStatus(Integer assocStatus) {
        this.assocStatus = assocStatus;
    }

    public byte[] getAssocTargetGuid() {
        return assocTargetGuid;
    }

    public void setAssocTargetGuid(byte[] assocTargetGuid) {
        this.assocTargetGuid = assocTargetGuid;
    }
}
