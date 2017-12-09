package leif.statue.com.model;

/**
 * Created by Leif on 12/6/2017.
 */

public class CountsItem {
    private int beforeDays;
    private int counts;

    public CountsItem() {
        beforeDays = 0;
        counts = 0;
    }

    public CountsItem(int arg0, int arg1) {
        this.beforeDays = arg0;
        this.counts = arg1;
    }

    public void setBeforeDays(int value) {
        this.beforeDays = value;
    }

    public int getBeforeDays() {
        return beforeDays;
    }

    public void setCounts(int value) {
        this.counts = value;
    }

    public int getCounts() {
        return counts;
    }
}
