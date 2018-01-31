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
    private String lastImage;
    private String honzonImage;
    private String modifyFlag;
    private String lang;

    @Override
    protected void onPreExecute() {

    }

    @Override
    protected UploadCompleteResponseVo doInBackground(String... params) {
        UploadCompleteProxy simpleProxy = new UploadCompleteProxy();
        userId = params[0];
        lastImage = params[1];
        honzonImage = params[2];
        modifyFlag = params[3];
        lang = params[4];
        try {
            final UploadCompleteResponseVo responseVo = simpleProxy.run(userId, lastImage, honzonImage, modifyFlag, lang);

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