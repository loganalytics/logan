package oracle.saas.logan.model.session.util;

import java.util.List;

import javax.ejb.Remote;

import oracle.saas.logan.model.persistance.EmTargetTypes;

@Remote
public interface LoganTargetTypes {
    Object queryByRange(String jpqlStmt, int firstResult, int maxResults);


    List<EmTargetTypes> getEmTargetTypesFindAll();
}
