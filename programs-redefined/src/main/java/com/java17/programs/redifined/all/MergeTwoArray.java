package com.java17.programs.redifined.all;

import java.util.Arrays;

public class MergeTwoArray {

    public static int[] mergeJustCombinesBothArraysIntoOne(int[] arr1, int[] arr2) {

        //1. Simple Merge (Unsorted Arrays)
        //
        //This just combines both arrays into one:
        int[] result = new int[arr1.length + arr2.length];

        for (int i = 0; i < arr1.length; i++) {
            result[i] = arr1[i];
        }

        for (int i = 0; i < arr2.length; i++) {
            result[arr1.length + i] = arr2[i];
        }

        return result;
    }

    public static int[] mergeAndSort(int[] arr1, int[] arr2) {
        //Merge + Sort (Common Interview Follow-up)
        int[] result = new int[arr1.length + arr2.length];

        System.arraycopy(arr1, 0, result, 0, arr1.length);
        System.arraycopy(arr2, 0, result, arr1.length, arr2.length);

        Arrays.sort(result);
        return result;
    }

    public static int[] mergeSortedUsingTwoPointer(int[] arr1, int[] arr2) {
        //Optimal Merge (Two Sorted Arrays → O(n))
        //
        //If arrays are already sorted, use two-pointer technique:
        int i = 0, j = 0, k = 0;
        int[] result = new int[arr1.length + arr2.length];

        while (i < arr1.length && j < arr2.length) {
            if (arr1[i] <= arr2[j]) {
                result[k++] = arr1[i++];
            } else {
                result[k++] = arr2[j++];
            }
        }

        while (i < arr1.length) {
            result[k++] = arr1[i++];
        }

        while (j < arr2.length) {
            result[k++] = arr2[j++];
        }

        return result;
    }


    public static void main(String[] args) {
        int[] arr1 = {1, 3, 5};
        int[] arr2 = {2, 4, 6};

        int[] arr3 = {5, 1, 3};
        int[] arr4 = {2, 6, 4};

        int[] simpleMerged = mergeJustCombinesBothArraysIntoOne(arr1, arr2);

        System.out.println("Merged Array: " + Arrays.toString(simpleMerged));

        int[] mergedAndSorted = mergeAndSort(arr3, arr4);

        System.out.println("Merged & Sorted: " + Arrays.toString(mergedAndSorted));

        int[] mergedOptimum = mergeSortedUsingTwoPointer(arr1, arr2);

        System.out.println("Merged Sorted Arrays: " + Arrays.toString(mergedOptimum));
    }
}
