package oracle.saas.logan.model.persistance;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@NamedQueries({ @NamedQuery(name = "EmTargetTypes.findAll", query = "select o from EmTargetTypes o") })
@Table(name = "MGMT_TARGET_TYPES")
public class EmTargetTypes implements Serializable {
    private static final long serialVersionUID = -3727641780334007874L;
    @Column(name = "COMPONENT_PARENT_TYPE", length = 64)
    private String componentParentType;
    @Column(name = "MAX_PLUGIN_VERSION", length = 32)
    private String maxPluginVersion;
    @Column(name = "MAX_TYPE_META_VER", length = 8)
    private String maxTypeMetaVer;
    @Column(name = "ME_CLASS", length = 10)
    private String meClass;
    @Column(name = "MIN_PLUGIN_VERSION", length = 32)
    private String minPluginVersion;
    @Column(name = "MONITORED_BY")
    private Integer monitoredBy;
    @Column(name = "OCM_GC_MERGED")
    private Integer ocmGcMerged;
    @Column(name = "PARENT_TARGET_TYPE", length = 64)
    private String parentTargetType;
    @Column(name = "PLUGIN_ID", length = 128)
    private String pluginId;
    @Column(name = "PRODUCT_ID", length = 64)
    private String productId;
    @Column(name = "TARGET_TYPE", nullable = false, length = 64)
    private String targetType;
    @Column(name = "TARGET_TYPE_GUID", nullable = false)
    private byte[] targetTypeGuid;
    @Column(name = "TYPE_DISPLAY_NAME", length = 128)
    private String typeDisplayName;
    @Column(name = "TYPE_DISPLAY_NLSID", length = 64)
    private String typeDisplayNlsid;
    @Column(name = "TYPE_RELATIONSHIP")
    private Integer typeRelationship;
    @Column(name = "TYPE_RESOURCE_BUNDLE", length = 256)
    private String typeResourceBundle;

    public EmTargetTypes() {
    }

    public EmTargetTypes(String componentParentType, String maxPluginVersion, String maxTypeMetaVer, String meClass,
                         String minPluginVersion, Integer monitoredBy, Integer ocmGcMerged, String parentTargetType,
                         String pluginId, String productId, String targetType, String typeDisplayName,
                         String typeDisplayNlsid, Integer typeRelationship, String typeResourceBundle) {
        this.componentParentType = componentParentType;
        this.maxPluginVersion = maxPluginVersion;
        this.maxTypeMetaVer = maxTypeMetaVer;
        this.meClass = meClass;
        this.minPluginVersion = minPluginVersion;
        this.monitoredBy = monitoredBy;
        this.ocmGcMerged = ocmGcMerged;
        this.parentTargetType = parentTargetType;
        this.pluginId = pluginId;
        this.productId = productId;
        this.targetType = targetType;
        this.typeDisplayName = typeDisplayName;
        this.typeDisplayNlsid = typeDisplayNlsid;
        this.typeRelationship = typeRelationship;
        this.typeResourceBundle = typeResourceBundle;
    }

    public String getComponentParentType() {
        return componentParentType;
    }

    public void setComponentParentType(String componentParentType) {
        this.componentParentType = componentParentType;
    }

    public String getMaxPluginVersion() {
        return maxPluginVersion;
    }

    public void setMaxPluginVersion(String maxPluginVersion) {
        this.maxPluginVersion = maxPluginVersion;
    }

    public String getMaxTypeMetaVer() {
        return maxTypeMetaVer;
    }

    public void setMaxTypeMetaVer(String maxTypeMetaVer) {
        this.maxTypeMetaVer = maxTypeMetaVer;
    }

    public String getMeClass() {
        return meClass;
    }

    public void setMeClass(String meClass) {
        this.meClass = meClass;
    }

    public String getMinPluginVersion() {
        return minPluginVersion;
    }

    public void setMinPluginVersion(String minPluginVersion) {
        this.minPluginVersion = minPluginVersion;
    }

    public Integer getMonitoredBy() {
        return monitoredBy;
    }

    public void setMonitoredBy(Integer monitoredBy) {
        this.monitoredBy = monitoredBy;
    }

    public Integer getOcmGcMerged() {
        return ocmGcMerged;
    }

    public void setOcmGcMerged(Integer ocmGcMerged) {
        this.ocmGcMerged = ocmGcMerged;
    }

    public String getParentTargetType() {
        return parentTargetType;
    }

    public void setParentTargetType(String parentTargetType) {
        this.parentTargetType = parentTargetType;
    }

    public String getPluginId() {
        return pluginId;
    }

    public void setPluginId(String pluginId) {
        this.pluginId = pluginId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getTargetType() {
        return targetType;
    }

    public void setTargetType(String targetType) {
        this.targetType = targetType;
    }

    public byte[] getTargetTypeGuid() {
        return targetTypeGuid;
    }

    public void setTargetTypeGuid(byte[] targetTypeGuid) {
        this.targetTypeGuid = targetTypeGuid;
    }

    public String getTypeDisplayName() {
        return typeDisplayName;
    }

    public void setTypeDisplayName(String typeDisplayName) {
        this.typeDisplayName = typeDisplayName;
    }

    public String getTypeDisplayNlsid() {
        return typeDisplayNlsid;
    }

    public void setTypeDisplayNlsid(String typeDisplayNlsid) {
        this.typeDisplayNlsid = typeDisplayNlsid;
    }

    public Integer getTypeRelationship() {
        return typeRelationship;
    }

    public void setTypeRelationship(Integer typeRelationship) {
        this.typeRelationship = typeRelationship;
    }

    public String getTypeResourceBundle() {
        return typeResourceBundle;
    }

    public void setTypeResourceBundle(String typeResourceBundle) {
        this.typeResourceBundle = typeResourceBundle;
    }
}
