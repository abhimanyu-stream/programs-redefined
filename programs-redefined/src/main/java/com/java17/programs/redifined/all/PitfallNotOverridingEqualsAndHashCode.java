package com.java17.programs.redifined.all;

import java.util.HashSet;
import java.util.Set;

public class PitfallNotOverridingEqualsAndHashCode {
    public static void main(String[] args) {
        Set<Employee> set = new HashSet<>();
        Employee e1 = new Employee(1, "Alice");
        Employee e2 = new Employee(1, "Alice");

        set.add(e1);
        set.add(e2);

        System.out.println(set.size()); // ❌ Output: 2, even though logically same

    }
}
class Employee {
    int id;
    String name;

    public Employee(int id, String name) {
        this.id = id;
        this.name = name;
    }
}