package leif.statue.com.util;

/**
 * Created by Leif on 12/8/2017.
 */

public class URLManager {
    private static final String HTTP_SERVER = "http://192.168.5.144/altar/";

    public static String getLoginURL() {
        return HTTP_SERVER + "Login.php";
    }
}
