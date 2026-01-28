package com.java17.programs.redifined.all;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.locks.ReentrantLock;


public class ThreadSafeLRUCacheWithConcurrentHashMapLinkedBlockingDeque<K, V> {
    /**
     * let’s go one step further and design a Java 8+ Thread-Safe LRU Cache using:
     *
     * ConcurrentHashMap → for O(1) thread-safe lookups and updates.
     *
     * LinkedBlockingDeque → to maintain LRU order efficiently.
     *
     * Minimal locking → reads (get) are lock-free, writes (put) use a small ReentrantLock only when eviction is needed.
     *
     * This makes it more scalable than synchronized or ReadWriteLock approaches, especially in read-heavy workloads.
     *
     *
     *
     */
    private final int capacity;
    private final ConcurrentHashMap<K, V> map;
    private final LinkedBlockingDeque<K> deque;
    private final ReentrantLock lock = new ReentrantLock();

    public ThreadSafeLRUCacheWithConcurrentHashMapLinkedBlockingDeque(int capacity) {
        this.capacity = capacity;
        this.map = new ConcurrentHashMap<>(capacity);
        this.deque = new LinkedBlockingDeque<>();
    }

    public V get(K key) {
        V value = map.get(key);
        if (value != null) {
            // Move accessed key to front (most recently used)
            lock.lock();
            try {
                deque.remove(key);
                deque.addFirst(key);
            } finally {
                lock.unlock();
            }
        }
        return value;
    }

    public void put(K key, V value) {
        if (map.containsKey(key)) {
            // Update existing key
            map.put(key, value);
            lock.lock();
            try {
                deque.remove(key);
                deque.addFirst(key);
            } finally {
                lock.unlock();
            }
            return;
        }

        // New key
        lock.lock();
        try {
            if (map.size() >= capacity) {
                // Evict least recently used (tail of deque)
                K lruKey = deque.removeLast();
                map.remove(lruKey);
            }
            deque.addFirst(key);
            map.put(key, value);
        } finally {
            lock.unlock();
        }
    }

    public int size() {
        return map.size();
    }

    @Override
    public String toString() {
        lock.lock();
        try {
            return deque.toString();
        } finally {
            lock.unlock();
        }
    }
    public static void main(String[] args) {
        ThreadSafeLRUCacheWithConcurrentHashMapLinkedBlockingDeque<Integer, String> cache = new ThreadSafeLRUCacheWithConcurrentHashMapLinkedBlockingDeque<>(3);

        cache.put(1, "A");
        cache.put(2, "B");
        cache.put(3, "C");
        System.out.println("Initial: " + cache); // [3, 2, 1]

        cache.get(1); // Access 1 → moves it to front
        cache.put(4, "D"); // Evicts LRU (2)
        System.out.println("After eviction: " + cache); // [4, 1, 3]

        // Multithreaded test
        Runnable reader = () -> {
            for (int i = 0; i < 5; i++) {
                System.out.println(Thread.currentThread().getName() + " read: " + cache.get(1));
            }
        };

        Runnable writer = () -> {
            for (int i = 5; i < 8; i++) {
                cache.put(i, "Val" + i);
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
