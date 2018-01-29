package leif.statue.com.proxy;

import com.google.gson.Gson;

import java.io.IOException;

import leif.statue.com.AltarApplication;
import leif.statue.com.util.URLManager;
import leif.statue.com.vo.CancelMembershipResponseVo;
import leif.statue.com.vo.ContactUsResponseVo;
import okhttp3.FormBody;
import okhttp3.RequestBody;

public class CancelMembershipProxy extends BaseProxy {

    public CancelMembershipResponseVo run(String userId, String lang) throws IOException {

        String params = "user_id=" + userId + "&lang=" + lang;

        String contentString = getPlain(URLManager.getCancelMembershipURL(), params);

        CancelMembershipResponseVo responseVo = new Gson().fromJson(contentString, CancelMembershipResponseVo.class);

        return responseVo;
    }
}
