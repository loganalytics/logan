package oracle.saas.logan.util;


import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;


/**
 * A class manages inter page messages. A MessageManager object itself is
 * stored in http session. Therefore it can be accessed whenever http request
 * or session is accessible.
 * <p>
 * Client should follow the steps of
 * <ll>
 *   <li>instantiate message object</li>
 *   <li>set needed properties to the message object</li>
 *   <li>construct URL to receiver page</li>
 *   <li>call appendToURL() with the message object and the URL</li>
 *   <li>redirect to the returned URL which has message id encoded</li>
 * </ll>
 */
public class MessageManager
{
    private HashMap messages;
    private int idSequence;
    
    public static final String ID_PARAM_NAME = "ipMsgID";
    private static final Logger s_log =
        Logger.getLogger(MessageManager.class.getName());
    public MessageManager()
    {
        messages = new HashMap();
        idSequence = new Random(System.currentTimeMillis()).nextInt();
    }
    
    /**
     * This function generates a unique identifier for the message using
     * an internal random number generator. It sets the ID to the message
     * and stores the message using the ID as key. Then it appends the ID
     * to URL. The receiving page will use the message ID from URL to
     * identify and retrieve the message object.
     * @param url the original url to which the message ID is to be appended
     * @param message the inter page message object
     * @return the url with the message ID appended
     */
    public String appendToURL(String url, InterPageMessage message)
    {
        return appendToURL(url, message, false);
    }
    
    /**
     * This function generates a unique identifier for the message using
     * an internal random number generator. It sets the ID to the message
     * and stores the message using the ID as key. Then it appends the ID
     * to URL. The receiving page will use the message ID from URL to
     * identify and retrieve the message object.
     * @param url the original url to which the message ID is to be appended
     * @param message the inter page message object
     * @param removeExistingMsg whether the existing message IDs (if present)
     * should be removed from the url
     * @return the url with the message ID appended
     */
    public String appendToURL(String url, 
                              InterPageMessage message, 
                              boolean removeExistingMsg)
    {
        if (message == null)
            return url;
            
        if (url == null)
            url = "";
            
        // purge oldest messages
        if (messages.size() >= 10)
        {
            Collection values = messages.values();
            Iterator iter = values.iterator();
            InterPageMessage oldestMsg = null;
            while (iter.hasNext())
            {
                InterPageMessage oldMsg = (InterPageMessage) iter.next();
                if (oldestMsg == null ||
                    oldestMsg.lastAccessedTime > oldMsg.lastAccessedTime)
                {
                    oldestMsg = oldMsg;
                }
            }
            values.remove(oldestMsg);
        }

        // setup new message.
        String newID = generateID();
        message.setID(newID);
        messages.put(newID, message);
        message.lastAccessedTime = System.currentTimeMillis();
    
        if (removeExistingMsg) 
        {
            // remove any existing InterPageMessage parameters from the url
            url = removeExistingIPMParams(url);
        }
 
        int tmpInt = url.indexOf("?");
        if (tmpInt >= 0)
        {
            if (tmpInt == (url.length()-1))
                url += ID_PARAM_NAME + "=" + newID;
            else
                url += "&" + ID_PARAM_NAME + "=" + newID;
        }
        else
        {
            url += "?" + ID_PARAM_NAME + "=" + newID;
        }
        
        if(s_log.isLoggable(Level.FINEST))
            s_log.finest("MessageManager.appendToURL. New message added. ID = "
            + newID);

        return url;
    }

    /**
     * Gets the message object and leaves it in manager. A helper function is
     * also available to peek a message from a request.
     * @returns null if message does not exist.
     * @see peekMessage(HttpServletReuqest)
     */
    public InterPageMessage peekMessage(String id)
    {
        InterPageMessage ipm = (InterPageMessage) messages.get(id);
        if (ipm != null)
        {
            ipm.lastAccessedTime = System.currentTimeMillis();
        }
        return ipm;
    }

    /**
     * Gets the message object and removes it from manager. A helper function
     * is also available to consume a message from a request.
     * @returns null if message does not exist.
     * @see consumeMessage(HttpServletReuqest)
     */
    public InterPageMessage consumeMessage(String id)
    {
        if (id == null)
            return null;
            
        InterPageMessage msg = (InterPageMessage) messages.remove(id);

        if(s_log.isLoggable(Level.FINEST))
        {
            if (msg != null)
                s_log.finest("MessageManager.consumeMessage. "
                    + "Message removed. ID = " + id);
            else
                s_log.finest("MessageManager.consumeMessage. "
                    + "Message not exist. ID = " + id);
        }
        
        return msg;
    }

    private String generateID()
    {
        return Integer.toHexString(idSequence++);
    }
   
    /**
     * Removes all the occurences of parameter name/value pairs of the form
     * ipMsgID=xyz from the url and returns the resulting url.
     * @param url the original url from which the name/value pairs are to be 
     * removed.
     * @return the url after removing the name/value pairs.
     */
    private String removeExistingIPMParams(String url)
    {
        int index = url.indexOf(ID_PARAM_NAME);
        while (index != -1)
        {
            int andIndex = url.indexOf("&", index);
            if (andIndex == -1) // ipMsgID=xyz is the last request parameter
            {
                // The character immediately preceding ipMsgID is not included
                // because that is either a ? or a & and this is the last 
                // parameter that is being removed, so that character is not
                // required.
                url = url.substring(0, index-1);
            }
            else // ipMsgID=xyz is not the last request parameter
            {
                url = // Include the character immediately preceding ipMsgID=xyz
                      url.substring(0, index) +
                      // Do not include the & immediately following ipMsgID=xyz
                      // since the character immediately preceding ipMsgID=xyz
                      // (which is a '?' or a '&') is included.
                      url.substring(andIndex+1, url.length()-1) +
                      url.charAt(url.length()-1);
            }
            index = url.indexOf(ID_PARAM_NAME);
        }
        return url;
    }

 
    //////////////////////////////////////////////////////////////////////////
    // Helper functions

    /**
     * Helper function. Gets the message manager in the session. Creates
     * a message manager if none exists.
     */
    public static MessageManager getInstance(HttpSession session)
    {
        MessageManager mm = (MessageManager) session.getAttribute(
            MessageManager.class.getName());
        if (mm == null)
        {
            // just to be really sure that only one instance is created
            // per session. Synchronize when in null case avoid penalty
            // for frequent non null case.
            synchronized(MessageManager.class)
            {
                // this may look strange. but it is necessary
                mm = (MessageManager) session.getAttribute(
                    MessageManager.class.getName());
                    
                if (mm == null)
                {
                    mm = new MessageManager();
                    session.setAttribute(MessageManager.class.getName(), mm);
                    
                    if(s_log.isLoggable(Level.FINEST))
                    {
                        s_log.finest("Created MessageManager. start sequence = "
                            + mm.idSequence);
                    }
                }
            }
        }
        
        return mm;
    }

    /**
     * Helper function. Gets the session and creates it if not already created.
     * Creates the message manager if none exists in session.
     */
    public static MessageManager getInstance(HttpServletRequest request)
    {
        return getInstance(request.getSession(true));
    }

    /**
     * Helper method. Gets the id from request URL, gets the manager in session
     * and call manager.peekMessage().
     */
    public static InterPageMessage peekMessage(HttpServletRequest request)
    {
        InterPageMessage ipm = null;
        String msgID = request.getParameter(ID_PARAM_NAME);
        if (msgID != null)
            ipm = getInstance(request).peekMessage(msgID);
            
        return ipm;
    }

    /**
     * Helper method. Gets the id from request URL, gets the manager in session
     * and call manager.consumeMessage().
     */
    public static InterPageMessage consumeMessage(HttpServletRequest request)
    {
        InterPageMessage ipm = null;
        String msgID = request.getParameter(ID_PARAM_NAME);
        if (msgID != null)
            ipm = getInstance(request).consumeMessage(msgID);
            
        return ipm;
    }
    
    /**
     * Performs the cleanup by invoking the sessionDestroyed method of the 
     * messages (currently stored in the MessageManager) that implement the 
     * HttpSessionListener interface. 
     * @param se the HttpSessionEvent object 
     * This method is invoked by oracle.sysman.eml.app.SessionListener when the
     * session is being destroyed/invalidated and should not be directly 
     * invoked by the client. If the session is being destroyed, it gives a 
     * chance to each of the messages to perform a cleanup (for eg. release 
     * any resources that they might be holding). 
     */
    public static void cleanup(HttpSessionEvent se) 
    {
        HttpSession session = se.getSession();
        MessageManager mm = (MessageManager) session.getAttribute(
            MessageManager.class.getName());  
        if (mm == null) 
        {
            // There is nothing to cleanup
            return;
        }
        
        // Iterate over all the messages currently stored in the MessageManager
        // and invoke the sessionDestroyed method of each message that 
        // implements the HttpSessionListener interface.
        Iterator iter = (mm.messages).values().iterator();
        for ( ; iter != null && iter.hasNext(); ) 
        {
            InterPageMessage msg = (InterPageMessage) iter.next();
            if (msg != null) 
            {
                Object body = msg.getBody();
                if (body instanceof HttpSessionListener) 
                {
                    HttpSessionListener lsnr = (HttpSessionListener) body;
                    lsnr.sessionDestroyed(se);
                    
                }
            }    
        }
    }
}
