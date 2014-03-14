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

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.faces.component.UIComponent;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import javax.servlet.http.HttpServletRequest;

import oracle.adf.model.BindingContext;
import oracle.adf.model.binding.DCBindingContainer;
import oracle.adf.view.rich.context.AdfFacesContext;


import org.apache.myfaces.trinidad.context.RequestContext;


public class UiUtil
{
    private static final Logger s_log =
        Logger.getLogger(UiUtil.class.getName());
    protected static int WARNMSG=0;
    protected static int ERRMSG=1;

    public static final String S_UI_MSG_CLASS = 
                                "oracle.saas.logan.view.SaasLoganUiBundle";
  
  
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
            Locale locale = AdfUtil.getClientLocale();
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
                if(s_log.isLoggable(Level.FINEST))
            {
                StringBuilder sb = new StringBuilder();
                sb.append("Error translating ").append(bundleName).append("<");
                sb.append(msgID);
                sb.append(">");
                sb.append(e.getMessage());
                s_log.finest(sb.toString());
            }
            str = msgID;
            if (str == null)
            {
                str = e.getMessage();
            }
        }        
        return str;
    }

    public static void setIpmBean(Map params)
    {
        InterPageMessageBean ipmBean =
            InterPageMessageBean.getCurrentInstance();
        if (ipmBean == null)
            ipmBean = new InterPageMessageBean();
        
        ipmBean.setParams(params);
    }

    /**
     * return IpmBean
     * @return
     */
    public static Map getIpmBean()
    {
        InterPageMessageBean ipmBean = InterPageMessageBean.getCurrentInstance();
        return ipmBean.getParams();
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
    
    /**
     * show warning message dialog
     * @param rb
     * @param errHdrId
     * @param errMsgId
     */
    public static void displayWarningMsg(ResourceBundle rb, String errHdrId, String errMsgId)
    {
        displayMsg(rb, errHdrId, errMsgId, null, WARNMSG);
    }


    /**
     * show error message dialog
     * @param rb
     * @param errHdrId
     * @param errMsgId
     */
    public static void displayErrorMsg(ResourceBundle rb, String errHdrId, String errMsgId)
    {
        displayMsg(rb, errHdrId, errMsgId, null, ERRMSG);
    }


    /**
     * show error message dialog w insert {0}
     * @param rb
     * @param errHdrId
     * @param errMsgId
     * @param args
     */   
    public static void displayErrorMsg(ResourceBundle rb, String errHdrId, String errMsgId, Object[] args)
    {
        displayMsg( rb, errHdrId, errMsgId, args, ERRMSG);               
    }
    
    
    
    /**
     * Pass msg type to display appropriate header
     * @param rb
     * @param errHdrId
     * @param errMsgId
     * @param args
     * @param type
     */
    public static void displayMsg(ResourceBundle rb, String errHdrId, String errMsgId, Object[] args, int type)
    {
        String errMsg = null;
        
        if (args == null)
            errMsg = rb.getString(errMsgId);
        else
        {
            errMsg =
                MessageFormat.format(rb.getString(errMsgId),
                                     args);
        }
        
        String errHdr = rb.getString(errHdrId);
        
        if (type == WARNMSG)
            AdfUtil.showMessageDialog(null,
                                      AdfUtil.MESSAGE_TYPE.WARNING,
                                      errHdr, errMsg, false, null);
        else // ERRMSG
            AdfUtil.showMessageDialog(null,
                                      AdfUtil.MESSAGE_TYPE.ERROR,
                                      errHdr, errMsg, false, null);
                     
    }

    public static Calendar getCalendarInstance()
    {
        return Calendar.getInstance(getBrowserLocale());
    }
    
    
    public static boolean isMatch(String source, String submittedValue)
    {
        if (submittedValue == null || "".equals(submittedValue) ||
            (source == null || "".equals(source)))
        {
            return true;
        }
        Locale l = AdfUtil.getClientLocale();
        return source.toLowerCase(l).contains(submittedValue.toLowerCase(l));
    }
}
