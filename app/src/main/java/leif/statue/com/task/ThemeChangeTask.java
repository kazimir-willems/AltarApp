package leif.statue.com.task;

import android.os.AsyncTask;

import org.greenrobot.eventbus.EventBus;

import leif.statue.com.event.LoginEvent;
import leif.statue.com.event.ThemeChangeEvent;
import leif.statue.com.proxy.LoginProxy;
import leif.statue.com.proxy.ThemeChangeProxy;
import leif.statue.com.vo.LoginResponseVo;
import leif.statue.com.vo.ThemeChangeResponseVo;

public class ThemeChangeTask extends AsyncTask<String, Void, ThemeChangeResponseVo> {

    private String userId;
    private String imageId;
    private String lang;

    @Override
    protected void onPreExecute() {

    }

    @Override
    protected ThemeChangeResponseVo doInBackground(String... params) {
        ThemeChangeProxy simpleProxy = new ThemeChangeProxy();
        userId = params[0];
        imageId = params[1];
        lang = params[2];
        try {
            final ThemeChangeResponseVo responseVo = simpleProxy.run(userId, imageId, lang);

            return responseVo;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(ThemeChangeResponseVo responseVo) {
        EventBus.getDefault().post(new ThemeChangeEvent(responseVo));
    }
}