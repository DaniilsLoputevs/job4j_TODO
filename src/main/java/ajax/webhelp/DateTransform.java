package ajax.webhelp;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTransform {
    public static final String PATTERN_TABLE_TIMESTAMP = "yyyy-MM-dd HH:mm:ss";

    public static String toFront(Timestamp timestamp) {
        String rsl = timestamp.toString();
        return rsl.substring(0, rsl.lastIndexOf('.'));
    }

    public static Timestamp toBack(String timestamp) {
        Timestamp rsl = null;
        try {
            Date date = new SimpleDateFormat(PATTERN_TABLE_TIMESTAMP).parse(timestamp);
            rsl = new Timestamp(date.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return rsl;
    }

    /* For future projects, explain how it work. */

//    public static void main(String[] args) {
//        CustomLog.log("START");
//        CustomLog.log("toFront");
//        var timestamp = new Timestamp(Long.parseLong("1602163405287"));
//        CustomLog.log("original", timestamp);
//        CustomLog.log("result  ", toFront(timestamp));
//
//
//        CustomLog.log("toBack");
//        String frontTimestamp = "2020-10-08 14:23:25";
//        CustomLog.log("original", frontTimestamp);
//        CustomLog.log("result  ", toBack(frontTimestamp));
//
//
//        CustomLog.log("FINISH");
//    }
}
