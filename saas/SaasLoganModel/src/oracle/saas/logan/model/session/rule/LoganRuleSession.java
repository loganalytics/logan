package oracle.saas.logan.model.session.rule;

import java.util.List;

import javax.ejb.Remote;

import oracle.saas.logan.model.persistance.EmLoganParser;
import oracle.saas.logan.model.persistance.rule.EmLoganRule;

@Remote
public interface LoganRuleSession {
    Object queryByRange(String jpqlStmt, int firstResult, int maxResults);

    <T> T persistEntity(T entity);

    <T> T mergeEntity(T entity);

    EmLoganRule persistEmLoganRule(EmLoganRule emLoganRule);

    EmLoganRule mergeEmLoganRule(EmLoganRule emLoganRule);

    void removeEmLoganRule(EmLoganRule emLoganRule);
    
    List<EmLoganRule> getEmLoganRuleFindByRuleId(Integer ruleId);

    List<EmLoganRule> getEmLoganRuleFindAll();
    
    Integer getNextRuleId();
}
