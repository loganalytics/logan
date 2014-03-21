/* $Header: emcore/jsrc/core/CoreLoganalyticsUi/src/oracle/sysman/core/loganalytics/ui/source/LoganSourceExtFieldsDefn.java /st_emgc_pt-13.1mstr/8 2014/02/20 21:56:45 zudeng Exp $ */

/* Copyright (c) 2002, 2014, Oracle and/or its affiliates. 
All rights reserved.*/

/*
   DESCRIPTION
   Log analytics Source Details extended fields definition bean

   PRIVATE CLASSES
    <list of private classes defined - with one-line descriptions>

   NOTES
    <other useful comments, qualificationsf, etc.>
   
    MODIFIED    (MM/DD/YY)
    zudeng      01/09/14 - use LoganOperatorUtil instead of LoganCommonFieldOperators
    rban        01/02/14 - add extend field for observation
    vivsharm    01/04/13 - log Analytics
 */

/**
 *  @version $Header: emcore/jsrc/core/CoreLoganalyticsUi/src/oracle/sysman/core/loganalytics/ui/source/LoganSourceExtFieldsDefn.java /st_emgc_pt-13.1mstr/8 2014/02/20 21:56:45 zudeng Exp $
 *  @author  vivsharm
 *  @since   release specific (what release of product did this appear in)
 */

package oracle.saas.logan.view.source;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import oracle.jbo.Key;
import oracle.jbo.NameValuePairs;
import oracle.jbo.Row;
import oracle.jbo.domain.ClobDomain;
import oracle.jbo.domain.Number;
import oracle.jbo.server.ViewObjectImpl;

import oracle.saas.logan.util.LoganLibUiUtil;
import oracle.saas.logan.util.LoganUtil;
import oracle.saas.logan.util.Logger;
import oracle.saas.logan.util.UiUtil;
import oracle.saas.logan.view.BaseDataPersistenceHandler;
import oracle.saas.logan.view.IPersistDataOpsHandler;
import oracle.saas.logan.view.KeyValuePairObj;


public class LoganSourceExtFieldsDefn extends BaseDataPersistenceHandler
    implements Cloneable
{
    private Number sefSourceId;
    private Number sefId;
    private String sefBaseFieldIname;
    private String sefBaseFieldLogText;
    private String sefBaseFieldDname;
    private Number sefBaseFieldFieldtype;
    private Number sefBaseFieldIsSystem;
    private Number sefBaseFieldIsPrimary;
    private String sefRegex;
    private ClobDomain sefConvertedRegex;
    private String author;
    
    private String displayRegex;

    private List<LoganSourceExtFieldsSetting> fields =
        new ArrayList<LoganSourceExtFieldsSetting>();

    private static final Logger s_log =
        Logger.getLogger(LoganSourceExtFieldsDefn.class.getName());

    private static final String SefSourceId = "SefSourceId";
    private static final String SefId = "SefId";
    private static final String SefBaseFieldIname = "SefBaseFieldIname";
    private static final String SefBaseFieldLogText = "SefBaseFieldLogText";
    private static final String SefConvertedRegex = "SefConvertedRegex";
    private static final String FieldFieldtype = "FieldFieldtype";
    private static final String FieldIsSystem = "FieldIsSystem";
    private static final String FieldIsPrimary = "FieldIsPrimary";
    private static final String SefRegex = "SefRegex";
    private static final String SefAuthor = "SefAuthor";

    private static final String FieldDname = "FieldDname";
    
    private static final String SAVED_REGEXP_USAGE_IDENTIFIER = ":@";
    private static final String PLAIN_REGEXP_USAGE_IDENTIFIER = ":";
    
    private String[] parentRefKeyNames =
    { SefSourceId };

    private static final String ParentFK_SourceId = "SourceId";
    private static final String ChildFK_SefId = "SefId";
    
    public LoganSourceExtFieldsDefn()
    {
        super();
    }
    
    public LoganSourceExtFieldsDefn(Row vorow)
    {
        super();
        if (vorow == null)
            return;
        Number sefSourceId = (Number) vorow.getAttribute(SefSourceId);
        Number sefId = (Number) vorow.getAttribute(SefId);
        String sefBaseFieldIname =
            (String) vorow.getAttribute(SefBaseFieldIname);
        this.sefBaseFieldLogText = 
            (String) vorow.getAttribute(SefBaseFieldLogText);
        this.sefBaseFieldFieldtype =
                (Number) vorow.getAttribute(FieldFieldtype);
        this.sefBaseFieldIsSystem =
                (Number) vorow.getAttribute(FieldIsSystem);
        this.sefBaseFieldIsPrimary =
                (Number) vorow.getAttribute(FieldIsPrimary);
        String sefRegex = (String) vorow.getAttribute(SefRegex);
        ClobDomain sefConvertedRegex = 
            (ClobDomain)vorow.getAttribute(SefConvertedRegex);
        String author = (String) vorow.getAttribute(SefAuthor);

        String sefBaseFieldDname = (String) vorow.getAttribute(FieldDname);

        this.sefSourceId = sefSourceId;
        this.sefId = sefId;
        this.sefBaseFieldIname = sefBaseFieldIname;
        this.sefRegex = sefRegex;
        this.sefConvertedRegex = sefConvertedRegex;
        this.author = author;

        this.sefBaseFieldDname = sefBaseFieldDname;
    }

    public LoganSourceExtFieldsDefn(Number sefSourceId, Number sefId,
                                    String sefBaseFieldIname,
                                    String sefBaseFieldLogText,
                                    Number sefBaseFieldFieldtype,
                                    Number sefBaseFieldIsSystem,
                                    Number sefBaseFieldIsPrimary,
                                    String sefRegex, ClobDomain sefConvertedRegex,
                                    String author,
                                    String sefBaseFieldDname)
    {
        super();
        this.sefSourceId = sefSourceId;
        this.sefId = sefId;
        this.sefBaseFieldIname = sefBaseFieldIname;
        this.sefBaseFieldLogText = sefBaseFieldLogText;
        this.sefBaseFieldFieldtype = sefBaseFieldFieldtype;
        this.sefBaseFieldIsSystem = sefBaseFieldIsSystem;
        this.sefBaseFieldIsPrimary = sefBaseFieldIsPrimary;
        this.sefRegex = sefRegex;
        this.sefConvertedRegex = sefConvertedRegex;
        this.author = author;

        this.sefBaseFieldDname = sefBaseFieldDname;
    }

    public Object clone()
    {
        LoganSourceExtFieldsDefn defn =
            new LoganSourceExtFieldsDefn(sefSourceId, sefId,
                                         sefBaseFieldIname,
                                         sefBaseFieldLogText,
                                         sefBaseFieldFieldtype,
                                         sefBaseFieldIsSystem,
                                         sefBaseFieldIsPrimary, 
                                         sefRegex, sefConvertedRegex,
                                         author, sefBaseFieldDname);
        if (fields != null)
        {
            List<LoganSourceExtFieldsSetting> ss =
                new ArrayList<LoganSourceExtFieldsSetting>();
            for (LoganSourceExtFieldsSetting p: fields)
            {
                ss.add((LoganSourceExtFieldsSetting) p.clone());
            }
            defn.setFields(ss);
        }
        return defn;
    }

    /**
     * Handle the Insert operation here.
     * <code>
     * SefId = sefId<br/>
     * SefSourceId = sefSourceId<br/>
     * SefRegex = sefRegex<br/>
     * SefConvertedRegex = sefConvertedRegex<br/>
     * SefBaseFieldIname = sefBaseFieldIname<br/>
     * SefBaseFieldLogText = sefBaseFieldLogText<br/>
     * SefAuthor = sefAuthor</code>
     * 
     */
    public void handleInsertOperation()
    {
        ViewObjectImpl voRef = getViewObjectImpl();
        NameValuePairs nvpmm = new NameValuePairs();
        // Note the patternId is generated only when we create a new row
        this.sefId = LoganLibUiUtil.getNextSequenceId("EM_LOGAN_EXTFIELD_DEFN_SEQ");
        nvpmm.setAttribute(SefId, sefId);
        nvpmm.setAttribute(SefSourceId, this.sefSourceId);
        nvpmm.setAttribute(SefRegex, this.sefRegex);
        nvpmm.setAttribute(SefConvertedRegex, this.sefConvertedRegex);
        nvpmm.setAttribute(SefBaseFieldIname, this.sefBaseFieldIname);
        nvpmm.setAttribute(SefBaseFieldLogText, this.sefBaseFieldLogText);

        nvpmm.setAttribute(SefAuthor, author);
        Row iRow = voRef.createAndInitRow(nvpmm);
        voRef.insertRow(iRow);
        
        if(s_log.isFinestEnabled())
        {
            StringBuilder  sb = new StringBuilder();
            sb.append("LOG EXT FIELD DEFN BEAN created a row to INSERT:");
            sb.append("\n\t sefBaseFieldIname ").append(sefBaseFieldIname);
            sb.append(",\n\t sefSourceId = ").append(sefSourceId);
            sb.append(",\n\t defn = ").append(sefRegex);
            sb.append(",\n\t converted = ");
            if(sefConvertedRegex != null)
                sb.append(sefConvertedRegex.getSubString(0, (int) sefConvertedRegex.getLength()));
            sb.append(",\n\t sefId = ").append(sefId);
            s_log.finest(sb.toString()+"\n");
        }
    }

    public void handleUpdateOperation()
    {
        if(s_log.isFinestEnabled())
            s_log.finest("LOG SOURCE Ext Fields Defn has only PKs exposed on UI" +
                      " so any change there is DELETE INSERT - no updates");
    }

    /**
     * Handle the delete operation here. 
     */
    public void handleDeleteOperation()
    {
        if (fields != null && fields.size() > 0)
        {
            Iterator<LoganSourceExtFieldsSetting> i = fields.iterator();
            while (i.hasNext())
            {
                // must be called before you can call i.remove()
                IPersistDataOpsHandler s = i.next();

                // Parent is going away so all children must first get removed
                s.handleDeleteOperation();

                // remove the child object from the list so that it does not
                // show up in the successive processSubmit phase for child objs
                i.remove();
            }
        }
        // do my delete operation after all children are deleted
        Object values[] =
        { this.sefSourceId, this.sefBaseFieldIname, this.sefRegex };
        Key key = new Key(values);
        Row[] dRows = getViewObjectImpl().findByKey(key, 1);
        if (dRows != null && dRows.length > 0)
        {
            Row dRow = dRows[0];
            
            if(s_log.isFinestEnabled())
            {
                StringBuilder  sb = new StringBuilder();
                sb.append("LOG EXT FIELD DEFN BEAN found a row to DELETE for sefBaseFieldDname " + sefBaseFieldDname);
                sb.append(", defn string = " + sefRegex );
                sb.append(" with NEW sefId (childFK) as " + sefId);
                s_log.finest(sb.toString());
            }
            dRow.remove();
        }
    }

    private ViewObjectImpl getViewObjectImpl()
    {
        //TODO JPA
//        LoganAMImpl am = LoganLibUiUtil.getAppModule();
//        return am.getEmLoganExtfieldDefnVO();
        return null;
    }

    public String[] getParentReferenceKeyNames()
    {
        return parentRefKeyNames;
    }

    public void setParentReferenceKeys(List<KeyValuePairObj> rks)
    {
        if (rks == null || rks.size() <= 0)
            return;

        for (KeyValuePairObj kvpo: rks)
        {
            if (ParentFK_SourceId.equals(kvpo.getKey()))
            {
                this.setSefSourceId((Number) kvpo.getValue());
            }
        }
    }

    public List<KeyValuePairObj> getReferenceKeysForDependents()
    {
        List<KeyValuePairObj> rks = new ArrayList<KeyValuePairObj>(4);
        rks.add(new KeyValuePairObj(ChildFK_SefId, this.sefId));
        rks.add(new KeyValuePairObj(SefSourceId, this.sefSourceId));
        rks.add(new KeyValuePairObj(SefBaseFieldIname,
                                    this.sefBaseFieldIname));
        rks.add(new KeyValuePairObj(SefRegex, this.sefRegex));
        return rks;
    }

    public static String getParentKeyStringSourceId()
    {
        return SefSourceId;
    }

    /**
     * A String representation of the Key column values
     * @return
     */
    public String getPrimaryKeyAsString()
    {
        StringBuilder sb = new StringBuilder();
        sb.append(this.sefSourceId).append("_");
        sb.append(this.sefBaseFieldIname).append("_");
        if(this.sefRegex != null)
            this.sefRegex = this.sefRegex.trim();
        sb.append(this.sefRegex).append("_");
        return sb.toString();
    }

    /**
     * validate if the regex is valid or not
     * @param extFieldDefnRegex
     * @param loganBundle
     * @return errMsg
     */
    public static String validateRegex(String extFieldDefnRegex,
                                       ResourceBundle loganBundle)
    {
        String errMsg = null;
        if (extFieldDefnRegex == null ||
            extFieldDefnRegex.trim().length() == 0)
        {
            errMsg =
                    UiUtil.getUiString("EXTENDED_FIELD_DEFINITION_BLANK_REGEX_ERR");
        }
        else
        {
            boolean isValidRegexString = LoganUtil.validateCurlyBracesInExpr(extFieldDefnRegex);
            if (!isValidRegexString)
                errMsg =
                        UiUtil.getUiString("EXTENDED_FIELD_DEFINITION_INCORRECT_REGEX_ERR");
            
            List<String> fieldStrs = LoganUtil.getFieldsEnclosedByCurlyBraces(extFieldDefnRegex);
            if (fieldStrs != null && fieldStrs.size() > 0)
            {
                HashMap<String, String> fm = new HashMap<String, String>();
                for(String fs : fieldStrs)
                {
                    if(fm.containsKey(fs))
                    {
                        errMsg = UiUtil.getUiString("EXT_FIELD_DEFN_DUPS_ERR");
                        break;
                    }
                    fm.put(fs, " ");
                }
            }

        }
        return errMsg;
    }

    public boolean isObjInstanceOfSameClass(Object o)
    {
        return (o != null && o instanceof LoganSourceExtFieldsDefn);
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
        sb.append(this.sefSourceId).append("_");
        sb.append(this.sefBaseFieldIname).append("_");
        if(this.sefRegex != null)
            this.sefRegex = this.sefRegex.trim();
        sb.append(this.sefRegex).append("_");
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

    public void setSefId(Number sefId)
    {
        this.sefId = sefId;
    }

    public Number getSefId()
    {
        return sefId;
    }

    public void setSefBaseFieldIname(String sefBaseFieldIname)
    { 
        this.sefBaseFieldIname = sefBaseFieldIname;
    }

    public String getSefBaseFieldIname()
    {
        return sefBaseFieldIname;
    }

    public void setSefRegex(String sefRegex)
    { 
        this.sefRegex = convertDisplayRegexToRegex(sefRegex);;
    }

    public String getSefRegex()
    {
        return sefRegex;
    }

    public void setAuthor(String author)
    {
        this.author = author;
    }

    public String getAuthor()
    {
        return author;
    }

    public void setSefBaseFieldDname(String sefBaseFieldDname)
    {
        this.sefBaseFieldDname = sefBaseFieldDname;
    }

    public String getSefBaseFieldDname()
    {
        return sefBaseFieldDname;
    }

    public void clearFields()
    {
        fields.clear();
    }

    public void setFields(List<LoganSourceExtFieldsSetting> fields)
    {
        this.fields = fields;
    }

    public void addField(LoganSourceExtFieldsSetting f)
    {
        fields.add(f);
    }

    public List<LoganSourceExtFieldsSetting> getFields()
    {
        return fields;
    }

    /**
     * Parse and extract fields from extend field definition regex and update 
     * extend fields, saved regular expression and unsaved regular expression
     * Ex. 1<br>
     *   input = "sample string with {FIELD1} and {FIELD2:@Client_ID}";<br>
     *   it will be 2 String elements "FIELD1" and "FIELD2" as extend 
     *   fields and update to LoganSourceExtFieldsSetting, and add Client_ID 
     *   to saved regex map
     * Ex. 2<br>
     *   input = "sample message {FIELD3:[a-zA-Z]+} ";<br>
     *   it will be 1 String element "FIELD3" as extend field and add "[a-zA-Z]+"
     *   to unsaved regex map<br>
     * <br>
     * @param extFieldDefnRegex
     * @param exFieldsmap
     * @param savedRegularExpMap
     * @param unSavedRegExps
     */
    public void parseExtractFieldsFromRegex(String extFieldDefnRegex,
                                            Map<String, LoganSourceExtFieldsSetting> exFieldsmap,
                                            Map<String, LoganSavedRegexBean> savedRegularExpMap,
                                            List<LoganSavedRegexBean> unSavedRegExps)
    {
        //TODO jpa
//        if(unSavedRegExps == null)
//            return;
//        
//        if (extFieldDefnRegex == null ||
//            extFieldDefnRegex.trim().length() == 0) // removeAll
//        {
//            clearFields();
//            return;
//        }
//        //if (extFieldDefnRegex.equals(sefRegex))
//        //    return; // already parsed
//
//        List<String> fieldStrs = LoganUtil.getFieldsEnclosedByCurlyBraces(extFieldDefnRegex);
//        if (fieldStrs != null && fieldStrs.size() > 0)
//        {
//            if (this.fields == null)
//                this.fields = new ArrayList<LoganSourceExtFieldsSetting>();
//            for (String fieldName: fieldStrs)
//            {
//
//                String seffDatatype = null;
//                Number fieldMetricKeyEligible = new Number(0);
//                Number fieldMetricValueEligible = new Number(0);
//                if(sefBaseFieldDname == null || sefBaseFieldDname.trim().length() == 0)
//                {
//                    sefBaseFieldDname = LoganLibUiUtil.getFieldDnameByIname(sefBaseFieldIname);
//                }
//                String seffSavedRegexIname = null, savedRegexDname =
//                    null, regexContent = null;
//                int idxOfColonAt = fieldName.indexOf(SAVED_REGEXP_USAGE_IDENTIFIER);
//                if(idxOfColonAt > 0)
//                {
//                    if(fieldName.lastIndexOf(SAVED_REGEXP_USAGE_IDENTIFIER) == idxOfColonAt)
//                    {
//                        // The field contains a SAVED REGEXP
//                        savedRegexDname = fieldName.substring(idxOfColonAt+2);
//                        fieldName = fieldName.substring(0, idxOfColonAt);
//                        LoganSavedRegexBean lsrb =
//                            savedRegularExpMap.get(savedRegexDname);
//                        if(lsrb != null)
//                        {
//                            seffSavedRegexIname = lsrb.getRegexIname();
//                            regexContent = lsrb.getRegexContent();
//                        }
//                        else
//                        {
//                            lsrb = new LoganSavedRegexBean(author, null,
//                                           null,
//                                           null, savedRegexDname,
//                                           null, savedRegexDname,
//                                           new Number(0));
//                            if(!unSavedRegExps.contains(lsrb))
//                            {
//                                unSavedRegExps.add(lsrb);
//                                savedRegularExpMap.put(savedRegexDname, lsrb);
//                            }
//                        }
//                    }
//                }
//                else
//                {
//                    int idxOfColonOnly = fieldName.indexOf(PLAIN_REGEXP_USAGE_IDENTIFIER);
//                    if(idxOfColonOnly > 0 &&
//                        fieldName.lastIndexOf(PLAIN_REGEXP_USAGE_IDENTIFIER) == idxOfColonOnly)
//                    {
//                        // The field contains a SAVED REGEXP
//                        savedRegexDname = fieldName.substring(idxOfColonOnly+1);
//                        fieldName = fieldName.substring(0, idxOfColonOnly);
//                        LoganSavedRegexBean lsrb =
//                            savedRegularExpMap.get(savedRegexDname);
//                        if(lsrb != null)
//                        {
//                            seffSavedRegexIname = lsrb.getRegexIname();
//                            regexContent = lsrb.getRegexContent();
//                        }
//                        else
//                        {
//                            lsrb = new LoganSavedRegexBean(author, null,
//                                           null,
//                                           null, savedRegexDname,
//                                           null, savedRegexDname,
//                                           new Number(-1));
//                            savedRegularExpMap.put(savedRegexDname, lsrb);
//                        }
//                    }
//                }
//                
//                Row eRow = LoganLibUiUtil.getCommonFieldRow(fieldName);
//                if (eRow == null) {
//                    //ignore captured text that not a valid field
//                    break;
//                }
//                
//                String fieldDname = LoganLibUiUtil.getFieldDnameByIname(fieldName);
//                
//                if (eRow != null &&
//                    !LoganSourceExtFieldsSetting.isCommonFieldForSource(eRow))
//                {
//                    String nameExists =
//                        UiUtil.getUiString("EXT_FIELD_NAME_ERROR");
//                    Object[] dArgs =
//                    { fieldName };
//                    String errm = MessageFormat.format(nameExists, dArgs);
//                    FacesMessage msg = new FacesMessage(errm);
//                    msg.setSeverity(FacesMessage.SEVERITY_ERROR);
//                    throw new ValidatorException(msg);
//                }
//                
//                if (exFieldsmap.containsKey(fieldName))
//                {
//                    LoganSourceExtFieldsSetting ef =
//                        exFieldsmap.get(fieldName);
//                    seffDatatype = ef.getSeffDatatype();
//                    fieldMetricKeyEligible = ef.getfieldMetricKeyEligible();
//                    fieldMetricValueEligible = ef.getfieldMetricValueEligible();
//                }
//                LoganSourceExtFieldsSetting setting =
//                    new LoganSourceExtFieldsSetting(this.sefSourceId,
//                                                    this.sefId, null,
//                                                    fieldName,
//                                                    this.sefBaseFieldIname,
//                                                    this.sefBaseFieldDname,
//                                                    this.sefBaseFieldFieldtype,
//                                                    this.sefBaseFieldIsSystem,
//                                                    this.sefBaseFieldIsPrimary,
//                                                    seffDatatype,
//                                                    fieldMetricKeyEligible,
//                                                    fieldMetricValueEligible,
//                                                    seffSavedRegexIname,
//                                                    savedRegexDname,
//                                                    regexContent,
//                                                    extFieldDefnRegex,
//                                                    fieldDname);
//                this.fields.add(setting);
//            }
//        }
    }
    
    /**
     * Parse all display field names from regex and replace all display names 
     * with internal names
     * 
     * @param regex
     * @return regex
     */
    public static String convertDisplayRegexToRegex(String regex){
        //TODO saas
//        if(regex == null || regex.trim().length() == 0)
//            return regex;
//        
//        List<String> fieldDnames = getFieldsFromRegex(regex);
//        if (fieldDnames != null && fieldDnames.size() >0){
//            for (String fieldDname: fieldDnames) {
//                boolean isValidFieldDname = LoganLibUiUtil.isValidFieldName(fieldDname);
//                if (isValidFieldDname) {
//                    //ignore captured text that not a valid field
//                    break;
//                }
//                
//                String fieldIname = LoganLibUiUtil.getFieldInameByDname(fieldDname);
//                regex = regex.replace(fieldDname, fieldIname);
//            }
//        }
        
        return regex;
    }
    
    /**
     * Parse all internal field names from regex and replace all internal names 
     * with display names
     * 
     * @param regex
     * @return regex
     */
    public static String convertRegexToDisplay(String regex){
        //TODO saas
//        if(regex == null || regex.trim().length() == 0)
//            return regex;
//        
//        List<String> fieldInames = getFieldsFromRegex(regex);
//        if (fieldInames != null && fieldInames.size() >0){
//            for (String fieldIname: fieldInames) {
//                Row eRow = LoganLibUiUtil.getCommonFieldRow(fieldIname);
//                if (eRow == null) {
//                    //ignore captured text that not a valid field
//                    break;
//                }
//                
//                String fieldDname = LoganLibUiUtil.getFieldDnameByIname(fieldIname);
//                regex = regex.replace(fieldIname, fieldDname);
//            }
//        }
//        
        return regex;
    }
    
    /**
     * Parse all field names from regex.
     * Extract all fields info enclosed by curly braces and then capture field 
     * name from fields info
     *  
     * @param regex
     * @return fieldNames
     */
    public static List<String> getFieldsFromRegex(String regex) {
        List<String> fieldStrs = LoganUtil.getFieldsEnclosedByCurlyBraces(regex);
        List<String> fieldNames =  new ArrayList<String>();
        if (fieldStrs != null && fieldStrs.size() > 0)
        {
            for (String fieldName: fieldStrs)
            {
                int idxOfColonAt = fieldName.indexOf(SAVED_REGEXP_USAGE_IDENTIFIER);
                if(idxOfColonAt > 0)
                {
                    if(fieldName.lastIndexOf(SAVED_REGEXP_USAGE_IDENTIFIER) == idxOfColonAt)
                    {
                        // if the field contains a SAVED REGEXP
                        fieldName = fieldName.substring(0, idxOfColonAt);
                    }
                }
                else
                {
                    int idxOfColonOnly = fieldName.indexOf(PLAIN_REGEXP_USAGE_IDENTIFIER);
                    if(idxOfColonOnly > 0 &&
                        fieldName.lastIndexOf(PLAIN_REGEXP_USAGE_IDENTIFIER) == idxOfColonOnly)
                    {
                        // if the field contains a SAVED REGEXP
                        fieldName = fieldName.substring(0, idxOfColonOnly);
                    }
                }
                
                fieldNames.add(fieldName);
            }
        }
        
        return fieldNames;
    }
        
    public void setSefConvertedRegex(ClobDomain sefConvertedRegex)
    {
        this.sefConvertedRegex = sefConvertedRegex;
    }

    public ClobDomain getSefConvertedRegex()
    {
        return sefConvertedRegex;
    }

    public void setSefBaseFieldLogText(String sefBaseFieldLogText)
    {
        this.sefBaseFieldLogText = sefBaseFieldLogText;
    }

    public String getSefBaseFieldLogText()
    {
        return sefBaseFieldLogText;
    }

    public void setDisplayRegex(String displayRegex) {
        this.displayRegex = displayRegex;
    }

    public String getDisplayRegex() {
        displayRegex = convertRegexToDisplay(this.getSefRegex());
        return displayRegex;
    }
}
