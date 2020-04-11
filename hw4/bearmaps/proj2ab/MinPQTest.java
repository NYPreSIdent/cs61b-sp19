package bearmaps.proj2ab;

import org.junit.Test;

import java.util.Random;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class MinPQTest {

    private ArrayHeapMinPQ<Integer> generateCase(int n) {
        Random rdm = new Random(20);
        ArrayHeapMinPQ<Integer> testCase = new ArrayHeapMinPQ<>();
        for (; n > 0; n -= 1) {
            testCase.add(n, rdm.nextDouble());
        }
        return testCase;
    }

    @Test
    public void RunnableTestPQ() {
        ArrayHeapMinPQ<Integer> testCase = generateCase(1000000);
        for (int j = 0; j < 999999; j += 1) {
            testCase.removeSmallest();
        }
        testCase.add(-100, Double.MIN_VALUE);
        int actual = testCase.removeSmallest();
        assertEquals(-100, actual);
    }

    @Test
    public void EmptyTest() {
        ArrayHeapMinPQ<Integer> testCase = generateCase(999999);
        for (int j = 0; j < 999999; j += 1) {
            testCase.removeSmallest();
        }
        assertTrue(testCase.isEmpty());
    }
}
