package com.java17.programs.redifined.all;


import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ThreadSafeLRUCacheWithReentrantReadWriteLock<K, V> {

    /**
     * a Thread-Safe LRU Cache in Java using ReentrantReadWriteLock.
     *
     * This approach improves concurrency compared to synchronized because:
     *
     * Multiple readers can access concurrently.
     *
     * Writers (put/evict operations) acquire exclusive lock.
     *
     * Key Points
     *
     * Read operations (get, size, toString) → use readLock(), so multiple readers can run in parallel.
     *
     * Write operations (put) → use writeLock(), so only one writer updates at a time.
     *
     * Eviction (removeEldestEntry) happens automatically on put.
     */
    private final int capacity;
    private final Map<K, V> cache;
    private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

    public ThreadSafeLRUCacheWithReentrantReadWriteLock(int capacity) {
        this.capacity = capacity;
        this.cache = new LinkedHashMap<K, V>(capacity, 0.75f, true) {
            @Override
            protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
                return size() > ThreadSafeLRUCacheWithReentrantReadWriteLock.this.capacity; // Evict LRU when over capacity
            }
        };
    }

    public V get(K key) {
        lock.readLock().lock();
        try {
            return cache.get(key);
        } finally {
            lock.readLock().unlock();
        }
    }

    public void put(K key, V value) {
        lock.writeLock().lock();
        try {
            cache.put(key, value);
        } finally {
            lock.writeLock().unlock();
        }
    }

    public int size() {
        lock.readLock().lock();
        try {
            return cache.size();
        } finally {
            lock.readLock().unlock();
        }
    }

    @Override
    public String toString() {
        lock.readLock().lock();
        try {
            return cache.toString();
        } finally {
            lock.readLock().unlock();
        }
    }

    public static void main(String[] args) {
        ThreadSafeLRUCacheWithReentrantReadWriteLock<Integer, String> lru = new ThreadSafeLRUCacheWithReentrantReadWriteLock<>(3);

        lru.put(1, "A");
        lru.put(2, "B");
        lru.put(3, "C");
        System.out.println("Initial: " + lru);

        lru.get(1); // Access 1 → moves it to MRU
        lru.put(4, "D"); // Evicts key 2 (LRU)
        System.out.println("After eviction: " + lru);

        // Run multithreaded test
        Runnable reader = () -> {
            for (int i = 0; i < 5; i++) {
                System.out.println(Thread.currentThread().getName() + " read: " + lru.get(1));
            }
        };

        Runnable writer = () -> {
            for (int i = 5; i < 8; i++) {
                lru.put(i, "Val" + i);
                System.out.println(Thread.currentThread().getName() + " wrote: " + i);
            }
        };

        Thread t1 = new Thread((ThreadGroup) reader, "Reader-1");
        Thread t2 = new Thread((ThreadGroup) writer, "Writer-1");
        Thread t3 = new Thread((ThreadGroup) reader, "Reader-2");

        t1.start();
        t2.start();
        t3.start();
    }
}
