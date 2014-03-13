package oracle.saas.logan.view.rule;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import java.util.logging.Logger;

import oracle.saas.logan.model.persistance.EmLoganParser;
import oracle.saas.logan.model.persistance.rule.EmLoganRule;
import oracle.saas.logan.model.session.rule.LoganRuleSession;
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
        LoganRuleBean logParser = null;
        if(facadeEJB == null)
            return logParser;
        try
        {
            List<EmLoganRule> rList = facadeEJB.getEmLoganRuleFindByRuleId(ruleId);
            if(rList != null && rList.size() > 0)
                logParser = new LoganRuleBean(rList.get(0));
        }
        catch(Exception e)
        {
            s_log.logp(Level.WARNING,LoganLibUiUtil.class.getName(),"getLogParserRow",
                       "*** Error occurred trying to get  getLogParserRow: ", e);
        }
        return logParser;
    }
}
