package leif.statue.com.event;

import leif.statue.com.vo.CheckUpdatedHonzonResponseVo;
import leif.statue.com.vo.ContactUsResponseVo;

/**
 * Created by Leif on 12/8/2017.
 */

public class CheckUpdatedHonzonEvent {
    private CheckUpdatedHonzonResponseVo responseVo;

    public CheckUpdatedHonzonEvent(CheckUpdatedHonzonResponseVo responseVo) {
        this.responseVo = responseVo;
    }

    public CheckUpdatedHonzonResponseVo getResponse() {
        return responseVo;
    }
}
