package leif.statue.com.task;

import android.os.AsyncTask;

import org.greenrobot.eventbus.EventBus;

import leif.statue.com.event.LoginEvent;
import leif.statue.com.event.UploadHonjouEvent;
import leif.statue.com.proxy.LoginProxy;
import leif.statue.com.proxy.UploadHonjouProxy;
import leif.statue.com.vo.LoginResponseVo;
import leif.statue.com.vo.UploadHonjouResponseVo;

public class UploadHonjouTask extends AsyncTask<String, Void, UploadHonjouResponseVo> {

    private String userId;
    private String image;
    private String imageId;
    private String modifyFlag;
    private String lang;

    @Override
    protected void onPreExecute() {

    }

    @Override
    protected UploadHonjouResponseVo doInBackground(String... params) {
        UploadHonjouProxy simpleProxy = new UploadHonjouProxy();
        userId = params[0];
        image = params[1];
        imageId = params[2];
        modifyFlag = params[3];
        lang = params[4];
        try {
            final UploadHonjouResponseVo responseVo = simpleProxy.run(userId, image, imageId, modifyFlag, lang);

            return responseVo;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(UploadHonjouResponseVo responseVo) {
        EventBus.getDefault().post(new UploadHonjouEvent(responseVo));
    }
}