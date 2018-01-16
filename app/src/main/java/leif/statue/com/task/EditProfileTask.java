package leif.statue.com.task;

import android.os.AsyncTask;

import org.greenrobot.eventbus.EventBus;

import leif.statue.com.event.EditProfileEvent;
import leif.statue.com.event.SignUpEvent;
import leif.statue.com.proxy.EditProfileProxy;
import leif.statue.com.proxy.SignUpProxy;
import leif.statue.com.vo.EditProfileResponseVo;
import leif.statue.com.vo.SignUpResponseVo;

public class EditProfileTask extends AsyncTask<String, Void, EditProfileResponseVo> {

    private String mailAddress;
    private String password;
    private String lang;
    private String prefecture;
    private String age;
    private String gender;
    private String isNotice;

    @Override
    protected void onPreExecute() {

    }

    @Override
    protected EditProfileResponseVo doInBackground(String... params) {
        EditProfileProxy simpleProxy = new EditProfileProxy();
        mailAddress = params[0];
        password = params[1];
        lang = params[2];
        prefecture = params[3];
        age = params[4];
        gender = params[5];
        isNotice = params[6];
        try {
            final EditProfileResponseVo responseVo = simpleProxy.run(mailAddress, password, lang, prefecture, age, gender, isNotice);

            return responseVo;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(EditProfileResponseVo responseVo) {
        EventBus.getDefault().post(new EditProfileEvent(responseVo));
    }
}