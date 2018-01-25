package leif.statue.com.proxy;

import com.google.gson.Gson;

import java.io.IOException;

import leif.statue.com.util.URLManager;
import leif.statue.com.vo.ContactUsResponseVo;
import leif.statue.com.vo.LoginResponseVo;
import okhttp3.FormBody;
import okhttp3.RequestBody;

public class ContactUsProxy extends BaseProxy {

    public ContactUsResponseVo run(String name, String mailAddress, String lang, String contents) throws IOException {
//        String params = "name=" + name + "&email=" + mailAddress + "&lang=" + lang + "&contents=" + contents;

        FormBody.Builder formBuilder = new FormBody.Builder();
        formBuilder.add("name", name);
        formBuilder.add("email", mailAddress);
        formBuilder.add("lang", lang);
        formBuilder.add("contents", contents);

        RequestBody formBody = formBuilder.build();

        String contentString = postPlain(URLManager.getContactUsURL(), formBody);

        ContactUsResponseVo responseVo = new Gson().fromJson(contentString, ContactUsResponseVo.class);

        return responseVo;
    }
}
