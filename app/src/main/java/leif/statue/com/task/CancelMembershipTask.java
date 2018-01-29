package leif.statue.com.task;

import android.os.AsyncTask;

import org.greenrobot.eventbus.EventBus;

import leif.statue.com.event.CancelMembershipEvent;
import leif.statue.com.event.EditProfileEvent;
import leif.statue.com.proxy.CancelMembershipProxy;
import leif.statue.com.proxy.EditProfileProxy;
import leif.statue.com.vo.CancelMembershipResponseVo;
import leif.statue.com.vo.EditProfileResponseVo;

public class CancelMembershipTask extends AsyncTask<String, Void, CancelMembershipResponseVo> {

    private String userId;
    private String lang;

    @Override
    protected void onPreExecute() {

    }

    @Override
    protected CancelMembershipResponseVo doInBackground(String... params) {
        CancelMembershipProxy simpleProxy = new CancelMembershipProxy();
        userId = params[0];
        lang = params[1];

        try {
            final CancelMembershipResponseVo responseVo = simpleProxy.run(userId, lang);

            return responseVo;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(CancelMembershipResponseVo responseVo) {
        EventBus.getDefault().post(new CancelMembershipEvent(responseVo));
    }
}