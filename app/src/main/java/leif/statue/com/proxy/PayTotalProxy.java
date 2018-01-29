package leif.statue.com.proxy;

import com.google.gson.Gson;

import java.io.IOException;

import leif.statue.com.util.URLManager;
import leif.statue.com.vo.ForgotPasswordResponseVo;
import leif.statue.com.vo.PayTotalResponseVo;

public class PayTotalProxy extends BaseProxy {

    public PayTotalResponseVo run(String userId, String lang, String payDate, String orderId, String plan) throws IOException {
        String params = "user_id=" + userId + "&lang=" + lang + "&pay_date=" + payDate + "&order_id=" + orderId + "&plan=" + plan;

        String contentString = getPlain(URLManager.getPayTotalURL(), params);

        PayTotalResponseVo responseVo = new Gson().fromJson(contentString, PayTotalResponseVo.class);

        return responseVo;
    }
}
