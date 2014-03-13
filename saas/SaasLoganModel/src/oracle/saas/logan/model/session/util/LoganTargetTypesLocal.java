package oracle.saas.logan.model.session.util;

import java.util.List;

import javax.ejb.Local;

import oracle.saas.logan.model.persistance.EmTargetTypes;

@Local
public interface LoganTargetTypesLocal {
    Object queryByRange(String jpqlStmt, int firstResult, int maxResults);

    List<EmTargetTypes> getEmTargetTypesFindAll();

}
