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
    }
}
