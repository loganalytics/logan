package oracle.saas.logan.view.rule;

import java.util.Hashtable;
import java.util.List;

import javax.naming.CommunicationException;
import javax.naming.Context;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import oracle.saas.logan.model.persistance.rule.EmLoganRule;
import oracle.saas.logan.model.session.rule.LoganRuleSession;

public class RuleListBean {
    public RuleListBean() {
        super();
    }
    public List<EmLoganRule> getLoganRuleList(){
        List<EmLoganRule> allRules = null;
        try {
            final Context context = getInitialContext();
            LoganRuleSession loganRuleSession =
                (LoganRuleSession) context.lookup("saas-SaasLoganModel-LoganRuleSession#oracle.saas.logan.model.session.rule.LoganRuleSession");
            allRules = loganRuleSession.getEmLoganRuleFindAll();
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
        return allRules;
    }
    private Context getInitialContext() throws NamingException {
        Hashtable env = new Hashtable();
        // WebLogic Server 10.x/12.x connection details
        env.put(Context.INITIAL_CONTEXT_FACTORY, "weblogic.jndi.WLInitialContextFactory");
        env.put(Context.PROVIDER_URL, "t3://127.0.0.1:7101");
        return new InitialContext(env);
    }    
}
