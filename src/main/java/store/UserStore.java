package store;

import hibernate.BasicHbmStore;
import models.User;

public class UserStore {
    private static final class Lazy {
        private static final UserStore INST = new UserStore();
    }

    public static UserStore instOf() {
        return UserStore.Lazy.INST;
    }

    /* Class description */
    private final BasicHbmStore<User> core = new BasicHbmStore<>("User");


    public void add(User user) {
        core.add(user);
    }

    public User getByEmail(String email) {
        var temp = core.getBy("email", email);
        return core.getFirstOrEmpty(temp, new User());
    }

    public User getByName(String name) {
        var temp = core.getBy("name", name);
        return core.getFirstOrEmpty(temp, new User());
    }

    public void delete(int id) {
      core.delete(id);
    }
}