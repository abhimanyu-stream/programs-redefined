package com.java17.programs.redifined.all;

public class FibonacciSeries {

    public static void main(String[] args) {

        int n = 10;
        int p1 = 0;
        int p2 = 1;
        int p3;

        System.out.println(p1);
        System.out.println(p2);
        for(int i = 0; i < n; i++){

            p3 = p1 + p2;
            System.out.println(p3);
            p1 = p2;
            p2 = p3;

        }
    }
}
