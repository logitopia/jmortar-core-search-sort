package com.logitopia.jmortar.core.sort.parallel;

import com.logitopia.jmortar.core.comparator.Comparator;
import com.logitopia.jmortar.core.test.AbstractUnitTest;
import com.logitopia.jmortar.core.test.exception.PrivateTestMethodException;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;

/**
 * Unit test the private <tt>mergeSortedLists</tt> method on the <tt>ParallelMergeSort</tt> class.
 */
public class ParallelMergeSortMergeSortedListsUnitTest extends AbstractUnitTest<ParallelMergeSort<Integer>> {

    @Before
    public void setup() {
        setSubject(new ParallelMergeSort<>());
    }

    @Test
    public void testBasicSuccess() throws PrivateTestMethodException {
        List<Integer> first = new ArrayList<>();
        first.add(4);
        first.add(8);

        List<Integer> second = new ArrayList<>();
        second.add(5);
        second.add(7);

        Object resultObj = executePrivateMethod("mergeSortedLists",
                new Class[] {List.class, List.class, Comparator.class},
                new Object[] {first, second, (Comparator<Integer>) (a, b) -> a > b});

        assertTrue("Is the result of the correct type?", resultObj instanceof List);

        List<Integer> result = (List<Integer>) resultObj;

        assertEquals("Does the list contain the correct number of results?", 4, result.size());
        testOrder(result);
    }

    private void testOrder(List<Integer> result) {
        assertEquals("Does the first element contain the expected value?", (Integer) 4, result.get(0));
        assertEquals("Does the second element contain the expected value?", (Integer) 5, result.get(1));
        assertEquals("Does the third element contain the expected value?", (Integer) 7, result.get(2));
        assertEquals("Does the fourth element contain the expected value?", (Integer) 8, result.get(3));
    }

    @Test
    public void testSmallerLeftList() throws PrivateTestMethodException {
        List<Integer> first = new ArrayList<>();
        first.add(2);
        first.add(5);

        List<Integer> second = new ArrayList<>();
        second.add(1);
        second.add(3);
        second.add(8);

        Object resultObj = executePrivateMethod("mergeSortedLists",
                new Class[] {List.class, List.class, Comparator.class},
                new Object[] {first, second, (Comparator<Integer>) (a, b) -> a > b});

        assertTrue("Is the result of the correct type?", resultObj instanceof List);

        List<Integer> result = (List<Integer>) resultObj;

        assertEquals("Does the list contain the correct number of results?", 5, result.size());
        verifyOrder(new int[] {1, 2, 3, 5, 8}, result);
    }

    @Test
    public void testSmallerRightList() throws PrivateTestMethodException {
        List<Integer> first = new ArrayList<>();
        first.add(2);
        first.add(5);
        first.add(7);

        List<Integer> second = new ArrayList<>();
        second.add(1);
        second.add(3);

        Object resultObj = executePrivateMethod("mergeSortedLists",
                new Class[] {List.class, List.class, Comparator.class},
                new Object[] {first, second, (Comparator<Integer>) (a, b) -> a > b});

        assertTrue("Is the result of the correct type?", resultObj instanceof List);

        List<Integer> result = (List<Integer>) resultObj;

        assertEquals("Does the list contain the correct number of results?", 5, result.size());
        verifyOrder(new int[] {1, 2, 3, 5, 7}, result);
    }

    private void verifyOrder(int[] expected, List<Integer> result) {
        for (int i=0; i < expected.length; i++) {
            assertEquals("Is the value at position " + (i + 1) + " expected?",
                    expected[i],
                    result.get(i).intValue());
        }
    }

    // TODO - Add in testing for single value lists, as well as uneven sized lists...
}
