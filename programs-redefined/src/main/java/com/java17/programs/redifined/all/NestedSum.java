package com.java17.programs.redifined.all;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class NestedSum {

    /**
     *Goal Interpretation
     * You seem to want to:
     *
     * Define a nested structure like [3, 4, [4, [5, 6]]]
     *
     * Flatten all the nested integers
     *
     * Compute their sum using Java Streams
     */
    public static void main(String[] args) {
        List<Object> nested = Arrays.asList(
                3,
                4,
                Arrays.asList(
                        4,
                        Arrays.asList(5, 6)
                )
        );

        int sum = flatten(nested)
                .mapToInt(Integer::intValue)
                .sum();

        System.out.println("Sum: " + sum);  // Output: 22
    }

    // Recursively flattens nested list
    private static Stream<Integer> flatten(List<Object> list) {
        return list.stream()
                .flatMap(o -> {
                    if (o instanceof Integer) {
                        return Stream.of((Integer) o);
                    } else if (o instanceof List<?>) {
                        return flatten((List<Object>) o);
                    } else {
                        return Stream.empty();
                    }
                });
    }
}