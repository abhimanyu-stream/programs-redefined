package com.java17.programs.redifined.all;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class FixedVersionOverridingEqualsAndHashCode {
    public static void main(String[] args) {

        Set<Employee1> set = new HashSet<>();
        Employee1 e1 = new Employee1(1, "Alice");
        Employee1 e2 = new Employee1(1, "Alice");

        set.add(e1);
        set.add(e2);

        System.out.println(set.size()); // ✅ Output: 1, duplicate avoided


    }
}
class Employee1 {
    int id;
    String name;

    public Employee1(int id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Employee1)) return false;
        Employee1 other = (Employee1) obj;
        return this.id == other.id && Objects.equals(this.name, other.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
