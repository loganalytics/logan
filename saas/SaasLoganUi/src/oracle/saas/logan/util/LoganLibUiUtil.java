package oracle.saas.logan.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import java.util.ResourceBundle;
import java.util.logging.Level;

import java.util.logging.Logger;

import javax.faces.component.UIComponent;
import javax.faces.model.SelectItem;

import javax.naming.CommunicationException;
import javax.naming.Context;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import oracle.adf.view.rich.component.rich.data.RichTable;

import oracle.jbo.Row;
import oracle.jbo.domain.Number;

import oracle.saas.logan.model.client.LoganSessionBeanProxy;
import oracle.saas.logan.model.persistance.EmLoganMetaSourceType;
import oracle.saas.logan.model.persistance.EmLoganSource;
import oracle.saas.logan.model.persistance.EmTargetTypes;

import oracle.saas.logan.model.session.rule.LoganRuleSession;

import oracle.saas.logan.model.session.source.LoganRuleSourceMapSessionBean;
import oracle.saas.logan.model.session.source.LoganRuleSourceMapSessionBeanBean;
import oracle.saas.logan.model.session.source.LoganSourceSessionBean;
import oracle.saas.logan.model.session.util.LoganMetaSourceTypeSession;

import oracle.saas.logan.view.BaseDataPersistenceHandler;

import oracle.saas.logan.view.rule.LoganRuleTargetAssoc;
import oracle.saas.logan.view.source.LoganLibSourcePojo;

import org.apache.myfaces.trinidad.context.RequestContext;
import org.apache.myfaces.trinidad.model.RowKeySet;


public class LoganLibUiUtil {
    
    private static final Logger s_log =
        Logger.getLogger(LoganLibUiUtil.class.getName());
    
    private static final String MODE_CONST = "mode";
    
    private static final String LOG_RULES_SESS_FACADE_EJB_JNDI_NAME = 
        "saas-SaasLoganModel-LoganRuleSession#oracle.saas.logan.model.session.rule.LoganRuleSession";
    
    private static final String LOG_META_SOURCE_TYPE_SESS_FACADE_EJB_JNDI_NAME = 
        "saas-SaasLoganModel-LoganMetaSourceTypeSession#oracle.saas.logan.model.session.util.LoganMetaSourceTypeSession";

    private static final String LOG_SOURCE_SESS_FACADE_EJB_JNDI_NAME = 
        "saas-SaasLoganModel-LoganSourceSessionBean#oracle.saas.logan.model.session.source.LoganSourceSessionBean";

    private static final String LOG_RULE_SOURCE_MAP_SESS_FACADE_EJB_JNDI_NAME = 
        "saas-SaasLoganModel-LoganRuleSourceMapSessionBean#oracle.saas.logan.model.session.source.LoganRuleSourceMapSessionBean";

    
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

    /**
     * Checks for user selected zero rows.
     * We need to disable some buttons in this case and
     * we cannot make the table single-select as well (for certain cases
     * we need multiple selection).
     *
     * @param table
     *            the ADF RichTable reference
     * @return true, if user selected zero rows.
     */
    public static boolean hasUserSelectedZeroRows(RichTable table) {
        // check if table has multiple rows selected then true else false
        boolean zeroRows = false;

        int rowsSelected = 1;
        if (table != null) {
            rowsSelected = getSelectedRowsCount(table);
        }
        if (rowsSelected == 0)
            zeroRows = true;
        return zeroRows;
    }

    /**
     * Gets the selected rows count of the given ADF RichTable.
     * TODO: Move this method to UiUtil.java
     * @param table
     *            the table
     * @return the selected rows count
     */
    public static int getSelectedRowsCount(RichTable table) {
        int rowsSelected = 1;
        if (table != null) {
            RowKeySet rk = table.getSelectedRowKeys();
            if (rk != null) {
                rowsSelected = rk.size();
            }
        }
        return rowsSelected;
    }
    
    /**
     * Viewbeans which show tables using Lists of BaseDataPersistenceHandler
     * objects can call this to get the selected row from their
     * selectionListener methods.
     *
     * @param iT adf Rich Table instance
     * @return BaseDataPersistenceHandler
     */
    public static BaseDataPersistenceHandler getSelectedIPRowForTable(RichTable iT){
        BaseDataPersistenceHandler selR = null;
        if (iT != null){
            RowKeySet rowKeySet = iT.getSelectedRowKeys();
            if (rowKeySet != null)
            {
                Iterator iter = rowKeySet.iterator();
                if (iter != null && iter.hasNext())
                {
                    iT.setRowKey(iter.next());
                    selR = (BaseDataPersistenceHandler) iT.getRowData();
                }
            }
        }
        return selR;
    }
    
    /**
          * Checks if user selected multiple rows or zero rows.
          * We need to disable some buttons in both these cases and
          * we cannot make the table single-select as well (for certain cases
          * we need multiple selection).
          * 
          * @param table
          *            the ADF RichTable reference
          * @return true, if user selected multiple rows or zero rows.
          */
        public static boolean hasUserSelectedMultipleOrZeroRows(RichTable table)
        {
            // check if table has multiple rows selected then true else false
            boolean multiOrZeroRows = false;

            int rowsSelected = 1;
            if (table != null)
            {
                rowsSelected = getSelectedRowsCount(table);
            }
            if (rowsSelected != 1)
                multiOrZeroRows = true;
            return multiOrZeroRows;
        }
    
    /**
     * The EJB interface for Log Types
     * @return
     */
    public static LoganRuleSession getLogRulesSessionFacadeEJB()
    {
        LoganRuleSession logRulesSessionFacadeEJB = null;
        try
        {
            final Context context = getInitialContext();
            logRulesSessionFacadeEJB =
                (LoganRuleSession) context.lookup(LOG_RULES_SESS_FACADE_EJB_JNDI_NAME);
        }
        catch (CommunicationException ex)
        {
            if(s_log.isLoggable(Level.FINE))
                s_log.logp(Level.FINE,LoganLibUiUtil.class.getName(),"getLogRulesSessionFacadeEJB",
                           "*** A CommunicationException was raised. " +
                            "This typically occurs when the target WebLogic server is not running", ex);
        }
        catch (Exception ex)
        {
            if(s_log.isLoggable(Level.FINE))
                s_log.logp(Level.FINE,LoganLibUiUtil.class.getName(),"getLogRulesSessionFacadeEJB",
                           "*** Error occurred trying to get  LogRulesSessionFacadeEJB: ", ex);
        }
        return logRulesSessionFacadeEJB;
    }
    
    /**
     * The EJB interface for Log Meta Source Type
     * @return
     */
    public static LoganMetaSourceTypeSession getLoganMetaSourceTypeSessionFacadeEJB(){
        LoganMetaSourceTypeSession loganMetaSourceTypeSession = null;
        try{
            final Context context = getInitialContext();
            loganMetaSourceTypeSession =
                (LoganMetaSourceTypeSession) context.lookup(LOG_META_SOURCE_TYPE_SESS_FACADE_EJB_JNDI_NAME);
        }
        catch (CommunicationException ex){
            if(s_log.isLoggable(Level.FINE))
                s_log.logp(Level.FINE,LoganLibUiUtil.class.getName(),"getLoganMetaSourceTypeSessionFacadeEJB",
                           "*** A CommunicationException was raised. " +
                            "This typically occurs when the target WebLogic server is not running", ex);
        }
        catch (Exception ex){
            if(s_log.isLoggable(Level.FINE))
                s_log.logp(Level.FINE,LoganLibUiUtil.class.getName(),"getLoganMetaSourceTypeSessionFacadeEJB",
                           "*** Error occurred trying to get  getLoganMetaSourceTypeSessionFacadeEJB: ", ex);
        }
        return loganMetaSourceTypeSession;
    }
    
    /**
     * The EJB interface for Log Source
     * @return
     */
    public static LoganSourceSessionBean getLogSourcesSessionFacadeEJB()
    {
        LoganSourceSessionBean logSourceSessionFacadeEJB = null;
        try
        {
            final Context context = getInitialContext();
            logSourceSessionFacadeEJB =
                (LoganSourceSessionBean) context.lookup(LOG_SOURCE_SESS_FACADE_EJB_JNDI_NAME);
        }
        catch (CommunicationException ex)
        {
            if(s_log.isLoggable(Level.FINE))
                s_log.logp(Level.FINE,LoganLibUiUtil.class.getName(),"getLogSourcesSessionFacadeEJB",
                           "*** A CommunicationException was raised. " +
                            "This typically occurs when the target WebLogic server is not running", ex);
        }
        catch (Exception ex)
        {
            if(s_log.isLoggable(Level.FINE))
                s_log.logp(Level.FINE,LoganLibUiUtil.class.getName(),"getLogSourcesSessionFacadeEJB",
                           "*** Error occurred trying to get  LogSourcesSessionFacadeEJB: ", ex);
        }
        return logSourceSessionFacadeEJB;
    }
    
    /**
     * The EJB interface for Log Types
     * @return
     */
    public static LoganRuleSourceMapSessionBean getLogRuleSourceMapSessionFacadeEJB()
    {
        LoganRuleSourceMapSessionBean logRuleSourceMapSessionFacadeEJB = null;
        try
        {
            final Context context = getInitialContext();
            logRuleSourceMapSessionFacadeEJB =
                (LoganRuleSourceMapSessionBean) context.lookup(LOG_RULE_SOURCE_MAP_SESS_FACADE_EJB_JNDI_NAME);
        }
        catch (CommunicationException ex)
        {
            if(s_log.isLoggable(Level.FINE))
                s_log.logp(Level.FINE,LoganLibUiUtil.class.getName(),"getLogRuleSourceMapSessionFacadeEJB",
                           "*** A CommunicationException was raised. " +
                            "This typically occurs when the target WebLogic server is not running", ex);
        }
        catch (Exception ex)
        {
            if(s_log.isLoggable(Level.FINE))
                s_log.logp(Level.FINE,LoganLibUiUtil.class.getName(),"getLogRuleSourceMapSessionFacadeEJB",
                           "*** Error occurred trying to get  LoganRuleSourceMapSessionBean: ", ex);
        }
        return logRuleSourceMapSessionFacadeEJB;
    }
    
    private static Context getInitialContext() throws NamingException {
        Hashtable env = new Hashtable();
        // WebLogic Server 10.x/12.x connection details
        env.put(Context.INITIAL_CONTEXT_FACTORY, "weblogic.jndi.WLInitialContextFactory");
        env.put(Context.PROVIDER_URL, "t3://127.0.0.1:7101");
        return new InitialContext(env);
    }
    
    /**
     * Gets the value of an input parameter set in the ipm bean.
     * @param param name String
     * @return param value String
     */
    public static String getParamFromReqParams(String param)
    {
        Map ipmParams =
            InterPageMessageBean.getCurrentInstance().getParams();
        return (String) ipmParams.get(param);
    }
    
    /**
      * Get the mode for Log Rule / Log Source train (bounded task flow). Mode
      * can be create / create_like / edit
      * 
      * @return the mode for train
      */
    public static String getModeForTrain()
    {
        return getParamFromReqParams(MODE_CONST);
    }
    
    /**
      * Gets the row selected from ui table.
      * 
      * @param iT
      *            reference to a RichTable
      * @return the row selected from ui table
      */
    public static List getRowSelectedFromUiTable(RichTable iT)
    {
        List retval = new ArrayList();
        boolean added = false;
        Object row = null;
        if (iT != null)
        {
            RowKeySet rowKeySet = iT.getSelectedRowKeys();
            if (rowKeySet != null)
            {
                Iterator iter = rowKeySet.iterator();
                while (iter != null && iter.hasNext())
                {
                    iT.setRowKey(iter.next());
                    row = iT.getRowData();
                    if (row != null) // not removed
                    {
                        retval.add(row);
                        added = true;
                    }
                }
            }
        }
        if (!added)
            retval =  null;

        return retval;
    }
    
    /**
      * Returns the system event severity levels as a list of SelectItems having
      * levels of INFORMATIONAL, WARNING, CRITICAL.
      * 
      * @param loganBundle
      *            the logan bundle
      * @return list of SelectItems of the system event severity levels
      */
    public static List<SelectItem> getRuleSeverityList(ResourceBundle loganBundle){
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
        return getSourceTypesList(false);
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
    public static List<SelectItem> getSourceTypesList(boolean includeAll){
        List<SelectItem> sourceTypeList = new ArrayList<SelectItem>();
        List<String> typeDisplayNames = new ArrayList<String>();
        List<EmLoganMetaSourceType> pojos = getLoganMetaSourceTypeSessionFacadeEJB().getEmLoganMetaSourceTypeFindAll();

        Map<String, String> typDispToType = new HashMap<String, String>();

        if (includeAll) {
            sourceTypeList.add(new SelectItem("ALL", LoganUiModelUtil.getUiString("ALL")));
        }

        for (EmLoganMetaSourceType pojo : pojos) {

            String stype = pojo.getSrctypeIname();
            //TODO use sdk provided nls handling
            //String tdisplaytype = TargetMetricUtil.getLocalizedTargetTypeLabel(ttype);
            String sdisplaytype = pojo.getSrctypeDname();
            typeDisplayNames.add(sdisplaytype);
            typDispToType.put(sdisplaytype, stype);
        }

        Collections.sort(typeDisplayNames);
        for (String sdname : typeDisplayNames) {
            sourceTypeList.add(new SelectItem(typDispToType.get(sdname), sdname));
        }
        return sourceTypeList;
    }
    
    /**
      * Get the List of LoganSourceDetailsBean for log rules UI which are
      * associated with the given targetType, logType and have the 
      * given name and description
      * from the EmLoganSourceVO using EmLoganSourceALLVOCriteria.
      * 
      * @param targetType
      *            the target type
      * @param logType
      *            the log type
      * @param name
      *            the name
      * @param desc
      *            the desc
      * @return List of LoganSourceDetailsBean
      */
    public static List<LoganLibSourcePojo> getFilteredSourceListForRules(String targetType,
                                                                             String logType,
                                                                             String name,
                                                                             String desc){
        List<LoganLibSourcePojo> sourceList = new ArrayList<LoganLibSourcePojo>();
        
        List<EmLoganSource> pojos = getLogSourcesSessionFacadeEJB().getFilteredSourceList(targetType, logType, (name==null?"" : name), (desc==null?"":desc));

        for(EmLoganSource pojo : pojos){
            LoganLibSourcePojo source = new LoganLibSourcePojo();
            source.setSourceId(pojo.getSourceId());
            source.setSourceDname(pojo.getSourceDname());
            source.setSourceIsSystem(pojo.getSourceIsSystem());
            source.setSourceDescription(pojo.getSourceDescription());
            source.setSourceAuthor(pojo.getSourceAuthor());
            source.setSourceLastUpdatedDate(pojo.getSourceLastUpdatedDate());
            
            sourceList.add(source);
        }
        return sourceList;
    }
    
    /**
      * Adds the partial target for a UI Element.
      * TODO: consider moving this to UiUtil.java
      * @param id
      *            the id of the UI element
      */
    public static void addPPR(String id){
        UIComponent component = AdfUtil.findComponent(id);;
        if (component != null){
            RequestContext.getCurrentInstance().addPartialTarget(component);
        }
    }
    
    /**
      * Get the List of Logan Rule Target Assocs (LoganRuleTargetAssoc)
      * associated with the given ruleId from the EmLoganRuleTargetAssocVO.
      * 
      * @param ruleId
      *            the rule id
      * @return List of Logan Rule Target Assocs (LoganRuleTargetAssoc)
      */
    public static List<LoganRuleTargetAssoc> loadLogRuleTargets(Number ruleId){
        if (ruleId == null)
            return null;
        List<LoganRuleTargetAssoc> targets =
            new ArrayList<LoganRuleTargetAssoc>();
        
        return targets;
    }
    
    /**
      * Gets the row selected from ui table.
      * 
      * @param tableId
      *            the table id like "emT:pgl1:t1"
      * @return the row selected from ui table
      */
    public static List getRowSelectedFromUiTable(String tableId){
        RichTable iT = (RichTable) AdfUtil.findComponent(tableId);
        return getRowSelectedFromUiTable(iT);
    }
}
