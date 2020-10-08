package hibernate;

import models.Task;
import store.TaskStore;

public class HbmManual {
        public static void main(String[] args) {
//        var first = new Task(1, "first", null, false);
//        var second = new Task(2, "second", null, false);
//        var third = new Task(3, "third", null, false);
//        new TaskStore().add(first);
//        new TaskStore().add(second);
//        new TaskStore().add(third);

//            var user = new User(0, "testName", "testEmail", "testPassword");
//            UserStore.instOf().add(user);

//            var temp = UserStore.instOf().getByEmail("testEmail");
//            System.out.println(temp);

            var temp = new Task();
            TaskStore.instOf().add(temp);

            System.out.println("FINISH MANUAL RUN");
    }
}
