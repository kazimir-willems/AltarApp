package leif.statue.com.proxy;

import com.google.gson.Gson;

import java.io.IOException;

import leif.statue.com.AltarApplication;
import leif.statue.com.util.URLManager;
import leif.statue.com.vo.EditProfileResponseVo;
import leif.statue.com.vo.SignUpResponseVo;

public class EditProfileProxy extends BaseProxy {

    public EditProfileResponseVo run(String mailAddress, String password, String lang, String prefecture, String age, String gender, String isNotice) throws IOException {
        String params = "email=" + mailAddress + "&password=" + password + "&lang=" + lang + "&prefecture=" + prefecture + "&age=" + age + "&gender=" + gender + "&is_notice=" + isNotice + "&user_id=" + AltarApplication.userId;

        String contentString = getPlain(URLManager.getEditProfileURL(), params);

        EditProfileResponseVo responseVo = new Gson().fromJson(contentString, EditProfileResponseVo.class);

        return responseVo;
    }
}
