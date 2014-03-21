/* $Header: emcore/jsrc/core/CoreLoganalyticsUi/src/oracle/sysman/core/loganalytics/ui/source/LoganSourceDetailsMain.java /st_emgc_pt-13.1mstr/4 2014/01/26 17:07:35 vivsharm Exp $ */

/* Copyright (c) 2002, 2014, Oracle and/or its affiliates. 
All rights reserved.*/

/*
   DESCRIPTION
   Log analytics Source Details main data bean

   PRIVATE CLASSES
    <list of private classes defined - with one-line descriptions>

   NOTES
    <other useful comments, qualificationsf, etc.>
   
    MODIFIED    (MM/DD/YY)
    vivsharm    01/10/14 - fix log level to finest
    vivsharm    01/04/13 - log Analytics
 */

/**
 *  @version $Header: emcore/jsrc/core/CoreLoganalyticsUi/src/oracle/sysman/core/loganalytics/ui/source/LoganSourceDetailsMain.java /st_emgc_pt-13.1mstr/4 2014/01/26 17:07:35 vivsharm Exp $
 *  @author  vivsharm
 *  @since   release specific (what release of product did this appear in)
 */

package oracle.saas.logan.view.source;

import java.util.ArrayList;
import java.util.List;

import oracle.jbo.Key;
import oracle.jbo.NameValuePairs;
import oracle.jbo.Row;
import oracle.jbo.domain.Date;
import oracle.jbo.domain.Number;
import oracle.jbo.domain.Timestamp;
import oracle.jbo.server.ViewObjectImpl;

import oracle.saas.logan.util.EMExecutionContext;
import oracle.saas.logan.util.LoganLibUiUtil;
import oracle.saas.logan.util.Logger;
import oracle.saas.logan.view.BaseDataPersistenceHandler;
import oracle.saas.logan.view.IPersistDataOpsHandler;
import oracle.saas.logan.view.KeyValuePairObj;
import oracle.saas.logan.view.TargetPropertyFilterBean;


public class LoganSourceDetailsMain extends BaseDataPersistenceHandler
    implements Cloneable, IPersistDataOpsHandler
{
    private Number sourceId;
    private boolean isSystemDefinedSource;
    private String name;
    private String internalName;
    private String author;
    private String sourceTypeIname;
    private String sourceTypeDname;
    private String targetType;
    private String description;
    private boolean isSecureContent;
    private Number sourceEditVer;
    private Number sourceCriticalEditVer;


    private static final String SourceId = "SourceId";
    private static final String SourceIsSystem = "SourceIsSystem";

    private static final String SourceDname = "SourceDname";
    private static final String SourceIname = "SourceIname";

    private static final String SourceAuthor = "SourceAuthor";
    private static final String SourceOwner = "SourceOwner";
    private static final String SourceTargetType = "SourceTargetType";
    private static final String TargetType = "TargetType";
    
    private static final String SourceDescription = "SourceDescription";
    private static final String SourceIsSecureContent =
        "SourceIsSecureContent";
    private static final String SourceSrctypeIname = "SourceSrctypeIname";
    private static final String SourceEditVersion = "SourceEditVersion";
    private static final String SourceCriticalEditVersion =
        "SourceCriticalEditVersion";
    private static final String SourceLastUpdatedBy =
        "SourceLastUpdatedBy";
    private static final String SourceLastUpdatedDate =
        "SourceLastUpdatedDate";

    private static final String SrctypeDname = "SrctypeDname";
    
    private TargetPropertyFilterBean targetProperties;
    private List<LoganSourceDetailsParameter> parameters;
    private List<LoganSourceFilePattern> inclPatts;
    private List<LoganSourceFilePattern> exclPatts;

    private static final Logger s_log =
        Logger.getLogger(LoganSourceDetailsMain.class.getName());

    public LoganSourceDetailsMain()
    {
        super();
    }

    public LoganSourceDetailsMain(Number sourceId,
                                  boolean isSystemDefinedSource,
                                  String name, String internalName,
                                  String sourceTypeDname,
                                  String sourceTypeIname, String author,
                                  String targetType, String description,
                                  boolean isSecureContent,
                                  Number sourceEditVer,
                                  Number sourceCriticalEditVer)
    {
        this.sourceId = sourceId;
        this.isSystemDefinedSource = isSystemDefinedSource;
        this.name = name;
        this.internalName = internalName;
        this.sourceTypeIname = sourceTypeIname;
        this.sourceTypeDname = sourceTypeDname;
        this.author = author;
        this.targetType = targetType;
        this.description = description;
        this.isSecureContent = isSecureContent;
        this.sourceEditVer = sourceEditVer;
        this.sourceCriticalEditVer = sourceCriticalEditVer;
    }

    public LoganSourceDetailsMain(Row dRow)
    {
        if (dRow != null)
        {
            sourceId = (Number) dRow.getAttribute(SourceId);
            Number isSys = (Number) dRow.getAttribute(SourceIsSystem);
            if (isSys != null && isSys.intValue() == 1)
            {
                isSystemDefinedSource = true;
            }
            name = (String) dRow.getAttribute(SourceDname);
            internalName = (String) dRow.getAttribute(SourceIname);
            sourceTypeIname =
                    (String) dRow.getAttribute(SourceSrctypeIname);
            sourceTypeDname = (String) dRow.getAttribute(SrctypeDname);
            author = (String) dRow.getAttribute(SourceAuthor);
            targetType = (String) dRow.getAttribute(SourceTargetType);
            description = (String) dRow.getAttribute(SourceDescription);
            Number isSec =
                (Number) dRow.getAttribute(SourceIsSecureContent);
            if (isSec != null && isSec.intValue() == 1)
            {
                isSecureContent = true;
            }
            sourceEditVer = (Number) dRow.getAttribute(SourceEditVersion);
            sourceCriticalEditVer =
                    (Number) dRow.getAttribute(SourceCriticalEditVersion);
        }
    }

    public Object clone()
    {
        LoganSourceDetailsMain c = new LoganSourceDetailsMain(sourceId, isSystemDefinedSource,
                                          name, internalName,
                                          sourceTypeDname, sourceTypeIname,
                                          author, targetType, description,
                                          isSecureContent, sourceEditVer,
                                          sourceCriticalEditVer);
        if (targetProperties != null)
            c.setTargetProperties((TargetPropertyFilterBean) targetProperties.clone());
        if (parameters != null)
        {
            List<LoganSourceDetailsParameter> params =
                new ArrayList<LoganSourceDetailsParameter>();
            for (LoganSourceDetailsParameter p: parameters)
            {
                params.add((LoganSourceDetailsParameter) p.clone());
            }
            c.setParameters(params);
        }
        if (inclPatts != null)
        {
            List<LoganSourceFilePattern> incs =
                new ArrayList<LoganSourceFilePattern>();
            for (LoganSourceFilePattern p: inclPatts)
            {
                incs.add((LoganSourceFilePattern) p.clone());
            }
            c.setInclPatts(incs);
        }
        if (exclPatts != null)
        {
            List<LoganSourceFilePattern> excs =
                new ArrayList<LoganSourceFilePattern>();
            for (LoganSourceFilePattern p: exclPatts)
            {
                excs.add((LoganSourceFilePattern) p.clone());
            }
            c.setExclPatts(excs);
        }
        return c;
    }

    public void handleInsertOperation()
    {
        ViewObjectImpl srcVO = getViewObjectImpl();
        NameValuePairs nvpmm = new NameValuePairs();
        sourceId = LoganLibUiUtil.getNextSequenceId("EM_LOGAN_SOURCE_SEQ");
        nvpmm.setAttribute(SourceId, sourceId);
        nvpmm.setAttribute(SourceIname, internalName);
        nvpmm.setAttribute(SourceSrctypeIname, sourceTypeIname);
        nvpmm.setAttribute(SourceTargetType, targetType);
        nvpmm.setAttribute(SourceIsSystem, new Number(0));
        nvpmm.setAttribute(SourceDname, name);
        nvpmm.setAttribute(SourceDescription, description);
        String emUser =
            EMExecutionContext.getExecutionContext().getEMUser();
        author = emUser;
        nvpmm.setAttribute(SourceAuthor, author);
        nvpmm.setAttribute(SourceOwner, emUser);
        nvpmm.setAttribute(SourceIsSecureContent,
                           (isSecureContent? new Number(1):
                            new Number(0)));
        nvpmm.setAttribute(SourceEditVersion, new Number(1));
        nvpmm.setAttribute(SourceCriticalEditVersion, new Number(1));
        nvpmm.setAttribute(SourceLastUpdatedBy, emUser);
        nvpmm.setAttribute(SourceLastUpdatedDate,
                           new Timestamp(new Date(Date.getCurrentDate())));
        nvpmm.setAttribute(TargetType, targetType);

        Row iRow = srcVO.createAndInitRow(nvpmm);
        srcVO.insertRow(iRow);
        
        if(s_log.isFinestEnabled())
        {
            StringBuilder  sb = new StringBuilder();
            sb.append("LOG SOURCE main BEAN  created a row to INSERT: ");
            sb.append("internalName ").append(internalName);
            sb.append(", Desc = ").append(description);
            sb.append(", sourceId = ").append(sourceId);
            s_log.finest(sb.toString());
        }
    }

    public void handleUpdateOperation()
    {
        Object values[] =
        { internalName, sourceTypeIname, targetType, targetType };
        Key key = new Key(values);
        Row[] uRows = getViewObjectImpl().findByKey(key, 1);

        if (uRows != null && uRows.length > 0)
        {
            Row uRow = uRows[0];
            
            if(s_log.isFinestEnabled())
            {
                StringBuilder  sb = new StringBuilder();
                sb.append("LOG SOURCE main BEAN  found a row to Update: internalName " + internalName);
                sb.append(", Desc = " + description);
                s_log.finest(sb.toString());
            }
            uRow.setAttribute(SourceIsSecureContent,
                              (isSecureContent? new Number(1):
                               new Number(0)));
            uRow.setAttribute(SourceDescription, description);
            Number upEV =
                (sourceEditVer != null? new Number(sourceEditVer.intValue() +
                                                   1): new Number(1));
            Number upCEV =
                (sourceCriticalEditVer != null? new Number(sourceCriticalEditVer.intValue() +
                                                           1):
                 new Number(1));
            uRow.setAttribute(SourceEditVersion, upEV);
            uRow.setAttribute(SourceCriticalEditVersion, upCEV);
            String emUser =
                EMExecutionContext.getExecutionContext().getEMUser();
            uRow.setAttribute(SourceLastUpdatedBy, emUser);
            uRow.setAttribute(SourceLastUpdatedDate,
                              new Timestamp(new Date(Date.getCurrentDate())));
        }
    }

    public void handleDeleteOperation()
    {
        Object values[] =
        { targetType, sourceTypeIname, internalName };
        Key key = new Key(values);
        Row[] dRows = getViewObjectImpl().findByKey(key, 1);

        if (dRows != null && dRows.length > 0)
        {
            Row dRow = dRows[0];
            
            if(s_log.isFinestEnabled())
            {
                StringBuilder  sb = new StringBuilder();
                sb.append("LOG SOURCE main BEAN  found a row to delete: internalName " + internalName);
                sb.append(", Desc = " + description);
                s_log.finest(sb.toString());
            }
            dRow.remove();
        }
    }

    private ViewObjectImpl getViewObjectImpl()
    {
        //TODO JPA module
//        LoganAMImpl am = LoganLibUiUtil.getAppModule();       
//        return am.getEmLoganSourceVO();
        return null;
    }

    public void setParentReferenceKeys(List<KeyValuePairObj> rks)
    {
        // this is the root, parent object for Log Source
    }

    public String getPrimaryKeyAsString()
    {
        StringBuilder sb = new StringBuilder();
        sb.append(this.targetType).append("_");
        sb.append(this.sourceTypeIname).append("_");
        sb.append(this.internalName).append("_");
        return sb.toString();
    }

    public List<KeyValuePairObj> getReferenceKeysForDependents()
    {
        List<KeyValuePairObj> rks = new ArrayList<KeyValuePairObj>(1);
        rks.add(new KeyValuePairObj(SourceId,
                                    this.sourceId));
        return rks;
    }

    public boolean isObjInstanceOfSameClass(Object o)
    {
        return (o != null && o instanceof LoganSourceDetailsMain);
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
        sb.append(this.name).append("_");
        sb.append(this.targetType).append("_");
        sb.append(this.description).append("_");
        sb.append(this.isSecureContent).append("_");
        return sb.toString();
    }

    public boolean isNoExclPatterns()
    {
        return (this.exclPatts == null || this.exclPatts.size() == 0);
    }

    public boolean isNoParameters()
    {
        return (this.parameters == null || this.parameters.size() == 0);
    }

    public boolean isNoInclPatterns()
    {
        return (this.inclPatts == null || this.inclPatts.size() == 0);
    }    

    public int getInclFilePatternsCount()
    {
        return this.inclPatts == null? 0: this.inclPatts.size();
    }    

    public void setSourceId(Number sourceId)
    {
        this.sourceId = sourceId;
    }

    public Number getSourceId()
    {
        return this.sourceId;
    }

    public void setName(String name)
    {
        this.name = name;
        this.internalName = name;
    }

    public String getName()
    {
        return this.name;
    }

    public void setAuthor(String author)
    {
        this.author = author;
    }

    public String getAuthor()
    {
        return this.author;
    }

    public void setTargetType(String targetType)
    {
        this.targetType = targetType;
    }

    public String getTargetType()
    {
        return this.targetType;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public String getDescription()
    {
        return this.description;
    }

    public void setIsSecureContent(boolean s)
    {
        this.isSecureContent = s;
    }

    public boolean getIsSecureContent()
    {
        return this.isSecureContent;
    }
    
    public void setSourceTypeIname(String sourceTypeIname)
    {
        this.sourceTypeIname = sourceTypeIname;
    }

    public String getSourceTypeIname()
    {
        return sourceTypeIname;
    }

    public void setSourceTypeDname(String sourceTypeDname)
    {
        this.sourceTypeDname = sourceTypeDname;
    }

    public String getSourceTypeDname()
    {
        return sourceTypeDname;
    }

    public void setSourceEditVer(Number sourceEditVer)
    {
        this.sourceEditVer = sourceEditVer;
    }

    public Number getSourceEditVer()
    {
        return sourceEditVer;
    }

    public void setInternalName(String internalName)
    {
        this.internalName = internalName;
    }

    public String getInternalName()
    {
        return internalName;
    }

    public void setSourceCriticalEditVer(Number sourceCriticalEditVer)
    {
        this.sourceCriticalEditVer = sourceCriticalEditVer;
    }

    public Number getSourceCriticalEditVer()
    {
        return sourceCriticalEditVer;
    }

    public void setIsSystemDefinedSource(boolean isSystemDefinedSource)
    {
        this.isSystemDefinedSource = isSystemDefinedSource;
    }

    public boolean isIsSystemDefinedSource()
    {
        return isSystemDefinedSource;
    }


    public void setTargetProperties(TargetPropertyFilterBean targetProperties)
    {
        this.targetProperties = targetProperties;
    }

    public TargetPropertyFilterBean getTargetProperties()
    {
        return targetProperties;
    }

    public void setParameters(List<LoganSourceDetailsParameter> parameters)
    {
        this.parameters = parameters;
    }

    public List<LoganSourceDetailsParameter> getParameters()
    {      
        return parameters;
    }

    public void setInclPatts(List<LoganSourceFilePattern> inclPatts)
    {
        this.inclPatts = inclPatts;
    }

    public List<LoganSourceFilePattern> getInclPatts()
    {
        return inclPatts;
    }

    public void setExclPatts(List<LoganSourceFilePattern> exclPatts)
    {
        this.exclPatts = exclPatts;
    }

    public List<LoganSourceFilePattern> getExclPatts()
    {
        return exclPatts;
    }
}
