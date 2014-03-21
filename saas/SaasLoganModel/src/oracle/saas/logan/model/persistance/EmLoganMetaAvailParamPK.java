package oracle.saas.logan.model.persistance;

import java.io.Serializable;

public class EmLoganMetaAvailParamPK implements Serializable {
    public String apName;
    public String apPluginId;
    public String apPluginVersion;
    public String apTargetType;

    public EmLoganMetaAvailParamPK() {
    }

    public EmLoganMetaAvailParamPK(String apName, String apPluginId, String apPluginVersion, String apTargetType) {
        this.apName = apName;
        this.apPluginId = apPluginId;
        this.apPluginVersion = apPluginVersion;
        this.apTargetType = apTargetType;
    }

    public boolean equals(Object other) {
        if (other instanceof EmLoganMetaAvailParamPK) {
            final EmLoganMetaAvailParamPK otherEmLoganMetaAvailParamPK = (EmLoganMetaAvailParamPK)other;
            final boolean areEqual = true;
            return areEqual;
        }
        return false;
    }

    public int hashCode() {
        return super.hashCode();
    }

    public String getApName() {
        return apName;
    }

    public void setApName(String apName) {
        this.apName = apName;
    }

    public String getApPluginId() {
        return apPluginId;
    }

    public void setApPluginId(String apPluginId) {
        this.apPluginId = apPluginId;
    }

    public String getApPluginVersion() {
        return apPluginVersion;
    }

    public void setApPluginVersion(String apPluginVersion) {
        this.apPluginVersion = apPluginVersion;
    }

    public String getApTargetType() {
        return apTargetType;
    }

    public void setApTargetType(String apTargetType) {
        this.apTargetType = apTargetType;
    }
}
