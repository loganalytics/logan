package oracle.saas.logan.model.client;

import java.util.Hashtable;
import java.util.List;

import javax.naming.CommunicationException;
import javax.naming.Context;
import javax.naming.InitialContext;

import javax.naming.NamingException;

import oracle.saas.logan.model.persistance.EmLoganRuleSourceMap;
import oracle.saas.logan.model.session.source.LoganRuleSourceMapSessionBean;

public class LoganRuleSourceMapSessionBeanClient {
    public static void main(String[] args) {
        try {
            final Context context = getInitialContext();
            LoganRuleSourceMapSessionBean loganRuleSourceMapSessionBean =
                (LoganRuleSourceMapSessionBean)context.lookup("saas-SaasLoganModel-LoganRuleSourceMapSessionBean#oracle.saas.logan.model.session.source.LoganRuleSourceMapSessionBean");
            for (EmLoganRuleSourceMap emloganrulesourcemap :
                 (List<EmLoganRuleSourceMap>)loganRuleSourceMapSessionBean.getEmLoganRuleSourceMapFindAll()) {
                printEmLoganRuleSourceMap(emloganrulesourcemap);
            }
        } catch (CommunicationException ex) {
            System.out.println(ex.getClass().getName());
            System.out.println(ex.getRootCause().getLocalizedMessage());
            System.out.println("\n*** A CommunicationException was raised.  This typically\n*** occurs when the target WebLogic server is not running.\n");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private static void printEmLoganRuleSourceMap(EmLoganRuleSourceMap emloganrulesourcemap) {
        System.out.println("rsRuleId = " + emloganrulesourcemap.getRsRuleId());
        System.out.println("rsSourceId = " + emloganrulesourcemap.getRsSourceId());
    }

    private static Context getInitialContext() throws NamingException {
        Hashtable env = new Hashtable();
        // WebLogic Server 10.x/12.x connection details
        env.put(Context.INITIAL_CONTEXT_FACTORY, "weblogic.jndi.WLInitialContextFactory");
        env.put(Context.PROVIDER_URL, "t3://127.0.0.1:7101");
        return new InitialContext(env);
    }
}
