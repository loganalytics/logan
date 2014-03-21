package oracle.saas.logan.model.session.source;

import java.util.List;

import javax.annotation.Resource;

import javax.ejb.SessionContext;
import javax.ejb.Stateless;

import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import oracle.saas.logan.model.persistance.EmLoganParser;
import oracle.saas.logan.model.persistance.EmLoganSourcePattern;
import oracle.saas.logan.model.persistance.EmLoganSourcePattern1;
import oracle.saas.logan.model.persistance.EmLoganSourcePattern1PK;

@Stateless(name = "LoganSourcePatternSession", mappedName = "saas-SaasLoganModel-LoganSourcePatternSession")
public class LoganSourcePatternSessionBean implements LoganSourcePatternSession, LoganSourcePatternSessionLocal {
    @Resource
    SessionContext sessionContext;
    @PersistenceContext(unitName = "SaasLoganModel")
    private EntityManager em;

    public LoganSourcePatternSessionBean() {
    }

    public EmLoganSourcePattern1 persistEmLoganSourcePattern1(EmLoganSourcePattern1 emLoganSourcePattern1) {
        em.persist(emLoganSourcePattern1);
        return emLoganSourcePattern1;
    }

    public EmLoganSourcePattern1 mergeEmLoganSourcePattern1(EmLoganSourcePattern1 emLoganSourcePattern1) {
        return em.merge(emLoganSourcePattern1);
    }

    public void removeEmLoganSourcePattern1(EmLoganSourcePattern1 emLoganSourcePattern1) {
        emLoganSourcePattern1 =
            em.find(EmLoganSourcePattern1.class,
                    new EmLoganSourcePattern1PK(emLoganSourcePattern1.getPatternIsInclude(),
                                                emLoganSourcePattern1.getPatternSourceId(),
                                                emLoganSourcePattern1.getPatternText()));
        em.remove(emLoganSourcePattern1);
    }

    /** <code>select o from EmLoganSourcePattern1 o</code> */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<EmLoganSourcePattern1> getEmLoganSourcePattern1FindAll() {
        return em.createNamedQuery("EmLoganSourcePattern1.findAll", EmLoganSourcePattern1.class).getResultList();
    }

    /** <code>select p,s,r from EmLoganSourcePattern p join EmLoganSource s on p.patternSourceId = s.sourceId join EmLoganParser r  on p.patternParserIname = r.parserIname where p.patternIsInclude = :isIncl and p.patternSourceId=:sourceId</code> */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<Object[]> getEmLoganSourcePatternFindAllBySourceId(Integer isIncl, Integer sourceId) {
        return em.createNamedQuery("EmLoganSourcePattern.findAllBySourceId",
                                   EmLoganSourcePattern1.class).setParameter("isIncl", isIncl).setParameter("sourceId",
                                                                                                            sourceId).getResultList();
    }
}
