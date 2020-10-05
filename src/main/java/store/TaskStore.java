package store;

import models.Task;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.util.List;
import java.util.function.Function;

public class TaskStore {
    private final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
            .configure().build();
    private final SessionFactory sf = new MetadataSources(registry)
            .buildMetadata().buildSessionFactory();

    private static final class Lazy {
        private static final TaskStore INST = new TaskStore();
    }

    public static TaskStore instOf() {
        return TaskStore.Lazy.INST;
    }

//    public static void main(String[] args) {
//        var first = new Task(1, "first", null, false);
//        var second = new Task(2, "second", null, false);
//        var third = new Task(3, "third", null, false);
//        new TaskStore().add(first);
//        new TaskStore().add(second);
//        new TaskStore().add(third);
//    }

    public void add(Task task) {
        transactionCore(session -> {
            session.save(task);
            return task;
        });
    }

    public Task getById(int id) {
        return (Task) transactionCore(session ->
                session.createQuery("from Task where id=" + id)
                        .getSingleResult()
        );
    }
    public List<Task> getAll() {
        return (List<Task>) transactionCore(session ->
                session.createQuery("from Task")
                        .list()
        );
    }
    public boolean delete(int id) {
        Task temp = new Task(id, "", null, false);
        transactionCore(session -> {
            session.delete(temp);
            return true;
        });
        return true;
    }
    public boolean update(Task task) {
        return (boolean) transactionCore(session -> {
            session.update(task);
            return true;
        });
    }

    private Object transactionCore(Function<Session, Object> action) {
        Session session = sf.openSession();
        session.beginTransaction();

        Object rsl = action.apply(session);

        session.getTransaction().commit();
        session.close();

        return rsl;
    }

}
