package oracle.saas.logan.model.session.parser;

import java.util.List;

import javax.ejb.Remote;

import oracle.saas.logan.model.persistance.EmLoganCommonFields;
import oracle.saas.logan.model.persistance.EmLoganParser;
import oracle.saas.logan.model.persistance.EmLoganParserFieldMap;

@Remote
public interface LoganParserSessionBean {
    Object queryByRange(String jpqlStmt, int firstResult, int maxResults);

    <T> T persistEntity(T entity);

    <T> T mergeEntity(T entity);

    EmLoganParser persistEmLoganParser(EmLoganParser emLoganParser);

    EmLoganParser mergeEmLoganParser(EmLoganParser emLoganParser);

    void removeEmLoganParser(EmLoganParser emLoganParser);

    List<EmLoganParser> getEmLoganParserFindAll();

    EmLoganParserFieldMap persistEmLoganParserFieldMap(EmLoganParserFieldMap emLoganParserFieldMap);

    EmLoganParserFieldMap mergeEmLoganParserFieldMap(EmLoganParserFieldMap emLoganParserFieldMap);

    void removeEmLoganParserFieldMap(EmLoganParserFieldMap emLoganParserFieldMap);

    List<EmLoganParserFieldMap> getEmLoganParserFieldMapFindAll();

    EmLoganCommonFields persistEmLoganCommonFields(EmLoganCommonFields emLoganCommonFields);

    EmLoganCommonFields mergeEmLoganCommonFields(EmLoganCommonFields emLoganCommonFields);

    void removeEmLoganCommonFields(EmLoganCommonFields emLoganCommonFields);

    List<EmLoganCommonFields> getEmLoganCommonFieldsFindAll();
}
