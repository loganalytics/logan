package oracle.saas.logan.view;

public class LoganLibRulesBean extends AbstractLoganLibBean{
    public LoganLibRulesBean() {
        super();
    }
    
    public boolean isAssocTargetsDisabled()
    {
        // check if table has multiple or no rows selected then disabled is true
        return true;
    } 
    
}
