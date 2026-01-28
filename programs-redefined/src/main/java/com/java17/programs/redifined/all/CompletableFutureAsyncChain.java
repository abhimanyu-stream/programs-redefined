package com.java17.programs.redifined.all;

import java.util.concurrent.CompletableFuture;
import java.util.logging.Logger;

public class CompletableFutureAsyncChain {
    private static final Logger log = Logger.getLogger(CompletableFutureAsyncChain.class.getName());

    public static void main(String[] args) {
        CompletableFuture.supplyAsync(() -> getUserData("user123"))
                .thenApply(data -> enrichData(data))
                .thenAccept(System.out::println)
                .exceptionally(ex -> {
                    log.info("Error: " + ex.getMessage());
                    return null;
                });

       try {
            Thread.sleep(2000); // Adjust if needed
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private static String getUserData(String userId) {
        // Simulated data fetch
        return "RawData for " + userId;
    }

    private static String enrichData(String data) {
        // Simulated enrichment
        return "Enriched[" + data + "]";
    }
}
