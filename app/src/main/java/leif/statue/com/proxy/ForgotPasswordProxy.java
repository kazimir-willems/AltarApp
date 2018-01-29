package leif.statue.com.proxy;

import com.google.gson.Gson;

import java.io.IOException;

import leif.statue.com.util.URLManager;
import leif.statue.com.vo.ForgotPasswordResponseVo;
import leif.statue.com.vo.SignUpResponseVo;

public class ForgotPasswordProxy extends BaseProxy {

    public ForgotPasswordResponseVo run(String mailAddress, String lang) throws IOException {
        String params = "email=" + mailAddress + "&lang=" + lang;

        String contentString = getPlain(URLManager.getForgotPasswordURL(), params);

        ForgotPasswordResponseVo responseVo = new Gson().fromJson(contentString, ForgotPasswordResponseVo.class);

        return responseVo;
    }
}
