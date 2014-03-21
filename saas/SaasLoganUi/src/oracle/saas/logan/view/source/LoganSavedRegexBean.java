/* $Header: emcore/jsrc/core/CoreLoganalyticsUi/src/oracle/sysman/core/loganalytics/ui/source/LoganSavedRegexBean.java /st_emgc_pt-13.1mstr/1 2013/10/12 19:35:56 rban Exp $ */

/* Copyright (c) 2002, 2013, Oracle and/or its affiliates. 
All rights reserved.*/

/*
   DESCRIPTION
   Log analytics Source Wizard extended fields page view scope bean

   PRIVATE CLASSES
    <list of private classes defined - with one-line descriptions>

   NOTES
    <other useful comments, qualificationsf, etc.>

    MODIFIED    (MM/DD/YY)
    vivsharm    01/04/13 - log Analytics
 */

/**
 *  @version $Header: emcore/jsrc/core/CoreLoganalyticsUi/src/oracle/sysman/core/loganalytics/ui/source/LoganSavedRegexBean.java /st_emgc_pt-13.1mstr/1 2013/10/12 19:35:56 rban Exp $
 *  @author  vivsharm
 *  @since   release specific (what release of product did this appear in)
 */

package oracle.saas.logan.view.source;

import java.util.List;

import oracle.jbo.Key;
import oracle.jbo.NameValuePairs;
import oracle.jbo.Row;
import oracle.jbo.domain.Number;
import oracle.jbo.server.ViewObjectImpl;

import oracle.saas.logan.util.Logger;
import oracle.saas.logan.view.BaseDataPersistenceHandler;
import oracle.saas.logan.view.KeyValuePairObj;


public class LoganSavedRegexBean
    extends BaseDataPersistenceHandler
{
    private String regexAuthor;
    private String regexContent;
    private String regexDescription;
    private String regexDescriptionNlsid;
    private String regexDname;
    private String regexDnameNlsid;
    private String regexIname;
    private Number regexIsSystem;


    private static final String RegexAuthor = "RegexAuthor";
    private static final String RegexContent = "RegexContent";
    private static final String RegexDescription = "RegexDescription";
    private static final String RegexDescriptionNlsid =
        "RegexDescriptionNlsid";
    private static final String RegexDname = "RegexDname";
    private static final String RegexDnameNlsid = "RegexDnameNlsid";
    private static final String RegexIname = "RegexIname";
    private static final String RegexIsSystem = "RegexIsSystem";

    private static final Logger s_log = Logger.getLogger(LoganSavedRegexBean.class.getName());

    public LoganSavedRegexBean(String regexAuthor, String regexContent,
                               String regexDescription,
                               String regexDescriptionNlsid,
                               String regexDname, String regexDnameNlsid,
                               String regexIname, Number regexIsSystem)
    {
        this.regexAuthor = regexAuthor;
        this.regexContent = regexContent;
        this.regexDescription = regexDescription;
        this.regexDescriptionNlsid = regexDescriptionNlsid;
        this.regexDname = regexDname;
        this.regexDnameNlsid = regexDnameNlsid;
        this.regexIname = regexIname;
        this.regexIsSystem = regexIsSystem;
    }

    public LoganSavedRegexBean(Row vorow)
    {
        super();
        if (vorow == null)
            return;
        this.regexAuthor = (String) vorow.getAttribute(RegexAuthor);
        this.regexContent = (String) vorow.getAttribute(RegexContent);
        this.regexDescription =
            (String) vorow.getAttribute(RegexDescription);
        this.regexDescriptionNlsid =
            (String) vorow.getAttribute(RegexDescriptionNlsid);
        this.regexDname = (String) vorow.getAttribute(RegexDname);
        this.regexDnameNlsid =
            (String) vorow.getAttribute(RegexDnameNlsid);
        this.regexIname = (String) vorow.getAttribute(RegexIname);
        this.regexIsSystem = (Number) vorow.getAttribute(RegexIsSystem);
    }

    public Object clone()
    {
        return new LoganSavedRegexBean(regexAuthor, regexContent,
                                       regexDescription,
                                       regexDescriptionNlsid, regexDname,
                                       regexDnameNlsid, regexIname,
                                       regexIsSystem);
    }

    public String getEditablePropertiesAsString()
    {
        if(regexIname == null)
            regexIname = regexDname;
        if(regexIsSystem == null)
            regexIsSystem = new Number(0);
        StringBuilder sb = new StringBuilder();
        sb.append(regexIname).append("_");
        sb.append(regexDname).append("_");
        sb.append(regexContent).append("_");
        sb.append(regexDescription).append("_");
        sb.append(regexDescriptionNlsid).append("_");
        return sb.toString();
    }

    public boolean isObjInstanceOfSameClass(Object o)
    {
        return (o != null && o instanceof LoganSavedRegexBean);
    }

    private ViewObjectImpl getViewObjectImpl()
    {
        //TODO get Facade
//        LoganAMImpl am = LoganLibUiUtil.getAppModule();
//        return am.getEmLoganSavedRegexVO();
        
        return null;
    }

    public void handleInsertOperation()
    {
        if(regexIsSystem != null && regexIsSystem.intValue() == 0 )
        {
            ViewObjectImpl voRef = getViewObjectImpl();
            NameValuePairs nvpmm = new NameValuePairs();
            nvpmm.setAttribute(RegexDname, regexDname);
            nvpmm.setAttribute(RegexDnameNlsid, regexDnameNlsid);
            if(regexIname == null)
                regexIname = regexDname;
            if(regexIsSystem == null)
                regexIsSystem = new Number(0);
            nvpmm.setAttribute(RegexIname, regexIname);
            nvpmm.setAttribute(RegexContent, regexContent);
            nvpmm.setAttribute(RegexDescription, regexDescription);
            nvpmm.setAttribute(RegexDescriptionNlsid, regexDescriptionNlsid);
            nvpmm.setAttribute(RegexAuthor, regexAuthor);
            nvpmm.setAttribute(RegexIsSystem, regexIsSystem); 
            
            Row iRow = voRef.createAndInitRow(nvpmm);
            voRef.insertRow(iRow);
            
            if(s_log.isFinestEnabled())
            {
                StringBuilder  sb = new StringBuilder();
                sb.append("LOGAN SAVED REGEX BEAN created a row to INSERT:");
                sb.append("\n\t regexDname ").append(regexDname);
                sb.append(",\n\t regexIname = ").append(regexIname);
                sb.append(",\n\t regexContent = ").append(regexContent);
                sb.append(",\n\t regexDescription = ").append(regexDescription);
                s_log.finest(sb.toString()+"\n\t");
            }
        }
    }

    public void handleUpdateOperation()
    {
        Object values[] =
        { this.regexIname };
        Key key = new Key(values);
        Row[] uRows = getViewObjectImpl().findByKey(key, 1);
        if (uRows != null && uRows.length > 0)
        {
            Row uRow = uRows[0];
            
            if(s_log.isFinestEnabled())
            {
                StringBuilder  sb = new StringBuilder();
                sb.append("LOGAN SAVED REGEX BEAN found a row to UPDATE for regexIname " + regexIname);
                s_log.finest(sb.toString());
            }
            uRow.setAttribute(RegexContent, regexContent);
            uRow.setAttribute(RegexDescription, regexDescription);
            uRow.setAttribute(RegexAuthor, regexAuthor);
        }
    }

    public void handleDeleteOperation()
    {
        Object values[] =
        { this.regexIname };
        Key key = new Key(values);
        Row[] dRows = getViewObjectImpl().findByKey(key, 1);
        if (dRows != null && dRows.length > 0)
        {
            Row dRow = dRows[0];
            
            if(s_log.isFinestEnabled())
            {
                StringBuilder  sb = new StringBuilder();
                sb.append("LOGAN SAVED REGEX BEAN found a row to DELETE for regexIname " + regexIname);
                sb.append(", regexContent = " + regexContent );
                s_log.finest(sb.toString());
            }
            dRow.remove();
        }
    }

    public void setParentReferenceKeys(List<KeyValuePairObj> rks)
    {
    }

    public String getPrimaryKeyAsString()
    {
        StringBuilder sb = new StringBuilder();
        if(regexIname == null)
            regexIname = regexDname;
        if(regexIsSystem == null)
            regexIsSystem = new Number(0);
        sb.append(regexIname).append("_");
        return sb.toString();
    }

    public void setRegexAuthor(String regexAuthor)
    {
        this.regexAuthor = regexAuthor;
    }

    public String getRegexAuthor()
    {
        return regexAuthor;
    }

    public void setRegexContent(String regexContent)
    {
        this.regexContent = regexContent;
    }

    public String getRegexContent()
    {
        return regexContent;
    }

    public void setRegexDescription(String regexDescription)
    {
        this.regexDescription = regexDescription;
    }

    public String getRegexDescription()
    {
        return regexDescription;
    }

    public void setRegexDescriptionNlsid(String regexDescriptionNlsid)
    {
        this.regexDescriptionNlsid = regexDescriptionNlsid;
    }

    public String getRegexDescriptionNlsid()
    {
        return regexDescriptionNlsid;
    }

    public void setRegexDname(String regexDname)
    {
        this.regexDname = regexDname;
    }

    public String getRegexDname()
    {
        return regexDname;
    }

    public void setRegexDnameNlsid(String regexDnameNlsid)
    {
        this.regexDnameNlsid = regexDnameNlsid;
    }

    public String getRegexDnameNlsid()
    {
        return regexDnameNlsid;
    }

    public void setRegexIname(String regexIname)
    {
        this.regexIname = regexIname;
    }

    public String getRegexIname()
    {
        return regexIname;
    }

    public void setRegexIsSystem(Number regexIsSystem)
    {
        this.regexIsSystem = regexIsSystem;
    }

    public Number getRegexIsSystem()
    {
        return regexIsSystem;
    }
}
