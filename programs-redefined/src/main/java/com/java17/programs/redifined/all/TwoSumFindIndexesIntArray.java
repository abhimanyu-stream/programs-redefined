package com.java17.programs.redifined.all;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class TwoSumFindIndexesIntArray {
    public static void main(String[] args) {

        int[] nums = {9, 9, 7, 8, 3, 9, 0};
        int target = 16;



        List<int[]> resultStream =  twoSumUsingStream(nums, target);

        resultStream.forEach(ints -> System.out.println(ints[0] +"-"+ ints[1]));



        // String reverse
        String  inputString = "JavaLearningCenter";

        // here input is String type
        String reversed = IntStream.range(0, inputString.length())
                .mapToObj(i -> inputString.charAt(inputString.length() - 1 - i))
                .map(String::valueOf)
                .collect(Collectors.joining());




        List<String> l1 = new ArrayList<>();
        l1.add ("apple");
        l1.add("crypt");
        //Arrays.asList("jj","");
        // count vowels in each object


        StringBuffer buffer = new StringBuffer();
        l1.forEach(s -> {
            long vowelCount = s.chars()
                    .mapToObj(c -> (char) c)
                    .filter(c -> "aeiouAEIOU".contains(String.valueOf(c)))//

                    .count();
            System.out.println("Vowels in '" + s + "': " + vowelCount);


            for(int i = 0; i < vowelCount; i++){
                buffer.append(s);//s ->
            }
        });
        System.out.println("buffer " +buffer);
        System.out.println(new String(buffer));

        String buffert = l1.stream()
                .flatMap(s -> s.chars()
                        .mapToObj(c -> (char) c))
                .filter(c -> "aeiouAEIOU".indexOf(c) != -1)
                .map(String::valueOf)
                .collect(Collectors.joining());

        System.out.println(buffert);




        StringBuilder buffers = new StringBuilder();

        l1.forEach(s -> {

            // extract vowels from the string
            String vowels = s.chars()
                    .mapToObj(c -> (char) c)
                    .filter(c -> "aeiouAEIOU".indexOf(c) != -1)
                    .map(String::valueOf)
                    .collect(Collectors.joining());

            System.out.println("Vowels in '" + s + "': " + vowels.length());

            // append vowels to buffer
            buffers.append(vowels);
        });



        //java program input is  "aaaaaaammdddddkkkkx" output is "xmmkkkkdddddaaaaaaa"
        // Given text: "aaaaaaammdddddkkkkx"



        //Both work — choose based on whether you want:
        //
        //Character keys → use str.chars()...
        //
        //String keys → use str.split("")...


        String str = "axaddaaakkaammdddkk";
        List<String> list = Arrays.stream(str.split("")).collect(Collectors.groupingBy(Function.identity(), LinkedHashMap::new, Collectors.counting())).entrySet()
                .stream().filter(f -> f.getValue() > 1L).map(Map.Entry::getKey).toList();


        Map<String, Long> charMap2 = Arrays.stream(str.split(""))
                .collect(Collectors.groupingBy(Function.identity(), LinkedHashMap::new, Collectors.counting()));


        String str2 = "hello world";

        Map<Character, Long> charMap3 = str2.chars()                       // Stream of int (Unicode values)
                .mapToObj(c -> (char) c)                                  // Convert int -> Character
                .collect(Collectors.groupingBy(                           // Group by character
                        Function.identity(),
                        LinkedHashMap::new,                               // Preserve insertion order
                        Collectors.counting()                             // Count occurrences
                ));

        System.out.println(charMap3);


        Map<Character, Long> charMap = str.chars()
                .mapToObj(c->(char)c)
                .collect(Collectors.groupingBy(
                        Function.identity(),
                        LinkedHashMap::new,
                        Collectors.counting()));
        StringBuffer b = new StringBuffer();// Thread Safe
        StringBuilder builder = new StringBuilder();


        charMap.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())// sorting
                //.sorted((e1, e2) -> e1.getKey().compareTo(e2.getKey()))
                //.sorted(Map.Entry.<Character, Long>comparingByKey().reversed())
                //.sorted(Map.Entry.comparingByKey(
                //        Comparator.comparingInt(Character::toLowerCase)
                //))
                .forEach(

                        characterLongEntry -> {
                            Character character = characterLongEntry.getKey();
                            Long value = characterLongEntry.getValue();
                            for(int i = 0; i < value.intValue(); i ++){
                                builder.append(character);
                            }

                        }
                );
        System.out.println(new String(builder));



        String str3 = "the java, developer jobs";


        //Approach-1

        // 1. String to String[]
        String[] splitted = str3.split(" ");
        // 2. StringBuffer
        StringBuffer buffer3 = new StringBuffer(splitted.length);
        for(int last = splitted.length - 1; last >=0; last--){
            buffer3.append(splitted[last]).append(" ");

        }
        System.out.println("buffer-----");
        System.out.println(new String(buffer3));




        //Approach-2
        String reversedd = IntStream.range(0, str3.length())
                //.mapToObj(i -> String.valueOf(str3.charAt(str3.length() - 1 - i))) ok
                .mapToObj(i -> (str3.charAt(str3.length() - 1 - i)))
                .map(String::valueOf)
                .collect(Collectors.joining());  // joining characters together

        System.out.println("Mirror image: " + reversedd);





        //Approach-3
        String reversedWords = Arrays.stream(str3.split(" "))  // split by spaces
                .collect(Collectors.collectingAndThen(Collectors.toList(), list1 -> {
                    Collections.reverse(list1);               // reverse the list
                    return list1.stream();
                }))
                .collect(Collectors.joining(" "));           // join back with spaces

        System.out.println(reversedWords);




    }
    // ✅ Stream-based implementation to return index pairs
    private static List<int[]> twoSumUsingStream(int[] A, int target) {// here input is array type

        List<int[]> result = new ArrayList<>();

        IntStream.range(0, A.length)// consider two for loop [ loop outer]
                .forEach(i -> IntStream.range(i + 1, A.length)// [ loop inner]
                        .filter(j -> A[i] + A[j] == target)
                        .forEach(j -> result.add(new int[]{i, j})));// 3

        return result;

        //List<int[]> resultStream =  twoSumUsingStream(nums, target);
        //resultStream.forEach(ints -> System.out.println(ints[0] +"-"+ ints[1]));


    }




}
