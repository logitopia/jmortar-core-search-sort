package com.logitopia.jmortar.core.search;

import com.logitopia.jmortar.core.comparator.Comparator;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

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

    private long searchTimeout = 5;

    private TimeUnit searchTimeoutUnit = TimeUnit.MINUTES;

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
        this(equalityComparator, lessThanComparator, null, null);
    }

    /**
     * Custom Timeout Constructor. Creates an instance of the {@link Search} with the required comparators. This
     * constructor also allows for a larger timeout specification for searching larger datasets.
     *
     * @param equalityComparator A comparator that will be used to determine if the search value is equal to a
     *                           specified element in the list.
     * @param lessThanComparator A comparator that will be used to determine if the search value is less than a
     *                           specified element in the list.
     * @param searchTimeout      The amount of time taken before the {@link BinarySearch#findMatches(List, Object)}
     *                          method
     *                           times out. This can be used to calibrate the timeout when you have a large number of
     *                           matches in a particularly large and/or complex dataset.
     * @param timeoutUnit        The time unit to apply to the provided <tt>searchTimeout</tt>.
     */
    public BinarySearch(Comparator<T> equalityComparator,
                        Comparator<T> lessThanComparator,
                        Long searchTimeout,
                        TimeUnit timeoutUnit) {
        this.equalityComparator = equalityComparator;
        this.lessThanComparator = lessThanComparator;

        if (searchTimeout != null) {
            this.searchTimeout = searchTimeout;
        }

        if (timeoutUnit != null) {
            this.searchTimeoutUnit = timeoutUnit;
        }
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
     * @param startIndex The start of the range to search.
     * @param endIndex   The end of the full range to search.
     * @param elements   The elements that we are searching in.
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
            return search(startIndex, midPointIndex - 1, elements, valueToFind);
        } else {
            // Search the right
            return search(midPointIndex + 1, endIndex + 1, elements, valueToFind);
        }
    }

    /**
     * {@inheritDoc}
     *
     * This method is <tt>synchronized</tt> for performance reasons. In order to search the lower and upper bounds as
     * quickly as possible, they are run in separate threads of execution. To prevent the number of created threads
     * getting out of control this method is therefore synchronized.
     */
    @Override
    public synchronized List<Integer> findMatches(List<T> elements, T valueToFind) {
        List<Integer> result = new ArrayList<>();

        if (elements.size() == 0) {
            // Route 1 - We don't do anything if we're passed an empty list (save CPU).
            return result;
        }

        ExecutorService searchExecutor = Executors.newFixedThreadPool(2);
        Future<Integer> lowerBound = searchExecutor.submit(() -> findLeftmostMatch(elements, valueToFind));
        Future<Integer> upperBound = searchExecutor.submit(() -> findRightmostMatch(elements, valueToFind));
        searchExecutor.shutdown();

        try {
            searchExecutor.awaitTermination(this.searchTimeout, this.searchTimeoutUnit);
        } catch (InterruptedException e) {
            searchExecutor.shutdownNow();

        }

        // TODO - Analyse and return the result.
        // IF -1 then return empty else get ALL elements between the left and the right index.

        return result;
    }

    private int findLeftmostMatch(List<T> elements, T valueToFind) {
        int startIndex = 0;
        int endIndex = elements.size();

        while (startIndex < endIndex) {
            int midPointIndex = startIndex + ((endIndex - startIndex) / 2);

            T middleElement = elements.get(midPointIndex);
            if (lessThanComparator.compare(middleElement, valueToFind)) {
                startIndex = midPointIndex + 1;
            } else {
                endIndex = midPointIndex;
            }
        }

        // Value Existence Test
        int result = startIndex;
        if (!equalityComparator.compare(elements.get(result), valueToFind)) {
            result = -1;
        }

        return result;
    }

    private int findRightmostMatch(List<T> elements, T valueToFind) {
        int startIndex = 0;
        int endIndex = elements.size();

        while (startIndex < endIndex) {
            int midPointIndex = startIndex + ((endIndex - startIndex) / 2);

            T middleElement = elements.get(midPointIndex);
            if (lessThanComparator.compare(valueToFind, middleElement)) {
                endIndex = midPointIndex;
            } else {
                startIndex = midPointIndex + 1;
            }
        }

        // Value Existence Test
        int result = endIndex - 1;
        if (!equalityComparator.compare(elements.get(result), valueToFind)) {
            result = -1;
        }

        return result;
    }
}
