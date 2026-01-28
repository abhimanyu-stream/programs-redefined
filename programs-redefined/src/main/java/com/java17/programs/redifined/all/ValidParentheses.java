package com.java17.programs.redifined.all;

public class ValidParentheses {

    public static void main(String[] args) {


        String str = "(){[}]";
        boolean result = isValid(str);
        System.out.println(str + " is "+ result);
    }

    private static boolean isValid(String str) {

        java.util.Stack<Character> stack = new java.util.Stack<>();
        for(char c: str.toCharArray()){

            if(c == '(' || c == '{' || c == '['){
                stack.push(c);

            }else if(c == ')' || c == '}' || c == ']') {
                if (stack.isEmpty()) {
                    return false;
                }
                // Popped top value
                char top = stack.pop();
                if (c == ')' && top != '(') {
                    return false;
                }
                if (c == '}' && top != '{') {
                    return false;
                }
                if (c == ']' && top != '[') {
                    return false;
                }
            }
        }
        return stack.isEmpty();
    }
}
