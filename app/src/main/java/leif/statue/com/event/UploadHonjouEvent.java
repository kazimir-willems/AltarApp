package leif.statue.com.event;

import leif.statue.com.vo.LoginResponseVo;
import leif.statue.com.vo.UploadHonjouResponseVo;

/**
 * Created by Leif on 12/8/2017.
 */

public class UploadHonjouEvent {
    private UploadHonjouResponseVo responseVo;

    public UploadHonjouEvent(UploadHonjouResponseVo responseVo) {
        this.responseVo = responseVo;
    }

    public UploadHonjouResponseVo getResponse() {
        return responseVo;
    }
}
