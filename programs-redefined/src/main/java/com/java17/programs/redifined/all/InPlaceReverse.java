package com.java17.programs.redifined.all;

import java.util.Arrays;
import java.util.stream.Collectors;

public class InPlaceReverse {

    public static void main(String[] args) {

        String str = "Hello World";

        String inPlaceReversedString = Arrays.stream(str.split(" ")).map(m -> new StringBuilder(m).reverse().toString()).collect(Collectors.joining(" "));
        System.out.println(inPlaceReversedString);
    }
}
