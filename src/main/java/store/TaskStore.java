package store;

import hibernate.HbmProvider;
import models.Task;

import java.util.List;

public class TaskStore {
    private static final class Lazy {
        private static final TaskStore INST = new TaskStore();
    }

    public static TaskStore instOf() {
        return TaskStore.Lazy.INST;
    }


    /* Class description */
    private final BasicHbmStore<Task> core = new BasicHbmStore<>();


    public void add(Task task) {
      core.add(task);
    }

    public Task getById(int id) {
      return core.getById(id, "Task", "categories");
    }

    public List<Task> getAll() {
        return core.getAll("Task", "categories");
    }

    public boolean delete(int id) {
        Task temp = new Task();
        temp.setId(id);
       return core.delete(temp);
    }

    public boolean update(Task task) {
        return (boolean) HbmProvider.instOf().standardTransactionCore(session -> {
            session.update(task);
            return true;
        });
    }

    public void updateAll(Task... tasks) {
        HbmProvider.instOf().standardTransactionCore(session -> {
            for (Task task : tasks) {
                session.update(task);
            }
            return true;
        });
    }

}