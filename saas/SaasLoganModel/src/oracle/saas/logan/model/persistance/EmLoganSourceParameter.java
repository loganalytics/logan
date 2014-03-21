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
@NamedQueries({
              @NamedQuery(name = "EmLoganSourceParameter.findAll", query = "select o from EmLoganSourceParameter o") })
@Table(name = "EM_LOGAN_SOURCE_PARAMETER")
@IdClass(EmLoganSourceParameterPK.class)
public class EmLoganSourceParameter implements Serializable {
    private static final long serialVersionUID = 47963025420550399L;
    @Column(name = "PARAM_AUTHOR", length = 128)
    private String paramAuthor;
    @Column(name = "PARAM_DEFAULT_VALUE", length = 4000)
    private String paramDefaultValue;
    @Column(name = "PARAM_DESCRIPTION", length = 4000)
    private String paramDescription;
    @Column(name = "PARAM_DESCRIPTION_NLSID", length = 64)
    private String paramDescriptionNlsid;
    @Column(name = "PARAM_IS_ACTIVE", nullable = false)
    private Integer paramIsActive;
    @Id
    @Column(name = "PARAM_NAME", nullable = false, length = 128)
    private String paramName;
    @Column(name = "PARAM_NAME_NLSID", length = 64)
    private String paramNameNlsid;
    @Id
    @Column(name = "PARAM_SOURCE_ID", nullable = false)
    private Integer paramSourceId;

    public EmLoganSourceParameter() {
    }

    public EmLoganSourceParameter(String paramAuthor, String paramDefaultValue, String paramDescription,
                                  String paramDescriptionNlsid, Integer paramIsActive, String paramName,
                                  String paramNameNlsid, Integer paramSourceId) {
        this.paramAuthor = paramAuthor;
        this.paramDefaultValue = paramDefaultValue;
        this.paramDescription = paramDescription;
        this.paramDescriptionNlsid = paramDescriptionNlsid;
        this.paramIsActive = paramIsActive;
        this.paramName = paramName;
        this.paramNameNlsid = paramNameNlsid;
        this.paramSourceId = paramSourceId;
    }

    public String getParamAuthor() {
        return paramAuthor;
    }

    public void setParamAuthor(String paramAuthor) {
        this.paramAuthor = paramAuthor;
    }

    public String getParamDefaultValue() {
        return paramDefaultValue;
    }

    public void setParamDefaultValue(String paramDefaultValue) {
        this.paramDefaultValue = paramDefaultValue;
    }

    public String getParamDescription() {
        return paramDescription;
    }

    public void setParamDescription(String paramDescription) {
        this.paramDescription = paramDescription;
    }

    public String getParamDescriptionNlsid() {
        return paramDescriptionNlsid;
    }

    public void setParamDescriptionNlsid(String paramDescriptionNlsid) {
        this.paramDescriptionNlsid = paramDescriptionNlsid;
    }

    public Integer getParamIsActive() {
        return paramIsActive;
    }

    public void setParamIsActive(Integer paramIsActive) {
        this.paramIsActive = paramIsActive;
    }

    public String getParamName() {
        return paramName;
    }

    public void setParamName(String paramName) {
        this.paramName = paramName;
    }

    public String getParamNameNlsid() {
        return paramNameNlsid;
    }

    public void setParamNameNlsid(String paramNameNlsid) {
        this.paramNameNlsid = paramNameNlsid;
    }

    public Integer getParamSourceId() {
        return paramSourceId;
    }

    public void setParamSourceId(Integer paramSourceId) {
        this.paramSourceId = paramSourceId;
    }
}
