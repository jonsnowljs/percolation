import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private final int size;
    private boolean[][] open;
    private int openSites = 0;
    private final WeightedQuickUnionUF uf;
    private static final int source = 0;
    private final int sink;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0) throw new IllegalArgumentException("n should be bigger than 0");
        open = new boolean[n+1][n];
        size = n;
        sink = n*n+n+1;
        uf = new WeightedQuickUnionUF(sink + 1);
        for (int i = 1; i <= n; i++) {
            uf.union(source, endcode(1, i));
            uf.union(sink, endcode(size+1, i));
            open[n][i-1] = true;
        }

        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= n; j++) {
                open[i-1][j-1] = false;
            }
        }

    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        int point = endcode(row, col);
        if (!isOpen(row, col)) {
            open[row-1][col-1] = true;
            openSites++;

            if (row == size) {
                uf.union(point, endcode(row - 1, col));
                uf.union(point, endcode(row, col - 1));
                uf.union(endcode(row, col + 1), point);
            }

            if (row - 1 > 0) {
                if (isOpen(row - 1, col)) {
                    uf.union(point, endcode(row - 1, col));
                }
            }
            if (row + 1 < size + 2) {
                if (isOpen(row + 1, col)) {
                    uf.union(endcode(row + 1, col), point);
                }
            }
            if (col - 1 > 0) {
                if (isOpen(row, col-1)) {
                    uf.union(point, endcode(row, col - 1));
                }
            }
            if (col + 1 < size + 1) {
                if (isOpen(row, col + 1)) {
                    uf.union(endcode(row, col + 1), point);
                }
            }

        }

    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        if (row <= 0 || col <= 0) {
            throw new IllegalArgumentException("n should be bigger than 0");
        }
        return open[row-1][col-1];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        boolean w = false, a = false, s = false, d = false;
        if (row <= 0 || col <= 0) {
            throw new IllegalArgumentException("n should be bigger than 0");
        }

        if (row == 2) {
            return uf.find(endcode(row, col)) == uf.find(source);
        }
        else if (row == size - 2) {
            return uf.find(endcode(row, col)) == uf.find(source);
        }
        else {
            if (isOpen(row, col)) {
                if (row - 1 > 0) {
                    w = uf.find(endcode(row - 1, col)) == uf.find(source);
                }
                if (col - 1 > 0) {
                    a = uf.find(endcode(row, col - 1)) == uf.find(source);
                }
                if (row + 1 < size + 1) {
                    s = uf.find(endcode(row + 1, col)) == uf.find(source);
                }
                if (col + 1 < size + 1) {
                    d = uf.find(endcode(row, col + 1)) == uf.find(source);
                }

            }
            return w || a || s || d;
        }
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return openSites;
    }

    // does the system percolate?
    public boolean percolates() {
        return uf.find(source) == uf.find(sink);
    }

    private int endcode(int row, int col) {
        return (row-1) * size + col;
    }

    // test client (optional)
    public static void main(String[] args) {
        //empty
    }
}