/* $Header: emcore/jsrc/core/CoreLoganalyticsUi/src/oracle/sysman/core/loganalytics/ui/source/LoganSourceAddDataDirect.java /st_emgc_pt-13.1mstr/1 2014/01/26 17:07:35 vivsharm Exp $ */

/* Copyright (c) 2002, 2014, Oracle and/or its affiliates. 
All rights reserved.*/

/*
   DESCRIPTION
        For Log analytics Add Data directly from file functionality

   PRIVATE CLASSES
    <list of private classes defined - with one-line descriptions>

   NOTES
    <other useful comments, qualificationsf, etc.>

    MODIFIED    (MM/DD/YY)
    vivsharm    11/22/13 - log Analytics add data directly from file functionality
 */

/**
 *  @version $Header: emcore/jsrc/core/CoreLoganalyticsUi/src/oracle/sysman/core/loganalytics/ui/source/LoganSourceAddDataDirect.java /st_emgc_pt-13.1mstr/1 2014/01/26 17:07:35 vivsharm Exp $
 *  @author  vivsharm
 *  @since   13.1.0.0
 */

package oracle.saas.logan.view.source;

import java.util.ArrayList;
import java.util.List;

import javax.faces.model.SelectItem;

import oracle.jbo.domain.Number;

import oracle.saas.logan.view.BaseLoganSourceViewBean;
import oracle.saas.logan.view.ILoganSourceDataBean;


public class LoganSourceAddDataDirect
    extends BaseLoganSourceViewBean
{
    private String sourceName;
    private String sourceDesc;
    private String sourceTypeIname;

    private Number sourceId = null;

    private String filePattern;
    private String sourceTargetType;
    private String fileParserIName;

    private String author;

    private LoganSourceDetailsBean detailsBean;

    public LoganSourceAddDataDirect(String sourceName, String sourceDesc,
                                    String sourceTypeIname,
                                    String filePattern,
                                    String sourceTargetType,
                                    String fileParserIName, String author)
    {
        this.sourceName = sourceName;
        this.sourceDesc = sourceDesc;
        this.sourceTypeIname = sourceTypeIname;
        this.filePattern = filePattern;
        this.sourceTargetType = sourceTargetType;
        this.fileParserIName = fileParserIName;
        this.author = author;
        
        initializeDetails();
    }

    private void initializeDetails()
    {
        boolean isSystemDefinedSource = false;
        String sourceTypeDname = null;
        boolean isSecureContent = false;
        Number sourceEditVer = null, sourceCriticalEditVer = null;

        // the source details bean which manages the validation and
        // insert of source and the related file pattern
        LoganSourceDetailsBean detailsBean = new LoganSourceDetailsBean();

        // the main bean which inserts into the EM_LOGAN_SOURCE table
        LoganSourceDetailsMain mainDetails =
            new LoganSourceDetailsMain(sourceId, isSystemDefinedSource,
                                       sourceName, sourceName,
                                       sourceTypeDname, sourceTypeIname,
                                       author, sourceTargetType,
                                       sourceDesc, isSecureContent,
                                       sourceEditVer,
                                       sourceCriticalEditVer);

        // there is only 1 include pattern that is the filePattern
        // specified on the ad-hoc add data UI form
        List<LoganSourceFilePattern> inclPatts =
            new ArrayList<LoganSourceFilePattern>();
        String fileDesc = null;
        // the bean which manages insert into the EM_LOGAN_SOURCE_PATTERN table.
        LoganSourceFilePattern srcfp =
            new LoganSourceFilePattern(author, filePattern, fileDesc,
                                       fileParserIName, sourceTargetType);
        inclPatts.add(srcfp);
        this.detailsBean = detailsBean;
        this.detailsBean.setMainDetails(mainDetails);
        this.detailsBean.setInclFilePatterns(inclPatts);
    }

    public ILoganSourceDataBean getSourceDataBean()
    {
        return detailsBean;
    }

    public void setSourceDataBean(ILoganSourceDataBean tsdb)
    {
        this.detailsBean = (LoganSourceDetailsBean) tsdb;
    }

    public String getName()
    {
        return this.detailsBean.getName();
    }

    public boolean validateViewBeanData()
    {
        boolean validationSuccess = true;
        List<SelectItem> errorsList =
            LoganSourceDetailsViewBean.validateLogSourceData(this.detailsBean);

        validationSuccess = (errorsList == null || errorsList.size() == 0);
        if (!validationSuccess)
        {
            //RichPopup.PopupHints hints = new RichPopup.PopupHints();
            //this.errmpop.show(hints);
        }
        return validationSuccess;
    }

    public Number getNewSourceId()
    {
        return this.detailsBean.getSourceId();
    }

    public void setMainDetails(LoganSourceDetailsMain mainDetails)
    {
        this.detailsBean.setMainDetails(mainDetails);
    }

    public LoganSourceDetailsMain getMainDetails()
    {
        return this.detailsBean.getMainDetails();
    }

    public void setInclPatts(List<LoganSourceFilePattern> inclPatts)
    {
        this.getMainDetails().setInclPatts(inclPatts);
    }

    public List<LoganSourceFilePattern> getInclPatts()
    {
        return this.getMainDetails().getInclPatts();
    }
}
