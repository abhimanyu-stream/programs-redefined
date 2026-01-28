package com.java17.programs.redifined.all;

import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.CompletableFuture;

public class CompletableFuturePaymentService {
    /**
     * Entry point for the asynchronous payment flow.
     * The method is transactional (REQUIRED), but since async code runs in new threads,
     * inner methods manage their own transactions when needed.
     */
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
    public CompletableFuture<String> executePaymentFlow(String userId) {

        int amount = 500; // could be passed as an argument later

        return CompletableFuture
                .supplyAsync(() -> validatePayment(userId, amount))
                .thenCompose(valid -> {
                    if (valid) {
                        // run transaction logic in a separate new transaction
                        return processTransaction(userId, amount);
                    } else {
                        return CompletableFuture.failedFuture(
                                new RuntimeException("Validation failed for user: " + userId)
                        );
                    }
                })
                .thenCompose(transactionId -> sendNotification(userId, transactionId))
                .exceptionally(ex -> {
                    System.err.println("Error in processing flow for " + userId + ": " + ex.getMessage());
                    return "Failed: " + ex.getMessage();
                });
    }

    // ------------------------------
    //  VALIDATION (Non-Transactional)
    // ------------------------------
    private boolean validatePayment(String userId, int amount) {
        System.out.println("Validating payment for " + userId + " amount: " + amount);
        // Add logic: check balance, limits, etc.
        return true;
    }

    // ---------------------------------
    //  PROCESS TRANSACTION (DB OPERATION)
    // ---------------------------------
    private CompletableFuture<String> processTransaction(String userId, int amount) {
        return CompletableFuture.supplyAsync(() -> processTransactionSync(userId, amount));
    }

    /**
     * This runs inside its own transaction because it does DB work.
     * Each async call creates a new transaction context.
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.READ_COMMITTED)
    public String processTransactionSync(String userId, int amount) {
        System.out.println("Processing transaction for " + userId + " in REQUIRES_NEW transaction");

        // Simulate DB insert/update (e.g. save payment record)
        String transactionId = "TXN-" + System.currentTimeMillis();

        System.out.println("Transaction persisted: " + transactionId);
        return transactionId;
    }

    // --------------------------------
    //  SEND NOTIFICATION (Non-DB Async)
    // --------------------------------
    private CompletableFuture<String> sendNotification(String userId, String transactionId) {
        return CompletableFuture.supplyAsync(() -> {
            System.out.println("Sending notification for transaction " + transactionId);
            // Example: call email/SMS service here
            return "Notification sent for " + transactionId;
        });
    }

    // --------------------------------
    //  EXAMPLE TEST MAIN METHOD
    // --------------------------------
    public static void main(String[] args) {
        CompletableFuturePaymentService service = new CompletableFuturePaymentService();
        service.executePaymentFlow("user123")
                .thenAccept(result -> System.out.println("Flow completed: " + result));
    }
}
