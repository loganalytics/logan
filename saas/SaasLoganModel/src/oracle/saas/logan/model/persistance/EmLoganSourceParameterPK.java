package oracle.saas.logan.model.persistance;

import java.io.Serializable;

public class EmLoganSourceParameterPK implements Serializable {
    public String paramName;
    public Integer paramSourceId;

    public EmLoganSourceParameterPK() {
    }

    public EmLoganSourceParameterPK(String paramName, Integer paramSourceId) {
        this.paramName = paramName;
        this.paramSourceId = paramSourceId;
    }

    public boolean equals(Object other) {
        if (other instanceof EmLoganSourceParameterPK) {
            final EmLoganSourceParameterPK otherEmLoganSourceParameterPK = (EmLoganSourceParameterPK)other;
            final boolean areEqual = true;
            return areEqual;
        }
        return false;
    }

    public int hashCode() {
        return super.hashCode();
    }

    public String getParamName() {
        return paramName;
    }

    public void setParamName(String paramName) {
        this.paramName = paramName;
    }

    public Integer getParamSourceId() {
        return paramSourceId;
    }

    public void setParamSourceId(Integer paramSourceId) {
        this.paramSourceId = paramSourceId;
    }
}
