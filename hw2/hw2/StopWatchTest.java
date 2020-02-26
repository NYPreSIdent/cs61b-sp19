package hw2;

import edu.princeton.cs.algs4.Stopwatch;

public class StopWatchTest {
    public static void main(String[] args) {
        for (int i = 200; i < 230; i += 1) {
            Stopwatch stopwatch = new Stopwatch();
            PercolationStats test;
            test = new PercolationStats(i, 600, new PercolationFactory());
            test.confidenceHigh();
            System.out.println(stopwatch.elapsedTime());
        }
    }
}
