package com.java17.programs.redifined.all;

import java.io.Serializable;

public class SingletonPattern implements Serializable, Cloneable {

    /**
     * Good catch 👍 — your SingletonPattern code has a few issues:
     * 
     * Constructor is public → should be private for singleton.
     * 
     * readResolve() should return the singleton instance.
     * 
     * clone() should prevent cloning → return the same instance or throw exception.
     * 
     * Proper serialization safety and double-checked locking included.
     * 
     */

    // volatile for safe double-checked locking
    private static volatile SingletonPattern singletonPattern = null;

    // private constructor to prevent external instantiation
    private SingletonPattern() {
        if (singletonPattern != null) {
            throw new RuntimeException("You cannot create multiple objects of Singleton");
        }
    }

    // double-checked locking for thread safety
    public static SingletonPattern getInstance() {
        if (singletonPattern == null) {
            synchronized (SingletonPattern.class) {
                if (singletonPattern == null) {
                    singletonPattern = new SingletonPattern();
                }
            }
        }
        return singletonPattern;
    }

    // To prevent creating a new instance during deserialization
    protected Object readResolve() {
        return getInstance();
    }

    // To prevent cloning
    @Override
    protected Object clone() {
        return getInstance();
        // OR throw new CloneNotSupportedException();
    }
}
