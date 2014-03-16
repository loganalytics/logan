package oracle.saas.logan.view.rule;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import oracle.jbo.domain.Number;

public class LoganRuleParamOverride implements Cloneable
{
    private String paramName;
    private String paramOverrideValue;
    
    private boolean builtInParam;
    private String paramValue;
    
    private Map<Number, String> defaultValueForSourceID =
        new HashMap<Number, String>();
    
    public LoganRuleParamOverride(String paramName,
                                  String paramOverrideValue,
                                  int builtIn,
                                  String paramValue)
    {
        this.paramName = paramName;
        this.paramOverrideValue = paramOverrideValue;
        
        this.builtInParam = false;
        if(builtIn == 1)
            this.builtInParam = true;
        
        this.paramValue = paramValue;
    }
    
    public String getPrimaryKeyAsString()
    {
        StringBuilder sb = new StringBuilder();
        sb.append(this.paramName).append("_");
        sb.append(this.paramOverrideValue).append("_");
        return sb.toString();
    }
    
    public boolean equals(Object o)
    {
        if(!(o instanceof LoganRuleParamOverride))
            return false;
        LoganRuleParamOverride that = (LoganRuleParamOverride)o;
        return this.getPrimaryKeyAsString().equals(that.getPrimaryKeyAsString());
    }
    
    public int hashCode()
    {
        return getPrimaryKeyAsString().hashCode();
    }
    
    public Object clone()
    {
        LoganRuleParamOverride c = 
            new LoganRuleParamOverride(paramName,
                                       paramOverrideValue,
                                       (builtInParam ? 1 : 0),
                                       paramValue);
        if(this.defaultValueForSourceID != null && 
            this.defaultValueForSourceID.size() > 0)
        {
            Iterator<Number> siter = this.getSourceIdDefaultValueMapIter();
            while(siter.hasNext())
            {
                Number nsrc = new Number(siter.next());
                c.addDefaultValueSourceIdPairToMap(nsrc,
                    this.defaultValueForSourceID.get(nsrc));
            }
        }
        return c;
    }

    public void setParamName(String paramName)
    {
        this.paramName = paramName;
    }

    public String getParamName()
    {
        return paramName;
    }

    public void setParamOverrideValue(String paramOverrideValue)
    {
        this.paramOverrideValue = paramOverrideValue;
    }

    public String getParamOverrideValue()
    {
        return paramOverrideValue;
    }

    public void setBuiltInParam(boolean builtInParam)
    {
        this.builtInParam = builtInParam;
    }

    public boolean isBuiltInParam()
    {
        return builtInParam;
    }

    public void setParamValue(String paramValue)
    {
        this.paramValue = paramValue;
    }

    public String getParamValue()
    {
        return paramValue;
    }
    
    public void addDefaultValueSourceIdPairToMap(Number srcId, String defaultVal)
    {
        defaultValueForSourceID.put(srcId, defaultVal);
    }
    
    public Iterator<Number> getSourceIdDefaultValueMapIter()
    {
        return defaultValueForSourceID.keySet().iterator();
    }
    
    public String getDefaultValueForSourceId(Number srcId)
    {
        return defaultValueForSourceID.get(srcId);
    }
    
    public Number getFirstSourceId()
    {
        Number srcId = null;
        Iterator<Number> siter = getSourceIdDefaultValueMapIter();
        if(siter.hasNext())
        {
            srcId = siter.next();
        }
        return srcId;
    }
}