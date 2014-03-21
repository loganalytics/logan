package oracle.saas.logan.model.session.source;

import java.util.List;

import javax.ejb.Local;

import oracle.saas.logan.model.persistance.EmLoganSourceParameter;

@Local
public interface EmLoganSourceParameterSessionLocal {
    EmLoganSourceParameter persistEmLoganSourceParameter(EmLoganSourceParameter emLoganSourceParameter);

    EmLoganSourceParameter mergeEmLoganSourceParameter(EmLoganSourceParameter emLoganSourceParameter);

    void removeEmLoganSourceParameter(EmLoganSourceParameter emLoganSourceParameter);

    List<EmLoganSourceParameter> getEmLoganSourceParameterFindAll();

    List<EmLoganSourceParameter> getEmLoganSourceParameterFindAllBySourceId(Integer sourceId);
}
