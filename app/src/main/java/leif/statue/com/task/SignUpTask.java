package leif.statue.com.task;

import android.os.AsyncTask;

import org.greenrobot.eventbus.EventBus;

import leif.statue.com.event.LoginEvent;
import leif.statue.com.event.SignUpEvent;
import leif.statue.com.proxy.LoginProxy;
import leif.statue.com.proxy.SignUpProxy;
import leif.statue.com.vo.LoginResponseVo;
import leif.statue.com.vo.SignUpResponseVo;

public class SignUpTask extends AsyncTask<String, Void, SignUpResponseVo> {

    private String mailAddress;
    private String password;
    private String lang;
    private String prefecture;
    private String age;
    private String gender;
    private String isNotice;
    private String plan;

    @Override
    protected void onPreExecute() {

    }

    @Override
    protected SignUpResponseVo doInBackground(String... params) {
        SignUpProxy simpleProxy = new SignUpProxy();
        mailAddress = params[0];
        password = params[1];
        lang = params[2];
        prefecture = params[3];
        age = params[4];
        gender = params[5];
        isNotice = params[6];
        plan = params[7];
        try {
            final SignUpResponseVo responseVo = simpleProxy.run(mailAddress, password, lang, prefecture, age, gender, isNotice, plan);

            return responseVo;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(SignUpResponseVo responseVo) {
        EventBus.getDefault().post(new SignUpEvent(responseVo));
    }
}