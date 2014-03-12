package oracle.saas.logan.model.session.util;

import java.util.List;

import javax.ejb.Remote;

import oracle.saas.logan.model.persistance.EmLoganCommonFields;
import oracle.saas.logan.model.persistance.EmLoganParser;

@Remote
public interface LoganTargetTypes {
    Object queryByRange(String jpqlStmt, int firstResult, int maxResults);

    <T> T persistEntity(T entity);

    <T> T mergeEntity(T entity);

    void removeEmLoganCommonFields(EmLoganCommonFields emLoganCommonFields);

    List<EmLoganCommonFields> getEmLoganCommonFieldsFindAll();

    void removeEmLoganParser(EmLoganParser emLoganParser);

    List<EmLoganParser> getEmLoganParserFindAll();
}
