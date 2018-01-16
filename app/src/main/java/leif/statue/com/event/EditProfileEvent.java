package leif.statue.com.event;

import leif.statue.com.vo.EditProfileResponseVo;
import leif.statue.com.vo.SignUpResponseVo;

/**
 * Created by Leif on 12/8/2017.
 */

public class EditProfileEvent {
    private EditProfileResponseVo responseVo;

    public EditProfileEvent(EditProfileResponseVo responseVo) {
        this.responseVo = responseVo;
    }

    public EditProfileResponseVo getResponse() {
        return responseVo;
    }
}
