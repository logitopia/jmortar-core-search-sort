package com.logitopia.jmortar.core.search;

import com.logitopia.jmortar.core.comparator.Comparator;

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

    /**
     * Default Constructor. Creates an instance of the {@link Search} with the required equality comparator. The
     * comparator will verify whether an element in a list is equal to the object being searched for. Using a
     * separate implementation means that we don't need to rely on the objects {@link Object#equals(Object)}
     * implementation.
     *
     * @param equalityComparator The comparator that this search instance will use.
     */
    public BinarySearch(Comparator<T> equalityComparator) {
        this.equalityComparator = equalityComparator;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int findMatch(List<T> elements, T valueToFind) {
        return 0;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Integer> findMatches(List<T> elements, T valueToFind) {
        return null;
    }
}
