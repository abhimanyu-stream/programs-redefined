package com.java17.programs.redifined.all;


import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedDeque;

/**
 *
 a Thread-Safe LRU Cache implementation in Java.
 We’ll make it efficient and safe for multi-threaded environments.

 Which One to Use?

 Option 1 (LinkedHashMap + synchronized) → Simple, good for moderate multi-threaded use.

 Option 2 (ConcurrentHashMap + Deque) → Scales better under heavy concurrency, avoids global lock on reads.
 */
public class HighConcurrencyLRUCacheWithConcurrentHashMapDeque<K, V> {
    private final int capacity;
    private final ConcurrentHashMap<K, V> map;
    private final ConcurrentLinkedDeque<K> order;

    public HighConcurrencyLRUCacheWithConcurrentHashMapDeque(int capacity) {
        //If you expect many threads accessing cache simultaneously, a non-blocking structure is better:
        this.capacity = capacity;
        this.map = new ConcurrentHashMap<>();
        this.order = new ConcurrentLinkedDeque<>();
    }

    public V get(K key) {
        if (!map.containsKey(key)) return null;
        synchronized (this) {
            order.remove(key);
            order.addFirst(key);
        }
        return map.get(key);
    }

    public synchronized void put(K key, V value) {
        if (map.containsKey(key)) {
            order.remove(key);
        } else if (map.size() == capacity) {
            K lruKey = order.removeLast();
            map.remove(lruKey);
        }
        order.addFirst(key);
        map.put(key, value);
    }

    public int size() {
        return map.size();
    }

    @Override
    public String toString() {
        return map.toString();
    }
}
/**
 * An LRU Cache stands for Least Recently Used Cache.
 * It’s a data structure that stores a fixed number of items, and when it reaches its capacity, it removes the least recently used item before inserting a new one.
 *
 * 🔹 Key Concepts
 *
 * Cache → A temporary storage layer for fast access.
 *
 * LRU Policy → When the cache is full, evict the item that was used the longest time ago.
 *
 * Operations usually supported:
 *
 * get(key) → Retrieve a value if it exists in cache, else return -1.
 *
 * put(key, value) → Insert/Update a value, evicting the LRU item if full.
 *
 * 🔹 Example
 *
 * Suppose cache capacity = 2
 *
 * put(1, A) → cache = {1=A}
 *
 * put(2, B) → cache = {1=A, 2=B}
 *
 * get(1) → returns A (1 becomes most recently used) → order = {2, 1}
 *
 * put(3, C) → cache full, evict least recently used (2), so cache = {1=A, 3=C}
 *
 * 🔹 Implementation Details
 *
 * Efficient implementation requires O(1) time for both get and put.
 * This is achieved using:
 *
 * HashMap (stores key → pointer to node).
 *
 * Doubly Linked List (to track recency order; head = most recent, tail = least recent).
 *
 * 🔹 Real-World Uses
 *
 * Operating Systems → Page replacement in memory management.
 *
 * Databases → Query caching.
 *
 * Web Browsers → Storing recently visited pages/images.
 *
 * Distributed Systems → Caching API responses.
 */