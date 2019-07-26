package com.logitopia.jmortar.core.sort;

import com.logitopia.jmortar.core.comparator.Comparator;

/**
 * Sort the given list, using the given comparator. The comparator will validate whether or not two components are in
 * the expected position, relative to each other.
 *
 * @param <T> The type of item that we wish to sort.
 */
public interface Sort<T> {

    /**
     * Sort the given items, using the given comparator to validate whether or not the elements are in the expected
     * position.
     *
     * @param itemsToBeSorted The items to be sorted.
     * @param comparator      The comparator that we wish to use to validate the location of the items.
     */
    void sort(T[] itemsToBeSorted, Comparator<T> comparator);
}
