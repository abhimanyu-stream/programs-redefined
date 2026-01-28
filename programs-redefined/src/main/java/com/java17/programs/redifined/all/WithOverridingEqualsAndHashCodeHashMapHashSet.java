package com.java17.programs.redifined.all;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class WithOverridingEqualsAndHashCodeHashMapHashSet {
    public static void main(String[] args) {
        Employee3 emp1 = new Employee3(1, "Alice");
        Employee3 emp2 = new Employee3(1, "Alice");
        Employee3 emp3 = new Employee3(2, "Alice");

        Map<Employee3, String> map = new HashMap<>();
        map.put(emp1, "HR");

        System.out.println("Contains emp2? " + map.containsKey(emp2));  // ✅ true
        System.out.println("Contains emp3? " + map.containsKey(emp3));  // false
    }
}
class Employee3 {
    int id;
    String name;

    public Employee3(int id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Employee3)) return false;
        Employee3 e = (Employee3) o;
        return id == e.id && Objects.equals(name, e.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}