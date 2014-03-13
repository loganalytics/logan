package oracle.saas.logan.model.client;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import oracle.saas.logan.model.persistance.EmLoganRuleSourceMap;
import oracle.saas.logan.model.persistance.EmTargetTypes;
import oracle.saas.logan.model.session.source.LoganRuleSourceMapSessionBean;
import oracle.saas.logan.model.session.source.LoganSourceSessionBean;
import oracle.saas.logan.model.session.util.LoganTargetTypes;

public class LoganSessionBeanProxy {
    private  LoganSessionBeanProxy() {
        super();
    }

    private static Context context;
    static {
        try {
            context = getContext();
        } catch (NamingException e) {
            //TODO log
        }
    }

    @SuppressWarnings("oracle.jdeveloper.java.null-collection-return")
    public static List<EmTargetTypes> getEmTargetTypesFindAll()
    {
        List<EmTargetTypes> list =  new ArrayList<EmTargetTypes>();
        LoganTargetTypes loganTargetTypesFacade = null;
        try {
               loganTargetTypesFacade =  (LoganTargetTypes)context.lookup("saas-SaasLoganModel-LoganTargetTypes#oracle.saas.logan.model.session.util.LoganTargetTypes");
        } catch (NamingException e) {
            //TODO log
        }
        if(loganTargetTypesFacade == null)
        {
            return list;
        } else{
            list = loganTargetTypesFacade.getEmTargetTypesFindAll();
            return  list;
        }
    }
    
    
    public static List<Object[]> getEmLoganSourceFindSources()
    {
        List<Object[]> list =  new ArrayList<Object[]>();
        LoganSourceSessionBean loganSourceSessionBean = null;
        try {
             loganSourceSessionBean =
                (LoganSourceSessionBean)context.lookup("saas-SaasLoganModel-LoganSourceSessionBean#oracle.saas.logan.model.session.source.LoganSourceSessionBean");
            
        } catch (NamingException e) {
            //TODO log
        }
        if(loganSourceSessionBean == null)
        {
            return list;
        } else{
            list = loganSourceSessionBean.getEmLoganSourceFindSources();
            return  list;
        }
    }
    
    
    public static List<EmLoganRuleSourceMap> getEmLoganRuleSourceMap()
    {
        List<EmLoganRuleSourceMap> list =  new ArrayList<EmLoganRuleSourceMap>();
         LoganRuleSourceMapSessionBean  loganRuleSourceMapSessionBean= null;
        try {
             loganRuleSourceMapSessionBean =
                (LoganRuleSourceMapSessionBean)context.lookup("saas-SaasLoganModel-LoganRuleSourceMapSessionBean#oracle.saas.logan.model.session.source.LoganRuleSourceMapSessionBean");
        } catch (NamingException e) {
            //TODO log
        }
        if(loganRuleSourceMapSessionBean == null)
        {
            return list;
        } else{
            list = loganRuleSourceMapSessionBean.getEmLoganRuleSourceMapFindAll();
            return  list;
        }
    }
    
    

    
    //TODO replace the hard coded way
    private static Context getContext() throws NamingException {
        Hashtable env = new Hashtable();
        // WebLogic Server 10.x/12.x connection details
        env.put(Context.INITIAL_CONTEXT_FACTORY, "weblogic.jndi.WLInitialContextFactory");
        env.put(Context.PROVIDER_URL, "t3://127.0.0.1:7101");
        return new InitialContext(env);
    }
    
    
}
