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

    @Override
    public List<Integer> get() {
        List<Integer> result = new ArrayList<>();
        int maxBound = 50000000;
        IntStream.range(0, maxBound)
                .forEach(e -> result.add(random.nextInt() & Integer.MAX_VALUE));
        return result;
    }
}
