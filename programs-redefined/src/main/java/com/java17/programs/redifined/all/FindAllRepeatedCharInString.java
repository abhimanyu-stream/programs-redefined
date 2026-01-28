package com.java17.programs.redifined.all;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class FindAllRepeatedCharInString {
    public static void main(String[] args) {

        String str = "dockerkubernetesjenkinsqwsargocd";
        List<Character> collect = str.chars().mapToObj(i -> (char) i).collect(Collectors.groupingBy(Function.identity(), LinkedHashMap::new, Collectors.counting()))
                .entrySet().stream().filter(f -> f.getValue() > 1L).map(Map.Entry::getKey).collect(Collectors.toList());
        System.out.println(collect);



        // Step 1: Count characters using LinkedHashMap to maintain order
        Map<Character, Long> frequencyMap = str.chars()
                .mapToObj(i -> (char) i)
                .collect(Collectors.groupingBy(Function.identity(), LinkedHashMap::new, Collectors.counting()));

        // Step 2: Filter repeated characters
        List<Character> repeatedChars = frequencyMap.entrySet().stream()
                .filter(entry -> entry.getValue() > 1)
                .map(Map.Entry::getKey)
                .toList();

        System.out.println("Repeated characters: " + repeatedChars);

        // Step 3: Build a string with each repeated character added as many times as it appears
        StringBuilder builder = new StringBuilder();
        for (Map.Entry<Character, Long> entry : frequencyMap.entrySet()) {
            if (entry.getValue() > 1) {
                for (int i = 0; i < entry.getValue(); i++) {
                    builder.append(entry.getKey());
                }
            }
        }

        System.out.println("Repeated characters expanded: " + builder.toString());


    }
}
