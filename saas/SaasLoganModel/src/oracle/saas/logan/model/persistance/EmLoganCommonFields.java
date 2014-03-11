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
@NamedQueries({ @NamedQuery(name = "EmLoganCommonFields.findAll", query = "select o from EmLoganCommonFields o") })
@Table(name = "EM_LOGAN_COMMON_FIELDS")
public class EmLoganCommonFields implements Serializable {
    private static final long serialVersionUID = 4659203089246405086L;
    @Column(name = "FIELD_AUTHOR", length = 128)
    private String fieldAuthor;
    @Column(name = "FIELD_CEE_DNAME", length = 128)
    private String fieldCeeDname;
    @Column(name = "FIELD_DATATYPE", length = 30)
    private String fieldDatatype;
    @Column(name = "FIELD_DB_DATATYPE", length = 30)
    private String fieldDbDatatype;
    @Column(name = "FIELD_DESCRIPTION", length = 4000)
    private String fieldDescription;
    @Column(name = "FIELD_DESCRIPTION_NLSID", length = 64)
    private String fieldDescriptionNlsid;
    @Column(name = "FIELD_DIM_KEY_COL", length = 30)
    private String fieldDimKeyCol;
    @Column(name = "FIELD_DIM_REF_COL", length = 30)
    private String fieldDimRefCol;
    @Column(name = "FIELD_DIM_SEQ", length = 30)
    private String fieldDimSeq;
    @Column(name = "FIELD_DIM_TAB_NAME", length = 30)
    private String fieldDimTabName;
    @Column(name = "FIELD_DNAME", nullable = false, length = 128)
    private String fieldDname;
    @Column(name = "FIELD_DNAME_NLSID", length = 64)
    private String fieldDnameNlsid;
    @Column(name = "FIELD_EDIT_VERSION")
    private Integer fieldEditVersion;
    @Column(name = "FIELD_FACT_COL_OFFSET")
    private Integer fieldFactColOffset;
    @Column(name = "FIELD_FACT_TAB_NAME", length = 30)
    private String fieldFactTabName;
    @Column(name = "FIELD_FIELDTYPE", nullable = false)
    private Integer fieldFieldtype;
    @Id
    @Column(name = "FIELD_INAME", nullable = false, length = 128)
    private String fieldIname;
    @Column(name = "FIELD_IS_PRIMARY", nullable = false)
    private Integer fieldIsPrimary;
    @Column(name = "FIELD_IS_SYSTEM", nullable = false)
    private Integer fieldIsSystem;
    @Column(name = "FIELD_MAXSIZE")
    private Integer fieldMaxsize;
    @Column(name = "FIELD_METRIC_KEY_ELIGIBLE", nullable = false)
    private Integer fieldMetricKeyEligible;
    @Column(name = "FIELD_METRIC_VALUE_ELIGIBLE", nullable = false)
    private Integer fieldMetricValueEligible;
    @Column(name = "FIELD_OBS_FIELD_NAME", length = 30)
    private String fieldObsFieldName;
    @OneToMany(mappedBy = "parfFieldIname", cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    private List<EmLoganParserFieldMap> emLoganParserFieldMapList;

    public EmLoganCommonFields() {
    }

    public EmLoganCommonFields(String fieldAuthor, String fieldCeeDname, String fieldDatatype, String fieldDbDatatype,
                               String fieldDescription, String fieldDescriptionNlsid, String fieldDimKeyCol,
                               String fieldDimRefCol, String fieldDimSeq, String fieldDimTabName, String fieldDname,
                               String fieldDnameNlsid, Integer fieldEditVersion, Integer fieldFactColOffset,
                               String fieldFactTabName, Integer fieldFieldtype, String fieldIname,
                               Integer fieldIsPrimary, Integer fieldIsSystem, Integer fieldMaxsize,
                               Integer fieldMetricKeyEligible, Integer fieldMetricValueEligible,
                               String fieldObsFieldName) {
        this.fieldAuthor = fieldAuthor;
        this.fieldCeeDname = fieldCeeDname;
        this.fieldDatatype = fieldDatatype;
        this.fieldDbDatatype = fieldDbDatatype;
        this.fieldDescription = fieldDescription;
        this.fieldDescriptionNlsid = fieldDescriptionNlsid;
        this.fieldDimKeyCol = fieldDimKeyCol;
        this.fieldDimRefCol = fieldDimRefCol;
        this.fieldDimSeq = fieldDimSeq;
        this.fieldDimTabName = fieldDimTabName;
        this.fieldDname = fieldDname;
        this.fieldDnameNlsid = fieldDnameNlsid;
        this.fieldEditVersion = fieldEditVersion;
        this.fieldFactColOffset = fieldFactColOffset;
        this.fieldFactTabName = fieldFactTabName;
        this.fieldFieldtype = fieldFieldtype;
        this.fieldIname = fieldIname;
        this.fieldIsPrimary = fieldIsPrimary;
        this.fieldIsSystem = fieldIsSystem;
        this.fieldMaxsize = fieldMaxsize;
        this.fieldMetricKeyEligible = fieldMetricKeyEligible;
        this.fieldMetricValueEligible = fieldMetricValueEligible;
        this.fieldObsFieldName = fieldObsFieldName;
    }

    public String getFieldAuthor() {
        return fieldAuthor;
    }

    public void setFieldAuthor(String fieldAuthor) {
        this.fieldAuthor = fieldAuthor;
    }

    public String getFieldCeeDname() {
        return fieldCeeDname;
    }

    public void setFieldCeeDname(String fieldCeeDname) {
        this.fieldCeeDname = fieldCeeDname;
    }

    public String getFieldDatatype() {
        return fieldDatatype;
    }

    public void setFieldDatatype(String fieldDatatype) {
        this.fieldDatatype = fieldDatatype;
    }

    public String getFieldDbDatatype() {
        return fieldDbDatatype;
    }

    public void setFieldDbDatatype(String fieldDbDatatype) {
        this.fieldDbDatatype = fieldDbDatatype;
    }

    public String getFieldDescription() {
        return fieldDescription;
    }

    public void setFieldDescription(String fieldDescription) {
        this.fieldDescription = fieldDescription;
    }

    public String getFieldDescriptionNlsid() {
        return fieldDescriptionNlsid;
    }

    public void setFieldDescriptionNlsid(String fieldDescriptionNlsid) {
        this.fieldDescriptionNlsid = fieldDescriptionNlsid;
    }

    public String getFieldDimKeyCol() {
        return fieldDimKeyCol;
    }

    public void setFieldDimKeyCol(String fieldDimKeyCol) {
        this.fieldDimKeyCol = fieldDimKeyCol;
    }

    public String getFieldDimRefCol() {
        return fieldDimRefCol;
    }

    public void setFieldDimRefCol(String fieldDimRefCol) {
        this.fieldDimRefCol = fieldDimRefCol;
    }

    public String getFieldDimSeq() {
        return fieldDimSeq;
    }

    public void setFieldDimSeq(String fieldDimSeq) {
        this.fieldDimSeq = fieldDimSeq;
    }

    public String getFieldDimTabName() {
        return fieldDimTabName;
    }

    public void setFieldDimTabName(String fieldDimTabName) {
        this.fieldDimTabName = fieldDimTabName;
    }

    public String getFieldDname() {
        return fieldDname;
    }

    public void setFieldDname(String fieldDname) {
        this.fieldDname = fieldDname;
    }

    public String getFieldDnameNlsid() {
        return fieldDnameNlsid;
    }

    public void setFieldDnameNlsid(String fieldDnameNlsid) {
        this.fieldDnameNlsid = fieldDnameNlsid;
    }

    public Integer getFieldEditVersion() {
        return fieldEditVersion;
    }

    public void setFieldEditVersion(Integer fieldEditVersion) {
        this.fieldEditVersion = fieldEditVersion;
    }

    public Integer getFieldFactColOffset() {
        return fieldFactColOffset;
    }

    public void setFieldFactColOffset(Integer fieldFactColOffset) {
        this.fieldFactColOffset = fieldFactColOffset;
    }

    public String getFieldFactTabName() {
        return fieldFactTabName;
    }

    public void setFieldFactTabName(String fieldFactTabName) {
        this.fieldFactTabName = fieldFactTabName;
    }

    public Integer getFieldFieldtype() {
        return fieldFieldtype;
    }

    public void setFieldFieldtype(Integer fieldFieldtype) {
        this.fieldFieldtype = fieldFieldtype;
    }

    public String getFieldIname() {
        return fieldIname;
    }

    public void setFieldIname(String fieldIname) {
        this.fieldIname = fieldIname;
    }

    public Integer getFieldIsPrimary() {
        return fieldIsPrimary;
    }

    public void setFieldIsPrimary(Integer fieldIsPrimary) {
        this.fieldIsPrimary = fieldIsPrimary;
    }

    public Integer getFieldIsSystem() {
        return fieldIsSystem;
    }

    public void setFieldIsSystem(Integer fieldIsSystem) {
        this.fieldIsSystem = fieldIsSystem;
    }

    public Integer getFieldMaxsize() {
        return fieldMaxsize;
    }

    public void setFieldMaxsize(Integer fieldMaxsize) {
        this.fieldMaxsize = fieldMaxsize;
    }

    public Integer getFieldMetricKeyEligible() {
        return fieldMetricKeyEligible;
    }

    public void setFieldMetricKeyEligible(Integer fieldMetricKeyEligible) {
        this.fieldMetricKeyEligible = fieldMetricKeyEligible;
    }

    public Integer getFieldMetricValueEligible() {
        return fieldMetricValueEligible;
    }

    public void setFieldMetricValueEligible(Integer fieldMetricValueEligible) {
        this.fieldMetricValueEligible = fieldMetricValueEligible;
    }

    public String getFieldObsFieldName() {
        return fieldObsFieldName;
    }

    public void setFieldObsFieldName(String fieldObsFieldName) {
        this.fieldObsFieldName = fieldObsFieldName;
    }

    public List<EmLoganParserFieldMap> getEmLoganParserFieldMapList() {
        return emLoganParserFieldMapList;
    }

    public void setEmLoganParserFieldMapList(List<EmLoganParserFieldMap> emLoganParserFieldMapList) {
        this.emLoganParserFieldMapList = emLoganParserFieldMapList;
    }

    public EmLoganParserFieldMap addEmLoganParserFieldMap(EmLoganParserFieldMap emLoganParserFieldMap) {
        getEmLoganParserFieldMapList().add(emLoganParserFieldMap);
        emLoganParserFieldMap.setParfFieldIname(this);
        return emLoganParserFieldMap;
    }

    public EmLoganParserFieldMap removeEmLoganParserFieldMap(EmLoganParserFieldMap emLoganParserFieldMap) {
        getEmLoganParserFieldMapList().remove(emLoganParserFieldMap);
        emLoganParserFieldMap.setParfFieldIname(null);
        return emLoganParserFieldMap;
    }
}
