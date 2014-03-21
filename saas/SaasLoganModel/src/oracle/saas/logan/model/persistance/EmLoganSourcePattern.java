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
@NamedQueries({ @NamedQuery(name = "EmLoganSourcePattern.findAll", query = "select o from EmLoganSourcePattern o") })
@Table(name = "EM_LOGAN_SOURCE_PATTERN")
@IdClass(EmLoganSourcePatternPK.class)
public class EmLoganSourcePattern implements Serializable {
    private static final long serialVersionUID = -8811227296903098372L;
    @Column(name = "PATTERN_AUTHOR", length = 128)
    private String patternAuthor;
    @Column(name = "PATTERN_DESCRIPTION", length = 4000)
    private String patternDescription;
    @Column(name = "PATTERN_DESCRIPTION_NLSID", length = 64)
    private String patternDescriptionNlsid;
    @Column(name = "PATTERN_ID", nullable = false, unique = true)
    private Integer patternId;
    @Id
    @Column(name = "PATTERN_IS_INCLUDE", nullable = false)
    private Integer patternIsInclude;
    @Column(name = "PATTERN_PARSER_INAME", length = 128)
    private String patternParserIname;
    @Id
    @Column(name = "PATTERN_SOURCE_ID", nullable = false)
    private Integer patternSourceId;
    @Id
    @Column(name = "PATTERN_TEXT", nullable = false, length = 4000)
    private String patternText;

    public EmLoganSourcePattern() {
    }

    public EmLoganSourcePattern(String patternAuthor, String patternDescription, String patternDescriptionNlsid,
                                Integer patternId, Integer patternIsInclude, String patternParserIname,
                                Integer patternSourceId, String patternText) {
        this.patternAuthor = patternAuthor;
        this.patternDescription = patternDescription;
        this.patternDescriptionNlsid = patternDescriptionNlsid;
        this.patternId = patternId;
        this.patternIsInclude = patternIsInclude;
        this.patternParserIname = patternParserIname;
        this.patternSourceId = patternSourceId;
        this.patternText = patternText;
    }

    public String getPatternAuthor() {
        return patternAuthor;
    }

    public void setPatternAuthor(String patternAuthor) {
        this.patternAuthor = patternAuthor;
    }

    public String getPatternDescription() {
        return patternDescription;
    }

    public void setPatternDescription(String patternDescription) {
        this.patternDescription = patternDescription;
    }

    public String getPatternDescriptionNlsid() {
        return patternDescriptionNlsid;
    }

    public void setPatternDescriptionNlsid(String patternDescriptionNlsid) {
        this.patternDescriptionNlsid = patternDescriptionNlsid;
    }

    public Integer getPatternId() {
        return patternId;
    }

    public void setPatternId(Integer patternId) {
        this.patternId = patternId;
    }

    public Integer getPatternIsInclude() {
        return patternIsInclude;
    }

    public void setPatternIsInclude(Integer patternIsInclude) {
        this.patternIsInclude = patternIsInclude;
    }

    public String getPatternParserIname() {
        return patternParserIname;
    }

    public void setPatternParserIname(String patternParserIname) {
        this.patternParserIname = patternParserIname;
    }

    public Integer getPatternSourceId() {
        return patternSourceId;
    }

    public void setPatternSourceId(Integer patternSourceId) {
        this.patternSourceId = patternSourceId;
    }

    public String getPatternText() {
        return patternText;
    }

    public void setPatternText(String patternText) {
        this.patternText = patternText;
    }
}
