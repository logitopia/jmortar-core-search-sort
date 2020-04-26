package com.logitopia.jmortar.core.input;

/**
 * A type of {@link InputValidator} that defines validation operations that are specific to a search.
 *
 * @param <T> The type of element that the search or sort will operate upon.
 */
public interface SearchInputValidator<T> extends InputValidator<T> {

    /**
     * Validates that the value we are searching for is suitable to be searched for.
     *
     * @param valueToFind The value that we are going to attempt to search for.
     * @return A flag indicating whether the element has passed validation or not. A 'true' value represents a
     * successful validation.
     * @throws InvalidInputException thrown when the input is invalid such that a search or sort cannot continue.
     *                               Under some validation conditions the search can return instantly if the
     *                               state of the input is such that a search is not required (i.e. no elements)
     */
    boolean validateValueToFind(T valueToFind) throws InvalidInputException;
}
