package oracle.saas.logan.model.client;

import java.util.Hashtable;
import java.util.List;

import javax.naming.CommunicationException;
import javax.naming.Context;
import javax.naming.InitialContext;

import javax.naming.NamingException;

import oracle.saas.logan.model.persistance.EmLoganMetaOperator;
import oracle.saas.logan.model.session.operator.LoganOperatorSessionBean;

public class LoganOperatorSessionBeanClient {
    public static void main(String[] args) {
        try {
            final Context context = getInitialContext();
            LoganOperatorSessionBean loganOperatorSessionBean =
                (LoganOperatorSessionBean) context.lookup("saas-SaasLoganModel-LoganOperatorSessionBean#oracle.saas.logan.model.session.operator.LoganOperatorSessionBean");
            for (EmLoganMetaOperator emloganmetaoperator :
                 (List<EmLoganMetaOperator>) loganOperatorSessionBean.getEmLoganMetaOperatorFindAll()) {
                printEmLoganMetaOperator(emloganmetaoperator);
            }
        } catch (CommunicationException ex) {
            System.out.println(ex.getClass().getName());
            System.out.println(ex.getRootCause().getLocalizedMessage());
            System.out.println("\n*** A CommunicationException was raised.  This typically\n*** occurs when the target WebLogic server is not running.\n");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private static void printEmLoganMetaOperator(EmLoganMetaOperator emloganmetaoperator) {
        System.out.println("operDatatype = " + emloganmetaoperator.getOperDatatype());
        System.out.println("operName = " + emloganmetaoperator.getOperName());
        System.out.println("operSql = " + emloganmetaoperator.getOperSql());
    }

    private static Context getInitialContext() throws NamingException {
        Hashtable env = new Hashtable();
        // WebLogic Server 10.x/12.x connection details
        env.put(Context.INITIAL_CONTEXT_FACTORY, "weblogic.jndi.WLInitialContextFactory");
        env.put(Context.PROVIDER_URL, "t3://127.0.0.1:7101");
        return new InitialContext(env);
    }
}
