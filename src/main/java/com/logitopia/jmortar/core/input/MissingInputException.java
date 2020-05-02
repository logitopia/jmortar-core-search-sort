package com.logitopia.jmortar.core.input;

/**
 * A missing input exception is a type of {@link Exception} that is thrown when a specific input is found to be
 * 'null'. In this case a null state represents 'missing' input.
 */
public class MissingInputException extends Exception {

    public MissingInputException(String message) {
        super(message);
    }
}
