package leif.statue.com.event;

import leif.statue.com.vo.LoginResponseVo;

/**
 * Created by Leif on 12/8/2017.
 */

public class LoginEvent {
    private LoginResponseVo responseVo;

    public LoginEvent(LoginResponseVo responseVo) {
        this.responseVo = responseVo;
    }

    public LoginResponseVo getResponse() {
        return responseVo;
    }
}
