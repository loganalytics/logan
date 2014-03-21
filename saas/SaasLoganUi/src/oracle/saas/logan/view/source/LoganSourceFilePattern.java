/* $Header: emcore/jsrc/core/CoreLoganalyticsUi/src/oracle/sysman/core/loganalytics/ui/source/LoganSourceFilePattern.java /st_emgc_pt-13.1mstr/5 2014/01/26 17:07:36 vivsharm Exp $ */

/* Copyright (c) 2002, 2014, Oracle and/or its affiliates. 
All rights reserved.*/

/*
   DESCRIPTION
   Log analytics Source file patterns bean

   PRIVATE CLASSES
    <list of private classes defined - with one-line descriptions>

   NOTES
    <other useful comments, qualificationsf, etc.>
   
    MODIFIED    (MM/DD/YY)
    vivsharm    01/10/14 - new constructor needed for add data direct func
    vivsharm    11/18/13 - add available params support
    vivsharm    01/04/13 - log Analytics
 */

/**
 *  @version $Header: emcore/jsrc/core/CoreLoganalyticsUi/src/oracle/sysman/core/loganalytics/ui/source/LoganSourceFilePattern.java /st_emgc_pt-13.1mstr/5 2014/01/26 17:07:36 vivsharm Exp $
 *  @author  vivsharm
 *  @since   release specific (what release of product did this appear in)
 */

package oracle.saas.logan.view.source;

import java.math.BigDecimal;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import oracle.jbo.Key;
import oracle.jbo.Row;
import oracle.jbo.domain.Number;
import oracle.jbo.server.ViewObjectImpl;

import oracle.saas.logan.model.client.LoganSessionBeanProxy;
import oracle.saas.logan.model.persistance.EmLoganSourcePattern1;
import oracle.saas.logan.model.session.source.LoganSourcePatternSession;
import oracle.saas.logan.util.EMExecutionContext;
import oracle.saas.logan.util.LoganLibUiUtil;
import oracle.saas.logan.util.Logger;
import oracle.saas.logan.view.BaseDataPersistenceHandler;
import oracle.saas.logan.view.KeyValuePairObj;


public class LoganSourceFilePattern extends BaseDataPersistenceHandler
    implements Comparable<LoganSourceFilePattern>, Cloneable
{
    private Number sourceId;
    private Number patternId;
    private String author;
    private String fileName;
    private String fileDesc;
    private String fileParser;
    private String fileParserIName;
    private boolean includePattern = true;
    // each pattern can include one or more params
    // when deleting a pattern all assoc params must be deleted/
    // this Set will keep track of the params within the pattern
    private Set<String> paramsInPattern = new HashSet<String>();
    private String sourceTargetType;
    private Set<LoganMetaAvailParameters> builtInParams;
    
    private static final Logger s_log =
        Logger.getLogger(LoganSourceFilePattern.class.getName());

    private static final String PatternId = "PatternId";
    private static final String PatternText = "PatternText";
    private static final String PatternDescription = "PatternDescription";
    private static final String PatternParserIname = "PatternParserIname";
    private static final String ParserDname = "ParserDname";
    private static final String ParserIname = "ParserIname";
    private static final String PatternSourceId = "PatternSourceId";
    private static final String PatternAuthor = "PatternAuthor";
    private static final String PatternIsInclude = "PatternIsInclude";
    
    private static final String SourceTargetType = "SourceTargetType";

    private static final String ParentFK_SourceId = "SourceId";
   
    /**
     * Constructor used by add data direct page.
     * @param author
     * @param fileName
     * @param fileDesc
     * @param fileParserIName
     * @param sourceTargetType
     */
    public LoganSourceFilePattern(String author, String fileName,
                                  String fileDesc,
                                  String fileParserIName,
                                  String sourceTargetType)
    {
        this.patternId = null;
        this.sourceId = null;
        this.author = author;
        setFileName(fileName);
        this.fileDesc = fileDesc;
        this.fileParser = fileParserIName;
        this.fileParserIName = fileParserIName;
        this.includePattern = true;
        this.sourceTargetType = sourceTargetType;
    }
 
    /**
     * @param vorow
     */
    public LoganSourceFilePattern(Row vorow)
    {
        if (vorow == null)
            return;
        Number patternId = (Number) vorow.getAttribute(PatternId);
        Number pattSrcId = (Number) vorow.getAttribute(PatternSourceId);
        String fileName = (String) vorow.getAttribute(PatternText);
        String fileDesc = (String) vorow.getAttribute(PatternDescription);
        String fileParser = (String) vorow.getAttribute(ParserDname);
        String fileParserIName = (String) vorow.getAttribute(ParserIname);
        String author = (String) vorow.getAttribute(PatternAuthor);

        Number isInc = (Number) vorow.getAttribute(PatternIsInclude);
        boolean includePattern = (isInc.intValue() == 1);

        this.patternId = patternId;
        this.sourceId = pattSrcId;
        this.author = author;
        setFileName(fileName);
        this.fileDesc = fileDesc;
        this.fileParser = fileParser;
        this.fileParserIName = fileParserIName;
        this.includePattern = includePattern;
        sourceTargetType = (String) vorow.getAttribute(SourceTargetType);
        builtInParams = LoganLibUiUtil.getLoganMetaAvailParameters(sourceTargetType);
    }

    /**
     * Constructor used by  Library Log Source management page.
     * @param patternId
     * @param sourceId
     * @param author
     * @param fileName
     * @param fileDesc
     * @param fileParser
     * @param fileParserIName
     * @param includePattern
     * @param sourceTargetType
     */
    public LoganSourceFilePattern(Number patternId, Number sourceId,
                                  String author, String fileName,
                                  String fileDesc, String fileParser,
                                  String fileParserIName,
                                  boolean includePattern,
                                  String sourceTargetType)
    {
        this.patternId = patternId;
        this.sourceId = sourceId;
        this.author = author;
        setFileName(fileName);
        this.fileDesc = fileDesc;
        this.fileParser = fileParser;
        this.fileParserIName = fileParserIName;
        this.includePattern = includePattern;
        this.sourceTargetType = sourceTargetType;
        builtInParams = LoganLibUiUtil.getLoganMetaAvailParameters(sourceTargetType);
    }

    // preserve the params specified in the filename
    private void updateParams()
    {
        //TODO JPA
//        String attrnoesc = LoganLibUiUtil.rmvEscParens(fileName);     
//        ArrayList<String> plist = LoganLibUiUtil.getParams(attrnoesc, builtInParams);
//        paramsInPattern.clear();
//        if (plist != null)
//            paramsInPattern.addAll(plist);
    }

    public boolean isObjInstanceOfSameClass(Object o)
    {
        return (o != null && o instanceof LoganSourceFilePattern);
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
        sb.append(this.fileName).append("_");
        sb.append(this.fileDesc).append("_");
        sb.append(this.fileParserIName).append("_");
        sb.append(this.includePattern).append("_");
        return sb.toString();
    }

    public Object clone()
    {
        return new LoganSourceFilePattern(patternId, sourceId, author,
                                          fileName, fileDesc, fileParser,
                                          fileParserIName, includePattern, 
                                          sourceTargetType);
    }

    public String getFileName()
    {
        return this.fileName;
    }

    public String getFileDesc()
    {
        return this.fileDesc;
    }

    public String getFileParser()
    {
        return this.fileParser;
    }

    public boolean isIncludePattern()
    {
        return this.includePattern;
    }

    public void setFileName(String s)
    {
        this.fileName = s;
        // populate the list of {<param>} within this filename
        this.updateParams();
    }

    public void setFileDesc(String s)
    {
        this.fileDesc = s;
    }

    public void setFileParser(String s)
    {
        this.fileParser = s;
    }

    public void isIncludePattern(boolean b)
    {
        this.includePattern = b;
    }

    /**
     * @param o
     * @return
     */
    public int compareTo(LoganSourceFilePattern o)
    {
        if (this.fileName == null)
            return (o.fileName == null? 0: -1);
        if (o.fileName == null)
            return 1;
        return this.fileName.trim().compareTo(o.fileName.trim());
    }

    public void setFileParserIName(String fileParserIName)
    {
        this.fileParserIName = fileParserIName;
    }

    public String getFileParserIName()
    {
        return fileParserIName;
    }

    private String[] parentRefKeyNames =
    { PatternSourceId };

    public static String getParentKeyStringSourceId()
    {
        return PatternSourceId;
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
                this.setSourceId((Number) kvpo.getValue());
            }
        }
    }

    public String getPrimaryKeyAsString()
    {
        StringBuilder sb = new StringBuilder();
        sb.append(this.sourceId).append("_");
        sb.append(this.fileName).append("_");
        return sb.toString();
    }
    
    //new JPA implementation
    public void handleInsertOperation()
    {
        LoganSourcePatternSession patternFacade = LoganSessionBeanProxy.getEmLoganSourcePatternFacade();
        EmLoganSourcePattern1 entity = new EmLoganSourcePattern1();
        String nextSql = LoganLibUiUtil.getNextSequenceIdSql("EM_LOGAN_SOURCE_PATTERN_SEQ");
        BigDecimal nextId = LoganSessionBeanProxy.getNextSeqBySql(nextSql);
        if(nextId == null)
        {
            nextId = new BigDecimal(10000);//initial id if no record in repository
        }
        patternId = new Number(nextId.longValue());
        entity.setPatternId(patternId.intValue());
        entity.setPatternSourceId(sourceId.intValue());
        entity.setPatternText(fileName);
        entity.setPatternDescription(fileDesc);
        if (fileParserIName != null)
        {
            entity.setPatternParserIname(fileParserIName);
        }
        String emUser = EMExecutionContext.getExecutionContext().getEMUser();
        author = emUser;
        entity.setPatternAuthor(author);
        entity.setPatternIsInclude((includePattern? 1: 0));
        patternFacade.persistEmLoganSourcePattern1(entity);
        

        if(s_log.isFinestEnabled())
        {
            StringBuilder  sb = new StringBuilder();
            sb.append("LOG PATT BEAN  created a row to INSERT for pattText ");
            sb.append(fileName);
            sb.append(", PatternParserIname = ");
            sb.append(fileParserIName);
            sb.append(", patternId = ");
            sb.append(patternId);
            sb.append("]");
            s_log.finest(sb.toString());
        }
        
        
//        ViewObjectImpl patVO = getViewObjectImpl();
//        NameValuePairs nvpmm = new NameValuePairs();
//        // Note the patternId is generated only when we create a new row
//        this.patternId = LoganLibUiUtil.getNextSequenceId("EM_LOGAN_SOURCE_PATTERN_SEQ");
//        nvpmm.setAttribute(PatternId, patternId);
//        nvpmm.setAttribute(PatternSourceId, sourceId);
//        nvpmm.setAttribute(PatternText, fileName);
//        nvpmm.setAttribute(PatternDescription, fileDesc);
//
//        if (fileParserIName != null)
//            nvpmm.setAttribute(PatternParserIname, fileParserIName);
//        nvpmm.setAttribute(PatternAuthor, author);
//        nvpmm.setAttribute(PatternIsInclude,
//                           (includePattern? new Number(1): new Number(0)));
//        Row iRow = patVO.createAndInitRow(nvpmm);
//        patVO.insertRow(iRow);
//        if(s_log.isFinestEnabled())
//        {
//            StringBuilder  sb = new StringBuilder();
//            sb.append("LOG PATT BEAN  created a row to INSERT for pattText ");
//            sb.append(fileName);
//            sb.append(", PatternParserIname = ");
//            sb.append(fileParserIName);
//            sb.append(", patternId = ");
//            sb.append(patternId);
//            sb.append("]");
//            s_log.finest(sb.toString());
//        }
    }

    public void handleUpdateOperation()
    {
        Object values[] =
        { (includePattern? new Number(1): new Number(0)), sourceId,
          fileName };
        Key key = new Key(values);
        Row[] uRows = getViewObjectImpl().findByKey(key, 1);

        if (uRows != null && uRows.length > 0)
        {
            Row uRow = uRows[0];
            if(s_log.isFinestEnabled())
            {
                StringBuilder  sb = new StringBuilder();
                sb.append("LOG PATT BEAN found a row to UPDATE for pattText ");
                sb.append(fileName);
                sb.append(", PatternParserIname = ");
                sb.append(fileParserIName);
                sb.append("]");
                s_log.finest(sb.toString());
            }
            uRow.setAttribute(PatternDescription, fileDesc);
            uRow.setAttribute(PatternParserIname, fileParserIName);
        }
    }

    public void handleDeleteOperation()
    {
        Object values[] =
        { (includePattern? new Number(1): new Number(0)), sourceId,
          fileName };
        Key key = new Key(values);
        Row[] dRows = getViewObjectImpl().findByKey(key, 1);

        if (dRows != null && dRows.length > 0)
        {
            Row dRow = dRows[0];
            if(s_log.isFinestEnabled())
            {
                StringBuilder  sb = new StringBuilder();
                sb.append("LOG PATT BEAN found a row to DELETE for pattText ");
                sb.append(fileName);
                sb.append(", PatternParserIname = ");
                sb.append(fileParserIName);
                sb.append("]");
                s_log.finest(sb.toString());
            }
            dRow.remove();
        }
    }

    private ViewObjectImpl getViewObjectImpl()
    {
        //TODO JPA
//        LoganAMImpl am = LoganLibUiUtil.getAppModule();       
//        ViewObjectImpl patVO = am.getEmLoganSourceInclPatternVO();
//        if(!this.isIncludePattern())
//        {
//            patVO = am.getEmLoganSourceExclPatternVO();
//        }
//        return patVO;
        
        return null;

    }
    
    public void setSourceId(Number sourceId)
    {
        this.sourceId = sourceId;
    }

    public Number getSourceId()
    {
        return sourceId;
    }

    public void setAuthor(String author)
    {
        this.author = author;
    }

    public String getAuthor()
    {
        return author;
    }

    public void setPatternId(Number patternId)
    {
        this.patternId = patternId;
    }

    public Number getPatternId()
    {
        return patternId;
    }

    public void setIncludePattern(boolean includePattern)
    {
        this.includePattern = includePattern;
    }

    public List<KeyValuePairObj> getReferenceKeysForDependents()
    {
        return Collections.emptyList();
    }

    public void addParamInPattern(String param)
    {
        paramsInPattern.add(param);
    }
    
    public void removeParamInPattern(String param)
    {
        paramsInPattern.remove(param);
    }

    public void setParamsInPattern(Set<String> paramsInPattern)
    {
        this.paramsInPattern = paramsInPattern;
    }

    public Set<String> getParamsInPattern()
    {
        return paramsInPattern;
    }

    public void setSourceTargetType(String sourceTargetType)
    {
        this.sourceTargetType = sourceTargetType;
        builtInParams = LoganLibUiUtil.getLoganMetaAvailParameters(sourceTargetType);
        updateParams();
    }

    public String getSourceTargetType()
    {
        return sourceTargetType;
    }

    public void setBuiltInParams(Set<LoganMetaAvailParameters> bp)
    {
        builtInParams = bp;
    }
    
    public Set<LoganMetaAvailParameters> getBuiltInParams()
    {
        return builtInParams;
    }
}
