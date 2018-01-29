package leif.statue.com.task;

import android.os.AsyncTask;

import org.greenrobot.eventbus.EventBus;

import leif.statue.com.event.GetUpdateHonzonEvent;
import leif.statue.com.event.PayTotalEvent;
import leif.statue.com.proxy.GetUpdateHonzonProxy;
import leif.statue.com.proxy.PayTotalProxy;
import leif.statue.com.vo.GetUpdatedHonzonResponseVo;
import leif.statue.com.vo.PayTotalResponseVo;

public class GetUpdatedHonzonTask extends AsyncTask<String, Void, GetUpdatedHonzonResponseVo> {

    private String userId;
    private String lang;

    @Override
    protected void onPreExecute() {

    }

    @Override
    protected GetUpdatedHonzonResponseVo doInBackground(String... params) {
        GetUpdateHonzonProxy simpleProxy = new GetUpdateHonzonProxy();
        userId = params[0];
        lang = params[1];
        try {
            final GetUpdatedHonzonResponseVo responseVo = simpleProxy.run(userId, lang);

            return responseVo;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(GetUpdatedHonzonResponseVo responseVo) {
        EventBus.getDefault().post(new GetUpdateHonzonEvent(responseVo));
    }
}