package bearmaps;

import edu.princeton.cs.algs4.Stopwatch;

/**
 * Created by hug. Demonstrates how you can use either
 * System.currentTimeMillis or the Princeton Stopwatch
 * class to time code.
 */
public class TimingTestDemo {
    public static void main(String[] args) {
        MinPQ<String> testCase = new MinPQ<>();
        testCase.add("hello", 2.0);
        testCase.add("the", 3.0);
        testCase.add("world", 4.0);
        testCase.add("go", 5.0);
        for (int i = 0; i < 1000000; i += 1) {
            testCase.add("a" + i, i);
        }
        Stopwatch sw1 = new Stopwatch();
        for (int i = 0; i < 1000; i += 1) {
            testCase.getSmallest();
        }
        System.out.println("Total time elapsed: " + sw1.elapsedTime() +  " seconds.");

        NaiveMinPQ<String> testCase2 = new NaiveMinPQ<>();
        testCase2.add("hello", 2.0);
        testCase2.add("the", 3.0);
        testCase2.add("world", 4.0);
        testCase2.add("go", 5.0);
        for (int i = 0; i < 1000000; i += 1) {
            testCase2.add("a" + i, i);
        }
        Stopwatch sw = new Stopwatch();
        for (int i = 0; i < 1000; i += 1) {
            testCase2.getSmallest();
        }
        System.out.println("Total time elapsed: " + sw.elapsedTime() +  " seconds.");
    }
}
