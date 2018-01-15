package leif.statue.com.model;

import java.io.Serializable;

/**
 * Created by Leif on 12/5/2017.
 */

public class AltarItem implements Serializable{
    private int id;
    private String url;
    private String theme;

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

    public void setTheme(String value) {
        this.theme = value;
    }

    public String getTheme() {
        return theme;
    }
}
