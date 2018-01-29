package leif.statue.com.util;

/**
 * Created by Leif on 12/8/2017.
 */

public class URLManager {
    private static final String HTTP_SERVER = "http://cruise2006.xsrv.jp";
//    private static final String HTTP_SERVER = "http://192.168.5.213";
    private static final String HTTP_API_HEADER = "/butsudan/api/";
    private static final String HTTP_FIXED_HEADER = "/butsudan/fixed/";

    public static String getLoginURL() {
        return HTTP_SERVER + HTTP_API_HEADER + "login?";
    }

    public static String getSignUpURL() {
        return HTTP_SERVER + HTTP_API_HEADER + "signup?";
    }

    public static String getThemeListURL() {
        return HTTP_SERVER + HTTP_API_HEADER + "themes?";
    }

    public static String getBuddhistListURL() {
        return HTTP_SERVER + HTTP_API_HEADER + "butsugu?";
    }

    public static String getContactUsURL() {
        return HTTP_SERVER + HTTP_API_HEADER + "question";
    }

    public static String getEditProfileURL() {
        return HTTP_SERVER + HTTP_API_HEADER + "profile?";
    }

    public static String getTermsURL() {
        return HTTP_SERVER + HTTP_FIXED_HEADER + "terms/";
    }

    public static String getPrivacyURL() {
        return HTTP_SERVER + HTTP_FIXED_HEADER + "privacy/";
    }

    public static String getSaveHistoryURL() {
        return HTTP_SERVER + HTTP_API_HEADER + "savehistory";
    }

    public static String getCommercialURL() {
        return HTTP_SERVER + HTTP_FIXED_HEADER + "commercial/";
    }

    public static String getAboutPaymentURL() {
        return HTTP_SERVER + HTTP_FIXED_HEADER + "cancel/";
    }

    public static String getUploadHonjonURL() {
        return HTTP_SERVER + HTTP_API_HEADER + "honzonupload";
    }

    public static String getForgotPasswordURL() {
        return HTTP_SERVER + HTTP_API_HEADER + "sendpwdmail?";
    }

    public static String getThemeChangeURL() {
        return HTTP_SERVER + HTTP_API_HEADER + "themechange?";
    }

    public static String getUploadCompleteURL() {
        return HTTP_SERVER + HTTP_API_HEADER + "savelastimg";
    }

    public static String getCancelMembershipURL() {
        return HTTP_SERVER + HTTP_API_HEADER + "cancel?";
    }

    public static String getPayTotalURL() {
        return HTTP_SERVER + HTTP_API_HEADER + "total?";
    }

    public static String getUpdatedHonzonURL() {
        return HTTP_SERVER + HTTP_API_HEADER + "gethonzon?";
    }
}
