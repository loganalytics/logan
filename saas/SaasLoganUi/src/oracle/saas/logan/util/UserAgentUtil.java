/* $Header: emcore/source/oracle/sysman/emSDK/util/http/UserAgentUtil.java /main/27 2012/07/11 11:07:55 wkeicher Exp $ */

/* Copyright (c) 2002, 2012, Oracle and/or its affiliates. 
All rights reserved. */

/*
   DESCRIPTION
    <short description of component this file declares/defines>

   PRIVATE CLASSES
    <list of private classes defined - with one-line descriptions>

   NOTES
    <other useful comments, qualifications, etc.>

   MODIFIED    (MM/DD/YY)
    sbhagwat    02/09/11 - Have specific user agent for emctl
    achyan      04/28/09 - port over some AS11 code
    bhupchan    09/01/08 - Adding isUserAgentEmCli
    vivsharm    05/02/06 - XbranchMerge ssinvhal_bug-4970400 from main
    ssinvhal    01/31/06 - null check
    asaraswa    07/21/05 - adding support for JVMs
    yaofeng     06/17/05 - new broswer support
    lgloyd      09/12/03 - add browswerTimezoneOffset
    hsu         07/20/03 - mozilla
    yaofeng     04/25/03 - ignore platforms
    xshen       04/09/03 - check null request
    xshen       03/27/03 -
    xshen       03/26/03 - support pocket pc browser type
    ancheng     03/20/03 - add function
    yaofeng     03/04/03 - support Netscape 7 on Win 98
    aholser     10/30/02 - add one more case for NT
    yaofeng     10/11/02 - support regression test
    lyang       10/08/02 - lyang_move_browser_version_to_sdk
    lyang       09/29/02 - Creation
 */

/**
 *  @version $Header: emcore/source/oracle/sysman/emSDK/util/http/UserAgentUtil.java /main/27 2012/07/11 11:07:55 wkeicher Exp $
 *  @author  lyang
 *  @since   release specific (what release of product did this appear in)
 */
package oracle.saas.logan.util;

import java.util.HashMap;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


/**
 * A utility class related to user agent, such as browser.
 */
public class UserAgentUtil {
    private static final Logger s_log = Logger.getLogger(UserAgentUtil.class.getName());
    public static final String MSIE = "MSIE";
    public static final String NETSCAPE = "MOZILLA";
    public static final String NETSCAPE_ = "NETSCAPE";
    public static final String NETSCAPE6 = "NETSCAPE6";
    public static final String FIREFOX = "FIREFOX";
    public static final String SAFARI = "SAFARI";

    public static final String MAC = "MAC";
    public static final String SUN = "SUNOS";
    public static final String WIN98 = "WINDOWS 98";
    public static final String WIN98_2 = "WIN98";
    public static final String WINNT = "WINNT";
    public static final String WINNT2 = "WINDOWS NT 4.0";
    public static final String WINNT3 = "WINDOWS NT";
    public static final String WIN2K = "WINDOWS NT 5.0";
    public static final String WINXP = "WINDOWS NT 5.1";

    private static final String BROWSER_NAME = "BROWSER_NAME";
    private static final String BROWSER_VERSION = "BROWSER_VERSION";
    private static final String BROWSER_OS = "BROWSER_OS";

    //--- User Agent for Pocket PC ----------------------------
    private static final String WINCE = "WINDOWS CE";

    public static final String BROWSER_TIMEZONE_OFFSET =
        "browserTimezoneOffset";

    //User agent for emctl
    public static final String EMCTL_USER_AGENT = "EMCTL COMMAND: ";
    
    //HTTP Header name for the mobile app agent
    //this is also used as a key on the session for caching mobile app agent
    public static final String EM_MOBILE_APP_AGENT = "EM-Mobile-App-Agent";

    //Specifically add a known user agent to warm up else Trinidad gives a null pointer exception(PS3) while fetching autoComplete for unknown agent.
    public static final String EM_ADF_WARMUP_USER_AGENT =
        "Mozilla/5.0 (Windows; U; Windows NT 6.1; ru; rv:1.9.2b5) Gecko/20091204 Firefox/3.6b5";

    private static Boolean desktopMobileTesting = null;

    /**
     * Pure utility class.
     */
    private UserAgentUtil() {
    }


    /**
     * EM follows iAS to support Netscape 4.78, 4.79, 7,
     * IE 5.5 SP1 and IE 6 across platforms
     *
     * Windows CE(PPC)       MSIE              3.02
     *
     * @param request the http request from the user agent.
     * @return if it is compatible with the server.
     */
    public static boolean isUserAgentCompatible(HttpServletRequest request) {
        boolean value = false;

        String clientData = request.getHeader("user-agent");
        if ((clientData == null) || (clientData.indexOf("Java") > -1)) {
            return true;
        }

        if (isMobileRequest(request)) {
            return true;
        }

        HashMap info = getBrowserInfo(clientData.toUpperCase());

        // not a browser; let regression test go
        // need a nicer way to check regression test
        if (info == null)
            return true;


        try {

            String browser = (String)info.get(BROWSER_NAME);
            String version = (String)info.get(BROWSER_VERSION);

            if (browser != null && version != null) {
                if (browser.equals(MSIE)) {
                    value = (compareVersion(version, "6.0") >= 0);
                } else if (browser.equals(FIREFOX)) {
                    value = (compareVersion(version, "1.0.4") >= 0);
                } else if (browser.equals(SAFARI)) {
                    // 125 Safari 1.2
                    // 312 Safari 1.3
                    value = (compareVersion(version, "125") >= 0);
                } else if (browser.equals(NETSCAPE)) {
                    // version >= 5.0f && version < 7.0f
                    if (compareVersion(version, "5.0") >= 0 &&
                        compareVersion(version, "7.0") < 0) {
                        // This is Mozilla
                        int rvStart = clientData.indexOf("rv:");
                        int i = rvStart + 3;
                        int len = clientData.length();
                        StringBuffer buf = new StringBuffer();
                        boolean decimalFound = false;
                        while (i < len) {
                            char c = clientData.charAt(i);
                            if (Character.isDigit(c))
                                buf.append(c);
                            else if (c == '.' && !decimalFound) {
                                buf.append(c);
                                decimalFound = true;
                            } else
                                break;
                            i++;
                        }

                        if (buf.length() > 0) {
                            value =
(compareVersion(new String(buf), "1.7") >= 0);
                        }
                    } else
                        value = (compareVersion(version, "7.2") >= 0);
                } else if (browser.equals(NETSCAPE_)) {
                    value = (compareVersion(version, "7.2") >= 0);
                }
            }

            // Windows CE: Pocket PC browsers
            String os = (String)info.get(BROWSER_OS);
            if (os != null && WINCE.equals(os)) {
                value = true;
            }

            return value;

        } catch (Exception e) {
            // best effort fails; raise the flag
            return false;
        }
    }

    /**
     * Returns whether User Agent Header os type Windows CE
     *  For pocket PC applications
     */
    public static boolean isUserAgentWindowsCE(HttpServletRequest request) {
        if (request == null) {
            return false;
        }

        String clientData = request.getHeader("user-agent");

        if (clientData == null || clientData.length() == 0) {
            return false;
        }

        HashMap info = getBrowserInfo(clientData.toUpperCase());

        if (info == null || info.isEmpty()) {
            return false;
        }

        String os = (String)info.get(BROWSER_OS);
        return WINCE.equals(os);
    }

    /**
     * Returns the browser name.
     *
     * @param request the http request from the user agent.
     * @return the browser name or empty string if not found.
     */
    public static String getBrowserName(HttpServletRequest request) {
        String clientData = request.getHeader("user-agent");
        HashMap info = getBrowserInfo(clientData.toUpperCase());
        if (info == null) {
            return "";
        }

        String browser = (String)info.get(BROWSER_NAME);
        if (browser == null) {
            return "";
        }

        return browser;
    }

    /**
     * Returns the browser version.
     *
     * @param request the http request from the user agent.
     * @return the browser version or empty string if not found.
     */
    public static String getBrowserVersion(HttpServletRequest request) {
        String clientData = request.getHeader("user-agent");
        HashMap info = getBrowserInfo(clientData.toUpperCase());
        if (info == null) {
            return "";
        }

        String version = (String)info.get(BROWSER_VERSION);
        if (version == null) {
            return "";
        }

        return version;
    }

    /**
     * Returns the browser OS.
     *
     * @param request the http request from the user agent.
     * @return the browser OS or empty string if not found.
     */
    public static String getBrowserOS(HttpServletRequest request) {
        String clientData = request.getHeader("user-agent");
        HashMap info = getBrowserInfo(clientData.toUpperCase());
        if (info == null) {
            return "";
        }

        String os = (String)info.get(BROWSER_OS);
        if (os == null) {
            return "";
        }

        return os;
    }

    /**
     * Collects the browser information from the "user_agent" string.
     */
    private static HashMap getBrowserInfo(String userAgent) {
        HashMap info = null;
        if (userAgent == null || userAgent.length() == 0)
            return info;

        int index = userAgent.indexOf(MSIE);
        if (index >= 0) {
            info = new HashMap(5);
            info.put(BROWSER_NAME, MSIE);
            // Peel off the browser version
            String version = userAgent.substring(index + MSIE.length());
            StringTokenizer ietoken = new StringTokenizer(version, " ;");
            version = ietoken.nextToken();

            info.put(BROWSER_VERSION, version);
        } else if (userAgent.indexOf(FIREFOX) >= 0) {
            info = new HashMap(5);
            info.put(BROWSER_NAME, FIREFOX);

            // Peel off the browser version
            index = userAgent.indexOf(FIREFOX);
            String version = userAgent.substring(index + FIREFOX.length());
            StringTokenizer token = new StringTokenizer(version, " /");
            version = token.nextToken();

            info.put(BROWSER_VERSION, version);
        } else if (userAgent.indexOf(SAFARI) >= 0) {
            info = new HashMap(5);
            info.put(BROWSER_NAME, SAFARI);

            // Peel off the browser version
            index = userAgent.indexOf(SAFARI);
            String version = userAgent.substring(index + SAFARI.length());
            StringTokenizer token = new StringTokenizer(version, " /");
            version = token.nextToken();

            info.put(BROWSER_VERSION, version);
        } else {
            // Check for Netscape 6
            if (userAgent.indexOf(NETSCAPE6) >= 0) {
                info = new HashMap(5);
                info.put(BROWSER_NAME, NETSCAPE6);
                info.put(BROWSER_VERSION, "6.0");
            }
            // Check for other Netscape versions
            // This must be done after checking NETSCAPE6
            else if (userAgent.indexOf(NETSCAPE_) >= 0) {
                index = userAgent.indexOf(NETSCAPE_);
                info = new HashMap(5);
                info.put(BROWSER_NAME, NETSCAPE_);
                String version =
                    userAgent.substring(index + NETSCAPE_.length());
                StringTokenizer token = new StringTokenizer(version, " /");
                version = token.nextToken();
                info.put(BROWSER_VERSION, version);
            } else if (userAgent.indexOf(NETSCAPE) >= 0) {
                // Peel off netscape version
                // (always the second token in the line)
                StringTokenizer line = new StringTokenizer(userAgent, " /");
                String netversion = line.nextToken();
                netversion = line.nextToken();

                info = new HashMap(5);
                info.put(BROWSER_NAME, NETSCAPE);
                info.put(BROWSER_VERSION, netversion);
            }
        }

        // can't recognize the browser
        if (info == null)
            return null;

        // Cycle through the possible os's
        if (userAgent.indexOf(MAC) >= 0) {
            info.put(BROWSER_OS, MAC);
        } else if (userAgent.indexOf(SUN) >= 0) {
            info.put(BROWSER_OS, SUN);
        } else if (userAgent.indexOf(WIN98) >= 0 ||
                   userAgent.indexOf(WIN98_2) >= 0) {
            info.put(BROWSER_OS, WIN98);
        } else if (userAgent.indexOf(WINNT) >= 0 ||
                   userAgent.indexOf(WINNT2) >= 0) {
            info.put(BROWSER_OS, WINNT);
        } else if (userAgent.indexOf(WIN2K) >= 0) {
            info.put(BROWSER_OS, WIN2K);
        } else if (userAgent.indexOf(WINXP) >= 0) {
            info.put(BROWSER_OS, WINXP);
        }
        // pocket pc
        else if (userAgent.indexOf(WINCE) >= 0) {
            info.put(BROWSER_OS, WINCE);
        }
        // this must be done at the last
        else if (userAgent.indexOf(WINNT3) >= 0) {
            info.put(BROWSER_OS, WINNT);
        }

        return info;
    }

    public static int compareVersion(String v1, String v2) {

        if (v1 == null || v2 == null)
            return 0;

        // same value "1.2" and "1.2"
        if (v1.equals(v2))
            return 0;

        StringTokenizer st1 = new StringTokenizer(v1, ".");
        StringTokenizer st2 = new StringTokenizer(v2, ".");

        while (st1.hasMoreTokens() && st2.hasMoreTokens()) {
            int value1 = Integer.parseInt(st1.nextToken());
            int value2 = Integer.parseInt(st2.nextToken());

            // "5.3" and "4.3"
            if (value1 > value2)
                return 1;

            // "4.3" and "5.3"
            if (value1 < value2)
                return -1;
        }

        // "5.3" and "5",  "5.0" and "5"
        if (st1.hasMoreTokens())
            return 1;

        // "5" and "5.1",  "5" and "5.0"
        if (st2.hasMoreTokens()) {
            int value2 = Integer.parseInt(st2.nextToken());

            return (value2 == 0 ? 0 : -1);
        }

        // same value
        return 0;
    }

    public static void setBrowserTimezoneOffset(HttpSession session,
                                                String browserTimezoneOffset) {
        if (browserTimezoneOffset != null) {
            Long offset = null;

            try {
                offset = new Long(Long.parseLong(browserTimezoneOffset));
            } catch (Exception e) {
                s_log.logp(Level.SEVERE,UserAgentUtil.class.getName(),"setBrowserTimezoneOffset",
                           "Error converting browserTimezoneOffset to numeric value",e);
            } finally {
                session.setAttribute(BROWSER_TIMEZONE_OFFSET, offset);
            }
        }
    }

    /**
     * Returns the browser timezone offset in minutes
     * (the difference between browser tz and GMT/UTC in minutes)
     *
     * @param session browser session
     * @return number of minutes or null if offset is unknown
     */
    public static Long getBrowserTimezoneOffset(HttpSession session) {
        return (Long)session.getAttribute(BROWSER_TIMEZONE_OFFSET);
    }

    /**
     * Check if the user-agent is emCli.
     */
    public static boolean isUserAgentEmCli(HttpServletRequest request) {
        boolean isEmCli = false;

        String clientData = request.getHeader("user-agent");
        // In 11g's http client, emCli sends a request header as "Java/1.5.0_05"
        if (clientData != null) {
            if (clientData.indexOf("Java") >= 0) {
                isEmCli = true;
            }
        }
        return isEmCli;
    }

    /**
     * Check if the user-agent is webtester.
     */
    public static boolean isUserAgentWebTester(HttpServletRequest request) {
        boolean isWebTester = false;

        String clientData = request.getHeader("user-agent");
        // In 11g's http client, webtester seems to always send request header
        // as "Oracle HTTPClient ...", even though webtester has code to
        // override it.

        //TODO : Remove the generic OR block that checks Oracle HTTPClient once bug 11773505 is fixed.
        // This will make code cleaner and easy to maintain plus won't add any future bugs.
        if (clientData != null) {
            if (clientData.indexOf("WebTester") >= 0 ||
                (clientData.indexOf("Oracle HTTPClient") >= 0 &&
                 clientData.indexOf(EMCTL_USER_AGENT) == -1 &&
                 clientData.indexOf(EM_ADF_WARMUP_USER_AGENT) == -1)) {
                isWebTester = true;
            }
        }

        return isWebTester;
    }

    /**
     * return the EM-Mobile-App-Agent from the given request/session
     * @param request
     * @return string value for EM-Mobile-App-Agent
     */
    public static String getEMMobileAppAgent(HttpServletRequest request) {
        String agent = request.getHeader(EM_MOBILE_APP_AGENT);
        //Trinidad AJAX requests lose the header, so we cache it on session when possible
        HttpSession sess = request.getSession();
        if (agent == null || "".equals(agent)) {
            if (sess != null) {
                agent = (String)sess.getAttribute(EM_MOBILE_APP_AGENT);
            }
        } else {
            if (sess != null) {
                sess.setAttribute(EM_MOBILE_APP_AGENT, agent);
            }
        }

        return agent;
    }
    
    private static boolean isDesktopMobileTesting(HttpServletRequest request)
    {
        if (desktopMobileTesting == null)
        {
            ServletContext context = request.getSession(true).getServletContext();
            String value = context.getInitParameter("oracle.sysman.mobile.ENABLE_DESKTOP_TESTING");
            desktopMobileTesting = false;
            if (value != null)
            {
                desktopMobileTesting = Boolean.parseBoolean(value);
            }
        }
        //exclude emcli and webtester
        return desktopMobileTesting && !isUserAgentWebTester(request) && !isUserAgentEmCli(request);
    }

    /**
     * @return true if request is coming from a mobile app
     */
    public static boolean isMobileAppRequest(HttpServletRequest request) {
        if (isDesktopMobileTesting(request))
        {
            return true;
        }
        
        return isMobileRequest(request) &&
            getEMMobileAppAgent(request) != null;
    }

    /**
     * @return true if request is coming from supported mobile platform
     */
    public static boolean isMobileRequest(HttpServletRequest request) {
        String userAgent = request.getHeader("user-agent");

        if (userAgent != null) {
            userAgent = userAgent.toLowerCase();
            
            if (isDesktopMobileTesting(request))
            {
                return true;
            }
            //only check for iphone and ipod
            return (userAgent.indexOf("iphone") != -1 || userAgent.indexOf("ipod") != -1);
        }

        return false;
    }
    
    /**
     * @return true if the given request is a tablet request.
     */ 
    public static boolean isTabletRequest(HttpServletRequest request)
    {
        String userAgent = request.getHeader("user-agent");

        if (userAgent != null) {
            userAgent = userAgent.toLowerCase();
            //only check for ipad
            return (userAgent.indexOf("ipad") != -1);
        }

        return false;
    }
}
