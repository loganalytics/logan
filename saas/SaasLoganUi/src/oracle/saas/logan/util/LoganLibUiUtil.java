package oracle.saas.logan.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.model.SelectItem;

import oracle.saas.logan.model.client.LoganSessionBeanProxy;
import oracle.saas.logan.model.persistance.EmTargetTypes;


public class LoganLibUiUtil {
    public LoganLibUiUtil() {
        super();
    }


    /**
     * List of Target Types as SelectItems with value = target type and
     * label = type Display Name NLSed.
     * @return List of Target Types as SelectItems
     */
    public static List<SelectItem> getTargetTypesList() {
        return getTargetTypesList(true);
    }

    /**
     * List of Target Types as SelectItems with value = target type and label =
     * type Display Name NLSed. If includeAll then add one more SelectItem
     * "ALL", "All".
     *
     *  JPA prototype version. -- ming
     *
     * @param includeAll the include all
     * @return List of Target Types as SelectItems
     */
    public static List<SelectItem> getTargetTypesList(boolean includeAll) {
        List<SelectItem> targetTypeList = new ArrayList<SelectItem>();
        List<String> typeDisplayNames = new ArrayList<String>();
        List<EmTargetTypes> pojos = LoganSessionBeanProxy.getEmTargetTypesFindAll();

        Map<String, String> typDispToType = new HashMap<String, String>();

        if (includeAll) {
            targetTypeList.add(new SelectItem("ALL", LoganUiModelUtil.getUiString("ALL")));
        }

        for (EmTargetTypes pojo : pojos) {

            String ttype = pojo.getTargetType();
            //TODO use sdk provided nls handling
            //String tdisplaytype = TargetMetricUtil.getLocalizedTargetTypeLabel(ttype);
            String tdisplaytype = pojo.getTypeDisplayName();
            typeDisplayNames.add(tdisplaytype);
            typDispToType.put(tdisplaytype, ttype);
        }

        Collections.sort(typeDisplayNames);
        for (String tdname : typeDisplayNames) {
            targetTypeList.add(new SelectItem(typDispToType.get(tdname), tdname));
        }
        return targetTypeList;
    }

}
