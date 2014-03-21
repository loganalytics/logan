/* $Header: emcore/jsrc/core/CoreLoganalyticsUi/src/oracle/sysman/core/loganalytics/ui/source/LoganSourceDetailsBean.java /st_emgc_pt-13.1mstr/4 2013/08/27 01:15:56 rban Exp $ */

/* Copyright (c) 2002, 2013, Oracle and/or its affiliates. 
All rights reserved.*/

/*
   DESCRIPTION
   Log analytics Source Details data bean

   PRIVATE CLASSES
    <list of private classes defined - with one-line descriptions>

   NOTES
    <other useful comments, qualificationsf, etc.>
   
    MODIFIED    (MM/DD/YY)
    vivsharm    01/04/13 - log Analytics
 */

/**
 *  @version $Header: emcore/jsrc/core/CoreLoganalyticsUi/src/oracle/sysman/core/loganalytics/ui/source/LoganSourceDetailsBean.java /st_emgc_pt-13.1mstr/4 2013/08/27 01:15:56 rban Exp $
 *  @author  vivsharm
 *  @since   release specific (what release of product did this appear in)
 */

package oracle.saas.logan.view.source;

import java.util.ArrayList;
import java.util.List;

import oracle.jbo.Row;
import oracle.jbo.domain.Number;

import oracle.saas.logan.util.LoganLibUiUtil;
import oracle.saas.logan.util.Logger;
import oracle.saas.logan.util.UiUtil;
import oracle.saas.logan.view.BaseLoganSourceDataBean;
import oracle.saas.logan.view.ILoganSourceDataBean;
import oracle.saas.logan.view.IPersistDataOpsHandler;
import oracle.saas.logan.view.LaunchContextModeBean;
import oracle.saas.logan.view.TargetPropertyFilterBean;


public class LoganSourceDetailsBean extends BaseLoganSourceDataBean 
    implements Cloneable
{
    private Number sourceId;
    private LoganSourceDetailsMain mainDetails;
    private static final Logger s_log =
        Logger.getLogger(LoganSourceDetailsBean.class.getName());

    public LoganSourceDetailsBean()
    {
        this.mainDetails = new LoganSourceDetailsMain(null);
        TargetPropertyFilterBean targetProperties = new TargetPropertyFilterBean();
        List<LoganSourceDetailsParameter> parameters = new ArrayList<LoganSourceDetailsParameter>();
        List<LoganSourceFilePattern> inclPatts = new ArrayList<LoganSourceFilePattern>();
        List<LoganSourceFilePattern> exclPatts = new ArrayList<LoganSourceFilePattern>();
        mainDetails.setTargetProperties(targetProperties);
        mainDetails.setParameters(parameters);
        mainDetails.setInclPatts(inclPatts);
        mainDetails.setExclPatts(exclPatts);
    }

    public LoganSourceDetailsBean(Row detailsContextRow)
    {
        initFromVORow(detailsContextRow);
    }

    private void initFromVORow(Row detailsContextRow)
    {
        this.mainDetails = new LoganSourceDetailsMain(detailsContextRow);
        this.sourceId = this.mainDetails.getSourceId();
        List<LoganSourceFilePattern> inclPatts = LoganLibUiUtil.filteredSourcePatternsInclude(sourceId);
        List<LoganSourceFilePattern> exclPatts = LoganLibUiUtil.filteredSourcePatternsExclude(sourceId);
        List<LoganSourceDetailsParameter> parameters = LoganLibUiUtil.loadLogSourceParameters(sourceId);
        //TODO: add code to get this data
        TargetPropertyFilterBean targetProperties = new TargetPropertyFilterBean();
        mainDetails.setTargetProperties(targetProperties);
        mainDetails.setParameters(parameters);
        mainDetails.setInclPatts(inclPatts);
        mainDetails.setExclPatts(exclPatts);
    }

    public boolean isDataEditAllowed()
    {
        return isIsSystemDefinedSource();
    }

    /**
     * Called before the processSubmit step so that the
     * step data beans can set the final parent references as needed
     * 
     * @param modeBean
     */
    public void beforeProcessSubmit(LaunchContextModeBean modeBean)
    {
        if(s_log.isFinestEnabled())
            s_log.finest("before Process Submit of MAIN BEAN got called");
        if(modeBean.isReadOnlyMode())
            return;
        LoganSourceDetailsMain main = this.getMainDetails();;
        List<LoganSourceFilePattern> iPCurr = this.getInclFilePatterns();
        if(iPCurr != null && iPCurr.size() > 0)
        {
            for (LoganSourceFilePattern ip: iPCurr)
                main.addDependentChildObject(ip);
        }
        List<LoganSourceFilePattern> ePCurr = this.getExclFilePatterns();
        if(ePCurr != null && ePCurr.size() > 0)
        {
            for (LoganSourceFilePattern ep: ePCurr)
                main.addDependentChildObject(ep);
        }
        List<LoganSourceDetailsParameter> paramCurr = this.getParameters();
        if(paramCurr != null && paramCurr.size() > 0)
        {
            for (LoganSourceDetailsParameter param: paramCurr)
                main.addDependentChildObject(param);
        }
    }

    public void processSubmit(ILoganSourceDataBean refData, 
                              LaunchContextModeBean modeBean)
    {
        //TODO JPA
//        if(s_log.isFinestEnabled())
//            s_log.finest("processSubmit of MAIN BEAN got called");
//        boolean readOnlyMode = modeBean.isReadOnlyMode();
//        if (readOnlyMode) // SHOW DETAILS
//            return;
//        LoganSourceDetailsBean sourceDetailsBeanRef = (LoganSourceDetailsBean)refData;
//        
//        boolean editMode = modeBean.isEditMode();        
//        // process Main
//        LoganSourceDetailsMain mainRef =
//            sourceDetailsBeanRef.getMainDetails();
//        Number sourceIdOld = new Number(-1);
//        if (mainRef.getSourceId() != null)
//            sourceIdOld = mainRef.getSourceId();
//        if(s_log.isFinestEnabled())
//            s_log.finest("OLD SOURCE ID = " + sourceIdOld.intValue());
//        LoganSourceDetailsMain mainCurr = this.getMainDetails();
//        if (mainCurr == null || mainRef == null)
//            return;        
//        List<IPersistDataOpsHandler> rList = new ArrayList<IPersistDataOpsHandler>();
//        rList.add(mainRef);        
//        List<IPersistDataOpsHandler> cList = new ArrayList<IPersistDataOpsHandler>();
//        cList.add(mainCurr);
//        LoganLibUiUtil.persistChildObjectData(rList, cList,
//                                              readOnlyMode, editMode);
//        if(s_log.isFinestEnabled())
//            s_log.finest("Source : Persist Data done");
//
//        Number sourceIdNew = mainCurr.getSourceId();
//        if(s_log.isFinestEnabled())
//            s_log.finest("NEW SOURCE ID =  " + sourceIdNew.intValue());
//        this.sourceId = sourceIdNew;
//        
//        // TODO: process Target Prop Filters
//
//        // process Includes
//        List<LoganSourceFilePattern> iPRef =
//            sourceDetailsBeanRef.getInclFilePatterns();
//        List<LoganSourceFilePattern> iPCurr = this.getInclFilePatterns();
//        rList = LoganLibUiUtil.getIPersistList(iPRef);
//        cList = LoganLibUiUtil.getIPersistList(iPCurr);
//        LoganLibUiUtil.persistChildObjectData(rList, cList,
//                                              readOnlyMode, editMode);
//        if(s_log.isFinestEnabled())
//            s_log.finest("Include Patterns : Persist Data done");
//
//        // process Excludes
//        List<LoganSourceFilePattern> ePRef =
//            sourceDetailsBeanRef.getExclFilePatterns();
//        List<LoganSourceFilePattern> ePCurr = this.getExclFilePatterns();
//        rList = LoganLibUiUtil.getIPersistList(ePRef);
//        cList = LoganLibUiUtil.getIPersistList(ePCurr);
//        LoganLibUiUtil.persistChildObjectData(rList, cList,
//                                              readOnlyMode, editMode);
//        if(s_log.isFinestEnabled())
//            s_log.finest("EXCLUDe Patterns : Persist Data done");
//
//        // process Parameters
//        List<LoganSourceDetailsParameter> paramRef =
//            sourceDetailsBeanRef.getParameters();
//        List<LoganSourceDetailsParameter> paramCurr = this.getParameters();
//        rList = LoganLibUiUtil.getIPersistList(paramRef);
//        cList = LoganLibUiUtil.getIPersistList(paramCurr);
//        LoganLibUiUtil.persistChildObjectData(rList, cList,
//                                              readOnlyMode, editMode);
//        if(s_log.isFinestEnabled())
//            s_log.finest("process Parameters is done");
    }


    public Object clone()
    {
        LoganSourceDetailsBean c = new LoganSourceDetailsBean();
        c.setSourceId(sourceId);
        if (mainDetails != null)
            c.setMainDetails((LoganSourceDetailsMain) mainDetails.clone());
        return c;
    }

    public List<LoganSourceDetailsParameter> loadLogSourceParameters()
    {
        List<LoganSourceDetailsParameter> parameters = mainDetails.getParameters();
        if (parameters == null)
        {
            parameters = LoganLibUiUtil.loadLogSourceParameters(sourceId);
            mainDetails.setParameters(parameters);
        }
        return parameters;
    }

    public List<LoganSourceFilePattern> filteredSourcePatternsInclude()
    {
        List<LoganSourceFilePattern> inclPatts = mainDetails.getInclPatts();
        if (inclPatts == null)
        {
            inclPatts = LoganLibUiUtil.filteredSourcePatternsInclude(sourceId);
            mainDetails.setInclPatts(inclPatts);
        }
        return inclPatts;
    }

    public List<LoganSourceFilePattern> filteredSourcePatternsExclude()
    {
        List<LoganSourceFilePattern> exclPatts = mainDetails.getExclPatts();
        if (exclPatts == null)
        {
            exclPatts = LoganLibUiUtil.filteredSourcePatternsExclude(sourceId);
            mainDetails.setExclPatts(exclPatts);
        }
        return exclPatts;
    }

    public boolean isNoExclPatterns()
    {
        return mainDetails.isNoExclPatterns();
    }

    public boolean isNoParameters()
    {
        return mainDetails.isNoParameters();
    }

    public boolean isNoInclPatterns()
    {
        return mainDetails.isNoInclPatterns();
    }

    public void setSourceId(Number sourceId)
    {
        this.sourceId = sourceId;
    }

    public Number getSourceId()
    {
        return this.sourceId;
    }

    public void setParameters(List<LoganSourceDetailsParameter> parameters)
    {
        mainDetails.setParameters(parameters);
    }

    public List<LoganSourceDetailsParameter> getParameters()
    {
        return mainDetails.getParameters();
    }

    public void setMainDetails(LoganSourceDetailsMain b)
    {
        this.mainDetails = b;
    }

    public LoganSourceDetailsMain getMainDetails()
    {
        return mainDetails;
    }

    public String getName()
    {
        return this.mainDetails.getName();
    }

    public void setName(String name)
    {
        this.mainDetails.setName(name);
    }

    public boolean isIsSystemDefinedSource()
    {
        return this.mainDetails.isIsSystemDefinedSource();
    }

    public void setAuthor(String author)
    {
        this.mainDetails.setAuthor(author);
    }

    public String getAuthor()
    {
        return this.mainDetails.getAuthor();
    }

    public void setSourceTypeDisplay(String type)
    {
        this.mainDetails.setSourceTypeDname(type);
    }

    public String getSourceTypeDisplay()
    {
        return this.mainDetails.getSourceTypeDname();
    }

    public void setSourceType(String type)
    {
        this.mainDetails.setSourceTypeIname(type);
    }

    public String getSourceType()
    {
        return this.mainDetails.getSourceTypeIname();
    }

    public void setTargetType(String targetType)
    {
        this.mainDetails.setTargetType(targetType);
    }

    public String getTargetType()
    {
        return this.mainDetails.getTargetType();
    }

    public void setDescription(String description)
    {
        this.mainDetails.setDescription(description);
    }

    public String getDescription()
    {
        return this.mainDetails.getDescription();
    }

    public void setIsSecureContent(boolean s)
    {
        mainDetails.setIsSecureContent(s);
    }

    public boolean getIsSecureContent()
    {
        return mainDetails.getIsSecureContent();
    }

    public int getInclFilePatternsCount()
    {
        return mainDetails.getInclFilePatternsCount();
    }

    public void setInclFilePatterns(List<LoganSourceFilePattern> p)
    {
        mainDetails.setInclPatts(p);
    }

    public List<LoganSourceFilePattern> getInclFilePatterns()
    {
        return mainDetails.getInclPatts();
    }

    public void setExclFilePatterns(List<LoganSourceFilePattern> p)
    {
        mainDetails.setExclPatts(p);
    }

    public List<LoganSourceFilePattern> getExclFilePatterns()
    {
        return mainDetails.getExclPatts();
    }

    public void setTargetPropertyFilterBean(TargetPropertyFilterBean b)
    {
        mainDetails.setTargetProperties(b);
    }

    public TargetPropertyFilterBean getTargetPropertyFilterBean()
    {
        return mainDetails.getTargetProperties();
    }

    public String validateLogSourceName(String sourceName)
    {
        String errMsg = null;
        if (sourceName == null || sourceName.trim().length() == 0)
        {
            errMsg = UiUtil.getUiString("SOURCE_NAME_BLANK_ERR");
        }
        else if (!LoganLibUiUtil.isValidSourceName(sourceName))
        {
            errMsg = UiUtil.getUiString("SOURCE_NAME_INUSE_ERR");
        }
        return errMsg;
    }

    public String validateLogSourceType()
    {
        String errMsg = null;
        String sourceType = this.getSourceType();
        if (sourceType == null || sourceType.trim().length() == 0)
        {
            errMsg = UiUtil.getUiString("SOURCE_TYPE_BLANK_ERR");
        }
        return errMsg;
    }

    public String validateLogSourceTargetType()
    {
        String sourceTargetType = this.getTargetType();
        String errMsg = null;
        if (sourceTargetType == null ||
            sourceTargetType.trim().length() == 0)
        {
            errMsg = UiUtil.getUiString("SOURCE_TARGET_TYPE_BLANK_ERR");
        }
        return errMsg;
    }
}
