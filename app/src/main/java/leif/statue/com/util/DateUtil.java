package leif.statue.com.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {

    public static String getDateFromFullTime(String value) {
        String date = value.split(" ")[0];
        return date;
    }

    public static String getTimeFromFullTime(String value) {
        String time = value.split(" ")[1];
        return time;
    }

    public static String getCurDate() {
        Calendar c = Calendar.getInstance();

        String year = String.valueOf(c.get(Calendar.YEAR));

        String month = String.valueOf(c.get(Calendar.MONTH) + 1);
        if (month.length() != 2)
            month = "0" + month;

        String day = String.valueOf(c.get(Calendar.DAY_OF_MONTH));
        if (day.length() != 2)
            day = "0" + day;

        StringBuffer sbBuffer = new StringBuffer();
        sbBuffer.append(year + "-" + month + "-" + day);

        return sbBuffer.toString();
    }

    public static String timeStampToDate(Date date) {
        DateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        String reportDate = df.format(date);

        return reportDate;
    }

}
