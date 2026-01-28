package com.java17.programs.redifined.all;

public class FindLongestCommonPrefixStringArray {
    public static void main(String[] args) {

        String[] s = {"flower", "flow", "flight"};
        String result = longestCommonPrefix(s);
        System.out.println(result);

    }

    private static String longestCommonPrefix(String[] s) {


        String prefix = s[0];

        for(int i = 1; i < s.length; i++){
            while (s[i].indexOf(prefix) != 0){
                prefix = prefix.substring(0, prefix.length() - 1);
            }
            if(prefix.isEmpty())
                return "";
        }
        return prefix;



    }
}
