package com.logitopia.jmortar.core.search;

/**
 * A parallelized search has been stopped prematurely.
 */
public class ParallelizedSearchStoppedException extends Exception {
    public ParallelizedSearchStoppedException(Throwable cause) {
        super(cause);
    }
}
