package com.java17.programs.redifined.all;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.*;

public class CustomThreadPoolThreadCPUIOTask {

    public static void main(String[] args) throws Exception, ExecutionException, InterruptedException {
        int numberOfTasks = 10;
        int numberOfThreads = 3;

        BlockingQueue<FutureTask<String>> taskQueue = new LinkedBlockingQueue<>();
        List<FutureTask<String>> futures = new ArrayList<>();
        Set<Thread> threadSet = new HashSet<>();

        HttpClient httpClient = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(5))
                .build();

        // ---------------------------------------------------
        // SUBMIT TASKS: mix of synchronous + asynchronous API calls
        // ---------------------------------------------------
        for (int i = 1; i <= numberOfTasks; i++) {
            int taskId = i;

            FutureTask<String> task = new FutureTask<>(() -> {
                String threadName = Thread.currentThread().getName();
                if (taskId % 2 == 0) {
                    // 🌐 Sync API Call
                    String response = makeSyncApiCall(httpClient, taskId);
                    return "[SYNC] Task " + taskId + " → " + response + " processed by " + threadName;
                } else {
                    // ⚡ Async API Call
                    String response = makeAsyncApiCall(httpClient, taskId).get(5, TimeUnit.SECONDS);
                    return "[ASYNC] Task " + taskId + " → " + response + " processed by " + threadName;
                }
            });

            futures.add(task);
            boolean result = taskQueue.offer(task);
            System.out.println("Task " + taskId + " submitted in taskQueue: " + result);
        }

        // ---------------------------------------------------
        // CREATE WORKER THREADS
        // ---------------------------------------------------
        for (int i = 0; i < numberOfThreads; i++) {
            Thread worker = new Thread(() -> {
                try {
                    while (true) {
                        FutureTask<String> task = taskQueue.poll(3, TimeUnit.SECONDS);
                        if (task == null) break; // no more tasks, shutdown
                        task.run();
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }, "Worker-" + i);

            threadSet.add(worker);
            worker.start();
        }

        // ---------------------------------------------------
        // COLLECT RESULTS
        // ---------------------------------------------------
        for (FutureTask<String> future : futures) {
            System.out.println("RESULT -> " + future.get());
        }

        System.out.println("\nAll tasks completed.");
    }

    // ---------------------------------------------------
    // Helper Methods
    // ---------------------------------------------------
    private static String makeSyncApiCall(HttpClient client, int id) {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://jsonplaceholder.typicode.com/todos/" + id))
                    .GET()
                    .timeout(Duration.ofSeconds(3))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return "ResponseCode=" + response.statusCode() + ", BodyLength=" + response.body().length();
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private static CompletableFuture<String> makeAsyncApiCall(HttpClient client, int id) {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://jsonplaceholder.typicode.com/todos/" + id))
                .GET()
                .timeout(Duration.ofSeconds(3))
                .build();

        return client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(response -> "ResponseCode=" + response.statusCode() + ", BodyLength=" + response.body().length())
                .exceptionally(ex -> "AsyncCallError: " + ex.getMessage());
    }
}

/**
 * What’s Happening
 * Part	Description
 * Task creation	Each task alternates between synchronous and asynchronous API calls (taskId % 2 == 0).
 * HTTP Client	Uses java.net.http.HttpClient (modern, async-capable).
 * Sync call	Blocks with client.send(request, ...).
 * Async call	Non-blocking with client.sendAsync(...), returns a CompletableFuture.
 * Custom ThreadPool	Tasks are placed in a BlockingQueue, and multiple worker threads poll and execute them.
 * Timeout	If no task arrives for 3 seconds, worker thread stops gracefully.
 * 🧠 Example Output (shortened)
 * Task 1 submitted: true
 * Task 2 submitted: true
 * Task 3 submitted: true
 * ...
 * RESULT -> [ASYNC] Task 1 → ResponseCode=200, BodyLength=83 processed by Worker-0
 * RESULT -> [SYNC] Task 2 → ResponseCode=200, BodyLength=83 processed by Worker-1
 * RESULT -> [ASYNC] Task 3 → ResponseCode=200, BodyLength=83 processed by Worker-2
 * ...
 * All tasks completed.
 *
 *
 * Would you like me to enhance this version further to include:
 *
 * ✅ graceful shutdown (join all workers)
 *
 * ✅ thread-safe logging (using ConcurrentLinkedQueue or Logger)
 *
 * ✅ metrics (average latency of sync vs async calls)?
 */