package oracle.saas.logan.model.persistance;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@NamedQueries({ @NamedQuery(name = "EmLoganMetaOperator.findAll", query = "select o from EmLoganMetaOperator o") })
@Table(name = "EM_LOGAN_META_OPERATOR")
public class EmLoganMetaOperator implements Serializable {
    private static final long serialVersionUID = 7809668909390866879L;
    @Column(name = "OPER_DATATYPE", nullable = false, length = 64)
    private String operDatatype;
    @Id
    @Column(name = "OPER_NAME", nullable = false, length = 64)
    private String operName;
    @Column(name = "OPER_SQL", length = 64)
    private String operSql;

    public EmLoganMetaOperator() {
    }

    public EmLoganMetaOperator(String operDatatype, String operName, String operSql) {
        this.operDatatype = operDatatype;
        this.operName = operName;
        this.operSql = operSql;
    }

    public String getOperDatatype() {
        return operDatatype;
    }

    public void setOperDatatype(String operDatatype) {
        this.operDatatype = operDatatype;
    }

    public String getOperName() {
        return operName;
    }

    public void setOperName(String operName) {
        this.operName = operName;
    }

    public String getOperSql() {
        return operSql;
    }

    public void setOperSql(String operSql) {
        this.operSql = operSql;
    }
}
