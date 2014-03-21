package oracle.saas.logan.model.session.util;

import java.util.List;

import javax.ejb.Local;

import oracle.saas.logan.model.persistance.EmLoganMetaAvailParam;

@Local
public interface LoganMetaAvailParamSessionLocal {
    EmLoganMetaAvailParam persistEmLoganMetaAvailParam(EmLoganMetaAvailParam emLoganMetaAvailParam);

    EmLoganMetaAvailParam mergeEmLoganMetaAvailParam(EmLoganMetaAvailParam emLoganMetaAvailParam);

    void removeEmLoganMetaAvailParam(EmLoganMetaAvailParam emLoganMetaAvailParam);

    List<EmLoganMetaAvailParam> getEmLoganMetaAvailParamFindAll();


    List<EmLoganMetaAvailParam> getEmLoganMetaAvailParamFindAllByTargetType(String targetType);
}
