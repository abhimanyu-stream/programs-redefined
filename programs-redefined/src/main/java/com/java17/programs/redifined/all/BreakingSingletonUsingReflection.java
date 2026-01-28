package com.java17.programs.redifined.all;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class BreakingSingletonUsingReflection {
    public static void main(String[] args) throws Exception, NoSuchMethodException, SecurityException,
            InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        SingletonPattern instance1 = SingletonPattern.getInstance();

        Constructor<SingletonPattern> constructor = SingletonPattern.class.getDeclaredConstructor();
        constructor.setAccessible(true); // bypass private
        SingletonPattern instance2 = constructor.newInstance();

        System.out.println("Instance 1: " + instance1.hashCode());
        System.out.println("Instance 2: " + instance2.hashCode());
        System.out.print("Two different objects = Singleton broken!");
    }

}
