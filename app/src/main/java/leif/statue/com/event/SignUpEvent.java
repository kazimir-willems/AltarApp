package leif.statue.com.event;

import leif.statue.com.vo.LoginResponseVo;
import leif.statue.com.vo.SignUpResponseVo;

/**
 * Created by Leif on 12/8/2017.
 */

public class SignUpEvent {
    private SignUpResponseVo responseVo;

    public SignUpEvent(SignUpResponseVo responseVo) {
        this.responseVo = responseVo;
    }

    public SignUpResponseVo getResponse() {
        return responseVo;
    }
}
