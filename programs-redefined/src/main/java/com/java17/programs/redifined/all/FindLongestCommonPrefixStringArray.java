package com.java17.programs.redifined.all;

public class FindLongestCommonPrefixStringArray {
    public static void main(String[] args) {

        String[] s = {"flower", "flow", "flight"};
        String result = longestCommonPrefix(s);
        System.out.println(result);

    }

    private static String longestCommonPrefix(String[] S) {


        String prefix = S[0];

        for (int i = 1; i < S.length ; i++) {// as S is String[]
            //while (S[i].indexOf(prefix) != 0) {
            while (!S[i].startsWith(prefix)) {
                prefix = prefix.substring(0, prefix.length() - 1); // as prefix is String
                if (prefix.isEmpty()) return "";
            }

        }
        return prefix;



    }
}
