package hw2;

import edu.princeton.cs.introcs.StdStats;
import edu.princeton.cs.introcs.StdRandom;

public class PercolationStats {
    private int performTimes;
    private int number;
    private double u;
    private PercolationFactory pf;
    private double allSamples[];

    /* perform T independent experiments on an N-by-N grid. */
    public PercolationStats(int N, int T, PercolationFactory pf) {
        if (N <= 0 || T <= 0) throw new IllegalArgumentException();
        performTimes = T;
        this.pf = pf;
        number = N;
    }

    /* sample mean of percolation threshold. */
    public double mean() {
        allSamples = new double[performTimes];
        int chosenCol = StdRandom.uniform(0, number);
        int chosenRow = StdRandom.uniform(0, number);
        for (int i = 0; i < performTimes; i += 1) {
            double times = 0;
            Percolation testSample = pf.make(number);
            while (!testSample.percolates()) {
                testSample.open(chosenRow, chosenCol);
                times += 1;
            }
            allSamples[i] = times / (number * number);
        }
        return StdStats.mean(allSamples);
    }

    /* sample standard deviation of percolation threshold. */
    public double stddev() {
        u = mean();
        double[] stdSamples = new double[performTimes];
        for (int i = 0; i < stdSamples.length; i += 1) {
            stdSamples[i] = Math.pow(allSamples[i] - u, 2);
        }
        return StdStats.stddev(stdSamples);
    }

    /* low endpoint of 95percent confidence interval. */
    public double confidenceLow() {
        double sigma = stddev();
        return u - (1.96 * sigma) / Math.sqrt(performTimes);
    }

    /* high endpoint of 95percent confidence interval. */
    public double confidenceHigh() {
        double sigma = stddev();
        return u + (1.96 * sigma) / Math.sqrt(performTimes);
    }
}
