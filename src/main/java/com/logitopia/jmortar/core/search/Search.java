package com.logitopia.jmortar.core.search;

import java.util.List;

/**
 * A 'search' that allows us to identify the location of an element in a given collection.
 *
 * @param <T> The type of element that the search will attempt to locate.
 */
public interface Search<T> {

    int findMatch(List<T> elements, String matcher);
}
