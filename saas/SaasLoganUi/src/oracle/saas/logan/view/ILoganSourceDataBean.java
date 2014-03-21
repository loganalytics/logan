package oracle.saas.logan.view;


public interface ILoganSourceDataBean {
    
    /**
     * Called before the processSubmit step so that the
     * step data beans can set the final parent references as needed
     * @param modeBean
     */
    public void beforeProcessSubmit(LaunchContextModeBean modeBean);

    /**
     * @param refData
     * @param modeBean
     */
    public void processSubmit(ILoganSourceDataBean refData, LaunchContextModeBean modeBean);
    
    /**
     * Called after the processSubmit step so that the
     * step data beans can set any parent references as needed
     * @param refData
     * @param modeBean
     */
    public void afterProcessSubmit(ILoganSourceDataBean refData, LaunchContextModeBean modeBean);
}
