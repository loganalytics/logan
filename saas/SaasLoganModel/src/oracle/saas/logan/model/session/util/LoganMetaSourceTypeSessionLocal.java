package oracle.saas.logan.model.session.util;

import java.util.List;

import javax.ejb.Local;

import oracle.saas.logan.model.persistance.EmLoganMetaSourceType;

@Local
public interface LoganMetaSourceTypeSessionLocal {
    Object queryByRange(String jpqlStmt, int firstResult, int maxResults);

    <T> T persistEntity(T entity);

    <T> T mergeEntity(T entity);

    EmLoganMetaSourceType persistEmLoganMetaSourceType(EmLoganMetaSourceType emLoganMetaSourceType);

    EmLoganMetaSourceType mergeEmLoganMetaSourceType(EmLoganMetaSourceType emLoganMetaSourceType);

    void removeEmLoganMetaSourceType(EmLoganMetaSourceType emLoganMetaSourceType);

    List<EmLoganMetaSourceType> getEmLoganMetaSourceTypeFindAll();
}
