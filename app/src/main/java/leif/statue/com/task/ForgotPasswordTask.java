package leif.statue.com.task;

import android.os.AsyncTask;

import org.greenrobot.eventbus.EventBus;

import leif.statue.com.event.ForgotPasswordEvent;
import leif.statue.com.event.SignUpEvent;
import leif.statue.com.proxy.ForgotPasswordProxy;
import leif.statue.com.proxy.SignUpProxy;
import leif.statue.com.vo.ForgotPasswordResponseVo;
import leif.statue.com.vo.SignUpResponseVo;

public class ForgotPasswordTask extends AsyncTask<String, Void, ForgotPasswordResponseVo> {

    private String mailAddress;
    private String lang;

    @Override
    protected void onPreExecute() {

    }

    @Override
    protected ForgotPasswordResponseVo doInBackground(String... params) {
        ForgotPasswordProxy simpleProxy = new ForgotPasswordProxy();
        mailAddress = params[0];
        lang = params[1];
        try {
            final ForgotPasswordResponseVo responseVo = simpleProxy.run(mailAddress, lang);

            return responseVo;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(ForgotPasswordResponseVo responseVo) {
        EventBus.getDefault().post(new ForgotPasswordEvent(responseVo));
    }
}