package leif.statue.com.proxy;

import com.google.gson.Gson;

import java.io.IOException;

import leif.statue.com.util.URLManager;
import leif.statue.com.vo.GetUpdatedHonzonResponseVo;
import leif.statue.com.vo.PayTotalResponseVo;

public class GetUpdateHonzonProxy extends BaseProxy {

    public GetUpdatedHonzonResponseVo run(String userId, String lang) throws IOException {
        String params = "user_id=" + userId + "&lang=" + lang;

        String contentString = getPlain(URLManager.getUpdatedHonzonURL(), params);

        GetUpdatedHonzonResponseVo responseVo = new Gson().fromJson(contentString, GetUpdatedHonzonResponseVo.class);

        return responseVo;
    }
}
