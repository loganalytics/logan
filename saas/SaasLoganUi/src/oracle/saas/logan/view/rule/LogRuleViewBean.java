package oracle.saas.logan.view.rule;

import java.util.Map;

import java.util.logging.Level;

import java.util.logging.Logger;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import javax.faces.validator.ValidatorException;

import oracle.saas.logan.util.InterPageMessageBean;
import oracle.saas.logan.util.LoganLibUiUtil;
import oracle.saas.logan.util.UiUtil;
import oracle.saas.logan.view.LaunchContextModeBean;

public class LogRuleViewBean {
    private static final Logger s_log =
        Logger.getLogger(LogRuleViewBean.class.getName());
    
    private LaunchContextModeBean modeBean;
    private String launchMode;
    private Integer ruleId;
    private String createLikeRuleName;
    private String author;
    
    private static final String RULE_ID = "ruleId";
    private static final String CREATE_LIKE_RULE_NAME_CONST =
        "create_like_logan_rule_name";

    private static final String OutlineColorRed =
        "border-color:Red; border-style:solid; border-width:medium;";

    private String ruleNameOutlineColor = "";
    private String ruleLTypeOutlineColor = "";
    private String ruleTTypeOutlineColor = "";


    private LoganRuleBean ruleBean;
    
    public LogRuleViewBean() {
        Map ipmParams =
            InterPageMessageBean.getCurrentInstance().getParams();
        launchMode = LoganLibUiUtil.getModeForTrain();
        modeBean = new LaunchContextModeBean(launchMode);
        
        String rId = String.valueOf(ipmParams.get(RULE_ID)) ;
        
        if (rId != null) // create_like, Edit or Show Details mode
        {
            // create_like, Edit or Show Details mode
            
            ruleId = new Integer(Integer.parseInt(rId));
            try
            {
                ruleBean = LogRuleDAO.getLogRuleRow(ruleId);
            }
            catch (Exception ew)
            {
                if(s_log.isLoggable(Level.FINE))
                    s_log.logp(Level.FINE,LogRuleViewBean.class.getName(),"constructor",
                    "Error init:: LogParserDataBean :: Failed with Error: ",ew);
            }
            
            if (ruleBean.isIsSystem() &&
                !modeBean.isCreateLikeMode())
                modeBean.setModeToReadOnly();
        }
    }
    
    public void setRuleId(Number sourceId)
    {
        this.ruleBean.setRuleId(sourceId);
    }

    public Number getRuleId()
    {
        return this.ruleBean.getRuleId();
    }

    
    public void setIname(String iname)
    {
        this.ruleBean.setIname(iname);
    }

    public String getIname()
    {
        return this.ruleBean.getIname();
    }

    public void setDname(String name)
    {
        this.ruleBean.setDname(name);
        this.ruleBean.setIname(name);
    }

    public String getDname()
    {
        return this.ruleBean.getDname();
    }

    public void setAuthor(String author)
    {
        this.ruleBean.setAuthor(author);
    }

    public String getAuthor()
    {
        return this.ruleBean.getAuthor();
    }

//    public void setLogType(String logType)
//    {
//        this.ruleBean.setLogType(logType);
//    }
//
//    public String getLogType()
//    {
//        return this.ruleBean.getLogType();
//    }

//    public String getLogTypeDisplayName()
//    {
//        String ltDis = getLogType();
//        return getLoganRuleWizTrainBean().getLogTypeDisplayName(ltDis);
//    }

//    public void setTargetType(String targetType)
//    {
//        this.detailsBean.setTargetType(targetType);
//    }

//    public String getTargetType()
//    {
//        return this.detailsBean.getTargetType();
//    }
//
//    public String getTargetTypeDisplayName()
//    {
//        String tt = getTargetType();
//        return getLoganRuleWizTrainBean().getTargetTypeDisplayName(tt);
//    }
//
//    public void setSeverity(String severity)
//    {
//        this.ruleBean.setSeverity(new Number(new Long(severity)));
//    }
//
//    public String getSeverity()
//    {
//        return ""+this.ruleBean.getSeverity();
//    }
//
//    public String getSeverityDisplay()
//    {
//        LoganRuleWizTrainBean lrtb = getLoganRuleWizTrainBean();
//        return lrtb.getSeverityDisplay(detailsBean.getSeverity());
//    }

    public void setDescription(String description)
    {
        this.ruleBean.setDescription(description);
    }

    public String getDescription()
    {
        return this.ruleBean.getDescription();
    }

    public void setRationale(String rationale)
    {
        this.ruleBean.setRationale(rationale);
    }

    public String getRationale()
    {
        return this.ruleBean.getRationale();
    }
    
    public boolean isCreateMode()
    {
        return modeBean.isCreateMode();
    }

    public boolean isCreateLikeMode()
    {
        return modeBean.isCreateLikeMode();
    }

    public boolean isEditMode()
    {
        return modeBean.isEditMode();
    }

    public boolean isReadOnlyMode()
    {
        return modeBean.isReadOnlyMode();
    }

    public boolean isReadEditMode()
    {
        return modeBean.isReadEditMode();
    }
    
    public void validateParserName(FacesContext facesContext,
                                   UIComponent uIComponent, Object object)
    {
        String nwVal = (String) object;
        validateParserName(nwVal);
    }
    public void validateParserName(String name)
    {
        if (name == null || name.trim().length() == 0)
        {
            String errm = UiUtil.getUiString("LOG_PARSER_NAME_BLANK_ERR");
            FacesMessage msg = new FacesMessage(errm);
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(msg);

        }
    }
}
