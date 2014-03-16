package oracle.saas.logan.view.rule;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;


import oracle.jbo.Key;
import oracle.jbo.Row;
import oracle.jbo.domain.Raw;
import oracle.jbo.domain.Number;

import oracle.jbo.server.ViewObjectImpl;

import oracle.saas.logan.util.LoganLibUiUtil;
import oracle.saas.logan.util.UiUtil;
import oracle.saas.logan.view.BaseDataPersistenceHandler;
import oracle.saas.logan.view.KeyValuePairObj;

public class LoganRuleTargetAssoc implements Cloneable{
    private static final Logger s_log =
        Logger.getLogger(LoganRuleTargetAssoc.class.getName());

    private static final String ME_NAME_PREFIX = "LoganRuleMetric_";

    private static final String AssocCriticalEditVersion =
        "AssocCriticalEditVersion";
    private static final String AssocEditVersion = "AssocEditVersion";
    private static final String AssocIsCustomized = "AssocIsCustomized";
    private static final String AssocIsEnabled = "AssocIsEnabled";
    private static final String AssocLastAttemptDate =
        "AssocLastAttemptDate";
    private static final String AssocRuleId = "AssocRuleId";
    private static final String AssocStatus = "AssocStatus";
    private static final String TxfStatus = "TxfStatus";
    private static final String AssocTargetGuid = "AssocTargetGuid";
    private static final String TargetName = "TargetName";
    private static final String EmdUrl = "EmdUrl";
    private static final String MecollMessageId = "MecollMessageId";
    private static final String MecollStatus = "MecollStatus";
    private static final String TxfDetails = "Txfdetails";  
    private static final String TargetType = "TargetType";
    
    private Number ruleId;
    private String targetGuid;
    private String targetName;
    private String targetType;
    private String emdURL;
    private Number criticalEditVersion;
    private Number editVersion;
    private boolean isCustomized;
    private boolean isEnabled;
    private Date lastAttemptDate;
    private Number status;
    private Number mestatus;
    private String memsgId;
    private String txfdetails;

    private String[] parentRefKeyNames =
    { AssocRuleId };

    public static final String ParentFK_RuleId = "RuleId";

    public LoganRuleTargetAssoc(Number ruleId, String targetGuid,
                                String targetName, String targetType,
                                String emdURL,
                                Number criticalEditVersion,
                                Number editVersion, boolean isCustomized,
                                boolean isEnabled, Date lastAttemptDate,
                                Number status, 
                                Number mestatus, String memsgId, String txfdetails)
    {
        super();
        this.ruleId = ruleId;
        this.targetGuid = targetGuid;
        this.targetName = targetName;
        this.targetType = targetType;
        this.emdURL = emdURL;
        this.criticalEditVersion = criticalEditVersion;
        this.editVersion = editVersion;
        this.isCustomized = isCustomized;
        this.isEnabled = isEnabled;
        this.lastAttemptDate = lastAttemptDate;
        this.status = status;
        this.mestatus = mestatus;
        this.memsgId = memsgId;
        this.txfdetails = txfdetails;
    }

    public LoganRuleTargetAssoc(Row dRow)
    {
        super();
        if (dRow != null)
        {
            ruleId = (Number) dRow.getAttribute(AssocRuleId);
            targetGuid =
                    ((Raw) dRow.getAttribute(AssocTargetGuid)).toString();
            targetName = (String) dRow.getAttribute(TargetName);
            targetType = (String) dRow.getAttribute(TargetType);
            emdURL = (String) dRow.getAttribute(EmdUrl);
            criticalEditVersion =
                    (Number) dRow.getAttribute(AssocCriticalEditVersion);
            editVersion = (Number) dRow.getAttribute(AssocEditVersion);
            Number isCust = (Number) dRow.getAttribute(AssocIsCustomized);
            if (isCust != null && isCust.intValue() == 1)
            {
                isCustomized = true;
            }
            Number isEna = (Number) dRow.getAttribute(AssocIsEnabled);
            if (isEna != null && isEna.intValue() == 1)
            {
                isEnabled = true;
            }
            lastAttemptDate =
                    (Date) dRow.getAttribute(AssocLastAttemptDate);
            status = (Number) dRow.getAttribute(TxfStatus);
            mestatus = (Number) dRow.getAttribute(MecollStatus);
            if (mestatus == null) mestatus = new Number(-1);
            memsgId = (String) dRow.getAttribute(MecollMessageId);
            txfdetails = (String) dRow.getAttribute(TxfDetails);
        }
    }

    public Object clone()
    {
        return new LoganRuleTargetAssoc(ruleId, targetGuid, targetName,
                                        targetType, emdURL,
                                        criticalEditVersion, editVersion,
                                        isCustomized, isEnabled,
                                        lastAttemptDate, status,
                                        mestatus, memsgId , txfdetails);

    }


    public boolean isObjInstanceOfSameClass(Object o)
    {
        return (o != null && o instanceof LoganRuleTargetAssoc);
    }

    /**
     * All properties of the object that are exposed on the Ui and
     * can be updated by the user should be used to construct this
     * representative string here.
     * This will be used to check if the user has edited any of the properties
     * @return object represeting the updatable properties as String
     */
    public String getEditablePropertiesAsString()
    {
        StringBuilder sb = new StringBuilder(); 
        sb.append(this.ruleId).append("_");
        sb.append(this.targetGuid).append("_");
        return sb.toString();
    }

    /**
     * @param o
     * @return
     */
    public int compareTo(LoganRuleTargetAssoc o)
    {
        if (this.targetName == null)
            return (o.targetName == null? 0: -1);
        if (o.targetName == null)
            return 1;
        return this.targetName.trim().compareTo(o.targetName.trim());
    }

    public String[] getParentReferenceKeyNames()
    {
        return parentRefKeyNames;
    }

    public void setParentReferenceKeys(List<KeyValuePairObj> rks)
    {
        if (rks == null || rks.size() <= 0)
            return;

        for (KeyValuePairObj kvpo: rks)
        {
            if (ParentFK_RuleId.equals(kvpo.getKey()))
            {
                this.setRuleId((Number) kvpo.getValue());
            }
        }
    }

    public String getPrimaryKeyAsString()
    {
        StringBuilder sb = new StringBuilder(); 
        sb.append(this.ruleId).append("_");
        sb.append(this.targetGuid).append("_");
        return sb.toString();
    }

    public void handleInsertOperation(){
    }

    public void handleUpdateOperation()
    {
            s_log.finest("RULE TARGET has only PKs exposed on UI so any change there is DELETE INSERT - no updates");
    }

    public void handleDeleteOperation()
    {
       
    }

    // flag the ME to stop or re-start collection if it exists
    public void updateMEStatus(Number rid, String tid, Number status)
    {
        
    }

   

    public List<KeyValuePairObj> getReferenceKeysForDependents()
    {
        return Collections.emptyList();
    }

    public void setRuleId(Number ruleId)
    {
        this.ruleId = ruleId;
    }

    public Number getRuleId()
    {
        return ruleId;
    }

    public void setTargetGuid(String targetGuid)
    {
        this.targetGuid = targetGuid;
    }

    public String getTargetGuid()
    {
        return targetGuid;
    }

    public void setTargetName(String targetName)
    {
        this.targetName = targetName;
    }

    public String getTargetName()
    {
        return targetName;
    }

    public void setCriticalEditVersion(Number criticalEditVersion)
    {
        this.criticalEditVersion = criticalEditVersion;
    }

    public Number getCriticalEditVersion()
    {
        return criticalEditVersion;
    }

    public void setEditVersion(Number editVersion)
    {
        this.editVersion = editVersion;
    }

    public Number getEditVersion()
    {
        return editVersion;
    }

    public void setIsCustomized(boolean isCustomized)
    {
        this.isCustomized = isCustomized;
    }

    public boolean isIsCustomized()
    {
        return isCustomized;
    }

    public void setIsEnabled(boolean isEnabled)
    {
        this.isEnabled = isEnabled;
    }

    public boolean isIsEnabled()
    {
        return isEnabled;
    }

    public String getEvaluationStatusText()
    {
        if (isEnabled)
        {
            return UiUtil.getUiString("ENABLED");
        }
        else
        {
            return UiUtil.getUiString("DISABLED");
        }
    }

    public void setLastAttemptDate(Date lastAttemptDate)
    {
        this.lastAttemptDate = lastAttemptDate;
    }

    public Date getLastAttemptDate()
    {
        return lastAttemptDate;
    }

    public void setStatus(Number status)
    {
        this.status = status;
    }

    // display the tranfer status in the assoc table
    public Number getStatus()
    {
        return status;
    }

    private String getStatusMsg(int stat)
    {
        if (stat == 0)
        {
            return UiUtil.getUiString("TRANSFER_PENDING");
        }
        else if (stat== 2)
        {
            return UiUtil.getUiString("TRANSFER_FAILED");
        }
        else // is 1
        {
            return UiUtil.getUiString("TRANSFER_DONE");
        }    
    }

    // TODO we need descriptive messages if failed
    private String getMetricStatusMsg(int mestat)
    {
        if (mestat == 2 || mestat == 5) // failed during deploy or undeploy
        {
            return UiUtil.getUiString("ME_FAILED");
        }
        else if (mestat == 1) // collection started
        {
            return UiUtil.getUiString("ME_STARTED");
        }    
        else if (mestat == 4) // collection stopped
        {
            return UiUtil.getUiString("ME_STOPPED");            
        }
        if (mestat == 0 || mestat == 3) // pending
        {
            return UiUtil.getUiString("ME_PENDING");            
        }
        else if (mestat == -1) // user did not check create ME
        {
            return UiUtil.getUiString("ME_NA");
        }
        else
            return "";
    }

    public String getTransferStatusText()
    {
        return getStatusMsg(status.intValue());
    }

    public void setEmdURL(String emdURL)
    {
        this.emdURL = emdURL;
    }

    public String getEmdURL()
    {
        return emdURL;
    }

    public void setMestatus(Number mestatus)
    {
        this.mestatus = mestatus;
    }
    
    public String getMetricStatusText()
    {
        String retStat = "";
        if (mestatus != null)
            retStat = getMetricStatusMsg(mestatus.intValue());
        return retStat;
    }
    
    public Number getMestatus()
    {
        return mestatus;
    }

    public int getMestatusVal()
    {
        return mestatus.intValue();
    }

    // TODO map a descriptive msg id
    public void setMemsgId(String memsgId)
    {
        this.memsgId = memsgId;
    }

    public String getMemsgId()
    {
        return memsgId;
    }
    public void setTxfdetails(String txfdetails) {
        this.txfdetails = txfdetails;
    }

    public String getTxfdetails() {
        return txfdetails;
    }

    public void setTargetType(String targetType)
    {
        this.targetType = targetType;
    }

    public String getTargetType()
    {
        return targetType;
    }
}

