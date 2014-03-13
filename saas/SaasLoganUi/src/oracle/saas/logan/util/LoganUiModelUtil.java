package oracle.saas.logan.util;

import java.text.MessageFormat;

import java.util.Locale;
import java.util.ResourceBundle;

public class LoganUiModelUtil {
    public LoganUiModelUtil() {
        super();
    }

    public static final String S_UI_MSG_CLASS = "oracle.saas.logan.view.SaasLoganUiBundle";

    public static String getUiString(String msgID) {
        return getString(S_UI_MSG_CLASS, msgID);
    }

    public static String getString(String bundleName, String msgID) {
        return getString(bundleName, msgID, (Object[])null);
    }

    public static String getString(String bundleName, String msgID, Object[] args) {
        String str = null;
        try {
            //TODO use sdk provided locale
            //Locale locale = EMExecutionContext.getExecutionContext().getLocale();
            Locale locale = Locale.getDefault();
            ResourceBundle msgs = ResourceBundle.getBundle(bundleName, locale);
            str = msgs.getString(msgID);
            if (args != null) {
                MessageFormat msgFormat = new MessageFormat(str);
                str = msgFormat.format(args);
            }
        } catch (Exception e) {

            StringBuilder sb = new StringBuilder();
            sb.append("Error translating ").append(bundleName).append("<");
            sb.append(msgID);
            sb.append(">");
            sb.append(e.getMessage());
            //TODO log handler from sdk
            System.err.print(sb.toString());

            str = msgID;
            if (str == null) {
                str = e.getMessage();
            }
        }
        return str;
    }

    public static Locale getBrowserLocale() {
        //TODO use sdk provided locale
        //        return EMExecutionContext.getExecutionContext().getLocale();
        return Locale.getDefault();
    }


}
