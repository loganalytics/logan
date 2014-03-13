package oracle.saas.logan.model.session.source;

import java.util.List;

import javax.ejb.Local;

import oracle.saas.logan.model.persistance.EmLoganRuleSourceMap;

@Local
public interface LoganRuleSourceMapSessionBeanLocal {
    Object queryByRange(String jpqlStmt, int firstResult, int maxResults);

    <T> T persistEntity(T entity);

    <T> T mergeEntity(T entity);

    EmLoganRuleSourceMap persistEmLoganRuleSourceMap(EmLoganRuleSourceMap emLoganRuleSourceMap);

    EmLoganRuleSourceMap mergeEmLoganRuleSourceMap(EmLoganRuleSourceMap emLoganRuleSourceMap);

    void removeEmLoganRuleSourceMap(EmLoganRuleSourceMap emLoganRuleSourceMap);

    List<EmLoganRuleSourceMap> getEmLoganRuleSourceMapFindAll();
}
