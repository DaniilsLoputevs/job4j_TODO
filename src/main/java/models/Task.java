package models;

import ajax.webhelp.DateTransform;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;

import static ajax.webhelp.JC.*;

@Entity
@Table(name = "tasks")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String description;

    @ManyToMany(cascade = CascadeType.ALL)
//    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
//    @ManyToMany(cascade = {PERSIST, MERGE, REMOVE, REFRESH, DETACH})
    @OnDelete(action = OnDeleteAction.NO_ACTION)
    @JoinColumn(name = "category_id")
    private List<Category> categories;

    //    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @ManyToOne(cascade = CascadeType.ALL)
    @OnDelete(action = OnDeleteAction.NO_ACTION)
    @JoinColumn(name = "user_id")
    private User creator;
    private Timestamp created;
    private boolean done;


    public Task() {
    }

    public Task(String description, List<Category> categories, User creator) {
//        Task temp = new Task(-1, desc, categories,
//                new Timestamp(System.currentTimeMillis()), false, user);
        this.id = -1;
        this.description = description;
        this.categories = categories;
        this.created = new Timestamp(System.currentTimeMillis());
        this.done = false;
        this.creator = creator;
    }

    public Task(int id, String description, List<Category> categories,
                Timestamp created, boolean done, User creator) {
        this.id = id;
        this.description = description;
        this.categories = categories;
        this.created = created;
        this.done = done;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Category> getCategory() {
        return categories;
    }

    public void setCategory(List<Category> categories) {
        this.categories = categories;
    }

    public User getCreator() {
        return creator;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }

    public Timestamp getCreated() {
        return created;
    }

    public void setCreated(Timestamp created) {
        this.created = created;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Task task = (Task) o;
        return id == task.id
                && done == task.done
                && Objects.equals(description, task.description)
                && Objects.equals(categories, task.categories)
                && Objects.equals(creator, task.creator)
                && Objects.equals(created, task.created);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, description, created, done);
    }

    @Override
    public String toString() {
        return "Task{"
                + "id=" + id
                + ", description='" + description + '\''
                + ", categories='" + categories + '\''
                + ", creator=" + creator
                + ", created=" + created
                + ", done=" + done
                + '}';
    }

    public String toJson() {
        String[] tempCategoryJsonArr = new String[this.categories.size()];
        for (int i = 0; i < this.categories.size(); i++) {
            var tempCategory = this.categories.get(i);
            tempCategoryJsonArr[i] = wrapObject(
                    collect("id", tempCategory.getId()),
                    collect("name", tempCategory.getName())
            );
        }
        String categories = wrapList(tempCategoryJsonArr);
        return wrapObject(
                collect("id", this.id),
                collect("description", this.description),
                collect("creator", this.creator.getName()),
                collect("created", DateTransform.toFront(this.created)),
                collect("done", this.getDescription()),
                collect("category", categories)
        );
    }
}
