package store;

import hibernate.HbmProvider;
import models.User;

import java.util.List;

public class UserStore {
    private static final class Lazy {
        private static final UserStore INST = new UserStore();
    }

    public static UserStore instOf() {
        return UserStore.Lazy.INST;
    }


    /* Class description */
    private final BasicHbmStore<User> core = new BasicHbmStore<>();


    public void add(User user) {
        core.add(user);
    }

    public User getByEmail(String email) {
        var temp = (List<User>) HbmProvider.instOf().standardTransactionCore(session ->
                session.createQuery("from User where email="
                        + "\'" + email + "\'")
                        .list()
        );
        return getUserOrEmptyUser(temp);
    }

    public User getByName(String name) {
        var temp = (List<User>) HbmProvider.instOf().standardTransactionCore(session ->
                session.createQuery("from User where name="
                        + "\'" + name + "\'")
                        .list()
        );
        return getUserOrEmptyUser(temp);
    }

    public boolean delete(int id) {
        var temp = new User();
        temp.setId(id);
        return core.delete(temp);
    }

    private User getUserOrEmptyUser(List<User> list) {
        if (list.isEmpty()) {
            return new User();
        } else {
            return list.get(0);
        }
    }
}
