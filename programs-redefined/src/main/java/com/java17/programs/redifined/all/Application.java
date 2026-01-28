package com.java17.programs.redifined.all;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}



}
/**
 * Here's your 🎯 Java equals() and hashCode() Cheat Sheet — super useful for interviews, real-world coding, and avoiding sneaky bugs.
 *
 * ✅ When to Override equals() and hashCode()
 *
 * Use Case	Override Required?	Why?
 * Using as a key in HashMap/HashSet	✅ Yes	Lookup and storage depend on hashCode & equality
 * Comparing domain model objects	✅ Yes (usually)	Need logical comparison, not reference equality
 * Using in TreeMap/TreeSet	❌ No (but need Comparable or Comparator)	Comparison is based on ordering, not equality
 * Only comparing by reference	❌ No	Default equals() and hashCode() (from Object) are enough
 * 🔑 equals() & hashCode() Contract
 * 🔁 Contract Summary
 *
 * If a.equals(b)	Then a.hashCode() == b.hashCode()
 * true	✅ Must be true
 * false	⚠️ hashCode may or may not be equal
 * Violating this contract breaks the behavior of Hash-based collections!
 *
 * 🧪 Example: Correct Implementation
 * java
 * Copy
 * Edit
 * import java.util.Objects;
 *
 * class Person {
 *     int id;
 *     String name;
 *
 *     Person(int id, String name) {
 *         this.id = id;
 *         this.name = name;
 *     }
 *
 *     @Override
 *     public boolean equals(Object o) {
 *         if (this == o) return true;
 *         if (!(o instanceof Person)) return false;
 *         Person p = (Person) o;
 *         return id == p.id && Objects.equals(name, p.name);
 *     }
 *
 *     @Override
 *     public int hashCode() {
 *         return Objects.hash(id, name);
 *     }
 * }
 * 🚨 Common Pitfalls
 *
 * Mistake	Effect
 * Overriding equals() but not hashCode()	Breaks HashSet / HashMap lookup
 * Using mutable fields in equals()/hashCode()	Leads to inconsistent behavior (e.g., change key after insertion)
 * Using == for object field comparison	Compares references, not values
 * Using non-final classes in key objects	If fields can change, it breaks the map/set
 * 💬 Interview Tips
 * Be ready to explain the contract of equals()/hashCode() — interviewers love this!
 *
 * Use Objects.equals() and Objects.hash() to simplify and null-proof your code.
 *
 * Talk about immutability when discussing hash keys — great bonus point.
 *
 * 💡 Quick Template (Copy-Paste Ready)
 * java
 * Copy
 * Edit
 * @Override
 * public boolean equals(Object o) {
 *     if (this == o) return true;
 *     if (!(o instanceof YourClass)) return false;
 *     YourClass that = (YourClass) o;
 *     return field1 == that.field1 && Objects.equals(field2, that.field2);
 * }
 *
 * @Override
 * public int hashCode() {
 *     return Objects.hash(field1, field2);
 * }
 * Would you like to see a real-world use case where overriding equals/hashCode saved from a critical bug (like duplicate removal, caching, etc.)?
 *
 *
 *
 *
 *
 *
 *
 * You said:
 * yes
 *
 *
 * ChatGPT said:
 * Awesome — let’s walk through a real-world bug that often bites developers who don’t override equals() and hashCode() — and how fixing that saved the day.
 *
 * 🔥 Scenario: Caching System for Products (with HashMap)
 * 👇 Problem Code (Buggy Behavior Without Overriding)
 * java
 * Copy
 * Edit
 * class Product {
 *     private int id;
 *     private String name;
 *
 *     Product(int id, String name) {
 *         this.id = id;
 *         this.name = name;
 *     }
 *
 *     // NO equals() and hashCode() override!
 * }
 *
 * public class CacheExample {
 *     public static void main(String[] args) {
 *         Map<Product, String> productCache = new HashMap<>();
 *
 *         Product p1 = new Product(1, "Laptop");
 *         productCache.put(p1, "Cached");
 *
 *         Product p2 = new Product(1, "Laptop");
 *
 *         System.out.println(productCache.get(p2));  // ❌ Returns null
 *     }
 * }
 * 😱 What Happened?
 * Even though p1 and p2 look logically equal, they are different objects in memory, so:
 *
 * p1.equals(p2) → false
 *
 * p1.hashCode() != p2.hashCode()
 *
 * → HashMap can’t find the entry by key. The cache fails.
 *
 * ✅ Solution: Override equals() and hashCode()
 * java
 * Copy
 * Edit
 * @Override
 * public boolean equals(Object o) {
 *     if (this == o) return true;
 *     if (!(o instanceof Product)) return false;
 *     Product that = (Product) o;
 *     return id == that.id && Objects.equals(name, that.name);
 * }
 *
 * @Override
 * public int hashCode() {
 *     return Objects.hash(id, name);
 * }
 * Now:
 *
 * java
 * Copy
 * Edit
 * System.out.println(productCache.get(p2));  // ✅ Returns "Cached"
 * ✅ The cache works — success!
 *
 * ⚠️ Bonus Pitfall: Mutable Key Fields
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
 * 💼 Real-World Use Cases Where This Matters
 *
 * System	Description
 * ✅ Caching (e.g. Guava Cache, Redis keys)	Fails if hashCode breaks
 * ✅ Deduplication with Set	Duplicate records sneak in
 * ✅ ORM like Hibernate	Fails to track entity identity
 * ✅ Grouping in Streams (Collectors.groupingBy())	Incorrect buckets
 * ✅ contains() in collections	Returns false for logical match
 *
 */