package com.logitopia.jmortar.core.input;

/**
 * An unsorted input exception is thrown when the presented input to a
 * {@link com.logitopia.jmortar.core.search.Search} is not sorted as expected.
 */
public class UnsortedInputException extends Exception {

    public UnsortedInputException(String message) {
        super(message);
    }
}
