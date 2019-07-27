package com.logitopia.jmortar.core.sort;

import com.logitopia.jmortar.core.comparator.Comparator;

import java.lang.reflect.Array;

/**
 * Sorts the given array of items using a selection sort algorithm.
 *
 * @param <T> The type of element being sorted.
 */
public class SelectionSort<T> implements Sort<T> {

    private Class<T> elementType;

    /**
     * Default Constructor. Creates an instance of the sort, where the consumer defines the "type" of the element
     * that will be sorted.
     *
     * @param elementType The type of the element being sorted.
     */
    public SelectionSort(final Class<T> elementType) {
        if (elementType == null) {
            throw new IllegalArgumentException("Unable to create the SelectionSort with missing element type.");
        }
        this.elementType = elementType;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void sort(T[] itemsToBeSorted, Comparator<T> comparator) {
        // TODO - Look into using a stack here rather than a fixed array ... it could save an extra looping cycle.
        @SuppressWarnings("unchecked")
        T[] sorted = (T[]) Array.newInstance(elementType, itemsToBeSorted.length);

        boolean first = true;
        T smallest = null;

        // find the smallest
        for (int i=0; i<itemsToBeSorted.length;i++) {
            T element = itemsToBeSorted[i];
            if (first) {
                smallest = element;
                first = false;
            } else {
                if (comparator.compare(smallest, element)) {
                    smallest = element;
                }
            }
        }
        // TODO - The smallest element has to be removed from the original array....
        sorted[0] = smallest; // TODO <-- Need to calculate the position in the sorted array length - current index
    }
}
