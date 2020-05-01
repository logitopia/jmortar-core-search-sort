package com.logitopia.jmortar.core.request;

import java.util.List;

/**
 * A request object that provides an interface between the consumer and the search. The user enters their search
 * criteria here, the input is validated at point of entry and if the input is invalid, feedback will be provided to
 * the user.
 *
 * @param <T> The type of element that we we want to search for.
 */
public class SearchRequest<T> {

    private List<T> elements;

    private T valueToFind;

    public SearchRequest(List<T> elements, T valueToFind) {
        if (elements == null) {
            throw new IllegalArgumentException("Unable to search without a valid list of elements");
        }

        if (valueToFind == null) {
            throw new IllegalArgumentException("Unable to search without a valid value to search for");
        }

        this.elements = elements;
        this.valueToFind = valueToFind;
    }

    public List<T> getElements() {
        return elements;
    }

    public T getValueToFind() {
        return valueToFind;
    }
}
