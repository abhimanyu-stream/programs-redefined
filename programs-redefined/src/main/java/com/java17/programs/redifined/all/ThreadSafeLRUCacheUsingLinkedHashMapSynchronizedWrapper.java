package com.java17.programs.redifined.all;


import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 *
 Approach

 There are multiple ways to make an LRU Cache thread-safe:

 Using Collections.synchronizedMap with LinkedHashMap (simple but blocking).

 Using ConcurrentHashMap + ConcurrentLinkedDeque (better concurrency).

 Using ReentrantLock around LinkedHashMap (fine-grained control).

 For most cases, Option 1 with LinkedHashMap is enough, but for higher concurrency, Option 2 is better.
 */

public class ThreadSafeLRUCacheUsingLinkedHashMapSynchronizedWrapper<K, V> {

    private final Map<K, V> cache;

    public ThreadSafeLRUCacheUsingLinkedHashMapSynchronizedWrapper(int capacity) {
        this.cache = Collections.synchronizedMap(new LinkedHashMap<K, V>(capacity, 0.75f, true) {
            @Override
            protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
                return size() > capacity; // Evict LRU
            }
        });
    }

    public V get(K key) {
        return cache.get(key); // synchronized via wrapper
    }

    public void put(K key, V value) {
        cache.put(key, value); // synchronized via wrapper
    }

    public int size() {
        return cache.size();
    }

    @Override
    public String toString() {
        return cache.toString();
    }
    public static void main(String[] args) {
        ThreadSafeLRUCacheUsingLinkedHashMapSynchronizedWrapper<Integer, String> lru = new ThreadSafeLRUCacheUsingLinkedHashMapSynchronizedWrapper<>(3);

        lru.put(1, "A");
        lru.put(2, "B");
        lru.put(3, "C");
        System.out.println(lru); // {1=A, 2=B, 3=C}

        lru.get(1);  // Access 1 → makes it most recently used
        lru.put(4, "D"); // Evicts key 2
        System.out.println(lru); // {3=C, 1=A, 4=D}
    }
}
