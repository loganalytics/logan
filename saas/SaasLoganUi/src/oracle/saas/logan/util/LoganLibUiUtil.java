package oracle.saas.logan.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import java.util.logging.Level;

import java.util.logging.Logger;

import javax.faces.model.SelectItem;

import javax.naming.CommunicationException;
import javax.naming.Context;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import oracle.adf.view.rich.component.rich.data.RichTable;

import oracle.saas.logan.model.client.LoganSessionBeanProxy;
import oracle.saas.logan.model.persistance.EmTargetTypes;

import oracle.saas.logan.model.session.rule.LoganRuleSession;

import org.apache.myfaces.trinidad.model.RowKeySet;


public class LoganLibUiUtil {
    
    private static final Logger s_log =
        Logger.getLogger(LoganLibUiUtil.class.getName());
    
    private static final String MODE_CONST = "mode";
    
    private static final String LOG_RULES_SESS_FACADE_EJB_JNDI_NAME = 
        "saas-SaasLoganModel-LoganRuleSession#oracle.saas.logan.model.session.rule.LoganRuleSession";

    
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
    


}
