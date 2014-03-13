package oracle.saas.logan.util;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.component.ContextCallback;
import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

import oracle.adf.view.rich.component.rich.layout.RichPanelStretchLayout;
import oracle.adf.view.rich.util.FacesMessageUtils;

import org.apache.myfaces.trinidad.context.RequestContext;
import org.apache.myfaces.trinidad.render.ExtendedRenderKitService;
import org.apache.myfaces.trinidad.util.Service;

public class AdfUtil {


    // Messaging Keys for parameters inside interpageMessage bean
    //
    public static final String MSG_TYPE = "MSG_TYPE";
    public static final String FROM_UIX_PAGE = "FROM_UIX_PAGE";
    public static final String USE_RES = "USE_RES";
    public static final String RSC_BUND_NAME = "RSC_BUND_NAME";
    public static final String SUM_MSG_KEY = "SUM_MSG_KEY";
    public static final String DET_MSG_KEY = "DET_MSG_KEY";
    public static final String SUM_MSG = "SUM_MSG";
    public static final String DET_MSG = "DET_MSG";
    public static final String CLEANUP = "cleanUp";
    public static final String REQ_INLINE_MSG = "REQ_INLINE_MSG";

    public static final String TEMPLATE_DEF_NO_FORM_ID = "emT";
    public static final String SUFFIX_INLINE_MSG_ID = "inlineMsgView:inlineMsg";
    private static final String 
        INLINE_MSG_PARENT_CONT_TEMP_DEF_NO_FORM = "inlineMsgParentLayout";
    public static final String TEMPLATE_DEF_NO_FORM_INLINE_MSG_ID = 
                            TEMPLATE_DEF_NO_FORM_ID + ":" + 
                            INLINE_MSG_PARENT_CONT_TEMP_DEF_NO_FORM;
    
    public AdfUtil() {
        super();
    }
    
    private static final Logger s_log = Logger.getLogger(AdfUtil.class.getName());
    
    ///////////////////
    // ADF SHORTCUTS //
    ///////////////////

    /**
     * Rerender the component on the current page whose id matches componentID.
     *
     * @param componentID
     */
    public static final void addPartialTarget(String componentID)
    {
        addPartialTarget(findComponent(componentID));
    }
    

    /**
     * Get the current instance of the request context.
     *
     * @return
     */
    public static final RequestContext requestContext()
    {
        return RequestContext.getCurrentInstance();
    }


    /**
     * Run the provided javascript on the current page.
     *
     * @param script
     */
    public static final void addScript(String script)
    {
        renderKitService().addScript(facesContext(), script);
    }


    /**
     * Get the current instance of the extended render kit service.
     *
     * @return
     */
    public static final ExtendedRenderKitService renderKitService()
    {
        return Service.getRenderKitService(facesContext(),
                                           ExtendedRenderKitService.class);
    }

    /**
     * Rerender the provided component on the current page.
     *
     * @param component
     */
    public static final void addPartialTarget(UIComponent component)
    {
        requestContext().addPartialTarget(component);
    }
    
    /////////////////////////
    // JSP & JSF SHORTCUTS //
    /////////////////////////

    /**
     * Find the compnent on the current page whose id matches componentID.
     *
     * @param componentID
     * @return
     */
    public static final UIComponent findComponent(String componentID)
    {
        UIComponent component = viewRoot().findComponent(componentID);
        
        if (component == null)
        {
            if(s_log.isLoggable(Level.FINEST))
                s_log.finest("Cannot find component: " + componentID + 
                        ".  Edit AdfUtil for stack details.");
        }

        return component;
    }

    /**
     * Get the current instance of the view root.
     *
     * @return
     */
    public static final UIViewRoot viewRoot()
    {
        return facesContext().getViewRoot();
    }

    /**
     * Get the current instance of the faces context.
     *
     * @return
     */
    public static final FacesContext facesContext()
    {
        return FacesContext.getCurrentInstance();
    }

    /////////////////////////////
    // INNER CLASSES and ENUMS //
    /////////////////////////////

    public static enum MESSAGE_TYPE
    {
        CONFIRMATION,
        INFORMATION,
        WARNING,
        ERROR,
        FATAL
    }
     

    /**
     * Show a messaging dialog message. This will use ADF messaging fwk.
     * @param componentId This is for component id for showing component
     *        specific message.For page level message this should be 
     *        passed null.
     * @param type Type of message whether it is 
     *        confirmation/information/warning/error/fatal
     * @param summaryMsg Summary message.
     * @param detailMsg Detail message.
     * @param requestInlineMsg register a request to show the message inline
     *        in the page.This request will be honoured only if there is no 
     *        component specific messages + messages of type 
     *        Fatal/Error/Warning in the message queue.
     *  @param messagesComponentId Component Id of af:messages component. 
     *         This id is dependent on the template in which messages is added.
     */
     @SuppressWarnings("deprecation") //ADAUtil is replaced by new class in new
                                      //src structure.
    public static final void showMessageDialog(String componentId,
                                               MESSAGE_TYPE type,
                                               String summaryMsg,
                                               String detailMsg,
                                               boolean requestInlineMsg,
                                               String messagesComponentId)
    {
        FacesMessage message = null;
        
        switch (type)
        {
            case CONFIRMATION:
                message =
                        FacesMessageUtils.getConfirmationMessage(summaryMsg,
                                                                 detailMsg);
                break;
            case INFORMATION:
                message =
                        new FacesMessage(FacesMessage.SEVERITY_INFO, summaryMsg,
                                         detailMsg);
                break;
            case WARNING:
                message =
                        new FacesMessage(FacesMessage.SEVERITY_WARN, summaryMsg,
                                         detailMsg);
                break;
            case ERROR:
                message =
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, summaryMsg,
                                         detailMsg);
                break;
            case FATAL:
                message =
                        new FacesMessage(FacesMessage.SEVERITY_FATAL, summaryMsg,
                                         detailMsg);
                break;
        }
        
        HttpServletRequest request = 
            (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
        if (UserAgentUtil.isMobileAppRequest(request)) {
            FacesContext.getCurrentInstance().addMessage(null, message);
            return;
        }

        if(!isInAccessibleMode() && requestInlineMsg && 
           willPPRTargetWork())
           
        {
            //Deliberately calling the flag on only when inline message is true.
            //This managed bean is common to all the messages.
            //Internal defaults of inline attribute is false
            InlineMessageBean inlineBean = (InlineMessageBean)
                                        resolveVariable("core_inline_msg");
            inlineBean.setRequestedInlineMsg(requestInlineMsg);
            inlineBean.setMessageType(type);
            inlineBean.setMessage(summaryMsg,detailMsg);
            if(messagesComponentId == null)
            {
                messagesComponentId = TEMPLATE_DEF_NO_FORM_INLINE_MSG_ID;
            }
        }
        else
        {
            FacesContext.getCurrentInstance().addMessage(componentId, message);
        }

        //Always render the message container.
        InlineMessageBean inlineBean = (InlineMessageBean)
                                        resolveVariable("core_inline_msg");

        if (messagesComponentId != null && !messagesComponentId.trim().equals(""))
        {
            UIComponent messagesComponent =
                findComponentUsingCallback(messagesComponentId);
            if (messagesComponent != null)
            {
                addPartialTarget(messagesComponent);
            }
            RichPanelStretchLayout panel = inlineBean.getSavedPanel();
            if(panel != null)
            {
                panel.setVisible(true);
            }
        }
    }

    private static class FindComponentCallback implements ContextCallback
    {
        UIComponent component = null;
    
        public void invokeContextCallback(FacesContext facesContext,
                                          UIComponent uIComponent)
        {
            component = uIComponent;
        }
    };
    
    /**
     * Find the compnent on the current page whose id matches componentID
     * using FindComponentCallback and invokeOnComponent api of UIComponent.
     * 
     * @param componentID
     * @return
     */
    private static final UIComponent findComponentUsingCallback(String componentID)
    {
        FindComponentCallback callback = new FindComponentCallback();
        viewRoot().invokeOnComponent(facesContext(), componentID, callback);

        UIComponent component = callback.component;

        if (component == null)
        {
            if(s_log.isLoggable(Level.FINEST))
                s_log.finest("Cannot find component: " + componentID + 
                        ".  Edit AdfUtil for stack details.");
            //m_log.trace("TRACE stack details: ",  new Exception());
        }

        return component;
    }

    /**
     * Resolve the provided variable using the current instance of the EL
     * resolver.
     *
     * @param varName
     * @return
     */
    public static final Object resolveVariable(String varName)
    {
        // Note that this implementation has broader semantics than the
        // contract defined in the javadoc comments: any unadorned
        // expression can be evaluated.
        return evaluateExpression("#{" + varName + "}", Object.class);
    }

    /**
     * Evaluates an expression.
     *
     * @param expr
     * @param expectedType
     * @return
     */
    public static final Object evaluateExpression(String expr,
                                                  Class expectedType)
    {
        return application().evaluateExpressionGet(facesContext(), expr,
                                                   expectedType);
    }

    /**
     * Get the current instance of the application.
     *
     * @return
     */
    public static final Application application()
    {
        return facesContext().getApplication();
    }

    public static boolean isInAccessibleMode() {
        RequestContext rc = RequestContext.getCurrentInstance();
        if (RequestContext.Accessibility.SCREEN_READER.equals(rc.getAccessibilityMode()))
            return true;
        return false;
    }
    
    public static String getAccessibilityMode(){
        RequestContext rc = RequestContext.getCurrentInstance();
        return rc.getAccessibilityMode().toString();
    }
    
    private static boolean willPPRTargetWork()
    {
        boolean pprTargetWork = true;
        FacesContext context = facesContext();        
        
        if(context != null)
        {  
            //Since it is full page refresh PPR target is irrelevant as whole
            //page is going to render.
            if(requestContext().isPostback())
            {
                boolean renderReponse = context.getRenderResponse();
                
                if(renderReponse)
                {
                    //If in render response apart from one usecase of custom
                    //PhaseListener it will not work for postBack request.
                    //So setting the value to false
                    pprTargetWork = false;
                    
                    //This is a bad hack and we are mainly supporting for backward
                    //compatibility. In MLR all ERROR/FATAL/WARNING were forcefully
                    //shown as faces message that works in render response phase.
                    //But in PS1 if integrators requested to show an ERROR
                    //message as inline we will show it as inline as this is allowed now.
                    //Problem is older plugin wrongly used inline message in render response
                    //phase and it will not showup as now we don't convert it to
                    //Faces message, hence the below hack to convert all real
                    //render reponse phase inline message requests to popup.
                    //However, this hack is not a good approach to achieve this. Have
                    //bug 14161725 to replace this hack by some better alternative.
                    //PreRenderComponentEvent in JSF 2 can be helpful here. This gets 
                    //triggered per component before rendering starts. But currently
                    //we are using JSF 1.2. In future, when we upgrade to JSF 2, we
                    //can replace this hack.
                    
                    StackTraceElement[] stackTraceElements =
                        Thread.currentThread().getStackTrace();
                    
                    if(stackTraceElements != null)
                    {
                        for(int i=0; i < stackTraceElements.length && i < 50; i++)
                        {
                            StackTraceElement caller = stackTraceElements[i];
                            if(caller != null)
                            {
                                String className = caller.getClassName();
                                String methodName = caller.getMethodName();
                                
                                if("javax.faces.component.UIViewRoot".equals(className)
                                    && "notifyBefore".equals(methodName))
                                {
                                    pprTargetWork = true;   
                                    break;
                                }
                            }
                        }                    
                    }
                }
            }            
        }
        return pprTargetWork;
    }

    
    ///////////////////////
    // MESSAGING METHODS //
    ///////////////////////

    /**
     * Show a messaging dialog message. This will use ADF messaging fwk.
     * @param componentId This is for component id for showing component 
     *        specific message. For page level message this should 
     *        be passed null.
     * @param type Type of message whether it is 
     *        confirmation/information/warning/error/fatal
     * @param resourceBundleName Resource bundle name from which messaging.
     * @param summaryMsgKey Key in the resource bundle for summary message.
     * @param detailMsgKey Key in the resource bundle for details message.
     */
    public static final void showMessageDialog(String componentId,
                                               MESSAGE_TYPE type,
                                               String resourceBundleName,
                                               String summaryMsgKey,
                                               String detailMsgKey)
    {
        showMessageDialog(componentId,
                          type,
                          resourceBundleName,
                          summaryMsgKey,
                          detailMsgKey,
                          false,
                          null);
    }

    /**
     * Show a messaging dialog message. This will use ADF messaging fwk.
     * @param type Type of message whether it is 
     *        confirmation/information/warning/error/fatal
     * @param resourceBundleName Resource bundle name from which messaging.
     * @param summaryMsgKey Key in the resource bundle for summary message.
     * @param detailMsgKey Key in the resource bundle for details message.
     * @param requestInlineMsg register a request to show the message 
     *        inline in the page.This request will be honoured only if there
     *        is no component specific messages + messages of type
     *        Fatal/Error/Warning in the message queue.
     */
    public static final void showMessageDialog(MESSAGE_TYPE type,
                                               String resourceBundleName,
                                               String summaryMsgKey,
                                               String detailMsgKey,
                                               boolean requestInlineMsg)
    {
        showMessageDialog(null,
                            type,
                            resourceBundleName,
                            summaryMsgKey,
                            detailMsgKey,
                            requestInlineMsg,
                            null);
    }
    
    /**
     * Get the current instance of the external context
     *
     * @return
     */
    public static final ExternalContext externalContext()
    {
        return facesContext().getExternalContext();
    }

    /**
     * Get the current instance of the servlet request.
     *
     * @return
     */
    public static final ServletRequest servletRequest()
    {
        return (ServletRequest) externalContext().getRequest();
    }

    public static final Locale getClientLocale()
    {
         return getClientLocale((HttpServletRequest)servletRequest());
    }
    
    /**
     * Gets the locale based on the request's header
     * fields <code>Accept-Language</code> and <code>Accept-Charset</code>.
     * If the locale cannot be determined from the request, returns the default 
     * locale of the JVM.
     *
     * @param request the http servlet request
     * @param useMapper If set to true, this method will use cabo mapper to map the
     *                  locale. This is needed for DLF. If set to false, the locale 
     *                  return by the method could be used for locale-sensitive 
     *                  processing, such as Date/time formatting/parsing and selection
     *                  of Resouce Bundle.
     * @return the locale
     */
    static private final Locale getClientLocale(HttpServletRequest request, 
                                                boolean useMapper)
    {
        Locale locale = null;

        
        if (locale == null) 
        {
            //TODO use new
//            locale = CaboHttpUtils.determineLocale(request);
            Locale.getDefault();
        }
        
        //The logic here is
        // 1. If useMapper is true, this method can only return cabo supported locales.
        // 2. If useMapper is false, we check if the desired locale is in the supported
        //     languages and country (for zh and pt locales only). If yes, we return
        //     the desired locale.
        // 3. If the desired locale is in the language or the country we don't support,
        //      return the default locale.
        if (locale != null)
        {
            String language = locale.getLanguage();
            String countryCode = locale.getCountry();
            //A special case for zh_HK and zh_SG
            if (language.equals(Locale.CHINESE.getLanguage()))
            {
                if (("HK".equals(countryCode)))
                {
                    locale = Locale.TAIWAN;
                }
                else if ("SG".equals(countryCode))
                {
                    locale = Locale.CHINA;
                }
            }
        }
        
        // set a default for the country in the locale, if necessary
        // from oracle.bali.share.LocaleUtils:
        // locale = getLocaleWithCountry(locale)
        if (locale == null)
        //BEGIN INFEASIBLE
        {
            //Here, the locale cannot be determined from the request, thus we
            // don't know if the locale is supported or not. Return JVM's locale
            // in this case.
            locale = Locale.getDefault();
        }
        //END INFEASIBLE

        return locale;
    }
    
    
    /**
     * Gets the locale for choosing NLS resource bundle, date/time number 
     *  formatting/parsing, collating etc. based on the request's header
     *  fields <code>Accept-Language</code> and <code>Accept-Charset</code>.
     * If the locale is not in the supported language or country (if language
     *  is zh or pt), {@link NLSUtil#DEFAULT_LOCALE NLSUtil.DEFAULT_LOCALE} is returned.
     * If the locale cannot be determined from the request, returns the default 
     *  locale of the JVM.
     *
     * @param request the http servlet request
     * @return the locale
     */
    static public final Locale getClientLocale(HttpServletRequest request)
    {
        return getClientLocale(request, false);
    }

    /**
     * Show a messaging dialog message. This will use ADF messaging fwk.
     * @param componentId This is for component id for showing component 
     *        specific message.For page level message this should be 
     *        passed null.
     * @param type Type of message whether it is 
     *        confirmation/information/warning/error/fatal
     * @param resourceBundleName Resource bundle name from which messaging.
     * @param summaryMsgKey Key in the resource bundle for summary message.
     * @param detailMsgKey Key in the resource bundle for details message.
     * @param requestInlineMsg register a request to show the message 
     *         inline in the page.This request will be honoured only if 
     *         there is no component specific messages + messages of type
     *         Fatal/Error/Warning in the message queue.
     *  @param messagesComponentId Component Id of af:messages component. 
     *         This id is dependent on the template in which messages is added.
     */
    public static final void showMessageDialog(String componentId,
                                               MESSAGE_TYPE type,
                                               String resourceBundleName,
                                               String summaryMsgKey,
                                               String detailMsgKey,
                                               boolean requestInlineMsg,
                                               String messagesComponentId)
    {
        Locale locale = getClientLocale();

        //If confirmMsg resource bundle is not given do nothing
        if (resourceBundleName == null ||
            resourceBundleName.trim().length() == 0)
        {
            return;
        }

        ResourceBundle resourceBundle =
            ResourceBundle.getBundle(resourceBundleName, locale);

        if (resourceBundle == null)
        {
            //Resource bundle not found.do nothing.
            return;
        }

        String summaryMsg = "";
        String detailMsg = "";
        if (summaryMsgKey != null)
        {
            summaryMsg = resourceBundle.getString(summaryMsgKey);
        }

        if (detailMsgKey != null)
        {
            detailMsg = resourceBundle.getString(detailMsgKey);
        }

        showMessageDialog(componentId, type, summaryMsg, detailMsg,
                          requestInlineMsg,messagesComponentId);
    }



    /**
     * Show a messaging dialog message. This will use ADF messaging fwk.
     * @param componentId This is for component id for showing component 
     *        specific message.
     *        For page level message this should be passed null.
     * @param type Type of message whether it is 
     *        confirmation/information/warning/error/fatal
     * @param summaryMsg Summary message.
     * @param detailMsg Detail message.
     */
    public static final void showMessageDialog(String componentId,
                                               MESSAGE_TYPE type,
                                               String summaryMsg,
                                               String detailMsg)
    {
        showMessageDialog(componentId,type,summaryMsg,detailMsg,false,null);
    }

    /**
     * Show a messaging dialog message. This will use ADF messaging fwk.
     * @param type Type of message whether it is 
     *        confirmation/information/warning/error/fatal
     * @param summaryMsg Summary message.
     * @param detailMsg Detail message.
     * @param requestInlineMsg register a request to show the message inline 
     *        in the page.This request will be honoured only if there is 
     *        no component specific messages + messages of type
     *        Fatal/Error/Warning in the message queue.
     */
    public static final void showMessageDialog(MESSAGE_TYPE type,
                                               String summaryMsg,
                                               String detailMsg,
                                               boolean requestInlineMsg)
    {
        showMessageDialog(null,
                            type,
                            summaryMsg,
                            detailMsg,
                            requestInlineMsg,
                            null);
    }

    /**
     * Show page level messages in messaging dialog. This will use ADF messaging fwk.
     * @param type Type of message whether it is 
     *        confirmation/information/warning/error/fatal
     * @param resourceBundleName Resource bundle name from which messaging.
     * @param summaryMsgKey Key in the resource bundle for summary message.
     * @param detailMsgKey Key in the resource bundle for details message.
     */
    public static final void showMessageDialog(MESSAGE_TYPE type,
                                               String resourceBundleName,
                                               String summaryMsgKey,
                                               String detailMsgKey)
    {
        showMessageDialog(null, type, resourceBundleName, summaryMsgKey,
                          detailMsgKey);
    }

    /**
     * Show page level messages in messaging dialog. This will use ADF messaging fwk.
     * @param type Type of message whether it is 
     *        confirmation/information/warning/error/fatal
     * @param summaryMsg Summary message.
     * @param detailMsg Details message.
     */
    public static final void showMessageDialog(MESSAGE_TYPE type,
                                               String summaryMsg,
                                               String detailMsg)
    {
        showMessageDialog(null, type, summaryMsg, detailMsg);
    }

    /**
     * Show page level messages in messaging dialog. 
     * This will use ADF messaging fwk.
     * @param type Type of message whether it is 
     *        confirmation/information/warning/error/fatal
     * @param resourceBundleName Resource bundle name from which messaging.
     * @param summaryMsgKey Key in the resource bundle for summary message.
     * @param detailMsgKey Key in the resource bundle for details message.
     */
    public static final void showMessageDialog(MESSAGE_TYPE type,
                                               String resourceBundleName,
                                               String summaryMsgKey,
                                               String detailMsgKey,
                                               boolean requestInlineMsg,
                                               String messagesComponentId)
    {
        showMessageDialog(null, type, resourceBundleName, summaryMsgKey,
                          detailMsgKey,requestInlineMsg,messagesComponentId);
    }

    /**
     * Show page level messages in messaging dialog. This will use ADF messaging fwk.
     * @param type Type of message whether it is 
     *        confirmation/information/warning/error/fatal
     * @param summaryMsg Summary message.
     * @param detailMsg Details message.
     */
    public static final void showMessageDialog(MESSAGE_TYPE type,
                                               String summaryMsg,
                                               String detailMsg,
                                               boolean requestInlineMsg,
                                               String messagesComponentId)
    {
        showMessageDialog(null, type, summaryMsg, detailMsg,
            requestInlineMsg,messagesComponentId);
    }


    /**
     * Passes the messaging to be shown in the navigated page.
     * Since facesMessage remains per request scope when the message is to be
     * shown in the next page the faces message is lost.
     * This api will push the message into interpageMessage bean
     * and EM fwk will pick up this message on next page processing
     * and show the message using ADF messaging fwk.
     * @param type Message type
     * @param resourceBundleName resourceBundle name
     * @param summaryMsgKey Key of summary message in resource bundle.
     * @param detailMsgKey Key of detail message in resource bundle.
     */
    public static final void passMessage(MESSAGE_TYPE type,
                                         String resourceBundleName,
                                         String summaryMsgKey,
                                         String detailMsgKey)
    {
        passMessage(type,resourceBundleName,summaryMsgKey,detailMsgKey,false);
    }

    /**
     * Passes the messaging to be shown in the navigated page.
     * Since facesMessage remains per request scope when the message is to be
     * shown in the next page the faces message is lost.
     * This api will push the message into interpageMessage bean
     * and EM fwk will pick up this message on next page processing
     * and show the message using ADF messaging fwk.
     * @param type Message type
     * @param resourceBundleName resourceBundle name
     * @param summaryMsgKey Key of summary message in resource bundle.
     * @param detailMsgKey Key of detail message in resource bundle.
     * @param requestInlineMsg register a request to show the message inline 
     *  in the page.
     *  This request will be honoured only if there is no component specific
     *  messages + messages of type Fatal/Error/Warning in the message queue.
     */
    public static final void passMessage(MESSAGE_TYPE type,
                                         String resourceBundleName,
                                         String summaryMsgKey,
                                         String detailMsgKey,
                                         boolean requestInlineMsg)
    {
        /*InterPageMessageBean ipmb =
            (InterPageMessageBean) resolveVariable(
                        InterPageMessageBean.MANAGED_BEAN_NAME);
        Map ipmbMap = ipmb.getParams();*/

        InterPageMessageDataBean messageData = (InterPageMessageDataBean) 
                    resolveVariable(
                        InterPageMessageDataBean.MESAGE_TEMPLATE_MANAGED_BEAN_NAME);

        if(messageData != null)
        {
            Map<String,Object> map = 
                messageData.getMessageData();
            if(map == null)
            {
                map = new HashMap<String,Object>();
                messageData.setMessageData(map);
            }
            map.put(MSG_TYPE, type);
            map.put(USE_RES, "true");
            map.put(RSC_BUND_NAME, resourceBundleName);
            map.put(SUM_MSG_KEY, summaryMsgKey);
            map.put(DET_MSG_KEY, detailMsgKey);
            map.put(CLEANUP, "false");
            map.put(REQ_INLINE_MSG,new Boolean(requestInlineMsg));

        }

    }


    /**
     * Passes the messaging to be shown in the navigated page.
     * Since facesMessage remains per request scope when the message is to be
     * shown in the next page the faces message is lost.
     * This api will push the message into interpageMessage bean
     * and EM fwk will pick up this message on next page processing
     * and show the message using ADF messaging fwk.
     * @param type Message type
     * @param summaryMsg Summary message
     * @param detailMsg Detail message
     */
    public static final void passMessage(MESSAGE_TYPE type,
                                         String summaryMsg,
                                         String detailMsg)
    {
        passMessage(type,summaryMsg,detailMsg,false);
    }

    /**
     * Passes the messaging to be shown in the navigated page.
     * Since facesMessage remains per request scope when the message is to be
     * shown in the next page the faces message is lost.
     * This api will push the message into interpageMessage bean
     * and EM fwk will pick up this message on next page processing
     * and show the message using ADF messaging fwk.
     * @param type Message type
     * @param summaryMsg Summary message
     * @param detailMsg Detail message
     * @param requestInlineMsg register a request to show the message inline 
     *        in the page.This request will be honoured only if there is no 
     *        component specific messages + messages of type 
     *        Fatal/Error/Warning in the message queue.
     */
    public static final void passMessage(MESSAGE_TYPE type,
                                         String summaryMsg,
                                         String detailMsg,
                                         boolean requestInlineMsg)
    {
        /*InterPageMessageBean ipmb =
            (InterPageMessageBean) resolveVariable(
                        InterPageMessageBean.MANAGED_BEAN_NAME);
        Map ipmbMap = ipmb.getParams();*/
        InterPageMessageDataBean messageData = (InterPageMessageDataBean) 
                    resolveVariable(
                        InterPageMessageDataBean.MESAGE_TEMPLATE_MANAGED_BEAN_NAME);

        if(messageData != null)
        {
            Map<String,Object> map = 
                messageData.getMessageData();
            if(map == null)
            {
                map = new HashMap<String,Object>();
                messageData.setMessageData(map);
            }
            map.put(USE_RES, "false");
            map.put(MSG_TYPE, type);
            map.put(SUM_MSG, summaryMsg);
            map.put(DET_MSG, detailMsg);
            map.put(CLEANUP, "false");
            map.put(REQ_INLINE_MSG,new Boolean(requestInlineMsg));

        }
    }

    //Message passing from UIX pages to be shown in ADF page//

    /**
     * To show message from UIX page in redirected ADF page. 
     * @param ipmbMap Map of InterpageMessage bean.
     * @param type Message type
     * @param summaryMsg Summary message
     * @param detailMsg Detail message
     * @param requestInlineMsg register a request to show the message inline 
     *        in the page.This request will be honoured only if there is no 
     *        component specific messages + messages of type 
     *        Fatal/Error/Warning in the message queue.
     */
     public static final void passMessageFromUIX(Map ipmbMap,
                                         MESSAGE_TYPE type,
                                         String summaryMsg,
                                         String detailMsg,
                                         boolean requestInlineMsg)
    {

        if(ipmbMap != null)
        {
            ipmbMap.put(MSG_TYPE,type);
            ipmbMap.put(FROM_UIX_PAGE,"true");
            ipmbMap.put(USE_RES, "false");
            ipmbMap.put(SUM_MSG, summaryMsg);
            ipmbMap.put(DET_MSG, detailMsg);
            ipmbMap.put(CLEANUP, "false");
            ipmbMap.put(REQ_INLINE_MSG,new Boolean(requestInlineMsg));

        }
    }

    /**
     * To show message from UIX page in redirected ADF page. 
     * @param ipmbMap Map of InterpageMessage bean.
     * @param type Message type
     * @param resourceBundleName resourceBundle name
     * @param summaryMsgKey Key of summary message in resource bundle.
     * @param detailMsgKey Key of detail message in resource bundle.
     * @param requestInlineMsg register a request to show the message inline 
     *  in the page.
     *  This request will be honoured only if there is no component specific
     *  messages + messages of type Fatal/Error/Warning in the message queue.
     */
    public static final void passMessageFromUIX(Map ipmbMap,
                                         MESSAGE_TYPE type,
                                         String resourceBundleName,
                                         String summaryMsgKey,
                                         String detailMsgKey,
                                         boolean requestInlineMsg)
    {
        if(ipmbMap != null)
        {
            ipmbMap.put(MSG_TYPE, type);
            ipmbMap.put(FROM_UIX_PAGE,"true");
            ipmbMap.put(USE_RES, "true");
            ipmbMap.put(RSC_BUND_NAME, resourceBundleName);
            ipmbMap.put(SUM_MSG_KEY, summaryMsgKey);
            ipmbMap.put(DET_MSG_KEY, detailMsgKey);
            ipmbMap.put(CLEANUP, "false");
            ipmbMap.put(REQ_INLINE_MSG,new Boolean(requestInlineMsg));

        }
    }
    
}
