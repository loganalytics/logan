package oracle.saas.logan.model.persistance;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@NamedQueries({ @NamedQuery(name = "EmLoganMetaAvailParam.findAll", query = "select o from EmLoganMetaAvailParam o") })
@Table(name = "EM_LOGAN_META_AVAIL_PARAM")
@IdClass(EmLoganMetaAvailParamPK.class)
public class EmLoganMetaAvailParam implements Serializable {
    private static final long serialVersionUID = -1213383140203301113L;
    @Column(name = "AP_AUTHOR", length = 128)
    private String apAuthor;
    @Column(name = "AP_DESCRIPTION", length = 4000)
    private String apDescription;
    @Column(name = "AP_DESCRIPTION_NLSID", length = 64)
    private String apDescriptionNlsid;
    @Column(name = "AP_EDIT_VERSION")
    private Integer apEditVersion;
    @Id
    @Column(name = "AP_NAME", nullable = false, length = 128)
    private String apName;
    @Id
    @Column(name = "AP_PLUGIN_ID", nullable = false, length = 64)
    private String apPluginId;
    @Id
    @Column(name = "AP_PLUGIN_VERSION", nullable = false, length = 32)
    private String apPluginVersion;
    @Id
    @Column(name = "AP_TARGET_TYPE", nullable = false, length = 64)
    private String apTargetType;
    @Column(name = "AP_TYPE", nullable = false)
    private Integer apType;

    public EmLoganMetaAvailParam() {
    }

    public EmLoganMetaAvailParam(String apAuthor, String apDescription, String apDescriptionNlsid,
                                 Integer apEditVersion, String apName, String apPluginId, String apPluginVersion,
                                 String apTargetType, Integer apType) {
        this.apAuthor = apAuthor;
        this.apDescription = apDescription;
        this.apDescriptionNlsid = apDescriptionNlsid;
        this.apEditVersion = apEditVersion;
        this.apName = apName;
        this.apPluginId = apPluginId;
        this.apPluginVersion = apPluginVersion;
        this.apTargetType = apTargetType;
        this.apType = apType;
    }

    public String getApAuthor() {
        return apAuthor;
    }

    public void setApAuthor(String apAuthor) {
        this.apAuthor = apAuthor;
    }

    public String getApDescription() {
        return apDescription;
    }

    public void setApDescription(String apDescription) {
        this.apDescription = apDescription;
    }

    public String getApDescriptionNlsid() {
        return apDescriptionNlsid;
    }

    public void setApDescriptionNlsid(String apDescriptionNlsid) {
        this.apDescriptionNlsid = apDescriptionNlsid;
    }

    public Integer getApEditVersion() {
        return apEditVersion;
    }

    public void setApEditVersion(Integer apEditVersion) {
        this.apEditVersion = apEditVersion;
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

    public Integer getApType() {
        return apType;
    }

    public void setApType(Integer apType) {
        this.apType = apType;
    }
}
