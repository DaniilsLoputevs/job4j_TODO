package models.other.manyToMany;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "ManyToMany_book")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;

    public Book() {
    }

    public Book(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public static Book of(String name) {
        Book book = new Book();
        book.name = name;
        return book;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book car = (Book) o;
        return Objects.equals(id, car.id) &&
                Objects.equals(name, car.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public String toString() {
        return "Book{"
                + "id='" + id + '\''
                + ", name='" + name + '\''
                + '}';
    }
}
