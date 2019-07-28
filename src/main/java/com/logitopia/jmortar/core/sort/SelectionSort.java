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
    public void sort(T[] itemsToBeSorted, Comparator<T> comparator) {
        List<T> toBeSorted = new ArrayList<>(Arrays.asList(itemsToBeSorted));
        List<T> sorted = new ArrayList<>(toBeSorted.size());

        boolean first = true;
        T smallest = null;

        // find the smallest
        for (int i=0; i<toBeSorted.size();i++) {
            T element = toBeSorted.get(i);
            if (first) {
                smallest = element;
                first = false;
            } else {
                if (comparator.compare(smallest, element)) {
                    smallest = element;
                }
            }
        }

        sorted.add(smallest);

        System.out.println("DEBUGGING == ");
    }
}
