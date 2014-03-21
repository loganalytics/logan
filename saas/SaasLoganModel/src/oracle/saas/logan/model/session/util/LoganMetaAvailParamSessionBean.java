package oracle.saas.logan.model.session.util;

import java.util.List;

import javax.annotation.Resource;

import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import oracle.saas.logan.model.persistance.EmLoganMetaAvailParam;
import oracle.saas.logan.model.persistance.EmLoganMetaAvailParamPK;

@Stateless(name = "LoganMetaAvailParamSession", mappedName = "saas-SaasLoganModel-LoganMetaAvailParamSession")
public class LoganMetaAvailParamSessionBean implements LoganMetaAvailParamSession, LoganMetaAvailParamSessionLocal {
    @Resource
    SessionContext sessionContext;
    @PersistenceContext(unitName = "SaasLoganModel")
    private EntityManager em;

    public LoganMetaAvailParamSessionBean() {
    }

    public EmLoganMetaAvailParam persistEmLoganMetaAvailParam(EmLoganMetaAvailParam emLoganMetaAvailParam) {
        em.persist(emLoganMetaAvailParam);
        return emLoganMetaAvailParam;
    }

    public EmLoganMetaAvailParam mergeEmLoganMetaAvailParam(EmLoganMetaAvailParam emLoganMetaAvailParam) {
        return em.merge(emLoganMetaAvailParam);
    }

    public void removeEmLoganMetaAvailParam(EmLoganMetaAvailParam emLoganMetaAvailParam) {
        emLoganMetaAvailParam =
            em.find(EmLoganMetaAvailParam.class,
                    new EmLoganMetaAvailParamPK(emLoganMetaAvailParam.getApName(),
                                                emLoganMetaAvailParam.getApPluginId(),
                                                emLoganMetaAvailParam.getApPluginVersion(),
                                                emLoganMetaAvailParam.getApTargetType()));
        em.remove(emLoganMetaAvailParam);
    }

    /** <code>select o from EmLoganMetaAvailParam o</code> */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<EmLoganMetaAvailParam> getEmLoganMetaAvailParamFindAll() {
        return em.createNamedQuery("EmLoganMetaAvailParam.findAll", EmLoganMetaAvailParam.class).getResultList();
    }


    /** <code>select o from EmLoganMetaAvailParam o where o.apTargetType=:targetType order by o.apName </code> */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<EmLoganMetaAvailParam> getEmLoganMetaAvailParamFindAllByTargetType(String targetType) {
        return em.createNamedQuery("EmLoganMetaAvailParam.findAllByTargetType",
                                   EmLoganMetaAvailParam.class).setParameter("targetType", targetType).getResultList();
    }
}
