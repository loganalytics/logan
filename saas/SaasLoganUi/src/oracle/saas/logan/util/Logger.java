package oracle.saas.logan.util;


/**
 * This Class is to replace the emsdk Logger and could be modifed  as the later saas Logger agent.
 * Currently in the prototype version we can use Log4j as the log instance.
 * @author ming
 * */
public class Logger   {

    private Logger() { 
    }

    private org.apache.log4j.Logger logger;
    
    
    public static Logger getLogger(java.lang.String p1) {
        Logger log = new Logger();
        org.apache.log4j.Logger instance = org.apache.log4j.Logger.getLogger(p1);
        log.setLogger(instance);
        return  log;
    }

    public static Logger getLogger(java.lang.Class p1) { 
        Logger log = new Logger();
        org.apache.log4j.Logger instance = org.apache.log4j.Logger.getLogger(p1);
        log.setLogger(instance);
        return  log;
    }

    public static Logger getLogger(java.lang.String p1, org.apache.log4j.spi.LoggerFactory p2) {
        Logger log = new Logger();
        org.apache.log4j.Logger instance = org.apache.log4j.Logger.getLogger(p1,p2);
        log.setLogger(instance);
        return  log;
    }

    public static Logger getRootLogger() {
        Logger log = new Logger();
        org.apache.log4j.Logger instance = org.apache.log4j.Logger.getRootLogger();
        log.setLogger(instance);
        return  log;
    }
    
    public boolean isFinestEnabled()
    {
        return logger.isDebugEnabled();
    }
    
    public void finest(Object p)
    {
        logger.debug(p);
    }
    
    public void finest(Object p,Exception e)
    {
        logger.debug(p,e);
    }
    
    public boolean isFineEnabled()
    {
        return logger.isInfoEnabled();
    }
    
    public void fine(Object p)
    {
        logger.info(p);
    }
    
    public void fine(Object p,Exception e)
    {
        logger.info(p,e);
    }
    
  
    public boolean isWarningEnabled()
    {
        return logger.isInfoEnabled();
    }
    
    public void warning(Object p)
    {
        logger.info(p);
    }
    
    public void warning(Object p,Exception e)
    {
        logger.info(p,e);
    }
    
    private  void setLogger(org.apache.log4j.Logger logger) {
        this.logger = logger;
    }

    private org.apache.log4j.Logger getLogger() {
        return logger;
    }
    
    
    
    
    
}
