package com.logitopia.jmortar.core.sort.data;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

/**
 * Generates a set of "random" positive integers that we can run through a unit.
 */
public class IntegerTestData implements TestData<Integer> {

    private Random random = new Random();

    private int maxBound = 1000000;

    /**
     * Create an instance of the test data that uses a default number of 1m entries.
     */
    public IntegerTestData() {
    }

    /**
     * Create an instance of the test data that specifies how many elements to generate for the test data list.
     *
     * @param maxBound The number of entries we want to create in the result list.
     */
    public IntegerTestData(int maxBound) {
        this.maxBound = maxBound;
    }

    @Override
    public List<Integer> get() {
        List<Integer> result = new ArrayList<>();
        IntStream.range(0, maxBound)
                .forEach(e -> result.add(random.nextInt() & Integer.MAX_VALUE));
        return result;
    }
}
