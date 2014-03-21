package oracle.saas.logan.view.rule;

import java.util.List;
import java.util.logging.Logger;

import oracle.jbo.Row;
import oracle.jbo.domain.Number;

import oracle.saas.logan.view.BaseDataPersistenceHandler;
import oracle.saas.logan.view.KeyValuePairObj;
import oracle.saas.logan.view.source.LoganLibSourcePojo;

public class LoganRuleSource extends BaseDataPersistenceHandler
    implements Cloneable
{
    private static final Logger s_log =
        Logger.getLogger(LoganRuleSource.class.getName());

    private static final String RsRuleId = "RsRuleId";
    private static final String RsSourceId = "RsSourceId";
    private static final String SourceDname = "SourceDname";
    private static final String SourceDescription = "SourceDescription";
    private static final String PatternCount = "PatternCount";

    private Number ruleId;
    private Number sourceId;
    private String sourceName;
    private String sourceDesc;
    private Number patternCount;

    private String[] parentRefKeyNames =
    { RsRuleId };

    private static final String ParentFK_RuleId = "RuleId";

    public LoganRuleSource(Number ruleId, Number sourceId,
                           String sourceName, String sourceDesc,
                           Number patternCount)
    {
        super();
        this.ruleId = ruleId;
        this.sourceId = sourceId;
        this.sourceName = sourceName;
        this.sourceDesc = sourceDesc;
        this.patternCount = patternCount;
    }

    public LoganRuleSource(Row dRow)
    {
        super();
        if (dRow != null)
        {
            ruleId = (Number) dRow.getAttribute(RsRuleId);
            sourceId = (Number) dRow.getAttribute(RsSourceId);
            sourceName = (String) dRow.getAttribute(SourceDname);
            sourceDesc = (String) dRow.getAttribute(SourceDescription);
            patternCount = (Number) dRow.getAttribute(PatternCount);
        }
    }

    public LoganRuleSource(Number ruleId, LoganLibSourcePojo bean)
    {
        if (bean != null)
        {
            this.ruleId = ruleId;
            sourceId = new Number(bean.getSourceId().intValue());
            sourceName = bean.getSourceDname();
            sourceDesc = bean.getSourceDescription();
            patternCount = new Number(bean.getInclFilePatternsCount());
        }
    }

    public Object clone()
    {
        return new LoganRuleSource(ruleId, sourceId, sourceName,
                                   sourceDesc, patternCount);
    }

    public void setRuleId(Number ruleId)
    {
        this.ruleId = ruleId;
    }

    public Number getRuleId()
    {
        return ruleId;
    }

    public void setSourceId(Number sourceId)
    {
        this.sourceId = sourceId;
    }

    public Number getSourceId()
    {
        return sourceId;
    }

    public void setSourceName(String sourceName)
    {
        this.sourceName = sourceName;
    }

    public String getSourceName()
    {
        return sourceName;
    }

    public void setPatternCount(Number patternCount)
    {
        this.patternCount = patternCount;
    }

    public Number getPatternCount()
    {
        return patternCount;
    }

    public void setSourceDesc(String sourceDesc)
    {
        this.sourceDesc = sourceDesc;
    }

    public String getSourceDesc()
    {
        return sourceDesc;
    }

    public boolean isObjInstanceOfSameClass(Object o)
    {
        return (o != null && o instanceof LoganRuleSource);
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
        sb.append(this.sourceId).append("_");
        sb.append(this.sourceName).append("_");
        return sb.toString();
    }

    /**
     * @param o
     * @return
     */
    public int compareTo(LoganRuleSource o)
    {
        if (this.sourceName == null)
            return (o.sourceName == null? 0: -1);
        if (o.sourceName == null)
            return 1;
        return this.sourceName.trim().compareTo(o.sourceName.trim());
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
        sb.append(this.sourceId).append("_");
        return sb.toString();
    }

    public void handleInsertOperation()
    {
//        ViewObjectImpl rsVO = getViewObjectImpl();
//        NameValuePairs nvpmm = new NameValuePairs();
//
//        nvpmm.setAttribute(RsRuleId, ruleId);
//        nvpmm.setAttribute(RsSourceId, sourceId);
//
//        Row iRow = rsVO.createAndInitRow(nvpmm);
//        rsVO.insertRow(iRow);
//
//        if (s_log.isFinestEnabled())
//        {
//            StringBuilder sb = new StringBuilder();
//            sb.append("RULE SOURCE BEAN  created a row to INSERT for ruleId " +
//                      ruleId);
//            sb.append(", sourceId = " + sourceId);
//            s_log.finest(sb.toString());
//        }
    }

    public void handleUpdateOperation(){
            s_log.finest("RULE SOURCE has only PKs exposed on UI so any change there is DELETE INSERT - no updates");
    }

    public void handleDeleteOperation()
    {
//        Object values[] =
//        { ruleId, sourceId };
//        Key key = new Key(values);
//        Row[] dRows = getViewObjectImpl().findByKey(key, 1);
//
//        if (dRows != null && dRows.length > 0)
//        {
//            Row dRow = dRows[0];
//
//            if (s_log.isFinestEnabled())
//            {
//                StringBuilder sb = new StringBuilder();
//                sb.append("RULE SOURCE BEAN  created a row to DELETE for ruleId " +
//                          ruleId);
//                sb.append(", sourceId = " + sourceId);
//                s_log.finest(sb.toString());
//            }
//            dRow.remove();
//        }
    }

//    private ViewObjectImpl getViewObjectImpl()
//    {
//        LoganAMImpl am = LoganLibUiUtil.getAppModule();
//        ViewObjectImpl vo = am.getEmLoganRuleSourceMapVO();
//        return vo;
//    }
//
//    public List<KeyValuePairObj> getReferenceKeysForDependents()
//    {
//        List<KeyValuePairObj> rks = new ArrayList<KeyValuePairObj>(1);
//        rks.add(new KeyValuePairObj(RsRuleId, this.ruleId));
//        return rks;
//    }
}
