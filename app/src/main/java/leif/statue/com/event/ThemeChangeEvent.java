package leif.statue.com.event;

import leif.statue.com.vo.LoginResponseVo;
import leif.statue.com.vo.ThemeChangeResponseVo;

/**
 * Created by Leif on 12/8/2017.
 */

public class ThemeChangeEvent {
    private ThemeChangeResponseVo responseVo;

    public ThemeChangeEvent(ThemeChangeResponseVo responseVo) {
        this.responseVo = responseVo;
    }

    public ThemeChangeResponseVo getResponse() {
        return responseVo;
    }
}
