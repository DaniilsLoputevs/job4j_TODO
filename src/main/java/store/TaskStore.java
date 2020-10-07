package store;

import hibernate.HbmProvider;
import models.Task;
import models.User;

import java.util.List;

public class TaskStore {
    private final HbmProvider hbmProvider = HbmProvider.instOf();

    private static final class Lazy {
        private static final TaskStore INST = new TaskStore();
    }

    public static TaskStore instOf() {
        return TaskStore.Lazy.INST;
    }


    public void add(Task task) {
        hbmProvider.standardTransactionCore(session -> {
            session.save(task);
            return task;
        });
    }

    public Task getById(int id) {
        return (Task) hbmProvider.standardTransactionCore(session ->
                session.createQuery("from Task where id=" + id)
                        .getSingleResult()
        );
    }

    public List<Task> getAll() {
        return (List<Task>) hbmProvider.standardTransactionCore(session ->
                session.createQuery("from Task")
                        .list()
        );
    }

    public boolean delete(int id) {
        Task temp = new Task(id, "", null, false, new User());
        hbmProvider.standardTransactionCore(session -> {
            session.delete(temp);
            return true;
        });
        return true;
    }

    public boolean update(Task task) {
        return (boolean) hbmProvider.standardTransactionCore(session -> {
            session.update(task);
            return true;
        });
    }

    public boolean updateAll(Task... tasks) {
        return (boolean) hbmProvider.standardTransactionCore(session -> {
            for (Task task : tasks) {
                session.update(task);
            }
            return true;
        });
    }

}