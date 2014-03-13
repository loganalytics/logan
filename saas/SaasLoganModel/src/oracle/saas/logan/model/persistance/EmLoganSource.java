package oracle.saas.logan.model.persistance;

import java.io.Serializable;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@NamedQueries({ @NamedQuery(name = "EmLoganSource.findAll", query = "select o from EmLoganSource o"),
//                @NamedQuery(name = "EmLoganSource.findSources", query = "select o.sourceSrctypeIname,t.srctypeIname,m.targetType from EmLoganSource o join EmLoganMetaSourceType t on o.sourceSrctypeIname =t.srctypeIname join  EmTargetTypes m  on o.sourceTargetType=m.targetType")
                @NamedQuery(name = "EmLoganSource.findSources", query = "select o,t,m  from EmLoganSource o join EmLoganMetaSourceType t on o.sourceSrctypeIname =t.srctypeIname join  EmTargetTypes m  on o.sourceTargetType=m.targetType")
                })
@Table(name = "EM_LOGAN_SOURCE")
@IdClass(EmLoganSourcePK.class)
public class EmLoganSource implements Serializable {
    @SuppressWarnings("compatibility:-1852165376069457732")
    private static final long serialVersionUID = 1L;
    @Column(name = "SOURCE_AUTHOR", length = 128)
    private String sourceAuthor;
    @Column(name = "SOURCE_CRITICAL_EDIT_VERSION")
    private Integer sourceCriticalEditVersion;
    @Column(name = "SOURCE_DESCRIPTION", length = 4000)
    private String sourceDescription;
    @Column(name = "SOURCE_DESCRIPTION_NLSID", length = 64)
    private String sourceDescriptionNlsid;
    @Column(name = "SOURCE_DNAME", nullable = false, length = 128)
    private String sourceDname;
    @Column(name = "SOURCE_DNAME_NLSID", length = 64)
    private String sourceDnameNlsid;
    @Column(name = "SOURCE_EDIT_VERSION")
    private Integer sourceEditVersion;
    @Column(name = "SOURCE_ID", nullable = false, unique = true)
    private Integer sourceId;
    @Id
    @Column(name = "SOURCE_INAME", nullable = false, length = 128)
    private String sourceIname;
    @Column(name = "SOURCE_IS_SECURE_CONTENT", nullable = false)
    private Integer sourceIsSecureContent;
    @Column(name = "SOURCE_IS_SYSTEM", nullable = false)
    private Integer sourceIsSystem;
    @Column(name = "SOURCE_LAST_UPDATED_BY", length = 128)
    private String sourceLastUpdatedBy;
    @Temporal(TemporalType.DATE)
    @Column(name = "SOURCE_LAST_UPDATED_DATE")
    private Date sourceLastUpdatedDate;
    @Column(name = "SOURCE_OWNER", length = 128)
    private String sourceOwner;
    @Id
    @Column(name = "SOURCE_SRCTYPE_INAME", nullable = false, length = 128)
    private String sourceSrctypeIname;
    @Id
    @Column(name = "SOURCE_TARGET_TYPE", nullable = false, length = 64)
    private String sourceTargetType;
    
//      @Id
//      @ManyToOne(fetch=FetchType.EAGER)
//      @JoinColumn(name = "SOURCE_SRCTYPE_INAME")
//      private EmLoganMetaSourceType sourceSrctypeIname;
//      @Id
//      @ManyToOne(fetch=FetchType.EAGER)
//      @JoinColumn(name = "SOURCE_TARGET_TYPE")
//      private EmTargetTypes sourceTargetType;

    public EmLoganSource() {
    }

    public EmLoganSource(String sourceAuthor, Integer sourceCriticalEditVersion, String sourceDescription,
                         String sourceDescriptionNlsid, String sourceDname, String sourceDnameNlsid,
                         Integer sourceEditVersion, Integer sourceId, String sourceIname, Integer sourceIsSecureContent,
                         Integer sourceIsSystem, String sourceLastUpdatedBy, Date sourceLastUpdatedDate,
                         String sourceOwner, String sourceSrctypeIname, String sourceTargetType) {
        this.sourceAuthor = sourceAuthor;
        this.sourceCriticalEditVersion = sourceCriticalEditVersion;
        this.sourceDescription = sourceDescription;
        this.sourceDescriptionNlsid = sourceDescriptionNlsid;
        this.sourceDname = sourceDname;
        this.sourceDnameNlsid = sourceDnameNlsid;
        this.sourceEditVersion = sourceEditVersion;
        this.sourceId = sourceId;
        this.sourceIname = sourceIname;
        this.sourceIsSecureContent = sourceIsSecureContent;
        this.sourceIsSystem = sourceIsSystem;
        this.sourceLastUpdatedBy = sourceLastUpdatedBy;
        this.sourceLastUpdatedDate = sourceLastUpdatedDate;
        this.sourceOwner = sourceOwner;
        this.sourceSrctypeIname = sourceSrctypeIname;
        this.sourceTargetType = sourceTargetType;
    }

    public String getSourceAuthor() {
        return sourceAuthor;
    }

    public void setSourceAuthor(String sourceAuthor) {
        this.sourceAuthor = sourceAuthor;
    }

    public Integer getSourceCriticalEditVersion() {
        return sourceCriticalEditVersion;
    }

    public void setSourceCriticalEditVersion(Integer sourceCriticalEditVersion) {
        this.sourceCriticalEditVersion = sourceCriticalEditVersion;
    }

    public String getSourceDescription() {
        return sourceDescription;
    }

    public void setSourceDescription(String sourceDescription) {
        this.sourceDescription = sourceDescription;
    }

    public String getSourceDescriptionNlsid() {
        return sourceDescriptionNlsid;
    }

    public void setSourceDescriptionNlsid(String sourceDescriptionNlsid) {
        this.sourceDescriptionNlsid = sourceDescriptionNlsid;
    }

    public String getSourceDname() {
        return sourceDname;
    }

    public void setSourceDname(String sourceDname) {
        this.sourceDname = sourceDname;
    }

    public String getSourceDnameNlsid() {
        return sourceDnameNlsid;
    }

    public void setSourceDnameNlsid(String sourceDnameNlsid) {
        this.sourceDnameNlsid = sourceDnameNlsid;
    }

    public Integer getSourceEditVersion() {
        return sourceEditVersion;
    }

    public void setSourceEditVersion(Integer sourceEditVersion) {
        this.sourceEditVersion = sourceEditVersion;
    }

    public Integer getSourceId() {
        return sourceId;
    }

    public void setSourceId(Integer sourceId) {
        this.sourceId = sourceId;
    }

    public String getSourceIname() {
        return sourceIname;
    }

    public void setSourceIname(String sourceIname) {
        this.sourceIname = sourceIname;
    }

    public Integer getSourceIsSecureContent() {
        return sourceIsSecureContent;
    }

    public void setSourceIsSecureContent(Integer sourceIsSecureContent) {
        this.sourceIsSecureContent = sourceIsSecureContent;
    }

    public Integer getSourceIsSystem() {
        return sourceIsSystem;
    }

    public void setSourceIsSystem(Integer sourceIsSystem) {
        this.sourceIsSystem = sourceIsSystem;
    }

    public String getSourceLastUpdatedBy() {
        return sourceLastUpdatedBy;
    }

    public void setSourceLastUpdatedBy(String sourceLastUpdatedBy) {
        this.sourceLastUpdatedBy = sourceLastUpdatedBy;
    }

    public Date getSourceLastUpdatedDate() {
        return sourceLastUpdatedDate;
    }

    public void setSourceLastUpdatedDate(Date sourceLastUpdatedDate) {
        this.sourceLastUpdatedDate = sourceLastUpdatedDate;
    }

    public String getSourceOwner() {
        return sourceOwner;
    }

    public void setSourceOwner(String sourceOwner) {
        this.sourceOwner = sourceOwner;
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


//    public void setSourceSrctypeIname(EmLoganMetaSourceType sourceSrctypeIname) {
//        this.sourceSrctypeIname = sourceSrctypeIname;
//    }
//
//    public EmLoganMetaSourceType getSourceSrctypeIname() {
//        return sourceSrctypeIname;
//    }
//
//    public void setSourceTargetType(EmTargetTypes sourceTargetType) {
//        this.sourceTargetType = sourceTargetType;
//    }
//
//    public EmTargetTypes getSourceTargetType() {
//        return sourceTargetType;
//    }


}
