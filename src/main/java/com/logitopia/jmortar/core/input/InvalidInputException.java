package com.logitopia.jmortar.core.input;

/**
 * A type of {@link Exception} indicating that an input has been given to a search or sort, such that a result
 * cannot be determined from it (i.e. null input).
 */
public class InvalidInputException extends Exception {

    public InvalidInputException(String message) {
        super(message);
    }

    public InvalidInputException(String message, Throwable cause) {
        super(message, cause);
    }
}
