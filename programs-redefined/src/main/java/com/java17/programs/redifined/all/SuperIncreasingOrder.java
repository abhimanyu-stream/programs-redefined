package com.java17.programs.redifined.all;

public class SuperIncreasingOrder {

    public static boolean isSuperIncreasing(int[] arr) {
        int sum = 0;
        for (int num : arr) {
            if (num <= sum) {
                return false;
            }
            sum += num;
        }
        return true;
    }

    public static void main(String[] args) {
        int[] arr1 = {1, 2, 4, 8, 16};
        int[] arr2 = {1, 2, 3, 4, 5};

        System.out.println("Is arr1 isSuperIncreasing? " + isSuperIncreasing(arr1)); // Output: true
        System.out.println("Is arr2 isSuperIncreasing? " + isSuperIncreasing(arr2)); // Output: false
    }
}
