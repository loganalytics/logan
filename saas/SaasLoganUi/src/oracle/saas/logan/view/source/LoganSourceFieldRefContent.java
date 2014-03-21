/* $Header: emcore/jsrc/core/CoreLoganalyticsUi/src/oracle/sysman/core/loganalytics/ui/source/LoganSourceFieldRefContent.java /st_emgc_pt-13.1mstr/3 2013/10/03 04:57:40 xxu Exp $ */

/* Copyright (c) 2002, 2013, Oracle and/or its affiliates. 
All rights reserved.*/

/*
   DESCRIPTION
   Log analytics Source field reference content data bean

   PRIVATE CLASSES
    <list of private classes defined - with one-line descriptions>

   NOTES
    <other useful comments, qualificationsf, etc.>
   
    MODIFIED    (MM/DD/YY)
    vivsharm    01/04/13 - log Analytics
 */

package oracle.saas.logan.view.source;

import java.util.List;

import oracle.jbo.Key;
import oracle.jbo.NameValuePairs;
import oracle.jbo.Row;
import oracle.jbo.domain.Number;
import oracle.jbo.server.ViewObjectImpl;

import oracle.saas.logan.util.LoganLibUiUtil;
import oracle.saas.logan.util.Logger;
import oracle.saas.logan.view.BaseDataPersistenceHandler;
import oracle.saas.logan.view.KeyValuePairObj;


public class LoganSourceFieldRefContent extends BaseDataPersistenceHandler
    implements Cloneable
{
    private static final String RefcId = "RefcId";
    private Number refcId;
    private static final String RefcRefIname = "RefcRefIname";
    private String refcRefIname;
    private static final String RefcName = "RefcName";
    private String refcName;
    private static final String RefcValue = "RefcValue";
    private String refcValue;
    
    private static final String ParentFK_RefIname = "RefIname";

    private static final Logger s_log =
        Logger.getLogger(LoganSourceFieldRefContent.class.getName());

    public LoganSourceFieldRefContent(String refcRefIname, Number refcId,
                                      String refcName, String refcValue)
    {
        super();
        this.refcRefIname = refcRefIname;
        this.refcId = refcId;
        this.refcName = refcName;
        this.refcValue = refcValue;
    }

    public LoganSourceFieldRefContent(Row vorow)
    {
        super();
        if (vorow == null)
            return;
        this.refcRefIname = (String) vorow.getAttribute(RefcRefIname);
        this.refcId = (Number) vorow.getAttribute(RefcId);
        this.refcName = (String) vorow.getAttribute(RefcName);
        this.refcValue = (String) vorow.getAttribute(RefcValue);
    }

    public Object clone()
    {
        return new LoganSourceFieldRefContent(refcRefIname, refcId,
                                              refcName, refcValue);
    }

    public void handleInsertOperation()
    {
        ViewObjectImpl voRef = getViewObjectImpl();
        NameValuePairs nvpmm = new NameValuePairs();
        // Note the Id is generated only when we create a new row
        this.refcId = LoganLibUiUtil.getNextSequenceId("EM_LOGAN_REFERENCE_CONTENT_SEQ");
        nvpmm.setAttribute(RefcId, refcId);

        nvpmm.setAttribute(RefcRefIname, this.refcRefIname);
        nvpmm.setAttribute(RefcName, this.refcName);
        nvpmm.setAttribute(RefcValue, this.refcValue);        

        Row iRow = voRef.createAndInitRow(nvpmm);
        voRef.insertRow(iRow);
        if (s_log.isFinestEnabled())
        {
            StringBuilder sb = new StringBuilder();
            sb.append("LOG FIELD REFERENCE CONTENT created a row to INSERT");
            sb.append("for refcRefIname ").append(refcRefIname);
            sb.append(" Name = ").append(refcName);
            sb.append(" , Value ").append(refcValue);
            s_log.finest(sb.toString());
        }
    }

    public void handleUpdateOperation()
    {
        Object values[] =
        { this.refcRefIname, refcName };
        Key key = new Key(values);
        Row[] uRows = getViewObjectImpl().findByKey(key, 1);

        if (uRows != null && uRows.length > 0)
        {
            Row uRow = uRows[0];
            uRow.setAttribute(RefcValue, this.refcValue);        
            if (s_log.isFinestEnabled())
            {
                StringBuilder sb = new StringBuilder();
                sb.append("LOG FIELD REFERENCE CONTENT row to UPDATE: ");
                sb.append("for refcRefIname ").append(refcRefIname);
                sb.append(" Name = ").append(refcName);
                sb.append(" , Value ").append(refcValue);
                s_log.finest(sb.toString());
            }
        }
    }

    public void handleDeleteOperation()
    {
        Object values[] =
        { this.refcRefIname, refcName };
        Key key = new Key(values);
        Row[] dRows = getViewObjectImpl().findByKey(key, 1);

        if (dRows != null && dRows.length > 0)
        {
            Row dRow = dRows[0];
            if (s_log.isFinestEnabled())
            {
                StringBuilder sb = new StringBuilder();
                sb.append("LOG FIELD REFERENCE CONTENT row to DELETE: ");
                sb.append("for refcRefIname ").append(refcRefIname);
                sb.append(" Name = ").append(refcName);
                sb.append(" , Value ").append(refcValue);
                s_log.finest(sb.toString());
            }
            dRow.remove();
        }
    }

    private ViewObjectImpl getViewObjectImpl()
    {
        //TODO JPA
//        LoganAMImpl am = LoganLibUiUtil.getAppModule();
//        return am.getEmLoganReferenceContentVO();
        return null;
    }

    public void setParentReferenceKeys(List<KeyValuePairObj> rks)
    {
        if (rks == null || rks.size() <= 0)
            return;

        for (KeyValuePairObj kvpo: rks)
        {
            if (ParentFK_RefIname.equals(kvpo.getKey()))
            {
                this.setRefcRefIname((String) kvpo.getValue());
            }
        }
    }

    public String getPrimaryKeyAsString()
    {
        StringBuilder sb = new StringBuilder();
        sb.append(this.refcRefIname).append("_");
        sb.append(this.refcName).append("_");
        return sb.toString();
    }
    
    public boolean isObjInstanceOfSameClass(Object o)
    {
        return (o != null && o instanceof LoganSourceFieldRefContent);
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
        sb.append(this.refcValue).append("_");
        return sb.toString();
    }

    public void setRefcId(Number refcId)
    {
        this.refcId = refcId;
    }

    public Number getRefcId()
    {
        return refcId;
    }

    public void setRefcRefIname(String refcRefIname)
    {
        this.refcRefIname = refcRefIname;
    }

    public String getRefcRefIname()
    {
        return refcRefIname;
    }

    public void setRefcName(String refcName)
    {
        this.refcName = refcName;
    }

    public String getRefcName()
    {
        return refcName;
    }

    public void setRefcValue(String refcValue)
    {
        this.refcValue = refcValue;
    }

    public String getRefcValue()
    {
        return refcValue;
    }
}
