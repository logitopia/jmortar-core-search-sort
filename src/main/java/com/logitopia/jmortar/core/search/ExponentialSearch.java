package com.logitopia.jmortar.core.search;

import com.logitopia.jmortar.core.comparator.Comparator;
import com.logitopia.jmortar.core.request.SearchRequest;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * The 'exponential' search starts with a sub array of size 1, compares it's last (only) element with the comparator.
 * If that element does not match then we double the size of the data set to 2. We keep this expansion going until
 * the last element is not greater than the value we are comparing to. Once an element is found that is greater than
 * the target element, we then know that our value / values resides between <i>found</i> and <i>found/2</i>.
 *
 * This approach allows us to rapidly narrow down the target data set. We then perform a quicker binary search on the
 * reduced dataset.
 *
 * @param <T> The type of element that the search will attempt to locate.
 */
public class ExponentialSearch<T> implements Search<T> {

    private Comparator<T> equalityComparator;

    private Comparator<T> lessThanComparator;

    private long searchTimeout = 5;

    private TimeUnit searchTimeoutUnit = TimeUnit.MINUTES;

    public ExponentialSearch(Comparator<T> equalityComparator,
                        Comparator<T> lessThanComparator) {
        this(equalityComparator, lessThanComparator, null, null);
    }

    public ExponentialSearch(Comparator<T> equalityComparator,
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
    public int findMatch(SearchRequest<T> request) {
        List<T> elements = request.getElements();
        T valueToFind = request.getValueToFind();

        // TODO - What if the elements list has only one element? Should we support it??
        // Answer: There are more efficient ways to match an element, so shouldn't run it
        // thought ... that said, the consumer might not want to faff around pre-processing
        // the list.

        if (elements == null) { // <-- Should perhaps provide this functionality from an
            // abstract as this kind of checking is common to ALL searches!
            // Throw a controlled exception .. we don't accept nulls here!
        }

        int searchIndex = 1;


        for (int i=1; lessThanComparator.compare(elements.get(i-1),valueToFind); i=i*2) {
            if (i >= elements.size()) {

            }
            searchIndex = i;
        }



        return 0;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Integer> findMatches(List<T> elements, T valueToFind)
            throws ParallelizedSearchStoppedException {

        return null;
    }
}
