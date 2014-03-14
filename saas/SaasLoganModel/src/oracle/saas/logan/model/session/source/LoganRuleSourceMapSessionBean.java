package oracle.saas.logan.model.session.source;

import java.util.List;

import javax.ejb.Remote;

import oracle.saas.logan.model.persistance.EmLoganRuleSourceMap;

@Remote
public interface LoganRuleSourceMapSessionBean {
    Object queryByRange(String jpqlStmt, int firstResult, int maxResults);

    <T> T persistEntity(T entity);

    <T> T mergeEntity(T entity);

    EmLoganRuleSourceMap persistEmLoganRuleSourceMap(EmLoganRuleSourceMap emLoganRuleSourceMap);

    EmLoganRuleSourceMap mergeEmLoganRuleSourceMap(EmLoganRuleSourceMap emLoganRuleSourceMap);

    void removeEmLoganRuleSourceMap(EmLoganRuleSourceMap emLoganRuleSourceMap);

    List<EmLoganRuleSourceMap> getEmLoganRuleSourceMapFindAll();
    
    List<EmLoganRuleSourceMap> getEmLoganRuleSourceMapFindByRuleId(int ruleId);
}
