package store;

import hibernate.HbmProvider;

import java.util.List;

/**
 * Just shared code in one place.
 */
public class BasicHbmStore<T> {

    /**
     * Automatically set id for @param item.
     *
     * @param item -
     */
    public void add(T item) {
        HbmProvider.instOf().standardTransactionCore(session -> {
            session.save(item);
            return item;
        });
    }
    public void addAll(List<T> items) {
        HbmProvider.instOf().standardTransactionCore(session -> {
            for (T item : items) {
                session.save(item);
            }
            return HbmProvider.RETURN_VOID;
        });
    }
    public T getById(int id, String modelClassName) {
        return getById(id, modelClassName, "");
    }

    /**
     * final hql = "
     * from modelClassName as mainTable
     * join fetch mainTable.useJoinFetchFieldName
     * where mainTable.id=id
     * ";
     *
     * @param id - id in DataBase.
     * @param modelClassName - model class name. Example: User, Item.
     * @param useJoinFetchFieldName - name of private field in model that you want to fetch.
     *                              Example: private List<User> users;
     *                              useJoinFetchFieldName = "users";
     * @return -
     */
    public T getById(int id, String modelClassName, String useJoinFetchFieldName) {
        StringBuilder builder = new StringBuilder("from ");
        builder.append(modelClassName);
        builder.append(" as mainTable ");
        if (!useJoinFetchFieldName.isBlank()) {
            builder.append("join fetch mainTable.");
            builder.append(useJoinFetchFieldName);
        }
        builder.append(" where mainTable.id=");
        builder.append(id);
        String hql = builder.toString();
//        CustomLog.log("hql", hql);
        return (T) HbmProvider.instOf().standardTransactionCore(session ->
                session.createQuery(hql)
                        .getSingleResult()
        );
    }

    public List<T> getAll(String tableName) {
        return getAll(tableName, "");
    }
    public List<T> getAll(String tableName, String useJoinFetchFieldName) {
        String hql = "from " + tableName + " as mainTable";
        if (!"".equalsIgnoreCase(useJoinFetchFieldName)) {
            hql += " join fetch mainTable." + useJoinFetchFieldName;
        }
        String effectivelyFinalHql = hql;
//        CustomLog.log("GetAll() - final hql", effectivelyFinalHql);

        return (List<T>) HbmProvider.instOf().standardTransactionCore(session ->
                session.createQuery(effectivelyFinalHql)
                        .list()
        );
    }

    public boolean delete(T item) {
        HbmProvider.instOf().standardTransactionCore(session -> {
            session.delete(item);
            return HbmProvider.RETURN_VOID;
        });
        return true;
    }
}
