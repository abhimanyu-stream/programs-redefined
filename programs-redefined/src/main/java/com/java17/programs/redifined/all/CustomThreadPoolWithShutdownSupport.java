package com.java17.programs.redifined.all;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.*;

public class CustomThreadPoolWithShutdownSupport {
    private final BlockingQueue<FutureTask<?>> taskQueue = new LinkedBlockingQueue<>();
    private final Set<Thread> workers = new HashSet<>();
    private final int poolSize;
    private volatile boolean isShutdown = false;

    public CustomThreadPoolWithShutdownSupport(int poolSize) {
        this.poolSize = poolSize;
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
        taskQueue.offer(futureTask);
        return futureTask;
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

    public boolean isShutdown() {
        return isShutdown;
    }

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        CustomThreadPoolWithShutdownSupport pool = new CustomThreadPoolWithShutdownSupport(4);

        List<FutureTask<String>> futures = new ArrayList<>();

        for (int i = 1; i <= 10; i++) {
            int taskId = i;
            FutureTask<String> future = pool.submit(() -> {
                Thread.sleep(1000);
                return "Task " + taskId + " processed by " + Thread.currentThread().getName();
            });
            futures.add(future);
        }

        // Collect results
        for (FutureTask<String> f : futures) {
            System.out.println("Result: " + f.get());
        }

        pool.shutdown();
        pool.awaitTermination();

        System.out.println("All tasks completed. Pool shut down.");
    }
}
/**
 * Key Benefits
 * Feature	Supported?	Description
 * Manual Shutdown	✅	Via shutdown()
 * Await Completion	✅	Via awaitTermination()
 * Future Support	✅	Returns Future<T> for submitted tasks
 * Blocking Queue	✅	Safe for multiple producers/consumers
 * Reuse of Threads	✅	Like a real thread pool
 * Auto Exit	✅	Worker exits when shutdown and queue is empty
 */