package oracle.saas.logan.model.session.source;

import java.util.List;

import javax.ejb.Remote;

import oracle.saas.logan.model.persistance.EmLoganSource;
import oracle.saas.logan.model.persistance.rule.EmLoganRule;

@Remote
public interface LoganSourceSessionBean {
    Object queryByRange(String jpqlStmt, int firstResult, int maxResults);

    <T> T persistEntity(T entity);

    <T> T mergeEntity(T entity);

    List<EmLoganSource> getEmLoganSourceFindBySourceId(Integer sourceId);
    
    List<EmLoganSource> getEmLoganSourceFindAll();
    
    List<EmLoganSource> getFilteredSourceList(String targetType, String logType, String dname, String desc);

    List<Object[]> getEmLoganSourceFindSources();
}
