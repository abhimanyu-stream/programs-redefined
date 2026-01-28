package com.java17.programs.redifined.all;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Common Rule of Thumb
 * If you override equals(), always override hashCode().
 *
 * Failing to do so breaks contracts for HashSet, HashMap, etc.
 *
 * 🧠 Bonus: equals() and hashCode() Contract
 * If a.equals(b) is true, then a.hashCode() == b.hashCode() must also be true.
 *
 * But the reverse is not required — same hashCode doesn't mean objects are equal.
 */
public class NoDuplicateObjectsTreatedAsDifferentInHashSetHashMap {
    public static void main(String[] args) {
        Set<Books> books = new HashSet<>();

        books.add(new Books(1, "Java"));
        books.add(new Books(1, "Java"));

        System.out.println("Books size: " + books.size()); // 1
    }
}
class Books {
    int id;
    String title;

    public Books(int id, String title) {
        this.id = id;
        this.title = title;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Books)) return false;
        Books books = (Books) o;
        return id == books.id && Objects.equals(title, books.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title);
    }
}