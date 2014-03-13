/* $Header: emcore/source/oracle/sysman/emSDK/page/msg/InterPageMessage.java /st_emcore_10.2.0.1.0/1 2009/03/10 00:45:55 pnayak Exp $ */

/* Copyright (c) 2004, Oracle Corporation.  All rights reserved.  */

/*
   DESCRIPTION
    <short description of component this file declares/defines>

   PRIVATE CLASSES
    <list of private classes defined - with one-line descriptions>

   NOTES
    <other useful comments, qualifications, etc.>

   MODIFIED    (MM/DD/YY)
    lyang       03/25/04 - lyang_inter_page_message 
    lyang       03/24/04 - Creation
 */

/**
 *  @version $Header: emcore/source/oracle/sysman/emSDK/page/msg/InterPageMessage.java /st_emcore_10.2.0.1.0/1 2009/03/10 00:45:55 pnayak Exp $
 *  @author  lyang   
 *  @since   release specific (what release of product did this appear in)
 */

package oracle.saas.logan.util;


/**
 * A class that holds the identifier and content of a message, which is sent
 * from one page to another. Client is encouraged to store strongly typed
 * data in the message body.
 */
public class InterPageMessage
{
    private String id;
    private Object body;
    // only used by MessageManager for purging algorithm
    long lastAccessedTime;
    
    /**
     * Sets the id of the message. This function should be called by
     * MessageManager which generates the unique ID.
     * @see MessageManager
     */
    void setID(String id)
    {
        this.id = id;
    }

    /**
     * Gets the ID of the message.
     */
    String getID()
    {
        return id;
    }

    /**
     * Gets the message body.
     */
    public Object getBody()
    {
        return body;
    }

    /**
     * Sets the message body.
     */
    public void setBody(Object body)
    {
        this.body = body;
    }
}
