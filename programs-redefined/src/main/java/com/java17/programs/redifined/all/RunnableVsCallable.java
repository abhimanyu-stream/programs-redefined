package com.java17.programs.redifined.all;

import java.lang.Runnable;
import java.util.concurrent.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.function.Supplier;

public class RunnableVsCallable {

    public static void main(String[] args) throws ExecutionException, InterruptedException {


        //Runnable Example (no return)
        Runnable task = () -> {
            System.out.println("Running in thread: " + Thread.currentThread().getName());
        };

        new Thread(task).start();


        //Callable Example (returns result)
        Callable<String> task2 = () -> {
            return "Result from " + Thread.currentThread().getName();
        };

        ExecutorService ex = Executors.newSingleThreadExecutor();
        Future<String> future = ex.submit(task2);
        System.out.println("Result: " + future.get()); // Blocks and gets result
        ex.shutdown();



        //Using CompletableFuture with Runnable and Callable
        //CompletableFuture.runAsync(Runnable) → 🔸 Does NOT return result

        CompletableFuture<Void> runAsync = CompletableFuture.runAsync(() -> {
            System.out.println("Running async task in: " + Thread.currentThread().getName());
        });

        runAsync.join(); // Wait for completion

        //CompletableFuture.supplyAsync(Supplier<T>) → 🔸 Returns result
        CompletableFuture<String> supplyAsync = CompletableFuture.supplyAsync(() -> {
            return "Result from " + Thread.currentThread().getName();
        });

        String result = supplyAsync.join(); // or future.get();
        System.out.println(result);

        // Convert Callable to Supplier (for use in supplyAsync)
        Callable<String> callable = () -> "Hello from Callable";

        Supplier<String> supplier = () -> {
            try {
                return callable.call();
            } catch (java.lang.Exception e) {
                throw new RuntimeException(e);
            }
        };

        CompletableFuture<Void> runFuture = CompletableFuture.runAsync(() -> {
            // No return
            System.out.println("Runnable task: " + Thread.currentThread().getName());
        });

        CompletableFuture<String> supplyFuture = CompletableFuture.supplyAsync(() -> {
            // Return a result
            return "Callable-like task result";
        });

        runFuture.join(); // Wait for void task
        System.out.println(supplyFuture.join()); // Get result



    }

}
/**
 * Summary: When to Use What?
 * Task Type	                      Use Interface	Use with	            Returns result?	             Use in CompletableFuture?
 * Simple task	                        Runnable	Thread, runAsync	              ❌ No	             CompletableFuture.runAsync()
 * Background task with result	        Callable<V>	ExecutorService	             ✅ Yes	             Wrap in Supplier<T> + supplyAsync
 * Async task returning value	        Supplier<T>	supplyAsync()	             ✅ Yes	                     ✅ Yes
 */