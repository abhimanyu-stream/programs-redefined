package com.java17.programs.redifined.all;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class SynchronizedHashMap {

    public static void main(String[] args) {
        // Create a regular HashMap
        Map<String, String> hashMap = new HashMap<>();

        // Synchronize the HashMap using Collections.synchronizedMap
        Map<String, String> synchronizedMap = Collections.synchronizedMap(hashMap);

        // Adding entries to the synchronized map
        synchronizedMap.put("1", "One");
        synchronizedMap.put("2", "Two");
        synchronizedMap.put("3", "Three");

        // Accessing entries from the synchronized map
        System.out.println("Value for key 1: " + synchronizedMap.get("1"));
        System.out.println("Value for key 2: " + synchronizedMap.get("2"));

        // Iterating over the synchronized map (need to use synchronized block)
        synchronized(synchronizedMap) {
            for (Map.Entry<String, String> entry : synchronizedMap.entrySet()) {
                System.out.println(entry.getKey() + " : " + entry.getValue());
            }
        }
    }
}
/**
 * Key Points:
 *
 * Synchronization: The Collections.synchronizedMap() method ensures that all read and write operations on the map are thread-safe.
 *
 * Iterating: If you need to iterate over the map, you must manually synchronize the block of code that iterates through the map. This is because while the map operations are synchronized, iteration itself is not automatically synchronized. In the example above, we use the synchronized(synchronizedMap) block to ensure thread safety during iteration.
 *
 * Thread Safety: When working with a synchronized map, it’s safe to use it across multiple threads as long as the operations are correctly synchronized.
 */