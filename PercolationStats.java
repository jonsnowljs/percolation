import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.Stopwatch;

public class PercolationStats {
    private final int trails;
    private double[] p;
    private final double mean;
    private final double stddev;
    private final double confidence95 = 1.96;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        trails = trials;
        p = new double[trials];
        for (int i = 0; i < trials; i++) {
            p[i] = performPercolate(n)/(double) (n*n);
        }
        mean = mean();
        stddev = stddev();

    }

    private double performPercolate(int n) {
        Percolation percolation = new Percolation(n);
        while (!percolation.percolates()) {

            double i = StdRandom.uniform(n)+1;
            double j = StdRandom.uniform(n)+1;
            int randomRow = (int) i;
            int randomCol = (int) j;
            if (!percolation.isOpen(randomRow, randomCol)) {
                percolation.open(randomRow, randomCol);
            }
        }
        return percolation.numberOfOpenSites();
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(p);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(p);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return mean - confidence95 * Math.sqrt(stddev) / (trails - 1);
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return mean + confidence95 * Math.sqrt(stddev) / (trails - 1);
    }

    // test client (see below)
    public static void main(String[] args) {
        // if (args.length != 2) {
        //     throw new IllegalArgumentException("Usage: java-algs4 PercolationStats 200 100");
        // }

        Stopwatch stopwatch = new Stopwatch();
        PercolationStats percolationStats = new PercolationStats(1000, 100);
        System.out.printf("%30s%30f%n", "mean", percolationStats.mean);
        System.out.printf("%30s%30f%n", "stddev", percolationStats.stddev);
        System.out.printf("%30s%30s%n", "95% confidence interval", "[" + percolationStats.confidenceLo() +", " + percolationStats.confidenceHi() + "]");

        System.out.printf("%30s%30f", "Time", stopwatch.elapsedTime());
    }

}