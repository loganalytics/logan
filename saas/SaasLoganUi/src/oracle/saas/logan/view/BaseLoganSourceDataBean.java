package oracle.saas.logan.view;


public abstract class BaseLoganSourceDataBean implements ILoganSourceDataBean {
    
    public BaseLoganSourceDataBean(){
        super();
    }
    
    /**
     * override if edit for the data is not allowed
     * @return
     */
    public boolean isDataEditAllowed()
    {
        return true;
    }

    /**
     * Called before the processSubmit step so that the
     * step data beans can set the final parent references as needed
     * @param modeBean
     */
    public void beforeProcessSubmit(LaunchContextModeBean modeBean) {
        return; // DO NOTHING
    }

    /**
     * @param refData
     * @param modeBean
     */
    public void processSubmit(ILoganSourceDataBean refData,
                              LaunchContextModeBean modeBean) {
        return; // DO NOTHING
    }

    /**
     * Called after the processSubmit step so that the
     * step data beans can set any parent references as needed
     * @param refData
     * @param modeBean
     */
    public void afterProcessSubmit(ILoganSourceDataBean refData,
                                   LaunchContextModeBean modeBean) {
        return; // DO NOTHING
    }
}
