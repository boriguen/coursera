import java.util.Random;

/**
 * 
 */

/**
 * @author boris
 *
 */
public class PercolationStats {

    private double[] percolationSiteCounts = null;

    /**
     * Performs T independent experiments on an N-by-N grid.
     * 
     * @param N
     * @param T
     */
    public PercolationStats(int N, int T) {
        if (N <= 0) {
            throw new IllegalArgumentException("N should be greater than 0");
        }

        if (T <= 0) {
            throw new IllegalArgumentException("T should be greater than 0");
        }

        // Perform experiments.
        percolationSiteCounts = new double[T];
        performExperiments(N, T);
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        if (args.length != 2) {
            throw new IllegalArgumentException(
                    "The program should only contain N and T");
        }
        
        Stopwatch stopWatch = new Stopwatch();

        // Compute stats.
        int N = Integer.parseInt(args[0]);
        int T = Integer.parseInt(args[1]);
        PercolationStats percolationStats = new PercolationStats(N, T);

        // Display stats.
        System.out.printf("mean                    = %f",
                percolationStats.mean());
        System.out.printf("\nstddev                  = %f",
                percolationStats.stddev());
        System.out.printf("\n95%% confidence interval = %f, %f",
                percolationStats.confidenceLo(),
                percolationStats.confidenceHi());
        
        System.out.printf("\nTime for N = %d and T = %d is %f seconds", N, T, stopWatch.elapsedTime());
    }

    /**
     * Provides sample mean of percolation threshold.
     * 
     * @return
     */
    public double mean() {
        return StdStats.mean(percolationSiteCounts);
    }

    /**
     * Provides sample standard deviation of percolation threshold.
     * 
     * @return
     */
    public double stddev() {
        return StdStats.stddev(percolationSiteCounts);
    }

    /**
     * Provides low endpoint of 95% confidence interval.
     * 
     * @return
     */
    public double confidenceLo() {
        return mean() - 1.96 * stddev() / Math.sqrt(percolationSiteCounts.length);
    }

    /**
     * Provides high endpoint of 95% confidence interval.
     * 
     * @return
     */
    public double confidenceHi() {
        return mean() + 1.96 * stddev() / Math.sqrt(percolationSiteCounts.length);
    }

    /**
     * Returns a pseudo-random number between min and max, inclusive. The
     * difference between min and max can be at most
     * <code>Integer.MAX_VALUE - 1</code>.
     *
     * @param min
     *            Minimum value
     * @param max
     *            Maximum value. Must be greater than min.
     * @return Integer between min and max, inclusive.
     * @see java.util.Random#nextInt(int)
     */
    private static int randInt(Random random, int min, int max) {
        // nextInt is normally exclusive of the top value,
        // so add 1 to make it inclusive
        int randomNum = random.nextInt((max - min) + 1) + min;

        return randomNum;
    }

    /**
     * Performs T percolation experiments of size N*N.
     * 
     * @param N
     * @param T
     */
    private void performExperiments(int N, int T) {
        for (int i = 0; i < T; i++) {
            percolationSiteCounts[i] = performExperiment(N);
        }
    }

    /**
     * Performs a percolation experiment for a grid of size N*N and the
     * threshold.
     * 
     * @param N
     * @return
     */
    private double performExperiment(int N) {
        Percolation percolation = new Percolation(N);

        Random random = new Random();
        int count = 0;
        int i = 0, j = 0;
        int min = 1, max = N;
        int maxSites = N * N;
        do {
            // Select random i and j.
            i = randInt(random, min, max);
            j = randInt(random, min, max);

            if (!percolation.isOpen(i, j)) {
                // Open site.
                percolation.open(i, j);
                ++count;
            }
        } while (!percolation.percolates() && count < maxSites);

        double threshold = (double) count / (double) maxSites;

        return threshold;
    }

}
