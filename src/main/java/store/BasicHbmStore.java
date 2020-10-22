package store;

import hibernate.HbmProvider;

import java.util.List;

/**
 * Shared code in one place. + Base for create any ModelStore.
 */
public class BasicHbmStore<T> {
    private final String modelClassName;

    public BasicHbmStore(String modelClassName) {
        this.modelClassName = modelClassName;
    }


    public void add(T item) {
        HbmProvider.instOf().saveModel(item);
    }

    public void addAll(List<T> items) {
        HbmProvider.instOf().voidTransaction(session -> items.forEach(session::save));
    }

    public <V> List<T> getBy(String fieldName, V value) {
        var hql = "from " + modelClassName + " as mt where mt." + fieldName + "="
                + '\'' + value + '\'';
//        CustomLog.log("hql", hql);
        return HbmProvider.instOf().exeQueryList(hql);
    }

    public List<T> getAll() {
        String hql = "from " + modelClassName;
        return HbmProvider.instOf().exeQueryList(hql);
    }

    public void delete(T item) {
        HbmProvider.instOf().deleteModel(item);
    }

    public void update(T model) {
        HbmProvider.instOf().updateModel(model);
    }
}