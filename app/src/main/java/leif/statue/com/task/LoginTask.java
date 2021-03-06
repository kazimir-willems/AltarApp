package leif.statue.com.task;

import android.os.AsyncTask;

import org.greenrobot.eventbus.EventBus;

import leif.statue.com.event.LoginEvent;
import leif.statue.com.proxy.LoginProxy;
import leif.statue.com.vo.LoginResponseVo;

public class LoginTask extends AsyncTask<String, Void, LoginResponseVo> {

    private String mailAddress;
    private String password;
    private String lang;
    private String token;
    private String isCancel;
    private String expireDate;
    private String plan;

    @Override
    protected void onPreExecute() {

    }

    @Override
    protected LoginResponseVo doInBackground(String... params) {
        LoginProxy simpleProxy = new LoginProxy();
        mailAddress = params[0];
        password = params[1];
        lang = params[2];
        token = params[3];
        isCancel = params[4];
        expireDate = params[5];
        plan = params[6];
        try {
            final LoginResponseVo responseVo = simpleProxy.run(mailAddress, password, lang, token, isCancel, expireDate, plan);

            return responseVo;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(LoginResponseVo responseVo) {
        EventBus.getDefault().post(new LoginEvent(responseVo));
    }
}