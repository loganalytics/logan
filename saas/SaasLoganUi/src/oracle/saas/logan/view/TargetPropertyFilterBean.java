/* $Header: emcore/jsrc/core/CoreLoganalyticsUi/src/oracle/sysman/core/loganalytics/ui/TargetPropertyFilterBean.java /st_emgc_pt-13.1mstr/1 2013/01/12 08:49:28 vivsharm Exp $ */

/* Copyright (c) 2002, 2013, Oracle and/or its affiliates. 
All rights reserved. */

/*
   DESCRIPTION
   Log analytics bean for Target Property Filter

   PRIVATE CLASSES
    <list of private classes defined - with one-line descriptions>

   NOTES
    <other useful comments, qualificationsf, etc.>
   
    MODIFIED    (MM/DD/YY)
    vivsharm    01/04/13 - log Analytics
 */

/**
 *  @version $Header: emcore/jsrc/core/CoreLoganalyticsUi/src/oracle/sysman/core/loganalytics/ui/TargetPropertyFilterBean.java /st_emgc_pt-13.1mstr/1 2013/01/12 08:49:28 vivsharm Exp $
 *  @author  vivsharm
 *  @since   release specific (what release of product did this appear in)
 */

package oracle.saas.logan.view;


public class TargetPropertyFilterBean
    implements Cloneable
{
    private String os;
    private String lifecycleState;
    private String version;
    private String platform;

    public TargetPropertyFilterBean()
    {
    }

    public TargetPropertyFilterBean(String os, String lifecycleState,
                                    String version, String platform)
    {
        this.os = os;
        this.lifecycleState = lifecycleState;
        this.version = version;
        this.platform = platform;
    }

    public Object clone()
    {
        return new TargetPropertyFilterBean(os, lifecycleState, version,
                                            platform);
    }

    public void setOs(String os)
    {
        this.os = os;
    }

    public void setLifecycleState(String lifecycleState)
    {
        this.lifecycleState = lifecycleState;
    }

    public void setVersion(String version)
    {
        this.version = version;
    }

    public void setPlatform(String platform)
    {
        this.platform = platform;
    }

    public String getOs()
    {
        return os;
    }

    public String getLifecycleState()
    {
        return lifecycleState;
    }

    public String getVersion()
    {
        return version;
    }

    public String getPlatform()
    {
        return platform;
    }

}
