package leif.statue.com.proxy;

import com.google.gson.Gson;

import java.io.IOException;

import leif.statue.com.util.URLManager;
import leif.statue.com.vo.ContactUsResponseVo;
import leif.statue.com.vo.LoginResponseVo;

public class ContactUsProxy extends BaseProxy {

    public ContactUsResponseVo run(String name, String mailAddress, String lang, String contents) throws IOException {
        String params = "name=" + name + "&email=" + mailAddress + "&lang=" + lang + "&contents=" + contents;

        String contentString = getPlain(URLManager.getContactUsURL(), params);

        ContactUsResponseVo responseVo = new Gson().fromJson(contentString, ContactUsResponseVo.class);

        return responseVo;
    }
}
