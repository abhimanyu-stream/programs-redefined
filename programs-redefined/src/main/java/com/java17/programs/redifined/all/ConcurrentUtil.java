package com.java17.programs.redifined.all;

import jakarta.annotation.PreDestroy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.function.Supplier;


@Component
public class ConcurrentUtil {


    Logger log = LoggerFactory.getLogger(ConcurrentUtil.class);

    // Used for rate-limiting or user-level request tracking
    private final ConcurrentHashMap<String, AtomicInteger> requestTracker = new ConcurrentHashMap<>();

    // Payment buffer queue for burst traffic control
    private final LinkedBlockingQueue<PayInRequest> paymentQueue = new LinkedBlockingQueue<>(1000);

    // Executes background tasks (e.g., Stripe API calls)
    private final ExecutorService executor = Executors.newFixedThreadPool(10);

    // Fine-grained locking on wallet/account per user
    private final Map<String, ReentrantLock> lockMap = new ConcurrentHashMap<>();

    // For shared configuration reads with infrequent updates
    private final ReadWriteLock configLock = new ReentrantReadWriteLock();

    // Limit concurrent DB/API access (e.g., max 10 concurrent Stripe calls)
    private final Semaphore apiAccessSemaphore = new Semaphore(10);

    // For coordination between init tasks (e.g., Redis + Kafka init)
    private final CountDownLatch initLatch = new CountDownLatch(2); // Adjust for your init services

    // Used for multi-thread coordination (e.g., log + audit)
    private final CyclicBarrier barrier = new CyclicBarrier(2);

    // ==== API ====

    public void trackRequest(String userId) {
        requestTracker.computeIfAbsent(userId, k -> new AtomicInteger(0)).incrementAndGet();
        log.debug("Tracked request for user: {}, count: {}", userId, getRequestCount(userId));
    }

    public int getRequestCount(String userId) {
        return requestTracker.getOrDefault(userId, new AtomicInteger(0)).get();
    }

    public void enqueuePayin(PayInRequest request) throws InterruptedException {
        paymentQueue.put(request);
        log.debug("Enqueued payment request: {}", request.getTxnId());
    }

    public PayInRequest dequeuePayin() throws InterruptedException {
        PayInRequest request = paymentQueue.take();
        log.debug("Dequeued payment request: {}", request.getTxnId());
        return request;
    }

    public <T> Future<T> runAsyncWithResult(Supplier<T> task) {
        return (Future<T>) executor.submit(() -> task.get());
    }

    public void runAsync(Runnable task) {
        executor.submit((java.lang.Runnable) task);
    }

    public void withUserLock(String userId, Runnable task) {
        ReentrantLock lock = lockMap.computeIfAbsent(userId, k -> new ReentrantLock());
        lock.lock();
        try {
            log.debug("Acquired lock for user: {}", userId);
            task.run();
        } finally {
            lock.unlock();
            log.debug("Released lock for user: {}", userId);
        }
    }

    public <T> T withUserLockAndReturn(String userId, Supplier<T> task) {
        ReentrantLock lock = lockMap.computeIfAbsent(userId, k -> new ReentrantLock());
        lock.lock();
        try {
            log.debug("Acquired lock for user: {}", userId);
            return task.get();
        } finally {
            lock.unlock();
            log.debug("Released lock for user: {}", userId);
        }
    }

    /**
     * Asynchronously executes a task with a user-level lock, returning a CompletableFuture
     * This combines the user lock mechanism with asynchronous execution
     */
    public <T> CompletableFuture<T> withUserLockAndReturnAsync(String userId, Supplier<T> task) {
        CompletableFuture<T> future = new CompletableFuture<>();
        
        executor.submit(() -> {
            ReentrantLock lock = lockMap.computeIfAbsent(userId, k -> new ReentrantLock());
            lock.lock();
            try {
                log.debug("Acquired lock for user: {} (async)", userId);
                T result = task.get();
                future.complete(result);
            } finally {
                lock.unlock();
                log.debug("Released lock for user: {} (async)", userId);
            }
        });
        
        return future;
    }

    public void withReadLock(Runnable task) {
        configLock.readLock().lock();
        try {
            task.run();
        } finally {
            configLock.readLock().unlock();
        }
    }

    public <T> T withReadLockAndReturn(Supplier<T> task) {
        configLock.readLock().lock();
        try {
            return task.get();
        } finally {
            configLock.readLock().unlock();
        }
    }

    public void withWriteLock(Runnable task) {
        configLock.writeLock().lock();
        try {
            task.run();
        } finally {
            configLock.writeLock().unlock();
        }
    }

    public <T> T withWriteLockAndReturn(Supplier<T> task) {
        configLock.writeLock().lock();
        try {
            return task.get();
        } finally {
            configLock.writeLock().unlock();
        }
    }

    public void acquireApiAccess(Runnable task) {
        try {
            log.debug("Waiting for API access semaphore, available permits: {}", apiAccessSemaphore.availablePermits());
            apiAccessSemaphore.acquire();
            log.debug("Acquired API access semaphore");
            task.run();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            log.error("Interrupted while waiting for API access", e);
        } finally {
            apiAccessSemaphore.release();
            log.debug("Released API access semaphore");
        }
    }

    public <T> T acquireApiAccessAndReturn(Supplier<T> task) {
        try {
            log.debug("Waiting for API access semaphore, available permits: {}", apiAccessSemaphore.availablePermits());
            apiAccessSemaphore.acquire();
            log.debug("Acquired API access semaphore");
            return task.get();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            log.error("Interrupted while waiting for API access", e);
            throw new RuntimeException("API access interrupted", e);
        } finally {
            apiAccessSemaphore.release();
            log.debug("Released API access semaphore");
        }
    }

    public void awaitStartup() throws InterruptedException {
        log.info("Waiting for initialization to complete...");
        initLatch.await();
        log.info("Initialization completed");
    }

    public void markInitDone() {
        initLatch.countDown();
        log.info("Marked initialization step as done, remaining: {}", initLatch.getCount());
    }

    public void syncAuditAndLog(Runnable task) {
        runAsync(() -> {
            try {
                barrier.await();
                task.run();
            } catch (Throwable e) {
                Thread.currentThread().interrupt();
                log.error("Error during synchronized audit and log", e);
            }
        });
    }

    @PreDestroy
    public void shutdown() {
        log.info("Shutting down ConcurrentUtil executor service");
        executor.shutdown();
        try {
            if (!executor.awaitTermination(5, TimeUnit.SECONDS)) {
                log.warn("Executor did not terminate in the specified time. Forcing shutdown.");
                executor.shutdownNow();
            }
        } catch (InterruptedException e) {
            log.error("Interrupted while waiting for executor shutdown", e);
            executor.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }
}