package oracle.saas.logan.model.session.source;

import java.util.List;

import javax.ejb.Local;

import oracle.saas.logan.model.persistance.EmLoganSource;

@Local
public interface LoganSourceSessionBeanLocal {
    Object queryByRange(String jpqlStmt, int firstResult, int maxResults);

    <T> T persistEntity(T entity);

    <T> T mergeEntity(T entity);

    List<EmLoganSource> getEmLoganSourceFindAll();

    List<Object[]> getEmLoganSourceFindSources();
}
