package models.othertasks.manytomany;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "ManyToMany_author")
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    @ManyToMany(cascade =  {CascadeType.PERSIST, CascadeType.MERGE})
//    @ManyToMany(cascade =  CascadeType.ALL)
    private List<Book> books = new ArrayList<>();

    public Author() {
    }

    public Author(int id, String name, List<Book> cars) {
        this.id = id;
        this.name = name;
        this.books = cars;
    }

    public static Author of(String name) {
        Author author = new Author();
        author.name = name;
        return author;
    }

    public void addBook(Book book) {
        this.books.add(book);
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

    public List<Book> getCars() {
        return books;
    }

    public void setCars(List<Book> cars) {
        this.books = cars;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Author brand = (Author) o;
        return Objects.equals(id, brand.id)
                && Objects.equals(name, brand.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public String toString() {
        return "Author{"
                + "id='" + id + '\''
                + ", name='" + name + '\''
                + ", cars=" + books
                + '}';
    }
}
