package oracle.saas.logan.util;


import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.logging.Logger;

import javax.faces.model.SelectItem;

import oracle.jbo.Row;
import oracle.jbo.RowSetIterator;
import oracle.jbo.server.ViewObjectImpl;

import oracle.saas.logan.model.util.LoganModelUtil;


public class LoganLibUiUtil
{    
    /**
      * Returns the system event severity levels as a list of SelectItems having
      * levels of INFORMATIONAL, WARNING, CRITICAL.
      * 
      * @param loganBundle
      *            the logan bundle
      * @return list of SelectItems of the system event severity levels
      */
    public static List<SelectItem> getRuleSeverityList(ResourceBundle loganBundle)
    {
        List<SelectItem> severityList = new ArrayList<SelectItem>();
        severityList.add(new SelectItem("18",
                                        UiUtil.getUiString("INFORMATIONAL")));
        severityList.add(new SelectItem("20",
                                        UiUtil.getUiString("WARNING")));
        severityList.add(new SelectItem("25",
                                        UiUtil.getUiString("CRITICAL")));
        return severityList;
    }
    
    /**
     * List of Log Source types as SelectItem with value = SrctypeIname and
     * label = SrctypeDname.
     * @return List of Log Source types
     */
    public static List<SelectItem> getSourceTypesList()
    {
//        return getSourceTypesList(false);
        List<SelectItem> l = new ArrayList<SelectItem>();
        return l;
    }
    
    /**
      * List of Log Source types as SelectItem with value = SrctypeIname and
      * label = SrctypeDname. If "includeAll" is true, adds one more option to
      * the select list "ALL", "All".
      * 
      * @param includeAll
      *            the include all
      * @return the source types list
      */
//    public static List<SelectItem> getSourceTypesList(boolean includeAll)
//    {
//        EMViewObjectImpl stvo =
//            getAppModule().getEmLoganMetaSourceTypeVO();
//        return getSelectItemList(stvo, "SrctypeIname", "SrctypeDname",
//                                 includeAll);
//    }
    
    /**
      * Gets the select item list.
      * 
      * @param vo
      *            the vo
      * @param internalAttrName
      *            the internal attr name
      * @param displayAttrName
      *            the display attr name
      * @param ifIncludeALL
      *            the if include all
      * @return the select item list
      */
//    private static List<SelectItem> getSelectItemList(ViewObjectImpl vo,
//                                                      String internalAttrName,
//                                                      String displayAttrName,
//                                                      boolean ifIncludeALL)
//    {
//        return getSelectItemList(vo, internalAttrName, displayAttrName, null, ifIncludeALL);
//    }
    
    /**
      * Given a VO whose rows are expected to have attributes with names
      * "internalAttrName" and "displayAttrName" and an optional attribute called
      * "auxAttrName", iterate over it and create a List of SelectItem objects
      * representing the rows. (The List of SelectItems can then be consumed
      * directly for a UI dropdown list).<br/>
      * If the parameter "includeAll" is set to true, the list will have the
      * first item as the option "All".
      * 
      * @param vo
      *            the vo
      * @param internalAttrName
      *            the internal attr name
      * @param displayAttrName
      *            the display attr name
      * @param auxAttrName
      *            the aux attr name
      * @param includeAll
      *            the include all
      * @return the select item list
      */
//    public static List<SelectItem> getSelectItemList(ViewObjectImpl vo,
//                                                      String internalAttrName,
//                                                      String displayAttrName,
//                                                      String auxAttrName,
//                                                      boolean includeAll)
//    {
//        List<SelectItem> selectList = new ArrayList<SelectItem>();
//        if (includeAll)
//            selectList.add(new SelectItem("ALL",
//                                          LoganModelUtil.getUiString("ALL")));
//        Set<String> uniqueNames = new HashSet<String>();
//        Map<String, String> stDtoI = new HashMap<String, String>();
//        Map<String, String> mDtoAux = null;
//        boolean auxInfoNeeded = false;
//        if(auxAttrName != null)
//        {
//            auxInfoNeeded = true;
//            mDtoAux = new HashMap<String, String>();
//        }
//        if (vo == null)
//            return selectList;
//        for(Row vorow : getRows(vo))
//        {
//            String stIName = (String) vorow.getAttribute(internalAttrName);
//            String stDName = (String) vorow.getAttribute(displayAttrName);
//            uniqueNames.add(stDName);
//            stDtoI.put(stDName, stIName);
//            if(auxInfoNeeded)
//            {
//                String auxI = vorow.getAttribute(auxAttrName).toString();
//                mDtoAux.put(stDName, auxI);
//            }
//        }
//        List<String> stDisplayNames = new ArrayList<String>(uniqueNames);
//        Collections.sort(stDisplayNames);
//        for (String tdname: stDisplayNames)
//        {
//            SelectItem si = null;
//            if(auxInfoNeeded)
//            {
//                si = new SelectItem(stDtoI.get(tdname), 
//                                    tdname, mDtoAux.get(tdname));
//            }
//            else
//            {
//                si = new SelectItem(stDtoI.get(tdname), tdname);
//            }
//            selectList.add(si);
//        }
//        return selectList;
//    }
    
    /**
      * Given a vo, create an iterator over its rows and add those to a List of
      * Row and return.
      * 
      * @param vo
      *            the vo
      * @return Lost of Row from the vo
      */
//    public static List<Row> getRows(ViewObjectImpl vo)
//    {
//        List<Row> rows = new ArrayList<Row>();
//        // do not use the getRowSetIterator() method to get the iterator since
//        // it returns the default iterator and call to next() on it will
//        // change the current row in the default iterator which might be 
//        // getting used on the UI table and cause changes to the UI row currency
//        RowSetIterator vorsetItrMy =
//            vo.createRowSetIterator(null); 
//        try
//        {
//            while (vorsetItrMy.hasNext())
//            {
//                rows.add(vorsetItrMy.next());
//            }
//        }
//        finally
//        {
//            if(vorsetItrMy != null)
//                vorsetItrMy.closeRowSetIterator(); 
//        }        
//        return rows;
//    }    


}