package com.java17.programs.redifined.all;

import java.util.concurrent.CompletableFuture;

public class CompletableFutureWithAndWithoutReturn {
    public static void main(String[] args) {

        // These are used after a task that returns void (CompletableFuture<Void>).
        CompletableFuture<Void> runAsyncAndThenRun = CompletableFuture
                .runAsync(() -> {
                    System.out.println("Step 1: runAsync - " + Thread.currentThread().getName());
                })
                .thenRun(() -> {
                    System.out.println("Step 2: thenRun - " + Thread.currentThread().getName());
                });

        runAsyncAndThenRun.join();



        CompletableFuture<Void> supplyAsyncThenAccept = CompletableFuture
                .supplyAsync(() -> "Step 1 result")
                .thenAccept(result -> {
                    System.out.println("Step 2 got: " + result);
                });

        supplyAsyncThenAccept.join();


        //These are used when each step returns a value. thenApply (transformation)
        CompletableFuture<String> thenApplyThenCompose = CompletableFuture
                .supplyAsync(() -> "Step 1: Hello")
                .thenApply(result -> result + ", Step 2: World");

        String composed = thenApplyThenCompose.join();// Output: Hello, Step 2: World
        System.out.println(composed);




        //using thenCompose (flatMap)
        //Use thenCompose when the next step itself returns a CompletableFuture.
        CompletableFuture<String> future = CompletableFuture
                .supplyAsync(() -> "User ID: 123")
                .thenCompose(CompletableFutureWithAndWithoutReturn::getUserDetailsAsync);

        System.out.println(future.join());







        //Mix of Void and Result Chains
        CompletableFuture
                .runAsync(() -> System.out.println("Start processing"))
                .thenRun(() -> System.out.println("Still no return"))
                .thenCompose(v -> CompletableFuture.supplyAsync(() -> "Final result"))
                .thenApply(data -> "Processed: " + data)
                .thenAccept(finalResult -> System.out.println("Consumer got: " + finalResult))
                .join();
        //🧠 Key Method Summary
        //Method	        Input	                             Output Type	             Use Case
        //thenRun	        Runnable	                        CompletableFuture<Void>	     next task doesn’t need result
        //thenAccept	    Consumer<T>	                        CompletableFuture<Void>	     consume previous result
        //thenApply	        Function<T,R>	                    CompletableFuture<R>	     transform previous result
        //thenCompose	    Function<T,CompletableFuture<R>>	CompletableFuture<R>	     async chaining / flattening
        //
        //Would you like a real-world use case chain (e.g., payment request → validate → process → notify)?







    }
    // Simulated method
    private static CompletableFuture<String> getUserDetailsAsync(String id) {
        return CompletableFuture.supplyAsync(() -> "Details for " + id);
    }
}
