package com.logitopia.jmortar.core.search;

import com.logitopia.jmortar.core.comparator.Comparator;

import java.util.ArrayList;
import java.util.List;

/**
 * A 'binary' implementation of {@link Search} works by taking an <b>ordered</b> data set and identifying which
 * half of the data set contains the value. It will continue to break the data set down until the value is found.
 *
 * <p>The binary search is a good tool for searching through medium and larger data sets. This is because it is an
 * O(log n) efficiency search. It achieves this by eliminating half the results that don't match each time.</p>
 *
 * @param <T> The type of element that the search will attempt to locate.
 */
public class BinarySearch<T> implements Search<T> {

    private Comparator<T> equalityComparator;

    private Comparator<T> lessThanComparator;

    /**
     * Default Constructor. Creates an instance of the {@link Search} with the required equality comparator. The
     * comparator will verify whether an element in a list is equal to the object being searched for. Using a
     * separate implementation means that we don't need to rely on the objects {@link Object#equals(Object)}
     * implementation.
     *
     * @param equalityComparator A comparator that will be used to determine if the search value is equal to a
     *                           specified element in the list.
     * @param lessThanComparator A comparator that will be used to determine if the search value is less than a
     *                           specified element in the list.
     */
    public BinarySearch(Comparator<T> equalityComparator,
                        Comparator<T> lessThanComparator) {
        this.equalityComparator = equalityComparator;
        this.lessThanComparator = lessThanComparator;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int findMatch(List<T> elements, T valueToFind) {
        int startIndex = 0;
        int endIndex = elements.size() - 1;
        return search(startIndex, endIndex, elements, valueToFind);
    }

    /**
     * Binary search, search the given range on the full list.
     *
     * @param startIndex    The start of the range to search.
     * @param endIndex      The end of the full range to search.
     * @param elements The elements that we are searching in.
     *
     * @return The index of the element we are trying to find.
     */
    private int search(int startIndex, int endIndex, List<T> elements, T valueToFind) {
        int midPointIndex = startIndex + ((endIndex - startIndex) / 2);

        // Check if the midpoint matches the value to find
        T middleElement = elements.get(midPointIndex);
        if (equalityComparator.compare(middleElement, valueToFind)) {
            // We found the value, return it's initial index
            return midPointIndex;
        } else if (startIndex == endIndex) {
            // We've exhausted all options, signal that no entry could be found
            return -1;
        }

        if (lessThanComparator.compare(valueToFind, middleElement)) {
            // Search the left
            return search(startIndex, midPointIndex-1, elements, valueToFind);
        } else {
            // Search the right
            return search(midPointIndex + 1, endIndex + 1, elements, valueToFind);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Integer> findMatches(List<T> elements, T valueToFind) {
        List<Integer> result = new ArrayList<>();
        int index = findMatch(elements, valueToFind);



        return result;
    }
}
