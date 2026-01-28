package com.java17.programs.redifined.all;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * A custom thread pool implementation with await termination support.
 * This implementation wraps a standard ExecutorService for reliability
 * while providing custom termination behavior.
 */
public class CustomThreadPoolWithAwaitTermination {
    private final java.util.concurrent.ExecutorService executor;
    private final AtomicBoolean isShutdown;
    private final int poolSize;

    /**
     * Creates a new thread pool with the specified number of threads.
     *
     * @param poolSize the number of threads in the pool
     * @throws IllegalArgumentException if poolSize is less than 1
     */
    public CustomThreadPoolWithAwaitTermination(int poolSize) {
        if (poolSize < 1) {
            throw new IllegalArgumentException("Pool size must be at least 1");
        }
        this.poolSize = poolSize;
        this.executor = java.util.concurrent.Executors.newFixedThreadPool(poolSize, r -> {
            Thread t = new Thread(r);
            t.setName("Worker-" + t.getId());
            return t;
        });
        this.isShutdown = new AtomicBoolean(false);
    }

    /**
     * Executes the given command at some time in the future.
     *
     * @param command the runnable task
     * @throws java.util.concurrent.RejectedExecutionException if the task cannot be scheduled for execution
     */
    public void execute(java.lang.Runnable command) {
        if (command == null) throw new NullPointerException();
        if (isShutdown.get()) {
            throw new java.util.concurrent.RejectedExecutionException("ThreadPool is shutdown");
        }
        executor.execute(command);
    }

    /**
     * Submits a Callable task for execution and returns a Future
     * representing the pending results of the task.
     *
     * @param <T> the type of the task's result
     * @param task the task to submit
     * @return a Future representing pending completion of the task
     * @throws java.util.concurrent.RejectedExecutionException if the task cannot be scheduled for execution
     */
    public <T> java.util.concurrent.Future<T> submit(java.util.concurrent.Callable<T> task) {
        if (task == null) throw new NullPointerException();
        if (isShutdown.get()) {
            throw new java.util.concurrent.RejectedExecutionException("ThreadPool is shutdown");
        }
        return executor.submit(task);
    }

    /**
     * Submits a Runnable task for execution and returns a Future representing
     * that task, which will upon completion return the given result.
     *
     * @param <T> the type of the result
     * @param task the task to submit
     * @param result the result to return upon successful completion
     * @return a Future representing pending completion of the task
     * @throws java.util.concurrent.RejectedExecutionException if the task cannot be scheduled for execution
     */
    public <T> java.util.concurrent.Future<T> submit(java.lang.Runnable task, T result) {
        if (task == null) throw new NullPointerException();
        if (isShutdown.get()) {
            throw new java.util.concurrent.RejectedExecutionException("ThreadPool is shutdown");
        }
        return executor.submit(task, result);
    }

    /**
     * Submits a Runnable task for execution and returns a Future representing
     * that task, which will upon completion return null.
     *
     * @param task the task to submit
     * @return a Future representing pending completion of the task
     * @throws java.util.concurrent.RejectedExecutionException if the task cannot be scheduled for execution
     */
    public java.util.concurrent.Future<?> submit(java.lang.Runnable task) {
        if (task == null) throw new NullPointerException();
        if (isShutdown.get()) {
            throw new java.util.concurrent.RejectedExecutionException("ThreadPool is shutdown");
        }
        return executor.submit(task);
    }

    /**
     * Initiates an orderly shutdown in which previously submitted
     * tasks are executed, but no new tasks will be accepted.
     */
    public void shutdown() {
        if (isShutdown.compareAndSet(false, true)) {
            executor.shutdown();
        }
    }

    /**
     * Attempts to stop all actively executing tasks and halts the
     * processing of waiting tasks.
     *
     * @return list of tasks that never commenced execution
     */
    public List<java.lang.Runnable> shutdownNow() {
        isShutdown.set(true);
        return executor.shutdownNow();
    }

    /**
     * Returns true if this executor has been shut down.
     *
     * @return true if this executor has been shut down
     */
    public boolean isShutdown() {
        return isShutdown.get();
    }

    /**
     * Returns true if all tasks have completed following shut down.
     *
     * @return true if all tasks have completed following shut down
     */
    public boolean isTerminated() {
        return executor.isTerminated();
    }

    /**
     * Blocks until all tasks have completed execution after a shutdown
     * request, or the timeout occurs, or the current thread is
     * interrupted, whichever happens first.
     *
     * @param timeout the maximum time to wait
     * @param unit the time unit of the timeout argument
     * @return true if this executor terminated and false if the timeout elapsed before termination
     * @throws InterruptedException if interrupted while waiting
     */
    public boolean awaitTermination(long timeout, java.util.concurrent.TimeUnit unit) throws InterruptedException {
        return executor.awaitTermination(timeout, unit);
    }

    /**
     * Example usage of the custom thread pool.
     */
    public static void main(String[] args) {
        CustomThreadPoolWithAwaitTermination pool = new CustomThreadPoolWithAwaitTermination(4);
        List<java.util.concurrent.Future<?>> futures = new ArrayList<>();

        // Submit Runnable tasks
        for (int i = 1; i <= 5; i++) {
            final int taskId = i;
            java.lang.Runnable task = new java.lang.Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(1000);
                        System.out.println("Runnable Task " + taskId + " processed by " + Thread.currentThread().getName());
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
            };
            futures.add(pool.submit(task));
        }

        // Submit Callable tasks
        for (int i = 6; i <= 10; i++) {
            final int taskId = i;
            java.util.concurrent.Callable<String> task = new java.util.concurrent.Callable<String>() {
                @Override
                public String call() throws InterruptedException {
                    Thread.sleep(1000);
                    return "Callable Task " + taskId + " processed by " + Thread.currentThread().getName();
                }
            };
            futures.add(pool.submit(task));
        }

        // Get results
        for (java.util.concurrent.Future<?> future : futures) {
            try {
                System.out.println("Result: " + future.get());
            } catch (InterruptedException | java.util.concurrent.ExecutionException e) {
                System.err.println("Error getting future result: " + e.getMessage());
            }
        }

        // Shutdown and await termination
        pool.shutdown();
        try {
            if (pool.awaitTermination(5, java.util.concurrent.TimeUnit.SECONDS)) {
                System.out.println("✅ All tasks completed within timeout");
            } else {
                System.out.println("⏱️ Timeout while waiting for thread pool termination");
            }
        } catch (InterruptedException e) {
            System.err.println("Error waiting for termination: " + e.getMessage());
            Thread.currentThread().interrupt();
        }
    }
}

/**
 * Final Signature Summary
 * Method                          Return Type  Notes
 * submit(Callable<T>)             Future<T>    ✅ Delegates to standard ExecutorService
 * submit(Runnable)                Future<?>    ✅ Delegates to standard ExecutorService
 * shutdown()                      void         ✅ Graceful shutdown
 * awaitTermination(timeout, unit) boolean      ✅ Delegates to standard ExecutorService
 * execute(Runnable)               void         ✅ Delegates to standard ExecutorService
 */
/**
 * Key Improvements
 * Feature                   Description
 * Reliable Implementation   Uses standard Java ExecutorService
 * Type-safe Delegation      All tasks properly typed with full class names
 * Graceful Shutdown         Stops after tasks are complete
 * Thread Safety             Uses atomic operations for shutdown state
 * Clear Implementation      Avoids name conflicts with fully qualified names
 */