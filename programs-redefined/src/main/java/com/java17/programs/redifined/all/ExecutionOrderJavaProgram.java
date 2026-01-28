package com.java17.programs.redifined.all;

public class ExecutionOrderJavaProgram {

    // Static variable
    static int staticVar = staticMethod();

    // Static block
    static {
        System.out.println("Static block executed");
    }
    static {
        System.out.println("Static block executed");
        sayHello();  // ✅ calling static method
    }

    public static void sayHello() {
        System.out.println("Hello from static method!");
    }

    // Instance variable
    int instanceVar = instanceMethod();

    // Instance block
    {
        System.out.println("Instance block executed");
    }

    // Constructor
    public ExecutionOrderJavaProgram() {
        System.out.println("Constructor executed");
    }

    // Static method
    static int staticMethod() {
        System.out.println("Static variable initialized");
        return 100;
    }

    // Instance method
    int instanceMethod() {
        System.out.println("Instance variable initialized");
        return 10;
    }

    public static void main(String[] args) {
        /**
         * Here’s a Java program that demonstrates the execution flow involving:
         *
         * Static variables
         *
         * Static block
         *
         * Instance variables
         *
         * Instance block
         *
         * Constructor
         *
         * This will help you clearly understand the order in which each block gets executed.
         *
         */
        System.out.println("Main method started");

        System.out.println("\nCreating first object:");
        ExecutionOrderJavaProgram obj1 = new ExecutionOrderJavaProgram();

        System.out.println("\nCreating second object:");
        ExecutionOrderJavaProgram obj2 = new ExecutionOrderJavaProgram();

        System.out.println("\nMain method ended");
    }
}
/**
 * Execution Order Summary:
 * Static variable is initialized → staticVar = staticMethod()
 *
 * Static block runs (only once when the class is loaded)
 *
 * main() method starts
 *
 * For each object creation:
 *
 * Instance variable is initialized → instanceVar = instanceMethod()
 *
 * Instance block runs
 *
 * Constructor is called
 *
 */