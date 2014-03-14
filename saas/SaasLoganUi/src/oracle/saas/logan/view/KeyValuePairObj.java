package oracle.saas.logan.view;

public class KeyValuePairObj
{
    private String key;
    private Object value;
    
    public KeyValuePairObj(String key, Object value)
    {
        this.key = key;
        this.value = value;
    }

    /**
     * @param key
     */
    public void setKey(String key)
    {
        this.key = key;
    }

    /**
     * @return
     */
    public String getKey()
    {
        return key;
    }

    /**
     * @param value
     */
    public void setValue(Object value)
    {
        this.value = value;
    }

    /**
     * @return
     */
    public Object getValue()
    {
        return value;
    }
    
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        sb.append("Key =`").append(key).append("`");
        sb.append(" : Value =`").append(value).append("`");
        return sb.toString();
    }
}
