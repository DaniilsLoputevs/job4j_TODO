package store;

import hibernate.HbmProvider;
import models.User;

import java.util.List;

public class UserStore {
    private final HbmProvider hbmProvider = HbmProvider.instOf();

    private static final class Lazy {
        private static final UserStore INST = new UserStore();
    }

    public static UserStore instOf() {
        return UserStore.Lazy.INST;
    }

    public void add(User user) {
        hbmProvider.standardTransactionCore(session -> {
            session.save(user);
            return user;
        });
    }

    public User getByEmail(String email) {
        var temp = (List<User>) hbmProvider.standardTransactionCore(session ->
                session.createQuery("from User where email="
                        + "\'" + email + "\'")
                        .list()
        );
        if (temp.isEmpty()) {
            return new User();
        } else {
            return temp.get(0);
        }
    }
}
