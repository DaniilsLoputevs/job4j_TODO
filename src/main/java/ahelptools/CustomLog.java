package ahelptools;

import java.util.Arrays;
import java.util.List;

/**
 * Print arr with: Arrays.asList(arr) - this is the way.
 */
public class CustomLog {
    private static final String PREFIX = "Custom LOG: ";

    public static void log(String desc, Object obj) {
//        if (isArray(obj)) {
//            logArray(obj, desc);
//            System.out.println("nice");
//        } else {
            log(desc + ": " + obj);
//        }
    }

    public static void log(String info) {
        System.out.println(PREFIX + info);
    }

//    public static  void logArray(Object arrWithMainArr, String arrName) {
//        Object[] temp = arrWithMainArr[0];

//        if (temp.length > 0) {
//            log(arrName, Arrays.asList(temp));
//        }
//    }

//    private static boolean isArray(Object object) {
//        return (object instanceof Object[]
//                || object instanceof int[]
//        );
//    }

    public static <T> void log(String listName, List<T> list) {
        String startStr = "Print: " + listName;
        if (!list.isEmpty()) {
            log(startStr);
            boolean isString = list.get(0) instanceof String;
            int index = 0;
            for (T temp : list) {
                if (isString) {
                    log(index + ": \"" + temp + "\"");
                } else {
                    log(index + ": " + temp);
                }
                index++;
            }
        } else {
            log(startStr + " - is empty.");
        }
    }


    public static void main(String[] args) {
        int[] intArr = new int[] {1,2,3};
        String[] strArr = new String[] {"a", "b", "c"};

        log("arr", Arrays.asList(intArr));
        log("arr", Arrays.asList(strArr));


        log("intArr", intArr.getClass());
        Object test = (Object) intArr;
        log("test", test.getClass());

        System.out.println("one:" + (intArr instanceof int[]));
        System.out.println("two:" + (test instanceof int[]));
        System.out.println("two:" + (test instanceof Object[]));
        System.out.println("two:" + (test instanceof Object));

//        int[] one = (int[]) test;
//        Class<Integer> temp;
//        temp.getDeclaredMethod()
//
//
//        log("intArr", intArr);
////        log("strArr", strArr);
//
//
//        var str = "str";
//        var obj = new Object();
//        var strDown = (Object) str;
//        log("str", str.getClass());
//        log("obj", obj.getClass());
//        log("strDown", strDown.getClass());

    }

}
