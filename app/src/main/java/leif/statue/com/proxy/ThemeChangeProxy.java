package leif.statue.com.proxy;

import com.google.gson.Gson;

import java.io.IOException;

import leif.statue.com.util.URLManager;
import leif.statue.com.vo.LoginResponseVo;
import leif.statue.com.vo.ThemeChangeResponseVo;

public class ThemeChangeProxy extends BaseProxy {

    public ThemeChangeResponseVo run(String userId, String imageId, String lang) throws IOException {
        String params = "user_id=" + userId + "&image_id=" + imageId + "&lang=" + lang;

        String contentString = getPlain(URLManager.getThemeChangeURL(), params);

        ThemeChangeResponseVo responseVo = new Gson().fromJson(contentString, ThemeChangeResponseVo.class);

        return responseVo;
    }
}
