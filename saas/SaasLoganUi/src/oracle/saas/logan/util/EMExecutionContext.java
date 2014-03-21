package oracle.saas.logan.util;

import java.util.Locale;
import java.util.TimeZone;

/**
 * For code migration .  Saas context could be refered here.
 * @author ming
 * */
public class EMExecutionContext {
    //
    // Constructors
    //
    public EMExecutionContext() {
        super();
    }

    //
    // Fields
    //
    private java.util.Locale locale;

    private java.util.TimeZone tz;

    private java.lang.String action;

    private java.lang.String clientId;

    private static java.lang.ThreadLocal tlCtxt;

    private static EMExecutionContext instance;


    //
    // Methods
    //
    public static EMExecutionContext getExecutionContext() {
        if (instance == null) {
            instance = new EMExecutionContext();
        }
        return instance;
    }

    public static void setExecutionContext(EMExecutionContext p1) {
        instance = p1;
    }

    public static void destroy() {
        instance = null;
    }

    public static void clearExecutionContext() {
        instance = null;
    }

    public java.sql.Connection getConnection() {
        return null;
    }

    public java.lang.String getEMUser() {
        return "SYSMAN";
    }

    public java.util.Locale getLocale() {
        if(locale == null)
        {
            locale = Locale.getDefault();
        }
        return locale;
    }

    public void setLocale(java.util.Locale p1) {
         locale = p1;
    }

    public java.util.TimeZone getDisplayTZ() {
        if(tz ==null)
        {
            tz = TimeZone.getDefault();
        }
        return tz;
    }

    public void setDisplayTZ(java.util.TimeZone p1) {
        tz = p1;
    }

//    private oracle.sysman.emSDK.sec.authz.EMSecurityContext getSecCtxt() {
//    }

    public java.lang.String getAction() {
        return action;
    }

    public void setAction(java.lang.String p1) {
        action = p1;
    }

    public java.lang.String getClientId() {
        return clientId;
    }

    public void setClientId(java.lang.String p1) {
        clientId = p1;
    }
}
