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

    public void add(User user) {
        HbmProvider.instOf().standardTransactionCore(session -> {
            session.save(user);
            return user;
        });
    }

    public User getByEmail(String email) {
        var temp = (List<User>) HbmProvider.instOf().standardTransactionCore(session ->
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
    public User getByName(String name) {
        if ("guest".equals(name)) {
            return User.GUEST;
        }
        var temp = (List<User>) HbmProvider.instOf().standardTransactionCore(session ->
                session.createQuery("from User where name="
                        + "\'" + name + "\'")
                        .list()
        );
        if (temp.isEmpty()) {
            return new User();
        } else {
            return temp.get(0);
        }
    }
    public boolean delete(int id) {
        User temp = new User(id, "", "", "");
        HbmProvider.instOf().standardTransactionCore(session -> {
            session.delete(temp);
            return true;
        });
        return true;
    }
}
