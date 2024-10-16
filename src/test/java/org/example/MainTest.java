package org.example;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Optional;

import static org.example.Main.parsing;
import static org.junit.jupiter.api.Assertions.assertEquals;

class MainTest {
    private List<Book> books;

    @BeforeEach
    void setUp() throws FileNotFoundException {
        Person[] visitors = parsing("books.json");
        books = Main.favoriteBooks(visitors);
    }

    @Test
    void janeAustenBookCheck() {
        Optional<Book> janeAustenBook = Main.janeAustenBookCheck(books);
        janeAustenBook.ifPresent(book -> assertEquals("Pride and Prejudice", book.getName()));
    }

    @Test
    void favoriteBooks() {
        Assertions.assertFalse(books.isEmpty());
        int dsCount = (int) books.stream().distinct().count();
        assertEquals(dsCount, books.size());
    }
}