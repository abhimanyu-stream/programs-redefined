package com.java17.programs.redifined.all;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class CompletableFuturePayment {


    public static void main(String[] args) throws ExecutionException, InterruptedException {


        CompletableFuture<Double> priceCheck = checkPriceAmount(5000.00);
        CompletableFuture<String> locationCheck = checkLocation("Nigeria");
        CompletableFuture<String> userCheck = checkUser("user-12");



        //CompletableFuture<java.lang.Void> result = priceCheck.thenApplyAsync(price->locationCheck.thenApplyAsync(location->userCheck.thenAccept(()-> System.out.println("ok"));
        //result.get();


    }

    private static CompletableFuture<String> checkUser(String s) {

        if(s.equals("user-12")) {
            return CompletableFuture.supplyAsync(()-> "Blocked User");

        }
        return CompletableFuture.supplyAsync(()-> "OK User");
    }

    private static CompletableFuture<String> checkLocation(String country) {


        if(country.equals("Nigeria")) {
            return CompletableFuture.supplyAsync(()-> "fraud");
        }


        return CompletableFuture.supplyAsync(()-> "OK");
    }

    private static CompletableFuture<Double> checkPriceAmount(double v) {


              if(v > 50000.00)
                  return  CompletableFuture.supplyAsync(()->4.5);




        return CompletableFuture.supplyAsync(()->1.0);
    }


}
