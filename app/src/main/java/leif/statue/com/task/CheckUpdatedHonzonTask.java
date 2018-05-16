package leif.statue.com.task;

import android.os.AsyncTask;

import org.greenrobot.eventbus.EventBus;

import leif.statue.com.event.CheckUpdatedHonzonEvent;
import leif.statue.com.event.ThemeChangeEvent;
import leif.statue.com.proxy.CheckUpdatedHonzonProxy;
import leif.statue.com.proxy.ThemeChangeProxy;
import leif.statue.com.vo.CheckUpdatedHonzonResponseVo;
import leif.statue.com.vo.ThemeChangeResponseVo;

public class CheckUpdatedHonzonTask extends AsyncTask<String, Void, CheckUpdatedHonzonResponseVo> {

    private String userId;
    private String lang;

    @Override
    protected void onPreExecute() {

    }

    @Override
    protected CheckUpdatedHonzonResponseVo doInBackground(String... params) {
        CheckUpdatedHonzonProxy simpleProxy = new CheckUpdatedHonzonProxy();
        userId = params[0];
        lang = params[1];
        try {
            final CheckUpdatedHonzonResponseVo responseVo = simpleProxy.run(userId, lang);

            return responseVo;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(CheckUpdatedHonzonResponseVo responseVo) {
        EventBus.getDefault().post(new CheckUpdatedHonzonEvent(responseVo));
    }
}