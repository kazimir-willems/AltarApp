package leif.statue.com.proxy;

import com.google.gson.Gson;

import java.io.IOException;

import leif.statue.com.util.URLManager;
import leif.statue.com.vo.CheckUpdatedHonzonResponseVo;
import leif.statue.com.vo.ContactUsResponseVo;
import okhttp3.FormBody;
import okhttp3.RequestBody;

public class CheckUpdatedHonzonProxy extends BaseProxy {

    public CheckUpdatedHonzonResponseVo run(String userId, String lang) throws IOException {
        String params = "user_id=" + userId + "&lang=" + lang;

        String contentString = getPlain(URLManager.getCheckHonzonUpdatedURL(), params);

        CheckUpdatedHonzonResponseVo responseVo = new Gson().fromJson(contentString, CheckUpdatedHonzonResponseVo.class);

        return responseVo;
    }
}
