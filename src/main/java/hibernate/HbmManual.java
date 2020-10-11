package hibernate;

import models.extra.manyToMany.Author;
import models.extra.manyToMany.Book;

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




        Author anatolij = Author.of("Anatolij");
        Author vera = Author.of("Vera");

        Book bookOne = Book.of("Music Magic");
        Book bookTwo = Book.of("The Witcher 3");

//        HbmProvider.instOf().standardTransactionCore(session -> session.save(anatolij));
//        HbmProvider.instOf().standardTransactionCore(session -> session.save(bookOne));


        List.of(bookOne, bookTwo).forEach(anatolij::addBook);
        List.of(bookOne).forEach(vera::addBook);

        HbmProvider.instOf().standardTransactionCore(session -> {
            session.persist(anatolij);
            session.persist(vera);
            return null;
        });


        System.out.println("FINISH MANUAL RUN");
    }
}
