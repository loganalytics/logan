package oracle.saas.logan.model.session.source;

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

import oracle.saas.logan.model.persistance.EmLoganRuleSourceMap;
import oracle.saas.logan.model.persistance.EmLoganRuleSourceMapPK;
import oracle.saas.logan.model.persistance.rule.EmLoganRule;

@Stateless(name = "LoganRuleSourceMapSessionBean", mappedName = "saas-SaasLoganModel-LoganRuleSourceMapSessionBean")
public class LoganRuleSourceMapSessionBeanBean implements LoganRuleSourceMapSessionBean,
                                                          LoganRuleSourceMapSessionBeanLocal {
    @Resource
    SessionContext sessionContext;
    @PersistenceContext(unitName = "SaasLoganModel")
    private EntityManager em;

    public LoganRuleSourceMapSessionBeanBean() {
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

    public EmLoganRuleSourceMap persistEmLoganRuleSourceMap(EmLoganRuleSourceMap emLoganRuleSourceMap) {
        em.persist(emLoganRuleSourceMap);
        return emLoganRuleSourceMap;
    }

    public EmLoganRuleSourceMap mergeEmLoganRuleSourceMap(EmLoganRuleSourceMap emLoganRuleSourceMap) {
        return em.merge(emLoganRuleSourceMap);
    }

    public void removeEmLoganRuleSourceMap(EmLoganRuleSourceMap emLoganRuleSourceMap) {
        emLoganRuleSourceMap =
            em.find(EmLoganRuleSourceMap.class,
                    new EmLoganRuleSourceMapPK(emLoganRuleSourceMap.getRsRuleId(),
                                               emLoganRuleSourceMap.getRsSourceId()));
        em.remove(emLoganRuleSourceMap);
    }

    /** <code>select o from EmLoganRuleSourceMap o</code> */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<EmLoganRuleSourceMap> getEmLoganRuleSourceMapFindAll() {
        return em.createNamedQuery("EmLoganRuleSourceMap.findAll", EmLoganRuleSourceMap.class).getResultList();
    }

    @Override
    public List<EmLoganRuleSourceMap> getEmLoganRuleSourceMapFindByRuleId(int ruleId) {
        // TODO Implement this method
        return em.createNamedQuery("EmLoganRuleSourceMap.findByRuleId", EmLoganRuleSourceMap.class).setParameter("ruleId",
                                                                                                     ruleId).getResultList();
    }

}
