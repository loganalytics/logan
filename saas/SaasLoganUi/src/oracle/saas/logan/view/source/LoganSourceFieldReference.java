/* $Header: emcore/jsrc/core/CoreLoganalyticsUi/src/oracle/sysman/core/loganalytics/ui/source/LoganSourceFieldReference.java /st_emgc_pt-13.1mstr/5 2014/02/19 10:58:52 vivsharm Exp $ */

/* Copyright (c) 2002, 2014, Oracle and/or its affiliates. 
All rights reserved.*/

/*
   DESCRIPTION
   Log analytics Source field reference data bean

   PRIVATE CLASSES
    <list of private classes defined - with one-line descriptions>

   NOTES
    <other useful comments, qualificationsf, etc.>
   
    MODIFIED    (MM/DD/YY)
    vivsharm    02/11/14 - set defaults for user defined common fields
    rban        01/02/14 - for common field management
    vivsharm    01/04/13 - log Analytics
 */

/**
 *  @version $Header: emcore/jsrc/core/CoreLoganalyticsUi/src/oracle/sysman/core/loganalytics/ui/source/LoganSourceFieldReference.java /st_emgc_pt-13.1mstr/5 2014/02/19 10:58:52 vivsharm Exp $
 *  @author  vivsharm
 *  @since   release specific (what release of product did this appear in)
 */

package oracle.saas.logan.view.source;

import java.util.ArrayList;
import java.util.List;

import oracle.jbo.Key;
import oracle.jbo.Row;
import oracle.jbo.domain.Number;
import oracle.jbo.server.ViewObjectImpl;

import oracle.saas.logan.util.Logger;
import oracle.saas.logan.util.UiUtil;
import oracle.saas.logan.view.BaseDataPersistenceHandler;
import oracle.saas.logan.view.KeyValuePairObj;


public class LoganSourceFieldReference extends BaseDataPersistenceHandler
    implements Cloneable
{
    // Table EM_LOGAN_SOURCE_REF_MAP
    private static final String SrefmapSourceId = "SrefmapSourceId";
    private Number srefmapSourceId;
    private static final String SrefmapRefIname = "SrefmapRefIname";
    private String srefmapRefIname;

    // Table EM_LOGAN_REFERENCE
    private static final String RefLookupType = "RefLookupType";
    private Number refLookupType;
    private String refLookupTypeDisplay;
    private static final String RefId = "RefId";
    private Number refId;
    private static final String RefIname = "RefIname";
    private String refIname;
    private static final String RefDname = "RefDname";
    private String refDname;
    private static final String RefBaseFieldIname = "RefBaseFieldIname";
    private String refBaseFieldIname;
    private static final String RefBaseFieldDname = "RefBaseFieldDname";
    private String refBaseFieldDname;
    private static final String RefSeffId = "RefSeffId";
    private Number refSeffId;
    private static final String RefExtFieldIname = "SeffNewFieldIname";
    private String refExtFieldIname;
    private static final String RefExtFieldDname = "RefExtFieldDname";
    private String refExtFieldDname;
    private static final String RefNewFieldIname = "RefNewFieldIname";
    private String refNewFieldIname;
    private static final String RefNewFieldDname = "RefNewFieldDname";
    private String refNewFieldDname;
    private static final String RefRepositoryQuery = "RefRepositoryQuery";
    private String refRepositoryQuery;
    private static final String RefAuthor = "RefAuthor";
    private String author;
    private static final String RefIsSystem = "RefIsSystem";
    private Number refIsSystem;

    private String contentDisplay;
    private String contentDisplayDetailed;
    
    private boolean nvpType;
    private Integer moreRows = 0;
    
    private boolean newFR = false;

    public static final int NVP_TYPE = 1;
    public static final int SQL_TYPE = 2;

    public static final Number NVP_CHOICE = new Number(NVP_TYPE);
    public static final Number SQL_CHOICE = new Number(SQL_TYPE);

    private List<LoganSourceFieldRefContent> refContentNameValuePairs;

    private static final Logger s_log =
        Logger.getLogger(LoganSourceFieldReference.class.getName());

    private static final String ParentFK_SourceId = "SourceId";
    private static final String ParentFK_SefSourceId = "SefSourceId";
    
    private static final String ParentFK_SeffId = "SeffId";
    
    public LoganSourceFieldReference()
    {
        super();
    }
    
    public LoganSourceFieldReference(Number srefmapSourceId,
                                     String srefmapRefIname, Number refId,
                                     Number refSeffId, String refIname,
                                     String refDname,
                                     String refBaseFieldIname,
                                     String refBaseFieldDname,
                                     String refExtFieldIname,
                                     String refExtFieldDname,
                                     String refNewFieldIname,
                                     String refNewFieldDname,
                                     String author, Number refIsSystem,
                                     Number refLookupType,
                                     List<LoganSourceFieldRefContent> refContentNameValuePairs,
                                     String refRepositoryQuery,
                                     boolean newFR)
    {
        super();
        this.srefmapSourceId = srefmapSourceId;
        this.srefmapRefIname = srefmapRefIname;

        this.refId = refId;

        this.refSeffId = refSeffId;

        this.refIname = refIname;
        this.refDname = refDname;

        this.refBaseFieldIname = refBaseFieldIname;
        this.refBaseFieldDname = refBaseFieldDname;

        this.refExtFieldIname = refExtFieldIname;
        this.refExtFieldDname = refExtFieldDname;

        this.refNewFieldIname = refNewFieldIname;
        this.refNewFieldDname = refNewFieldDname;

        this.author = author;
        this.refIsSystem = refIsSystem;

        this.refLookupType = refLookupType;
        this.refContentNameValuePairs = refContentNameValuePairs;
        this.refRepositoryQuery = refRepositoryQuery;
        setupRefLookupTypeDisplayAndContent();
        this.newFR = newFR;
    }

    public LoganSourceFieldReference(Row vorow)
    {
        super();
        if (vorow == null)
            return;
        
        if(vorow.getAttributeIndexOf(SrefmapSourceId) >= 0)
            this.srefmapSourceId =
                    (Number) vorow.getAttribute(SrefmapSourceId);
        if(vorow.getAttributeIndexOf(SrefmapRefIname) >= 0)
            this.srefmapRefIname =
                    (String) vorow.getAttribute(SrefmapRefIname);

        this.refId = (Number) vorow.getAttribute(RefId);

        this.refSeffId = (Number) vorow.getAttribute(RefSeffId);

        this.refIname = (String) vorow.getAttribute(RefIname);
        this.refDname = (String) vorow.getAttribute(RefDname);

        this.refBaseFieldIname =
                (String) vorow.getAttribute(RefBaseFieldIname);
        this.refBaseFieldDname =
                (String) vorow.getAttribute(RefBaseFieldDname);

        this.refExtFieldIname =
                (String) vorow.getAttribute(RefExtFieldIname);
        this.refExtFieldDname =
                (String) vorow.getAttribute(RefExtFieldDname);

        this.refNewFieldIname =
                (String) vorow.getAttribute(RefNewFieldIname);
        this.refNewFieldDname =
                (String) vorow.getAttribute(RefNewFieldDname);

        this.author = (String) vorow.getAttribute(RefAuthor);
        this.refIsSystem = (Number) vorow.getAttribute(RefIsSystem);

        this.refLookupType = (Number) vorow.getAttribute(RefLookupType);
        this.refRepositoryQuery =
                (String) vorow.getAttribute(RefRepositoryQuery);
        //TODO JPA
//        if (refLookupType != null && refLookupType.intValue() == NVP_TYPE)
//            this.refContentNameValuePairs = LoganLibUiUtil.loadLogSourceFieldRefContents(this.refIname);
        setupRefLookupTypeDisplayAndContent();
        this.newFR = false;
    }
    
    /**
     * Set up reference look up display and content, including name-value pair 
     * and sql repository query.
     * 
     */
    public void setupRefLookupTypeDisplayAndContent()
    {
        if (refLookupType != null && refLookupType.intValue() == NVP_TYPE)
        {
            nvpType = true;
            this.refLookupTypeDisplay =
                    UiUtil.getUiString("NAME_VALUE_PAIR_TYPE");
            String[] contentDisplayBriefAndFull =
                getRefContentDisplayBriefAndFull(refContentNameValuePairs);
            this.contentDisplay = contentDisplayBriefAndFull[0];
            this.contentDisplayDetailed = contentDisplayBriefAndFull[1];
        }
        else
        {
            nvpType = false;
            this.refLookupTypeDisplay =
                    UiUtil.getUiString("EM_REPOS_SQL_TYPE");
            this.contentDisplay = refRepositoryQuery;
            this.contentDisplayDetailed = refRepositoryQuery;
        }
    }

    /**
     * get referencr content display into array for name-value pair, split 
     * name-value by ',' for each line.
     * 
     * @param sfrc reference content list
     * @return String[] with name and value 
     */
    public String[] getRefContentDisplayBriefAndFull(List<LoganSourceFieldRefContent> sfrc)
    {
        StringBuilder sbb = new StringBuilder();
        StringBuilder sbf = new StringBuilder();
        if (sfrc != null && sfrc.size() > 0)
        {
            int totalRows = sfrc.size();
            int count = 0;
            for (LoganSourceFieldRefContent c: sfrc)
            {
                if (count < 2)
                {
                    sbb.append(c.getRefcName()).append(" , ");
                    sbb.append(c.getRefcValue()).append("<br/>");
                    count++;
                }
                sbf.append(c.getRefcName()).append(" , ");
                sbf.append(c.getRefcValue()).append("<br/>");
            }
            this.moreRows = totalRows - count;
        }
        return new String[]
            { sbb.toString(), sbf.toString() };
    }
    
    /**
     * get referencr name-value for edit
     * 
     * @return String for name-value pair
     */
    public String getRefNVPContentForEdit()
    {
        StringBuilder nvpc = new StringBuilder();
        if (nvpType)
        {
            if(refContentNameValuePairs != null && refContentNameValuePairs.size() > 0)
            {
                for (LoganSourceFieldRefContent c: refContentNameValuePairs)
                {
                    nvpc.append(c.getRefcName()).append(", ");
                    nvpc.append(c.getRefcValue()).append("\n");
                }
            }
        }
        return nvpc.toString();
    }

    public Object clone()
    {
        List<LoganSourceFieldRefContent> rcnvp = null;
        if (this.refContentNameValuePairs != null)
        {
            rcnvp = new ArrayList<LoganSourceFieldRefContent>();
            for (LoganSourceFieldRefContent rc: refContentNameValuePairs)
            {
                rcnvp.add((LoganSourceFieldRefContent) rc.clone());
            }
        }
        LoganSourceFieldReference fref =
            new LoganSourceFieldReference(srefmapSourceId, srefmapRefIname,
                                          refId, refSeffId, refIname,
                                          refDname, refBaseFieldIname,
                                          refBaseFieldDname,
                                          refExtFieldIname,
                                          refExtFieldDname,
                                          refNewFieldIname,
                                          refNewFieldDname, author,
                                          refIsSystem, refLookupType,
                                          rcnvp, refRepositoryQuery, newFR);
        return fref;
    }
    
    /**
     * Handle the Insert operation here.
     * will first retrive fieid name and insert new field into common field 
     * table before doing field ref.
     */
    public void handleInsertOperation()
    {
        //TODO JPA
//        if(this.newFR)
//        {           
//            String[] userDefNames =null;
//            String fieldObsFieldName = null;
//  
//            userDefNames = LoganLibUiUtil.getUserDefFieldNames();
//                     
//            if (userDefNames != null && userDefNames.length == 2)
//            { 
//                this.refNewFieldIname = userDefNames[0];
//                fieldObsFieldName = userDefNames[1];
//            }
//            if (this.refNewFieldIname != null) {
//                LoganLibUiUtil.insertCommonFieldIfNeeded(this.refNewFieldDname,
//                                                         this.refNewFieldIname,
//                                                         LoganCommonFieldBean.FIELD_DATATYPE_USERDEF_DEFAULT,
//                                                         new Number(LoganCommonFieldBean.FIELD_MAXSIZE_USERDEF_DEFAULT),
//                                                         fieldObsFieldName, null,
//                                                         null);               
//                
//                ViewObjectImpl voRef = getViewObjectImpl();
//                NameValuePairs nvpmm = new NameValuePairs();
//                // Note the Id is generated only when we create a new row
//                this.refId = LoganLibUiUtil.getNextSequenceId("EM_LOGAN_REFERENCE_SEQ");
//                nvpmm.setAttribute(RefId, refId);
//        
//                nvpmm.setAttribute(RefIname, this.refIname);
//                nvpmm.setAttribute(RefDname, this.refDname);
//                nvpmm.setAttribute(RefBaseFieldIname, this.refBaseFieldIname);
//                nvpmm.setAttribute(RefSeffId, this.refSeffId);
//                nvpmm.setAttribute(RefNewFieldIname, this.refNewFieldIname);
//                nvpmm.setAttribute(RefLookupType, this.refLookupType);
//                nvpmm.setAttribute(RefRepositoryQuery, this.refRepositoryQuery);
//                author = EMExecutionContext.getExecutionContext().getEMUser();
//                nvpmm.setAttribute(RefAuthor, author);
//                nvpmm.setAttribute(RefIsSystem, new Number(0));
//        
//                Row iRow = voRef.createAndInitRow(nvpmm);
//                voRef.insertRow(iRow);
//        
//                if (s_log.isFinestEnabled())
//                {
//                    StringBuilder sb = new StringBuilder();
//                    sb.append("LOG FIELD REFERENCE BEAN created a row to INSERT");
//                    sb.append("refIname = ").append(refIname);
//                    sb.append(", refBaseFieldIname = ").append(refBaseFieldIname);
//                    sb.append(", refSeffId = ").append(refSeffId);
//                    sb.append(", refExtFieldIname = ").append(this.refExtFieldIname);
//                    sb.append(", refNewFieldIname = ").append(refNewFieldIname);
//                    sb.append(", refLookupType = ").append(refLookupType);
//                    sb.append(", RefId = ").append( refId);
//                    s_log.finest(sb.toString());
//                }
//            }
//        }
//
//        // the REFERENCE is created or existed before, now create the MAP
//        LoganAMImpl am = LoganLibUiUtil.getAppModule();
//        ViewObjectImpl voSrefMap = am.getEmLoganSourceFieldRefsVO();
//        NameValuePairs nvpsm = new NameValuePairs();
//        nvpsm.setAttribute(SrefmapSourceId, this.srefmapSourceId);
//        nvpsm.setAttribute(SrefmapRefIname, this.srefmapRefIname);
//        Row smRow = voSrefMap.createAndInitRow(nvpsm);
//        voSrefMap.insertRow(smRow);
//
//        if (s_log.isFinestEnabled())
//        {
//            StringBuilder sb2 = new StringBuilder();
//            sb2.append("LOG SOURCE FIELD REFERENCE MAP created a row to INSERT:");
//            sb2.append(" srefmapRefIname ").append(srefmapRefIname);
//            sb2.append(", srefmapSourceId = ").append(srefmapSourceId);
//            s_log.finest(sb2.toString());
//        }
    }

    /**
     * Handle the Insert operation here.
     * For new added field, will first retrive fieid name and insert new field 
     * into common field table before doing field ref.
     */
    public void handleUpdateOperation()
    {
        //JPA
//        Object values[] =
//        { this.refIname };
//        Key key = new Key(values);
//        Row[] uRows = getViewObjectImpl().findByKey(key, 1);
//
//        if (uRows != null && uRows.length > 0)
//        {
//            Row uRow = uRows[0];
//            if (this.refNewFieldIname == null) {
//                String[] userDefNames =null;
//                String fieldObsFieldName = null;
//
//                userDefNames = LoganLibUiUtil.getUserDefFieldNames();
//
//                if (userDefNames != null && userDefNames.length == 2)
//                { 
//                    this.refNewFieldIname = userDefNames[0];
//                    fieldObsFieldName = userDefNames[1];
//                }
//                
//                LoganLibUiUtil.insertCommonFieldIfNeeded(this.refNewFieldDname,
//                                                         this.refNewFieldIname,
//                                                         LoganCommonFieldBean.FIELD_DATATYPE_USERDEF_DEFAULT,
//                                                         new Number(LoganCommonFieldBean.FIELD_MAXSIZE_USERDEF_DEFAULT),
//                                                         fieldObsFieldName, null, null);
//            }
//            
//            uRow.setAttribute(RefBaseFieldIname, this.refBaseFieldIname);
//            uRow.setAttribute(RefSeffId, this.refSeffId);
//            uRow.setAttribute(RefNewFieldIname, this.refNewFieldIname);
//            uRow.setAttribute(RefLookupType, this.refLookupType);
//            uRow.setAttribute(RefRepositoryQuery, this.refRepositoryQuery);
//            if (s_log.isFinestEnabled())
//            {
//                StringBuilder sb = new StringBuilder();
//                sb.append("LOG FIELD REFERENCE BEAN row to UPDATE: ");
//                sb.append("refIname = ").append(refIname);
//                sb.append(", refBaseFieldIname = ").append(refBaseFieldIname);
//                sb.append(", refSeffId = ").append(refSeffId);
//                sb.append(", refExtFieldIname = ").append(this.refExtFieldIname);
//                sb.append(", refNewFieldIname = ").append(refNewFieldIname);
//                sb.append(", refLookupType = ").append(refLookupType);
//                sb.append(", RefId = ").append( refId);
//                s_log.finest(sb.toString());
//            }
//        }
    }

    /**
     * Handle the delete operation here.
     */
    public void handleDeleteOperation()
    {
        Object values[] =
        { this.refIname };
        Key key = new Key(values);
        Row[] dRows = getViewObjectImpl().findByKey(key, 1);

        if (dRows != null && dRows.length > 0)
        {
            Row dRow = dRows[0];
            if (s_log.isFinestEnabled())
            {
                StringBuilder sb = new StringBuilder();
                sb.append("LOG FIELD REFERENCE BEAN row to DELETE: ");
                sb.append("refIname = ").append(refIname);
                sb.append(", refBaseFieldIname = ").append(refBaseFieldIname);
                sb.append(", refSeffId = ").append(refSeffId);
                sb.append(", refExtFieldIname = ").append(this.refExtFieldIname);
                sb.append(", refNewFieldIname = ").append(refNewFieldIname);
                sb.append(", refLookupType = ").append(refLookupType);
                sb.append(", RefId = ").append( refId);
                s_log.finest(sb.toString());
            }
            dRow.remove();
        }
    }

    public void setParentReferenceKeys(List<KeyValuePairObj> rks)
    {
        if (rks == null || rks.size() <= 0)
            return;

        for (KeyValuePairObj kvpo: rks)
        {
            if (ParentFK_SourceId.equals(kvpo.getKey()) || 
                ParentFK_SefSourceId.equals(kvpo.getKey()))
            {
                this.setSrefmapSourceId((Number) kvpo.getValue());
            }
            else if(ParentFK_SeffId.equals(kvpo.getKey()))
            {
                this.setRefSeffId((Number) kvpo.getValue());
            }
        }
    }

    public String getPrimaryKeyAsString()
    {
        return this.refIname;
    }

    public List<KeyValuePairObj> getReferenceKeysForDependents()
    {
        List<KeyValuePairObj> rks = new ArrayList<KeyValuePairObj>(1);
        rks.add(new KeyValuePairObj(RefIname, this.refIname));
        return rks;
    }

    public boolean isObjInstanceOfSameClass(Object o)
    {
        return (o != null && o instanceof LoganSourceFieldReference);
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
        sb.append(this.refIname).append("_");
        sb.append(this.refBaseFieldIname).append("_");
        sb.append(this.refExtFieldIname).append("_");
        sb.append(this.refNewFieldDname).append("_");
        sb.append(this.refRepositoryQuery).append("_");
        sb.append(this.refLookupType);
        return sb.toString();
    }

    private ViewObjectImpl getViewObjectImpl()
    {
        
        //TODO JPA
//        LoganAMImpl am = LoganLibUiUtil.getAppModule();
//        return am.getEmLoganReferenceVO();
        return null;
    }

    public void setSrefmapSourceId(Number srefmapSourceId)
    {
        this.srefmapSourceId = srefmapSourceId;
    }

    public Number getSrefmapSourceId()
    {
        return srefmapSourceId;
    }

    public void setRefLookupType(Number refLookupType)
    {
        this.refLookupType = refLookupType;
    }

    public Number getRefLookupType()
    {
        return refLookupType;
    }

    public void setRefId(Number refId)
    {
        this.refId = refId;
    }

    public Number getRefId()
    {
        return refId;
    }

    public void setRefIname(String refIname)
    {
        this.refIname = refIname;
    }

    public String getRefIname()
    {
        return refIname;
    }

    public void setRefDname(String refDname)
    {
        this.refDname = refDname;
    }

    public String getRefDname()
    {
        return refDname;
    }

    public void setContentDisplay(String contentDisplay)
    {
        this.contentDisplay = contentDisplay;
    }

    public String getContentDisplay()
    {
        return contentDisplay;
    }

    public void setRefLookupTypeDisplay(String refLookupTypeDisplay)
    {
        this.refLookupTypeDisplay = refLookupTypeDisplay;
    }

    public String getRefLookupTypeDisplay()
    {
        return refLookupTypeDisplay;
    }

    public void setRefBaseFieldIname(String refBaseFieldIname)
    {
        this.refBaseFieldIname = refBaseFieldIname;
    }

    public String getRefBaseFieldIname()
    {
        return refBaseFieldIname;
    }

    public void setRefNewFieldIname(String refNewFieldIname)
    {
        this.refNewFieldIname = refNewFieldIname;
    }

    public String getRefNewFieldIname()
    {
        return refNewFieldIname;
    }

    public void setRefRepositoryQuery(String refRepositoryQuery)
    {
        this.refRepositoryQuery = refRepositoryQuery;
    }

    public String getRefRepositoryQuery()
    {
        return refRepositoryQuery;
    }

    public void setAuthor(String author)
    {
        this.author = author;
    }

    public String getAuthor()
    {
        return author;
    }

    public void setRefIsSystem(Number refIsSystem)
    {
        this.refIsSystem = refIsSystem;
    }

    public Number getRefIsSystem()
    {
        return refIsSystem;
    }

    public void setRefContentNameValuePairs(List<LoganSourceFieldRefContent> refContentNameValuePairs)
    {
        this.refContentNameValuePairs = refContentNameValuePairs;
    }

    public List<LoganSourceFieldRefContent> getRefContentNameValuePairs()
    {
        return refContentNameValuePairs;
    }

    public void setRefExtFieldIname(String refExtFieldIname)
    {
        this.refExtFieldIname = refExtFieldIname;
    }

    public String getRefExtFieldIname()
    {
        return refExtFieldIname;
    }

    public void setRefExtFieldDname(String refExtFieldDname)
    {
        this.refExtFieldDname = refExtFieldDname;
    }

    public String getRefExtFieldDname()
    {
        return refExtFieldDname;
    }

    public void setRefNewFieldDname(String refNewFieldDname)
    {
        this.refNewFieldDname = refNewFieldDname;
    }

    public String getRefNewFieldDname()
    {
        return refNewFieldDname;
    }

    public void setRefSeffId(Number refSeffId)
    {
        this.refSeffId = refSeffId;
    }

    public Number getRefSeffId()
    {
        return refSeffId;
    }

    public void setContentDisplayDetailed(String contentDisplayDetailed)
    {
        this.contentDisplayDetailed = contentDisplayDetailed;
    }

    public String getContentDisplayDetailed()
    {
        return contentDisplayDetailed;
    }

    public void setSrefmapRefIname(String srefmapRefIname)
    {
        this.srefmapRefIname = srefmapRefIname;
    }

    public String getSrefmapRefIname()
    {
        return srefmapRefIname;
    }

    public void setRefBaseFieldDname(String refBaseFieldDname)
    {
        this.refBaseFieldDname = refBaseFieldDname;
    }

    public String getRefBaseFieldDname()
    {
        return refBaseFieldDname;
    }

    public void setMoreRows(Integer moreRows)
    {
        this.moreRows = moreRows;
    }

    public Integer getMoreRows()
    {
        return moreRows;
    }

    public void setNewFR(boolean newFR)
    {
        this.newFR = newFR;
    }

    public boolean isNewFR()
    {
        return newFR;
    }

    public void setNvpType(boolean nvpType)
    {
        this.nvpType = nvpType;
    }

    public boolean isNvpType()
    {
        return nvpType;
    }
}
