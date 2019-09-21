package com.logitopia.jmortar.core.sort.parallel;

import com.logitopia.jmortar.core.sort.data.IntegerTestData;
import com.logitopia.jmortar.core.sort.data.TestData;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Unit-level performance testing of the <tt>ParallelMergeSort</tt> implementation of the <tt>Sort</tt> interface
 */
public class ParallelMergeSortPerformanceTest {

    private static final int MEDIUM_YIELD_ELEMENT_SIZE = 1000000;

    private ParallelMergeSort<Integer> subject = new ParallelMergeSort<>();

    @Test
    public void testMediumYieldTestData() {
        TestData<Integer> testData = new IntegerTestData(MEDIUM_YIELD_ELEMENT_SIZE);
        System.out.println("Generating Test Data..");
        List<Integer> testDataElements = testData.get();

        System.out.println("Loading Test Data into Array..");
        Integer[] input = new Integer[testDataElements.size()];
        testDataElements.toArray(input);

        System.out.println("Initiating sort..");
        long startTime = System.nanoTime();
        subject.sort(input, (first, second) -> first > second);
        long endTime = System.nanoTime();
        long timeTaken = endTime - startTime;
        System.out.println("Sort completed in " + TimeUnit.NANOSECONDS.toSeconds(timeTaken) + " seconds!");
    }

    private List<String> retrieveTestData(String testDataRelativePath) throws IOException {
        List<String> results = new ArrayList<>();
        String path = new StringBuilder("src/test/resources/")
                .append(testDataRelativePath).toString();

        BufferedReader br = new BufferedReader(new FileReader(path));
        String line;
        while ((line = br.readLine()) != null) {
            String[] values = line.split(",");
            results.addAll(Arrays.asList(values));
        }
        return results;
    }
}
