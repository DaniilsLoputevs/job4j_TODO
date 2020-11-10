package store;

import hibernate.BasicHbmStore;
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
    private final BasicHbmStore<Task> core = new BasicHbmStore<>("Task");


    public void add(Task task) {
        core.add(task);
    }

    public Task getById(int id) {
        String temp = "from Task as mt join fetch mt.categories where mt.id=" + id;
        return core.getProvider().exeQuerySingleRsl(temp);
    }

    public List<Task> getAll() {
        String temp = "select distinct mt from Task as mt join fetch mt.categories";
        return core.getProvider().exeQueryList(temp);
    }

    public void delete(int id) {
        core.delete(id);
    }

    public void update(Task task) {
        core.update(task);
    }

    public void updateAll(List<Task> tasks) {
        core.updateAll(tasks);
    }
}