/* $Header: emcore/jsrc/core/CoreLoganalyticsUi/src/oracle/sysman/core/loganalytics/ui/source/LoganSourceExtFieldsSetting.java /st_emgc_pt-13.1mstr/8 2014/02/20 21:56:45 zudeng Exp $ */

/* Copyright (c) 2002, 2014, Oracle and/or its affiliates. 
All rights reserved.*/

/*
   DESCRIPTION
   Log analytics Source Details extended fields settings bean

   PRIVATE CLASSES
    <list of private classes defined - with one-line descriptions>

   NOTES
    <other useful comments, qualificationsf, etc.>
   
    MODIFIED    (MM/DD/YY)
    vivsharm    01/23/14 - make it comparable on new field iname
    zudeng      01/09/14 - get datatype from em_logan_common_fields
    rban        01/02/14 - Show display field name
    vivsharm    01/04/13 - log Analytics
 */

/**
 *  @version $Header: emcore/jsrc/core/CoreLoganalyticsUi/src/oracle/sysman/core/loganalytics/ui/source/LoganSourceExtFieldsSetting.java /st_emgc_pt-13.1mstr/8 2014/02/20 21:56:45 zudeng Exp $
 *  @author  vivsharm
 *  @since   release specific (what release of product did this appear in)
 */

package oracle.saas.logan.view.source;

import java.util.ArrayList;
import java.util.List;

import oracle.jbo.Key;
import oracle.jbo.NameValuePairs;
import oracle.jbo.Row;
import oracle.jbo.domain.Number;
import oracle.jbo.server.ViewObjectImpl;

import oracle.saas.logan.util.LoganLibUiUtil;
import oracle.saas.logan.util.Logger;
import oracle.saas.logan.util.UiUtil;
import oracle.saas.logan.view.BaseDataPersistenceHandler;
import oracle.saas.logan.view.KeyValuePairObj;


public class LoganSourceExtFieldsSetting extends BaseDataPersistenceHandler
    implements Cloneable, Comparable<LoganSourceExtFieldsSetting>
{
    public static final String TEXT = "TEXT";
    public static final String NUMBER = "NUMBER";
    public static final String STRING = "STRING";

    private static final String[] dataTypeSOCList =
    { TEXT, NUMBER };

    private Number sefSourceId;
    private Number seffId;
    private Number seffSefId;
    private String seffNewFieldIname;
    private String sefBaseFieldIname;
    private String sefBaseFieldDname;
    private Number sefBaseFieldFieldtype;
    private Number sefBaseFieldIsSystem;
    private Number sefBaseFieldIsPrimary;
    private String seffDatatype = TEXT;
    private String seffDatatypeDisplay;
    private Number fieldMetricKeyEligible;
    private Number fieldMetricValueEligible;
    private String seffSavedRegexIname;
    private String sefRegex;
    private String savedRegexDname;
    private String regexContent;
    private String seffNewFieldDname;
    
    private String displayRegex;

    private boolean metricKEligible;
    private boolean metricVEligible;

    private static final Logger s_log =
        Logger.getLogger(LoganSourceExtFieldsSetting.class.getName());

    private static final String SeffId = "SeffId";
    private static final String SeffSefId = "SeffSefId";
    private static final String SeffNewFieldIname = "SeffNewFieldIname";
    private static final String SeffDatatype = "SeffDatatype";
    private static final String FieldMetricKeyEligible = "FieldMetricKeyEligible";
    private static final String FieldMetricValueEligible = "FieldMetricValueEligible";
    private static final String SeffSavedRegexIname =
        "SeffSavedRegexIname";
    private static final String SefSourceId = "SefSourceId";
    private static final String SefRegex = "SefRegex";
    private static final String SefBaseFieldIname = "SefBaseFieldIname";
    private static final String SefBaseFieldDname = "SefBaseFieldDname";
    private static final String SefBaseFieldFieldtype =
        "SefBaseFieldFieldtype";
    private static final String SefBaseFieldIsSystem =
        "SefBaseFieldIsSystem";
    private static final String SefBaseFieldIsPrimary =
        "SefBaseFieldIsPrimary";

    private static final String RegexIname = "RegexIname";
    private static final String RegexDname = "RegexDname";
    private static final String RegexContent = "RegexContent";

    private static final String FieldFieldtype = "FieldFieldtype";

    private String[] parentRefKeyNames =
    { SeffSefId };

    private static final String ParentFK_SefId = "SefId";

    public LoganSourceExtFieldsSetting(Number sefSourceId,
                                       Number seffSefId, Number seffId,
                                       String seffNewFieldIname,
                                       String sefBaseFieldIname,
                                       String sefBaseFieldDname,
                                       Number sefBaseFieldFieldtype,
                                       Number sefBaseFieldIsSystem,
                                       Number sefBaseFieldIsPrimary,
                                       String seffDatatype,
                                       Number fieldMetricKeyEligible,
                                       Number fieldMetricValueEligible,
                                       String seffSavedRegexIname,
                                       String savedRegexDname,
                                       String regexContent,
                                       String sefRegex,
                                       String seffNewFieldDname)
    {
        super();
        this.sefSourceId = sefSourceId;
        this.seffSefId = seffSefId;
        this.seffId = seffId;
        this.seffNewFieldIname = seffNewFieldIname;
        this.sefBaseFieldIname = sefBaseFieldIname;
        this.sefBaseFieldDname = sefBaseFieldDname;
        this.sefBaseFieldFieldtype = sefBaseFieldFieldtype;
        this.sefBaseFieldIsSystem = sefBaseFieldIsSystem;
        this.sefBaseFieldIsPrimary = sefBaseFieldIsPrimary;
        this.seffDatatypeDisplay = UiUtil.getUiString(TEXT);
        this.seffDatatype = TEXT;
        if (NUMBER.equalsIgnoreCase(seffDatatype))
        {
            this.seffDatatypeDisplay = UiUtil.getUiString(NUMBER);
            this.seffDatatype = NUMBER;
        }
        this.fieldMetricKeyEligible = new Number(0);
        this.fieldMetricValueEligible = new Number(0);
        this.metricKEligible = false;
        if (fieldMetricKeyEligible != null &&
            fieldMetricKeyEligible.intValue() == 1)
        {
            this.fieldMetricKeyEligible = new Number(1);
            this.metricKEligible = true;
        }
        this.metricVEligible = false;
        if (fieldMetricValueEligible != null &&
            fieldMetricValueEligible.intValue() == 1)
        {
            this.fieldMetricValueEligible = new Number(1);
            this.metricVEligible = true;
        }
        this.seffSavedRegexIname = seffSavedRegexIname;
        this.savedRegexDname = savedRegexDname;

        this.regexContent = regexContent;
        this.sefRegex = sefRegex;
        
        this.seffNewFieldDname = seffNewFieldDname;
    }

    public LoganSourceExtFieldsSetting(Row vorow)
    {
        super();
        if (vorow == null)
            return;
        //TODO JPA
//        this.sefSourceId = (Number) vorow.getAttribute(SefSourceId);
//        this.seffSefId = (Number) vorow.getAttribute(SeffSefId);
//        this.seffId = (Number) vorow.getAttribute(SeffId);
//        this.seffNewFieldIname =
//                (String) vorow.getAttribute(SeffNewFieldIname);
//        this.sefBaseFieldIname =
//                (String) vorow.getAttribute(SefBaseFieldIname);
//        this.sefBaseFieldDname =
//                (String) vorow.getAttribute(SefBaseFieldDname);
//        this.sefBaseFieldFieldtype =
//                (Number) vorow.getAttribute(SefBaseFieldFieldtype);
//        this.sefBaseFieldIsSystem =
//                (Number) vorow.getAttribute(SefBaseFieldIsSystem);
//        this.sefBaseFieldIsPrimary =
//                (Number) vorow.getAttribute(SefBaseFieldIsPrimary);
//        //this.seffDatatype = (String) vorow.getAttribute(SeffDatatype);
//        this.seffDatatype = new FieldDAO(LoganLibUiUtil.getReposDBConnection()).getDataType(this.sefBaseFieldIname);
//        if (STRING.equalsIgnoreCase(seffDatatype))
//        {
//            this.seffDatatype = TEXT;
//            this.seffDatatypeDisplay = UiUtil.getUiString(TEXT);
//        }
//        else if (NUMBER.equalsIgnoreCase(seffDatatype))
//        {
//            this.seffDatatypeDisplay = UiUtil.getUiString(NUMBER);
//        }
//        this.fieldMetricKeyEligible =
//                (Number) vorow.getAttribute(FieldMetricKeyEligible);
//        if (fieldMetricKeyEligible != null &&
//            fieldMetricKeyEligible.intValue() == 1)
//        {
//            this.metricKEligible = true;
//        }
//        
//        this.fieldMetricValueEligible =
//                (Number) vorow.getAttribute(FieldMetricValueEligible);
//        if (fieldMetricValueEligible != null &&
//            fieldMetricValueEligible.intValue() == 1)
//        {
//            this.metricVEligible = true;
//        }
//        this.seffSavedRegexIname =
//                (String) vorow.getAttribute(SeffSavedRegexIname);
//        this.savedRegexDname = (String) vorow.getAttribute(RegexDname);
//
//        this.regexContent = (String) vorow.getAttribute(RegexContent);
//        this.sefRegex = (String) vorow.getAttribute(SefRegex);
//        
//        this.seffNewFieldDname = LoganLibUiUtil.getFieldDnameByIname(this.seffNewFieldIname);
    }

    public Object clone()
    {
        LoganSourceExtFieldsSetting setting =
            new LoganSourceExtFieldsSetting(sefSourceId, seffSefId, seffId,
                                            seffNewFieldIname,
                                            sefBaseFieldIname,
                                            sefBaseFieldDname,
                                            sefBaseFieldFieldtype,
                                            sefBaseFieldIsSystem,
                                            sefBaseFieldIsPrimary,
                                            seffDatatype,
                                            fieldMetricKeyEligible,
                                            fieldMetricValueEligible,
                                            seffSavedRegexIname,
                                            savedRegexDname, regexContent,
                                            sefRegex, seffNewFieldDname);
        return setting;
    }

    public static boolean isCommonFieldForSource(Row cRow)
    {
        if (cRow == null)
            return false;
        Number fieldType = (Number) cRow.getAttribute(FieldFieldtype);
        if(s_log.isFinestEnabled())
            s_log.finest("Found the referenced COMMON field for LOG EXT FIELD SETTINGS  type = " +fieldType);
        //if(fieldType != null && fieldType.equals(SOURCE_EXT_FIELD_TYPE))
        //    return true;
        if (fieldType != null)
            return true;
        return false;
    }

    /**
     * Handle the Insert operation here.
     * <code>
     * SeffNewFieldIname = SeffNewFieldIname<br/>
     * SeffId = SeffId<br/>
     * SeffSefId = SeffSefId<br/>
     * SeffDatatype = SeffDatatype<br/>
     * FieldMetricKeyEligible = mkey<br/>
     * FieldMetricValueEligible = mvalue<br/>
     * SeffSavedRegexIname = SeffSavedRegexIname</code>
     * 
     */
    public void handleInsertOperation()
    {
        Number mkey = this.metricKEligible? new Number(1): new Number(0);
        Number mvalue = this.metricVEligible? new Number(1): new Number(0);
        
        // then corresponding record into ExtFields
        ViewObjectImpl voRef = getViewObjectImpl();
        NameValuePairs nvpmm = new NameValuePairs();
        // Note the Id is generated only when we create a new row
        this.seffId = LoganLibUiUtil.getNextSequenceId("EM_LOGAN_EXTFIELDS_SEQ");
        // The Common field's handleInsertOperation() will be invoked before 
        // this so that when we use the "seffNewFieldIname" here, we will not 
        // get a FK violated error
        nvpmm.setAttribute(SeffNewFieldIname, seffNewFieldIname);
        nvpmm.setAttribute(SeffId, seffId);
        nvpmm.setAttribute(SeffSefId, this.seffSefId);
//        nvpmm.setAttribute(SeffDatatype, seffDatatype);
        nvpmm.setAttribute(FieldMetricKeyEligible, mkey);
        nvpmm.setAttribute(FieldMetricValueEligible, mvalue);
        
        if (seffSavedRegexIname != null && regexContent != null &&
            regexContent.trim().length() > 0)
            nvpmm.setAttribute(SeffSavedRegexIname, seffSavedRegexIname);
 
        Row iRow = voRef.createAndInitRow(nvpmm);
        voRef.insertRow(iRow);
        
        if(s_log.isFinestEnabled())
        {
            StringBuilder  sb = new StringBuilder();
            sb.append("LOG EXT FIELD SETTINGS  created a row to INSERT: [");
            sb.append("seffId = ").append(seffId);
            sb.append(",\n\t ");
            sb.append("seffSefId = ").append(seffSefId);
            sb.append(",\n\t ");
            sb.append("seffNewFieldIname = ").append(seffNewFieldIname);
            sb.append(",\n\t ");
            sb.append("seffSavedRegexIname = ").append(seffSavedRegexIname);
            sb.append("]");
            s_log.finest(sb.toString()+"\n");
        }
    }

    /**
     * Handle the update operation here.
     * <code>
     * SeffDatatype = SeffDatatype<br/>
     * FieldMetricKeyEligible = metricKEligible ? 1:0<br/>
     * FieldMetricValueEligible = metricVEligible? 1:0<br/>
     * SeffSavedRegexIname = SeffSavedRegexIname</code>
     * 
     */
    public void handleUpdateOperation()
    {
        Object values[] =
        { this.seffSefId, this.seffNewFieldIname };
        Key key = new Key(values);
        Row[] uRows = getViewObjectImpl().findByKey(key, 1);

        if (uRows != null && uRows.length > 0)
        {
            Row uRow = uRows[0];
            if(s_log.isFinestEnabled())
            {
                StringBuilder  sb = new StringBuilder();
                sb.append("LOG EXT FIELD SETTINGS found row for UPDATE. Field details: [");
                sb.append(seffSefId);
                sb.append(", ");
                sb.append(seffNewFieldIname);
                sb.append("]");
                s_log.finest(sb.toString());
            }
//            uRow.setAttribute(SeffDatatype, seffDatatype);
            uRow.setAttribute(FieldMetricKeyEligible,
                              (this.metricKEligible? new Number(1):
                               new Number(0)));
            uRow.setAttribute(FieldMetricValueEligible,
                              (this.metricVEligible? new Number(1):
                               new Number(0)));
            if (seffSavedRegexIname != null)
                uRow.setAttribute(SeffSavedRegexIname,
                                  seffSavedRegexIname);
        }
    }

    /**
     * NO NEED TO DELETE : since parent row has cascade delete and there is no 
     * use case to delete the child without  deleting the parent (as soon as 
     * you change the regex it means DELETE + INSERT for parent - and then the 
     * cascade  delete on FieldSettings automatically deletes that row).
     */
    public void handleDeleteOperation()
    {
        Object values[] =
        { this.seffSefId, this.seffNewFieldIname };
        Key key = new Key(values);
        Row[] dRows = getViewObjectImpl().findByKey(key, 1);
        if (dRows != null && dRows.length > 0)
        {
            if(s_log.isFinestEnabled())
            {
                StringBuilder  sb = new StringBuilder();
                sb.append("LOG EXT FIELD SETTINGS found row for DELETE. Field details: [");
                sb.append(seffSefId);
                sb.append(", ");
                sb.append(seffNewFieldIname);
                sb.append("]");
                s_log.finest(sb.toString());
            }
        }
    }

    private ViewObjectImpl getViewObjectImpl()
    {
        //TODO JPA
//        LoganAMImpl am = LoganLibUiUtil.getAppModule();
//        return am.getEmLoganExtfieldsVO();
        return null;
    }

    public String[] getParentReferenceKeyNames()
    {
        return parentRefKeyNames;
    }
    
    /**
     * Return a List of KeyValuePairObj which can be passed
     * directly to any dependent child objects so that they can set their 
     * reference values for the parent. We will call setParentReferenceKeys
     * to set the List of KeyValuePairObj to each child bject returned in
     * getDependentChildObjsList()
     * 
     * @return List of KeyValuePairObj
     */
    public List<KeyValuePairObj> getReferenceKeysForDependents()
    {
        List<KeyValuePairObj> rks = new ArrayList<KeyValuePairObj>(4);
        rks.add(new KeyValuePairObj(SeffId, this.seffId));
        rks.add(new KeyValuePairObj(SefSourceId, this.sefSourceId));
        return rks;        
    }

    public void setParentReferenceKeys(List<KeyValuePairObj> rks)
    {
        if (rks == null || rks.size() <= 0)
            return;

        for (KeyValuePairObj kvpo: rks)
        {
            if (ParentFK_SefId.equals(kvpo.getKey()))
            {
                this.seffSefId = (Number) kvpo.getValue();
            }
            else if (SefSourceId.equals(kvpo.getKey()))
            {
                this.sefSourceId = (Number) kvpo.getValue();
            }
            else if (SefBaseFieldIname.equals(kvpo.getKey()))
            {
                this.sefBaseFieldIname = (String) kvpo.getValue();
            }
            else if (SefRegex.equals(kvpo.getKey()))
            {
                this.sefRegex = (String) kvpo.getValue();
            }
        }
    }

    public String getPrimaryKeyAsString()
    {
        StringBuilder sb = new StringBuilder();
        sb.append(this.seffSefId).append("_");
        sb.append(this.seffNewFieldIname).append("_");
        return sb.toString();
    }

    public boolean isObjInstanceOfSameClass(Object o)
    {
        return (o != null && o instanceof LoganSourceExtFieldsSetting);
    }
    
    /**
     * All properties of the object that are exposed on the Ui and
     * can be updated by the user should be used to construct this
     * representative string here.
     * This will be used to check if the user has edited any of the properties
     * @return object represeting the updatable properties as String
     */
    public String getEditablePropertiesAsString()
    {
        StringBuilder sb = new StringBuilder();
        sb.append(this.sefBaseFieldIname).append("_");
        sb.append(this.sefRegex).append("_");
        sb.append(this.seffNewFieldIname).append("_");
        sb.append(this.seffDatatype).append("_");
        sb.append(this.metricKEligible).append("_");
        sb.append(this.metricVEligible).append("_");
        sb.append(this.seffSavedRegexIname).append("_");
        return sb.toString();
    }

    public void setSefSourceId(Number sefSourceId)
    {
        this.sefSourceId = sefSourceId;
    }

    public Number getSefSourceId()
    {
        return sefSourceId;
    }

    public void setSeffId(Number seffId)
    {
        this.seffId = seffId;
    }

    public Number getSeffId()
    {
        return seffId;
    }

    public void setSeffSefId(Number seffSefId)
    {
        this.seffSefId = seffSefId;
    }

    public Number getSeffSefId()
    {
        return seffSefId;
    }

    public void setSeffNewFieldIname(String seffNewFieldIname)
    {
        this.seffNewFieldIname = seffNewFieldIname;
    }

    public String getSeffNewFieldIname()
    {
        return seffNewFieldIname;
    }

    public void setSefBaseFieldIname(String sefBaseFieldIname)
    {
        this.sefBaseFieldIname = sefBaseFieldIname;
    }

    public String getSefBaseFieldIname()
    {
        return sefBaseFieldIname;
    }

    public void setSefBaseFieldDname(String sefBaseFieldDname)
    {
        this.sefBaseFieldDname = sefBaseFieldDname;
    }

    public String getSefBaseFieldDname()
    {
        return sefBaseFieldDname;
    }

    public void setSeffDatatype(String seffDatatype)
    {
        this.seffDatatype = seffDatatype;
    }

    public String getSeffDatatype()
    {
        return seffDatatype;
    }

    public void setSeffSavedRegexIname(String seffSavedRegexIname)
    {
        this.seffSavedRegexIname = seffSavedRegexIname;
    }

    public String getSeffSavedRegexIname()
    {
        return seffSavedRegexIname;
    }

    public void setSefRegex(String sefRegex)
    {
        this.sefRegex = sefRegex;
    }

    public String getSefRegex()
    {
        return sefRegex;
    }

    public void setSavedRegexDname(String savedRegexDname)
    {
        this.savedRegexDname = savedRegexDname;
    }

    public String getSavedRegexDname()
    {
        return savedRegexDname;
    }

    public void setRegexContent(String regexContent)
    {
        this.regexContent = regexContent;
    }

    public String getRegexContent()
    {
        return regexContent;
    }

    public void setSeffDatatypeDisplay(String seffDatatypeDisplay)
    {
        this.seffDatatypeDisplay = seffDatatypeDisplay;
    }

    public String getSeffDatatypeDisplay()
    {
        return seffDatatypeDisplay;
    }

    public static String[] getDataTypeSOCList()
    {
        return dataTypeSOCList;
    }

    public void setSefBaseFieldFieldtype(Number sefBaseFieldFieldtype)
    {
        this.sefBaseFieldFieldtype = sefBaseFieldFieldtype;
    }

    public Number getSefBaseFieldFieldtype()
    {
        return sefBaseFieldFieldtype;
    }

    public void setSefBaseFieldIsSystem(Number sefBaseFieldIsSystem)
    {
        this.sefBaseFieldIsSystem = sefBaseFieldIsSystem;
    }

    public Number getSefBaseFieldIsSystem()
    {
        return sefBaseFieldIsSystem;
    }

    public void setSefBaseFieldIsPrimary(Number sefBaseFieldIsPrimary)
    {
        this.sefBaseFieldIsPrimary = sefBaseFieldIsPrimary;
    }

    public Number getSefBaseFieldIsPrimary()
    {
        return sefBaseFieldIsPrimary;
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

    public void setSeffNewFieldDname(String seffNewFieldDname) {
        this.seffNewFieldDname = seffNewFieldDname;
    }

    public String getSeffNewFieldDname() {
        //TODO JPA
//        if (seffNewFieldDname == null)
//            seffNewFieldDname =
//                    LoganLibUiUtil.getFieldDnameByIname(this.getSeffNewFieldIname());
//        return seffNewFieldDname;
        return null;
    }

    public void setDisplayRegex(String displayRegex) {
        this.displayRegex = displayRegex;
    }

    public String getDisplayRegex() {
        displayRegex = LoganSourceExtFieldsDefn.convertRegexToDisplay(this.getSefRegex());
        return displayRegex;
    }

    /**
     * compare based on the field iname
     * @param o
     * @return
     */
    public int compareTo(LoganSourceExtFieldsSetting o)
    {
        if(o == null)
            return -1;
        return this.seffNewFieldIname.compareTo(o.seffNewFieldIname);
    }
}
