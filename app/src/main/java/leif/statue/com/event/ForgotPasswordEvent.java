package leif.statue.com.event;

import leif.statue.com.vo.ForgotPasswordResponseVo;
import leif.statue.com.vo.SignUpResponseVo;

/**
 * Created by Leif on 12/8/2017.
 */

public class ForgotPasswordEvent {
    private ForgotPasswordResponseVo responseVo;

    public ForgotPasswordEvent(ForgotPasswordResponseVo responseVo) {
        this.responseVo = responseVo;
    }

    public ForgotPasswordResponseVo getResponse() {
        return responseVo;
    }
}
