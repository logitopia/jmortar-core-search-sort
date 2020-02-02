package com.logitopia.jmortar.core.search;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertNotNull;

public class BinarySearchFindMatchesUnitTest {

    private static final List<Integer> TEST_ELEMENTS = new ArrayList<>();

    static {
        TEST_ELEMENTS.addAll(Arrays.asList(1, 2, 5, 7, 8, 10, 11, 12, 12, 12, 13, 16, 18, 22, 25, 26, 27, 30));
    }

    private BinarySearch<Integer> subject;

    @Before
    public void setup() {
        subject = new BinarySearch<>(
          Integer::equals,
                (first, second) -> first < second);
    }

    @Test
    public void testBasicSuccess() {
        List<Integer> results = subject.findMatches(TEST_ELEMENTS, 12);
        assertNotNull("Is the result list null?", results);
    }

}
