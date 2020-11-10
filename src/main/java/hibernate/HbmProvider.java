package hibernate;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * This class is low level abstraction functionality of Hibernate.
 * This public API use for create higher abstraction level of MVC.
 *
 * @author Daniils Loputevs(laiwiense@gmail.com)
 * @version 1.3.
 * @since 10.11.2020.
 */
public class HbmProvider {
    private final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
            .configure().build();
    private final SessionFactory sf = new MetadataSources(registry)
            .buildMetadata().buildSessionFactory();

    private static final class Lazy {
        private static final HbmProvider INST = new HbmProvider();
    }

    /**
     * Lazy Singleton.
     */
    public static HbmProvider instOf() {
        return HbmProvider.Lazy.INST;
    }


    public <T> void saveModel(T model) {
        defaultTransaction(session -> session.save(model));
    }

    public <T> void saveAllModel(List<T> models) {
        voidTransaction(session -> models.forEach(session::save));
    }

    public <T> void updateModel(T model) {
        voidTransaction(session -> session.update(model));
    }

    public <T> void updateAllModel(List<T> items) {
        voidTransaction(session -> items.forEach(session::merge));
    }

    public <T> void deleteModel(T model) {
        voidTransaction(session -> session.delete(model));
    }

    public <T> void deleteAllModel(List<T> items) {
        voidTransaction(session -> items.forEach(session::delete));
    }

    public <T> T exeQuerySingleRsl(String query) {
        return (T) defaultTransaction(session -> session.createQuery(query).getSingleResult());
    }

    public <T> List<T> exeQueryList(String query) {
        return (List<T>) defaultTransaction(session -> session.createQuery(query).list());
    }

    public void exeQueryVoid(String query) {
        defaultTransaction(session -> session.createQuery(query).executeUpdate());
    }

    /**
     * default transaction, - use for write complex query on HQL.
     *
     * @param action - action with HBM. example: {@code
     *               standardTransactionCore(session -> {
     *               session.save(modelExample);
     *               return modelExample;
     *               });
     *               }
     * @return - return result of your function.
     */
    private Object defaultTransaction(Function<Session, Object> action) {
        Session session = sf.getCurrentSession();
        Object rsl;
        try {
            session.beginTransaction();

            rsl = action.apply(session);

            session.getTransaction().commit();
        } finally {
            session.close();
        }

        return rsl;
    }

    /**
     * The same that defaultTransaction() but without return type.
     *
     * @param action -
     */
    public void voidTransaction(Consumer<Session> action) {
        defaultTransaction(session -> {
            action.accept(session);
            return null;
        });
    }

}