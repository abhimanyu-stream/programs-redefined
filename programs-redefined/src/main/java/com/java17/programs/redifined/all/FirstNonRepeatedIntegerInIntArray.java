package com.java17.programs.redifined.all;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class FirstNonRepeatedIntegerInIntArray {
    public static void main(String[] args) {

        int[] intArray = { 2, 6, 7, 8, 9, 8, 9, 2};

        List<Integer> collect = Arrays.stream(intArray).boxed().collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                .entrySet().stream().filter(f -> f.getValue() == 1L).map(m -> m.getKey()).collect(Collectors.toList());
        System.out.println(collect);
        System.out.println(collect.stream().findFirst().get());
        System.out.println(collect.stream().skip(1).findFirst().get());


        List<Integer> integerList = Arrays.asList(2, 6, 7, 8, 9, 8, 9, 2);
        List<Integer> collect1 = integerList.stream().collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                .entrySet().stream().filter(f -> f.getValue() == 1L).map(m -> m.getKey()).collect(Collectors.toList());
        System.out.println(collect1);


    }
}
