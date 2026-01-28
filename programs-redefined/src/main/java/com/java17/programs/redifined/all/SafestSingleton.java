package com.java17.programs.redifined.all;

public class SafestSingleton {
    public static void main(String[] args) {
        EnumSingleton instance1 = EnumSingleton.INSTANCE;
        EnumSingleton instance2 = EnumSingleton.INSTANCE;

        System.out.println("Instance 1: " + instance1.hashCode());
        System.out.println("Instance 2: " + instance2.hashCode());

        instance1.showMessage();
        /**
         * Why enum Singleton is Best?
         * 
         * Reflection cannot break it.
         * 
         * Serialization automatically returns the same instance.
         * 
         * Simple and clean — no need for volatile, synchronized, or readResolve().
         */
    }

}

enum EnumSingleton {
    INSTANCE;

    public void showMessage() {
        System.out.println("Hello from Enum Singleton!");
    }
}