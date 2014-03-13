package oracle.saas.logan.model.session.source;

import java.util.List;

import javax.ejb.Remote;

import oracle.saas.logan.model.persistance.EmLoganSource;

@Remote
public interface LoganSourceSessionBean {
    Object queryByRange(String jpqlStmt, int firstResult, int maxResults);

    <T> T persistEntity(T entity);

    <T> T mergeEntity(T entity);

    List<EmLoganSource> getEmLoganSourceFindAll();

    List<Object[]> getEmLoganSourceFindSources();
}
