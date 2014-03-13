package oracle.saas.logan.model.persistance;

import java.io.Serializable;

public class EmLoganSourcePK implements Serializable {
    public String sourceIname;
    public String sourceSrctypeIname;
    public String sourceTargetType;

    public EmLoganSourcePK() {
    }

    public EmLoganSourcePK(String sourceIname, String sourceSrctypeIname, String sourceTargetType) {
        this.sourceIname = sourceIname;
        this.sourceSrctypeIname = sourceSrctypeIname;
        this.sourceTargetType = sourceTargetType;
    }

    public boolean equals(Object other) {
        if (other instanceof EmLoganSourcePK) {
            final EmLoganSourcePK otherEmLoganSourcePK = (EmLoganSourcePK)other;
            final boolean areEqual = true;
            return areEqual;
        }
        return false;
    }

    public int hashCode() {
        return super.hashCode();
    }

    public String getSourceIname() {
        return sourceIname;
    }

    public void setSourceIname(String sourceIname) {
        this.sourceIname = sourceIname;
    }

    public String getSourceSrctypeIname() {
        return sourceSrctypeIname;
    }

    public void setSourceSrctypeIname(String sourceSrctypeIname) {
        this.sourceSrctypeIname = sourceSrctypeIname;
    }

    public String getSourceTargetType() {
        return sourceTargetType;
    }

    public void setSourceTargetType(String sourceTargetType) {
        this.sourceTargetType = sourceTargetType;
    }
}
