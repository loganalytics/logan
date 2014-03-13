/* $Header: emcore/source/oracle/sysman/core/model/message/InterPageMessageDataBean.java /st_emcore_10.2.0.1.0/1 2009/06/14 22:17:50 jashukla Exp $ */

/* Copyright (c) 2007, 2009, Oracle and/or its affiliates. 
All rights reserved. */

/*
   DESCRIPTION
    <short description of component this file declares/defines>

   PRIVATE CLASSES
    <list of private classes defined - with one-line descriptions>

   NOTES
    <other useful comments, qualifications, etc.>

   MODIFIED    (MM/DD/YY)
    sbhagwat     09/26/07 - Creation
    sbhagwat     09/26/07 - Creation
 */

/**
 *  @version $Header: emcore/source/oracle/sysman/core/model/message/InterPageMessageDataBean.java /st_emcore_10.2.0.1.0/1 2009/06/14 22:17:50 jashukla Exp $
 *  @author  sbhagwat 
 *  @since   release specific (what release of product did this appear in)
 */

package oracle.saas.logan.util;

import java.util.Map;

/**
 * Bean that can be used for passing message data between pages.
 * This is been introduced since SDK IPM Bean is inconsistently caching the
 * data when the page navigated to has regions. Once SDK IPM bean issues
 * are resolved this can be transitioned.
 * Note: The responsibility of clearing the message data is on developer
 * who adds the data.
 * Current intention of this class to serve as message data passing
 * mechanism especially for AdfUtil.passMessage
 */
public class InterPageMessageDataBean
{
    /**
     * Managed bean name
     */
    public static final String 
        MESAGE_TEMPLATE_MANAGED_BEAN_NAME = "uifwk_msg_bean";

   /**
    * Message data map
    */
    private Map<String,Object> m_messageData;

    /**
     * Gets the message data
     * @return Map message data
     */
    public Map<String,Object> getMessageData()
    {
        return m_messageData;
    }

    /**
     * Sets the message data
     * @param messageData Message data map.
     */
    public void setMessageData(Map<String,Object> messageData)
    {
        m_messageData = messageData;
    }
};