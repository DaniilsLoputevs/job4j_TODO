package hibernate;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.util.function.Function;

/**
 * This class describe technical Hibernate part.
 * User public API for use HBM function, this all that you need to know about it.
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
     * just for clear understanding that this transaction haven't return value.
     */
    public static final int RETURN_VOID = -1;

    /**
     * Public API for shortcut in ModelStore classes.
     * Example: {@code
     * public class UserStore {
     * private final HbmProvider hbmProvider = HbmProvider.instOf();
     * ...
     * }
     * }
     *
     * @return inst of HbmProvider.
     */
    public static HbmProvider instOf() {
        return HbmProvider.Lazy.INST;
    }

    /**
     * @param action - action with HBM. example: {@code
     *               standardTransactionCore(session -> {
     *               session.save(modelExample);
     *               return modelExample;
     *               });
     *               }
     * @return - return of your function.
     * #see const RETURN_VOID
     */
    public Object standardTransactionCore(Function<Session, Object> action) {
        Session session = sf.getCurrentSession();
        session.beginTransaction();

        Object rsl = action.apply(session);

        session.getTransaction().commit();
        session.close();

        return rsl;
    }
}
