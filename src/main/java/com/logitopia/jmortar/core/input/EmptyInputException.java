package com.logitopia.jmortar.core.input;

/**
 * An empty input exception is thrown when the provided input is present, but the object or primitive does not
 * contain the expected data (i.e. is empty).
 */
public class EmptyInputException extends Exception {

    public EmptyInputException(String message) {
        super(message);
    }
}
