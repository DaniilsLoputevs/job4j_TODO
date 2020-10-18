package store;

import hibernate.HbmProvider;
import models.Category;

import java.util.List;

public class CategoryStore {
    /* Static description */
    private static final class Lazy {
        private static final CategoryStore INST = new CategoryStore();
    }

    public static CategoryStore instOf() {
        return CategoryStore.Lazy.INST;
    }


    /* Class description */
    private final BasicHbmStore<Category> core = new BasicHbmStore<>();


    public void add(Category category) {
        core.add(category);
    }

    public void addAll(List<Category> items) {
        core.addAll(items);
    }

    public Category getById(int id) {
        return core.getById(id, "Category");
    }

    public Category getByName(String name) {
        var temp = (List<Category>) HbmProvider.instOf().standardTransactionCore(session ->
                session.createQuery("from Category where name="
                        + "\'" + name + "\'")
                        .list()
        );
        return (temp.isEmpty()) ? new Category() : temp.get(0);
    }

    public List<Category> getAll() {
        return core.getAll("Category");
    }

    public boolean delete(int id) {
        var temp = new Category();
        temp.setId(id);
        return core.delete(temp);
    }
}