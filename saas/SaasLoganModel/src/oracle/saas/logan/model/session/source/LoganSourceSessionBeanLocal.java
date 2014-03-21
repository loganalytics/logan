package oracle.saas.logan.model.session.source;

import java.math.BigDecimal;

import java.util.List;

import javax.ejb.Local;

import oracle.saas.logan.model.persistance.EmLoganSource;

@Local
public interface LoganSourceSessionBeanLocal {
    Object queryByRange(String jpqlStmt, int firstResult, int maxResults);


    BigDecimal getEmLoganSourceNextSeq(String nextSeqSql) ;
    
    <T> T persistEntity(T entity);

    <T> T mergeEntity(T entity);
    
    void  removeEntity(EmLoganSource entity);

    List<EmLoganSource> getEmLoganSourceFindAll();
    
    List<Object[]> getFilteredSourceList(String targetType, String logType, String dname, String desc, boolean matchAll);

    List<Object[]> getEmLoganSourceFindSources();
    
    List<EmLoganSource> getEmLoganSourceFindBySourceName(String sourceName);
    
    List<EmLoganSource> getEmLoganSourceFindBySourceId(Integer sourceId);
    
}
