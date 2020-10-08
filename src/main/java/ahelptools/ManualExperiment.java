package ahelptools;

import store.UserStore;

/**
 * manual tool - not for production.
 */
public class ManualExperiment {

//    public static String dateFormat(String format, Calendar calendar) {
//        var temp = new Timestamp(1602163405287);
//        return new SimpleDateFormat(format).format(temp);
//    }

    public static void main(String[] args) {
        CustomLog.log("START");
//        UserStore.instOf().add(new User(777, "rootNew", "root", "root"));
        UserStore.instOf().delete(34);
        CustomLog.log("FINISH");
    }

}
