package com.math.algorithms;

import java.util.Arrays;

/**
 * Сортировка массива подсчетом (counting sort)
 */
public class CountingSort {

    public static void main(String[] args) {
        final int[] sorted = sort(new int[]{1, 4, 1, 2, 7, 5, 2});
        System.out.println(Arrays.toString(sorted));
    }

    public static int[] sort(int[] array) {
        System.out.println(Arrays.toString(array));
        int[] allNumbersArray = new int[10];
        for (int i : array) {
            allNumbersArray[i]++;
        }

        for (int i = 0; i < allNumbersArray.length - 1; i++) {
            allNumbersArray[i+1] = allNumbersArray[i] + allNumbersArray[i+1];
        }

        int[] resultArray = new int[array.length];
        for (int i : array) {
            int positionInResult = allNumbersArray[i];
            allNumbersArray[i]--;
            resultArray[positionInResult - 1] = i;
        }

        return resultArray;
    }
}
