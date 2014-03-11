package oracle.saas.logan.model.persistance;

import java.io.Serializable;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@NamedQueries({ @NamedQuery(name = "EmLoganParser.findAll", query = "select o from EmLoganParser o") })
@Table(name = "EM_LOGAN_PARSER")
public class EmLoganParser implements Serializable {
    private static final long serialVersionUID = -8976031526324108607L;
    @Column(name = "PARSER_AUTHOR", length = 128)
    private String parserAuthor;
    @Column(name = "PARSER_CONTENT")
    private String parserContent;
    @Column(name = "PARSER_CRITICAL_EDIT_VERSION")
    private Integer parserCriticalEditVersion;
    @Column(name = "PARSER_DESCRIPTION", length = 4000)
    private String parserDescription;
    @Column(name = "PARSER_DESCRIPTION_NLSID", length = 64)
    private String parserDescriptionNlsid;
    @Column(name = "PARSER_DNAME", nullable = false, length = 128)
    private String parserDname;
    @Column(name = "PARSER_DNAME_NLSID", length = 64)
    private String parserDnameNlsid;
    @Column(name = "PARSER_EDIT_VERSION")
    private Integer parserEditVersion;
    @Column(name = "PARSER_ENCODING", length = 10)
    private String parserEncoding;
    @Column(name = "PARSER_EXAMPLE_CONTENT")
    private String parserExampleContent;
    @Column(name = "PARSER_HEADER_CONTENT", length = 1000)
    private String parserHeaderContent;
    @Column(name = "PARSER_ID", nullable = false, unique = true)
    private Integer parserId;
    @Id
    @Column(name = "PARSER_INAME", nullable = false, length = 128)
    private String parserIname;
    @Column(name = "PARSER_IS_SINGLE_LINE_CONTENT")
    private Integer parserIsSingleLineContent;
    @Column(name = "PARSER_IS_SYSTEM", nullable = false)
    private Integer parserIsSystem;
    @Column(name = "PARSER_LANGUAGE", nullable = false, length = 10)
    private String parserLanguage;
    @Column(name = "PARSER_TYPE")
    private Integer parserType;
    @OneToMany(mappedBy = "parfParserIname", cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    private List<EmLoganParserFieldMap> emLoganParserFieldMapList1;

    public EmLoganParser() {
    }

    public EmLoganParser(String parserAuthor, String parserContent, Integer parserCriticalEditVersion,
                         String parserDescription, String parserDescriptionNlsid, String parserDname,
                         String parserDnameNlsid, Integer parserEditVersion, String parserEncoding,
                         String parserExampleContent, String parserHeaderContent, Integer parserId, String parserIname,
                         Integer parserIsSingleLineContent, Integer parserIsSystem, String parserLanguage,
                         Integer parserType) {
        this.parserAuthor = parserAuthor;
        this.parserContent = parserContent;
        this.parserCriticalEditVersion = parserCriticalEditVersion;
        this.parserDescription = parserDescription;
        this.parserDescriptionNlsid = parserDescriptionNlsid;
        this.parserDname = parserDname;
        this.parserDnameNlsid = parserDnameNlsid;
        this.parserEditVersion = parserEditVersion;
        this.parserEncoding = parserEncoding;
        this.parserExampleContent = parserExampleContent;
        this.parserHeaderContent = parserHeaderContent;
        this.parserId = parserId;
        this.parserIname = parserIname;
        this.parserIsSingleLineContent = parserIsSingleLineContent;
        this.parserIsSystem = parserIsSystem;
        this.parserLanguage = parserLanguage;
        this.parserType = parserType;
    }

    public String getParserAuthor() {
        return parserAuthor;
    }

    public void setParserAuthor(String parserAuthor) {
        this.parserAuthor = parserAuthor;
    }

    public String getParserContent() {
        return parserContent;
    }

    public void setParserContent(String parserContent) {
        this.parserContent = parserContent;
    }

    public Integer getParserCriticalEditVersion() {
        return parserCriticalEditVersion;
    }

    public void setParserCriticalEditVersion(Integer parserCriticalEditVersion) {
        this.parserCriticalEditVersion = parserCriticalEditVersion;
    }

    public String getParserDescription() {
        return parserDescription;
    }

    public void setParserDescription(String parserDescription) {
        this.parserDescription = parserDescription;
    }

    public String getParserDescriptionNlsid() {
        return parserDescriptionNlsid;
    }

    public void setParserDescriptionNlsid(String parserDescriptionNlsid) {
        this.parserDescriptionNlsid = parserDescriptionNlsid;
    }

    public String getParserDname() {
        return parserDname;
    }

    public void setParserDname(String parserDname) {
        this.parserDname = parserDname;
    }

    public String getParserDnameNlsid() {
        return parserDnameNlsid;
    }

    public void setParserDnameNlsid(String parserDnameNlsid) {
        this.parserDnameNlsid = parserDnameNlsid;
    }

    public Integer getParserEditVersion() {
        return parserEditVersion;
    }

    public void setParserEditVersion(Integer parserEditVersion) {
        this.parserEditVersion = parserEditVersion;
    }

    public String getParserEncoding() {
        return parserEncoding;
    }

    public void setParserEncoding(String parserEncoding) {
        this.parserEncoding = parserEncoding;
    }

    public String getParserExampleContent() {
        return parserExampleContent;
    }

    public void setParserExampleContent(String parserExampleContent) {
        this.parserExampleContent = parserExampleContent;
    }

    public String getParserHeaderContent() {
        return parserHeaderContent;
    }

    public void setParserHeaderContent(String parserHeaderContent) {
        this.parserHeaderContent = parserHeaderContent;
    }

    public Integer getParserId() {
        return parserId;
    }

    public void setParserId(Integer parserId) {
        this.parserId = parserId;
    }

    public String getParserIname() {
        return parserIname;
    }

    public void setParserIname(String parserIname) {
        this.parserIname = parserIname;
    }

    public Integer getParserIsSingleLineContent() {
        return parserIsSingleLineContent;
    }

    public void setParserIsSingleLineContent(Integer parserIsSingleLineContent) {
        this.parserIsSingleLineContent = parserIsSingleLineContent;
    }

    public Integer getParserIsSystem() {
        return parserIsSystem;
    }

    public void setParserIsSystem(Integer parserIsSystem) {
        this.parserIsSystem = parserIsSystem;
    }

    public String getParserLanguage() {
        return parserLanguage;
    }

    public void setParserLanguage(String parserLanguage) {
        this.parserLanguage = parserLanguage;
    }

    public Integer getParserType() {
        return parserType;
    }

    public void setParserType(Integer parserType) {
        this.parserType = parserType;
    }

    public List<EmLoganParserFieldMap> getEmLoganParserFieldMapList1() {
        return emLoganParserFieldMapList1;
    }

    public void setEmLoganParserFieldMapList1(List<EmLoganParserFieldMap> emLoganParserFieldMapList1) {
        this.emLoganParserFieldMapList1 = emLoganParserFieldMapList1;
    }

    public EmLoganParserFieldMap addEmLoganParserFieldMap(EmLoganParserFieldMap emLoganParserFieldMap) {
        getEmLoganParserFieldMapList1().add(emLoganParserFieldMap);
        emLoganParserFieldMap.setParfParserIname(this);
        return emLoganParserFieldMap;
    }

    public EmLoganParserFieldMap removeEmLoganParserFieldMap(EmLoganParserFieldMap emLoganParserFieldMap) {
        getEmLoganParserFieldMapList1().remove(emLoganParserFieldMap);
        emLoganParserFieldMap.setParfParserIname(null);
        return emLoganParserFieldMap;
    }
}
