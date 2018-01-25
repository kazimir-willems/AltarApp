package leif.statue.com.proxy;

import android.util.Log;

import com.google.gson.Gson;

import java.io.IOException;

import leif.statue.com.util.URLManager;
import leif.statue.com.vo.UploadCompleteResponseVo;
import leif.statue.com.vo.UploadHonjouResponseVo;
import okhttp3.FormBody;
import okhttp3.RequestBody;

public class UploadCompleteProxy extends BaseProxy {

    public UploadCompleteResponseVo run(String userId, String img, String lang) throws IOException {
        FormBody.Builder formBuilder = new FormBody.Builder();
        formBuilder.add("user_id", userId);
        formBuilder.add("file", img);
        formBuilder.add("lang", lang);

        RequestBody formBody = formBuilder.build();

        String contentString = postPlain(URLManager.getUploadCompleteURL(), formBody);

        Log.v("contentString", contentString);

        UploadCompleteResponseVo responseVo = new Gson().fromJson(contentString, UploadCompleteResponseVo.class);

        return responseVo;
    }
}
