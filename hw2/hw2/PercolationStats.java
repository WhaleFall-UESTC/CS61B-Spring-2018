package hw2;
import edu.princeton.cs.introcs.StdRandom;
import edu.princeton.cs.introcs.StdStats;

public class PercolationStats {
    private int N;
    private int T;
    private PercolationFactory pf;
    private double[] x;

    public PercolationStats(int N, int T, PercolationFactory pf) {
        this.N = N;
        this.T = T;
        this.pf = pf;
        x = new double[T];

        for (int i = 0; i < T; i++) {
            x[i] = computeRandomPercolation();
        }
    }

    private double computeRandomPercolation() {
        StdRandom.setSeed(System.currentTimeMillis());
        Percolation p = pf.make(N);

        while(!p.percolates()) {
            int row = StdRandom.uniform(N);
            int col = StdRandom.uniform(N);
            p.open(row, col);
        }

        double openNum = p.numberOfOpenSites();
        double sum = N * N;
        return openNum / sum;
    }

    public double mean() {
        return StdStats.mean(x);
    }

    public double stddev() {
        return StdStats.stddev(x);
    }

    public double confidenceLow() {
        return mean() - 1.96 * stddev() / Math.sqrt(T);
    }

    public double confidenceHigh() {
        return mean() + 1.96 * stddev() / Math.sqrt(T);
    }

    public static void main(String[] args) {
        PercolationFactory pf = new PercolationFactory();
        PercolationStats ps = new PercolationStats(150, 999, pf);
        System.out.println("The range is [" + ps.confidenceLow() + ", " + ps.confidenceHigh() + "]");
    }
}
