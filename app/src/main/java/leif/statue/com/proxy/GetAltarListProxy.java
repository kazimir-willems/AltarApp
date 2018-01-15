package leif.statue.com.proxy;

import android.util.Log;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import leif.statue.com.util.URLManager;
import leif.statue.com.vo.GetAltarResponseVo;
import leif.statue.com.vo.LoginResponseVo;

public class GetAltarListProxy extends BaseProxy {

    public GetAltarResponseVo run() throws IOException {

        String contentString = getPlain(URLManager.getThemeListURL(), "");

        Log.v("Content", contentString);

        GetAltarResponseVo responseVo = new GetAltarResponseVo();

        try {
            JSONObject res = new JSONObject(contentString);
            responseVo.success = res.getInt("success");
            if(responseVo.success == 0) {
                responseVo.error = res.getInt("error");
                responseVo.error_msg = res.getString("error_msg");
            } else {
                responseVo.data = res.getString("data");
            }
        } catch (JSONException ex) {
            ex.printStackTrace();
        }

        return responseVo;
    }
}
