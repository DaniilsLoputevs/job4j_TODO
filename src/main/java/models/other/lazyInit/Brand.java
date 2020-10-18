package models.other.lazyInit;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * LazyInitializationException - 2 ways how to fix this problem.
 * Reason: get access to inner object out of linked session with this inner object.
 * Way 1: use(get access) inner object before you close the session.
 * Way 2: use "join fetch" in HQL query. Example:
 * HQL = "select distinct b from Brand b join fetch b.cars"
 */
@Entity
@Table(name = "LIE_brands")
public class Brand {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    @OneToMany(mappedBy = "brand"
//            , cascade = CascadeType.ALL, orphanRemoval = true
    )
    private List<Car> cars = new ArrayList<>();

    public Brand() {
    }

    public Brand(int id, String name, List<Car> cars) {
        this.id = id;
        this.name = name;
        this.cars = cars;
    }

    public static Brand of(String name) {
        Brand brand = new Brand();
        brand.name = name;
        return brand;
    }

    public void addCar(Car car) {
        this.cars.add(car);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Brand brand = (Brand) o;
        return Objects.equals(id, brand.id) &&
                Objects.equals(name, brand.name);
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

    public List<Car> getCars() {
        return cars;
    }

    public void setCars(List<Car> cars) {
        this.cars = cars;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public String toString() {
        return "Brand{"
                + "id='" + id + '\''
                + ", name='" + name + '\''
                + ", cars=" + cars
                + '}';
    }
}