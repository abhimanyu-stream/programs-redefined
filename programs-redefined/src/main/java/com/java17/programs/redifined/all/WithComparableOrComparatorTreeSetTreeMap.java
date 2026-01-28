package com.java17.programs.redifined.all;

import java.util.Set;
import java.util.TreeSet;

/**
 * Final Notes:
 *
 * Collection	Required for Custom Objects
 * HashMap / HashSet	equals() + hashCode()
 * TreeSet / TreeMap	Comparable or Comparator
 */
public class WithComparableOrComparatorTreeSetTreeMap {
    public static void main(String[] args) {
        Set<Employee5> set = new TreeSet<>();
        set.add(new Employee5(2));
        set.add(new Employee5(1));
        set.add(new Employee5(2));  // Duplicate based on compareTo

        System.out.println(set);   // ✅ Sorted set with no duplicates
    }
}
class Employee5 implements java.lang.Comparable<Employee5> {
    int id;

    public Employee5(int id) {
        this.id = id;
    }

    @Override
    public int compareTo(Employee5 other) {
        return Integer.compare(this.id, other.id);
    }

    @Override
    public String toString() {
        return "Employee{id=" + id + "}";
    }
}