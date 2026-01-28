package com.java17.programs.redifined.all;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.*;

public class CustomThreadPoolWithAwaitTerminationEnhancements {
    public enum RejectionPolicy {
        ABORT,
        CALLER_RUNS,
        DISCARD,
        DISCARD_OLDEST
    }

    private final BlockingQueue<FutureTask<?>> taskQueue;
    private final Set<Thread> workers = new HashSet<>();
    private final int poolSize;
    private final RejectionPolicy rejectionPolicy;
    private volatile boolean isShutdown = false;

    public CustomThreadPoolWithAwaitTerminationEnhancements(int poolSize, int queueCapacity, RejectionPolicy rejectionPolicy) {
        this.poolSize = poolSize;
        this.rejectionPolicy = rejectionPolicy;
        this.taskQueue = new ArrayBlockingQueue<>(queueCapacity);
        initThreads();
    }

    private void initThreads() {
        for (int i = 0; i < poolSize; i++) {
            Thread worker = new Thread(() -> {
                try {
                    while (true) {
                        FutureTask<?> task;
                        synchronized (this) {
                            if (isShutdown && taskQueue.isEmpty()) break;
                        }
                        task = taskQueue.poll(1, TimeUnit.SECONDS);
                        if (task != null) {
                            task.run();
                        }
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }, "Worker-" + i);
            workers.add(worker);
            worker.start();
        }
    }

    public <T> FutureTask<T> submit(Callable<T> task) {
        if (isShutdown) throw new RejectedExecutionException("Thread pool is shutting down");
        FutureTask<T> futureTask = new FutureTask<>(task);
        if (!taskQueue.offer(futureTask)) {
            handleRejectedTask(futureTask);
        }
        return futureTask;
    }

    public Future<?> submit(Runnable task) {
        return submit((Runnable) Executors.callable((java.lang.Runnable) task));
    }

    /**
     * Enhancements
     * Bounded task queue – configurable capacity.
     *
     * Custom rejection policies, similar to ThreadPoolExecutor.AbortPolicy, CallerRunsPolicy, etc.
     *
     * Enum-based RejectionPolicy for clean extensibility.
     *
     * Graceful fallback when queue is full.
     *
     * How to Use Rejection Policies
     * ABORT: throws RejectedExecutionException.
     *
     * CALLER_RUNS: runs task in caller thread.
     *
     * DISCARD: silently discards task.
     *
     * DISCARD_OLDEST: removes oldest task in queue and adds the new one.
     */
    private void handleRejectedTask(FutureTask<?> task) {
        switch (rejectionPolicy) {
            case ABORT:
                throw new RejectedExecutionException("Task queue is full.");
            case CALLER_RUNS:
                System.out.println("Executing task in caller thread: " + Thread.currentThread().getName());
                task.run();
                break;
            case DISCARD:
                System.out.println("Task discarded.");
                break;
            case DISCARD_OLDEST:
                taskQueue.poll(); // remove oldest
                taskQueue.offer(task);
                System.out.println("Oldest task discarded, new task added.");
                break;
        }
    }

    public void shutdown() {
        synchronized (this) {
            isShutdown = true;
        }
    }

    public void awaitTermination() {
        for (Thread t : workers) {
            try {
                t.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public boolean awaitTermination(long timeout, TimeUnit unit) {
        long endTime = System.nanoTime() + unit.toNanos(timeout);
        boolean terminated = true;
        for (Thread t : workers) {
            long timeLeft = endTime - System.nanoTime();
            if (timeLeft <= 0) {
                terminated = false;
                break;
            }
            try {
                t.join(TimeUnit.NANOSECONDS.toMillis(timeLeft));
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return false;
            }
        }
        return terminated;
    }

    public boolean isShutdown() {
        return isShutdown;
    }

    // Demo
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        CustomThreadPoolWithAwaitTerminationEnhancements pool = new CustomThreadPoolWithAwaitTerminationEnhancements(
                3,           // 3 worker threads
                5,           // 5 task queue capacity
                RejectionPolicy.CALLER_RUNS // Change to ABORT, DISCARD, DISCARD_OLDEST to test behavior
        );

        List<FutureTask<Object>> futures = new ArrayList<>();

        for (int i = 1; i <= 10; i++) {
            int taskId = i;
            FutureTask<Object> future = pool.submit(() -> {
                try {
                    Thread.sleep(1000);
                    System.out.println("Task " + taskId + " processed by " + Thread.currentThread().getName());
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                return null;
            });
            futures.add(future);
        }

        pool.shutdown();

        if (pool.awaitTermination(10, TimeUnit.SECONDS)) {
            System.out.println("✅ All tasks completed.");
        } else {
            System.out.println("⏱️ Timeout while waiting for termination.");
        }
    }
}
