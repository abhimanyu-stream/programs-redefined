package com.java17.programs.redifined.all;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

public class PaymentProcessingPipelineCompletableFutureNoReturn {
    public static void main(String[] args) {
        System.out.println("Payment request received");

        CompletableFuture.supplyAsync(() -> validatePayment("user123", 500))
                .thenCompose(valid -> {
                    if (valid) {
                        return processTransaction("user123", 500);
                    } else {
                        return CompletableFuture.failedFuture(new RuntimeException("Validation failed"));
                    }
                })
                .thenCompose(transactionId -> sendNotification("user123", transactionId))
                .thenAccept(result -> System.out.println("Flow completed: " + result))
                .exceptionally(ex -> {
                    System.err.println("Error in processing: " + ex.getMessage());
                    return null;
                });





    }




    // Step 1: Validate Payment
    private static void simulateDelay() {
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException ignored) {}
    }


    public static boolean validatePayment(String userId, int amount) {
        simulateDelay();
        System.out.println("Validated payment for user: " + userId);
        return amount <= 1000;  // Simple rule: allow only ≤ 1000
    }

    // Step 2: Process Transaction
    public static CompletableFuture<String> processTransaction(String userId, int amount) {
        return CompletableFuture.supplyAsync(() -> {
            simulateDelay();
            String txnId = "TXN" + System.currentTimeMillis();
            System.out.println("Processed payment of " + amount + " for " + userId + ", TXN ID: " + txnId);
            return txnId;
        });
    }

    // Step 3: Send Notification
    public static CompletableFuture<String> sendNotification(String userId, String txnId) {
        return CompletableFuture.supplyAsync(() -> {
            simulateDelay();
            System.out.println("Notification sent to " + userId + " for transaction " + txnId);
            return "Notification success";
        });
    }
    /**
     * non-blocking execution:
     *
     * Payment request received
     * Validated payment for user: user123
     * Processed payment of 500 for user123, TXN ID: TXN...
     * Notification sent to user123 for transaction TXN...
     * Flow completed: Notification success
     * 🔍 Key Concepts Used:
     * supplyAsync() → Starts async task with return value
     *
     * thenCompose() → Used when next step returns another CompletableFuture
     *
     * thenAccept() → Consumes the final result
     *
     * exceptionally() → Handles failure anywhere in the chain
     */
}
