import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

/**
 * 
 */

/**
 * @author boris
 *
 */
public class Percolation {

    private int N = 0;
    private boolean[] grid = null;
    private ArrayList<Integer> bottomSiteIndexes = null;
    private int virtualTopSiteIndex = 0;
    private WeightedQuickUnionUF weightedQuickUnionUF = null;

    /**
     * Creates N-by-N grid, with all sites blocked.
     * 
     * @param N
     */
    public Percolation(int N) {
        if (N <= 0) {
            throw new IllegalArgumentException("N must be greater than 0");
        }
        
        // Store N.
        this.N = N;

        // Init blocked/open grid.
        this.grid = new boolean[N * N];
        for (int i = 0; i < this.grid.length; i++) {
            this.grid[i] = false;
        }

        // Init the quick union.
        initWeightedQuickUnion();
        
        // Init the stack to be used to unify bottom sites with virtual one as soon
        // as they become full.
        this.bottomSiteIndexes = new ArrayList<Integer>(N / 2);
    }
    
    /**
     * @param args
     * @throws FileNotFoundException 
     */
    public static void main(String[] args) throws FileNotFoundException {
        if (args.length != 1) {
            throw new IllegalArgumentException(
                    "The program should only a file name");
        }

        Scanner scanner = new Scanner(new File(args[0]));
        if (scanner.hasNextInt()) {
            Percolation percolation = new Percolation(scanner.nextInt());
            int count = 1;
            while (scanner.hasNextLine() && scanner.hasNextInt()) {
                int i = scanner.nextInt();
                int j = scanner.nextInt();
                percolation.open(i, j);
                
                percolation.printSystem();
                //System.out.printf("\n%d. isFull(%d, %d) equals %b", count, i, j, percolation.isFull(i, j));
                System.out.printf("\n%d. percolates after (%d, %d) equals %b", count, i, j, percolation.percolates());
                
                count++;
            }
        }
        scanner.close();
    }

    /**
     * Opens site (row i, column j) if it is not open already.
     * 
     * @param i
     * @param j
     */
    public void open(int i, int j) {
        int unifiedIndex = processUnifiedIndex(i, j);

        if (!this.grid[unifiedIndex]) {
            // Open site.
            this.grid[unifiedIndex] = true;
            
            // Union between neighboring sites as applicable.
            trySiteUnion(unifiedIndex, i - 1, j); // Top.
            trySiteUnion(unifiedIndex, i + 1, j); // Bottom.
            trySiteUnion(unifiedIndex, i, j - 1); // Left.
            trySiteUnion(unifiedIndex, i, j + 1); // Right.
            
            if (i == 1) {
                this.weightedQuickUnionUF.union(this.virtualTopSiteIndex,
                        unifiedIndex);
            }
            
            if (i == N) {
                this.bottomSiteIndexes.add(unifiedIndex);
            }
        }
    }

    /**
     * Is site (row i, column j) open?
     * 
     * @param i
     * @param j
     * @return
     */
    public boolean isOpen(int i, int j) {
        return isOpen(processUnifiedIndex(i, j));
    }

    /**
     * Is site (row i, column j) full?
     * 
     * @param i
     * @param j
     * @return
     */
    public boolean isFull(int i, int j) {
        return isFull(processUnifiedIndex(i, j));
    }

    /**
     * Does the system percolate?
     * 
     * @return
     */
    public boolean percolates() {
        boolean percolates = false;
        for (int i = 0; i < this.bottomSiteIndexes.size() && !percolates; i++) {
            percolates = this.weightedQuickUnionUF.connected(this.virtualTopSiteIndex,
                    this.bottomSiteIndexes.get(i)); 
        }
        return percolates;
    }

    /**
     * Is site (unifiedIndex) open?
     * 
     * @param unifiedIndex
     * @return
     */
    private boolean isOpen(int unifiedIndex) {
        return this.grid[unifiedIndex];
    }

    /**
     * Is site (unifiedIndex) full?
     * 
     * @param unifiedIndex
     * @return
     */
    private boolean isFull(int unifiedIndex) {
        return isOpen(unifiedIndex)
                && this.weightedQuickUnionUF.connected(unifiedIndex,
                        this.virtualTopSiteIndex);
    }

    /**
     * Prints the current state of the system to the console.
     */
    private void printSystem() {
        System.out.println("\n\nCurrent percolation system state:");
        System.out.print(this.grid[0] ? 1 : 0);
        for (int i = 1; i < this.grid.length; i++) {
            if (i % this.N == 0) {
                System.out.println();
            } else {
                System.out.print(' ');
            }
            System.out.print(this.grid[i] ? 1 : 0);
        }
    }

    private void initWeightedQuickUnion() {
        int N2 = this.N * this.N;

        // Determine virtual site index.
        this.virtualTopSiteIndex = N2;

        // Instantiate the weighted quick union.
        this.weightedQuickUnionUF = new WeightedQuickUnionUF(N2 + 1);
    }

    /**
     * Checks whether the given indexes are within the bounds and calculates
     * unified index to be used internally.
     * 
     * @param i
     * @param j
     */
    private int processUnifiedIndex(int i, int j) {
        final String template = "Index %s of value %d is out of bounds";
        if (i < 1 || i > this.N) {
            throw new IndexOutOfBoundsException(String.format(template, "i", i));
        } else if (j < 1 || j > this.N) {
            throw new IndexOutOfBoundsException(String.format(template, "j", j));
        }
        return (i - 1) * this.N + (j - 1);
    }

    /**
     * Tries to perform a site union between a given unified index site and a
     * site of indexes i, j.
     * 
     * @param unifiedIndex
     * @param i
     * @param j
     * @return
     */
    private boolean trySiteUnion(int unifiedIndex, int i, int j) {
        boolean success = false;
        if (i >= 1 && i <= this.N && j >= 1 && j <= this.N) {
            int neighborUnifiedIndex = processUnifiedIndex(i, j);
            if (isOpen(neighborUnifiedIndex)) {
                this.weightedQuickUnionUF.union(unifiedIndex,
                        neighborUnifiedIndex);
                success = true;
            }
        }
        return success;
    }

}
