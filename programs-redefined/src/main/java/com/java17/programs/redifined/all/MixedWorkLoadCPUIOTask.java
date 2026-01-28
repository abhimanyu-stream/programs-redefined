package com.java17.programs.redifined.all;

import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.Future;

public class MixedWorkLoadCPUIOTask {
    static int numberOfTasks = 10;
    static int numberOfThreads = 4;


    public static void main(String[] args) throws InterruptedException, ExecutionException {
        // You use a BlockingQueue to hold FutureTask<String> objects (these encapsulate both the task and the result).
        // It serves as the communication bridge between main thread (producer) and worker threads (consumers).
        BlockingQueue<FutureTask<String>> taskQueue = new LinkedBlockingQueue<>();
        // Custom thread pool using Runnable Set
        Set<Thread> threadSet = new HashSet<>();// its threadSetSet not thread safe

        List<java.util.concurrent.Future<String>> futures = new ArrayList<>();// results are stored here and it's a Consumer

        // Submit Tasks to the Queue
        // Submit tasks (simulate Callable)
        for (int i = 1; i <= numberOfTasks; i++) {
            int taskId = i;
            FutureTask<String> task = new FutureTask<>(() -> {
                String threadName = Thread.currentThread().getName();

                // I/O-bound phase (simulate reading from DB or API)
                Thread.sleep(500);

                // CPU-bound phase (data transformation)
                int value = new Random().nextInt(10000);
                int result = (int) Math.sqrt(value) * 42;

                return "Task " + taskId + " → computed " + result + " in " + threadName;
            });

            futures.add(task); // Store reference to future . Stores each future in a list to later fetch the
            // results.

            // Submitting Tasks to the Queue
            boolean result = taskQueue.offer(task);// It's a Producer, Put the task into the queue .Adds task to queue
            // (taskQueue.offer(task)).
            // OR ->>>> taskQueue.put(task); // void
            System.out.println(result);// true
        }

        // Create Worker Threads
        for (int i = 0; i < numberOfThreads; i++) {
            Thread worker = new Thread(() -> {// Create Worker Threads 1 2, 3 ,.....
                // This block is the implementation of the Runnable run() method
                // Definition of Job that will be done/executed by Thread Object
                try {

                    while (true) {
                        FutureTask<String> task = taskQueue.poll(2, TimeUnit.SECONDS); // Wait for a task from taskQueue
                        // for 2 seconds , no task came
                        // in 2 seconds then its shutdown
                        // itself
                        if (task == null)
                            break; // Exit if no task in time (simulate shutdown)
                        task.run(); // Run the actual task
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }, "Worker-" + i);// this line is assigning name to each Thread Object
            threadSet.add(worker);
        }

        // Start all custom threads
        // This starts all 4 worker threads, and they begin polling the queue for tasks.
        threadSet.forEach(Thread::start);

        // Wait for all results
        for (Future<String> future : futures) {
            String result = future.get(); // Blocking wait. // Blocks until task is done
            System.out.println("Consumer received: " + result);
        }

        // Join worker threads to exit cleanly
        /**
         * for (Thread t : threadSet) {
         * t.join();
         * }
         */
        // threadSet.forEach(Thread::join);// unhandled InterruptedException
        threadSet.forEach(thread -> {
            try {
                thread.join();
            } catch (InterruptedException e) {
                // Restore interrupt flag so higher-level code knows it was interrupted
                Thread.currentThread().interrupt();
                System.err.println("Thread was interrupted while waiting: " + e.getMessage());
            }
        });

        System.out.println("All tasks completed. Exiting.");
    }

}
