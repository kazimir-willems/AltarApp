package leif.statue.com.event;

import leif.statue.com.vo.CancelMembershipResponseVo;
import leif.statue.com.vo.ContactUsResponseVo;

/**
 * Created by Leif on 12/8/2017.
 */

public class CancelMembershipEvent {
    private CancelMembershipResponseVo responseVo;

    public CancelMembershipEvent(CancelMembershipResponseVo responseVo) {
        this.responseVo = responseVo;
    }

    public CancelMembershipResponseVo getResponse() {
        return responseVo;
    }
}
