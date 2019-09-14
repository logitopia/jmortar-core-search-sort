package com.logitopia.jmortar.core.sort.parallel;

import com.google.common.collect.Lists;
import com.logitopia.jmortar.core.comparator.Comparator;
import com.logitopia.jmortar.core.sort.Sort;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * An implementation of <tt>Sort</tt> that uses a merge sort technique to sort the required input. This
 * implementation implements the sort to utilise parallelisation techniques.
 *
 * @param <T> The type of item being sorted.
 */
public class ParallelMergeSort<T> implements Sort<T> {

    /**
     * {@inheritDoc}
     */
    @Override
    public void sort(T[] itemsToBeSorted, Comparator<T> comparator) {
        /* TODO - NEW IDEA - Take each element of the original array as a "Single Element List". Then simply build
        the list up from there WITHOUT needing to expensively split it first.
         */

        // TODO - Break the array into two element lists and feed to the reducer...
        List<T> input = new ArrayList<>(Arrays.asList(itemsToBeSorted));
        List<List<T>> partitioned = Lists.partition(input, 2);
        List<List<T>> result = reducer(partitioned, comparator);
        System.out.println(2);
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
        List<List<T>> result = new ArrayList<>();
        Iterator<List<T>> input = lists.iterator();

        while (input.hasNext()) {
            List<T> first = input.next();

            // Odd Element - Add to result and skip
            if (!input.hasNext()) {
                result.add(first);
                continue;
            }

            List<T> second = input.next();
            result.add(mergeSortedLists(first, second, comparator));
        }

        return result;
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
