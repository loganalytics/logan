package oracle.saas.logan.model.client;

import java.util.Hashtable;
import java.util.List;

import javax.naming.CommunicationException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import oracle.saas.logan.model.persistance.EmLoganSourceParameter;
import oracle.saas.logan.model.session.source.EmLoganSourceParameterSession;

public class EmLoganSourceParameterSessionClient {
    public static void main(String[] args) {
        try {
            final Context context = getInitialContext();
            EmLoganSourceParameterSession emLoganSourceParameterSession =
                (EmLoganSourceParameterSession)context.lookup("saas-SaasLoganModel-EmLoganSourceParameterSession#oracle.saas.logan.model.session.source.EmLoganSourceParameterSession");
            for (EmLoganSourceParameter emlogansourceparameter :
                 (List<EmLoganSourceParameter>)emLoganSourceParameterSession.getEmLoganSourceParameterFindAll()) {
                printEmLoganSourceParameter(emlogansourceparameter);
            }
            for (EmLoganSourceParameter emlogansourceparameter :
                 (List<EmLoganSourceParameter>)emLoganSourceParameterSession.getEmLoganSourceParameterFindAllBySourceId(10000) /* FIXME: Pass parameters here */) {
                printEmLoganSourceParameter(emlogansourceparameter);
            }
        } catch (CommunicationException ex) {
            System.out.println(ex.getClass().getName());
            System.out.println(ex.getRootCause().getLocalizedMessage());
            System.out.println("\n*** A CommunicationException was raised.  This typically\n*** occurs when the target WebLogic server is not running.\n");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private static void printEmLoganSourceParameter(EmLoganSourceParameter emlogansourceparameter) {
        System.out.println("paramAuthor = " + emlogansourceparameter.getParamAuthor());
        System.out.println("paramDefaultValue = " + emlogansourceparameter.getParamDefaultValue());
        System.out.println("paramDescription = " + emlogansourceparameter.getParamDescription());
        System.out.println("paramDescriptionNlsid = " + emlogansourceparameter.getParamDescriptionNlsid());
        System.out.println("paramIsActive = " + emlogansourceparameter.getParamIsActive());
        System.out.println("paramName = " + emlogansourceparameter.getParamName());
        System.out.println("paramNameNlsid = " + emlogansourceparameter.getParamNameNlsid());
        System.out.println("paramSourceId = " + emlogansourceparameter.getParamSourceId());
    }
    
    private static Context getInitialContext() throws NamingException {
        Hashtable env = new Hashtable();
        // WebLogic Server 10.x/12.x connection details
        env.put(Context.INITIAL_CONTEXT_FACTORY, "weblogic.jndi.WLInitialContextFactory");
        env.put(Context.PROVIDER_URL, "t3://127.0.0.1:7101");
        return new InitialContext(env);
    }
}
