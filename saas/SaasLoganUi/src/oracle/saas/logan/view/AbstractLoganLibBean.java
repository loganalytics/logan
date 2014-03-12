package oracle.saas.logan.view;

public class AbstractLoganLibBean {
    public AbstractLoganLibBean() {
        super();
    }
    
    
    public boolean isCreateLikeDisabled() {
        // check if table has multiple or no rows selected then disabled is true
        return false;
    }
    public boolean isShowDetailsDisabled()
    {
        // check if table has multiple or no rows selected then disabled is true
        return false;
    }

    /**
     * @return
     */
    public boolean isEditDisabled()
    {
        // check if table has multiple or no rows selected then disabled is true
        return false;
    }

    /**
     * @return
     */
    public boolean isDeleteDisabled()
    {
        // check if table has multiple or no rows selected then disabled is true
        return false;
    }   
}
