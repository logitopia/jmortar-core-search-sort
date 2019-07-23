package com.logitopia.jmortar.core.sort;

public class BubbleSort {

    public void sort(int[] input) {
        int length = input.length;
        int i, j, temp;
        boolean swapped;
        for (i = 0; i < length - 1; i++) {
            swapped = false;

            for (j = 0; j < length - i - 1; j++) {
                int first = input[j];
                int second = input[j + 1];
                if (first > second) {
                    // First should be lower than the second... SWAP
                    temp = first;
                    input[j] = second;
                    input[j + 1] = temp;
                    swapped = true;
                }
            }

            if (!swapped) break;
        }
    }
}
