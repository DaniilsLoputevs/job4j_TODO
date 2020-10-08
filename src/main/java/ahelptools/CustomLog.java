package ahelptools;

public class CustomLog {

    public static void log(String desc, Object object) {
        System.out.println("Custom LOG: " + desc + ": " + object);
    }
    public static void log(String desc) {
        System.out.println("Custom LOG: " + desc);
    }

}
