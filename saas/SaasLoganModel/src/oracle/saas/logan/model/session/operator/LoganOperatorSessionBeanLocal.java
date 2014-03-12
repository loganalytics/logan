package oracle.saas.logan.model.session.operator;

import java.util.List;

import javax.ejb.Local;

import oracle.saas.logan.model.persistance.EmLoganMetaOperator;

@Local
public interface LoganOperatorSessionBeanLocal {
    Object queryByRange(String jpqlStmt, int firstResult, int maxResults);

    <T> T persistEntity(T entity);

    <T> T mergeEntity(T entity);

    EmLoganMetaOperator persistEmLoganMetaOperator(EmLoganMetaOperator emLoganMetaOperator);

    EmLoganMetaOperator mergeEmLoganMetaOperator(EmLoganMetaOperator emLoganMetaOperator);

    void removeEmLoganMetaOperator(EmLoganMetaOperator emLoganMetaOperator);

    List<EmLoganMetaOperator> getEmLoganMetaOperatorFindAll();
}
