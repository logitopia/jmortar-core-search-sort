package com.logitopia.jmortar.core.sort.parallel;

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
        // TODO - Find the midpoint for the given array
        int arrayLength = itemsToBeSorted.length;
        int half = arrayLength / 2;

        // TODO - Split the array into two halves

        // TODO - Call this method recursively on the two split lists
        // TODO - Merge the sorted tw o halves
    }

    /**
     * Merge two "sorted" lists by continuously comparing the first element of each list until there are no elements
     * left. This merges the elements of the two lists, whilst sorting them at the same time.
     * @param first The first of two lists that we wish to merge.
     * @param second The second of two lists that we wish to merge.
     * @return The "merged" and sorted lists.
     */
    private List<T> mergeSortedLists(T[] first, T[] second, Comparator<T> comparator) {
        List<T> result = new ArrayList<>();

        List<T> firstList = new ArrayList<>(Arrays.asList(first));
        List<T> secondList = new ArrayList<>(Arrays.asList(second));

        Iterator<T> firstListIterator = firstList.iterator();
        while(firstListIterator.hasNext()) {
            T firstElement = firstListIterator.next();
            T secondElement = secondList.get(0);

            /* If the first "sorted" list still has elements, but the second shorter list has run out, then we just
               continue adding the remaining elements from the first list to the result.*/
            if (secondElement == null) {
                result.add(firstElement);
                continue;
            }

            if (comparator.compare(firstElement, secondElement)) {
                result.add(secondElement);
                secondList.remove(0);
            } else {
                result.add(firstElement);
                firstListIterator.remove();
            }
        }

        /* If the second "sorted" list has elements left, add them to the result (they will be higher and in order than
           the final result element. */
        if (secondList.size() > 0) {
            result.addAll(secondList);
        }

        return result;
    }
}
