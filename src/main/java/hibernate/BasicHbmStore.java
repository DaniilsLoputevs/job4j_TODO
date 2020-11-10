package hibernate;

import java.util.List;

/**
 * Shared code in one place. + Base for create any ModelStore.
 *
 * @author Daniils Loputevs(laiwiense@gmail.com)
 * @version 1.3.
 * @since 10.11.2020.
 */
public class BasicHbmStore<T> {
    private final String modelClassName;
    private final HbmProvider provider = HbmProvider.instOf();

    public BasicHbmStore(String modelClassName) {
        this.modelClassName = modelClassName;
    }


    public void add(T item) {
        provider.saveModel(item);
    }

    public void addAll(List<T> items) {
        provider.saveAllModel(items);
    }

    public <V> List<T> getBy(String fieldName, V value) {
        var hql = "from " + modelClassName + " as mt where mt." + fieldName + "="
                + '\'' + value + '\'';
//        CustomLog.log("hql", hql);
        return provider.exeQueryList(hql);
    }

    public List<T> getAll() {
        String hql = "from " + modelClassName;
        return provider.exeQueryList(hql);
    }

    public void update(T item) {
        provider.updateModel(item);
    }

    public void updateAll(List<T> items) {
        provider.updateAllModel(items);
    }

    public void delete(T item) {
        provider.deleteModel(item);
    }

    public void delete(int id) {
        String hql = "delete from " + modelClassName + "where id=" + id;
        HbmProvider.instOf().exeQueryVoid(hql);
    }

    public void deleteAll(List<T> items) {
        provider.deleteAllModel(items);
    }

    /**
     * When you use query with return List<T> but expect || want || need only one result.
     *
     * @param list  -
     * @param empty -
     * @return -
     */
    public T getFirstOrEmpty(List<T> list, T empty) {
        return (list.isEmpty()) ? empty : list.get(0);
    }

    /**
     * Use it, if you need exe query.
     *
     * @return -
     */
    public HbmProvider getProvider() {
        return this.provider;
    }
}