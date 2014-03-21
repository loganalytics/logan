/* $Header: emcore/jsrc/core/CoreLoganalyticsUi/src/oracle/sysman/core/loganalytics/ui/source/LoganSourceDetailsParameter.java /st_emgc_pt-13.1mstr/3 2013/06/26 11:59:40 kchiasso Exp $ */

/* Copyright (c) 2002, 2013, Oracle and/or its affiliates. 
All rights reserved. */

/*
   DESCRIPTION
   Log analytics Source Details parameters data bean

   PRIVATE CLASSES
    <list of private classes defined - with one-line descriptions>

   NOTES
    <other useful comments, qualificationsf, etc.>
   
    MODIFIED    (MM/DD/YY)
    vivsharm    01/04/13 - log Analytics
 */

/**
 *  @version $Header: emcore/jsrc/core/CoreLoganalyticsUi/src/oracle/sysman/core/loganalytics/ui/source/LoganSourceDetailsParameter.java /st_emgc_pt-13.1mstr/3 2013/06/26 11:59:40 kchiasso Exp $
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

import oracle.saas.logan.util.Logger;
import oracle.saas.logan.view.BaseDataPersistenceHandler;
import oracle.saas.logan.view.IPersistDataOpsHandler;
import oracle.saas.logan.view.KeyValuePairObj;


public class LoganSourceDetailsParameter extends BaseDataPersistenceHandler implements Cloneable
{
    private Number paramSourceId;
    private String paramName;
    private String defaultValue;
    private String paramDescription;
    private Number paramIsActive;
    private String author;    
    private boolean paramActive;
    
    private static final Logger s_log =
        Logger.getLogger(LoganSourceDetailsParameter.class.getName());

    private static final String ParamName = "ParamName";
    private static final String ParamDescription = "ParamDescription";    
    private static final String ParamDefaultValue = "ParamDefaultValue";
    private static final String ParamSourceId = "ParamSourceId";
    private static final String ParamAuthor = "ParamAuthor";
    private static final String ParamIsActive = "ParamIsActive";

    private String[] parentRefKeyNames = { ParamSourceId };
    
    List<IPersistDataOpsHandler> dependentObjects = 
        new ArrayList<IPersistDataOpsHandler>();

    private static final String ParentFK_SourceId = "SourceId";
    
    public LoganSourceDetailsParameter(Row vorow)
    {
        if (vorow == null)
            return;
        Number paramSourceId = (Number) vorow.getAttribute(ParamSourceId);
        String paramName = (String) vorow.getAttribute(ParamName);
        String defaultValue = (String) vorow.getAttribute(ParamDefaultValue);
        Number paramIsActive = (Number) vorow.getAttribute(ParamIsActive);
        String paramDescription =
            (String) vorow.getAttribute(ParamDescription);
        String author = (String) vorow.getAttribute(ParamAuthor);

        this.paramSourceId = paramSourceId;
        this.paramName = paramName;
        this.defaultValue = defaultValue;
        this.paramDescription = paramDescription;
        this.author = author;
        this.paramActive = (paramIsActive.intValue() == 1);
        this.paramIsActive = paramIsActive;
    }

    public LoganSourceDetailsParameter(Number paramSourceId,
                                       String paramName,
                                       String defaultValue,
                                       String paramDescription,
                                       String author,
                                       boolean paramActive)
    {
        this.paramSourceId = paramSourceId;
        this.paramName = paramName;
        this.defaultValue = defaultValue;
        this.paramDescription = paramDescription;
        this.author = author;
        this.paramActive = paramActive;
        this.paramIsActive = (paramActive ? new Number(1) : new Number(0));
    }

    public Object clone()
    {
        return new LoganSourceDetailsParameter(paramSourceId, paramName, defaultValue, 
                                               paramDescription, author, paramActive);
    }
       
    public void setParamName(String paramName)
    {
        this.paramName = paramName;
    }

    public String getParamName()
    {
        return this.paramName;
    }

    public void setDefaultValue(String defaultValue)
    {
        this.defaultValue = defaultValue;
    }

    public String getDefaultValue()
    {
        return this.defaultValue;
    }

    public void setParamDescription(String paramDescription)
    {
        this.paramDescription = paramDescription;
    }

    public String getParamDescription()
    {
        return this.paramDescription;
    }

    public void setParamSourceId(Number paramSourceId)
    {
        this.paramSourceId = paramSourceId;
    }

    public Number getParamSourceId()
    {
        return paramSourceId;
    }
    
    /**
     * Handle the Insert operation here.
     * The VO reference will be used to create
     * the row represented by this object and voRow.createAndInitRow() 
     * will be called here. Parent reference attributes needed to create
     * the VO row would already have been set via setParentReferenceKeys()
     */
    public void handleInsertOperation()
    {
        ViewObjectImpl paramVO = getViewObjectImpl();
        NameValuePairs nvpmm = new NameValuePairs();
        nvpmm.setAttribute(ParamSourceId, paramSourceId);
        nvpmm.setAttribute(ParamName, paramName);
        nvpmm.setAttribute(ParamDescription, paramDescription);
        nvpmm.setAttribute(ParamDefaultValue, defaultValue);
        nvpmm.setAttribute(ParamAuthor, author);
        nvpmm.setAttribute(ParamIsActive,
                           (paramActive? new Number(1): new Number(0)));
        Row iRow = paramVO.createAndInitRow(nvpmm);
        paramVO.insertRow(iRow);
        
        if(s_log.isFinestEnabled())
        {
            StringBuilder  sb = new StringBuilder();
            sb.append("LOG PARAM BEAN  created a row to INSERT for paramName " + paramName);
            sb.append(", Desc = " + paramDescription);
            s_log.finest(sb.toString());
        }
    }

    /**
     * Handle the Update operation here.
     * The VO reference will be used to search
     * for the row represented by this object and voRow.setAttribute() 
     * will be called here to update the new values from this object
     */
    public void handleUpdateOperation()
    {
        Object values[] = { paramName, paramSourceId };
        Key key = new Key(values);
        Row[] uRows = getViewObjectImpl().findByKey(key, 1);

        if (uRows != null && uRows.length > 0)
        {
            Row uRow = uRows[0];
            
            if(s_log.isFinestEnabled())
            {
                StringBuilder  sb = new StringBuilder();
                sb.append("LOG PARAM BEAN  created a row to UPDATE for paramName " + paramName);
                sb.append(", Desc = " + paramDescription);
                s_log.finest(sb.toString());
            }
            uRow.setAttribute(ParamDescription, paramDescription);
            uRow.setAttribute(ParamDefaultValue, defaultValue);
        }
    }

    /**
     * Handle the delete operation here.
     * The VO reference will be used to search
     * for the row represented by this object and voRow.remove() will be called
     */
    public void handleDeleteOperation()
    {
        Object values[] = { paramName, paramSourceId };
        Key key = new Key(values);
        Row[] dRows = getViewObjectImpl().findByKey(key, 1);

        if (dRows != null && dRows.length > 0)
        {
            Row dRow = dRows[0];
            
            if(s_log.isFinestEnabled())
            {
                StringBuilder  sb = new StringBuilder();
                sb.append("LOG PARAM BEAN  created a row to DELETE for paramName " + paramName);
                sb.append(", Desc = " + paramDescription);
                s_log.finest(sb.toString());
            }
            dRow.remove();
        }
    }

    private ViewObjectImpl getViewObjectImpl()
    {
        
        //TODO JPA
//        LoganAMImpl am = LoganLibUiUtil.getAppModule();
//        return am.getEmLoganSourceParameterVO();
        return null;
    }
    
    public static String getParentKeyStringSourceId()
    {
        return ParamSourceId;
    }

   /**
     * An array of Key Column Name Strings
     * @return
     */
    public String[] getParentReferenceKeyNames()
    {        
        return parentRefKeyNames;
    }

    /**
     * If this is a child object of the parent, following method
     * will be called to set the parent reference keys so that
     * handle insert has the right FK reference values.
     * @param rks
     */
    public void setParentReferenceKeys(List<KeyValuePairObj> rks)
    {
        if(rks == null || rks.size() <= 0)
            return;
    
        for(KeyValuePairObj kvpo : rks)
        {
            if(ParentFK_SourceId.equals(kvpo.getKey()))
            {
                this.setParamSourceId((Number)kvpo.getValue());
            }
        }
    }

    /**
     * A String representation of the Key column values
     * @return
     */
    public String getPrimaryKeyAsString()
    {
        StringBuilder sb = new StringBuilder();
        sb.append(this.paramSourceId).append("_");
        sb.append(this.paramName).append("_");
        return sb.toString();
    }


    public boolean isObjInstanceOfSameClass(Object o)
    {
        return (o != null && o instanceof LoganSourceDetailsParameter);
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
        sb.append(this.paramName).append("_");
        sb.append(this.defaultValue).append("_");
        sb.append(this.paramDescription).append("_");
        return sb.toString();
    }
    
    public void setParamActive(boolean paramActive)
    {
        this.paramActive = paramActive;
    }

    public boolean isParamActive()
    {
        return paramActive;
    }

    public void setParamIsActive(Number paramIsActive)
    {
        this.paramIsActive = paramIsActive;
    }

    public Number getParamIsActive()
    {
        return paramIsActive;
    }

    public void setAuthor(String author)
    {
        this.author = author;
    }

    public String getAuthor()
    {
        return author;
    }

    public List<KeyValuePairObj> getReferenceKeysForDependents()
    {
        List<KeyValuePairObj> rks = new ArrayList<KeyValuePairObj>(1);
        rks.add(new KeyValuePairObj(ParamSourceId,
                                    this.paramSourceId));
        rks.add(new KeyValuePairObj(ParamName,
                                    this.paramName));
        return rks;
    }

    // when pattern is removed it is no longer refs params
    // if there is still a reference by another pattern
    // this will be reset to active during params checking
    public void handleRemove()
    {
        // no longer an active pattern
        // will be deleted when pattern is deleted
        setParamActive(false);
        setParamIsActive(new Number(0));
    }
    
}
