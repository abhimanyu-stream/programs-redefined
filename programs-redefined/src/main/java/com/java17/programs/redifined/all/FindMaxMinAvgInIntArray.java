package com.java17.programs.redifined.all;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;
import java.util.*;
import java.util.stream.*;

public class FindMaxMinAvgInIntArray {
    public static void main(String[] args) {

        int[] intArray = { 2, 6, 7, 8, 9, 8, 9, 2};

        int max = Arrays.stream(intArray)
                .boxed()
                .max(Comparator.naturalOrder())
                .orElseThrow();

        System.out.println("Max: " + max);
        int min = Arrays.stream(intArray)
                .boxed()
                .min(Comparator.naturalOrder())
                .orElseThrow();

        System.out.println("Min: " + min);


        IntSummaryStatistics stats = Arrays.stream(intArray).summaryStatistics();

        System.out.println("From int[]:");
        System.out.println("Max: " + stats.getMax());
        System.out.println("Min: " + stats.getMin());
        System.out.println("Average: " + stats.getAverage());
        System.out.println("Sum: " + stats.getSum());


        int secondLargestInt  = Arrays.stream(intArray).boxed().sorted(Comparator.reverseOrder()).distinct().limit(2).skip(1).findFirst().get();
        System.out.println("secondLargestInt "+secondLargestInt);

        List<Integer> integerList = Arrays.asList(2, 6, 7, 8, 9, 8, 9, 2);
        Stream<Integer> sortedReverseOrder = integerList.stream().sorted(Comparator.reverseOrder());
        System.out.println("sortedReverseOrder");
        sortedReverseOrder.forEach(System.out::print);



        Stream<Integer> sortedNaturalOrder = integerList.stream().sorted(Comparator.naturalOrder());
        System.out.println("\nsortedNaturalOrder");
        sortedNaturalOrder.forEach(System.out::print);

        IntSummaryStatistics statistics = integerList.stream()
                .mapToInt(Integer::intValue)
                .summaryStatistics();

        System.out.println("From List<Integer>:");
        System.out.println("Max: " + statistics.getMax());
        System.out.println("Min: " + statistics.getMin());
        System.out.println("Average: " + statistics.getAverage());

        int secondLargestInteger  = integerList.stream().sorted(Comparator.reverseOrder()).distinct().limit(2).skip(1).findFirst().get();
        System.out.println("secondLargestInteger "+secondLargestInteger);





    }
}
