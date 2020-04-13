package com.logitopia.jmortar.core.comparator;

/**
 * Specifies the behavior for an implementation that compares one value to another of a specific type. This can be
 * used by sorts and searches.
 *
 * @param <T> The type of objects that are being compared.
 */
public interface Comparator<T> {

    /**
     * Compare the first element to the second. If the first and second elements aren't in the expected position,
     * then a <b>false</b> value will be returned from this operation.
     *
     * @param first  The first element to be compared.
     * @param second The element to compare to the first.
     * @return A flag indicating whether the compare was expected or not (false means unexpected).
     */
    boolean compare(T first, T second);
}
