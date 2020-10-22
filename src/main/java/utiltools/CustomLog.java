package utiltools;

import java.util.List;

/**
 * Print arr with: Arrays.asList(arr) - this is the way.
 */
public class CustomLog {
    private static final String PREFIX = "Custom LOG: ";

    public static void log(String info) {
        System.out.println(PREFIX + info);
    }

    public static <T> void log(String desc, T anything) {
        if (anything instanceof String) {
            log(desc + ": \"" + anything + '\"');
        } else if (anything instanceof List) {
            logList(desc, (List) anything);
        } else if (isArray(anything)) {
            logArray(desc, anything);
        } else {
            log(desc + ": " + anything);
        }
    }

    private static boolean isArray(Object object) {
        return object.getClass().getName().startsWith("[");
    }

    private static <T> void logList(String listName, List<T> list) {
        if (!list.isEmpty()) {
            log("Print List: " + listName);

            boolean isString = list.get(0) instanceof String;
            int index = 0;
            for (T each : list) {
                printElement("" + each, index++, isString);
            }
        } else {
            log(listName + " - is empty.");
        }
    }

    /**
     * https://www.geeksforgeeks.org/array-primitive-type-object-java/
     * see the link for add more array type.
     *
     * @param arrName-
     * @param array-
     */
    private static void logArray(String arrName, Object array) {
        String arrTypeName = array.getClass().getName();
        int printIndex = 0;
        log("Print Array: " + arrName);
        if (arrTypeName.equals("[Ljava.lang.String;")) {
            for (var each : (String[]) array) {
                printElement(each, printIndex++, true);
            }
        } else if (arrTypeName.equals("[I")) {
            for (var each : (int[]) array) {
                printElement("" + each, printIndex++, false);
            }
        } else if (arrTypeName.equals("[Z")) {
            for (var each : (boolean[]) array) {
                printElement("" + each, printIndex++, false);
            }
        } else if (arrTypeName.equals("[D")) {
            for (var each : (double[]) array) {
                printElement("" + each, printIndex++, false);
            }
        } else if (arrTypeName.equals("[S")) {
            for (var each : (short[]) array) {
                printElement("" + each, printIndex++, false);
            }
        } else if (arrTypeName.equals("[B")) {
            for (var each : (short[]) array) {
                printElement("" + each, printIndex++, false);
            }
        }
    }

    private static void printElement(String elem, int index, boolean isString) {
        if (isString) {
            log(index + ": \"" + elem + "\"");
        } else {
            log(index + ": " + elem);
        }
    }

}