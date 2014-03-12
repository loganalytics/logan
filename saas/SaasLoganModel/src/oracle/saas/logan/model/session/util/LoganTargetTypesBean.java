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

import oracle.saas.logan.model.persistance.EmLoganCommonFields;
import oracle.saas.logan.model.persistance.EmLoganParser;

@Stateless(name = "LoganTargetTypes", mappedName = "saas-SaasLoganModel-LoganTargetTypes")
public class LoganTargetTypesBean implements LoganTargetTypes, LoganTargetTypesLocal {
    @Resource
    SessionContext sessionContext;
    @PersistenceContext(unitName = "SaasLoganModel")
    private EntityManager em;

    public LoganTargetTypesBean() {
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

    public void removeEmLoganCommonFields(EmLoganCommonFields emLoganCommonFields) {
        emLoganCommonFields = em.find(EmLoganCommonFields.class, emLoganCommonFields.getFieldIname());
        em.remove(emLoganCommonFields);
    }

    /** <code>select o from EmLoganCommonFields o</code> */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<EmLoganCommonFields> getEmLoganCommonFieldsFindAll() {
        return em.createNamedQuery("EmLoganCommonFields.findAll", EmLoganCommonFields.class).getResultList();
    }

    public void removeEmLoganParser(EmLoganParser emLoganParser) {
        emLoganParser = em.find(EmLoganParser.class, emLoganParser.getParserIname());
        em.remove(emLoganParser);
    }

    /** <code>select o from EmLoganParser o</code> */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<EmLoganParser> getEmLoganParserFindAll() {
        return em.createNamedQuery("EmLoganParser.findAll", EmLoganParser.class).getResultList();
    }
}
