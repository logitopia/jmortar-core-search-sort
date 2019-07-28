package com.logitopia.jmortar.core.sort;

import com.logitopia.jmortar.core.comparator.Comparator;

/**
 * An implementation of <tt>Sort</tt> that uses a bubble sorting technique to sort the required input.
 *
 * @param <T> The type of item being sorted
 */
public class BubbleSort<T> implements Sort<T> {

    /**
     * {@inheritDoc}
     */
    @Override
    public synchronized void sort(T[] input, Comparator<T> comparator) {
        int length = input.length;
        int i, j;
        T temp;
        boolean swapped;
        for (i = 0; i < length - 1; i++) {
            swapped = false;

            for (j = 0; j < length - i - 1; j++) {
                T first = input[j];
                T second = input[j + 1];
                if (comparator.compare(first, second)) {
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
