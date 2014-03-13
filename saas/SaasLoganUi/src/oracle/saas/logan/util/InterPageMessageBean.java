/* $Header: emcore/source/oracle/sysman/emSDK/pagemodel/InterPageMessageBean.java /main/11 2013/11/06 10:01:26 mgrumbac Exp $ */

/* Copyright (c) 2006, 2013, Oracle and/or its affiliates. 
All rights reserved.*/

/*
   DESCRIPTION
    <short description of component this file declares/defines>

   PRIVATE CLASSES
    <list of private classes defined - with one-line descriptions>

   NOTES
    <other useful comments, qualifications, etc.>

   MODIFIED    (MM/DD/YY)
    mgrumbac    10/15/13 - improve error handling
    mgrumbac    09/17/07 - Remove Variable Resolver
    tyhorton    02/28/07 - Adding convenience method getCurrentInstance
    nigupta     02/23/07 - add toString method
    dankim      08/15/06 - modify setParams to add entire contents of map with
                           current params map
    nigupta     08/18/06 - add MANAGED_BEAN_NAME
    dankim      05/31/06 - change so values aren't propogated 
    dankim      05/03/06 - new bean for parameter passing 
    dankim      05/03/06 - Creation
 */

/**
 *  @version $Header: emcore/source/oracle/sysman/emSDK/pagemodel/InterPageMessageBean.java /main/11 2013/11/06 10:01:26 mgrumbac Exp $
 *  @author  dankim  
 *  @since   release specific (what release of product did this appear in)
 */

package oracle.saas.logan.util;


import java.util.HashMap;
import java.util.Map;

import javax.faces.application.Application;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;


/**
 * This bean will be used for parameter passing between pages.
 */
@ManagedBean(name="sdk_ipmbean", eager=true)
@ApplicationScoped
public class InterPageMessageBean
{
    private Map params;
    
  /**
   * The name with which this bean is registered in adfc-config.xml as a 
   * managed bean. 
   */
  public static final String MANAGED_BEAN_NAME = "sdk_ipmbean";
  
    
    public InterPageMessageBean()
    {
        params = new HashMap();
        Map sdk_navi_param_query_params = new HashMap();
        params.put("sdk_navi_param_query_params", sdk_navi_param_query_params);
    }

    /**
     * @param params
     */
    public void setParams(Map params)
    {
        if(params == null)
        {
            this.params = params;
        }
        else
        {
            this.params.putAll(params);
        }
    }

    /**
     * @return
     */
    public Map getParams()
    {
        return params;
    }

    /**
     * Convenience method to access the current 
     * <code>InterPageMessageBean</code>.  Finding this bean is accomplished by 
     * resolving the managed bean named "sdk_ipmbean" from the current 
     * <code>FacesContext</code>. Caller is responsible for calling this method
     * only in Faces context.
     * 
     * @return The current <code>InterPageMessageBean</code> if avialable.
     * @throws IllegalStateException if bean instance unavailable.
     */
    public static InterPageMessageBean getCurrentInstance()
        throws IllegalStateException
    {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        if (facesContext == null)
            throw new IllegalStateException("Caller is not in Faces Context.");

        InterPageMessageBean bean = null;
        try 
        {
            Application application = facesContext.getApplication();
            bean = (InterPageMessageBean)
                        application.evaluateExpressionGet(facesContext,
                                "#{" + MANAGED_BEAN_NAME + "}", Object.class);
        }
        catch (Exception e) 
        {
            IllegalStateException ise = 
                new IllegalStateException("Invalid state! <ipmBean>", e);
            throw(ise);
        }

        if (bean == null)
        {
            IllegalStateException ise = 
                new IllegalStateException("Bean unavailable! <ipmBean>");
            throw(ise);
        }
        
        return(bean);
    }

    /**
     * @return
     */
    public String toString() 
    {
        StringBuffer sb = new StringBuffer(50);
        sb.append("hashCode=").append(hashCode()).append(", ");
        sb.append("params=").append(params);
        
        return sb.toString();
    }

}
