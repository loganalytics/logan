/*
   DESCRIPTION
   UIUtil

   PRIVATE CLASSES
    <list of private classes defined - with one-line descriptions>

   NOTES
    <other useful comments, qualificationsf, etc.>
    MODIFIED    (MM/DD/YY)
    kchiasso    12/20/13 - add calendar method
    vivsharm    10/18/13 - moved the builtin param check to the ui bean
    kchiasso    05/21/13 - log Analytics
 */
package oracle.saas.logan.util;

import java.text.MessageFormat;

import java.util.Calendar;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;


import javax.faces.component.UIComponent;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import javax.servlet.http.HttpServletRequest;
import oracle.adf.view.rich.context.AdfFacesContext;


import org.apache.myfaces.trinidad.context.RequestContext;


public class UiUtil
{
    protected static int WARNMSG=0;
    protected static int ERRMSG=1;

    public static final String S_UI_MSG_CLASS = 
                                "oracle.saas.logan.view.CoreLoganalyticsUiMsg";
  
  
    public static Locale getBrowserLocale()
    {
        ExternalContext ectx =
            FacesContext.getCurrentInstance().getExternalContext();
        HttpServletRequest req = (HttpServletRequest) ectx.getRequest();
        return req.getLocale();
    }

    public static ResourceBundle getLoganBundle()
    {
        return ResourceBundle.getBundle(S_UI_MSG_CLASS,
                                        getBrowserLocale());
    }
    
   
    /**
     * return pageFlowScope
     * @return
     */
    public static boolean isLoganBrowserContext()
    {
        String tableName = (String) getPFS().get(UiConst.PFS_CURRENTTABLE);
        return UiConst.LOG_BROWSER_TABLE.equals(tableName);
    }
    
    /**
     * return pageFlowScope
     * @return
     */
    public static Map getPFS()
    {
        Map pageFlowScope =
            RequestContext.getCurrentInstance().getPageFlowScope();
        return pageFlowScope;
    }
    
    /**
     * Locate an UIComponent from its root component.
     * Taken from http://www.jroller.com/page/mert?entry=how_to_find_a_uicomponent
     * @param base root Component (parent)
     * @param id UIComponent id
     * @return UIComponent object
     */
    public static UIComponent findComponent(UIComponent base, String id)
    {
        if (id.equals(base.getId()))
            return base;

        UIComponent children = null;
        UIComponent result = null;
        Iterator childrens = base.getFacetsAndChildren();
        while (childrens.hasNext() && (result == null))
        {
            children = (UIComponent) childrens.next();
            if (id.equals(children.getId()))
            {
                result = children;
                break;
            }
            result = findComponent(children, id);
            if (result != null)
            {
                break;
            }
        }
        return result;
    }
    /**
     * Locate an UIComponent in view root with its component id. Use a recursive way to achieve this.
     * @param id UIComponent id
     * @return UIComponent object
     */
    public static UIComponent findComponentInRoot(String id)
    {
        UIComponent component = null;
        FacesContext facesContext = FacesContext.getCurrentInstance();
        if (facesContext != null)
        {
            UIComponent root = facesContext.getViewRoot();
            component = findComponent(root, id);
        }
        return component;
    }
    
    
    /**
     * ppr refresh
     * @param component
     */
    public static void doRefresh(String component) {        
      UIComponent layout =  findComponentInRoot(component);
      if (layout != null)
             RequestContext.getCurrentInstance().addPartialTarget(layout);  
           
    }
    
    /**
     * ppr refresh
     * @param component
     */
    public static void doRefresh(UIComponent component) {        
      if (component != null)
          AdfFacesContext.getCurrentInstance().addPartialTarget(component);         
    }
    


    public static Calendar getCalendarInstance()
    {
        return Calendar.getInstance(getBrowserLocale());
    }
    
    public static String getUiString(String msgID) {
        return getString(S_UI_MSG_CLASS, msgID);
    }
    
    public static String getString(String bundleName, String msgID)
    {
        return getString(bundleName, msgID, (Object[]) null);
    }

    public static String getString(String   bundleName,
                                   String   msgID, 
                                   Object[] args)
    {
        String str = null;
        try
        {
            Locale locale = getBrowserLocale();
            ResourceBundle msgs = ResourceBundle.getBundle(bundleName, locale);
            str = msgs.getString(msgID);
            if (args != null)
            {
                MessageFormat msgFormat = new MessageFormat(str, locale);
                str = msgFormat.format(args);
            }

        }
        catch (Exception e)
        {
            
                StringBuilder sb = new StringBuilder();
                sb.append("Error translating ").append(bundleName).append("<");
                sb.append(msgID);
                sb.append(">");
                sb.append(e.getMessage());
                System.out.println(sb.toString());
            str = msgID;
            if (str == null)
            {
                str = e.getMessage();
            }
        }        
        return str;
    }
    
    
}
