package leif.statue.com.task;

import android.os.AsyncTask;

import org.greenrobot.eventbus.EventBus;

import leif.statue.com.event.UploadCompleteEvent;
import leif.statue.com.event.UploadHonjouEvent;
import leif.statue.com.proxy.UploadCompleteProxy;
import leif.statue.com.proxy.UploadHonjouProxy;
import leif.statue.com.vo.UploadCompleteResponseVo;
import leif.statue.com.vo.UploadHonjouResponseVo;

public class UploadCompleteTask extends AsyncTask<String, Void, UploadCompleteResponseVo> {

    private String userId;
    private String image;
    private String lang;

    @Override
    protected void onPreExecute() {

    }

    @Override
    protected UploadCompleteResponseVo doInBackground(String... params) {
        UploadCompleteProxy simpleProxy = new UploadCompleteProxy();
        userId = params[0];
        image = params[1];
        lang = params[2];
        try {
            final UploadCompleteResponseVo responseVo = simpleProxy.run(userId, image, lang);

            return responseVo;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(UploadCompleteResponseVo responseVo) {
        EventBus.getDefault().post(new UploadCompleteEvent(responseVo));
    }
}