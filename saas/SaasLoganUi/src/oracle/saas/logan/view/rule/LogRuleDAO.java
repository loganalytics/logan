package oracle.saas.logan.view.rule;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;

import java.util.logging.Logger;
import oracle.jbo.domain.Number;

import oracle.saas.logan.model.persistance.EmLoganRuleSourceMap;
import oracle.saas.logan.model.persistance.EmLoganSource;
import oracle.saas.logan.model.persistance.rule.EmLoganRule;
import oracle.saas.logan.model.session.rule.LoganRuleSession;
import oracle.saas.logan.model.session.source.LoganRuleSourceMapSessionBean;
import oracle.saas.logan.model.session.source.LoganSourceSessionBean;
import oracle.saas.logan.util.LoganLibUiUtil;

public class LogRuleDAO {
    
    private static final Logger s_log =
        Logger.getLogger(LogRuleDAO.class.getName());
    
    
    public LogRuleDAO() {
        super();
    }
    
    public static List<LoganRuleBean> getAllLogRules()
    {
        LoganRuleSession facadeEJB = LoganLibUiUtil.getLogRulesSessionFacadeEJB();
        List<LoganRuleBean> logPs = null;
        if(facadeEJB == null)
            return logPs;
        try
        {
            List<EmLoganRule> logParsers = facadeEJB.getEmLoganRuleFindAll();
            if(logParsers != null)
            {
                logPs = new ArrayList<LoganRuleBean>();
                for(EmLoganRule p : logParsers)
                {
                    logPs.add(new LoganRuleBean(p));
                }
            }
        }
        catch(Exception e)
        {
            s_log.logp(Level.WARNING,LoganLibUiUtil.class.getName(),"getAllLogParsers",
                       "*** Error occurred trying to get  getLogParserRow: ", e);
        }
        return logPs;
    }
    
    /**
     * @param parserId
     * @return
     */
    public static LoganRuleBean getLogRuleRow(Integer ruleId)
    {
        LoganRuleSession facadeEJB = LoganLibUiUtil.getLogRulesSessionFacadeEJB();
        LoganRuleBean logRule = null;
        if(facadeEJB == null)
            return logRule;
        try
        {
            List<EmLoganRule> rList = facadeEJB.getEmLoganRuleFindByRuleId(ruleId);
            if(rList != null && rList.size() > 0)
                logRule = new LoganRuleBean(rList.get(0));
        }
        catch(Exception e)
        {
            s_log.logp(Level.WARNING,LoganLibUiUtil.class.getName(),"getLogRuleRow",
                       "*** Error occurred trying to get  getLogRuleRow: ", e);
        }
        return logRule;
    }
    
    /**
     * @param parserId
     * @return
     */
    public static List<LoganRuleSource> getLogRuleSourceMapRow(Integer ruleId){
        LoganRuleSession facadeRuleEJB = LoganLibUiUtil.getLogRulesSessionFacadeEJB();
        LoganSourceSessionBean facadeSourceEJB = LoganLibUiUtil.getLogSourcesSessionFacadeEJB();
        LoganRuleSourceMapSessionBean facadeRuleSoruceMapEJB = LoganLibUiUtil.getLogRuleSourceMapSessionFacadeEJB();
        List<LoganRuleSource> ruleSources = null;
        if(facadeRuleEJB == null || facadeSourceEJB == null || facadeRuleSoruceMapEJB == null)
            return ruleSources;
        try
        {
            List<EmLoganRuleSourceMap> rsList = facadeRuleSoruceMapEJB.getEmLoganRuleSourceMapFindByRuleId(ruleId);
            if(rsList != null && rsList.size() > 0){
                for(EmLoganRuleSourceMap srMap : rsList){
                    List<EmLoganSource>sources = facadeSourceEJB.getEmLoganSourceFindBySourceId(srMap.getRsSourceId());
                    EmLoganSource source = sources.get(0);
                    if(ruleSources == null){
                        ruleSources = new ArrayList<LoganRuleSource>();
                    }
                    ruleSources.add((new LoganRuleSource( new Number(ruleId), new Number(source.getSourceId()),source.getSourceDname(), source.getSourceDescription(), new Number(0))));
                    //                
                }
//                List<EmLoganSource> srcList = new ArrayList(rList.get(0).getSources());
//                EmLoganRule rule = rList.get(0);
//                Iterator iter = rule.getSources().iterator();
//                while(iter.hasNext()){
//                    EmLoganSource source = (EmLoganSource)iter.next();
//                    ruleSources.add((new LoganRuleSource( new Number(rule.getRuleId()), new Number(source.getSourceId()),source.getSourceDname(), source.getSourceDescription(), new Number(0))));
//                }
            }
        }
        catch(Exception e)
        {
            s_log.logp(Level.WARNING,LoganLibUiUtil.class.getName(),"getLogRuleSourceMapRow",
                       "*** Error occurred trying to get  getLogRuleSourceMapRow: ", e);
        }
        return ruleSources;
        
    }
}
