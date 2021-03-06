package com.logitopia.jmortar.core.sort.parallel;

import com.google.common.collect.Lists;
import com.logitopia.jmortar.core.comparator.Comparator;
import com.logitopia.jmortar.core.sort.Sort;

import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * An implementation of <tt>Sort</tt> that uses a merge sort technique to sort the required input. This
 * implementation implements the sort to utilise parallelisation techniques.
 *
 * @param <T> The type of item being sorted.
 */
public class ParallelMergeSort<T> implements Sort<T> {

    private ExecutorService threadPool;

    /**
     * Default Constructor. This creates a <tt>ParallelMergeSort</tt> that has a default number of threads.
     */
    public ParallelMergeSort() {
        threadPool = Executors.newFixedThreadPool(5);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void sort(T[] itemsToBeSorted, Comparator<T> comparator) {
        // [1] Break the incoming array down into their smallest components (i.e. lists of size 1)
        List<T> input = new ArrayList<>(Arrays.asList(itemsToBeSorted));
        List<List<T>> partitioned = Lists.partition(input, 1);

        // [2] Build them back up by sorting and merging them
        List<List<T>> result = reducer(partitioned, comparator);

        // [3] Load the sorted result back into a resulting array
        List<T> resultItem = result.get(0);
        loadSortedListIntoInputArray(itemsToBeSorted, resultItem);
    }

    private void loadSortedListIntoInputArray(T[] input, List<T> sorted) {
        for (int i = 0; i < sorted.size(); i++) {
            input[i] = sorted.get(i);
        }
    }

    /**
     * Reduces a given list of lists by merging them off. If there is an odd remainder, it will be bolted on to the
     * end of the result and reduced in the next step.
     *
     * @param lists      The list of lists that we wish to reduce by merging.
     * @param comparator A strategy that we use to compare two elements.
     * @return A reduced list of lists where small lists have been merged into a smaller set of larger ones.
     */
    private List<List<T>> reducer(List<List<T>> lists, Comparator<T> comparator) {
        List<List<T>> result = Collections.synchronizedList(new ArrayList<>());
        Iterator<List<T>> input = lists.iterator();

        CountDownLatch latch = initializeLatch(lists.size());
        while (input.hasNext()) {
            List<T> first = input.next();

            // Odd Element - Add to result and skip
            if (!input.hasNext()) {
                result.add(first);
                continue;
            }

            List<T> second = input.next();

            // Submit the merge to be run in it's own thread
            threadPool.submit(() -> {
                result.add(mergeSortedLists(new ArrayList<>(first), new ArrayList<>(second), comparator));
                latch.countDown();
            });
        }

        // Wait for completion of all of the merge operations
        try {
            latch.await(3, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            // Unknown state, unable to determine that the sort completed successfully..
            throw new IllegalStateException("Unable to determine whether the sort completed successfully.");
        }

        // Recurse until we get to a list of size 1 (i.e. the final sorted list)
        if (lists.size() > 1) {
            return reducer(result, comparator);
        }

        return result;
    }

    /**
     * Initialize a latch that allows us to track when all of the sorted lists have been merged.
     *
     * @param inputListSize The size of the list that we want to provide a latch for.
     */
    private CountDownLatch initializeLatch(int inputListSize) {
        if (inputListSize % 2 == 0) {
            return new CountDownLatch(inputListSize / 2);
        } else {
            return new CountDownLatch((inputListSize - 1) / 2);
        }
    }

    /**
     * Merge two "sorted" lists by continuously comparing the first element of each list until there are no elements
     * left. This merges the elements of the two lists, whilst sorting them at the same time.
     *
     * @param first  The first of two lists that we wish to merge.
     * @param second The second of two lists that we wish to merge.
     * @return The "merged" and sorted lists.
     */
    private List<T> mergeSortedLists(List<T> first, List<T> second, Comparator<T> comparator) {
        List<T> result = new ArrayList<>();
        int expectedSize = first.size() + second.size();

        Iterator<T> firstListIterator = first.iterator();
        T firstElement = null;
        T secondElement;
        boolean firstListCycle = true;
        while (result.size() != expectedSize) {
            if (firstListIterator.hasNext() && firstListCycle) {
                firstElement = firstListIterator.next();
                firstListCycle = false;
            }

            /* If the first "sorted" list still has elements, but the second shorter list has run out, then we just
               continue adding the remaining elements from the first list to the result.*/
            if (second.size() == 0) {
                result.add(firstElement);
                firstListCycle = true;
                continue;
            } else {
                secondElement = second.get(0);
            }

            if (comparator.compare(firstElement, secondElement)) {
                result.add(secondElement);
                second.remove(0);
            } else {
                result.add(firstElement);
                firstListCycle = true;

                if (!firstListIterator.hasNext()) break;
                firstListIterator.remove();
            }
        }

        /* If the second "sorted" list has elements left, add them to the result (they will be higher and in order than
           the final result element. */
        if (second.size() > 0) {
            result.addAll(second);
        }

        return result;
    }
}
