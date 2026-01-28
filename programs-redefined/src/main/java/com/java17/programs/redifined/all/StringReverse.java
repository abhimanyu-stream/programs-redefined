package com.java17.programs.redifined.all;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class StringReverse {

    public static void main(String[] args) {

        String str = "Java Learning Center JLC";
        // Reverse the string using streams
        String reversed = IntStream.range(0, str.length())
                .mapToObj(i -> str.charAt(str.length() - 1 - i))
                .map(String::valueOf)
                .collect(Collectors.joining());

        System.out.println("Reversed String: " + reversed);

        if (reversed.equals(str)) {
            System.out.println(str +" Palindrome");
        } else {
            System.out.println(str +" Not a palindrome");
        }
    }

}
