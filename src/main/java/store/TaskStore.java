package store;

import hibernate.HbmProvider;
import models.Task;
import models.User;

import java.util.List;

public class TaskStore {
    private static final class Lazy {
        private static final TaskStore INST = new TaskStore();
    }

    public static TaskStore instOf() {
        return TaskStore.Lazy.INST;
    }


    public void add(Task task) {
        HbmProvider.instOf().standardTransactionCore(session -> {
            session.save(task);
            return task;
        });
    }

    public Task getById(int id) {
        return (Task) HbmProvider.instOf().standardTransactionCore(session ->
                session.createQuery("from Task where id=" + id)
                        .getSingleResult()
        );
    }

    public List<Task> getAll() {
        return (List<Task>) HbmProvider.instOf().standardTransactionCore(session ->
                session.createQuery("from Task")
                        .list()
        );
    }

    public boolean delete(int id) {
        Task temp = new Task(id, "", null, false, new User());
        HbmProvider.instOf().standardTransactionCore(session -> {
            session.delete(temp);
            return true;
        });
        return true;
    }

    public boolean update(Task task) {
        return (boolean) HbmProvider.instOf().standardTransactionCore(session -> {
            session.update(task);
            return true;
        });
    }

    public boolean updateAll(Task... tasks) {
        return (boolean) HbmProvider.instOf().standardTransactionCore(session -> {
            for (Task task : tasks) {
                session.update(task);
            }
            return true;
        });
    }

}