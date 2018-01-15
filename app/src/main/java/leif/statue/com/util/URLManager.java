package leif.statue.com.util;

/**
 * Created by Leif on 12/8/2017.
 */

public class URLManager {
    private static final String HTTP_SERVER = "http://cruise2006.xsrv.jp/butsudan/api/";
//    private static final String HTTP_SERVER = "http://192.168.5.213/butsudan/api/";

    public static String getLoginURL() {
        return HTTP_SERVER + "login?";
    }

    public static String getSignUpURL() {
        return HTTP_SERVER + "signup?";
    }

    public static String getThemeListURL() {
        return HTTP_SERVER + "themes";
    }

    public static String getBuddhistListURL() {
        return HTTP_SERVER + "butsugu/";
    }
}
