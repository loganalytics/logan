/* $Header: emcore/source/oracle/sysman/core/view/message/InlineMessageBean.java /main/12 2014/01/02 12:15:12 sumanku Exp $ */


/* Copyright (c) 2007, 2013, Oracle and/or its affiliates. 
All rights reserved.*/

/*
   DESCRIPTION
    <short description of component this file declares/defines>

   PRIVATE CLASSES
    <list of private classes defined - with one-line descriptions>

   NOTES
    <other useful comments, qualifications, etc.>

   MODIFIED    (MM/DD/YY)
    sumanku     12/12/13 - Fix for consuming UIX messages
    pramathu    05/09/12 - Fix for bug 13960448
    sbhagwat    04/04/12 - Add all type of message as inline
    idoshi      11/04/11 - bug 10075134
    pramathu    07/26/11 - bug 12661998
    dtsao       04/19/11 - Do not use inline in screen reader mode.
    sbhagwat    11/07/08 - Inline Message ui review
    kannatar    08/27/08 - Refresh the parent component
    sbhagwat    11/13/07 - Change to use own message queue for inline message
    sbhagwat    10/10/07 - Add close button handler in inline message
    sbhagwat    10/07/07 - Check passed message from inline attribute binding.
    sbhagwat    09/06/07 - Creation
 */

/**
 *  @version $Header: emcore/source/oracle/sysman/core/view/message/InlineMessageBean.java /main/12 2014/01/02 12:15:12 sumanku Exp $
 *  @author  sbhagwat
 *  @since   release specific (what release of product did this appear in)
 */
package oracle.saas.logan.util;

import java.util.Map;

import javax.faces.context.FacesContext;

import javax.servlet.http.HttpServletRequest;

import oracle.adf.view.rich.component.rich.layout.RichPanelStretchLayout;

import org.apache.myfaces.trinidad.util.ComponentReference;

/*
import oracle.sysman.core.model.message.InterPageMessageDataBean;
import oracle.sysman.emSDK.page.msg.InterPageMessage;
import oracle.sysman.emSDK.page.msg.MessageManager;
import oracle.sysman.emSDK.pagemodel.InterPageMessageBean;
import javax.faces.context.FacesContext;
*/



/**
 * InlineMessageBean checks whether inline message support is required based on
 * the messages in the queue plus developer request to show the message inline.
 * The bean scope is per request.
 */
public class InlineMessageBean
{

    public static final int PREF_INLINE_MESSAGES_HEIGHT = 76;

    /**
     * Stores request to set inline message or not.
     */
    private boolean m_requestedInlineMsg = false;

    /**
     * Message type
     */
    private String messageType;

    /**
     * Summary message
     */
    private String sumMessage;

    /**
     * Detail Message
     */
    private String detMessage;
    
    /**
      * Component reference for the panel containing the inline message
      */
    private ComponentReference<RichPanelStretchLayout> savedPanelReference;

    /**
     * Checks whether inline message is to be shown. 
     * This method is bound to inline message property of af:messages
     * and called once so we can pop the message from previous message data 
     * cache and add it to Faces message context.
     * Inline message is shown only if the developer desires to show plus there are 
     * no component specific or message type other than information/confirmation.
     * @return boolean true to show inline false to show in popup
     */
    public boolean isInline() 
    {
        //false indicate that don't peek the message but
        //pop the data from previous page message data
        //cache.
        checkInline(false);

        //Due to bug 6605226 we are not using af:messages and FacesMessage 
        //queue for showing message inline. For inline message we have a 
        //separate queue.We are returning inline as statically false till 
        //we get a better solution for bug 6605226 from ADF. 
        //Currently this method has significance as its pops the data
        //from passed message queue. This aspect can be relooked at later.

        return false;
    }

    /**
     * Returns message type.
     */
    public String getMessageType()
    {
        return messageType;
    }

    /**
     * Sets the message type
     */
    public void setMessageType(AdfUtil.MESSAGE_TYPE type)
    {
        switch (type)
        {
            case CONFIRMATION:
                messageType = "confirmation";
                break;
            case INFORMATION:
                messageType = "info";
                break;
            case FATAL:
                messageType = "fatal";
                break;
            case ERROR:
                messageType = "error";
                break;
            case WARNING:
                messageType = "warning";
                break;
        }
    }

    /**
     * Gets a message
     */
    public String getMessage()
    {
        if(detMessage == null || detMessage.trim().length() == 0)
        {
            return sumMessage;
        }
        else
        {
            return detMessage;
        }
    }

    /**
     * Sets a message
     */
    public void setMessage(String sumMessage,String detMessage)
    {
        this.sumMessage = sumMessage;
        this.detMessage = detMessage;
    }
    
    /**
     * Checks whether message is to be shown inline or not.
     * It checks the message data from the Faces Context 
     * message queue as well as from previous page message
     * data cache
     * @param peek Whether to peek the data from previous data cache or 
     *        pop the data from previous data cache 
     */
    @SuppressWarnings("deprecation") //ADAUtil is replaced by new class in new
                                   //src structure.
    private boolean checkInline(boolean peek)
    {
        boolean showInline = false;
        boolean isPassedMessageInline = true;
        
        //Check the passed message cache if there are messages
        //and candidate to be shown as inline.
        //This needs to be called independent of request inline or not.
        //If peek is false then this would pop the data from passed message to
        //facescontext message queue        
        if(isPassedMessagePresent())
        {
            isPassedMessageInline = checkPassedMsg(peek);
            //Set the showInline incase there are no faces message
            //in the facescontext message queue
            showInline = isPassedMessageInline;
        }

        //Now check first if inline is requested and message from FacesContext
        //message queue
        if(isRequestedInlineMsg() && isPassedMessageInline) 
        {
            showInline = true;
            //Check component specific message

            //No need to check from FacesMessage queue as due to bug 6605226
            //we are not using af:messages and its inline attribute binding.
            /*Iterator clientIDs = 
                FacesContext.getCurrentInstance().getClientIdsWithMessages();

            while(clientIDs != null && clientIDs.hasNext())
            {
                Object clientId = clientIDs.next();
                if(clientId != null)
                {                    
                    showInline = false;
                    break;
                }
            }
            
            Iterator <FacesMessage> iterator = 
                FacesContext.getCurrentInstance().getMessages();
            
            //Check messages in Faces context message queue
            if(showInline && iterator != null)
            {               
                while(iterator.hasNext())
                {
                    FacesMessage message = iterator.next();
                    if(message != null)
                    {
                        FacesMessage.Severity sev = message.getSeverity();
                        if(sev.equals(FacesMessage.SEVERITY_ERROR) ||
                            sev.equals(FacesMessage.SEVERITY_FATAL) ||
                            sev.equals(FacesMessage.SEVERITY_WARN))
                        {
                            showInline = false;
                            break;
                        }
                    }
                }
            }*/
        }
        
        if (AdfUtil.isInAccessibleMode())
            return false;
        
        return showInline;
        
    }
    
    /**
     * Returns the panel containing the inline message
     */ 
    public RichPanelStretchLayout getSavedPanel()
    {
        return savedPanelReference == null ?
                   null : savedPanelReference.getComponent();
    }

    /**
     * Stores the panel containing the inline message
     * @param savedPanel Panel containing the inline message
     */
    public void setSavedPanel(RichPanelStretchLayout savedPanel)
    {
        savedPanelReference =
            ComponentReference.newUIComponentReference(savedPanel);
    }
    
    /**
     * Returns whether the request for inline message is set.
     */
    public boolean isRequestedInlineMsg() 
    {
        return m_requestedInlineMsg;
    }

    /**
     * Sets inline message request support.
     * @param requestInlineMsg Whether to set inline message or not.
     */
    public void setRequestedInlineMsg(boolean requestInlineMsg) 
    {
        m_requestedInlineMsg = requestInlineMsg;
    }
    
    
    /**
     * Gets the preferred Inline message height. If there is no inline message height is 0.
     * else the preferred inline message height is 73.
     */
    public int getPrefInlineMsgHeight()
    {
        if(checkInline(true))
        {
            return PREF_INLINE_MESSAGES_HEIGHT;
        }
        else
        {
            return 0;
        }
    }


    /**
     * Returns the one liner message.
     * Note: This is just a workaround as blank string given in the UI displays the 
     * generic message.
     * Infact once we have ER 6520561 in our EM label this method won't be required.
     */
    public String getInlineGenericMessage()
    {
        return "";
    }

    /**
     * Checks if there is any message passed by previous page.
     * @param peek if passed true it will just peek the message from preivous
     *        data cache but will not pop it, if passed as false then it will
     *        pop the data from previous data as well as add the message data
     *        in FacesContext message queue.
     * @return true if the message is to be shown is requested to be inline
     */    
    private boolean checkPassedMsg(boolean peek)
    {
        //Check if there is any messages from the previous page if yes
        //then push those in FacesMessage.
        boolean inlineMsgCandidate = true;
        boolean toShowInline = false;
        
        /*
           SDK IPM Bean is used only for message passed using UIX pages.
         */        
        InterPageMessageBean ipmb = InterPageMessageBean.getCurrentInstance();
        Map ipmbMap = null;
        boolean messageFromUixPage = false;
        if (ipmb != null)
        {
            Map newPageMap = ipmb.getParams();

            //TODO: This code need to relooked at as now oldIPMBody 
            //is never copied into ADF's InterPageMessageBean
            if(newPageMap != null && newPageMap.containsKey("oldIPMBody"))
            {
                Object ipmObj = newPageMap.get("oldIPMBody");
                if(ipmObj instanceof Map)
                {
                    ipmbMap = (Map)ipmObj;
                    messageFromUixPage = true;
                }
            }
        }
        /*For the the scenarios where oldIPMBody in not being used, check for 
         * the presenece of InterPageMessageBean sent by UIX pages in the request url
         */
        if(!messageFromUixPage)
        {
        
            /* Changes  made to consume UIX messages. 
             * We read InterPageMessage from request and fetch the message. 
             * This is required since InterPageMessageBean cant be set 
             * from UIX.*/
            FacesContext facesContext = FacesContext.getCurrentInstance();
            HttpServletRequest request =
                (HttpServletRequest) facesContext.getExternalContext().getRequest();
            InterPageMessage ipMsg = null;
            if(peek)
            {
                ipMsg = MessageManager.peekMessage(request);
            }
            else
            {
                ipMsg = MessageManager.consumeMessage(request);
            }
            
            if(ipMsg != null){
                Object messageObj = ipMsg.getBody();
                if(messageObj instanceof Map) 
                {
                    ipmbMap = (Map)messageObj;
                    messageFromUixPage = true;
                }            
                
            }
        }
        
        
        if(!messageFromUixPage)
        {
            /*
              SDK IPM Bean is removed for the reason the data cached in it is 
              observed to be very inconsistent especially if the page has regions.
              So MessageData bean is used as alternative for storing the passed data. 
              This is temporary workaround till SDK IPM bean issue is resolved
            */
            InterPageMessageDataBean messageData = (InterPageMessageDataBean) 
                    AdfUtil.resolveVariable(
                        InterPageMessageDataBean.MESAGE_TEMPLATE_MANAGED_BEAN_NAME);
            if(messageData != null)
            {
                ipmbMap = messageData.getMessageData();
            }
        }

        
            
        if(ipmbMap != null && ipmbMap.containsKey(AdfUtil.MSG_TYPE)) 
        {

            //Get all the messaging information
            AdfUtil.MESSAGE_TYPE type = (AdfUtil.MESSAGE_TYPE)
                                ipmbMap.get(AdfUtil.MSG_TYPE);
            
            String resourceBundle = (String)
                                ipmbMap.get(AdfUtil.RSC_BUND_NAME);

            Boolean isInline = (Boolean)
                                ipmbMap.get(AdfUtil.REQ_INLINE_MSG);

            boolean useResourceBundle = false;
            String strResourceBundle = (String)ipmbMap.get(AdfUtil.USE_RES);
            if(strResourceBundle != null && strResourceBundle.equals("true"))
            {
                useResourceBundle = true;
            }

            if(!useResourceBundle)
            {
                String sumMsg = (String)
                                ipmbMap.get(AdfUtil.SUM_MSG);
                String detMsg = (String)
                                ipmbMap.get(AdfUtil.DET_MSG); 
                
                boolean showInline = false;
                if(isInline != null)
                {
                    showInline = isInline.booleanValue();
                    if(inlineMsgCandidate && showInline)
                    {
                        toShowInline = true;
                    }
                }

                if(!peek)
                {
                    AdfUtil.showMessageDialog(null,
                                          type,
                                          sumMsg,
                                          detMsg,
                                          showInline,
                                          null);
                    
                    //Remove the keys
                    ipmbMap.remove(AdfUtil.MSG_TYPE);
                    ipmbMap.remove(AdfUtil.FROM_UIX_PAGE);
                    ipmbMap.remove(AdfUtil.USE_RES);
                    ipmbMap.remove(AdfUtil.RSC_BUND_NAME);
                    ipmbMap.remove(AdfUtil.SUM_MSG);
                    ipmbMap.remove(AdfUtil.DET_MSG);
                    ipmbMap.remove(AdfUtil.REQ_INLINE_MSG);
                }
            }
            else
            {
                String sumMsgKey = (String)
                                ipmbMap.get(AdfUtil.SUM_MSG_KEY);
                String detMsgKey = (String)
                                ipmbMap.get(AdfUtil.DET_MSG_KEY);

                boolean showInline = false;
                if(isInline != null)
                {
                    showInline = isInline.booleanValue();
                    if(inlineMsgCandidate && showInline)
                    {
                        toShowInline = true;
                    }
                }
                
                if(!peek)
                {
                    AdfUtil.showMessageDialog(null,
                                          type,
                                          resourceBundle,
                                          sumMsgKey,
                                          detMsgKey,
                                          showInline,
                                          null);
                                          
                    ipmbMap.remove(AdfUtil.MSG_TYPE);
                    ipmbMap.remove(AdfUtil.FROM_UIX_PAGE);
                    ipmbMap.remove(AdfUtil.USE_RES);
                    ipmbMap.remove(AdfUtil.SUM_MSG);
                    ipmbMap.remove(AdfUtil.DET_MSG);
                    ipmbMap.remove(AdfUtil.REQ_INLINE_MSG);
                } 
             }
        }
        return toShowInline;
    }
    
    private boolean isPassedMessagePresent()
    {
        //Check message passed from UIX pages
        InterPageMessageBean ipmb = InterPageMessageBean.getCurrentInstance();
        Map ipmbMap = null;
        boolean messageFromUixPage = false;
        if (ipmb != null)
        {
            Map newPageMap = ipmb.getParams();

            if(newPageMap != null && newPageMap.containsKey("oldIPMBody"))
            {
                Object ipmObj = newPageMap.get("oldIPMBody");
                if(ipmObj instanceof Map)
                {
                    ipmbMap = (Map)ipmObj;
                    messageFromUixPage = true;
                }
            }
        }
        /*For the the scenarios where oldIPMBody in not being used,check for 
         * the presenece of InterPageMessageBean sent by UIX pages in the request url
         */
        if(!messageFromUixPage)
        {
        
            /* Changes  made to consume UIX messages. 
             * We read InterPageMessage from request and fetch the message. 
             * This is required since InterPageMessageBean cant be set 
             * from UIX.*/
            FacesContext facesContext = FacesContext.getCurrentInstance();
            HttpServletRequest request =
                (HttpServletRequest) facesContext.getExternalContext().getRequest();
            InterPageMessage ipMsg = MessageManager.peekMessage(request);
            if(ipMsg != null){
                Object messageObj = ipMsg.getBody();
                if(messageObj instanceof Map) 
                {
                    ipmbMap = (Map)messageObj;
                    messageFromUixPage = true;
                }            
                
            }
        }
        
        if(!messageFromUixPage)
        {
            InterPageMessageDataBean messageData = (InterPageMessageDataBean) 
                    AdfUtil.resolveVariable(
                        InterPageMessageDataBean.MESAGE_TEMPLATE_MANAGED_BEAN_NAME);


            if(messageData != null)
            {
                ipmbMap = messageData.getMessageData();        
            }
        }
            
        if(ipmbMap != null && ipmbMap.containsKey(AdfUtil.MSG_TYPE)) 
        {
            return true;
        }      
        
        
        return false;
    }
}
