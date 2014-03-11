package oracle.saas.logan.model.session.parser;

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
import oracle.saas.logan.model.persistance.EmLoganParserFieldMap;
import oracle.saas.logan.model.persistance.EmLoganParserFieldMapPK;

@Stateless(name = "LoganParserSessionBean", mappedName = "saas-SaasLoganModel-LoganParserSessionBean")
public class LoganParserSessionBeanBean implements LoganParserSessionBean, LoganParserSessionBeanLocal {
    @Resource
    SessionContext sessionContext;
    @PersistenceContext(unitName = "SaasLoganModel")
    private EntityManager em;

    public LoganParserSessionBeanBean() {
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

    public EmLoganParser persistEmLoganParser(EmLoganParser emLoganParser) {
        em.persist(emLoganParser);
        return emLoganParser;
    }

    public EmLoganParser mergeEmLoganParser(EmLoganParser emLoganParser) {
        return em.merge(emLoganParser);
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

    public EmLoganParserFieldMap persistEmLoganParserFieldMap(EmLoganParserFieldMap emLoganParserFieldMap) {
        em.persist(emLoganParserFieldMap);
        return emLoganParserFieldMap;
    }

    public EmLoganParserFieldMap mergeEmLoganParserFieldMap(EmLoganParserFieldMap emLoganParserFieldMap) {
        return em.merge(emLoganParserFieldMap);
    }

//    public void removeEmLoganParserFieldMap(EmLoganParserFieldMap emLoganParserFieldMap) {
//        emLoganParserFieldMap =
//            em.find(EmLoganParserFieldMap.class,
//                    new EmLoganParserFieldMapPK(emLoganParserFieldMap.getEmLoganCommonFields().getFieldIname(),
//                                                emLoganParserFieldMap.getEmLoganParser().getParserIname()));
//        em.remove(emLoganParserFieldMap);
//    }
    public void removeEmLoganParserFieldMap(EmLoganParserFieldMap emLoganParserFieldMap) {
        emLoganParserFieldMap =
            em.find(EmLoganParserFieldMap.class,
                    new EmLoganParserFieldMapPK(emLoganParserFieldMap.getParfFieldIname().getFieldIname(),
                                                emLoganParserFieldMap.getParfParserIname().getParserIname()));
        em.remove(emLoganParserFieldMap);
    }    
//    public void removeEmLoganParserFieldMap(EmLoganParserFieldMap emLoganParserFieldMap) {
//        emLoganParserFieldMap =
//            em.find(EmLoganParserFieldMap.class,
//                    new EmLoganParserFieldMapPK(emLoganParserFieldMap.getEmLoganCommonFields(),
//                                                emLoganParserFieldMap.getEmLoganParser()));
//        em.remove(emLoganParserFieldMap);
//    }
    /** <code>select o from EmLoganParserFieldMap o</code> */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<EmLoganParserFieldMap> getEmLoganParserFieldMapFindAll() {
        return em.createNamedQuery("EmLoganParserFieldMap.findAll", EmLoganParserFieldMap.class).getResultList();
    }

    public EmLoganCommonFields persistEmLoganCommonFields(EmLoganCommonFields emLoganCommonFields) {
        em.persist(emLoganCommonFields);
        return emLoganCommonFields;
    }

    public EmLoganCommonFields mergeEmLoganCommonFields(EmLoganCommonFields emLoganCommonFields) {
        return em.merge(emLoganCommonFields);
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
}
