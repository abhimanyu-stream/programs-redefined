package com.java17.programs.redifined.all;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class FunctionalInterface {

    public static void main(String[] args) {
        // Predicate: tests a condition and returns true/false
        Predicate<Integer> isEven = number -> number % 2 == 0;
        System.out.println("Is 4 even? " + isEven.test(4)); // true
        System.out.println("Is 5 even? " + isEven.test(5)); // false

        // Function: takes input and returns output
        Function<String, Integer> stringLength = str -> str.length();
        System.out.println("Length of 'Hello': " + stringLength.apply("Hello")); // 5

        // Consumer: takes input and performs an action, returns nothing
        Consumer<String> greet = name -> System.out.println("Hello, " + name + "!");
        greet.accept("Alice"); // Hello, Alice!

        // Supplier: provides or generates a value without input
        Supplier<Double> randomValue = () -> Math.random();
        System.out.println("Random value: " + randomValue.get());


        // 1. Predicate: check if a number is even
        //Predicate<Integer> isEven = num -> num % 2 == 0;

        // 2. Supplier: generate a number (for example, 10)
        Supplier<Integer> supplyNumber = () -> 10;

        // 3. Function: double the number
        Function<Integer, Integer> doubleIt = num -> num * 2;

        // 4. Consumer: print the result
        Consumer<Integer> printResult = result -> System.out.println("Result is: " + result);

        // Chain execution
        if (isEven.test(supplyNumber.get())) {       // Predicate check
            Integer value = supplyNumber.get();       // Supplier provides value
            Integer result = doubleIt.apply(value);   // Function processes value
            printResult.accept(result);              // Consumer prints result
        } else {
            System.out.println("Number is not even.");
        }
    }
}
