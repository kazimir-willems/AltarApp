package leif.statue.com.event;

import leif.statue.com.vo.LoginResponseVo;
import leif.statue.com.vo.SaveHistoryResponseVo;

/**
 * Created by Leif on 12/8/2017.
 */

public class SaveHistoryEvent {
    private SaveHistoryResponseVo responseVo;

    public SaveHistoryEvent(SaveHistoryResponseVo responseVo) {
        this.responseVo = responseVo;
    }

    public SaveHistoryResponseVo getResponse() {
        return responseVo;
    }
}
