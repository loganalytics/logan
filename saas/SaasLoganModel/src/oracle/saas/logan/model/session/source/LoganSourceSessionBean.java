package oracle.saas.logan.model.session.source;

import java.math.BigDecimal;

import java.util.List;

import javax.ejb.Remote;

import oracle.saas.logan.model.persistance.EmLoganSource;

@Remote
public interface LoganSourceSessionBean {
    Object queryByRange(String jpqlStmt, int firstResult, int maxResults);

    <T> T persistEntity(T entity);

    <T> T mergeEntity(T entity);
    
    void removeEntity(EmLoganSource entity);

    List<EmLoganSource> getEmLoganSourceFindBySourceId(Integer sourceId);
    
    BigDecimal getEmLoganSourceNextSeq(String nextSeqSql) ;
    
    List<EmLoganSource> getEmLoganSourceFindBySourceName(String sourceName);
    
    List<EmLoganSource> getEmLoganSourceFindAll();
    
    List<Object[]> getFilteredSourceList(String targetType, String logType, String dname, String desc,boolean matchAll);

    List<Object[]> getEmLoganSourceFindSources();
}
