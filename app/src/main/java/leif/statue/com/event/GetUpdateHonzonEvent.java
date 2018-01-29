package leif.statue.com.event;

import leif.statue.com.vo.GetUpdatedHonzonResponseVo;
import leif.statue.com.vo.PayTotalResponseVo;

/**
 * Created by Leif on 12/8/2017.
 */

public class GetUpdateHonzonEvent {
    private GetUpdatedHonzonResponseVo responseVo;

    public GetUpdateHonzonEvent(GetUpdatedHonzonResponseVo responseVo) {
        this.responseVo = responseVo;
    }

    public GetUpdatedHonzonResponseVo getResponse() {
        return responseVo;
    }
}
