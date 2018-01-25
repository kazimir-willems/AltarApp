package leif.statue.com.event;

import leif.statue.com.vo.UploadCompleteResponseVo;
import leif.statue.com.vo.UploadHonjouResponseVo;

/**
 * Created by Leif on 12/8/2017.
 */

public class UploadCompleteEvent {
    private UploadCompleteResponseVo responseVo;

    public UploadCompleteEvent(UploadCompleteResponseVo responseVo) {
        this.responseVo = responseVo;
    }

    public UploadCompleteResponseVo getResponse() {
        return responseVo;
    }
}
