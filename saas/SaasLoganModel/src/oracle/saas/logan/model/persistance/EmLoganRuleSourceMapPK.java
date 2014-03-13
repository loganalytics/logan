package oracle.saas.logan.model.persistance;

import java.io.Serializable;

public class EmLoganRuleSourceMapPK implements Serializable {
    public Integer rsRuleId;
    public Integer rsSourceId;

    public EmLoganRuleSourceMapPK() {
    }

    public EmLoganRuleSourceMapPK(Integer rsRuleId, Integer rsSourceId) {
        this.rsRuleId = rsRuleId;
        this.rsSourceId = rsSourceId;
    }

    public boolean equals(Object other) {
        if (other instanceof EmLoganRuleSourceMapPK) {
            final EmLoganRuleSourceMapPK otherEmLoganRuleSourceMapPK = (EmLoganRuleSourceMapPK)other;
            final boolean areEqual = true;
            return areEqual;
        }
        return false;
    }

    public int hashCode() {
        return super.hashCode();
    }

    public Integer getRsRuleId() {
        return rsRuleId;
    }

    public void setRsRuleId(Integer rsRuleId) {
        this.rsRuleId = rsRuleId;
    }

    public Integer getRsSourceId() {
        return rsSourceId;
    }

    public void setRsSourceId(Integer rsSourceId) {
        this.rsSourceId = rsSourceId;
    }
}
