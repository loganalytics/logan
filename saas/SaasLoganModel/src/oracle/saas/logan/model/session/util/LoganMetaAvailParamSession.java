package oracle.saas.logan.model.session.util;

import java.util.List;

import javax.ejb.Remote;

import oracle.saas.logan.model.persistance.EmLoganMetaAvailParam;

@Remote
public interface LoganMetaAvailParamSession {
    EmLoganMetaAvailParam persistEmLoganMetaAvailParam(EmLoganMetaAvailParam emLoganMetaAvailParam);

    EmLoganMetaAvailParam mergeEmLoganMetaAvailParam(EmLoganMetaAvailParam emLoganMetaAvailParam);

    void removeEmLoganMetaAvailParam(EmLoganMetaAvailParam emLoganMetaAvailParam);

    List<EmLoganMetaAvailParam> getEmLoganMetaAvailParamFindAll();

    List<EmLoganMetaAvailParam> getEmLoganMetaAvailParamFindAllByTargetType(String targetType);
}
