package com.logitopia.jmortar.core.search;

import com.logitopia.jmortar.core.test.AbstractUnitTest;
import com.logitopia.jmortar.core.test.exception.PrivateTestMethodException;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class BinarySearchFindLeftmostMatchUnitTest extends AbstractUnitTest<BinarySearch<Integer>> {

    private static final List<Integer> TEST_ELEMENTS = new ArrayList<>();

    static {
        TEST_ELEMENTS.addAll(Arrays.asList(1, 2, 5, 7, 8, 10, 11, 12, 12, 12, 13, 16, 18, 22, 25, 26, 27, 30));
    }

    @Before
    public void setup() {
        // Set a binary search that compares by integer equality and defines a proper less-than comparator.
        setSubject(new BinarySearch<>(Integer::equals,
                (first, second) -> first < second));
    }

    /**
     * Test that, given a properly-formed dataset containing the target of the search with duplicate values, we
     * correctly identify the left-most index of the target value.
     *
     * @throws PrivateTestMethodException
     */
    @Test
    public void testBasicSuccess() throws PrivateTestMethodException {
        int result = (Integer) this.executePrivateMethod("findLeftmostMatch",
                new Class[]{List.class, Object.class},
                new Object[]{TEST_ELEMENTS, 12});

        assertEquals("Successfully identified the leftmost element where duplicates exist",
                7, result);
    }

    /**
     * Test that, given a properly-formed dataset containing the target of the search with a single (non-duplicate)
     * value, we correctly identify it's location in the array.
     *
     * @throws PrivateTestMethodException
     */
    @Test
    public void testSingleValueDoesNotError() throws PrivateTestMethodException {
        int result = (Integer) this.executePrivateMethod("findLeftmostMatch",
                new Class[]{List.class, Object.class},
                new Object[]{new ArrayList<>(Arrays.asList(12)), 12});

        assertEquals("Ensure that the method correctly indicates the location of the single value",
                0, result);
    }
}
