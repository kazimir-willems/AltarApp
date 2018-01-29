package leif.statue.com.proxy;

import android.util.Log;

import com.google.gson.Gson;

import java.io.IOException;

import leif.statue.com.util.URLManager;
import leif.statue.com.vo.SaveHistoryResponseVo;
import leif.statue.com.vo.SignUpResponseVo;
import okhttp3.FormBody;
import okhttp3.RequestBody;

public class SaveHistoryProxy extends BaseProxy {

    public SaveHistoryResponseVo run(String userId, String lang, String data) throws IOException {
        FormBody.Builder formBuilder = new FormBody.Builder();
        formBuilder.add("user_id", userId);
        formBuilder.add("lang", lang);
        formBuilder.add("data", data);

        RequestBody formBody = formBuilder.build();

        String contentString = postPlain(URLManager.getSaveHistoryURL(), formBody);

        Log.v("contentString", contentString);

        SaveHistoryResponseVo responseVo = new Gson().fromJson(contentString, SaveHistoryResponseVo.class);

        return responseVo;
    }
}
