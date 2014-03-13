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
@NamedQueries({ @NamedQuery(name = "EmLoganRuleSourceMap.findAll", query = "select o from EmLoganRuleSourceMap o") })
@Table(name = "EM_LOGAN_RULE_SOURCE_MAP")
@IdClass(EmLoganRuleSourceMapPK.class)
public class EmLoganRuleSourceMap implements Serializable {
    private static final long serialVersionUID = 5180733332711438157L;
    @Id
    @Column(name = "RS_RULE_ID", nullable = false)
    private Integer rsRuleId;
    @Id
    @Column(name = "RS_SOURCE_ID", nullable = false)
    private Integer rsSourceId;

    public EmLoganRuleSourceMap() {
    }

    public EmLoganRuleSourceMap(Integer rsRuleId, Integer rsSourceId) {
        this.rsRuleId = rsRuleId;
        this.rsSourceId = rsSourceId;
    }

    public Integer getRsRuleId() {
        return rsRuleId;
    }

    public void setRsRuleId(Integer rsRuleId) {
        this.rsRuleId = rsRuleId;
    }

    public Integer getRsSourceId() {
        return rsSourceId;
    }

    public void setRsSourceId(Integer rsSourceId) {
        this.rsSourceId = rsSourceId;
    }
}
