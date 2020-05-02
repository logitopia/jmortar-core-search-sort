package com.logitopia.jmortar.core.input;

import java.util.List;

/**
 * The <code>InputValidator</code> chain allows searches and sorts to verify that input given matches required
 * criteria. Implementations of this interface then take appropriate action depending on the level of severity of the
 * violation.
 *
 * @param <T> The type of element that the search or sort will operate upon
 */
public interface InputValidator<T> {

    /**
     * Validates that the list of elements presented are suitable to be sorted or searched upon.
     *
     * @param elements The elements that we wish to sort or search.
     * @return A flag indicating whether the element has passed validation or not. A 'true' value represents a
     * successful validation.
     */
    boolean validateElements(List<T> elements);
}
