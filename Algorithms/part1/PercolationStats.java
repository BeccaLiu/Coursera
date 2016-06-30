package Algorithms.part1;

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

/**
 * Created by rliu on 5/25/16.
 */
public class PercolationStats {
    private double[] testResult;
    private int T;

    public PercolationStats(int N, int T)     // perform T independent experiments on an N-by-N grid
    {
        this.T = T;
        if (N <= 0 || T <= 0)
            throw new java.lang.IllegalArgumentException();
        testResult = new double[T];
        for (int i = 0; i < T; i++) {
            Percolation p = new Percolation(N);
            double openCount = 0;
            while (!p.percolates()) {
                int row = StdRandom.uniform(N) + 1;
                int column = StdRandom.uniform(N) + 1;
                if (!p.isOpen(row, column)) {
                    // System.out.println(row+","+column);
                    openCount += 1.0;
                    p.open(row, column);
                }
            }
            testResult[i] = openCount / (N * N);

        }

    }

    public static void main(String[] args)    // test client (described below)
    {
//        Stopwatch sw=new Stopwatch();
        PercolationStats ps = new PercolationStats(Integer.valueOf(args[0]), Integer.valueOf(args[1]));
        StdOut.printf("%-25s = %s\n", "mean", Double.toString(ps.mean()));
        StdOut.printf("%-25s = %s\n", "stddev", Double.toString(ps.stddev()));
        StdOut.printf("%-25s = %s,%s\n", "95% confidence interval", Double.toString(ps.confidenceLo()), Double.toString(ps.confidenceHi()));
//        StdOut.println(sw.elapsedTime());
    }

    public double mean()                      // sample mean of percolation threshold
    {
        return StdStats.mean(testResult);
    }

    public double stddev()                    // sample standard deviation of percolation threshold
    {
        return StdStats.stddev(testResult);

    }

    public double confidenceLo()              // low  endpoint of 95% confidence interval
    {
        return StdStats.mean(testResult) - 1.96 * StdStats.stddev(testResult) / Math.sqrt(this.T);

    }

    public double confidenceHi()              // high endpoint of 95% confidence interval
    {
        return StdStats.mean(testResult) + 1.96 * StdStats.stddev(testResult) / Math.sqrt(this.T);
    }
}
