package oracle.saas.logan.model.session.source;

import java.util.List;

import javax.ejb.Local;

import oracle.saas.logan.model.persistance.EmLoganSourcePattern1;

@Local
public interface LoganSourcePatternSessionLocal {
    EmLoganSourcePattern1 persistEmLoganSourcePattern1(EmLoganSourcePattern1 emLoganSourcePattern1);

    EmLoganSourcePattern1 mergeEmLoganSourcePattern1(EmLoganSourcePattern1 emLoganSourcePattern1);

    void removeEmLoganSourcePattern1(EmLoganSourcePattern1 emLoganSourcePattern1);

    List<EmLoganSourcePattern1> getEmLoganSourcePattern1FindAll();

    List<Object[]> getEmLoganSourcePatternFindAllBySourceId(Integer isIncl, Integer sourceId);
    
    List<EmLoganSourcePattern1> getEmLoganSourcePatternFindAllBySourceId( Integer sourceId);
}
