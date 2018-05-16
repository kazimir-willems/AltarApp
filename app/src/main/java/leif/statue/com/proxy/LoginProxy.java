package leif.statue.com.proxy;

import android.util.Log;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import leif.statue.com.util.URLManager;
import leif.statue.com.vo.LoginResponseVo;
import okhttp3.FormBody;
import okhttp3.RequestBody;

public class LoginProxy extends BaseProxy {

    public LoginResponseVo run(String mailAddress, String password, String lang, String token, String isCancel, String expireDate, String plan) throws IOException {
        String params = "user_id=" + mailAddress + "&password=" + password + "&lang=" + lang + "&token=" + token + "&is_cancel=" + isCancel + "&expire_date=" + expireDate + "&plan=" + plan;

        String contentString = getPlain(URLManager.getLoginURL(), params);

        Log.v("Content String", contentString);

        /*public int user_id;
        public String email;
        public int prefecture;
        public int age;
        public int gender;
        public int is_notice;
        public String confirm;
        public String honzon;
        public int music;
        public String last_img;*/

        LoginResponseVo responseVo = new LoginResponseVo();
        try {
            JSONObject jsonResponse = new JSONObject(contentString);

            responseVo.success = jsonResponse.getInt("success");

            if(responseVo.success == 1) {
                responseVo.user_id = jsonResponse.getInt("user_id");
                responseVo.prefecture = jsonResponse.getInt("prefecture");
                responseVo.is_notice = jsonResponse.getInt("is_notice");
                responseVo.honzon = jsonResponse.getString("honzon");
                responseVo.music = jsonResponse.getInt("music");
                responseVo.last_img = jsonResponse.getString("last_img");
                responseVo.age = jsonResponse.getInt("age");
                responseVo.confirm = jsonResponse.getString("confirm");
                responseVo.email = jsonResponse.getString("email");
                responseVo.gender = jsonResponse.getInt("gender");
                responseVo.plan = jsonResponse.getInt("plan");
                responseVo.count_history = jsonResponse.getString("count_history");
            }

            if(responseVo.success != 1) {
                responseVo.error = jsonResponse.getInt("error");
                responseVo.error_msg = jsonResponse.getString("error_msg");
            }
        } catch (JSONException ex) {
            ex.printStackTrace();
        }

//        LoginResponseVo responseVo = new Gson().fromJson(contentString, LoginResponseVo.class);

        return responseVo;
    }
}
