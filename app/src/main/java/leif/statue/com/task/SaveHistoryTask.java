package leif.statue.com.task;

import android.os.AsyncTask;

import org.greenrobot.eventbus.EventBus;

import leif.statue.com.event.SaveHistoryEvent;
import leif.statue.com.event.UploadHonjouEvent;
import leif.statue.com.proxy.SaveHistoryProxy;
import leif.statue.com.proxy.UploadHonjouProxy;
import leif.statue.com.vo.SaveHistoryResponseVo;
import leif.statue.com.vo.UploadHonjouResponseVo;

public class SaveHistoryTask extends AsyncTask<String, Void, SaveHistoryResponseVo> {

    private String userId;
    private String data;
    private String lang;

    @Override
    protected void onPreExecute() {

    }

    @Override
    protected SaveHistoryResponseVo doInBackground(String... params) {
        SaveHistoryProxy simpleProxy = new SaveHistoryProxy();
        userId = params[0];
        lang = params[1];
        data = params[2];

        try {
            final SaveHistoryResponseVo responseVo = simpleProxy.run(userId, lang, data);

            return responseVo;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(SaveHistoryResponseVo responseVo) {
        EventBus.getDefault().post(new SaveHistoryEvent(responseVo));
    }
}