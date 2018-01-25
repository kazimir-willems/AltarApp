package leif.statue.com.task;

import android.os.AsyncTask;

import org.greenrobot.eventbus.EventBus;

import leif.statue.com.event.GetAltarListEvent;
import leif.statue.com.event.LoginEvent;
import leif.statue.com.proxy.GetAltarListProxy;
import leif.statue.com.proxy.LoginProxy;
import leif.statue.com.vo.GetAltarResponseVo;
import leif.statue.com.vo.LoginResponseVo;

public class GetAltarListTask extends AsyncTask<String, Void, GetAltarResponseVo> {

    private String lang;

    @Override
    protected void onPreExecute() {

    }

    @Override
    protected GetAltarResponseVo doInBackground(String... params) {
        lang = params[0];
        GetAltarListProxy simpleProxy = new GetAltarListProxy();
        try {
            final GetAltarResponseVo responseVo = simpleProxy.run(lang);

            return responseVo;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(GetAltarResponseVo responseVo) {
        EventBus.getDefault().post(new GetAltarListEvent(responseVo));
    }
}