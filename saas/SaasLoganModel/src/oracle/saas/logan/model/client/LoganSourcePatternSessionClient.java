package oracle.saas.logan.model.client;

import java.util.Hashtable;
import java.util.List;

import javax.naming.CommunicationException;
import javax.naming.Context;
import javax.naming.InitialContext;

import javax.naming.NamingException;

import oracle.saas.logan.model.persistance.EmLoganSourcePattern1;
import oracle.saas.logan.model.session.source.LoganSourcePatternSession;

public class LoganSourcePatternSessionClient {
    public static void main(String[] args) {
        try {
            final Context context = getInitialContext();
            LoganSourcePatternSession loganSourcePatternSession =
                (LoganSourcePatternSession)context.lookup("saas-SaasLoganModel-LoganSourcePatternSession#oracle.saas.logan.model.session.source.LoganSourcePatternSession");
            for (EmLoganSourcePattern1 emlogansourcepattern1 :
                 (List<EmLoganSourcePattern1>)loganSourcePatternSession.getEmLoganSourcePattern1FindAll()) {
                printEmLoganSourcePattern1(emlogansourcepattern1);
            }
        } catch (CommunicationException ex) {
            System.out.println(ex.getClass().getName());
            System.out.println(ex.getRootCause().getLocalizedMessage());
            System.out.println("\n*** A CommunicationException was raised.  This typically\n*** occurs when the target WebLogic server is not running.\n");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private static void printEmLoganSourcePattern1(EmLoganSourcePattern1 emlogansourcepattern1) {
        System.out.println("patternAuthor = " + emlogansourcepattern1.getPatternAuthor());
        System.out.println("patternDescription = " + emlogansourcepattern1.getPatternDescription());
        System.out.println("patternDescriptionNlsid = " + emlogansourcepattern1.getPatternDescriptionNlsid());
        System.out.println("patternId = " + emlogansourcepattern1.getPatternId());
        System.out.println("patternIsInclude = " + emlogansourcepattern1.getPatternIsInclude());
        System.out.println("patternParserIname = " + emlogansourcepattern1.getPatternParserIname());
        System.out.println("patternSourceId = " + emlogansourcepattern1.getPatternSourceId());
        System.out.println("patternText = " + emlogansourcepattern1.getPatternText());
    }

    private static Context getInitialContext() throws NamingException {
        Hashtable env = new Hashtable();
        // WebLogic Server 10.x/12.x connection details
        env.put(Context.INITIAL_CONTEXT_FACTORY, "weblogic.jndi.WLInitialContextFactory");
        env.put(Context.PROVIDER_URL, "t3://127.0.0.1:7101");
        return new InitialContext(env);
    }
}
