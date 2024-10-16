package org.example;
import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        Person[] visitors = parsing("books.json");
        // Задание 1. Все посетители
        System.out.println("------Task 1-------");
        Arrays.stream(visitors).forEach(visitor -> {
            System.out.println("-------------");
            System.out.println("Name:" + visitor.getName() + " " + visitor.getSurname());
            System.out.println("Phone:" + visitor.getPhone());
            System.out.println("Subscribed:" + visitor.getSubscribed());
        });
        System.out.println("Visitors number:" + visitors.length);
        // Задание 2. Вывести список и количество книг, добавленных посетителями в
        // избранное, без повторений.
        System.out.println("------Task 2-------");
        List<Book> books = favoriteBooks(visitors);
        books.forEach(book -> {
            System.out.println("-------------");
            System.out.println("Name:" + book.getName());
            System.out.println("Author:" + book.getAuthor());
            System.out.println("Publishing year:" + book.getPublishingYear());
            System.out.println("ISBN:" + book.getIsbn());
            System.out.println("Publisher:" + book.getPublisher());
        });
        // Задание 3. Отсортировать по году издания и вывести список книг.
        System.out.println("------Task 3-------");
        books = sortedBooks(books);
        books.forEach(book -> {
            System.out.println("-------------");
            System.out.println("Name:" + book.getName());
            System.out.println("Author:" + book.getAuthor());
            System.out.println("Publishing year:" + book.getPublishingYear());
            System.out.println("ISBN:" + book.getIsbn());
            System.out.println("Publisher:" + book.getPublisher());
        });
        // Задание 4. Проверить, есть ли у кого-то в избранном книга автора “Jane Austen”.
        System.out.println("------Task 4-------");
        Optional<Book> janeAustenBook = janeAustenBookCheck(books);
        janeAustenBook.ifPresent(book -> System.out.println(book.getName() + " by " + book.getAuthor()));
        // Задание 5. Вывести максимальное число добавленных в избранное книг.
        System.out.println("------Task 5-------");
        Optional<Integer> maxFavoriteBooks = maxFavBooks(visitors);
        maxFavoriteBooks.ifPresent(max -> System.out.println("Max books: " + max));
        // Задание 6. SMS
        System.out.println("------Task 6-------");
        List<SMS> smsList = Messaging(visitors);
        smsList.forEach(sms -> {
            System.out.println("Phone:" + sms.getPhoneNumber());
            System.out.println("Message:" + sms.getMessage());
        });
    }
    public static Person[] parsing(String path) throws FileNotFoundException {
        BufferedReader buf = new BufferedReader(new FileReader(path));
        Gson gson = new Gson();
        return gson.fromJson(buf, Person[].class);
    }
    public static List<Book> favoriteBooks(Person[] visitors) {
        return Arrays.stream(visitors).flatMap(visitor -> visitor.
                getFavoriteBooks().stream().distinct()).toList();
    }
    public static List<Book> sortedBooks(List<Book> books) {
        return books.stream().sorted(Comparator.comparingInt(Book::getPublishingYear)).toList();
    }
    public static Optional<Book> janeAustenBookCheck(List<Book> books) {
        return books.stream().filter(book -> "Jane Austen".equals(book.getAuthor())).findFirst();
    }
    public static Optional<Integer> maxFavBooks(Person[] visitors) {
        return Arrays.stream(visitors)
                .map(visitor -> visitor.getFavoriteBooks().size())
                .max(Integer::compareTo);
    }
    public static List<SMS> Messaging(Person[] visitors) {
        double averageFavorites = Arrays.stream(visitors)
                .filter(Person::getSubscribed)
                .mapToInt(visitor -> visitor.getFavoriteBooks().size())
                .average()
                .orElse(0);

        return Arrays.stream(visitors)
                .filter(Person::getSubscribed)
                .map(visitor -> {
                    int favoriteCount = visitor.getFavoriteBooks().size();
                    String message;

                    if (favoriteCount > averageFavorites) {
                        message = "you are a bookworm";
                    } else if (favoriteCount < averageFavorites) {
                        message = "read more";
                    } else {
                        message = "fine";
                    }

                    return new SMS(visitor.getPhone(), message);
                })
                .toList();
    }
}