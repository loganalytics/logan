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

import oracle.saas.logan.model.persistance.EmLoganSource;
import oracle.saas.logan.model.persistance.rule.EmLoganRule;

@Stateless(name = "LoganSourceSessionBean", mappedName = "saas-SaasLoganModel-LoganSourceSessionBean")
public class LoganSourceSessionBeanBean implements LoganSourceSessionBean, LoganSourceSessionBeanLocal {
    @Resource
    SessionContext sessionContext;
    @PersistenceContext(unitName = "SaasLoganModel")
    private EntityManager em;

    public LoganSourceSessionBeanBean() {
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

    /** <code>select o from EmLoganSource o</code> */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<EmLoganSource> getEmLoganSourceFindAll() {
        return em.createNamedQuery("EmLoganSource.findAll", EmLoganSource.class).getResultList();
    }

//    /** <code>select o from EmLoganSource o join o.sourceSrctypeIname t join  o.sourceTargetType m where o.sourceSrctypeIname1  =t.srctypeIname and o.sourceTargetType1=m.targetType</code> */
//    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
//    public List<EmLoganSource> getEmLoganSourceFindSources() {
//        return em.createNamedQuery("EmLoganSource.findSources", EmLoganSource.class).getResultList();
//    }
    
    /** <code>select o from EmLoganSource o join o.sourceSrctypeIname t join  o.sourceTargetType m where o.sourceSrctypeIname1  =t.srctypeIname and o.sourceTargetType1=m.targetType</code> */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<Object[]> getEmLoganSourceFindSources() {
        return em.createNamedQuery("EmLoganSource.findSources", Object[].class).getResultList();
    }

    @Override
    public List<EmLoganSource> getEmLoganSourceFindByPK(String srcIname, String targetType, String logType) {
        // TODO Implement this method
        return Collections.emptyList();
    }

    @Override
    public List<EmLoganSource> getEmLoganSourceFindBySourceId(Integer sourceId) {
        // TODO Implement this method
//        return Collections.emptyList();
        return em.createNamedQuery("EmLoganSource.findBySourceId", EmLoganSource.class).setParameter("sourceId",
                                                                                                sourceId).getResultList();
    }
    
//    /** <code>select o from EmLoganRule o where o.ruleId = :rule</code> */
//    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
//    public List<EmLoganRule> getEmLoganRuleFindByRuleId(Integer ruleId){
//        return em.createNamedQuery("EmLoganRule.findByRuleId", EmLoganRule.class).setParameter("ruleId",
//                                                                                                     ruleId).getResultList();
//    }
}
