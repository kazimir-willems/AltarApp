package leif.statue.com.model;

import java.io.Serializable;

/**
 * Created by Leif on 12/5/2017.
 */

public class BuddhistItem implements Serializable{
    public int id;
    public String url;
    public String butsudan;
    public String confirm;

    public void setId(int value) {
        this.id = value;
    }

    public int getId() {
        return id;
    }

    public void setUrl(String value) {
        this.url = value;
    }

    public String getUrl() {
        return url;
    }

    public void setButsudan(String value) {
        this.butsudan = value;
    }

    public String getButsudan() {
        return butsudan;
    }

    public void setConfirm(String value) {
        this.confirm = value;
    }

    public String getConfirm() {
        return confirm;
    }
}
