package org.example;
import lombok.Getter;

import java.util.ArrayList;

@Getter
public class Person {
    private String name;
    private String surname;
    private String phone;
    private Boolean subscribed;
    private ArrayList<Book> favoriteBooks;
}