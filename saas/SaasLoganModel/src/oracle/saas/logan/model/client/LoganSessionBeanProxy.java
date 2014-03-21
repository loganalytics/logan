package oracle.saas.logan.model.client;

import java.math.BigDecimal;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import oracle.saas.logan.model.persistance.EmLoganMetaAvailParam;
import oracle.saas.logan.model.persistance.EmLoganParser;
import oracle.saas.logan.model.persistance.EmLoganRuleSourceMap;
import oracle.saas.logan.model.persistance.EmLoganSource;
import oracle.saas.logan.model.persistance.EmLoganSourceParameter;
import oracle.saas.logan.model.persistance.EmLoganSourcePattern1;
import oracle.saas.logan.model.persistance.EmTargetTypes;
import oracle.saas.logan.model.session.parser.LoganParserSessionBean;
import oracle.saas.logan.model.session.source.EmLoganSourceParameterSession;
import oracle.saas.logan.model.session.source.LoganRuleSourceMapSessionBean;
import oracle.saas.logan.model.session.source.LoganSourcePatternSession;
import oracle.saas.logan.model.session.source.LoganSourceSessionBean;
import oracle.saas.logan.model.session.util.LoganMetaAvailParamSession;
import oracle.saas.logan.model.session.util.LoganTargetTypes;

public class LoganSessionBeanProxy {
    private LoganSessionBeanProxy() {
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
    public static List<EmTargetTypes> getEmTargetTypesFindAll() {
        List<EmTargetTypes> list = new ArrayList<EmTargetTypes>();
        LoganTargetTypes loganTargetTypesFacade = null;
        try {
            loganTargetTypesFacade =
                (LoganTargetTypes)context.lookup("saas-SaasLoganModel-LoganTargetTypes#oracle.saas.logan.model.session.util.LoganTargetTypes");
        } catch (NamingException e) {
            //TODO log
        }
        if (loganTargetTypesFacade == null) {
            return list;
        } else {
            list = loganTargetTypesFacade.getEmTargetTypesFindAll();
            return list;
        }
    }

    public static List<EmLoganMetaAvailParam> getEmLoganMetaAvailParamFindAll() {
        List<EmLoganMetaAvailParam> list = new ArrayList<EmLoganMetaAvailParam>();
        LoganMetaAvailParamSession loganMetaAvailParamSession = null;
        try {
            loganMetaAvailParamSession =
                (LoganMetaAvailParamSession)context.lookup("saas-SaasLoganModel-LoganMetaAvailParamSession#oracle.saas.logan.model.session.util.LoganMetaAvailParamSession");
        } catch (NamingException e) {
            //TODO log
        }
        if (loganMetaAvailParamSession == null) {
            return list;
        } else {
            list = loganMetaAvailParamSession.getEmLoganMetaAvailParamFindAll();
            return list;
        }
    }
    
    public static List<EmLoganMetaAvailParam> getEmLoganMetaAvailParamFindAllByTargetType(String targetType) {
        List<EmLoganMetaAvailParam> list = new ArrayList<EmLoganMetaAvailParam>();
        LoganMetaAvailParamSession loganMetaAvailParamSession = null;
        try {
            loganMetaAvailParamSession =
                (LoganMetaAvailParamSession)context.lookup("saas-SaasLoganModel-LoganMetaAvailParamSession#oracle.saas.logan.model.session.util.LoganMetaAvailParamSession");
        } catch (NamingException e) {
            //TODO log
        }
        if (loganMetaAvailParamSession == null) {
            return list;
        } else {
            list = loganMetaAvailParamSession.getEmLoganMetaAvailParamFindAllByTargetType(targetType);
            return list;
        }
    }
    


    public static List<Object[]> getEmLoganSourceFindSources() {
        List<Object[]> list = new ArrayList<Object[]>();
        LoganSourceSessionBean loganSourceSessionBean = null;
        try {
            loganSourceSessionBean =
                (LoganSourceSessionBean)context.lookup("saas-SaasLoganModel-LoganSourceSessionBean#oracle.saas.logan.model.session.source.LoganSourceSessionBean");

        } catch (NamingException e) {
            //TODO log
        }
        if (loganSourceSessionBean == null) {
            return list;
        } else {
            list = loganSourceSessionBean.getEmLoganSourceFindSources();
            return list;
        }
    }

    public static List<Object[]> getEmLoganSourceFilteredSourceList(String targetType, String logType, String dname,
                                                                    String desc, boolean matchAll) {

        List<Object[]> list = new ArrayList<Object[]>();
        LoganSourceSessionBean loganSourceSessionBean = null;
        try {
            loganSourceSessionBean =
                (LoganSourceSessionBean)context.lookup("saas-SaasLoganModel-LoganSourceSessionBean#oracle.saas.logan.model.session.source.LoganSourceSessionBean");

        } catch (NamingException e) {
            //TODO log
        }
        if (loganSourceSessionBean == null) {
            return list;
        } else {
            list = loganSourceSessionBean.getFilteredSourceList(targetType, logType, dname, desc, matchAll);
            return list;
        }
    }
    
    public static LoganSourceSessionBean getEmLoganSourceFacade()
    {
        LoganSourceSessionBean loganSourceSessionBean = null;
        try {
            loganSourceSessionBean =
                (LoganSourceSessionBean)context.lookup("saas-SaasLoganModel-LoganSourceSessionBean#oracle.saas.logan.model.session.source.LoganSourceSessionBean");

        } catch (NamingException e) {
            //TODO log
        }
        return loganSourceSessionBean;
    }
    
    public static List<EmLoganSource> getEmLoganSourceFindBySourceName(String sourceName) {
        List<EmLoganSource> list = new ArrayList<EmLoganSource>();
        LoganSourceSessionBean loganSourceSessionBean = null;
        try {
            loganSourceSessionBean =
                (LoganSourceSessionBean)context.lookup("saas-SaasLoganModel-LoganSourceSessionBean#oracle.saas.logan.model.session.source.LoganSourceSessionBean");

        } catch (NamingException e) {
            //TODO log
        }
        if (loganSourceSessionBean == null) {
            return list;
        }
        else {
            list = loganSourceSessionBean.getEmLoganSourceFindBySourceName(sourceName);
            return list;
        }
    }
    

    
    public static BigDecimal getNextSeqBySql(String nextSeqSql) {
        BigDecimal nextSeqNum = null;                                         
        LoganSourceSessionBean loganSourceSessionBean = null;
        try {
            loganSourceSessionBean =
                (LoganSourceSessionBean)context.lookup("saas-SaasLoganModel-LoganSourceSessionBean#oracle.saas.logan.model.session.source.LoganSourceSessionBean");
        } catch (NamingException e) {
            //TODO log
        }
        if (loganSourceSessionBean == null) {
            return nextSeqNum;
        } else {
            nextSeqNum =  loganSourceSessionBean.getEmLoganSourceNextSeq( nextSeqSql);
            return nextSeqNum;
        }
    }
    
    
    public static List<EmLoganSource> getEmLoganSourceFindAll() {
        List<EmLoganSource> list = new ArrayList<EmLoganSource>();
        LoganSourceSessionBean loganSourceSessionBean = null;
        try {
            loganSourceSessionBean =
                (LoganSourceSessionBean)context.lookup("saas-SaasLoganModel-LoganSourceSessionBean#oracle.saas.logan.model.session.source.LoganSourceSessionBean");

        } catch (NamingException e) {
            //TODO log
        }
        if (loganSourceSessionBean == null) {
            return list;
        } else {
            list = loganSourceSessionBean.getEmLoganSourceFindAll();
            return list;
        }
    }
    
    
    public static List<EmLoganSource> getEmLoganSourceFindBySourceId(Integer id) {
        List<EmLoganSource> list = new ArrayList<EmLoganSource>();
        LoganSourceSessionBean loganSourceSessionBean = null;
        try {
            loganSourceSessionBean =
                (LoganSourceSessionBean)context.lookup("saas-SaasLoganModel-LoganSourceSessionBean#oracle.saas.logan.model.session.source.LoganSourceSessionBean");

        } catch (NamingException e) {
            //TODO log
        }
        if (loganSourceSessionBean == null) {
            return list;
        } else {
            list = loganSourceSessionBean.getEmLoganSourceFindBySourceId(id);
            return list;
        }
    }
    
    
    public static LoganParserSessionBean getEmLoganParserFacade()
    {
        LoganParserSessionBean loganParserSessionBean = null;
        try {
             loganParserSessionBean =
                (LoganParserSessionBean) context.lookup("saas-SaasLoganModel-LoganParserSessionBean#oracle.saas.logan.model.session.parser.LoganParserSessionBean");
        } catch (NamingException e) {
            //TODO log
        }
        return loganParserSessionBean;
    }
    
    
    public static List<EmLoganParser> getEmLoganParserFindAll() {
        List<EmLoganParser> list = new ArrayList<EmLoganParser>();
        LoganParserSessionBean loganParserSessionBean = null;
        try {
             loganParserSessionBean =
                (LoganParserSessionBean) context.lookup("saas-SaasLoganModel-LoganParserSessionBean#oracle.saas.logan.model.session.parser.LoganParserSessionBean");
        } catch (NamingException e) {
            //TODO log
        }
        if (loganParserSessionBean == null) {
            return list;
        } else {
            list = loganParserSessionBean.getEmLoganParserFindAll();
            return list;
        }
    }
    
    
    public static List<Object[]> getEmLoganSourcePatternFindAllBySourceId(Integer isIncl, Integer sourceId) {
            List<Object[]> list = new ArrayList<Object[]>();
            LoganSourcePatternSession loganSourcePatternSession = null;
            try {
                 loganSourcePatternSession =
                    (LoganSourcePatternSession)context.lookup("saas-SaasLoganModel-LoganSourcePatternSession#oracle.saas.logan.model.session.source.LoganSourcePatternSession");
            } catch (NamingException e) {
                //TODO log
            }
            if (loganSourcePatternSession == null) {
                return list;
            } else {
                list = loganSourcePatternSession.getEmLoganSourcePatternFindAllBySourceId(isIncl, sourceId);
                return list;
            }
        }
    
    
    public static LoganSourcePatternSession getEmLoganSourcePatternFacade()
    {
        LoganSourcePatternSession loganSourcePatternSession = null;
        try {
             loganSourcePatternSession =
                (LoganSourcePatternSession)context.lookup("saas-SaasLoganModel-LoganSourcePatternSession#oracle.saas.logan.model.session.source.LoganSourcePatternSession");
        } catch (NamingException e) {
            //TODO log
        }
        return loganSourcePatternSession;
    }
    
    
    public static List<EmLoganSourcePattern1> getEmLoganSourcePatternFindAll() {
            List<EmLoganSourcePattern1> list = new ArrayList<EmLoganSourcePattern1>();
            LoganSourcePatternSession loganSourcePatternSession = null;
            try {
                 loganSourcePatternSession =
                    (LoganSourcePatternSession)context.lookup("saas-SaasLoganModel-LoganSourcePatternSession#oracle.saas.logan.model.session.source.LoganSourcePatternSession");
            } catch (NamingException e) {
                //TODO log
            }
            if (loganSourcePatternSession == null) {
                return list;
            } else {
                list = loganSourcePatternSession.getEmLoganSourcePattern1FindAll();
                return list;
            }
        }
    
        
    public static List<EmLoganRuleSourceMap> getEmLoganRuleSourceMap() {
        List<EmLoganRuleSourceMap> list = new ArrayList<EmLoganRuleSourceMap>();
        LoganRuleSourceMapSessionBean loganRuleSourceMapSessionBean = null;
        try {
            loganRuleSourceMapSessionBean =
                (LoganRuleSourceMapSessionBean)context.lookup("saas-SaasLoganModel-LoganRuleSourceMapSessionBean#oracle.saas.logan.model.session.source.LoganRuleSourceMapSessionBean");
        } catch (NamingException e) {
            //TODO log
        }
        if (loganRuleSourceMapSessionBean == null) {
            return list;
        } else {
            list = loganRuleSourceMapSessionBean.getEmLoganRuleSourceMapFindAll();
            return list;
        }
    }
    
    public static List<EmLoganSourceParameter> getEmLoganSourceParameterFindAllBySourceId(Integer sourceId) {
            List<EmLoganSourceParameter> list = new ArrayList<EmLoganSourceParameter>();
            EmLoganSourceParameterSession emLoganSourceParameterSession = null;
            try {
                 emLoganSourceParameterSession =
                    (EmLoganSourceParameterSession)context.lookup("saas-SaasLoganModel-EmLoganSourceParameterSession#oracle.saas.logan.model.session.source.EmLoganSourceParameterSession");
            } catch (NamingException e) {
                //TODO log
            }
            if (emLoganSourceParameterSession == null) {
                return list;
            } else {
                list = emLoganSourceParameterSession.getEmLoganSourceParameterFindAllBySourceId(sourceId);
                return list;
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
