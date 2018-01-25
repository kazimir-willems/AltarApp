package leif.statue.com.model;

import leif.statue.com.util.StringUtil;

/**
 * Created by Leif on 12/6/2017.
 */

public class CountsItem {
    private String date;
    private int counts;

    public CountsItem() {
        date = "";
        counts = 0;
    }

    public CountsItem(String arg0, int arg1) {
        this.date = arg0;
        this.counts = arg1;
    }

    public void setDate(String value) {
        this.date = value;
    }

    public String getDate() {
        return date;
    }

    public void setCounts(int value) {
        this.counts = value;
    }

    public int getCounts() {
        return counts;
    }
}
