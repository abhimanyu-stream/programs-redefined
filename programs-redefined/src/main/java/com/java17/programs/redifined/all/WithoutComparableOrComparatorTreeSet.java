package com.java17.programs.redifined.all;

import java.util.Set;
import java.util.TreeSet;

public class WithoutComparableOrComparatorTreeSet {
    public static void main(String[] args) {
        Set<Employee4> set = new TreeSet<>();
        set.add(new Employee4(1));  // ❌ Throws ClassCastException
    }
}
class Employee4 {
    int id;

    public Employee4(int id) {
        this.id = id;
    }
}