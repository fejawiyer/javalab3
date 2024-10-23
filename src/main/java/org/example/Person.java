package org.example;

import lombok.Data;

import java.util.ArrayList;

@Data
public class Person {
    private String name;
    private String surname;
    private String phone;
    private Boolean subscribed;
    private ArrayList<Book> favoriteBooks;
}