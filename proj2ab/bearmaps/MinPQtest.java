package bearmaps;

import static org.junit.Assert.*;
import org.junit.Test;

public class MinPQtest {

    /** test the function of add items. */
    @Test
    public void addTest() {
        MinPQ<String> testCase = new MinPQ<>();
        testCase.add("hello", 2.0);
        testCase.add("the", 3.0);
        testCase.add("world", 4.0);
        testCase.add("go", 5.0);
        for (double i = 5.0; i < 1000; i += 1) {
            testCase.add("a" + (int) i, i);
        }

        assertEquals("hello", testCase.getSmallest());
    }

    @Test
    public void deleteTest() {
        MinPQ<String> testCase = new MinPQ<>();
        testCase.add("hello", 2.0);
        testCase.add("the", 3.0);
        testCase.add("world", 4.0);
        testCase.add("go", 1.0);
        for (double i = 11; i < 80; i += 1) {
            testCase.add("a" + (int) i, i);
        }

        for (int j = 11; j < 80; j += 1) {
            testCase.removeSmallest();
        }

        assertEquals("a76", testCase.getSmallest());
    }

    @Test
    public void ContainsTest() {
        MinPQ<String> testCase = new MinPQ<>();
        testCase.add("hello", 2.0);
        testCase.add("the", 3.0);
        testCase.add("world", 4.0);
        testCase.add("go", 5.0);
        assertTrue(testCase.contains("go"));

        for (int i = 0; i < 4; i += 1) {
            testCase.removeSmallest();
        }

        assertFalse(testCase.contains("go"));
    }

    @Test
    public void changePriorityTest() {
        MinPQ<String> testCase = new MinPQ<>();
        testCase.add("hello", 2.0);
        testCase.add("the", 3.0);
        testCase.add("world", 4.0);
        testCase.add("go", 5.0);
        assertTrue(testCase.contains("go"));

        testCase.changePriority("go", 1.0);
        testCase.changePriority("world", 1.5);
        assertEquals("go", testCase.removeSmallest());

        for (double i = 11; i < 80; i += 1) {
            testCase.add("a" + (int) i, i);
            testCase.changePriority("a" + (int) i, 1000 - i);
        }
        assertEquals("world", testCase.removeSmallest());
    }
}
