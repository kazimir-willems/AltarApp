package leif.statue.com.vo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Leif on 12/8/2017.
 */

public class LoginResponseVo extends BaseResponseVo{
    public int user_id;
    public String email;
    public int prefecture;
    public int age;
    public int gender;
    public int is_notice;
    public String confirm;
    public String honzon;
    public int music;
    public String last_img;
    public String count_history;
    public int plan;
}
