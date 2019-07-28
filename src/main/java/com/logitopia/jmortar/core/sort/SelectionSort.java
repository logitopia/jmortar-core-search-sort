package com.logitopia.jmortar.core.sort;

import com.logitopia.jmortar.core.comparator.Comparator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Sorts the given array of items using a selection sort algorithm.
 *
 * @param <T> The type of element being sorted.
 */
public class SelectionSort<T> implements Sort<T> {

    /**
     * {@inheritDoc}
     */
    @Override
    public synchronized void sort(T[] itemsToBeSorted, Comparator<T> comparator) {
        for(int index = 0; index < itemsToBeSorted.length; index++) {
            int smallestLocation = index;
            // find the smallest
            T smallest = itemsToBeSorted[index];
            for (int i = smallestLocation; i < itemsToBeSorted.length; i++) {
                T element = itemsToBeSorted[i];
                if (comparator.compare(smallest, element)) {
                    smallestLocation = i;
                    smallest = itemsToBeSorted[smallestLocation];
                }
            }
            // Swap Values around...
            T temp = itemsToBeSorted[index];
            itemsToBeSorted[index] = smallest;
            itemsToBeSorted[smallestLocation] = temp;
        }
    }
}
