package com.java17.programs.redifined.all;

import java.util.HashMap;
import java.util.Map;

public class WithoutOverridingEqualsAndHashCodeHashMapHashSet {
    public static void main(String[] args) {
        Employee2 emp1 = new Employee2(1, "Alice");
        Employee2 emp2 = new Employee2(1, "Alice");

        Map<Employee2, String> map = new HashMap<>();
        map.put(emp1, "HR");

        System.out.println("Contains emp2? " + map.containsKey(emp2));  // ❌ false (different hashCode)
    }

}
class Employee2 {
    int id;
    String name;

    public Employee2(int id, String name) {
        this.id = id;
        this.name = name;
    }
}