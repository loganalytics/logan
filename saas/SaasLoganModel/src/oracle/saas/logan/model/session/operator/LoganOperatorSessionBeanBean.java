package oracle.saas.logan.model.session.operator;

import java.util.List;

import javax.annotation.Resource;

import javax.ejb.SessionContext;
import javax.ejb.Stateless;

import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import oracle.saas.logan.model.persistance.EmLoganMetaOperator;

@Stateless(name = "LoganOperatorSessionBean", mappedName = "saas-SaasLoganModel-LoganOperatorSessionBean")
public class LoganOperatorSessionBeanBean implements LoganOperatorSessionBean, LoganOperatorSessionBeanLocal {
    @Resource
    SessionContext sessionContext;
    @PersistenceContext(unitName = "SaasLoganModel")
    private EntityManager em;

    public LoganOperatorSessionBeanBean() {
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

    public EmLoganMetaOperator persistEmLoganMetaOperator(EmLoganMetaOperator emLoganMetaOperator) {
        em.persist(emLoganMetaOperator);
        return emLoganMetaOperator;
    }

    public EmLoganMetaOperator mergeEmLoganMetaOperator(EmLoganMetaOperator emLoganMetaOperator) {
        return em.merge(emLoganMetaOperator);
    }

    public void removeEmLoganMetaOperator(EmLoganMetaOperator emLoganMetaOperator) {
        emLoganMetaOperator = em.find(EmLoganMetaOperator.class, emLoganMetaOperator.getOperName());
        em.remove(emLoganMetaOperator);
    }

    /** <code>select o from EmLoganMetaOperator o</code> */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<EmLoganMetaOperator> getEmLoganMetaOperatorFindAll() {
        return em.createNamedQuery("EmLoganMetaOperator.findAll", EmLoganMetaOperator.class).getResultList();
    }
}
