package oracle.saas.logan.model.session.util;

import java.util.List;

import javax.ejb.Remote;

import oracle.saas.logan.model.persistance.EmLoganMetaSourceType;

@Remote
public interface LoganMetaSourceTypeSession {
    Object queryByRange(String jpqlStmt, int firstResult, int maxResults);

    <T> T persistEntity(T entity);

    <T> T mergeEntity(T entity);

    EmLoganMetaSourceType persistEmLoganMetaSourceType(EmLoganMetaSourceType emLoganMetaSourceType);

    EmLoganMetaSourceType mergeEmLoganMetaSourceType(EmLoganMetaSourceType emLoganMetaSourceType);

    void removeEmLoganMetaSourceType(EmLoganMetaSourceType emLoganMetaSourceType);

    List<EmLoganMetaSourceType> getEmLoganMetaSourceTypeFindAll();
}
