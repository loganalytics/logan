package oracle.saas.logan.model.persistance;

import java.io.Serializable;

public class EmLoganSourcePatternPK implements Serializable {
    public Integer patternIsInclude;
    public Integer patternSourceId;
    public String patternText;

    public EmLoganSourcePatternPK() {
    }

    public EmLoganSourcePatternPK(Integer patternIsInclude, Integer patternSourceId, String patternText) {
        this.patternIsInclude = patternIsInclude;
        this.patternSourceId = patternSourceId;
        this.patternText = patternText;
    }

    public boolean equals(Object other) {
        if (other instanceof EmLoganSourcePatternPK) {
            final EmLoganSourcePatternPK otherEmLoganSourcePatternPK = (EmLoganSourcePatternPK) other;
            final boolean areEqual = true;
            return areEqual;
        }
        return false;
    }

    public int hashCode() {
        return super.hashCode();
    }

    public Integer getPatternIsInclude() {
        return patternIsInclude;
    }

    public void setPatternIsInclude(Integer patternIsInclude) {
        this.patternIsInclude = patternIsInclude;
    }

    public Integer getPatternSourceId() {
        return patternSourceId;
    }

    public void setPatternSourceId(Integer patternSourceId) {
        this.patternSourceId = patternSourceId;
    }

    public String getPatternText() {
        return patternText;
    }

    public void setPatternText(String patternText) {
        this.patternText = patternText;
    }
}
