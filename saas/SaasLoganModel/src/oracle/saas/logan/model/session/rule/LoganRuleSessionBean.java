package oracle.saas.logan.model.session.rule;

import java.util.Collections;
import java.util.List;

import javax.annotation.Resource;

import javax.ejb.SessionContext;
import javax.ejb.Stateless;

import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import oracle.saas.logan.model.persistance.rule.EmLoganRule;
import oracle.saas.logan.model.persistance.rule.EmLoganRulePK;

@Stateless(name = "LoganRuleSession", mappedName = "saas-SaasLoganModel-LoganRuleSession")
public class LoganRuleSessionBean implements LoganRuleSession, LoganRuleSessionLocal {
    @Resource
    SessionContext sessionContext;
    @PersistenceContext(unitName = "SaasLoganModel")
    private EntityManager em;

    public LoganRuleSessionBean() {
    }

    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Object queryByRange(String jpqlStmt, int firstResult, int maxResults) {
        Query query = em.createQuery(jpqlStmt);
        if (firstResult > 0) {
            query = query.setFirstResult(firstResult);
        }
        if (maxResults > 0) {
            query = query.setMaxResults(maxResults);
        }
        return query.getResultList();
    }

    public <T> T persistEntity(T entity) {
        em.persist(entity);
        return entity;
    }

    public <T> T mergeEntity(T entity) {
        return em.merge(entity);
    }

    public EmLoganRule persistEmLoganRule(EmLoganRule emLoganRule) {
        
        emLoganRule.setRuleIname(emLoganRule.getRuleDname());
        emLoganRule.setRuleId(10003);
        emLoganRule.setRuleIsSystem(0);
        emLoganRule.setRuleActionCentralized(0);
        emLoganRule.setRuleActionEvent(1);
        emLoganRule.setRuleActionEventBundle(1);
        emLoganRule.setRuleActionEventBundletime(10);
        emLoganRule.setRuleActionObservation(1);
        emLoganRule.setRuleActionRulemetric(1);
        emLoganRule.setRuleEditVersion(0);
        
        
        em.persist(emLoganRule);
        return emLoganRule; 
    }

    public EmLoganRule mergeEmLoganRule(EmLoganRule emLoganRule) {
        return em.merge(emLoganRule);
    }

    public void removeEmLoganRule(EmLoganRule emLoganRule) {
        emLoganRule =
            em.find(EmLoganRule.class,
                    new EmLoganRulePK(emLoganRule.getRuleIname(), emLoganRule.getRuleSrctypeIname(),
                                      emLoganRule.getRuleTargetType()));
        em.remove(emLoganRule);
    }

    /** <code>select o from EmLoganRule o</code> */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<EmLoganRule> getEmLoganRuleFindAll() {
        return em.createNamedQuery("EmLoganRule.findAll", EmLoganRule.class).getResultList();
    }
    
    public List<EmLoganRule> queryLoganRuleFindAll() {
        return em.createNamedQuery("EmLoganRule.findAll", EmLoganRule.class).getResultList();
    }
}
