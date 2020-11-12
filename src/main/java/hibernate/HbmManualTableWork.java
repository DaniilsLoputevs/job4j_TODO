package hibernate;

import models.Category;
import models.Task;
import models.User;
import store.CategoryStore;
import store.TaskStore;
import store.UserStore;

import java.util.List;

public class HbmManualTableWork {
    public static void main(String[] args) {

        // init Table users
        User root = (new User(0, "root", "root", "root"));
        User guest = new User(0, "guest", "", "");
        User maxim = new User(0, "Maxim", "maxim@mail.test", "password");
        UserStore.instOf().add(root);
        UserStore.instOf().add(guest);
        UserStore.instOf().add(maxim);

        // init Table categories
        Category daily =  Category.of("Daily");
        Category weekly =  Category.of("Weekly");
        Category monthly =  Category.of("Monthly");
        Category global =  Category.of("Global");
        CategoryStore.instOf().addAll(List.of(
              daily, weekly, monthly, global
        ));

        // init Table tasks
        TaskStore.instOf().addAll(List.of(
                new Task("Go to shop",
                        List.of(weekly),
                        root
                ),
                new Task("Ring to grandMother",
                        List.of(daily),
                        guest
                ),
                new Task("Learn English",
                        List.of(monthly, global),
                        root
                ),
                new Task("Complete home improvement",
                        List.of(weekly, monthly, global),
                        maxim
                ))
        );
    }

}
