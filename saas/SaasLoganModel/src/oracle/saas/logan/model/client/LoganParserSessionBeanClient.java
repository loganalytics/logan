package oracle.saas.logan.model.client;

import java.util.Hashtable;
import java.util.List;

import javax.naming.CommunicationException;
import javax.naming.Context;
import javax.naming.InitialContext;

import javax.naming.NamingException;

import oracle.saas.logan.model.persistance.EmLoganCommonFields;
import oracle.saas.logan.model.persistance.EmLoganParser;
import oracle.saas.logan.model.session.parser.LoganParserSessionBean;

public class LoganParserSessionBeanClient {
    public static void main(String[] args) {
        try {
            final Context context = getInitialContext();
            LoganParserSessionBean loganParserSessionBean =
                (LoganParserSessionBean) context.lookup("saas-SaasLoganModel-LoganParserSessionBean#oracle.saas.logan.model.session.parser.LoganParserSessionBean");
            for (EmLoganParser emloganparser : (List<EmLoganParser>) loganParserSessionBean.getEmLoganParserFindAll()) {
                printEmLoganParser(emloganparser);
            }
            for (EmLoganCommonFields emlogancommonfields :
                 (List<EmLoganCommonFields>) loganParserSessionBean.getEmLoganCommonFieldsFindAll()) {
                printEmLoganCommonFields(emlogancommonfields);
            }
        } catch (CommunicationException ex) {
            System.out.println(ex.getClass().getName());
            System.out.println(ex.getRootCause().getLocalizedMessage());
            System.out.println("\n*** A CommunicationException was raised.  This typically\n*** occurs when the target WebLogic server is not running.\n");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private static void printEmLoganParser(EmLoganParser emloganparser) {
        System.out.println("parserAuthor = " + emloganparser.getParserAuthor());
        System.out.println("parserContent = " + emloganparser.getParserContent());
        System.out.println("parserCriticalEditVersion = " + emloganparser.getParserCriticalEditVersion());
        System.out.println("parserDescription = " + emloganparser.getParserDescription());
        System.out.println("parserDescriptionNlsid = " + emloganparser.getParserDescriptionNlsid());
        System.out.println("parserDname = " + emloganparser.getParserDname());
        System.out.println("parserDnameNlsid = " + emloganparser.getParserDnameNlsid());
        System.out.println("parserEditVersion = " + emloganparser.getParserEditVersion());
        System.out.println("parserEncoding = " + emloganparser.getParserEncoding());
        System.out.println("parserExampleContent = " + emloganparser.getParserExampleContent());
        System.out.println("parserHeaderContent = " + emloganparser.getParserHeaderContent());
        System.out.println("parserId = " + emloganparser.getParserId());
        System.out.println("parserIname = " + emloganparser.getParserIname());
        System.out.println("parserIsSingleLineContent = " + emloganparser.getParserIsSingleLineContent());
        System.out.println("parserIsSystem = " + emloganparser.getParserIsSystem());
        System.out.println("parserLanguage = " + emloganparser.getParserLanguage());
        System.out.println("parserType = " + emloganparser.getParserType());
        System.out.println("emLoganParserFieldMapList1 = " + emloganparser.getEmLoganParserFieldMapList1());
    }

    private static void printEmLoganCommonFields(EmLoganCommonFields emlogancommonfields) {
        System.out.println("fieldAuthor = " + emlogancommonfields.getFieldAuthor());
        System.out.println("fieldCeeDname = " + emlogancommonfields.getFieldCeeDname());
        System.out.println("fieldDatatype = " + emlogancommonfields.getFieldDatatype());
        System.out.println("fieldDbDatatype = " + emlogancommonfields.getFieldDbDatatype());
        System.out.println("fieldDescription = " + emlogancommonfields.getFieldDescription());
        System.out.println("fieldDescriptionNlsid = " + emlogancommonfields.getFieldDescriptionNlsid());
        System.out.println("fieldDimKeyCol = " + emlogancommonfields.getFieldDimKeyCol());
        System.out.println("fieldDimRefCol = " + emlogancommonfields.getFieldDimRefCol());
        System.out.println("fieldDimSeq = " + emlogancommonfields.getFieldDimSeq());
        System.out.println("fieldDimTabName = " + emlogancommonfields.getFieldDimTabName());
        System.out.println("fieldDname = " + emlogancommonfields.getFieldDname());
        System.out.println("fieldDnameNlsid = " + emlogancommonfields.getFieldDnameNlsid());
        System.out.println("fieldEditVersion = " + emlogancommonfields.getFieldEditVersion());
        System.out.println("fieldFactColOffset = " + emlogancommonfields.getFieldFactColOffset());
        System.out.println("fieldFactTabName = " + emlogancommonfields.getFieldFactTabName());
        System.out.println("fieldFieldtype = " + emlogancommonfields.getFieldFieldtype());
        System.out.println("fieldIname = " + emlogancommonfields.getFieldIname());
        System.out.println("fieldIsPrimary = " + emlogancommonfields.getFieldIsPrimary());
        System.out.println("fieldIsSystem = " + emlogancommonfields.getFieldIsSystem());
        System.out.println("fieldMaxsize = " + emlogancommonfields.getFieldMaxsize());
        System.out.println("fieldMetricKeyEligible = " + emlogancommonfields.getFieldMetricKeyEligible());
        System.out.println("fieldMetricValueEligible = " + emlogancommonfields.getFieldMetricValueEligible());
        System.out.println("fieldObsFieldName = " + emlogancommonfields.getFieldObsFieldName());
        System.out.println("emLoganParserFieldMapList = " + emlogancommonfields.getEmLoganParserFieldMapList());
    }

    private static Context getInitialContext() throws NamingException {
        Hashtable env = new Hashtable();
        // WebLogic Server 10.x/12.x connection details
        env.put(Context.INITIAL_CONTEXT_FACTORY, "weblogic.jndi.WLInitialContextFactory");
        env.put(Context.PROVIDER_URL, "t3://127.0.0.1:7101");
        return new InitialContext(env);
    }
}
