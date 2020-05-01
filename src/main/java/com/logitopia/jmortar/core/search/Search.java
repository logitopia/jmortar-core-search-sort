package com.logitopia.jmortar.core.search;

import com.logitopia.jmortar.core.request.SearchRequest;

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
     * @param searchRequest A request containing all of the elements needed to perform a search.
     * @return The position in the list of elements where the value is found. If there are multiple matches in the
     * list of elements, the first match is returned.
     */
    int findMatch(SearchRequest<T> searchRequest);

    /**
     * Find all of the elements that match a specific value from the given list of elements. This is particularly
     * useful when you have a set of complex elements (i.e. objects) that have common, searchable elements, and
     * differing elements.
     *
     * @param searchRequest A request containing all of the elements needed to perform a search
     * @return A {@link List} of the positions matching the given element.
     * @throws ParallelizedSearchStoppedException when a parallelized search was stopped prematurely.
     */
    List<Integer> findMatches(SearchRequest<T> searchRequest)
            throws ParallelizedSearchStoppedException;
}
