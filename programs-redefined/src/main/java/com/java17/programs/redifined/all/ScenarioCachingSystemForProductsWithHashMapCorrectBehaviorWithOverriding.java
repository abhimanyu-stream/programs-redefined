package com.java17.programs.redifined.all;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ScenarioCachingSystemForProductsWithHashMapCorrectBehaviorWithOverriding {

    public static void main(String[] args) {
        Map<Product2, String> productCache = new HashMap<>();

        Product2 p1 = new Product2(1, "Laptop");
        productCache.put(p1, "Cached");

        Product2 p2 = new Product2(1, "Laptop");

        System.out.println(productCache.get(p2));  //
        /**
         * Solution: Override equals() and hashCode()
         *
         */

        /**
         *  Bonus Pitfall: Mutable Key Fields
         * java
         * Copy
         * Edit
         * Product p = new Product(1, "Laptop");
         * productCache.put(p, "Saved");
         *
         * p.setName("Phone");  // ❗ Modifies the key after insertion
         *
         * System.out.println(productCache.get(p));  // ❌ Returns null (HashMap broken)
         * 🔐 Rule: Never use mutable fields in equals()/hashCode() — or make your key objects immutable!
         *
         * Real-World Use Cases Where This Matters
         *
         * System	Description
         * ✅ Caching (e.g. Guava Cache, Redis keys)	Fails if hashCode breaks
         * ✅ Deduplication with Set	Duplicate records sneak in
         * ✅ ORM like Hibernate	Fails to track entity identity
         * ✅ Grouping in Streams (Collectors.groupingBy())	Incorrect buckets
         * ✅ contains() in collections	Returns false for logical match
         *
         */
    }
}
class Product2 {
    private int id;
    private String name;

    Product2(int id, String name) {
        this.id = id;
        this.name = name;
    }

    //  equals() and hashCode() override!


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product2 product2 = (Product2) o;
        return id == product2.id && Objects.equals(name, product2.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}