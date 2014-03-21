package oracle.saas.logan.util;


import java.sql.Connection;
import java.sql.SQLException;

import java.text.MessageFormat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public final class LoganUtil
{
    private static Logger s_logger = Logger.getLogger(LoganUtil.class);
    private static final String LOGAN_RESOURCE_BUNDLE = 
                       "oracle.sysman.core.loganaltics.model.util.LoganResourceBundleMsg";
  
    private static final String RegexToMatchCurlyBraces =
        "(?i)(\\{)(.+?)(\\})";
    private static final String ESC_CURLY_OPEN =
        "_E_S_C__C_U_R_L_Y__O_P_E_N_";
    private static final String ESC_CURLY_CLOSE =
        "_E_S_C__C_U_R_L_Y__C_L_O_S_E_";
    
    private static final String SAVED_REGEXP_USAGE_IDENTIFIER = ":@";
    private static final String PLAIN_REGEXP_USAGE_IDENTIFIER = ":";

  /**
   * Return the name of the Logan resource bundle.
   *
   * @return the Loganalytics resource bundle name.
   */
  public static String getLoganResourceBundle() 
  {
    return LOGAN_RESOURCE_BUNDLE;
  }

 /**
   * obtain the NLS message for the specified key in the resource bundle.
   *
   * @param key the key for value lookup in the bundle.
   *
   * @return the value for the specified key.
   */
  public static String getNLSString(String key) 
  {
    return LoganUtil.formatNLSString(getClientLocale(), LoganUtil.getLoganResourceBundle(), key, null);
  }
  
  /**
   * obtain the NLS message for the specified bundle key and message arguments.
   *
   * @param key the key for value lookup in the bundle.
   * @param args the arguments to insert into the message
   *
   * @return the value for the specified key.
   */
  public static String formatNLSString(String key, Object[]  args) 
  {
    return LoganUtil.formatNLSString(getClientLocale(), LoganUtil.getLoganResourceBundle(), key, args);
  }
  

  /**
   * obtain the NLS message for the specified information.
   *
   * @param locale the client locale
   * @param bundleName the name of the resource bundle
   * @param key the key for value lookup in the bundle.
   * @param args the arguments to insert into the message
   *
   * @return the translated and formatted message.
   */
  public static String formatNLSString(Locale locale, String bundleName, 
                                    String key, Object[]  args) 
  {
    String rtnStr = null;
    ResourceBundle msgs = ResourceBundle.getBundle(bundleName, locale);
    try
    {
        rtnStr = msgs.getString(key);
        if (args != null)
        {
            MessageFormat msgFormat = new MessageFormat(rtnStr);
            rtnStr = msgFormat.format(args);
        }

    }
    catch (Exception e)
    {
        if (s_logger.isFinestEnabled())
        {
            s_logger.finest("Error translating " + bundleName + "<" + key 
                       + ">" + e.getMessage());
        }
        rtnStr = key;
        if (rtnStr == null)
        {
            rtnStr = e.getMessage();
        }
    }        
    return rtnStr;
  }

  /**
   * obtain the client locale using the NLS utility method.  If it fails to 
   * return a valid locale, then use the default.
   *
   * @param key the key for value lookup in the bundle.
   *
   * @return the value for the specified key.
   */
  public static Locale getClientLocale() 
  {
      //TODO saas
//    Locale locale = NLSUtil.getClientLocale();
     Locale locale  =  Locale.getDefault();
    if (locale == null)
    {
      locale = Locale.getDefault();
    }
    return locale;
  }
  public static int transBooleanDef(String booleanDef){
      int result = 0;
      
      if("true".equalsIgnoreCase(booleanDef)||"TRUE".equalsIgnoreCase(booleanDef)||"1".equalsIgnoreCase(booleanDef)||"Y".equalsIgnoreCase(booleanDef)){
          result = 1;
      }
      
      return result;
  }
  
    public static boolean isBooleanValid(String booleanDef){

        if ("true".equalsIgnoreCase(booleanDef) ||
            "TRUE".equalsIgnoreCase(booleanDef) ||
            "1".equalsIgnoreCase(booleanDef) ||
            "Y".equalsIgnoreCase(booleanDef) ||
            "false".equalsIgnoreCase(booleanDef) ||
            "FALSE".equalsIgnoreCase(booleanDef) ||
            "0".equalsIgnoreCase(booleanDef) ||
            "N".equalsIgnoreCase(booleanDef))
        {
            return true;
        }
        
        return false;
    }
    
    public static List<String> getAllPatternParameters(String patternvalue)
    {
        List<String> pattern_parameters = new ArrayList<String>();
        if(patternvalue == null || patternvalue.trim().length() == 0)
            return pattern_parameters;
        //extract all parameters that are being used in pattern
        patternvalue =
                patternvalue.replaceAll("\\{\\{\\}", "");
        patternvalue =
                patternvalue.replaceAll("\\{\\}\\}", "");
        String lparen = "{";
        String rparen = "}";
        Pattern p =
            Pattern.compile("\\{(.*)\\}", Pattern.DOTALL);

        if (patternvalue.indexOf(lparen) != -1)
        {
            String[] splitparen = patternvalue.split("\\{");
            int count = splitparen.length;

            for (int i = 0; i < count; i++)
            {
                if (splitparen[i].indexOf(rparen) > 0)
                {
                    String toMatch =
                        lparen + splitparen[i].substring(0,
                                                         splitparen[i].indexOf(rparen) +
                                                         1);
                    Matcher matcher = p.matcher(toMatch);
                    if (matcher.matches())
                    {
                        // add this to parameters table row
                        if (!pattern_parameters.contains(matcher.group(1)))
                        {
                            pattern_parameters.add(matcher.group(1));
                        }
                    }
                }
            }
        }
        return pattern_parameters;
    }
    
    /**
     * Parses the regex expressionstring and returns string representing the regex
     * which used check if regex matches sample text
     * 
     * @param conn
     * @param regex
     * @return List of String objects
     */
    public static String getRegexFromRegexExpr(Connection conn, String regex) throws SQLException {
        //TODO saas
//        List<String> fields = getFieldsEnclosedByCurlyBraces(regex);
//        
//        for (String field: fields)
//        {
//            String regexName = null;
//            String regexContent = null;
//            int idxOfColonAt = field.indexOf(SAVED_REGEXP_USAGE_IDENTIFIER);
//            if(idxOfColonAt > 0)
//            {
//                if(field.lastIndexOf(SAVED_REGEXP_USAGE_IDENTIFIER) == idxOfColonAt)
//                {
//                    // if the field contains a SAVED REGEXP
//                    regexName = field.substring(idxOfColonAt+2);
//                    regexContent = LoganSQLUtil.getRegexContentByName(conn, regexName);
//                }
//            }
//            else
//            {
//                int idxOfColonOnly = field.indexOf(PLAIN_REGEXP_USAGE_IDENTIFIER);
//                if(idxOfColonOnly > 0 &&
//                    field.lastIndexOf(PLAIN_REGEXP_USAGE_IDENTIFIER) == idxOfColonOnly)
//                {
//                    // if the field contains a SAVED REGEXP
//                    regexContent = field.substring(idxOfColonOnly+1);
//                }
//            }
//            
//            regex = regex.replace("{" + field + "}", regexContent);
//  
//        }
        
        return regex;
    }
    
    
    /**
     * Parses the regex string and returns a list of strings representing the
     * fields (only field name) which have been enclosed within curly braces
     * 
     * @param regex
     * @return List of String objects
     */
    public static  List<String> getFieldsFromRegex(String regex) {
        List<String> fieldStrs = getFieldsEnclosedByCurlyBraces(regex);
        List<String> fieldNames =  new ArrayList<String>();
        if (fieldStrs != null && fieldStrs.size() > 0)
        {
            for (String fieldName: fieldStrs)
            {
                int idxOfColonAt = fieldName.indexOf(SAVED_REGEXP_USAGE_IDENTIFIER);
                if(idxOfColonAt > 0)
                {
                    if(fieldName.lastIndexOf(SAVED_REGEXP_USAGE_IDENTIFIER) == idxOfColonAt)
                    {
                        // if the field contains a SAVED REGEXP
                        fieldName = fieldName.substring(0, idxOfColonAt);
                    }
                }
                else
                {
                    int idxOfColonOnly = fieldName.indexOf(PLAIN_REGEXP_USAGE_IDENTIFIER);
                    if(idxOfColonOnly > 0 &&
                        fieldName.lastIndexOf(PLAIN_REGEXP_USAGE_IDENTIFIER) == idxOfColonOnly)
                    {
                        // if the field contains a SAVED REGEXP
                        fieldName = fieldName.substring(0, idxOfColonOnly);
                    }
                }
                
                fieldNames.add(fieldName);
            }
        }
        
        return fieldNames;
    }
    
    /**
     * Parses the input string and returns a list of strings representing the
     * fields (if any) which have been enclosed within curly braces<p>
     * Ex. 1<br>
     *   input = "sample string with {FIELD1} and {FIELD2}";<br>
     *   output will be a List of 2 String elements "FIELD1" and "FIELD2"<br><br>
     * Ex. 2<br>
     *   input = "[a-zA-Z]+ some message {REAL_FIELD} which might have {{} or {}}";<br>
     *   output will be a List of 1 String element "REAL_FIELD" only<br>
     * <br>
     * @param input
     * @return List of String objects
     */
    public static List<String> getFieldsEnclosedByCurlyBraces(String input)
    {
        validateCurlyBracesInExpr(input);
        // take care of the special {{} and {}} and \{ and \}
        input = replaceSplCharsInExpr(input);

        Pattern p = Pattern.compile(RegexToMatchCurlyBraces);
        Matcher m = p.matcher(input);
        Map<String, String> fieldMap = new HashMap<String, String>();
        List<String> fields = new ArrayList<String>();
        while (m.find())
        {
            String matchedExp = m.group(2);
            matchedExp = matchedExp.replaceAll(ESC_CURLY_OPEN, "\\{");
            matchedExp = matchedExp.replaceAll(ESC_CURLY_CLOSE, "\\}");
            // check if the curly braces have been escaped by curly braces
            // if the curly braces "{" or "}" are part of the text of the
            // string expression and are not supposed to generate a FIELD then
            // the users are expected to escape those using curly braces like:
            //
            //    [a-zA-Z]+ some message {REAL_FIELD} which might have {{} or {}}
            //
            // In the above expression the only field generated would be the
            // REAL_FIELD and the curly braces will be skipped
            //
            if (!"{".equals(matchedExp) && !"}".equals(matchedExp))
            {
                if (!fieldMap.containsValue(matchedExp))
                {
                    fields.add(matchedExp);
                    fieldMap.put(matchedExp, "Y");
                }
            }
        }
        if (fields.size() == 0)
        {
            s_logger.finest("Did not find any field match");
            fields = null;
        }
        else
        {
            s_logger.finest("Found a total of " + fields.size() +
                         " matches.");
        }
        return fields;
    }
    
    /**
     * Validates if the expressions String passed here conforms to
     * a valid syntax for embedded field references between { and }
     * <p>
     * - It will return true if no such references are found<br>
     * - It will return true if expression contains fields in the correct syntax<br>
     * <p>
     * Valid syntax: <br>
     * &nbsp;&nbsp; "some text with no curly braces is valid"<br>
     * &nbsp;&nbsp; "valid text or regular expr {FIELD1} and {FIELD2}"<br>
     * &nbsp;&nbsp; "{FIELD1@SAVED_EXP} : {FIELD2} valid synt"<br>
     * &nbsp;&nbsp; "normal string with escaped curly {{} and {}} valid"<br>
     * &nbsp;&nbsp; "valid mixed string {WITH} escaped curly {{} braces"<br>
     * <p>
     * Invalid Expressions: <br>
     * &nbsp;&nbsp; "example of unmatched { open curly in expr {FIELD1} and {FIELD2}"<br>
     * &nbsp;&nbsp; "example } of unmatched closed curly } in  {FIELD2} synt"<br>
     * &nbsp;&nbsp; "seemingly valid {{FIELD1}:{FIELD2}} which is" invalid since:<br>
     * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; Unmatched Open curly { not closed before opening another {<br>
     *
     * @param input
     * @return true if expression syntax is valid
     */
    public static boolean validateCurlyBracesInExpr(String input)
    {
        boolean validsyntax = true;
        if (input == null || input.trim().length() == 0)
            return validsyntax;

        // no curly brackets
        if (input.indexOf('{') < 0 && input.indexOf('}') < 0)
            return validsyntax;

        // take care of the special {{} and {}} and \{ and \}


        //      Is \ an escape char for Pattern Name?  Not so in CCC?
        //      we are only checking esc {{} and {}}
        //      not sure it is necessary to check matching for patterns?
        //      KC : check this with Vivek

        input = replaceSplCharsInExpr(input);
        char data[] = input.toCharArray();
        int openCurlyFirstIndex = -1;

        int closeCurlyFirstIndex = -1;
        int openCurlyCount = 0;
        int closeCurlyCount = 0;
        int currOpenedCurlyCount = 0;
        for (int i = 0; i < data.length; i++)
        {
            if ('{' == data[i])
            {
                if (openCurlyFirstIndex == -1)
                    openCurlyFirstIndex = i;
                openCurlyCount++;
                if (currOpenedCurlyCount >= 1)
                {
                    validsyntax = false;
                    break;
                }
                currOpenedCurlyCount++;
            }
            else if ('}' == data[i])
            {
                if (currOpenedCurlyCount < 1)
                {
                    validsyntax = false;
                    break;
                }
                currOpenedCurlyCount--;
                if (closeCurlyFirstIndex == -1)
                    closeCurlyFirstIndex = i;
                closeCurlyCount++;
            }
        }
        if (openCurlyCount != closeCurlyCount)
        {
            Object[] args =
            { Integer.toString(openCurlyCount),
              Integer.toString(closeCurlyCount) };
            
            validsyntax = false;
        }
        return validsyntax;
    }
    
    private static String replaceSplCharsInExpr(String input)
    {
        if (input == null)
            return null;
        char[] regex = input.toCharArray();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < regex.length; i++)
        {
            char ch = regex[i];
            if (ch == '\\') // escape encountered
            {
                ++i;
                if (i < regex.length)
                {
                    ch = regex[i]; // look at the immediate next char
                    if (!(ch == '{' ||
                          ch == '}')) // else remove escaped part
                    {
                        sb.append('\\').append(ch);
                    }
                    else
                    {
                        if (ch == '{')
                            sb.append(ESC_CURLY_OPEN);
                        else
                            sb.append(ESC_CURLY_CLOSE);
                    }
                }
            }
            else if (ch == '{')
            {
                ++i;
                if (i < regex.length)
                {
                    ch = regex[i]; // look at the immediate next char
                    if (!(ch == '{' ||
                          ch == '}')) // some other regular char
                    {
                        sb.append('{').append(ch);
                    }
                    else // encountered a '{' followed by a '{' or '}'
                    {
                        ++i;
                        if (i < regex.length)
                        {
                            char char2 =
                                regex[i]; // look at the immediate next char
                            if (char2 !=
                                '}') // else escape seq found -> chars '{{}' or '{}}'
                            {
                                sb.append('{').append(ch).append(char2);
                            }
                        }
                    }
                }
            }
            else
            {
                sb.append(ch);
            }
        }
        return sb.toString();
    }
  
}