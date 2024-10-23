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
    public static void main(String[] args)  {
        BufferedReader buf;
        try {
            buf = new BufferedReader(new FileReader("books.json"));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        Gson gson = new Gson();
        Person[] visitors = gson.fromJson(buf, Person[].class);
        // Задание 1. Все посетители
        System.out.println("------Task 1-------");
        Arrays.stream(visitors).forEach(System.out::println);
        System.out.println("Visitors number:" + visitors.length);
        // Задание 2. Вывести список и количество книг, добавленных посетителями в
        // избранное, без повторений.
        System.out.println("------Task 2-------");
        List<Book> books = Arrays.stream(visitors).flatMap(visitor -> visitor.
                getFavoriteBooks().stream().distinct()).toList();
        books.forEach(System.out::println);
        // Задание 3. Отсортировать по году издания и вывести список книг.
        System.out.println("------Task 3-------");
        books = books.stream().sorted(Comparator.comparingInt(Book::getPublishingYear)).distinct().toList();
        books.forEach(System.out::println);
        // Задание 4. Проверить, есть ли у кого-то в избранном книга автора “Jane Austen”.
        System.out.println("------Task 4-------");
        Optional<Book> janeAustenBook = books.stream().
                filter(book -> "Jane Austen".equals(book.getAuthor())).findFirst();
        janeAustenBook.ifPresent(book -> System.out.println(book.getName() + " by " + book.getAuthor()));
        // Задание 5. Вывести максимальное число добавленных в избранное книг.
        System.out.println("------Task 5-------");
        Optional<Integer> maxFavoriteBooks = Arrays.stream(visitors).
                map(visitor -> visitor.getFavoriteBooks().size()).max(Integer::compareTo);
        maxFavoriteBooks.ifPresent(max -> System.out.println("Max books: " + max));
        // Задание 6. SMS
        System.out.println("------Task 6-------");
        double averageFavorites = Arrays.stream(visitors)
                .filter(Person::getSubscribed)
                .mapToInt(visitor -> visitor.getFavoriteBooks().size())
                .average().orElse(0);
        List<SMS> smsList = Arrays.stream(visitors)
                .filter(Person::getSubscribed)
                .map(visitor -> {
                    int favoriteCount = visitor.getFavoriteBooks().size();
                    String message;

                    if (favoriteCount > averageFavorites) {
                        message = "You are a bookworm";
                    } else if (favoriteCount < averageFavorites)
                    {
                        message = "Read more";
                    } else
                    {
                        message = "Fine";
                    }
                    return new SMS(visitor.getPhone(), message);
                }).toList();
        smsList.forEach(System.out::println);
    }
}