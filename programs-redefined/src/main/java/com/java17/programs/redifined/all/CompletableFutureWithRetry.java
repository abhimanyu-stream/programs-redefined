package com.java17.programs.redifined.all;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.function.Supplier;

public class CompletableFutureWithRetry {

    //final int[] count = new int[3];
    int i = 0;
    public static void main(String[] args) {

    }
    <T> CompletableFuture <T> retryAsync(Supplier <T> task, int maxAttempts) {// maxAttempts = 3

        return CompletableFuture.supplyAsync(task)

                .handle((result, ex) -> {
                    if (ex == null) return CompletableFuture.completedFuture(result);
                    //count[i] = i+ 1;
                    i++;

                    if (maxAttempts !=0) return retryAsync(task, maxAttempts - i);
                    else throw new CompletionException(ex);
                }).thenCompose(Function.identity());
    }

}
