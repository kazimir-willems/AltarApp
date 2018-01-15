package leif.statue.com.event;

import leif.statue.com.vo.GetAltarResponseVo;
import leif.statue.com.vo.GetBuddhistResponseVo;

/**
 * Created by Leif on 12/8/2017.
 */

public class GetBuddhistListEvent {
    private GetBuddhistResponseVo responseVo;

    public GetBuddhistListEvent(GetBuddhistResponseVo responseVo) {
        this.responseVo = responseVo;
    }

    public GetBuddhistResponseVo getResponse() {
        return responseVo;
    }
}
