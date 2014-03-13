package oracle.saas.logan.model.persistance;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@NamedQueries({ @NamedQuery(name = "EmLoganMetaSourceType.findAll", query = "select o from EmLoganMetaSourceType o") })
@Table(name = "EM_LOGAN_META_SOURCE_TYPE")
public class EmLoganMetaSourceType implements Serializable {
    private static final long serialVersionUID = -4115635018966560517L;
    @Column(name = "SRCTYPE_DESCRIPTION", length = 4000)
    private String srctypeDescription;
    @Column(name = "SRCTYPE_DESCRIPTION_NLSID", length = 64)
    private String srctypeDescriptionNlsid;
    @Column(name = "SRCTYPE_DNAME", nullable = false, length = 128)
    private String srctypeDname;
    @Column(name = "SRCTYPE_DNAME_NLSID", length = 64)
    private String srctypeDnameNlsid;
    @Column(name = "SRCTYPE_ENTITY_DNAME", nullable = false, length = 128)
    private String srctypeEntityDname;
    @Column(name = "SRCTYPE_ENTITY_DNAME_NLSID", length = 64)
    private String srctypeEntityDnameNlsid;
    @Column(name = "SRCTYPE_ENTITY_INAME", nullable = false, length = 128)
    private String srctypeEntityIname;
    @Id
    @Column(name = "SRCTYPE_INAME", nullable = false, length = 128)
    private String srctypeIname;
    @Column(name = "SRCTYPE_MAX_EXCL_PATTERN", nullable = false)
    private Integer srctypeMaxExclPattern;
    @Column(name = "SRCTYPE_MAX_INCL_PATTERN", nullable = false)
    private Integer srctypeMaxInclPattern;

    public EmLoganMetaSourceType() {
    }

    public EmLoganMetaSourceType(String srctypeDescription, String srctypeDescriptionNlsid, String srctypeDname,
                                 String srctypeDnameNlsid, String srctypeEntityDname, String srctypeEntityDnameNlsid,
                                 String srctypeEntityIname, String srctypeIname, Integer srctypeMaxExclPattern,
                                 Integer srctypeMaxInclPattern) {
        this.srctypeDescription = srctypeDescription;
        this.srctypeDescriptionNlsid = srctypeDescriptionNlsid;
        this.srctypeDname = srctypeDname;
        this.srctypeDnameNlsid = srctypeDnameNlsid;
        this.srctypeEntityDname = srctypeEntityDname;
        this.srctypeEntityDnameNlsid = srctypeEntityDnameNlsid;
        this.srctypeEntityIname = srctypeEntityIname;
        this.srctypeIname = srctypeIname;
        this.srctypeMaxExclPattern = srctypeMaxExclPattern;
        this.srctypeMaxInclPattern = srctypeMaxInclPattern;
    }

    public String getSrctypeDescription() {
        return srctypeDescription;
    }

    public void setSrctypeDescription(String srctypeDescription) {
        this.srctypeDescription = srctypeDescription;
    }

    public String getSrctypeDescriptionNlsid() {
        return srctypeDescriptionNlsid;
    }

    public void setSrctypeDescriptionNlsid(String srctypeDescriptionNlsid) {
        this.srctypeDescriptionNlsid = srctypeDescriptionNlsid;
    }

    public String getSrctypeDname() {
        return srctypeDname;
    }

    public void setSrctypeDname(String srctypeDname) {
        this.srctypeDname = srctypeDname;
    }

    public String getSrctypeDnameNlsid() {
        return srctypeDnameNlsid;
    }

    public void setSrctypeDnameNlsid(String srctypeDnameNlsid) {
        this.srctypeDnameNlsid = srctypeDnameNlsid;
    }

    public String getSrctypeEntityDname() {
        return srctypeEntityDname;
    }

    public void setSrctypeEntityDname(String srctypeEntityDname) {
        this.srctypeEntityDname = srctypeEntityDname;
    }

    public String getSrctypeEntityDnameNlsid() {
        return srctypeEntityDnameNlsid;
    }

    public void setSrctypeEntityDnameNlsid(String srctypeEntityDnameNlsid) {
        this.srctypeEntityDnameNlsid = srctypeEntityDnameNlsid;
    }

    public String getSrctypeEntityIname() {
        return srctypeEntityIname;
    }

    public void setSrctypeEntityIname(String srctypeEntityIname) {
        this.srctypeEntityIname = srctypeEntityIname;
    }

    public String getSrctypeIname() {
        return srctypeIname;
    }

    public void setSrctypeIname(String srctypeIname) {
        this.srctypeIname = srctypeIname;
    }

    public Integer getSrctypeMaxExclPattern() {
        return srctypeMaxExclPattern;
    }

    public void setSrctypeMaxExclPattern(Integer srctypeMaxExclPattern) {
        this.srctypeMaxExclPattern = srctypeMaxExclPattern;
    }

    public Integer getSrctypeMaxInclPattern() {
        return srctypeMaxInclPattern;
    }

    public void setSrctypeMaxInclPattern(Integer srctypeMaxInclPattern) {
        this.srctypeMaxInclPattern = srctypeMaxInclPattern;
    }
}
