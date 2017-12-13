package leif.statue.com.proxy;

import android.util.Log;

import com.google.gson.Gson;

import java.io.IOException;

import leif.statue.com.util.URLManager;
import leif.statue.com.vo.LoginResponseVo;
import okhttp3.FormBody;
import okhttp3.RequestBody;

public class LoginProxy extends BaseProxy {

    public LoginResponseVo run(String mailAddress, String password) throws IOException {
        String params = "user_id=" + mailAddress + "&password=" + password;

        String contentString = getPlain(URLManager.getLoginURL(), params);

        LoginResponseVo responseVo = new Gson().fromJson(contentString, LoginResponseVo.class);

        return responseVo;
    }
}
