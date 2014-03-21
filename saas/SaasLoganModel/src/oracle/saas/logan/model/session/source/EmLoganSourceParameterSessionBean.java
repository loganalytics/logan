package oracle.saas.logan.model.session.source;

import java.util.List;

import javax.annotation.Resource;

import javax.ejb.SessionContext;
import javax.ejb.Stateless;

import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import oracle.saas.logan.model.persistance.EmLoganSourceParameter;
import oracle.saas.logan.model.persistance.EmLoganSourceParameterPK;

@Stateless(name = "EmLoganSourceParameterSession", mappedName = "saas-SaasLoganModel-EmLoganSourceParameterSession")
public class EmLoganSourceParameterSessionBean implements EmLoganSourceParameterSession,
                                                          EmLoganSourceParameterSessionLocal {
    @Resource
    SessionContext sessionContext;
    @PersistenceContext(unitName = "SaasLoganModel")
    private EntityManager em;

    public EmLoganSourceParameterSessionBean() {
    }

    public EmLoganSourceParameter persistEmLoganSourceParameter(EmLoganSourceParameter emLoganSourceParameter) {
        em.persist(emLoganSourceParameter);
        return emLoganSourceParameter;
    }

    public EmLoganSourceParameter mergeEmLoganSourceParameter(EmLoganSourceParameter emLoganSourceParameter) {
        return em.merge(emLoganSourceParameter);
    }

    public void removeEmLoganSourceParameter(EmLoganSourceParameter emLoganSourceParameter) {
        emLoganSourceParameter =
            em.find(EmLoganSourceParameter.class,
                    new EmLoganSourceParameterPK(emLoganSourceParameter.getParamName(),
                                                 emLoganSourceParameter.getParamSourceId()));
        em.remove(emLoganSourceParameter);
    }

    /** <code>select o from EmLoganSourceParameter o</code> */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<EmLoganSourceParameter> getEmLoganSourceParameterFindAll() {
        return em.createNamedQuery("EmLoganSourceParameter.findAll", EmLoganSourceParameter.class).getResultList();
    }

    /** <code> select o from EmLoganSourceParameter o  join EmLoganSource s on o.paramSourceId=o.sourceId where o.paramSourceId=:sourceId</code> */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<EmLoganSourceParameter> getEmLoganSourceParameterFindAllBySourceId(Integer sourceId) {
        return em.createNamedQuery("EmLoganSourceParameter.findAllBySourceId",
                                   EmLoganSourceParameter.class).setParameter("sourceId", sourceId).getResultList();
    }
}
