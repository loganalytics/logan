package oracle.saas.logan.model.util;

import java.text.MessageFormat;

import java.util.Locale;
import java.util.ResourceBundle;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import javax.servlet.http.HttpServletRequest;

public class LoganModelUtil {
    
    public static final String S_UI_MSG_CLASS = 
                                "oracle.saas.logan.model.CoreLoganalyticsUiModelMsg";
    
    public LoganModelUtil() {
        super();
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
                MessageFormat msgFormat = new MessageFormat(str);
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

    public static Locale getBrowserLocale()
    {
        ExternalContext ectx =
            FacesContext.getCurrentInstance().getExternalContext();
        HttpServletRequest req = (HttpServletRequest) ectx.getRequest();
        return req.getLocale();
    }
    
}
