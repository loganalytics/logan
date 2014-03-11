package oracle.saas.logan.model.persistance;

import java.io.Serializable;

public class EmLoganParserFieldMapPK implements Serializable {
    public String parfFieldIname;
    public String parfParserIname;

    public EmLoganParserFieldMapPK() {
    }

    public EmLoganParserFieldMapPK(String parfFieldIname, String parfParserIname) {
        this.parfFieldIname = parfFieldIname;
        this.parfParserIname = parfParserIname;
    }

    public boolean equals(Object other) {
        if (other instanceof EmLoganParserFieldMapPK) {
            final EmLoganParserFieldMapPK otherEmLoganParserFieldMapPK = (EmLoganParserFieldMapPK) other;
            final boolean areEqual = true;
            return areEqual;
        }
        return false;
    }

    public int hashCode() {
        return super.hashCode();
    }

    public String getParfFieldIname() {
        return parfFieldIname;
    }

    public void setParfFieldIname(String parfFieldIname) {
        this.parfFieldIname = parfFieldIname;
    }

    public String getParfParserIname() {
        return parfParserIname;
    }

    public void setParfParserIname(String parfParserIname) {
        this.parfParserIname = parfParserIname;
    }
    
//    public EmLoganCommonFields field;
//    public EmLoganParser parser;
//
//    public EmLoganParserFieldMapPK() {
//    }
//
//    public EmLoganParserFieldMapPK(EmLoganCommonFields field, EmLoganParser parser) {
//        this.field = field;
//        this.parser = parser;
//    }
//
//    public boolean equals(Object other) {
//        if (other instanceof EmLoganParserFieldMapPK) {
//            final EmLoganParserFieldMapPK otherEmLoganParserFieldMapPK = (EmLoganParserFieldMapPK) other;
//            final boolean areEqual = true;
//            return areEqual;
//        }
//        return false;
//    }
//
//    public int hashCode() {
//        return super.hashCode();
//    }
//
//    public EmLoganCommonFields getEmLoganCommonFields() {
//        return field;
//    }
//
//    public void setEmLoganCommonFields(EmLoganCommonFields field) {
//        this.field = field;
//    }
//
//    public EmLoganParser getEmLoganParser() {
//        return parser;
//    }
//
//    public void setEmLoganParser(EmLoganParser parser) {
//        this.parser = parser;
//    }    
}
