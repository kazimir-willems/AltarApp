package leif.statue.com.task;

import android.os.AsyncTask;

import org.greenrobot.eventbus.EventBus;

import leif.statue.com.event.ForgotPasswordEvent;
import leif.statue.com.event.PayTotalEvent;
import leif.statue.com.proxy.ForgotPasswordProxy;
import leif.statue.com.proxy.PayTotalProxy;
import leif.statue.com.vo.ForgotPasswordResponseVo;
import leif.statue.com.vo.PayTotalResponseVo;

public class PayTotalTask extends AsyncTask<String, Void, PayTotalResponseVo> {

    private String userId;
    private String lang;
    private String payDate;
    private String orderId;
    private String plan;

    @Override
    protected void onPreExecute() {

    }

    @Override
    protected PayTotalResponseVo doInBackground(String... params) {
        PayTotalProxy simpleProxy = new PayTotalProxy();
        userId = params[0];
        lang = params[1];
        payDate = params[2];
        orderId = params[3];
        plan = params[4];
        try {
            final PayTotalResponseVo responseVo = simpleProxy.run(userId, lang, payDate, orderId, plan);

            return responseVo;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(PayTotalResponseVo responseVo) {
        EventBus.getDefault().post(new PayTotalEvent(responseVo));
    }
}