package oracle.saas.logan.model.client;

import java.util.Hashtable;
import java.util.List;

import javax.naming.CommunicationException;
import javax.naming.Context;
import javax.naming.InitialContext;

import javax.naming.NamingException;

import oracle.saas.logan.model.persistance.EmLoganMetaAvailParam;
import oracle.saas.logan.model.session.util.LoganMetaAvailParamSession;

public class LoganMetaAvailParamSessionClient {
    public static void main(String[] args) {
        try {
            final Context context = getInitialContext();
            LoganMetaAvailParamSession loganMetaAvailParamSession =
                (LoganMetaAvailParamSession)context.lookup("saas-SaasLoganModel-LoganMetaAvailParamSession#oracle.saas.logan.model.session.util.LoganMetaAvailParamSession");
            for (EmLoganMetaAvailParam emloganmetaavailparam :
                 (List<EmLoganMetaAvailParam>)loganMetaAvailParamSession.getEmLoganMetaAvailParamFindAll()) {
                printEmLoganMetaAvailParam(emloganmetaavailparam);
            }
        } catch (CommunicationException ex) {
            System.out.println(ex.getClass().getName());
            System.out.println(ex.getRootCause().getLocalizedMessage());
            System.out.println("\n*** A CommunicationException was raised.  This typically\n*** occurs when the target WebLogic server is not running.\n");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private static void printEmLoganMetaAvailParam(EmLoganMetaAvailParam emloganmetaavailparam) {
        System.out.println("apAuthor = " + emloganmetaavailparam.getApAuthor());
        System.out.println("apDescription = " + emloganmetaavailparam.getApDescription());
        System.out.println("apDescriptionNlsid = " + emloganmetaavailparam.getApDescriptionNlsid());
        System.out.println("apEditVersion = " + emloganmetaavailparam.getApEditVersion());
        System.out.println("apName = " + emloganmetaavailparam.getApName());
        System.out.println("apPluginId = " + emloganmetaavailparam.getApPluginId());
        System.out.println("apPluginVersion = " + emloganmetaavailparam.getApPluginVersion());
        System.out.println("apTargetType = " + emloganmetaavailparam.getApTargetType());
        System.out.println("apType = " + emloganmetaavailparam.getApType());
    }

    private static Context getInitialContext() throws NamingException {
        Hashtable env = new Hashtable();
        // WebLogic Server 10.x/12.x connection details
        env.put(Context.INITIAL_CONTEXT_FACTORY, "weblogic.jndi.WLInitialContextFactory");
        env.put(Context.PROVIDER_URL, "t3://127.0.0.1:7101");
        return new InitialContext(env);
    }
}
