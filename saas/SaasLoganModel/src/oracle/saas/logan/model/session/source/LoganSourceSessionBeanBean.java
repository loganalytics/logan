package oracle.saas.logan.model.session.source;

import java.math.BigDecimal;

import java.util.List;

import javax.annotation.Resource;

import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import oracle.saas.logan.model.persistance.EmLoganSource;

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
    
    public void removeEntity(EmLoganSource entity) {
        em.remove(mergeEntity(entity));
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
    
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public BigDecimal getEmLoganSourceNextSeq(String nextSeqSql) {
        Query query = em.createNativeQuery(nextSeqSql);
        BigDecimal val = (BigDecimal) query.getSingleResult();
        return val;
    }
        
   
        

    @Override
    public List<EmLoganSource> getEmLoganSourceFindBySourceId(Integer sourceId) {
        // TODO Implement this method
        //        return Collections.emptyList();
        return em.createNamedQuery("EmLoganSource.findBySourceId", EmLoganSource.class).setParameter("sourceId",
                                                                                                     sourceId).getResultList();
    }
    
    @Override
    public List<EmLoganSource> getEmLoganSourceFindBySourceName(String sourceName) {
        return em.createNamedQuery("EmLoganSource.findBySourceName", EmLoganSource.class).setParameter("sourceName",
                                                                                                     sourceName).getResultList();
    }
    

    //    /** <code>select o from EmLoganRule o where o.ruleId = :rule</code> */
    //    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    //    public List<EmLoganRule> getEmLoganRuleFindByRuleId(Integer ruleId){
    //        return em.createNamedQuery("EmLoganRule.findByRuleId", EmLoganRule.class).setParameter("ruleId",
    //                                                                                                     ruleId).getResultList();
    //    }
    
    @Override
    public List<Object[]> getFilteredSourceList(String targetType, String logType, String dname, String desc,boolean matchAll) {
        String qlString =
            "select o,t,m  from EmLoganSource o join " +
            "               EmLoganMetaSourceType t on o.sourceSrctypeIname =t.srctypeIname join  " +
            "               EmTargetTypes m  on o.sourceTargetType=m.targetType ";
        
        String match = matchAll?" and ":" or ";
        
        int[] sign = new int[]{   (targetType != null && !targetType.isEmpty())?1:0 , 
                                  (logType != null && !logType.isEmpty())?1:0 ,
                                  (dname != null && !dname.isEmpty())?1:0,
                                  (desc != null && !desc.isEmpty())?1:0 }; 
        
        int sum = 0;
        for(int v : sign)
        {
            sum+=v;
        }
        
        //maunally concact the where clause
        StringBuffer clause = null;
        if (sum > 0) {
            clause = new StringBuffer(" where ");
            int c = 0;
            if (sign[0] > 0) {
                clause.append(" o.sourceTargetType = :targetType ");
                c++;
            }
            
            if (sign[1] > 0) {
                if (c > 0) {
                    clause.append(match);
                }
                clause.append(" o.sourceSrctypeIname = :srcType ");
                c++;
            }
            
            if (sign[2] > 0) {
                if (c > 0) {
                    clause.append(match);
                }
                clause.append(" o.sourceDname like :name ");
                c++;
            }

            if (sign[3] > 0) {
                if (c > 0) {
                    clause.append(match);
                }
                clause.append("  o.sourceDescription like :desc ");
                c++;
            }
        } 
            qlString = clause!=null? qlString+clause:qlString;
            
            TypedQuery<Object[]>  query =  em.createQuery(qlString, Object[].class);

            if (sign[0] > 0) {
                query.setParameter("targetType", targetType);
            }
            
            if (sign[1] > 0) {
                query .setParameter("srcType", logType) ;
            }
            
            if (sign[2] > 0) {
                query.setParameter("name","%"+dname +"%");
            }
            
            if (sign[3] > 0) {
                query.setParameter("desc", "%"+desc+"%");
            }
            return query.getResultList();
    }
}
