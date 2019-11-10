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
        int midPointIndex = startIndex + ((endIndex - startIndex) / 2);

        // Check if the midpoint matches the value to find
        if (equalityComparator.compare(elements.get(midPointIndex), valueToFind)) {
            // Match found .. return as the first match
            // TODO :: BUG FOUND :: As we recurse and return a sublist, we are finding the element, but we arent'
            //  returning the index from the original elements list where it resides, this is why we need a search
            //  method that takes the original list and the positions :-( Either that or change from recursion to
            //  iteration... (I think iteration is more efficient inside the JVM - should check)
            return midPointIndex;
        }

        if (lessThanComparator.compare(valueToFind, elements.get(midPointIndex))) {
            // Search the left
            return findMatch(elements.subList(startIndex, midPointIndex-1), valueToFind);
        } else {
            // Search the right
            return findMatch(elements.subList(midPointIndex+1, endIndex+1), valueToFind);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Integer> findMatches(List<T> elements, T valueToFind) {
        return null;
    }
}
