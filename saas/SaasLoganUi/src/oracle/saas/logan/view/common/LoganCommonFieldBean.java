package oracle.saas.logan.view.common;


import java.util.List;

import oracle.jbo.Row;
import oracle.jbo.domain.Number;

import oracle.saas.logan.util.Logger;
import oracle.saas.logan.view.BaseDataPersistenceHandler;
import oracle.saas.logan.view.KeyValuePairObj;


public class LoganCommonFieldBean extends BaseDataPersistenceHandler
    implements Cloneable
{    
    private static final String FieldIname = "FieldIname";
    private static final String FieldDname = "FieldDname";
    private static final String FieldFieldtype = "FieldFieldtype";
    private static final String FieldDescription = "FieldDescription";
    private static final String FieldIsSystem = "FieldIsSystem";
    private static final String FieldIsPrimary = "FieldIsPrimary";
    private static final String FieldCeeDname = "FieldCeeDname";
    private static final String FieldObsFieldName = "FieldObsFieldName";
    private static final String FieldAuthor = "FieldAuthor";
    private static final String FieldMaxsize = "FieldMaxsize";
    private static final String FieldDatatype = "FieldDatatype";
    private static final String FieldMetricKeyEligible = "FieldMetricKeyEligible";
    private static final String FieldMetricValueEligible = "FieldMetricValueEligible";

    private String fieldIname;
    private String fieldDname;
    
    private String fieldAuthor;

    private Number fieldType;
    private Number fieldMaxsize;
    private String fieldDescription;

    private boolean systemDefined;
    private boolean primaryField;

    private Number isSystem;
    private Number isPrimary;

    private String fieldCeeName;
    private String fieldObsName;
    
    private String fieldDatatype = DATATYPE_TEXT;
    
    private Number fieldMetricKeyEligible;
    private Number fieldMetricValueEligible;
    
    private boolean metricKEligible;
    private boolean metricVEligible;

    private static final String DATATYPE_TEXT = "TEXT";
    private static final String DATATYPE_NUMBER = "NUMBER";
    private static final String DATATYPE_STRING = "STRING";
    
    public static final String FIELD_DATATYPE_USERDEF_DEFAULT = DATATYPE_STRING;
    public static final int FIELD_MAXSIZE_USERDEF_DEFAULT = 256;
    
    public LoganCommonFieldBean(String fieldIname, 
                                String fieldDname,
                                String fieldDatatype, 
                                Number fieldType)
    {
        this.fieldIname = fieldIname;
        this.fieldDname = fieldDname;
        this.fieldDatatype = fieldDatatype;
        this.fieldType = fieldType;
    }

    private static final String[] dataTypeSOCList =
    { DATATYPE_TEXT, DATATYPE_NUMBER };
    
    private static final String LOGAN_RESBUNDLE =
        "oracle.sysman.core.loganalytics.ui.CoreLoganalyticsUiMsg";
    
    private static final Logger s_log =
        Logger.getLogger(LoganCommonFieldBean.class.getName());

    public LoganCommonFieldBean(String fieldIname, String fieldDname,
                                String fieldObsFieldName, Number fieldType,
                                String fieldDescription, Number isSystem,
                                Number isPrimary, String fieldCeeName,
                                Number fieldMaxsize, String fieldAuthor,
                                String fieldDatatype)
    {
        this.fieldIname = fieldIname;
        this.fieldDname = fieldDname;
        this.fieldObsName = fieldObsFieldName;
        this.fieldType = fieldType;
        this.fieldDescription = fieldDescription;
        this.isSystem = isSystem;
        if (isSystem != null && isSystem.intValue() == 1)
            systemDefined = true;
        this.isPrimary = isPrimary;
        if (isPrimary != null && isPrimary.intValue() == 1)
            primaryField = true;
        this.fieldCeeName = fieldCeeName;
        this.fieldMaxsize = fieldMaxsize;
        this.fieldAuthor = fieldAuthor;        
        this.fieldDatatype = DATATYPE_TEXT;
        if (DATATYPE_NUMBER.equalsIgnoreCase(fieldDatatype))
        {
            this.fieldDatatype = DATATYPE_NUMBER;
        }
    }

    public LoganCommonFieldBean(Row vorow)
    {
        if(vorow == null)
            return;
        
        this.fieldIname = (String)vorow.getAttribute(FieldIname);
        this.fieldDname = (String)vorow.getAttribute(FieldDname);
        this.fieldType = (Number)vorow.getAttribute(FieldFieldtype);
        this.fieldDescription = (String)vorow.getAttribute(FieldDescription);
        this.isSystem = (Number)vorow.getAttribute(FieldIsSystem);
        if (isSystem != null && isSystem.intValue() == 1)
            systemDefined = true;
        this.isPrimary = (Number)vorow.getAttribute(FieldIsPrimary);
        if (isPrimary != null && isPrimary.intValue() == 1)
            primaryField = true;
        this.fieldCeeName = (String)vorow.getAttribute(FieldCeeDname);
        this.fieldObsName = (String)vorow.getAttribute(FieldObsFieldName);
        this.fieldMaxsize = (Number)vorow.getAttribute(FieldMaxsize);
        this.fieldAuthor = (String)vorow.getAttribute(FieldAuthor);
        
        this.fieldDatatype = (String) vorow.getAttribute(FieldDatatype);
        if (DATATYPE_STRING.equalsIgnoreCase(fieldDatatype))
        {
            this.fieldDatatype = DATATYPE_TEXT;
        }
        
        this.fieldMetricKeyEligible =
                (Number) vorow.getAttribute(FieldMetricKeyEligible);
        if (fieldMetricKeyEligible != null &&
            fieldMetricKeyEligible.intValue() == 1)
        {
            this.metricKEligible = true;
        }
        
        this.fieldMetricValueEligible =
                (Number) vorow.getAttribute(FieldMetricValueEligible);
        if (fieldMetricValueEligible != null &&
            fieldMetricValueEligible.intValue() == 1)
        {
            this.metricVEligible = true;
        }
    }
    
    public Object clone()
    {
        LoganCommonFieldBean c =
            new LoganCommonFieldBean(fieldIname, fieldDname, fieldObsName,
                                     fieldType, fieldDescription, isSystem,
                                     isPrimary, fieldCeeName, fieldMaxsize,
                                     fieldAuthor, fieldDatatype);
        return c;
    }
    
    /**
     * Handle the Insert operation here.
     * will first retrive fieid internal name and field obs field name from 
     * database which are not in use and be the first available one.
     * <code>
     * FieldIname = FieldIname<br/>
     * FieldDname = FieldDname<br/>
     * FieldIsSystem = 0 (not a system defined field)<br/>
     * FieldMaxsize = FieldMaxsize<br/>
     * FieldDatatype = FieldDatatype<br/>
     * FieldMetricKeyEligible = metricKey<br/>
     * FieldMetricValueEligible = metricValue.</code>
     * 
     */
    public void handleInsertOperation() {
        //TODO JPA
//        LoganAMImpl am = LoganLibUiUtil.getAppModule();
//        ViewObjectImpl voRef = am.getEmLoganCommonFieldsVO();
//        
//        Number mkey = this.metricKEligible? new Number(1): new Number(0);
//        Number mvalue = this.metricVEligible? new Number(1): new Number(0);
//
//        String fieldObsFieldName = null;
//        String fieldIname = null;
//        NameValuePairs nvpmm = new NameValuePairs();
//        Connection connection = null;
//        String[] userDefNames =null;
//        
//        try {
//            connection = LoganLibUiUtil.getSystemConnection();
//            //generate user defined fieldIname and fieldObsFieldName for new 
//            //added field each time, these names are automatically generate 
//            //from database which are first available use
//            userDefNames = LoganSQLUtil.getUserDefFieldNames(connection);
//        } 
//        catch (SQLException se)
//        {
//            s_log.warning("Error in retriving fieldIname and fieldObsFieldName: ", se);
//        } finally {
//          JDBCUtil.close(connection); // return it to the system pool
//        }
//        
//        if (userDefNames != null && userDefNames.length == 2)
//        { 
//            fieldIname = userDefNames[0];
//            fieldObsFieldName = userDefNames[1];
//        }
//        
//        if (fieldIname != null)
//        {
//            nvpmm.setAttribute("FieldIname", fieldIname);
//            nvpmm.setAttribute("FieldDname", fieldDname);
//            nvpmm.setAttribute("FieldFieldtype", new Number(1));
//            nvpmm.setAttribute("FieldAuthor", fieldAuthor);
//            nvpmm.setAttribute("FieldMaxsize", fieldMaxsize);
//            
//            if (DATATYPE_NUMBER.equalsIgnoreCase(fieldDatatype)) {
//                nvpmm.setAttribute("FieldDatatype", fieldDatatype);
//            } else if (DATATYPE_TEXT.equalsIgnoreCase(fieldDatatype)) {
//                nvpmm.setAttribute("FieldDatatype", DATATYPE_STRING);
//            }
//                
//            nvpmm.setAttribute("FieldDescription", fieldDescription);
//            nvpmm.setAttribute("FieldIsSystem", new Number(0));
//            nvpmm.setAttribute("FieldIsPrimary", new Number(1));
//            nvpmm.setAttribute("FieldMetricKeyEligible", mkey);
//            nvpmm.setAttribute("FieldMetricValueEligible", mvalue);
//            nvpmm.setAttribute("FieldObsFieldName", fieldObsFieldName);
//            
//            Row iRow = voRef.createAndInitRow(nvpmm);
//            voRef.insertRow(iRow);
//            if (s_log.isFinestEnabled())
//                s_log.finest("New COMMON Field INSERT with FieldDname -> " +
//                             fieldDname);
//        } else {
//            s_log.warning("Error: Missing fieldIname for creating field ->" +
//                          fieldDname);
//        }
    }

    /**
     * Handle the update operation here.
     *  <code>
     * FieldDname = FieldDname<br/>
     * FieldMaxsize = FieldMaxsize<br/>
     * FieldDatatype = FieldDatatype<br/>
     * FieldMetricKeyEligible = metricKey<br/>
     * FieldMetricValueEligible = metricValue.</code> 
     */
    public void handleUpdateOperation() {
      //TODO JPA
//        Object values[] = { fieldIname };
//        Key key = new Key(values);
//        LoganAMImpl am = LoganLibUiUtil.getAppModule();
//        ViewObjectImpl voRef = am.getEmLoganCommonFieldsVO();
//
//        Row[] uRows = voRef.findByKey(key, 1);
//
//        if (uRows != null && uRows.length > 0)
//        {
//            Row uRow = uRows[0];
//            
//            if(s_log.isFinestEnabled())
//            {
//                StringBuilder  sb = new StringBuilder();
//                sb.append("Field being EDITED - PK FieldDname " + fieldDname);
//                sb.append(". ");
//                s_log.finest(sb.toString());
//            }
//            
//            Number mkey = this.metricKEligible? new Number(1): new Number(0);
//            Number mvalue = this.metricVEligible? new Number(1): new Number(0);
//
//            uRow.setAttribute("FieldDname", fieldDname);
//            uRow.setAttribute("FieldMaxsize", fieldMaxsize);
//            
//            if (DATATYPE_NUMBER.equalsIgnoreCase(fieldDatatype)) {
//                uRow.setAttribute("FieldDatatype", fieldDatatype);
//            } else if (DATATYPE_TEXT.equalsIgnoreCase(fieldDatatype)) {
//                uRow.setAttribute("FieldDatatype", DATATYPE_STRING);
//            }
//            
//            uRow.setAttribute("FieldDescription", fieldDescription);
//            uRow.setAttribute("FieldMetricKeyEligible", mkey);
//            uRow.setAttribute("FieldMetricValueEligible", mvalue);
//        }
    }

    public void handleDeleteOperation() {
        //delete field 
    }

    public void setParentReferenceKeys(List<KeyValuePairObj> rks) {
    }

    public String getPrimaryKeyAsString() {
        return this.fieldIname;
    }
    
    public static String[] getDataTypeSOCList()
    {
        return dataTypeSOCList;
    }

    public boolean isFieldDatatypeNumber()
    {
        return DATATYPE_NUMBER.equals(fieldDatatype);
    }

    public boolean isFieldDatatypeText()
    {
        return DATATYPE_TEXT.equals(fieldDatatype);
    }

    public void setFieldIname(String fieldIname)
    {
        this.fieldIname = fieldIname;
    }

    public String getFieldIname()
    {
        return fieldIname;
    }

    public void setFieldDname(String fieldDname)
    {
        this.fieldDname = fieldDname;
    }

    public String getFieldDname()
    {
        return fieldDname;
    }

    public void setFieldType(Number fieldType)
    {
        this.fieldType = fieldType;
    }

    public Number getFieldType()
    {
        return fieldType;
    }

    public void setFieldDescription(String fieldDescription)
    {
        this.fieldDescription = fieldDescription;
    }

    public String getFieldDescription()
    {
        return fieldDescription;
    }

    public void setSystemDefined(boolean systemDefined)
    {
        this.systemDefined = systemDefined;
    }

    public boolean isSystemDefined()
    {
        return systemDefined;
    }

    public void setPrimaryField(boolean primaryField)
    {
        this.primaryField = primaryField;
    }

    public boolean isPrimaryField()
    {
        return primaryField;
    }

    public void setIsSystem(Number isSystem)
    {
        this.isSystem = isSystem;
    }

    public Number getIsSystem()
    {
        return isSystem;
    }

    public void setIsPrimary(Number isPrimary)
    {
        this.isPrimary = isPrimary;
    }

    public Number getIsPrimary()
    {
        return isPrimary;
    }

    public void setFieldCeeName(String fieldCeeName)
    {
        this.fieldCeeName = fieldCeeName;
    }

    public String getFieldCeeName()
    {
        return fieldCeeName;
    }

    public void setFieldDatatype(String fieldDatatype)
    {
        this.fieldDatatype = fieldDatatype;
    }

    public String getFieldDatatype()
    {
        return fieldDatatype;
    }

    public void setFieldAuthor(String fieldAuthor) {
        this.fieldAuthor = fieldAuthor;
    }

    public String getFieldAuthor() {
        return fieldAuthor;
    }

    public void setFieldMaxsize(Number fieldMaxsize) {
        this.fieldMaxsize = fieldMaxsize;
    }

    public Number getFieldMaxsize() {
        return fieldMaxsize;
    }

    public void setFieldObsName(String fieldObsName) {
        this.fieldObsName = fieldObsName;
    }

    public String getFieldObsName() {
        return fieldObsName;
    }

    public String getEditablePropertiesAsString() {
        return null;
    }

    public boolean isObjInstanceOfSameClass(Object o) {
        return false;
    }
    
    public void setfieldMetricKeyEligible(Number metricKEligible)
    {
        this.fieldMetricKeyEligible = (this.metricKEligible ? new Number(1):
                                    new Number(0));
    }

    public Number getfieldMetricKeyEligible()
    {
        return fieldMetricKeyEligible;
    
    }

    public void setfieldMetricValueEligible(Number metricVEligible)
    {
        this.fieldMetricValueEligible = (this.metricVEligible ? new Number(1):
                                    new Number(0));
    }

    public Number getfieldMetricValueEligible()
    {
        return fieldMetricValueEligible;
    }

    public void setMetricKEligible(boolean metricKEligible)
    {
        this.metricKEligible = metricKEligible;
        this.fieldMetricKeyEligible = (this.metricKEligible ? new Number(1):
                                       new Number(0));
    }
    
    public boolean isMetricKEligible()
    {
        this.metricKEligible = false;
        if (this.fieldMetricKeyEligible != null &&
            this.fieldMetricKeyEligible.intValue() == 1)
        {
            this.metricKEligible = true;
        }
        return metricKEligible;
    }
    
    public void setMetricVEligible(boolean metricVEligible)
    {
        this.metricVEligible = metricVEligible;
        this.fieldMetricValueEligible = (this.metricVEligible ? new Number(1):
                                  new Number(0));
    }
    
    public boolean isMetricVEligible()
    {
        this.metricVEligible = false;
        if (this.fieldMetricValueEligible != null &&
            this.fieldMetricValueEligible.intValue() == 1)
        {
            this.metricVEligible = true;
        }
        return metricVEligible;
    }
    
}
