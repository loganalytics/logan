package oracle.saas.logan.view.parser;

import java.util.Hashtable;
import java.util.List;

import javax.naming.CommunicationException;
import javax.naming.Context;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import oracle.saas.logan.model.persistance.EmLoganParser;
import oracle.saas.logan.model.session.parser.LoganParserSessionBean;

public class ParserListBean {
    public ParserListBean() {
        super();
    }
    public List<EmLoganParser> getLoganParserList(){
        List<EmLoganParser> allParsers = null;
        try {
            final Context context = getInitialContext();
            LoganParserSessionBean loganParserSessionBean =
                (LoganParserSessionBean) context.lookup("saas-SaasLoganModel-LoganParserSessionBean#oracle.saas.logan.model.session.parser.LoganParserSessionBean");
            allParsers = (List<EmLoganParser>) loganParserSessionBean.getEmLoganParserFindAll();
//            for (EmLoganParser emloganparser : allParsers) {
//                printEmLoganParser(emloganparser);
//            }
        } catch (CommunicationException ex) {
            System.out.println(ex.getClass().getName());
            System.out.println(ex.getRootCause().getLocalizedMessage());
            System.out.println("\n*** A CommunicationException was raised.  This typically\n*** occurs when the target WebLogic server is not running.\n");
        } catch (Exception ex) {
            ex.printStackTrace();
        }            
        return allParsers;
    }
    private Context getInitialContext() throws NamingException {
        Hashtable env = new Hashtable();
        // WebLogic Server 10.x/12.x connection details
        env.put(Context.INITIAL_CONTEXT_FACTORY, "weblogic.jndi.WLInitialContextFactory");
        env.put(Context.PROVIDER_URL, "t3://127.0.0.1:7101");
        return new InitialContext(env);
    }    
}
