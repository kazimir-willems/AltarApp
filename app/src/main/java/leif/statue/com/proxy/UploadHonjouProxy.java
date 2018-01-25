package leif.statue.com.proxy;

import android.util.Log;

import com.google.gson.Gson;

import java.io.IOException;

import leif.statue.com.util.URLManager;
import leif.statue.com.vo.UploadHonjouResponseVo;
import okhttp3.FormBody;
import okhttp3.RequestBody;

public class UploadHonjouProxy extends BaseProxy {

    public UploadHonjouResponseVo run(String userId, String img, String imageId, String modifyFlag, String lang) throws IOException {
        FormBody.Builder formBuilder = new FormBody.Builder();
        formBuilder.add("user_id", userId);
        formBuilder.add("file", img);
        formBuilder.add("modify_flag", modifyFlag);
        formBuilder.add("lang", lang);

        RequestBody formBody = formBuilder.build();

        String contentString = postPlain(URLManager.getUploadHonjonURL(), formBody);

        Log.v("contentString", contentString);

        UploadHonjouResponseVo responseVo = new Gson().fromJson(contentString, UploadHonjouResponseVo.class);

        return responseVo;
    }
}
