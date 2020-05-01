package com.logitopia.jmortar.core.input;

import java.util.List;

/**
 * Implements the Chain of Responsibility behaviour to save duplication in subsequent implementations.
 *
 * @param <T> The type of element that the search or sort will operate upon.
 */
public abstract class BaseInputValidator<T> implements InputValidator<T> {

    private InputValidator next;

    public BaseInputValidator(final InputValidator next) {
        this.next = next;
    }

    public abstract boolean doValidateElements(List<T> elements) throws InvalidInputException;

    /**
     * {@inheritDoc}
     */
    @Override
    public final boolean validateElements(List<T> elements) throws InvalidInputException {
        boolean result = doValidateElements(elements);


        return false;
    }
}
