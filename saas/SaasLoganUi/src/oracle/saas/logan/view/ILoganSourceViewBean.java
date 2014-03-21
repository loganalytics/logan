package oracle.saas.logan.view;


public interface ILoganSourceViewBean {
    
    /**
     * This will validate the data entered by the user for the source.
     * 
     * This method is also supposed to update the error fields if any
     * to be able to show the error messages on that train step
     * 
     * @return true if no errors in data validation else false
     */
    public boolean validateViewBeanData();

    /**
     * This will validate the data entered by the user for the source and
     * if validation is successful, insert the data in appropriate tables
     * and return true or false based on the success or failure of all
     * inserts.
     * 
     * @return true if create successful else false
     */
    public boolean createSource();
}
