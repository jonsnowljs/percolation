import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private final int size;
    private boolean[][] open;
    private int openSites = 0;
    private final WeightedQuickUnionUF uf;
    private final int source = 0;
    private final int sink;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0) throw new IllegalArgumentException("n should be bigger than 0");
        open = new boolean[n][n];
        size = n;
        sink = n * n + 1;
        uf = new WeightedQuickUnionUF(sink + 1);

        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= n; j++) {
                open[i-1][j-1] = false;
            }
        }
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        if (row <= 0 || row > size || col <= 0 || col > size) {
            throw new IndexOutOfBoundsException();
        }

        if (!isOpen(row, col)) {
            open[row-1][col-1] = true;
            openSites++;
            if (row == 1) {
                uf.union(source, encode(row, col));
            }
            unionTop(row, col);
            unionBottom(row, col);
            unionLeft(row, col);
            unionRight(row, col);
            if (row == size) {
                uf.union(sink, encode(row, col));
            }

        }

    }

    private void unionTop(int row, int col) {
        if (row > 1 && isOpen(row - 1, col)) {
            uf.union(encode(row, col), encode(row - 1, col));
        }
    }

    private void unionLeft(int row, int col) {
        if (col > 1 && isOpen(row, col-1)) {
            uf.union(encode(row, col), encode(row, col - 1));
        }
    }

    private void unionBottom(int row, int col) {
        if (row < size && isOpen(row + 1, col)) {
            uf.union(encode(row, col), encode(row +1, col));
        }
    }

    private void unionRight(int row, int col) {
        if (col < size && isOpen(row, col + 1)) {
            uf.union(encode(row, col + 1), encode(row, col));
        }
    }


    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        if (row <= 0 || row > size || col <= 0 || col > size) {
            throw new IndexOutOfBoundsException();
        }
        return open[row-1][col-1];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        if (row <= 0 || row > size || col <= 0 || col > size) {
            throw new IndexOutOfBoundsException();
        }

        return uf.find(encode(row, col)) == uf.find(source);
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return openSites;
    }

    // does the system percolate?
    public boolean percolates() {
        return uf.find(source) == uf.find(sink);
    }

    private int encode(int row, int col) {
        return (row-1) * size + col;
    }

    // test client (optional)
    public static void main(String[] args) {
        // empty
    }
}