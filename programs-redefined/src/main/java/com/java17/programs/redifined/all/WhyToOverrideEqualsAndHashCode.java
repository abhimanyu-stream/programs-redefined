package com.java17.programs.redifined.all;

public class WhyToOverrideEqualsAndHashCode {

    /**
     * Great question! Overriding equals() and hashCode() in Java is essential for ensuring that objects behave correctly in collections like HashMap, HashSet, and Hashtable, and for maintaining logical equality between objects.
     *
     * ✅ Why Override equals() and hashCode()
     * 🔁 equals():
     * Used to compare the logical equality of two objects (not just memory reference).
     *
     * Default behavior (from Object) compares references (i.e., a == b).
     *
     * #️⃣ hashCode():
     * Used in hash-based collections like HashMap, HashSet, Hashtable.
     *
     * If two objects are equal, they must have the same hash code.
     *
     * 🔴 Pitfall: Not Overriding equals() and hashCode()
     * 💡 Use Case Scenario
     * You are storing employee records in a HashSet to ensure uniqueness based on id.
     *
     * ❌ Example (Without overriding):
     * java
     * Copy
     * Edit
     * class Employee {
     *     int id;
     *     String name;
     *
     *     public Employee(int id, String name) {
     *         this.id = id;
     *         this.name = name;
     *     }
     * }
     * java
     * Copy
     * Edit
     * Set<Employee> set = new HashSet<>();
     * Employee e1 = new Employee(1, "Alice");
     * Employee e2 = new Employee(1, "Alice");
     *
     * set.add(e1);
     * set.add(e2);
     *
     * System.out.println(set.size()); // ❌ Output: 2, even though logically same
     * ➡ Why? Because HashSet uses hashCode() + equals() to determine uniqueness. Since you didn't override them, it falls back to reference equality (e1 != e2), and both are added.
     *
     * ✅ Fixed Version (Overriding equals() and hashCode())
     * java
     * Copy
     * Edit
     * class Employee {
     *     int id;
     *     String name;
     *
     *     public Employee(int id, String name) {
     *         this.id = id;
     *         this.name = name;
     *     }
     *
     *     @Override
     *     public boolean equals(Object obj) {
     *         if (this == obj) return true;
     *         if (!(obj instanceof Employee)) return false;
     *         Employee other = (Employee) obj;
     *         return this.id == other.id && Objects.equals(this.name, other.name);
     *     }
     *
     *     @Override
     *     public int hashCode() {
     *         return Objects.hash(id, name);
     *     }
     * }
     * java
     * Copy
     * Edit
     * Set<Employee> set = new HashSet<>();
     * Employee e1 = new Employee(1, "Alice");
     * Employee e2 = new Employee(1, "Alice");
     *
     * set.add(e1);
     * set.add(e2);
     *
     * System.out.println(set.size()); // ✅ Output: 1, duplicate avoided
     *
     * Common Pitfalls if You Don’t Override:
     *
     * Scenario	Result
     * HashSet / HashMap keys misbehave	Duplicates may exist
     * Logical duplicates aren't recognized	Bugs in filtering, validation
     * Performance degradation in HashMap	Uneven hashing, many collisions
     *
     * ✅ Golden Rule:
     * If you override equals(), you must override hashCode() – and vice versa.
     *
     * Otherwise, collections like HashMap and HashSet won’t work correctly.
     *
     *
     * Here’s a comprehensive list of Java data structures and scenarios that require overriding equals() and hashCode() for correct and expected behavior, especially when storing custom objects:
     *
     * ✅ Hash-based Collections (Must override both)
     *
     * Data Structure	Package	Requires equals() + hashCode()
     * HashMap	java.util	✅ Yes
     * HashSet	java.util	✅ Yes
     * Hashtable	java.util	✅ Yes
     * LinkedHashMap	java.util	✅ Yes
     * LinkedHashSet	java.util	✅ Yes
     * ConcurrentHashMap	java.util.concurrent	✅ Yes
     * WeakHashMap	java.util	✅ Yes
     * IdentityHashMap	java.util	❌ No — Uses reference equality only
     * 🔍 These data structures use hash buckets internally and rely on both hashCode() for locating the bucket and equals() for resolving collisions.
     *
     * ✅ Tree-based Collections (Override compareTo() or provide Comparator)
     *
     * Data Structure	Package	Requires compareTo() or Comparator
     * TreeMap	java.util	✅ Must define sort order
     * TreeSet	java.util	✅ Must define sort order
     * ⚠️ These don't require equals()/hashCode() for uniqueness, but instead rely on natural ordering via Comparable or custom ordering via Comparator.
     *
     * ⚠️ Other Collections/Scenarios where equals() is used
     *
     * Scenario	Uses equals() for comparison?	Uses hashCode()?
     * List.contains(Object)	✅ Yes	❌ No
     * List.remove(Object)	✅ Yes	❌ No
     * Collection.containsAll()	✅ Yes	❌ No
     * Map.containsKey()	✅ Yes (and hashCode)	✅ Yes
     * Set.contains()	✅ Yes (and hashCode)	✅ Yes
     * 📌 Any method that checks for object equality (like contains, remove, etc.) uses equals(). Hash-based collections also require a proper hashCode().
     *
     * 🔥 Summary
     *
     * Type	Requires
     * Hash-based	equals() + hashCode()
     * Tree-based	compareTo() / Comparator
     * List/Queue/etc.	Only equals()
     * 💡 Pro Tip:
     * To safely use your custom object in any Java collection:
     *
     * java
     * Copy
     * Edit
     * @Override
     * public boolean equals(Object o) {
     *     // implement logical equality
     * }
     *
     * @Override
     * public int hashCode() {
     *     // must be consistent with equals()
     * }
     * Would you like code examples for TreeSet or HashMap using a custom object?
     *
     *
     *
     */
}
