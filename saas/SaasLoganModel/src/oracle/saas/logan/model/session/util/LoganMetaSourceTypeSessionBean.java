package oracle.saas.logan.model.session.util;

import java.util.List;

import javax.annotation.Resource;

import javax.ejb.SessionContext;
import javax.ejb.Stateless;

import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import oracle.saas.logan.model.persistance.EmLoganMetaSourceType;

@Stateless(name = "LoganMetaSourceTypeSession", mappedName = "saas-SaasLoganModel-LoganMetaSourceTypeSession")
public class LoganMetaSourceTypeSessionBean implements LoganMetaSourceTypeSession, LoganMetaSourceTypeSessionLocal {
    @Resource
    SessionContext sessionContext;
    @PersistenceContext(unitName = "SaasLoganModel")
    private EntityManager em;

    public LoganMetaSourceTypeSessionBean() {
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

    public EmLoganMetaSourceType persistEmLoganMetaSourceType(EmLoganMetaSourceType emLoganMetaSourceType) {
        em.persist(emLoganMetaSourceType);
        return emLoganMetaSourceType;
    }

    public EmLoganMetaSourceType mergeEmLoganMetaSourceType(EmLoganMetaSourceType emLoganMetaSourceType) {
        return em.merge(emLoganMetaSourceType);
    }

    public void removeEmLoganMetaSourceType(EmLoganMetaSourceType emLoganMetaSourceType) {
        emLoganMetaSourceType = em.find(EmLoganMetaSourceType.class, emLoganMetaSourceType.getSrctypeIname());
        em.remove(emLoganMetaSourceType);
    }

    /** <code>select o from EmLoganMetaSourceType o</code> */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<EmLoganMetaSourceType> getEmLoganMetaSourceTypeFindAll() {
        return em.createNamedQuery("EmLoganMetaSourceType.findAll", EmLoganMetaSourceType.class).getResultList();
    }
}
