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

import oracle.saas.logan.model.persistance.EmTargetTypes;

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


    /** <code>select o from EmTargetTypes o</code> */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<EmTargetTypes> getEmTargetTypesFindAll() {
        return em.createNamedQuery("EmTargetTypes.findAll", EmTargetTypes.class).getResultList();
    }


}
