package leif.statue.com.event;

import leif.statue.com.vo.GetAltarResponseVo;
import leif.statue.com.vo.LoginResponseVo;

/**
 * Created by Leif on 12/8/2017.
 */

public class GetAltarListEvent {
    private GetAltarResponseVo responseVo;

    public GetAltarListEvent(GetAltarResponseVo responseVo) {
        this.responseVo = responseVo;
    }

    public GetAltarResponseVo getResponse() {
        return responseVo;
    }
}
