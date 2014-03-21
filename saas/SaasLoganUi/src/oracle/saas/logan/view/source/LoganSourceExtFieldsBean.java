/* $Header: emcore/jsrc/core/CoreLoganalyticsUi/src/oracle/sysman/core/loganalytics/ui/source/LoganSourceExtFieldsBean.java /st_emgc_pt-13.1mstr/6 2014/01/26 17:07:46 vivsharm Exp $ */

/* Copyright (c) 2002, 2014, Oracle and/or its affiliates. 
All rights reserved.*/

/*
   DESCRIPTION
   Log analytics Source Details extended fields data bean

   PRIVATE CLASSES
    <list of private classes defined - with one-line descriptions>

   NOTES
    <other useful comments, qualificationsf, etc.>
   
    MODIFIED    (MM/DD/YY)
    vivsharm    01/23/14 - sort the settings on fieldIname
    vivsharm    01/04/13 - log Analytics
 */

/**
 *  @version $Header: emcore/jsrc/core/CoreLoganalyticsUi/src/oracle/sysman/core/loganalytics/ui/source/LoganSourceExtFieldsBean.java /st_emgc_pt-13.1mstr/6 2014/01/26 17:07:46 vivsharm Exp $
 *  @author  vivsharm
 *  @since   release specific (what release of product did this appear in)
 */

package oracle.saas.logan.view.source;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import oracle.jbo.domain.Number;

import oracle.saas.logan.util.Logger;
import oracle.saas.logan.view.BaseLoganSourceDataBean;
import oracle.saas.logan.view.ILoganSourceDataBean;
import oracle.saas.logan.view.LaunchContextModeBean;


public class LoganSourceExtFieldsBean extends BaseLoganSourceDataBean 
    implements Cloneable
{
    private Number sourceId;
    private List<LoganSourceExtFieldsDefn> definitions;
    private List<LoganSourceExtFieldsSetting> settings;
    private List<LoganSavedRegexBean> savedRegularExpList = 
        new ArrayList<LoganSavedRegexBean>();
    private Map<String, LoganSavedRegexBean> savedRegularExpMap = 
        new HashMap<String, LoganSavedRegexBean>();

    private List<LoganSavedRegexBean> savedRegularExpListRef = 
        new ArrayList<LoganSavedRegexBean>();

    private static final Logger s_log =
        Logger.getLogger(LoganSourceExtFieldsBean.class.getName());

    public LoganSourceExtFieldsBean()
    {
        this.definitions = new ArrayList<LoganSourceExtFieldsDefn>();
        this.settings = new ArrayList<LoganSourceExtFieldsSetting>();
        this.savedRegularExpList = new ArrayList<LoganSavedRegexBean>();
        this.savedRegularExpMap = new HashMap<String, LoganSavedRegexBean>();
    }

    public LoganSourceExtFieldsBean(Number sourceId,
                                    List<LoganSourceExtFieldsDefn> definitions,
                                    List<LoganSourceExtFieldsSetting> settings,
                                    List<LoganSavedRegexBean> savedRegularExpList,
                                    Map<String, LoganSavedRegexBean> savedRegularExpMap)
    {
        this.sourceId = sourceId;
        this.definitions = definitions;
        this.settings = settings;
        Collections.sort(this.settings);
        this.savedRegularExpList = savedRegularExpList;
        this.savedRegularExpMap = savedRegularExpMap;
    }

    public void initExtFields(Number srId, LaunchContextModeBean modeBean)
    {
        //TODO JPA
//        if (!modeBean.isCreateMode())
//        {
//            this.sourceId = srId;
//            this.definitions = LoganLibUiUtil.loadLogSourceExtFieldDefs(sourceId);
//            HashMap<Number, LoganSourceExtFieldsDefn> defM = 
//                new HashMap<Number, LoganSourceExtFieldsDefn>();
//            if(definitions != null)
//            {
//                for(LoganSourceExtFieldsDefn d : definitions)
//                    defM.put(d.getSefId(), d);
//            }
//            this.settings = LoganLibUiUtil.loadLogSourceExtFieldSettings(sourceId);
//            if(this.definitions != null && this.settings != null)
//            {
//                for(LoganSourceExtFieldsSetting fieldS : settings)
//                {
//                    LoganSourceExtFieldsDefn parentDef = defM.get(fieldS.getSeffSefId());
//                    if(parentDef != null)
//                        parentDef.getFields().add(fieldS);
//                }
//            }
//            refreshSettingsForSource();
//        }
//        this.savedRegularExpList = 
//            LoganLibUiUtil.loadSavedRegularExpList(this.savedRegularExpMap);        
//        for(LoganSavedRegexBean sr : savedRegularExpList)
//            savedRegularExpMap.put(sr.getRegexDname(), sr); 
    }

    public void initModeSpecificData(LaunchContextModeBean modeBean)
    {
        for(LoganSavedRegexBean sr : savedRegularExpList)
            savedRegularExpListRef.add((LoganSavedRegexBean)sr.clone());
    }

    public Object clone()
    {
        LoganSourceExtFieldsBean c = new LoganSourceExtFieldsBean();
        c.setSourceId(sourceId);
        if (definitions != null)
        {
            List<LoganSourceExtFieldsDefn> ds =
                new ArrayList<LoganSourceExtFieldsDefn>();
            for (LoganSourceExtFieldsDefn p: definitions)
            {
                ds.add((LoganSourceExtFieldsDefn) p.clone());
            }
            c.setDefinitions(ds);
        }
        if (settings != null)
        {
            List<LoganSourceExtFieldsSetting> ss =
                new ArrayList<LoganSourceExtFieldsSetting>();
            for (LoganSourceExtFieldsSetting p: settings)
            {
                ss.add((LoganSourceExtFieldsSetting) p.clone());
            }
            c.setSettings(ss);
            c.refreshSettingsForSource();
        }
        if(savedRegularExpList != null)
        {
            List<LoganSavedRegexBean> srel = 
                    new ArrayList<LoganSavedRegexBean>();
            Map<String, LoganSavedRegexBean> srem = 
                    new HashMap<String, LoganSavedRegexBean>();
            for(LoganSavedRegexBean sr : savedRegularExpList)
            {
                LoganSavedRegexBean lsrb = (LoganSavedRegexBean)sr.clone();
                srel.add(lsrb);
                srem.put(lsrb.getRegexDname(), lsrb);
            }
            c.setSavedRegularExpList(srel);
            c.setSavedRegularExpMap(srem);
        }
        return c;
    }

    /**
     * Called before the processSubmit step so that the
     * step data beans can set the final parent references as needed
     * 
     * @param modeBean
     */    
    public void beforeProcessSubmit(LaunchContextModeBean modeBean)
    {
        //TODO JPA
//        if(s_log.isFinestEnabled())
//            s_log.finest("before process submit of EXT FIELDS BEAN got called");
//        if (this.definitions != null)
//        {
//            LoganSourceDetailsMain mainDetails = new LoganSourceDetailsMain(null);
//            Map<String, String> paramsMap = new HashMap<String, String>();
//            for(LoganSavedRegexBean sreg : getSavedRegularExpList())
//                paramsMap.put(sreg.getRegexDname(), sreg.getRegexContent());
//            LoganExtendedFieldRegexpConverter conv = 
//                new LoganExtendedFieldRegexpConverter(paramsMap);
//            for (LoganSourceExtFieldsDefn defn: this.definitions)
//            {
//                if (modeBean.isCreateLikeMode()){
//                    defn.setSefSourceId(this.sourceId);
//                }
//                mainDetails.addDependentChildObject(defn);
//                String convertedRegex = 
//                    conv.convertExtendedParser(defn.getSefRegex());
//                defn.setSefConvertedRegex(new ClobDomain(convertedRegex));
//                List<LoganSourceExtFieldsSetting> efields =
//                    defn.getFields();
//                if (efields.size() != 0)
//                {
//                    for (LoganSourceExtFieldsSetting setting: efields)
//                    {
//                        defn.addDependentChildObject(setting);
//                    }
//                }
//            }
//        }
    }
    
    public void processSubmit(ILoganSourceDataBean refData, 
                              LaunchContextModeBean modeBean)
    {
        //TODO JPA
//        if(s_log.isFinestEnabled())
//            s_log.finest("processSubmit of EXT FIELDS BEAN got called");
//        boolean readOnlyMode = modeBean.isReadOnlyMode();
//        if (readOnlyMode) // SHOW DETAILS
//            return;        
//        boolean editMode = modeBean.isEditMode();        
//        
//        // process the Saved Regexps First since that will be referenced
//        // by the Ext field Defns
//        List<IPersistDataOpsHandler> rList = 
//            LoganLibUiUtil.getIPersistList(savedRegularExpListRef);
//        List<IPersistDataOpsHandler> cList = 
//            LoganLibUiUtil.getIPersistList(getSavedRegularExpList());
//        
//        LoganLibUiUtil.persistChildObjectData(rList, cList,
//                                              readOnlyMode, true);
//        if(s_log.isFinestEnabled())
//            s_log.finest("Extfield Defs : Persist Data done");
//
//        // process ExtfieldDefns
//        List<LoganSourceExtFieldsDefn> iPRef =
//                    ((LoganSourceExtFieldsBean) refData).getDefinitions();
//        List<LoganSourceExtFieldsDefn> iPCurr = this.getDefinitions();        
//        rList = LoganLibUiUtil.getIPersistList(iPRef);
//        cList = LoganLibUiUtil.getIPersistList(iPCurr);
//
//        LoganLibUiUtil.persistChildObjectData(rList, cList,
//                                              readOnlyMode, editMode);
//        if(s_log.isFinestEnabled())
//            s_log.finest("Extfield Defs : Persist Data done");
//
//        // process ExtfieldSettings
//        List<LoganSourceExtFieldsSetting> sPRef =
//                    ((LoganSourceExtFieldsBean) refData).getSettings();
//        List<LoganSourceExtFieldsSetting> sPCurr = this.getSettings();        
//        rList = LoganLibUiUtil.getIPersistList(sPRef);
//        cList = LoganLibUiUtil.getIPersistList(sPCurr);
//        LoganLibUiUtil.persistChildObjectData(rList, cList,
//                                              readOnlyMode, editMode);
//        if(s_log.isFinestEnabled())
//            s_log.finest("Extfield Settings : Persist Data done");
    }
    
    public void clearSettings()
    {
        if(settings != null)
            this.settings.clear();
    }

    public void updateSettingsWithRegexContent()
    {
        if (this.definitions != null && this.definitions.size() > 0 &&
            savedRegularExpMap != null && savedRegularExpMap.size() > 0)
        {
            for (LoganSourceExtFieldsDefn defn: this.definitions)
            {
                List<LoganSourceExtFieldsSetting> fields = defn.getFields();
                if (fields != null && fields.size() > 0)
                {
                    for (LoganSourceExtFieldsSetting setting: fields)
                    {
                        if(setting.getSavedRegexDname() != null &&
                            setting.getRegexContent() == null)
                        {
                            LoganSavedRegexBean rb = 
                                savedRegularExpMap.get(setting.getSavedRegexDname());
                            if(rb != null)
                                setting.setRegexContent(rb.getRegexContent());
                        }
                    }
                }
            }
        }
    }
    
    public void refreshSettingsForSource()
    {
        if (this.definitions != null && this.definitions.size() > 0)
        {
            if(this.settings == null)
                this.settings = new ArrayList<LoganSourceExtFieldsSetting>();
            this.settings.clear();
            for (LoganSourceExtFieldsDefn defn: this.definitions)
            {
                List<LoganSourceExtFieldsSetting> fields =
                    defn.getFields();
                if (fields != null && fields.size() > 0)
                {
                    for (LoganSourceExtFieldsSetting setting: fields)
                    {
                        this.settings.add(setting);
                    }
                }
            }
            Collections.sort(settings);
        }
    }

    public void addExtFieldDefn(LoganSourceExtFieldsDefn extFirldDefn)
    {
        if (this.definitions == null)
        {
            this.definitions = new ArrayList<LoganSourceExtFieldsDefn>();
        }
        definitions.add(extFirldDefn);
        refreshSettingsForSource();
    }

    public void removeExtFieldDefn(LoganSourceExtFieldsDefn currSelectedExtFieldsDefn)
    {        
        definitions.remove(currSelectedExtFieldsDefn);
        refreshSettingsForSource();
    }
    
    public void setDefinitions(List<LoganSourceExtFieldsDefn> definitions)
    {
        this.definitions = definitions;
    }

    public List<LoganSourceExtFieldsDefn> getDefinitions()
    {
        return definitions;
    }

    public void setSettings(List<LoganSourceExtFieldsSetting> settings)
    {
        this.settings = settings;
    }

    public List<LoganSourceExtFieldsSetting> getSettings()
    {
        return settings;
    }

    public void setSourceId(Number sourceId)
    {
        this.sourceId = sourceId;
    }

    public Number getSourceId()
    {
        return sourceId;
    }

    public void setSavedRegularExpList(List<LoganSavedRegexBean> savedRegularExpList)
    {
        this.savedRegularExpList = savedRegularExpList;
    }

    public List<LoganSavedRegexBean> getSavedRegularExpList()
    {
        return savedRegularExpList;
    }

    public void setSavedRegularExpMap(Map<String, LoganSavedRegexBean> savedRegularExpMap)
    {
        this.savedRegularExpMap = savedRegularExpMap;
    }

    public Map<String, LoganSavedRegexBean> getSavedRegularExpMap()
    {
        return savedRegularExpMap;
    }
}
