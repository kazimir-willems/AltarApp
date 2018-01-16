package leif.statue.com.event;

import leif.statue.com.vo.ContactUsResponseVo;
import leif.statue.com.vo.LoginResponseVo;

/**
 * Created by Leif on 12/8/2017.
 */

public class ContactUsEvent {
    private ContactUsResponseVo responseVo;

    public ContactUsEvent(ContactUsResponseVo responseVo) {
        this.responseVo = responseVo;
    }

    public ContactUsResponseVo getResponse() {
        return responseVo;
    }
}
