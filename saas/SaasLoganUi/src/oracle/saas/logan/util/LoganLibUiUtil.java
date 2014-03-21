package oracle.saas.logan.util;

import java.math.BigDecimal;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import javax.faces.component.UIComponent;
import javax.faces.model.SelectItem;

import javax.naming.CommunicationException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import oracle.adf.view.rich.component.rich.data.RichTable;
import oracle.adf.view.rich.component.rich.layout.RichShowDetailItem;

import oracle.jbo.Row;
import oracle.jbo.domain.Number;

import oracle.saas.logan.model.client.LoganSessionBeanProxy;
import oracle.saas.logan.model.persistance.EmLoganMetaAvailParam;
import oracle.saas.logan.model.persistance.EmLoganMetaSourceType;
import oracle.saas.logan.model.persistance.EmLoganParser;
import oracle.saas.logan.model.persistance.EmLoganSource;
import oracle.saas.logan.model.persistance.EmLoganSourceParameter;
import oracle.saas.logan.model.persistance.EmLoganSourcePattern1;
import oracle.saas.logan.model.persistance.EmTargetTypes;
import oracle.saas.logan.model.session.rule.LoganRuleSession;
import oracle.saas.logan.model.session.source.LoganRuleSourceMapSessionBean;
import oracle.saas.logan.model.session.source.LoganSourceSessionBean;
import oracle.saas.logan.model.session.util.LoganMetaSourceTypeSession;
import oracle.saas.logan.view.BaseDataPersistenceHandler;
import oracle.saas.logan.view.IPersistDataOpsHandler;
import oracle.saas.logan.view.KeyValuePairObj;
import oracle.saas.logan.view.rule.LoganRuleTargetAssoc;
import oracle.saas.logan.view.source.LoganLibSourcePojo;
import oracle.saas.logan.view.source.LoganMetaAvailParameters;
import oracle.saas.logan.view.source.LoganSourceDetailsParameter;
import oracle.saas.logan.view.source.LoganSourceFilePattern;

import org.apache.myfaces.trinidad.context.RequestContext;
import org.apache.myfaces.trinidad.model.RowKeySet;


public class LoganLibUiUtil {

    private static final Logger s_log = Logger.getLogger(LoganLibUiUtil.class.getName());

    public static final String TAB_PARAMS_ID = "sdi12";
    public static final String TAB_LOGFILES_ID = "sdi11";
    public static final String TABS_ID = "emT:pt11";

    private static final String METRIC_PREFIX = "LoganRuleMetricExt_";

    private static final String RegexToMatchCurlyBraces = "(?i)(\\{)(.+?)(\\})";
    private static final String ESC_CURLY_OPEN = "_E_S_C__C_U_R_L_Y__O_P_E_N_";
    private static final String ESC_CURLY_CLOSE = "_E_S_C__C_U_R_L_Y__C_L_O_S_E_";
    private static final String SOURCE_CONST = "sourceId";
    private static final String MODE_CONST = "mode";
    private static final String AUTHOR_CONST = "author";
    private static final String SOURCE_DNAME_CONST = "sourceDname";
    private static final String SOURCE_TYPE_CONST = "sourceType";
    private static final String TARGET_TYPE_CONST = "targetType";
    private static final String SOURCE_IDS_STRING_CONST = "sourceIdsString";

    private static final String CREATE_LIKE_SOURCE_NAME_CONST = "create_like_logan_source_name";
    private static final String CREATE_LIKE_RULE_NAME_CONST = "create_like_logan_rule_name";

    private static final String SOURCE_REF_KEY_CONST = "SOURCE_REF_KEY_CONST";
    private static final String RULE_REF_KEY_CONST = "RULE_REF_KEY_CONST";

    private static final String RULE_ID_CONST = "ruleId";
    private static final String RULE_DNAME_CONST = "ruleDname";

    private static final String MD5_ALGO = "MD5";


    private static final String BLANK_INAME = "blankiname";
    private static final String SEPERATE_LINE = "-----------------------------------------";


    private static final String LOG_RULES_SESS_FACADE_EJB_JNDI_NAME =
        "saas-SaasLoganModel-LoganRuleSession#oracle.saas.logan.model.session.rule.LoganRuleSession";

    private static final String LOG_META_SOURCE_TYPE_SESS_FACADE_EJB_JNDI_NAME =
        "saas-SaasLoganModel-LoganMetaSourceTypeSession#oracle.saas.logan.model.session.util.LoganMetaSourceTypeSession";

    private static final String LOG_SOURCE_SESS_FACADE_EJB_JNDI_NAME =
        "saas-SaasLoganModel-LoganSourceSessionBean#oracle.saas.logan.model.session.source.LoganSourceSessionBean";

    private static final String LOG_RULE_SOURCE_MAP_SESS_FACADE_EJB_JNDI_NAME =
        "saas-SaasLoganModel-LoganRuleSourceMapSessionBean#oracle.saas.logan.model.session.source.LoganRuleSourceMapSessionBean";


    private static final Map<String, String> DB_SEQ_NAMES_MAP;

    private static final String[][] LOGAN_TABLE_SEQ_NAMES = {
        { "EM_LOGAN_BROWSE_SESSION_SEQ", "select EM_LOGAN_BROWSE_SESSION_SEQ.NEXTVAL from dual" },
        { "EM_LOGAN_BROWSE_REQUEST_SEQ", "select EM_LOGAN_BROWSE_REQUEST_SEQ.NEXTVAL from dual" },
        { "EM_LOGAN_BROWSE_SEARCHSTAT_SEQ", "select EM_LOGAN_BROWSE_SEARCHSTAT_SEQ.NEXTVAL from dual" },
        { "EM_LOGAN_BROWSE_BASESEARCH_SEQ", "select EM_LOGAN_BROWSE_BASESEARCH_SEQ.NEXTVAL from dual" },
        { "EM_LOGAN_BROWSE_EXECUTION_SEQ", "select EM_LOGAN_BROWSE_EXECUTION_SEQ.NEXTVAL from dual" },
        { "EM_LOGAN_META_WARNTYPE_SEQ", "select EM_LOGAN_META_WARNTYPE_SEQ.NEXTVAL from dual" },
        { "EM_LOGAN_PARSER_SEQ", "select EM_LOGAN_PARSER_SEQ.NEXTVAL from dual" },
        { "EM_LOGAN_PARSER_FIELD_MAP_SEQ", "select EM_LOGAN_PARSER_FIELD_MAP_SEQ.NEXTVAL from dual" },
        { "EM_LOGAN_SOURCE_SEQ", "select EM_LOGAN_SOURCE_SEQ.NEXTVAL from dual" },
        { "EM_LOGAN_SOURCE_PATTERN_SEQ", "select EM_LOGAN_SOURCE_PATTERN_SEQ.NEXTVAL from dual" },
        { "EM_LOGAN_EXTFIELD_DEFN_SEQ", "select EM_LOGAN_EXTFIELD_DEFN_SEQ.NEXTVAL from dual" },
        { "EM_LOGAN_EXTFIELDS_SEQ", "select EM_LOGAN_EXTFIELDS_SEQ.NEXTVAL from dual" },
        { "EM_LOGAN_REFERENCE_SEQ", "select EM_LOGAN_REFERENCE_SEQ.NEXTVAL from dual" },
        { "EM_LOGAN_REFERENCE_CONTENT_SEQ", "select EM_LOGAN_REFERENCE_CONTENT_SEQ.NEXTVAL from dual" },
        { "EM_LOGAN_RULE_SEQ", "select EM_LOGAN_RULE_SEQ.NEXTVAL from dual" },
        { "EM_LOGAN_RULE_CONDITION_SEQ", "select EM_LOGAN_RULE_CONDITION_SEQ.NEXTVAL from dual" },
        { "EM_LOGAN_RULE_CONDFRAGMENT_SEQ", "select EM_LOGAN_RULE_CONDFRAGMENT_SEQ.NEXTVAL from dual" },
        { "EM_LOGAN_SAVED_FILTER_SEQ", "select EM_LOGAN_SAVED_FILTER_SEQ.NEXTVAL from dual" },
        { "EM_LOGAN_RULE_TGTOVR_FIELD_SEQ", "select EM_LOGAN_RULE_TGTOVR_FIELD_SEQ.NEXTVAL from dual" },
        { "EM_LOGAN_TAGD_ELEMENT_SEQ", "select EM_LOGAN_TAGD_ELEMENT_SEQ.NEXTVAL from dual" },
        { "EM_LOGAN_TAG_DEFINITION_SEQ", "select EM_LOGAN_TAG_DEFINITION_SEQ.NEXTVAL from dual" }
    };


    private static final String[] standardRegexes = { "[a-z]+", // 1+ chars in [a-z]
                                                      "[A-Z]+", // 1+ chars in [A-Z] - all caps
                                                      "\\w+", // 1+ chars in [a-zA-Z_0-9]
                                                      "\\d+", // 1+ [0-9]
                                                      "\\d+:", // 1+ [0-9] followed by a colon
                                                      "'\\w+'\\.?", // ' followed by 1+ [a-zA-Z_0-9]followed by ' and optional .
                                                      "\\w+:[0-9]+", // 1+ chars in [a-zA-Z_0-9] followed by : and [0-9]+
                                                      "\\w+:[0-9\\.]+", // 1+ chars in [a-zA-Z_0-9] followed by : and [0-9]+ or .
                                                      "[a-z]+[0-9]+[a-z]+", // 1+ chars in [a-z] then 1+ [0-9] then 1+ [a-z]
                                                      "[a-zA-Z]+:[0-9]+", // 1+ chars in [a-zA-Z_0-9] followed by : and [0-9]+
                                                      "[a-zA-Z0-9_\\.\\s]+", // multiple words having alpha num chars or DOTs
                                                      ".*"
    };

    static {
        DB_SEQ_NAMES_MAP = new HashMap<String, String>(LOGAN_TABLE_SEQ_NAMES.length);
        for (String[] kv : LOGAN_TABLE_SEQ_NAMES) {
            DB_SEQ_NAMES_MAP.put(kv[0], kv[1]);
        }
    }


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


    public static List<SelectItem> getRefreshedTargetTypesList(boolean includeAll) {
        targetTypeList = null;
        return getTargetTypesList(includeAll);
    }

    private static List<SelectItem> targetTypeList = null;

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
        if (targetTypeList == null) {
            targetTypeList = new ArrayList<SelectItem>();
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
    public static BaseDataPersistenceHandler getSelectedIPRowForTable(RichTable iT) {
        BaseDataPersistenceHandler selR = null;
        if (iT != null) {
            RowKeySet rowKeySet = iT.getSelectedRowKeys();
            if (rowKeySet != null) {
                Iterator iter = rowKeySet.iterator();
                if (iter != null && iter.hasNext()) {
                    iT.setRowKey(iter.next());
                    selR = (BaseDataPersistenceHandler)iT.getRowData();
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
    public static boolean hasUserSelectedMultipleOrZeroRows(RichTable table) {
        // check if table has multiple rows selected then true else false
        boolean multiOrZeroRows = false;

        int rowsSelected = 1;
        if (table != null) {
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
    public static LoganRuleSession getLogRulesSessionFacadeEJB() {
        LoganRuleSession logRulesSessionFacadeEJB = null;
        try {
            final Context context = getInitialContext();
            logRulesSessionFacadeEJB = (LoganRuleSession)context.lookup(LOG_RULES_SESS_FACADE_EJB_JNDI_NAME);
        } catch (CommunicationException ex) {
            if (s_log.isFineEnabled())
                s_log.fine("getLogRulesSessionFacadeEJB"+
                           "*** A CommunicationException was raised. " +
                           "This typically occurs when the target WebLogic server is not running", ex);
        } catch (Exception ex) {
            if (s_log.isFineEnabled())
                s_log.fine( "getLogRulesSessionFacadeEJB"+
                           "*** Error occurred trying to get  LogRulesSessionFacadeEJB: ", ex);
        }
        return logRulesSessionFacadeEJB;
    }

    /**
     * The EJB interface for Log Meta Source Type
     * @return
     */
    public static LoganMetaSourceTypeSession getLoganMetaSourceTypeSessionFacadeEJB() {
        LoganMetaSourceTypeSession loganMetaSourceTypeSession = null;
        try {
            final Context context = getInitialContext();
            loganMetaSourceTypeSession =
                (LoganMetaSourceTypeSession)context.lookup(LOG_META_SOURCE_TYPE_SESS_FACADE_EJB_JNDI_NAME);
        } catch (CommunicationException ex) {
            if (s_log.isFineEnabled())
                s_log.fine( "getLoganMetaSourceTypeSessionFacadeEJB"+
                           "*** A CommunicationException was raised. " +
                           "This typically occurs when the target WebLogic server is not running", ex);
        } catch (Exception ex) {
            if (s_log.isFineEnabled())
                s_log.fine( "getLoganMetaSourceTypeSessionFacadeEJB"+
                           "*** Error occurred trying to get  getLoganMetaSourceTypeSessionFacadeEJB: ", ex);
        }
        return loganMetaSourceTypeSession;
    }

    /**
     * The EJB interface for Log Source
     * @return
     */
    public static LoganSourceSessionBean getLogSourcesSessionFacadeEJB() {
        LoganSourceSessionBean logSourceSessionFacadeEJB = null;
        try {
            final Context context = getInitialContext();
            logSourceSessionFacadeEJB = (LoganSourceSessionBean)context.lookup(LOG_SOURCE_SESS_FACADE_EJB_JNDI_NAME);
        } catch (CommunicationException ex) {
            if (s_log.isFineEnabled())
                s_log.fine("getLogSourcesSessionFacadeEJB"+
                           "*** A CommunicationException was raised. " +
                           "This typically occurs when the target WebLogic server is not running", ex);
        } catch (Exception ex) {
            if (s_log.isFineEnabled())
                s_log.fine( "getLogSourcesSessionFacadeEJB"+
                           "*** Error occurred trying to get  LogSourcesSessionFacadeEJB: ", ex);
        }
        return logSourceSessionFacadeEJB;
    }

    //TODO remove the direct EJB calling

    /**
     * The EJB interface for Log Types
     * @return
     */
    public static LoganRuleSourceMapSessionBean getLogRuleSourceMapSessionFacadeEJB() {
        LoganRuleSourceMapSessionBean logRuleSourceMapSessionFacadeEJB = null;
        try {
            final Context context = getInitialContext();
            logRuleSourceMapSessionFacadeEJB =
                (LoganRuleSourceMapSessionBean)context.lookup(LOG_RULE_SOURCE_MAP_SESS_FACADE_EJB_JNDI_NAME);
        } catch (CommunicationException ex) {
            if (s_log.isFineEnabled())
                s_log.fine("getLogRuleSourceMapSessionFacadeEJB"+
                           "*** A CommunicationException was raised. " +
                           "This typically occurs when the target WebLogic server is not running", ex);
        } catch (Exception ex) {
            if (s_log.isFineEnabled())
                s_log.fine("getLogRuleSourceMapSessionFacadeEJB"+
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
    public static String getParamFromReqParams(String param) {
        Map ipmParams = InterPageMessageBean.getCurrentInstance().getParams();
        return (String)ipmParams.get(param);
    }

    /**
     * Get the mode for Log Rule / Log Source train (bounded task flow). Mode
     * can be create / create_like / edit
     *
     * @return the mode for train
     */
    public static String getModeForTrain() {
        return getParamFromReqParams(MODE_CONST);
    }

    /**
     * Gets the row selected from ui table.
     *
     * @param iT
     *            reference to a RichTable
     * @return the row selected from ui table
     */
    public static List getRowSelectedFromUiTable(RichTable iT) {
        List retval = new ArrayList();
        boolean added = false;
        Object row = null;
        if (iT != null) {
            RowKeySet rowKeySet = iT.getSelectedRowKeys();
            if (rowKeySet != null) {
                Iterator iter = rowKeySet.iterator();
                while (iter != null && iter.hasNext()) {
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
            retval = null;

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
    public static List<SelectItem> getRuleSeverityList(ResourceBundle loganBundle) {
        List<SelectItem> severityList = new ArrayList<SelectItem>();
        severityList.add(new SelectItem("18", UiUtil.getUiString("INFORMATIONAL")));
        severityList.add(new SelectItem("20", UiUtil.getUiString("WARNING")));
        severityList.add(new SelectItem("25", UiUtil.getUiString("CRITICAL")));
        return severityList;
    }

    /**
     * List of Log Source types as SelectItem with value = SrctypeIname and
     * label = SrctypeDname.
     * @return List of Log Source types
     */
    public static List<SelectItem> getSourceTypesList() {
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
    public static List<SelectItem> getSourceTypesList(boolean includeAll) {
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


    public static List<LoganLibSourcePojo> getFilteredSourceListForRules(String targetType, String logType, String name,
                                                                         String desc) {
        //TODO add explicit isMatchAll for all query
        return getFilteredSourceListForRules(targetType, logType, name, desc, false);
    }

    /**
     * Get the List of LoganSourceDetailsBean for log rules UI which are
     * associated with the given targetType, logType and have the
     * given name and description
     * JPA version
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
    public static List<LoganLibSourcePojo> getFilteredSourceListForRules(String targetType, String logType, String name,
                                                                         String desc, boolean isMatchAll) {
        List<LoganLibSourcePojo> sourceList = new ArrayList<LoganLibSourcePojo>();

        List<Object[]> pojos =
            LoganSessionBeanProxy.getEmLoganSourceFilteredSourceList(targetType, logType, (name == null ? "" : name),
                                                                     (desc == null ? "" : desc), isMatchAll);

        for (Object[] objs : pojos) {

            EmLoganSource emLoganSource = (EmLoganSource)objs[0];
            EmLoganMetaSourceType emLoganMetaSourceType = (EmLoganMetaSourceType)objs[1];
            EmTargetTypes emTargetTypes = (EmTargetTypes)objs[2];

            LoganLibSourcePojo source = new LoganLibSourcePojo();
            source.setSourceAuthor(emLoganSource.getSourceAuthor());
            source.setSourceId(emLoganSource.getSourceId());
            source.setSourceDescription(emLoganSource.getSourceDescription());
            source.setSourceDname(emLoganSource.getSourceDname());
            source.setSourceIsSystem(emLoganSource.getSourceIsSystem());
            source.setSourceLastUpdatedDate(emLoganSource.getSourceLastUpdatedDate());
            source.setSrcTargetType(emTargetTypes.getTargetType());
            source.setSrcTargetTypeDisplayNls(emTargetTypes.getTypeDisplayName());
            source.setSrctypeDname(emLoganMetaSourceType.getSrctypeDname());


            sourceList.add(source);
        }
        return sourceList;
    }


    /**
     * Get the current Row object from the EmLoganSourceVO which the
     * collectionModel.makeCurrent sets when row is selected on the UI table
     * backed by the EmLoganSourceVO.
     *
     * @param sourceId
     *            the source id
     * @return current Row of EmLoganSourceVO
     */
    @Deprecated
    public static Row getCurrentLogSourceRow(Number sourceId) {
        Row selectedSourceCtxRow = null;
        //TODO JPA
        //        if (sourceId != null)
        //        {
        //            // get the current VO row for the EmLogSourceVO
        //            EMApplicationModuleImpl am = getAppModule();
        //            EmLoganSourceVOImpl sourceVO =
        //                (EmLoganSourceVOImpl) am.findViewObject("EmLoganSourceVO");
        //            selectedSourceCtxRow = sourceVO.getCurrentRow();
        //        }
        return selectedSourceCtxRow;
    }
    


    /**
     * Adds the partial target for a UI Element.
     * TODO: consider moving this to UiUtil.java
     * @param id
     *            the id of the UI element
     */
    public static void addPPR(String id) {
        UIComponent component = AdfUtil.findComponent(id);
        ;
        if (component != null) {
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
    public static List<LoganRuleTargetAssoc> loadLogRuleTargets(Number ruleId) {
        if (ruleId == null)
            return null;
        List<LoganRuleTargetAssoc> targets = new ArrayList<LoganRuleTargetAssoc>();

        return targets;
    }

    /**
     * Gets the row selected from ui table.
     *
     * @param tableId
     *            the table id like "emT:pgl1:t1"
     * @return the row selected from ui table
     */
    public static List getRowSelectedFromUiTable(String tableId) {
        RichTable iT = (RichTable)AdfUtil.findComponent(tableId);
        return getRowSelectedFromUiTable(iT);
    }
    
    
    public static String getNextSequenceIdSql(String seqName)
    {
        String idSQL = DB_SEQ_NAMES_MAP.get(seqName);
        return idSQL;
    }
    


    /**
     * This method will get the next sequenceId using the Application Module
     * DBConnection.
     *
     * @param seqName
     *            the seq name
     * @return the next sequence id
     */
    public static Number getNextSequenceId(String seqName) {
        Number nextId = new Number(-1);
        String idSQL = DB_SEQ_NAMES_MAP.get(seqName);
        PreparedStatement stmt = null;
        ResultSet rs = null;
        //TODO JPA version

        //        EMApplicationModuleImpl am = getAppModule();
        //        try
        //        {
        //            DBTransaction dbt = am.getDBTransaction();
        //            stmt = dbt.createPreparedStatement(idSQL, DBTransaction.DEFAULT);
        //            rs = stmt.executeQuery();
        //            rs.next();
        //            nextId = new Number(rs.getInt(1));
        //        }
        //        catch (Exception ew)
        //        {
        //            s_log.warning("error trying to get next sequence val using: " +
        //                          idSQL, ew);
        //        }
        //        finally
        //        {
        //            JDBCUtil.close(stmt, rs);
        //        }
        return nextId;
    }


    /**
     * Disclose patterns tab on log source details page.
     * TODO: Consider moving to UiUtil.java
     */
    public static void disclosePatternsTab() {
        RichShowDetailItem paramstab = (RichShowDetailItem)AdfUtil.findComponentInRoot(TAB_PARAMS_ID);
        RichShowDetailItem patternstab = (RichShowDetailItem)AdfUtil.findComponentInRoot(TAB_LOGFILES_ID);
        boolean b = patternstab.isDisclosed();
        if (b == false) {
            // switch back to patterns - user needs to correct issue
            patternstab.setDisclosed(true);
            paramstab.setDisclosed(false);
        }
        // refresh tabs
        LoganLibUiUtil.addPPR(TABS_ID);
        return;
    }


    /**
     * Disclose params tab on log source details page.
     * TODO: Consider moving to UiUtil.java
     */
    public static void discloseParamsTab() {
        RichShowDetailItem paramstab = (RichShowDetailItem)AdfUtil.findComponentInRoot(TAB_PARAMS_ID);
        RichShowDetailItem patternstab = (RichShowDetailItem)AdfUtil.findComponentInRoot(TAB_LOGFILES_ID);
        boolean b = paramstab.isDisclosed();
        if (b == false) {
            // switch back to patterns - user needs to correct issue
            paramstab.setDisclosed(true);
            patternstab.setDisclosed(false);
        }
        // refresh tabs
        LoganLibUiUtil.addPPR(TABS_ID);
        return;
    }


    /**
     * Get a List of SelectItems having the values as ParserIname (internal) and
     * the labels as the ParserDname (display names).
     * JPA version
     * @return SelectItem list of all parsers available
     */
    public static List<SelectItem> getParsersList() {
        List<SelectItem>  items = new ArrayList<SelectItem>();
        List<EmLoganParser> parsers = LoganSessionBeanProxy.getEmLoganParserFindAll();
        if (parsers != null && parsers.size() > 0) {
            for (EmLoganParser parser : parsers) {
                SelectItem item = new SelectItem();
                item.setLabel(parser.getParserDname());
                item.setValue(parser.getParserIname());
                //original use id as description?
                item.setDescription(parser.getParserDescription());
                items.add(item);
            }
        }

        //        EMViewObjectImpl stvo = getAppModule().getEmLoganParserVO();
        //        return getSelectItemList(stvo,
        //                                 "ParserIname",
        //                                 "ParserDname",
        //                                 "ParserId",
        //                                 false);
        return items;
    }


    /**
     * Get the list of distinct Parameter Names that can be used in the log
     * source definitions for a log source of a given target type. For a given
     * target type, for different versions of the plugin to which the target
     * type belongs the same parameter name could be registered multiple times
     * with various plugin versions, but the log source UI needs a distinct list
     * of the parameter names for the given target type and we do not need to
     * show which plugin version(s) registered this parameter for the given
     * target type. If a user uses a parameter in the log source file pattern,
     * and tries to assciate a rule containing the log source to a target which
     * is as yet on an older plugin version and does not have the respective
     * target property, we will ask the user at rule-target assoc time to choose
     * an alternate property or a non-null override hard-coded value for such
     * parameters.
     *
     * We are now adding to the set of LoganMetaAvailParameters that
     * we return from here, the set of parameters applicable to
     * all target types and plugin ids, versions.
     *
     * @param ttype
     *            the target type
     * @return the logan meta avail parameters
     */
    public static Set<LoganMetaAvailParameters> getLoganMetaAvailParameters(String ttype) {
        if (ttype == null)
            return null;

        List<EmLoganMetaAvailParam> params = LoganSessionBeanProxy.getEmLoganMetaAvailParamFindAllByTargetType(ttype);
        Map<String, LoganMetaAvailParameters> availParams = new HashMap<String, LoganMetaAvailParameters>();
        Set<LoganMetaAvailParameters> availPDistinctSet = new HashSet<LoganMetaAvailParameters>();

        if (params != null || params.size() > 0) {

            for (EmLoganMetaAvailParam vorow : params) {
                LoganMetaAvailParameters cb = new LoganMetaAvailParameters(vorow);
                boolean exists = !(availPDistinctSet.add(cb));
                if (exists) {
                    LoganMetaAvailParameters existingParam = availParams.get(cb.getPrimaryKeyAsString());
                    String pver = cb.getParamAvailPluginVersion();
                    existingParam.addParamAvailablePluginVersion(pver);
                } else // new avail param object
                {
                    availParams.put(cb.getPrimaryKeyAsString(), cb);
                }
            }
            availPDistinctSet.addAll(getLoganMetaAvailParamsApplicableToAllTypes());
        }


        return availPDistinctSet;
    }


    /**
     * Get the set of LoganMetaAvailParameters which are applicable to
     * all target types for all plugin ids and version.
     * JPA version
     * @return Set of LoganMetaAvailParameters applicable to all types.
     */
    public static Set<LoganMetaAvailParameters> getLoganMetaAvailParamsApplicableToAllTypes() {

        Set<LoganMetaAvailParameters> availParamForAll = new HashSet<LoganMetaAvailParameters>();

        List<EmLoganMetaAvailParam> params = LoganSessionBeanProxy.getEmLoganMetaAvailParamFindAll();

        if (params != null && params.size() > 0) {

            for (EmLoganMetaAvailParam param : params) {
                String paramName = param.getApName();
                String targetType = param.getApTargetType();
                String pluginId = param.getApPluginId();
                String pluginVersion = param.getApPluginVersion();
                int paramType = param.getApType();
                String paramDescription = param.getApDescription();
                String paramDescNlsId = param.getApDescriptionNlsid();
                String author = param.getApAuthor();
                List<String> paramAvailPluginVersions = new ArrayList<String>();
                paramAvailPluginVersions.add(pluginVersion);
                LoganMetaAvailParameters cb =
                    new LoganMetaAvailParameters(paramName, targetType, paramType, paramDescription, paramDescNlsId,
                                                 pluginId, author, paramAvailPluginVersions);
                availParamForAll.add(cb);
            }

        }
        return availParamForAll;
    }


    /**
     * Filtered log source include patterns for the given sourceId.
     * Also see "EmLoganSourceInclPatternVOCriteria" in EmLoganSourceExclPatternVO.
     *
     * @param sourceId
     *            the source id
     * @return the list of included LoganSourceFilePattern
     */
    public static List<LoganSourceFilePattern> filteredSourcePatternsInclude(Number sourceId) {
        return filterPatterns(sourceId, true);
    }

    /**
     * Filtered log source exclude patterns for the given sourceId.
     * Also see "EmLoganSourceExclPatternVOCriteria" in EmLoganSourceExclPatternVO.
     *
     * @param sourceId
     *            the source id
     * @return the list of excluded LoganSourceFilePattern
     */
    public static List<LoganSourceFilePattern> filteredSourcePatternsExclude(Number sourceId) {
        return filterPatterns(sourceId, false);
    }

    /**
     * Gets the list of Filtered log source file patterns for the given
     * sourceId.
     *
     * @param sourceId
     *            the source id
     * @param remVC
     *            the view criteria to remove (if applied)
     * @param applyVC
     *            the view criteria to apply for filtering.
     * @param isInclude
     *            the is include flag
     * @return the list of LoganSourceFilePattern
     */
    private static List<LoganSourceFilePattern> filterPatterns(Number sourceId, boolean isInclude) {
        if (sourceId == null)
            return null;
        List<LoganSourceFilePattern> patts = new ArrayList<LoganSourceFilePattern>();

        Integer isIncl = 1;
        if (!isInclude) {
            isIncl = 0;
        }
        List<Object[]> entities =
            LoganSessionBeanProxy.getEmLoganSourcePatternFindAllBySourceId(isIncl,sourceId.intValue());
        if (entities != null && entities.size() > 0) {

            for (Object[] objs : entities) {
                EmLoganSourcePattern1 emLoganPattern = (EmLoganSourcePattern1)objs[0];
                EmLoganSource emLoganSource = (EmLoganSource)objs[1];
                EmLoganParser emLoganParser = (EmLoganParser)objs[2];

                BigDecimal d = new BigDecimal(emLoganPattern.getPatternId());
                Number patternId = null;
                try {
                    patternId = new Number(d);
                } catch (SQLException e) {
                    //TODO log
                }
                //              BigDecimal d2 = new BigDecimal( emLoganPattern.getPatternSourceId());
                //              Number pattSrcId =new Number(d2);
                String fileName = emLoganPattern.getPatternText();
                String fileDesc = emLoganPattern.getPatternDescription();
                String fileParser = emLoganParser.getParserDname();
                String fileParserIName = emLoganParser.getParserIname();
                String author = emLoganParser.getParserAuthor();
                String sourceTargetType = emLoganSource.getSourceTargetType();
                Integer isInc = emLoganPattern.getPatternIsInclude();
                boolean includePattern = (isInc.intValue() == 1);

                LoganSourceFilePattern pat =
                    new LoganSourceFilePattern(patternId, sourceId, author, fileName, fileDesc, fileParser,
                                               fileParserIName, includePattern, sourceTargetType);
                patts.add(pat);

            }
        }


        //        LoganAMImpl am = getAppModule();
        //        EMViewObjectImpl pattVO = am.getEmLoganSourceInclPatternVO();
        //        if (!isInclude)
        //            pattVO = am.getEmLoganSourceExclPatternVO();
        //
        //        pattVO.removeApplyViewCriteriaName(remVC);
        //        pattVO.setApplyViewCriteriaName(applyVC, false);
        //        // set the bind var value
        //        pattVO.ensureVariableManager().setVariableValue("patternSourceIdVar",
        //                                                        sourceId);
        //        pattVO.executeQuery();
        //        for(Row vorow : getRows(pattVO))
        //        {
        //            patts.add(new LoganSourceFilePattern(vorow));
        //        }


        return patts;
    }


    /**
     * Load log source parameters for the given sourceId.
     * Also see "EmLoganSourceParameterVOCriteria" in EmLoganSourceParameterVO.
     *
     * @param sourceId
     *            the source id
     * @return the list of LoganSourceDetailsParameter
     */
    public static List<LoganSourceDetailsParameter> loadLogSourceParameters(Number sourceId) {
        if (sourceId == null)
            return null;
        List<LoganSourceDetailsParameter> patts = new ArrayList<LoganSourceDetailsParameter>();

        List<EmLoganSourceParameter> entities =
            LoganSessionBeanProxy.getEmLoganSourceParameterFindAllBySourceId(sourceId.intValue());
        if (entities != null && entities.size() > 0) {

            for (EmLoganSourceParameter parameter : entities) {


                BigDecimal d = new BigDecimal(parameter.getParamSourceId());
                Number paramSourceId = null;
                try {
                    paramSourceId = new Number(d);
                } catch (SQLException e) {
                    //TODO log
                }
                String paramName = parameter.getParamName();
                String defaultValue = parameter.getParamDefaultValue();

                BigDecimal d2 = new BigDecimal(parameter.getParamIsActive());
                Number paramIsActive = null;
                try {
                    paramIsActive = new Number(d2);
                } catch (SQLException e) {
                    //TODO log
                }

                String paramDescription = parameter.getParamDescription();
                String author = parameter.getParamAuthor();
                boolean paramActive = (paramIsActive.intValue() == 1);

                LoganSourceDetailsParameter dParameter =
                    new LoganSourceDetailsParameter(paramSourceId, paramName, defaultValue, paramDescription, author,
                                                    paramActive);
                patts.add(dParameter);

            }
        }


        // old version
        //        LoganAMImpl am = getAppModule();
        //        EMViewObjectImpl paramVO = am.getEmLoganSourceParameterVO();
        //        paramVO.setApplyViewCriteriaName("EmLoganSourceParameterVOCriteria",
        //                                         false);
        //        // set the bind var value
        //        paramVO.ensureVariableManager().setVariableValue("ParamSourceIdVar",
        //                                                         sourceId);
        //        paramVO.executeQuery();
        //        for(Row vorow : getRows(paramVO))
        //        {
        //            patts.add(new LoganSourceDetailsParameter(vorow));
        //        }

        return patts;
    }
    
    
    /**
      * Return the objects in the list by casting those to
      * IPersistDataOpsHandler.
      * 
      * @param fpList the list of objects which must be implementing 
      *               the IPersistDataOpsHandler interface
      * @return the list of objects cast to IPersistDataOpsHandler interface
      */
    public static List<IPersistDataOpsHandler> getIPersistList(List fpList)
    {
        List<IPersistDataOpsHandler> rList = null;
        if (fpList != null)
        {
            rList = new ArrayList<IPersistDataOpsHandler>(fpList.size());
            for (int i = 0, l = fpList.size(); i < l; i++)
                rList.add((IPersistDataOpsHandler) fpList.get(i));
        }
        return rList;
    }
    
    
    
    /**
      * Persist the data in the objects from the current data list
      * where the objects implement the IPersistDataOpsHandler and hence
      * have implemented the CRUD methods:<p>
      * {@link IPersistDataOpsHandler#handleInsertOperation() insert},
      * {@link IPersistDataOpsHandler#handleUpdateOperation() update} and 
      * {@link IPersistDataOpsHandler#handleDeleteOperation() delete}
      * . <p>The objects also implement the setParentReferenceKeys() 
      * method which we call here once parent data has been inserted and 
      * before the child data is going to be inserted (which will need to know
      * the Foreign key values before the child Data is inserted)
      * 
      * @param referenceData
      *            the List of Reference Data beans
      * @param currentData
      *            the List of Current Data beans
      * @param isReadOnlyMode
      *            is read only mode
      * @param isEditMode
      *            is edit mode
      * @see IPersistDataOpsHandler
      */
    public static void persistChildObjectData(List<IPersistDataOpsHandler> referenceData,
                                               List<IPersistDataOpsHandler> currentData,
                                               boolean isReadOnlyMode,
                                               boolean isEditMode)
    {
        if (isReadOnlyMode) // SHOW DETAILS
            return;

        List<IPersistDataOpsHandler> inserts =
            new ArrayList<IPersistDataOpsHandler>();
        List<IPersistDataOpsHandler> updates =
            new ArrayList<IPersistDataOpsHandler>();
        List<IPersistDataOpsHandler> deletes =
            new ArrayList<IPersistDataOpsHandler>();

        if (!isEditMode) // CREATE OR CREATE_LIKE
        {
            if (currentData != null && currentData.size() > 0)
            {
                inserts = currentData;
            }
        }
        else // EDIT MODE
        {
            // if nothing was previously selected then this
            // is an insert all rows
            if (referenceData == null || referenceData.size() == 0)
            {
                if (currentData != null && currentData.size() > 0)
                {
                    for (IPersistDataOpsHandler p: currentData)
                    {
                        inserts.add(p);
                        if (s_log.isFinestEnabled())
                            s_log.finest("edit mode adding : " +
                                         p.getPrimaryKeyAsString());
                    }
                }
            }
            else // means reference list not blank :  referenceData != null && referenceData.size() > 0
            {
                // if nothing currently selected then this 
                // is a remove all rows
                if (currentData == null || currentData.size() == 0)
                {
                    for (IPersistDataOpsHandler p: referenceData)
                    {
                        deletes.add(p);
                        if (s_log.isFinestEnabled())
                            s_log.finest("edit mode removing : " +
                                         p.getPrimaryKeyAsString());
                    }
                }
                else // means current list also not blank :  currentData != null && currentData.size() > 0
                {
                    HashMap<String, IPersistDataOpsHandler> matchedRefMap =
                        new HashMap<String, IPersistDataOpsHandler>();
                    for (IPersistDataOpsHandler p: currentData)
                    {
                        IPersistDataOpsHandler matchedP = null;
                        for (IPersistDataOpsHandler r: referenceData)
                        {
                            // break out when matching key is found
                            if (p.hasMatchingPrimaryKey(r))
                            {
                                matchedP = r;
                                matchedRefMap.put(r.getPrimaryKeyAsString(),
                                                  r);
                                break;
                            }
                        }
                        // if no match found this is insert
                        if (matchedP == null)
                        {
                            inserts.add(p);
                            if (s_log.isFinestEnabled())
                                s_log.finest("Edit mode INSERT op : " +
                                             p.getPrimaryKeyAsString());
                        }
                        else
                        {
                            // if the object contents do not match
                            // then this is an update
                            if (!matchedP.equals(p)) // object Edited
                            {
                                updates.add(p);
                                if (s_log.isFinestEnabled())
                                    s_log.finest("UPDATE op : " +
                                                 p.getPrimaryKeyAsString());
                            }
                            // both objects do match its a NO-OP
                            else
                            {
                                if (s_log.isFinestEnabled())
                                    s_log.finest("NOOP  in Edit mode: " +
                                                 p.getPrimaryKeyAsString());
                            }
                        }
                    }
                    // loop the reference list to check for missing objects
                    for (IPersistDataOpsHandler r: referenceData)
                    {
                        // if we cannot find the key then this is a delete
                        if (!matchedRefMap.containsKey(r.getPrimaryKeyAsString()))
                        {
                            deletes.add(r);
                            if (s_log.isFinestEnabled())
                                s_log.finest("Edit mode DELETE op : " +
                                             r.getPrimaryKeyAsString());
                        }
                    }
                }
            }
        }
        for (IPersistDataOpsHandler i: inserts)
        {
            handleInsert(i);
        }
        for (IPersistDataOpsHandler u: updates)
        {
            u.handleUpdateOperation();
        }
        for (IPersistDataOpsHandler d: deletes)
        {
            d.handleDeleteOperation();
        }
        if (s_log.isFinestEnabled())
            s_log.finest("Persisted Data");
    }
    
    /**
      * Handle insert of Data Object which implement the IPersistDataOpsHandler
      * interface and hence has implemented methods for 
      * {@link IPersistDataOpsHandler#handleInsertOperation() insert},
      * {@link IPersistDataOpsHandler#getDependentChildObjsList() dependents}
      * and {@link IPersistDataOpsHandler#setParentReferenceKeys(List<KeyValuePairObj>) parentKeys}
      * .
      * @param ipdoh
      *            the object implementing IPersistDataOpsHandler
      */
    private static void handleInsert(IPersistDataOpsHandler ipdoh)
    {
        ipdoh.handleInsertOperation();
        if (ipdoh.hasDependentChildObjects())
        {
            List<IPersistDataOpsHandler> childList =
                ipdoh.getDependentChildObjsList();
            if (childList != null && childList.size() > 0)
            {
                List<KeyValuePairObj> prks =
                    ipdoh.getReferenceKeysForDependents();
                for (IPersistDataOpsHandler child: childList)
                {
                    child.setParentReferenceKeys(prks);
                }
            }
        }
    }
    

    /**
     * Checks if it is a valid source name.
     * Internally checks if the log source name already exists in the database.
     *
     * @param name
     *            the log source name
     * @return true if log source name does not already exist, otherwise false.
     */
    public static boolean isValidSourceName(String name) {
        boolean valid = false;
        //TODO Use select 1 for performance
        List<EmLoganSource> entities = LoganSessionBeanProxy.getEmLoganSourceFindBySourceName(name);
        if (entities == null || entities.size() == 0) {
            valid = true;
        }
        return valid;
    }


}
