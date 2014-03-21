/* $Header: emcore/jsrc/core/CoreLoganalyticsUi/src/oracle/sysman/core/loganalytics/ui/source/LoganMetaAvailParameters.java /st_emgc_pt-13.1mstr/1 2013/12/03 13:14:00 vivsharm Exp $ */

/* Copyright (c) 2002, 2013, Oracle and/or its affiliates. 
All rights reserved.*/

/*
   DESCRIPTION
   Log analytics Source available parameters data bean

   PRIVATE CLASSES
    <list of private classes defined - with one-line descriptions>

   NOTES
    <other useful comments, qualificationsf, etc.>
   
    MODIFIED    (MM/DD/YY)
    vivsharm    10/17/13 - log Analytics
 */

/**
 * @version $Header: emcore/jsrc/core/CoreLoganalyticsUi/src/oracle/sysman/core/loganalytics/ui/source/LoganMetaAvailParameters.java /st_emgc_pt-13.1mstr/1 2013/12/03 13:14:00 vivsharm Exp $
 * @author vivsharm
 * @since 13.1.0.0
 */

package oracle.saas.logan.view.source;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import oracle.jbo.Row;

import oracle.saas.logan.model.persistance.EmLoganMetaAvailParam;

public class LoganMetaAvailParameters
{
    private String paramName;
    private Integer paramType;
    private String paramTargetType;
    private String paramDescription;
    private String paramDescNlsId;
    private String paramPluginId;
    private String paramAuthor;
    
    private List<String> paramAvailPluginVersions =
        new ArrayList<String>();
    
    private static final String ApParamAuthor = "ApAuthor";
    private static final String ApParamDescription = "ApDescription";
    private static final String ApParamDescriptionNlsid = "ApDescriptionNlsid";
    private static final String ApParamName = "ApName";
    private static final String ApParamPluginId = "ApPluginId";
    private static final String ApPluginVersion = "ApPluginVersion";
    private static final String ApParamType = "ApType";
    private static final String ApTargetType = "ApTargetType";
    
    
    public LoganMetaAvailParameters(Row voRow)
    {
        if(voRow != null)
        {
            this.paramName = (String) voRow.getAttribute(ApParamName);
            this.paramTargetType = (String) voRow.getAttribute(ApTargetType);
            this.paramType = (Integer) voRow.getAttribute(ApParamType);
            this.paramDescription = (String) voRow.getAttribute(ApParamDescription);
            this.paramDescNlsId = (String) voRow.getAttribute(ApParamDescriptionNlsid);
            this.paramPluginId = (String) voRow.getAttribute(ApParamPluginId);
            this.paramAuthor = (String) voRow.getAttribute(ApParamAuthor);
            String appv = (String) voRow.getAttribute(ApPluginVersion);
            paramAvailPluginVersions.add(appv);
        }
    }
    
    public LoganMetaAvailParameters(EmLoganMetaAvailParam voRow)
    {
        if(voRow != null)
        {
            this.paramName = voRow.getApName();
            this.paramTargetType = voRow.getApTargetType();
            this.paramType = voRow.getApType();
            this.paramDescription = voRow.getApDescription();
            this.paramDescNlsId = voRow.getApDescriptionNlsid();
            this.paramPluginId = voRow.getApPluginId();
            this.paramAuthor = voRow.getApAuthor();
            String appv = voRow.getApPluginVersion();
            paramAvailPluginVersions.add(appv);
        }
    }
    
    
    public LoganMetaAvailParameters(String paramName, 
                                    String paramTargetType, 
                                    Integer paramType, 
                                    String paramDescription, 
                                    String paramDescNlsId, 
                                    String paramPluginId, 
                                    String paramAuthor,
                                    List<String> paramAvailPluginVersions)
    {
        this.paramName = paramName;
        this.paramTargetType = paramTargetType;
        this.paramType = paramType;
        this.paramDescription = paramDescription;
        this.paramDescNlsId = paramDescNlsId;
        this.paramPluginId = paramPluginId;
        this.paramAuthor = paramAuthor;
        this.paramAvailPluginVersions = paramAvailPluginVersions;
    }
    
    public String getPrimaryKeyAsString()
    {
        StringBuilder sb = new StringBuilder();
        sb.append(this.paramName).append("_");
        sb.append(this.paramTargetType).append("_");
        return sb.toString();
    }
    
    public boolean equals(Object o)
    {
        if(!(o instanceof LoganMetaAvailParameters) || this.paramName == null)
            return false;
        LoganMetaAvailParameters that = (LoganMetaAvailParameters)o;
        
        return this.getPrimaryKeyAsString().equals(that.getPrimaryKeyAsString());
    }
    
    public int hashCode()
    {
        return this.getPrimaryKeyAsString().hashCode();
    }

    public void setParamName(String paramName)
    {
        this.paramName = paramName;
    }

    public String getParamName()
    {
        return paramName;
    }

    public void setParamType(Integer paramType)
    {
        this.paramType = paramType;
    }

    public Integer getParamType()
    {
        return paramType;
    }

    public void setParamTargetType(String paramTargetType)
    {
        this.paramTargetType = paramTargetType;
    }

    public String getParamTargetType()
    {
        return paramTargetType;
    }

    public void setParamDescription(String paramDescription)
    {
        this.paramDescription = paramDescription;
    }

    public String getParamDescription()
    {
        return paramDescription;
    }

    public void setParamDescNlsId(String paramDescNlsId)
    {
        this.paramDescNlsId = paramDescNlsId;
    }

    public String getParamDescNlsId()
    {
        return paramDescNlsId;
    }

    public void setParamAuthor(String paramAuthor)
    {
        this.paramAuthor = paramAuthor;
    }

    public String getParamAuthor()
    {
        return paramAuthor;
    }
    
    public void addParamAvailablePluginVersion(String pluginVersion)
    {
        paramAvailPluginVersions.add(pluginVersion);
    }
    
    public boolean removeParamAvailablePluginVersion(String pluginVersion)
    {
        return paramAvailPluginVersions.remove(pluginVersion);
    }

    /**
     * This is to be used on a freshly initialized object of this class
     * on init this list has only 1 element which is the curr row's plugin version
     * 
     * @return
     */
    public String getParamAvailPluginVersion()
    {
        String pluginVer = null;
        if(paramAvailPluginVersions != null)
            pluginVer = paramAvailPluginVersions.get(0);
        return pluginVer;
    }

    /**
     * A given parameter might have been added several versions earlier and
     * this list provides all the plugin versions where the param is defined.
     * It sorts the list by the plugin versions in asc order.
     * This is called only on demand when the user clicks the info icon
     * for a given built-in parameter name
     * 
     * @return
     */
    public List<String> getParamAvailPluginVersions()
    {
        if(paramAvailPluginVersions != null)
        {
            Collections.sort(paramAvailPluginVersions);
        }
        return paramAvailPluginVersions;
    }
}
