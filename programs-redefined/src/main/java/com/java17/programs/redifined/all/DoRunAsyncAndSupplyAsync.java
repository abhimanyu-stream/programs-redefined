package com.java17.programs.redifined.all;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class DoRunAsyncAndSupplyAsync {
    public static void main(String[] args) throws ExecutionException, InterruptedException {

        /**
         * runAsync(Runnable) — No input, no output
         * It takes a Runnable (which has no parameters and returns nothing).
         *
         * Use it when you don’t care about return values.
         */
        CompletableFuture<Void> runAsync = CompletableFuture.runAsync(() -> {
            System.out.println("No input, no output.");
        });
        runAsync.join();

        /**
         * supplyAsync(Supplier<T>) — No input, but returns a value
         * Takes a Supplier<T> — no parameters, but returns something (T).
         */
        CompletableFuture<String> supplyAsync = CompletableFuture.supplyAsync(() -> {
            return "Produced result"; // no input, returns String
        });

        String result = supplyAsync.get();
        System.out.println(result);




        CompletableFuture.supplyAsync(() -> {
                    return 10;               // No input, returns 10
                })
                .thenApply(n -> n * 2)       // Takes input `10`, returns `20`
                .thenAccept(System.out::println); // Takes `20`, prints it (no return)



        CompletableFuture.supplyAsync(() -> {
                    return 10;               // Step 1: Asynchronously supply 10
                })
                .thenApply(n -> n * 2)       // Step 2: Apply a function to double the result (10 → 20)
                .thenCompose(n -> CompletableFuture.supplyAsync(() -> n + 5))  // Step 3: Compose into another async task (20 → 25)
                .thenAccept(composed -> System.out.println("Final result: " + composed));  // Step 4: Print result

        /**
         *  Explanation of thenCompose
         * thenCompose is used when your next step returns a CompletableFuture<T>, not just a value.
         *
         * It's a flattening operator, used when a step itself is asynchronous.
         *
         * In this example:
         *
         * After thenApply, you get 20.
         *
         * You use thenCompose(n -> CompletableFuture.supplyAsync(...)) to simulate another async task (e.g., fetching from DB).
         *
         * The whole chain results in "Final result: 25" printed to the console.
         *
         */








    }
}
/**
 * What about thenApply, thenAccept, etc.?
 * These DO take input: they consume the result of the previous stage.
 *
 * Method	          Input	                       Returns	                Description
 * thenRun()	     ❌ no input	                    Void	                  Runs after previous completes, no input used
 * thenAccept()	     ✅ takes input (from previous)	    Void	                  Consumes the result from previous
 * thenApply()	     ✅ takes input (from previous)	    Value	                  Transforms the result from previous
 * thenCompose()	 ✅ takes input (from previous)	    CompletableFuture<T>	  Returns a new future based on previous result
 */