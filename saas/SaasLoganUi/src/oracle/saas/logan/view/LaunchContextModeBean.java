/* $Header: emcore/jsrc/core/CoreLoganalyticsUi/src/oracle/sysman/core/loganalytics/ui/LaunchContextModeBean.java /st_emgc_pt-13.1mstr/2 2014/01/26 17:07:31 vivsharm Exp $ */

/* Copyright (c) 2013, Oracle and/or its affiliates. All rights reserved.*/

/*
   DESCRIPTION
    Eapsulates the launch mode for the various editors from log library tabs

   PRIVATE CLASSES
    <list of private classes defined - with one-line descriptions>

   NOTES
    <other useful comments, qualifications, etc.>

   MODIFIED    (MM/DD/YY)
    vivsharm    01/10/13 - launch context mode bean
    vivsharm    01/10/13 - Creation
 */

/**
 *  @version $Header: emcore/jsrc/core/CoreLoganalyticsUi/src/oracle/sysman/core/loganalytics/ui/LaunchContextModeBean.java /st_emgc_pt-13.1mstr/2 2014/01/26 17:07:31 vivsharm Exp $
 *  @author  vivsharm
 *  @since   release specific (what release of product did this appear in)
 */

package oracle.saas.logan.view;

public class LaunchContextModeBean
{
    private String mode = CREATE_M;

    private boolean readOnlyMode;
    private boolean editMode;
    private boolean createLikeMode;
    private boolean createMode;

    private static final String VIEW_M = "VIEW";
    private static final String EDIT_M = "EDIT";
    private static final String CREATELIKE_M = "CREATE_LIKE";
    private static final String CREATE_M = "CREATE";
    
    private static final LaunchContextModeBean CREATE_CONTEXT =
        new LaunchContextModeBean(CREATE_M);
    
    public LaunchContextModeBean(String mode)
    {
        setMode(mode);
    }
    
    public static LaunchContextModeBean getCreateModeInstance()
    {
        return CREATE_CONTEXT;
    }

    /**
     * @return
     */
    public boolean isCreateLikeMode()
    {
        return this.createLikeMode;
    }

    /**
     * @return
     */
    public boolean isEditMode()
    {
        return this.editMode;
    }

    public boolean isReadOnlyMode()
    {
        return this.readOnlyMode;
    }

    /**
     * @return
     */
    public boolean isReadEditMode()
    {
        return (isReadOnlyMode() || isEditMode());
    }

    /**
     * @param mode
     */
    public void setMode(String mode)
    {
        this.mode = mode;
        this.readOnlyMode = VIEW_M.equals(this.mode);
        this.editMode = EDIT_M.equals(this.mode);
        this.createLikeMode = CREATELIKE_M.equals(this.mode);
        this.createMode = CREATE_M.equals(this.mode);
    }

    /**
     * @return mode
     */
    public String getMode()
    {
        return mode;
    }

    public boolean isCreateMode()
    {
        return createMode;
    }

    public void setModeToCreate()
    {
        setMode(CREATE_M);
    }

    public void setModeToCreateLike()
    {
        setMode(CREATELIKE_M);
    }

    public void setModeToEdit()
    {
        setMode(EDIT_M);
    }

    public void setModeToReadOnly()
    {
        setMode(VIEW_M);
    }
}