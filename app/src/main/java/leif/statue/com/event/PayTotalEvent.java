package leif.statue.com.event;

import leif.statue.com.vo.ForgotPasswordResponseVo;
import leif.statue.com.vo.PayTotalResponseVo;

/**
 * Created by Leif on 12/8/2017.
 */

public class PayTotalEvent {
    private PayTotalResponseVo responseVo;

    public PayTotalEvent(PayTotalResponseVo responseVo) {
        this.responseVo = responseVo;
    }

    public PayTotalResponseVo getResponse() {
        return responseVo;
    }
}
