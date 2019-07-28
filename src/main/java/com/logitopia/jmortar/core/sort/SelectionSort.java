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
        for(int index = 0; index < toBeSorted.size(); index++) {
            int smallestLocation = index;
            // find the smallest
            T smallest = toBeSorted.get(index);
            for (int i = smallestLocation; i < toBeSorted.size(); i++) {
                T element = toBeSorted.get(i);
                if (comparator.compare(smallest, element)) {
                    smallestLocation = i;
                    smallest = toBeSorted.get(smallestLocation);
                }
            }
            toBeSorted.remove(smallestLocation);
            toBeSorted.add(index, smallest);
        }

        // TODO - The original passed in array needs to be replaced with the content from the List.
        itemsToBeSorted = (T[]) toBeSorted.toArray();
    }
}
