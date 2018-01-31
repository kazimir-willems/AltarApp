package leif.statue.com.proxy;

import android.util.Log;

import com.google.gson.Gson;

import java.io.IOException;

import leif.statue.com.util.URLManager;
import leif.statue.com.vo.LoginResponseVo;
import leif.statue.com.vo.SignUpResponseVo;

public class SignUpProxy extends BaseProxy {

    public SignUpResponseVo run(String mailAddress, String password, String lang, String prefecture, String age, String gender, String isNotice, String plan, String token, String orderId) throws IOException {
        String params = "email=" + mailAddress + "&password=" + password + "&lang=" + lang + "&prefecture=" + prefecture + "&age=" + age + "&gender=" + gender + "&is_notice=" + isNotice + "&plan=" + plan + "&token=" + token + "&order_id=" + orderId;

        String contentString = getPlain(URLManager.getSignUpURL(), params);

        SignUpResponseVo responseVo = new Gson().fromJson(contentString, SignUpResponseVo.class);

        return responseVo;
    }
}
