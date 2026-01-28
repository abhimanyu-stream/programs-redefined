package com.java17.programs.redifined.all;

public class ScopeOfAStaticBlockJava {
    static int staticVar;

    static {
        System.out.println("Static block executed");
        staticVar = 42;
    }
    int instanceVar = 10;

    static {
        // System.out.println(instanceVar); ❌ This will cause a compile-time error.
        //You can't access instanceVar inside a static block because instance variables belong to an object, which hasn't been created when the static block runs.
    }
    public static void main(String[] args) {
        System.out.println("Main method started");
        System.out.println("Static variable value: " + staticVar);
    }
}
/**
 * Scope of a Static Block in Java
 * A static block is a special block of code that runs once when the class is loaded into memory — before the main method runs or any object is created.
 *
 * ✅ Key Points about Static Block Scope:
 *
 * Feature	Description
 * When it runs	When the class is loaded by the JVM (only once).
 * Runs before	Any constructor, instance block, or main() method.
 * Can access	Only static variables and static methods directly.
 * Can’t access	Instance variables or this, because no object is created yet.
 * Used for	Static initialization logic (e.g., loading configuration, setting up logs).
 * Belongs to	The class itself (not to any object).
 */