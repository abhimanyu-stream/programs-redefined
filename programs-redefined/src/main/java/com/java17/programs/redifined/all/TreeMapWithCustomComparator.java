package com.java17.programs.redifined.all;

import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

public class TreeMapWithCustomComparator {
    public static void main(String[] args) {
        Comparator<Employee6> byName = Comparator.comparing(emp -> emp.name);

        TreeMap<Employee6, String> employeeMap = new TreeMap<>(byName);

        employeeMap.put(new Employee6(1, "Charlie"), "Finance");
        employeeMap.put(new Employee6(2, "Alice"), "HR");
        employeeMap.put(new Employee6(3, "Bob"), "IT");

        for (Map.Entry<Employee6, String> entry : employeeMap.entrySet()) {
            System.out.println(entry.getKey() + " => " + entry.getValue());
        }
    }

}
class Employee6 {
    int id;
    String name;

    public Employee6(int id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public String toString() {
        return "Employee6{id=" + id + ", name='" + name + "'}";
    }
}