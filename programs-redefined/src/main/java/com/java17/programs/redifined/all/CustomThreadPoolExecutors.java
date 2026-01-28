package com.java17.programs.redifined.all;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

public class CustomThreadPoolExecutors {
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        int numberOfThreads = 4; // Custom thread pool size
        int numberOfTasks = 10;  // Total tasks to execute

        // Create fixed thread pool
        ExecutorService fixedThreadPool = Executors.newFixedThreadPool(numberOfThreads);


        // List to hold Future responses
        List<Future<String>> futures = new ArrayList<>();

        // Submit tasks
        for (int i = 1; i <= numberOfTasks; i++) {
            int taskId = i;
            Callable<String> task = () -> {
                String threadName = Thread.currentThread().getName();
                Thread.sleep(1000); // Simulate processing
                return "Task " + taskId + " processed by " + threadName;
            };
            futures.add(fixedThreadPool.submit(task));
        }

        // Consumer: wait for responses
        for (Future<String> future : futures) {
            String result = future.get(); // This blocks until the result is available
            System.out.println("Consumer received: " + result);
        }

        // Shutdown executor
        fixedThreadPool.shutdown();
    }
}
