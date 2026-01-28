package com.java17.programs.redifined.all;

import java.util.concurrent.CompletableFuture;

public class PaymentProcessingPipelineCompletableFutureWithReturn {

    public static void main(String[] args) {
        CompletableFuture<Void> future = validatePaymentAsync("user123", 500)
                .thenCompose(valid -> {
                    if (valid) {
                        return processTransaction("user123", 500);
                    } else {
                        // Match type: return a failed CompletableFuture<String>
                        return CompletableFuture.failedFuture(new RuntimeException("Validation failed"));
                    }
                })
                .thenCompose(txnId -> sendNotification("user123", txnId))
                .thenAccept(result -> System.out.println("Flow completed: " + result))
                .exceptionally(ex -> {
                    System.err.println("Error in processing: " + ex.getMessage());
                    return null;
                });

        // Wait for all async tasks to finish (for demo purposes)
        future.join();
    }

    static CompletableFuture<Boolean> validatePaymentAsync(String userId, int amount) {
        return CompletableFuture.supplyAsync(() -> {
            simulateDelay();
            System.out.println("Validated payment for user: " + userId);
            return amount <= 1000;
        });
    }

    static CompletableFuture<String> processTransaction(String userId, int amount) {
        return CompletableFuture.supplyAsync(() -> {
            simulateDelay();
            String txnId = "TXN-" + System.currentTimeMillis();
            System.out.println("Processed transaction: " + txnId);
            return txnId;
        });
    }

    static CompletableFuture<String> sendNotification(String userId, String txnId) {
        return CompletableFuture.supplyAsync(() -> {
            simulateDelay();
            String result = "Notification sent to " + userId + " for txn: " + txnId;
            System.out.println(result);
            return result;
        });
    }

    static void simulateDelay() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ignored) {
        }
    }
}
