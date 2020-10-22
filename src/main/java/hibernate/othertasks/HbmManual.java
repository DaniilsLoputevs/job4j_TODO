package hibernate.othertasks;

import models.other.lazyinit.Brand;
import models.other.lazyinit.Car;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import utiltools.CustomLog;

import java.util.ArrayList;
import java.util.List;

public class HbmManual {
    public static void main(String[] args) {
//        var first = new Task(1, "first", null, false);
//        var second = new Task(2, "second", null, false);
//        var third = new Task(3, "third", null, false);
//        new TaskStore().add(first);
//        new TaskStore().add(second);
//        new TaskStore().add(third);

//            var user = new User(0, "testName", "testEmail", "testPassword");
//            UserStore.instOf().add(user);

//            var temp = UserStore.instOf().getByEmail("testEmail");
//            System.out.println(temp);

//            var temp = new Task();
//            TaskStore.instOf().add(temp);


//        Author anatolij = Author.of("Anatolij");
//        Author vera = Author.of("Vera");

//        Book bookOne = Book.of("Music Magic");
//        Book bookTwo = Book.of("The Witcher 3");


//        List.of(bookOne, bookTwo).forEach(anatolij::addBook);
//        List.of(bookOne).forEach(vera::addBook);

//        HbmProvider.instOf().standardTransactionCore(session -> {
//            session.persist(anatolij);
//            session.persist(vera);
//            return null;
//        });


//        var brand = Brand.of("Mark");
//        var one = Car.of("one");
//        var two = Car.of("two");
//        var three = Car.of("three");
//
//        brand.setCars(List.of(one, two, three));
//        one.setBrand(brand);
//        two.setBrand(brand);
//        three.setBrand(brand);
//
//        HbmProvider.instOf().standardTransactionCore(session -> {
//            session.save(brand);
//            session.save(one);
//            session.save(two);
//            session.save(three);
//            return true;
//        });


        List<Brand> list = new ArrayList<>();
        List<Car> cars = new ArrayList<>();
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure().build();
        try {
            SessionFactory sf = new MetadataSources(registry)
                    .buildMetadata().buildSessionFactory();
            Session session = sf.openSession();
            session.beginTransaction();

//            list = session.createQuery("from Brand").list();
//            for (Brand tempBrand : list) {
//                for (Car car : tempBrand.getCars()) {
//                    cars.add(car);
//                    System.out.println(car);
//                }
//            }
            list = session.createQuery(
                    "select distinct b from Brand b join fetch b.cars"
            ).list();

            session.getTransaction().commit();
            session.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            StandardServiceRegistryBuilder.destroy(registry);
        }
        for (Brand tempBrand : list) {
            for (Car car : tempBrand.getCars()) {
                System.out.println(car);
            }
        }

        CustomLog.log("list", list);
        CustomLog.log("cars", cars);
//        cars.forEach(System.out::println);


//        List<Category> list = new ArrayList<>();
//        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
//                .configure().build();
//        try {
//            SessionFactory sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();
//            Session session = sf.openSession();
//            session.beginTransaction();
//            list = session.createQuery(
//                    "select distinct c from Category c join fetch c.tasks"
//            ).list();
//            session.getTransaction().commit();
//            session.close();
//        }  catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            StandardServiceRegistryBuilder.destroy(registry);
//        }
//        for (Task task : list.get(0).getTasks()) {
//            System.out.println(task);
//        }


        System.out.println("FINISH MANUAL RUN");
    }
}
