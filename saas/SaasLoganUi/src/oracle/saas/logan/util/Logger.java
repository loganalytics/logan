package oracle.saas.logan.util;


/**
 * This Class is to replace the sdk Logger and could be used as the later saas Logger agent.
 * Currently in the prototype version we can use Log4j
 * @author ming
 * */
public class Logger  extends org.apache.log4j.Logger {
    protected Logger(String p1) { 
        super(p1);
    }
    
    public static Logger getLogger(java.lang.String p1) {
        org.apache.log4j.Logger instance = org.apache.log4j.Logger.getLogger(p1);
        return  (Logger)instance;
    }

    public static Logger getLogger(java.lang.Class p1) { 
            org.apache.log4j.Logger instance = org.apache.log4j.Logger.getLogger(p1);
            return  (Logger)instance;
    }

    public static Logger getLogger(java.lang.String p1, org.apache.log4j.spi.LoggerFactory p2) {
            org.apache.log4j.Logger instance = org.apache.log4j.Logger.getLogger(p1,p2);
            return  (Logger)instance;
    }

    public static Logger getRootLogger() {
        org.apache.log4j.Logger instance = org.apache.log4j.Logger.getRootLogger();
        return  (Logger)instance;
    }
    
    public boolean isFinestEnabled()
    {
        return super.isDebugEnabled();
    }
    
    public void finest(Object p)
    {
        super.debug(p);
    }
    
    public void finest(Object p,Exception e)
    {
        super.debug(p,e);
    }
    
    public boolean isWarningEnabled()
    {
        return super.isInfoEnabled();
    }
    
    public void warning(Object p)
    {
        super.info(p);
    }
    
    public void warning(Object p,Exception e)
    {
        super.info(p,e);
    }
    
    
    
    
    
    
    
}
