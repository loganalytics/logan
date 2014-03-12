package oracle.saas.logan.model.session.util;

import java.util.List;

import javax.ejb.Local;

import oracle.saas.logan.model.persistance.EmLoganCommonFields;
import oracle.saas.logan.model.persistance.EmLoganParser;

@Local
public interface LoganTargetTypesLocal {
    Object queryByRange(String jpqlStmt, int firstResult, int maxResults);

    <T> T persistEntity(T entity);

    <T> T mergeEntity(T entity);

    void removeEmLoganCommonFields(EmLoganCommonFields emLoganCommonFields);

    List<EmLoganCommonFields> getEmLoganCommonFieldsFindAll();

    void removeEmLoganParser(EmLoganParser emLoganParser);

    List<EmLoganParser> getEmLoganParserFindAll();
}
