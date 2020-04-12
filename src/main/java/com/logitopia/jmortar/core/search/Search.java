package com.logitopia.jmortar.core.search;

import java.util.List;

/**
 * A 'search' that allows us to identify the location of an element in a given collection.
 *
 * @param <T> The type of element that the search will attempt to locate.
 */
public interface Search<T> {

    /**
     * Find the given element from the list of elements.
     *
     * @param elements    A list of the elements that we wish to search.
     * @param valueToFind A specific value that we want to find in the given list.
     * @return The position in the list of elements where the value is found. If there are multiple matches in the
     * list of elements, the first match is returned.
     */
    int findMatch(List<T> elements, T valueToFind);

    /**
     * Find all of the elements that match a specific value from the given list of elements. This is particularly
     * useful when you have a set of complex elements (i.e. objects) that have common, searchable elements, and
     * differing elements.
     *
     * @param elements    A list of the elements that we wish to search through.
     * @param valueToFind A specific value that we want to find in the given list.
     * @return A {@link List} of the positions matching the given element.
     * @throws ParallelizedSearchStoppedException when a parallelized search was stopped prematurely.
     */
    List<Integer> findMatches(List<T> elements, T valueToFind)
            throws ParallelizedSearchStoppedException;
}
