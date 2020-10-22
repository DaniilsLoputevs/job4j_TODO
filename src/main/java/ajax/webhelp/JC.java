package ajax.webhelp;

import java.util.List;

/**
 * JC JsonCollector - manual JSON writer.
 * Use thi API for collect PART of object OR warp pair of key-value into json string.
 *
 * @author Daniils Loputevs
 * @since 16.10.2020.
 */
public class JC {

    public static String wrapList(List<String> pairsList) {
        return wrapCore('[', ']', pairsList);
    }

    public static String wrapList(String... pairsList) {
        return wrapCore('[', ']', pairsList);
    }

    public static String wrapObject(List<String> pairs) {
        return wrapCore('{', '}', pairs);
    }

    public static String wrapObject(String... pairs) {
        return wrapCore('{', '}', pairs);
    }

    private static String wrapCore(char firstWrap, char secondWrap, String... pairs) {
        if (pairs.length != 0) {
            var rsl = new StringBuilder();

            for (int i = 0; i < pairs.length; i++) {
                rsl.append(pairs[i]);
                if (i <= pairs.length - 2) {
                    rsl.append(", ");
                }
            }
            return firstWrap + rsl.toString() + secondWrap;
        } else {
            return String.valueOf(firstWrap + ' ' + firstWrap);
        }
    }

    private static String wrapCore(char firstWrap, char secondWrap, List<String> pairs) {
        if (!pairs.isEmpty()) {
            var rsl = new StringBuilder();

            for (int i = 0; i < pairs.size(); i++) {
                rsl.append(pairs.get(i));
                if (i <= pairs.size() - 2) {
                    rsl.append(", ");
                }
            }
            return firstWrap + rsl.toString() + secondWrap;
        } else {
            return String.valueOf(firstWrap + ' ' + firstWrap);
        }
    }


    public static String collect(String name, String object) {
        String firstPart = "\"" + name + "\":";
        if (object == null || object.startsWith("[") || object.startsWith("{")) {
            return firstPart + object;
        } else {
            return firstPart + "\"" + object + "\"";
        }

    }

    public static String collect(String name, int num) {
        return "\"" + name + "\":" + num;
    }

    public static String collect(String name, boolean bool) {
        return "\"" + name + "\":" + bool;
    }


    /* For future projects, explain how it work. */


//    public static void main(String[] args) {
//        String numVariable = "numVariable";
//        int number = 12345;
//        String variable = "variable";
//        String value = "value";
//
//
//        String oneArgStringList = wrapList(
//                collect(numVariable, number)
//        );
//        CustomLog.log("oneArgStringList", oneArgStringList);
//        String manyArgStringList = wrapList(
//                collect(numVariable, number),
//                collect(variable, variable),
//                collect(variable, value),
//                collect(numVariable, number),
//                collect(variable, variable),
//                collect(variable, value)
//        );
//        CustomLog.log("manyArgStringList", manyArgStringList);
//
//        String complexObject = wrapObject(
//                collect("name", "Daniils"),
//                collect("age", 111),
//                collect("address", wrapObject(
//                        collect("city", "Vladivostok"),
//                        collect("phone_code", 44)
//                        )
//                ),
//                collect("phones", wrapList(
//                        collect("main", 1234),
//                        collect("add", 987)
//                        )
//                )
//        );
//        String complexObjectTwo = wrapObject(
//                collect("name", "Daniils"),
//                collect("age", 111),
//                collect("address", wrapObject(
//                        collect("city", "Vladivostok"),
//                        collect("phone_code", 44)
//                        )
//                ),
//                collect("phones", wrapList(
//                        collect("main", 1234),
//                        collect("add", 987)
//                        )
//                )
//        );
//
//
//        CustomLog.log("test list", wrapList(complexObject, complexObjectTwo));
//
//        CustomLog.log("Production test");
//
//        String firstObject = wrapObject(
//                collect("id", 49),
//                collect("description", "updTaskTable"),
//                collect("creator", "guest"),
//                collect("created", "2020-10-08 14:23:25"),
//                collect("done", false),
//                collect("done", wrapList(wrapObject(
//                        collect("id", 12),
//                        collect("name", "updTaskTable")
//                        ))
//                )
//        );
//        String secondObject = wrapObject(
//                collect("id", 49),
//                collect("description", "updTaskTable"),
//                collect("creator", "guest"),
//                collect("created", "2020-10-08 14:23:25"),
//                collect("done", false),
//                collect("category", wrapList(wrapObject(
//                        collect("id", 12),
//                        collect("name", "updTaskTable")
//                        ))
//                )
//        );
//        String rsl = wrapList(firstObject, secondObject);
//        CustomLog.log("rsl", rsl);
//    }

}