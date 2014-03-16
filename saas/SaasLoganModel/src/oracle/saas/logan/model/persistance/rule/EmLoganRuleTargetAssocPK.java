package oracle.saas.logan.model.persistance.rule;

import java.io.Serializable;

public class EmLoganRuleTargetAssocPK implements Serializable {
    public Integer assocRuleId;
    public byte[] assocTargetGuid;

    public EmLoganRuleTargetAssocPK() {
    }

    public EmLoganRuleTargetAssocPK(Integer assocRuleId, byte[] assocTargetGuid) {
        this.assocRuleId = assocRuleId;
        this.assocTargetGuid = assocTargetGuid;
    }

    public boolean equals(Object other) {
        if (other instanceof EmLoganRuleTargetAssocPK) {
            final EmLoganRuleTargetAssocPK otherEmLoganRuleTargetAssocPK = (EmLoganRuleTargetAssocPK) other;
            final boolean areEqual = true;
            return areEqual;
        }
        return false;
    }

    public int hashCode() {
        return super.hashCode();
    }

    public Integer getAssocRuleId() {
        return assocRuleId;
    }

    public void setAssocRuleId(Integer assocRuleId) {
        this.assocRuleId = assocRuleId;
    }

    public byte[] getAssocTargetGuid() {
        return assocTargetGuid;
    }

    public void setAssocTargetGuid(byte[] assocTargetGuid) {
        this.assocTargetGuid = assocTargetGuid;
    }
}
