/* $Header: emcore/jsrc/core/CoreLoganalyticsUi/src/oracle/sysman/core/loganalytics/ui/source/LoganSourceFieldRefsDataBean.java /st_emgc_pt-13.1mstr/4 2013/10/03 04:57:40 xxu Exp $ */

/* Copyright (c) 2002, 2013, Oracle and/or its affiliates. 
All rights reserved.*/

/*
   DESCRIPTION
   Log analytics Source field reference data bean

   PRIVATE CLASSES
    <list of private classes defined - with one-line descriptions>

   NOTES
    <other useful comments, qualificationsf, etc.>
   
    MODIFIED    (MM/DD/YY)
    vivsharm    01/04/13 - log Analytics
 */

package oracle.saas.logan.view.source;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import oracle.jbo.domain.Number;

import oracle.saas.logan.util.Logger;
import oracle.saas.logan.view.BaseLoganSourceDataBean;
import oracle.saas.logan.view.ILoganSourceDataBean;
import oracle.saas.logan.view.LaunchContextModeBean;


public class LoganSourceFieldRefsDataBean extends BaseLoganSourceDataBean 
    implements Cloneable
{
    private LoganSourceExtFieldsBean extFieldsBean;
    private LoganSourceDetailsBean sourceCurrentDataBean;
    
    private Number sourceId;
    private List<LoganSourceFieldReference> references;

    private static final Logger s_log =
        Logger.getLogger(LoganSourceFieldRefsDataBean.class.getName());
    
    private static final int STATE_INIT = 0;
    private static final int STATE_BEFORE_SUBMIT = 1;

    public LoganSourceFieldRefsDataBean()
    {
        this.references = new ArrayList<LoganSourceFieldReference>();
    }

    public LoganSourceFieldRefsDataBean(Number sourceId,
                                    List<LoganSourceFieldReference> references)
    {
        this.sourceId = sourceId;
        this.references = references;
    }
    
    public void initFieldRefs(Number sourceId, LaunchContextModeBean modeBean)
    {
        if (!modeBean.isCreateMode())
        {
            this.sourceId = sourceId;
            //TODO JPA
//            this.references = LoganLibUiUtil.loadLogSourceFieldRefs(sourceId);
        }
    }
    
    public Object clone()
    {
        LoganSourceFieldRefsDataBean c = new LoganSourceFieldRefsDataBean();
        c.setSourceId(sourceId);
        if (references != null)
        {
            List<LoganSourceFieldReference> frefs =
                new ArrayList<LoganSourceFieldReference>();
            for (LoganSourceFieldReference r: references)
            {
                frefs.add((LoganSourceFieldReference) r.clone());
            }
            c.setReferences(frefs);
        }
        return c;
    }

    /**
     * Called before the processSubmit step so that the
     * data beans can set the final parent references as needed
     * 
     * do nothing impl, make sure you override this with actual impl 
     * 
     * @param modeBean
     */
    
    public void beforeProcessSubmit(LaunchContextModeBean modeBean)
    {
        if(s_log.isFinestEnabled())
            s_log.finest("before Process Submit of FIELD REFSS BEAN got called");
        if (modeBean.isCreateLikeMode()){
            List<LoganSourceFieldReference> refList = new ArrayList<LoganSourceFieldReference>();
            Iterator<LoganSourceFieldReference> fIter = this.references.iterator();
            while(fIter.hasNext())
            {
                LoganSourceFieldReference r = fIter.next();
                r.setSrefmapSourceId(sourceId);
                refList.add(r);
            }
            this.references = refList;
        }
        setParentExtForReferences(STATE_BEFORE_SUBMIT);
    }

    public void initModeSpecificData(LaunchContextModeBean modeBean)
    {
        if(modeBean.isReadOnlyMode())
            return;
        setParentExtForReferences(STATE_INIT);
    }
    
    public void processSubmit(ILoganSourceDataBean refData, 
                              LaunchContextModeBean modeBean)
    {
        //TODO JPA
//        if(s_log.isFinestEnabled())
//            s_log.finest("processSubmit of FIELD REFSS BEAN got called");
//        boolean readOnlyMode =modeBean.isReadOnlyMode();
//        if (readOnlyMode) // SHOW DETAILS
//            return;        
//        boolean editMode = modeBean.isEditMode();
//        // process FieldReference
//        List<LoganSourceFieldReference> iPRef =
//                    ((LoganSourceFieldRefsDataBean) refData).getReferences();
//        List<LoganSourceFieldReference> iPCurr = this.getReferences();
//        List<IPersistDataOpsHandler> rList = LoganLibUiUtil.getIPersistList(iPRef);
//        List<IPersistDataOpsHandler> cList = LoganLibUiUtil.getIPersistList(iPCurr);
//
//        LoganLibUiUtil.persistChildObjectData(rList, cList,
//                                              readOnlyMode, editMode);
//        if(s_log.isFinestEnabled())
//            s_log.finest("Field References : Persist Data done");
//
//        // process Field Ref Content - there could be 1000s of rows
//        // try to be smarter while checking the hasChanges flag
//        List<LoganSourceFieldRefContent> rcRef = getAllRefContent(iPRef);
//        List<LoganSourceFieldRefContent> rcCur = getAllRefContent(iPCurr);
//        rList = LoganLibUiUtil.getIPersistList(rcRef);
//        cList = LoganLibUiUtil.getIPersistList(rcCur);
//        LoganLibUiUtil.persistChildObjectData(rList, cList,
//                                              readOnlyMode, editMode);
//        if(s_log.isFinestEnabled())
//            s_log.finest("Field Reference Contents : Persist Data done");
    }
    
    public void resetParentExtForReferences()
    {
        setParentExtForReferences(this.STATE_INIT);
    }
    
    private void setParentExtForReferences(int state)
    {
        //TODO JPA
//        if(this.references != null) 
//        {
//            // if a reference is based on an Ext Field and the user
//            // has removed that on the prev wizard step for ext fields, 
//            // we need to make sure that the
//            // field reference that was using it gets removed here as well
//            Iterator<LoganSourceFieldReference> fIter = this.references.iterator();
//            while(fIter.hasNext())
//            {
//                LoganSourceFieldReference r = fIter.next();
//                boolean attachedToExtf = false;
//                if(r.getRefBaseFieldIname() != null &&
//                   r.getRefExtFieldIname() != null) // means it references an Ext Field
//                {
//                    attachedToExtf = attachFieldRefToParentExt(r, state);
//                    if(!attachedToExtf)
//                    {
//                        // The Ext Field for Field which this field reference 
//                        // is based on, was not found.
//                        // which means at the previous step (Ext Fields), the 
//                        // user would have removed the Ext Field which this
//                        // Field reference was based on and so this field ref
//                        // must also be deleted
//                        fIter.remove();
//                    }
//                }
//                if(STATE_BEFORE_SUBMIT == state)
//                {
//                    // set this reference as the parent for all its LoganSourceFieldRefContent
//                    List<LoganSourceFieldRefContent> rcForFRRef = 
//                        r.getRefContentNameValuePairs();
//                    if(rcForFRRef != null && rcForFRRef.size() > 0)
//                    {
//                        for(LoganSourceFieldRefContent rc : rcForFRRef)
//                            r.addDependentChildObject(rc);
//                    }
//                    if(!attachedToExtf) // if not attached, this is needed
//                    {
//                        LoganSourceDetailsMain mainDetails = new LoganSourceDetailsMain(null);
//                        // now the main bena will set its Source ID here
//                        mainDetails.addDependentChildObject(r);
//                    }
//                }
//            }
//        }        
    }

    private boolean attachFieldRefToParentExt(LoganSourceFieldReference fref,
                                              int state)
    {
        //TODO JPA
//        boolean attached = false;
//        // check if the corresponding Ext Field exists
//        LoganSourceExtFieldsSetting refEF =  getExtFieldBeanForRef(fref);
//        // if corresponding ext Field exists it becomes the parent
//        if(refEF != null)
//        {
//            // refEF is now parent of r and will set its SeffId
//            if(STATE_BEFORE_SUBMIT == state)
//            {
//                refEF.addDependentChildObject(fref); 
//            }
//            else if(STATE_INIT == state)
//            {
//                fref.setRefSeffId(refEF.getSeffId());
//            }
//            attached = true;
//        }
//        return attached;
        return true;
    }

    /**
     * Gets the LoganSourceExtFieldsBean from the current list of
     * extended fields as per the current ext field definitions from
     * the previous train step of Extended fields
     * @param fr
     * @return
     */
    private LoganSourceExtFieldsSetting getExtFieldBeanForRef(LoganSourceFieldReference fr)
    {
        LoganSourceExtFieldsSetting referencedExtField = null;
        LoganSourceExtFieldsBean extCurr =getExtFieldsBean();
        if(extCurr != null && extCurr.getSettings() != null)
        {
            List<LoganSourceExtFieldsSetting> currSett = extCurr.getSettings();
            // repeated lookups - change to HasMap
            for(LoganSourceExtFieldsSetting f : currSett)
            {
                String bf = f.getSefBaseFieldIname();
                String nf = f.getSeffNewFieldIname();
                bf = (bf != null ? bf : "");
                nf = (nf != null ? nf : "");
                if(bf.equals(fr.getRefBaseFieldIname()) && 
                    nf.equals(fr.getRefExtFieldIname()))
                {
                    referencedExtField = f;
                    break;
                }
            }
        }
        return referencedExtField;
    }

    private List<LoganSourceFieldRefContent> getAllRefContent(List<LoganSourceFieldReference> rList)
    {
        List<LoganSourceFieldRefContent> rcRef = 
            new ArrayList<LoganSourceFieldRefContent>();
        if(rList != null && rList.size() > 0)
        {
            for(LoganSourceFieldReference fr : rList)
            {
                if(fr.isNewFR())
                {
                    List<LoganSourceFieldRefContent> rcForFRRef = 
                        fr.getRefContentNameValuePairs();
                    if(rcForFRRef != null && rcForFRRef.size() > 0)
                    {
                        rcRef.addAll(rcForFRRef);
                    }
                }
            }
        }
        return rcRef;
    }

    public void setReferences(List<LoganSourceFieldReference> references)
    {
        this.references = references;
    }

    public List<LoganSourceFieldReference> getReferences()
    {
        return references;
    }

    public void setReferenceContent(LoganSourceFieldReference fr, 
                              List<LoganSourceFieldRefContent> rc)
    {
        fr.setRefContentNameValuePairs(rc);
    }

    public List<LoganSourceFieldRefContent> getReferenceContent(LoganSourceFieldReference fr)
    {
        return fr.getRefContentNameValuePairs();
    }

    public void setSourceId(Number sourceId)
    {
        this.sourceId = sourceId;
    }

    public Number getSourceId()
    {
        return sourceId;
    }

    public void setExtFieldsBean(LoganSourceExtFieldsBean extFieldsBean) {
        this.extFieldsBean = extFieldsBean;
    }

    public LoganSourceExtFieldsBean getExtFieldsBean() {
        return extFieldsBean;
    }

    public void setSourceCurrentDataBean(LoganSourceDetailsBean sourceCurrentDataBean) {
        this.sourceCurrentDataBean = sourceCurrentDataBean;
    }

    public LoganSourceDetailsBean getSourceCurrentDataBean() {
        return sourceCurrentDataBean;
    }
}
