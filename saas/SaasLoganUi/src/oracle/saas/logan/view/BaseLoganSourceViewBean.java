/* $Header: emcore/jsrc/core/CoreLoganalyticsUi/src/oracle/sysman/core/loganalytics/ui/BaseLoganSourceViewBean.java /st_emgc_pt-13.1mstr/4 2014/01/26 17:07:30 vivsharm Exp $ */

/* Copyright (c) 2002, 2014, Oracle and/or its affiliates. 
All rights reserved.*/

/*
   DESCRIPTION
   Log analytics Base bean having base functions for sources view

   PRIVATE CLASSES
    <list of private classes defined - with one-line descriptions>

   NOTES
    <other useful comments, qualificationsf, etc.>
    MODIFIED    (MM/DD/YY)
    vivshar 01/17/14 - for add data direct
    rban    01/02/14 - log Analytics
    rban    09/02/13 - Creation
 */

/**
 *  @version $Header: emcore/jsrc/core/CoreLoganalyticsUi/src/oracle/sysman/core/loganalytics/ui/BaseLoganSourceViewBean.java /st_emgc_pt-13.1mstr/4 2014/01/26 17:07:30 vivsharm Exp $
 *  @author  rban
 *  @since   13.1.0.0
 */
package oracle.saas.logan.view;

import java.text.MessageFormat;

import oracle.saas.logan.util.AdfUtil;
import oracle.saas.logan.util.Logger;
import oracle.saas.logan.util.UiUtil;
import oracle.saas.logan.view.source.LoganSourceDetailsViewBean;


public abstract class BaseLoganSourceViewBean implements ILoganSourceViewBean {
    
    private static final Logger s_log =
        Logger.getLogger(LoganSourceDetailsViewBean.class.getName());
    private static final String LOG_SOURCE_SAVED_CONF = "LOG_SOURCE_SAVED_CONF";
    private static final String LOG_SOURCE_SAVED_CONF_DETAIL = "LOG_SOURCE_SAVED_CONF_DETAIL";
    private static final String LOG_SOURCE_SAVE_ERROR = "LOG_SOURCE_SAVE_ERROR";
    
    public BaseLoganSourceViewBean() {
        super();
    }

    /**
     * This will validate the data entered by the user for the source and
     * if validation is successful, insert the data in appropriate tables
     * and return true or false based on the success or failure of all
     * inserts.
     * 
     * @return true if create successful else false
     */
    public boolean createSource()
    {
        boolean commitSuccess = 
            saveInternal(getSourceDataBean(),
                         LaunchContextModeBean.getCreateModeInstance());
        return commitSuccess;
    }

    public void save(ILoganSourceDataBean refData, 
                     LaunchContextModeBean modeBean)
    {
        boolean commitSuccess = saveInternal(refData,
                                             modeBean);
        if(commitSuccess)
        {
            if (!modeBean.isReadOnlyMode())
            {
                if(commitSuccess)
                {
                    String confMode =
                        (modeBean.isEditMode()? UiUtil.getUiString("EDITED"):
                         UiUtil.getUiString("CREATED"));
            
                    String confTitle =
                        UiUtil.getUiString(LOG_SOURCE_SAVED_CONF);
                    Object[] tArgs = { confMode };
            
                    String confDetail =
                        UiUtil.getUiString(LOG_SOURCE_SAVED_CONF_DETAIL);
                    Object[] dArgs = 
                        { "'" +this.getName() +"'", confMode };
            
                    AdfUtil.passMessage(AdfUtil.MESSAGE_TYPE.CONFIRMATION,
                                        MessageFormat.format(confTitle, tArgs),
                                        MessageFormat.format(confDetail, dArgs));
                }
                else // FAILURE
                {
                    AdfUtil.passMessage(AdfUtil.MESSAGE_TYPE.ERROR, 
                                    UiUtil.S_UI_MSG_CLASS, LOG_SOURCE_SAVED_CONF, 
                                    LOG_SOURCE_SAVE_ERROR, true);            
                }
            }
        }        
    }

    private boolean saveInternal(ILoganSourceDataBean refData, 
                     LaunchContextModeBean modeBean)
    {
        
        //TODO JPA
        boolean commitSuccess = false;
        boolean validationSuccess = true;
        validationSuccess = validateViewBeanData();
        if (validationSuccess)
        {
            ILoganSourceDataBean sourceDataBean = getSourceDataBean();
            boolean beforeProcessSubmitSuccess = false;
            try
            {
                sourceDataBean.beforeProcessSubmit(modeBean);
                beforeProcessSubmitSuccess =true;
            }
            catch(Exception ew)
            {
                beforeProcessSubmitSuccess = false;
                s_log.warning("Error at beforeProcessSubmit step: ", ew);
            }
                
            if(beforeProcessSubmitSuccess)
            {
                boolean processSubmitStepsSuccess = false;
                try
                {
                     sourceDataBean.processSubmit(refData, modeBean);
                     processSubmitStepsSuccess = true;
                }
                catch(Exception ew)
                {
                    processSubmitStepsSuccess = false;
                    s_log.warning("Error at processSubmit step: ", ew);
                }
    
//               TODO JPA commit succcess process is different with jdbc 
//                if(processSubmitStepsSuccess)
//                {
//                    try
//                    {
//                        // Commit the changes in transaction
//                        LoganLibUiUtil.getAppModule().getTransaction().postChanges();
//                        LoganLibUiUtil.getAppModule().getTransaction().commit();
//                        commitSuccess = true;
//                    }
//                    catch(Exception ew)
//                    {
//                        commitSuccess = false;
//                        s_log.warning("Error at txn post / commit step: ", ew);
//                        LoganLibUiUtil.getAppModule().getTransaction().rollback();
//                    }
//                }
            }
        }
        return commitSuccess;
    }
    
    /**
     * Concrete class to return the respective source data bean
     * The step data bean manages the data loading and persistence
     * for the whole page, it may use composition pattern for
     * various tabs and lists on the page.
     * @return
     */
    public abstract ILoganSourceDataBean getSourceDataBean();

    /**
     * Concrete class will be called to set its reference to a source data bean
     * from the constructor of this class
     * @param tsdb
     */
    public abstract void setSourceDataBean(ILoganSourceDataBean tsdb);
    
    /**
     * Return the Name of the entity created / edited for Saved Confirmation
     * @return
     */
    public abstract String getName();
}
