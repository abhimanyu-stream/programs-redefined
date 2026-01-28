package com.java17.programs.redifined.all;

import java.util.HashSet;
import java.util.Set;

public class PitfallDuplicateObjectsTreatedAsDifferentInHashSetHashMap {
    public static void main(String[] args) {
        Set<Book> books = new HashSet<>();

        books.add(new Book(1, "Java"));
        books.add(new Book(1, "Java"));

        System.out.println("Books size: " + books.size()); // ❌ 2 instead of 1
    }
}
class Book {
    int id;
    String title;

    public Book(int id, String title) {
        this.id = id;
        this.title = title;
    }
}