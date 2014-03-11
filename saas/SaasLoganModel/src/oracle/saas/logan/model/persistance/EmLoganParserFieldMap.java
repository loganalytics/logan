package oracle.saas.logan.model.persistance;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@NamedQueries({ @NamedQuery(name = "EmLoganParserFieldMap.findAll", query = "select o from EmLoganParserFieldMap o") })
@Table(name = "EM_LOGAN_PARSER_FIELD_MAP")
@IdClass(EmLoganParserFieldMapPK.class)
public class EmLoganParserFieldMap implements Serializable {
    private static final long serialVersionUID = 7389188090824392632L;
    @Column(name = "PARF_ID", nullable = false, unique = true)
    private Integer parfId;
    @Column(name = "PARF_SEQUENCE", nullable = false)
    private Integer parfSequence;
//    @ManyToOne
//    @Id
//    @JoinColumn(name = "PARF_FIELD_INAME")
//    private EmLoganCommonFields emLoganCommonFields;
//    @ManyToOne
//    @Id
//    @JoinColumn(name = "PARF_PARSER_INAME")
//    private EmLoganParser emLoganParser;

    public EmLoganParserFieldMap() {
    }

    public EmLoganParserFieldMap(EmLoganCommonFields emLoganCommonFields, Integer parfId, EmLoganParser emLoganParser,
                                 Integer parfSequence) {
        this.parfFieldIname = emLoganCommonFields;
        this.parfId = parfId;
        this.parfParserIname = emLoganParser;
        this.parfSequence = parfSequence;
    }


    public Integer getParfId() {
        return parfId;
    }

    public void setParfId(Integer parfId) {
        this.parfId = parfId;
    }


    public Integer getParfSequence() {
        return parfSequence;
    }

    public void setParfSequence(Integer parfSequence) {
        this.parfSequence = parfSequence;
    }

//    public EmLoganCommonFields getEmLoganCommonFields() {
//        return emLoganCommonFields;
//    }
//
//    public void setEmLoganCommonFields(EmLoganCommonFields emLoganCommonFields) {
//        this.emLoganCommonFields = emLoganCommonFields;
//    }
//
//    public EmLoganParser getEmLoganParser() {
//        return emLoganParser;
//    }
//
//    public void setEmLoganParser(EmLoganParser emLoganParser) {
//        this.emLoganParser = emLoganParser;
//    }
    
    @Id
    @JoinColumn(name = "PARF_FIELD_INAME")
    private EmLoganCommonFields parfFieldIname;
    @ManyToOne
    @Id
    @JoinColumn(name = "PARF_PARSER_INAME")
    private EmLoganParser parfParserIname;    
    
    public EmLoganCommonFields getParfFieldIname() {
        return parfFieldIname;
    }

    public void setParfFieldIname(EmLoganCommonFields parfFieldIname) {
        this.parfFieldIname = parfFieldIname;
    }

    public EmLoganParser getParfParserIname() {
        return parfParserIname;
    }

    public void setParfParserIname(EmLoganParser parfParserIname) {
        this.parfParserIname = parfParserIname;
    }
    
}
