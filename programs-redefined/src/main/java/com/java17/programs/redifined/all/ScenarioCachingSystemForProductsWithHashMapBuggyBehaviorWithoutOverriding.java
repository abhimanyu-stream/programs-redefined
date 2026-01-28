package com.java17.programs.redifined.all;

import java.util.HashMap;
import java.util.Map;

public class ScenarioCachingSystemForProductsWithHashMapBuggyBehaviorWithoutOverriding {

    public static void main(String[] args) {
        Map<Product1, String> productCache = new HashMap<>();

        Product1 p1 = new Product1(1, "Laptop");
        productCache.put(p1, "Cached");

        Product1 p2 = new Product1(1, "Laptop");

        System.out.println(productCache.get(p2));  // ❌ Returns null
        /**
         * What Happened?
         * Even though p1 and p2 look logically equal, they are different objects in memory, so:
         *
         * p1.equals(p2) → false
         *
         * p1.hashCode() != p2.hashCode()
         *
         * → HashMap can’t find the entry by key. The cache fails.
         *
         */
    }
}
class Product1 {
    private int id;
    private String name;

    Product1(int id, String name) {
        this.id = id;
        this.name = name;
    }

    // NO equals() and hashCode() override!
}