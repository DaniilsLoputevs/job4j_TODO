package store;

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
    private final BasicHbmStore<Category> core = new BasicHbmStore<>("Category");


    public void add(Category category) {
        core.add(category);
    }

    public void addAll(List<Category> items) {
        core.addAll(items);
    }

    public Category getById(int id) {
        List<Category> temp = core.getBy("id", id);
        return getCategoryOrEmptyUser(temp);
    }

    public Category getByName(String name) {
        List<Category> temp = core.getBy("name", name);
        return getCategoryOrEmptyUser(temp);
    }

    public List<Category> getAll() {
        return core.getAll();
    }

    public void delete(int id) {
        var temp = new Category();
        temp.setId(id);
        core.delete(temp);
    }

    private Category getCategoryOrEmptyUser(List<Category> list) {
        return (list.isEmpty()) ? new Category() : list.get(0);
    }

}