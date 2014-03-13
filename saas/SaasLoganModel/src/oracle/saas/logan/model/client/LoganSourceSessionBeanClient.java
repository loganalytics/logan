package oracle.saas.logan.model.client;

import java.util.Hashtable;
import java.util.List;

import javax.naming.CommunicationException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import oracle.saas.logan.model.persistance.EmLoganMetaSourceType;
import oracle.saas.logan.model.persistance.EmLoganSource;
import oracle.saas.logan.model.persistance.EmTargetTypes;
import oracle.saas.logan.model.session.source.LoganSourceSessionBean;

public class LoganSourceSessionBeanClient {
    public static void main(String[] args) {
        try {
            final Context context = getInitialContext();
            LoganSourceSessionBean loganSourceSessionBean =
                (LoganSourceSessionBean)context.lookup("saas-SaasLoganModel-LoganSourceSessionBean#oracle.saas.logan.model.session.source.LoganSourceSessionBean");
            
            for (EmLoganSource emLoganSource : (List<EmLoganSource>) loganSourceSessionBean.getEmLoganSourceFindAll()) {
                printEmLoganSource(emLoganSource);
            }
            
            List<Object[]> list = loganSourceSessionBean.getEmLoganSourceFindSources();
            
            for (Object[] obj : list) {
                EmLoganSource emLoganSource = (EmLoganSource)obj[0];
                EmLoganMetaSourceType emLoganMetaSourceType = (EmLoganMetaSourceType)obj[1];
                EmTargetTypes emTargetTypes = (EmTargetTypes)obj[2];
                
                printEmLoganSource(emLoganSource);
                printEmLoganMetaSourceType(emLoganMetaSourceType);
                printEmTargetTypes(emTargetTypes);
            }
            
        } catch (CommunicationException ex) {
            System.out.println(ex.getClass().getName());
            System.out.println(ex.getRootCause().getLocalizedMessage());
            System.out.println("\n*** A CommunicationException was raised.  This typically\n*** occurs when the target WebLogic server is not running.\n");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    private static void printEmLoganSource(EmLoganSource emLoganSource) {
        System.out.println(" sourceDname = " + emLoganSource.getSourceDname());
        System.out.println(" sourceIname = " + emLoganSource.getSourceIname());
    }
    
    private static void printEmLoganMetaSourceType(EmLoganMetaSourceType emLoganMetaSourceType) {
        System.out.println(" getSrctypeIname = " + emLoganMetaSourceType.getSrctypeIname());
        System.out.println(" getSrctypeDname = " + emLoganMetaSourceType.getSrctypeDname());
    }
    
    private static void printEmTargetTypes(EmTargetTypes emTargetTypes) {
        System.out.println(" getTargetType = " + emTargetTypes.getTargetType());
        System.out.println(" getTypeDisplayName = " + emTargetTypes.getTypeDisplayName());
    }
    

    private static Context getInitialContext() throws NamingException {
        Hashtable env = new Hashtable();
        // WebLogic Server 10.x/12.x connection details
        env.put(Context.INITIAL_CONTEXT_FACTORY, "weblogic.jndi.WLInitialContextFactory");
        env.put(Context.PROVIDER_URL, "t3://127.0.0.1:7101");
        return new InitialContext(env);
    }
}
