package com.logitopia.jmortar.core.sort;

import org.junit.Test;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertArrayEquals;

/**
 * Basic unit testing of the <tt>BubbleSort</tt> implementation.
 */
public class BubbleSortUnitTest {

    private BubbleSort subject = new BubbleSort();

    @Test
    public void testBasicSuccess() throws InterruptedException {
        int[] input = new int[] {5, 23, 8, 12, 3, 6, 1, 9, 52, 4, 16, 25};
        int inputLength = input.length;
        int[] expected = new int[] {1, 3, 4, 5, 6, 8, 9, 12, 16, 23, 25, 52};

        subject.sort(input);

        assertEquals("Input size has not changed", inputLength, input.length);
        assertArrayEquals("Is the list ordered correctly?", expected, input);
    }
}
