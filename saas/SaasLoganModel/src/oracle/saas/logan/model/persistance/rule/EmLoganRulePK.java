package oracle.saas.logan.model.persistance.rule;

import java.io.Serializable;

public class EmLoganRulePK implements Serializable {
    public String ruleIname;
    public String ruleSrctypeIname;
    public String ruleTargetType;

    public EmLoganRulePK() {
    }

    public EmLoganRulePK(String ruleIname, String ruleSrctypeIname, String ruleTargetType) {
        this.ruleIname = ruleIname;
        this.ruleSrctypeIname = ruleSrctypeIname;
        this.ruleTargetType = ruleTargetType;
    }

    public boolean equals(Object other) {
        if (other instanceof EmLoganRulePK) {
            final EmLoganRulePK otherEmLoganRulePK = (EmLoganRulePK) other;
            final boolean areEqual = true;
            return areEqual;
        }
        return false;
    }

    public int hashCode() {
        return super.hashCode();
    }

    public String getRuleIname() {
        return ruleIname;
    }

    public void setRuleIname(String ruleIname) {
        this.ruleIname = ruleIname;
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
