package leif.statue.com.task;

import android.os.AsyncTask;

import org.greenrobot.eventbus.EventBus;

import leif.statue.com.event.GetAltarListEvent;
import leif.statue.com.event.GetBuddhistListEvent;
import leif.statue.com.proxy.GetAltarListProxy;
import leif.statue.com.proxy.GetBuddhistListProxy;
import leif.statue.com.vo.GetAltarResponseVo;
import leif.statue.com.vo.GetBuddhistResponseVo;

public class GetBuddhistListTask extends AsyncTask<String, Void, GetBuddhistResponseVo> {

    private String theme = "";
    private String lang = "";

    @Override
    protected void onPreExecute() {

    }

    @Override
    protected GetBuddhistResponseVo doInBackground(String... params) {
        theme = params[0];
        lang = params[1];

        GetBuddhistListProxy simpleProxy = new GetBuddhistListProxy();
        try {
            final GetBuddhistResponseVo responseVo = simpleProxy.run(theme, lang);

            return responseVo;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(GetBuddhistResponseVo responseVo) {
        EventBus.getDefault().post(new GetBuddhistListEvent(responseVo));
    }
}