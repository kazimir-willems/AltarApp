package leif.statue.com.task;

import android.os.AsyncTask;

import org.greenrobot.eventbus.EventBus;

import leif.statue.com.event.ContactUsEvent;
import leif.statue.com.event.LoginEvent;
import leif.statue.com.proxy.ContactUsProxy;
import leif.statue.com.proxy.LoginProxy;
import leif.statue.com.vo.ContactUsResponseVo;
import leif.statue.com.vo.LoginResponseVo;

public class ContactUsTask extends AsyncTask<String, Void, ContactUsResponseVo> {

    private String name;
    private String mailAddress;
    private String lang;
    private String contents;

    @Override
    protected void onPreExecute() {

    }

    @Override
    protected ContactUsResponseVo doInBackground(String... params) {
        ContactUsProxy simpleProxy = new ContactUsProxy();
        name = params[0];
        mailAddress = params[1];
        lang = params[2];
        contents = params[3];
        try {
            final ContactUsResponseVo responseVo = simpleProxy.run(name, mailAddress, lang, contents);

            return responseVo;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(ContactUsResponseVo responseVo) {
        EventBus.getDefault().post(new ContactUsEvent(responseVo));
    }
}