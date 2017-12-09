package leif.statue.com.proxy;

import com.google.gson.Gson;

import java.io.IOException;

import leif.statue.com.util.URLManager;
import leif.statue.com.vo.LoginResponseVo;
import okhttp3.FormBody;
import okhttp3.RequestBody;

public class LoginProxy extends BaseProxy {

    public LoginResponseVo run(String mailAddress, String password) throws IOException {
        FormBody.Builder formBuilder = new FormBody.Builder();
        formBuilder.add("email", mailAddress);
        formBuilder.add("password", password);

        RequestBody formBody = formBuilder.build();

        String contentString = postPlain(URLManager.getLoginURL(), formBody);

        LoginResponseVo responseVo = new Gson().fromJson(contentString, LoginResponseVo.class);

        return responseVo;
    }
}
