package hw2;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private int[][] grid;
    private int top;
    private int bottom;
    private WeightedQuickUnionUF set;
    private int number;

    /* create N-by-N grid, with all sites initially blocked. */
    public Percolation(int N) {
        number = 0;
        if (N <= 0) {
            throw new IllegalArgumentException("N must greater than 0");
        }
        top = N * N;
        bottom = N * N + 1;
        grid = new int[N][N];
        set = new WeightedQuickUnionUF(N * N + 2);
        for (int i = 0; i < grid.length; i += 1) {
            for (int j = 0; j < grid[i].length; j += 1) {
                grid[i][j] = translation(j, i, N);
            }
        }
        for (int elem : grid[0]) {
            set.union(elem, top);
        }
        for (int elem : grid[N - 1]) {
            set.union(elem, bottom);
        }
    }

    /* open the site(row, col) if it is not open already. */
    public void open(int row, int col) {
        if (isOpen(row, col)) return;
        grid[row][col] = -1;
        link(row, col);
        number += 1;
    }

    /* is the site(row, col) open? */
    public boolean isOpen(int row, int col) {
        if (isOverBound(row, col)) throw new IndexOutOfBoundsException();
        return grid[row][col] == -1;
    }

    /* is the site(row, col) full? */
    public boolean isFull(int row, int col) {
        if (isOverBound(row, col)) throw new IndexOutOfBoundsException();
        return true;
    }

    /* number of open sites. */
    public int numberOfOpenSites() {
        return number;
    }

    /* does the system percolate? */
    public boolean percolates() {
        return set.connected(top, bottom);
    }

    /* translates the row&column position of the grid into a single number. */
    private int translation(int c, int r, int length) {
        if (isOverBound(r, c)) throw new IndexOutOfBoundsException();
        return r * length + c;
    }

    /* return true if position is too large or small. */
    private boolean isOverBound(int row, int col) {
        return row >= grid.length && col >= grid.length && row > 0 && col > 0;
    }

    /* if the sites around the central are legal then link them together. */
    private void link(int row, int col) {
        int originPosition = translation(col, row, grid.length);
        // left corner case.
        if (col == 0) {
            if (isOpen(row, col + 1)) {
                set.union(originPosition, translation(row, col + 1, grid.length));
            }
            // left top case.
            if (row == 0) {
                if (isOpen(row + 1, col)) {
                    set.union(originPosition, translation(row + 1, col, grid.length));
                }
            }
            // left bottom case.
            if (row == grid.length - 1) {
                if (isOpen(row - 1, col)) {
                    set.union(originPosition, translation(row - 1, col, grid.length));
                }
            }
            if (row != grid.length && row != 0) {
                if (isOpen(row - 1, col)) {
                    set.union(originPosition, translation(row - 1, col, grid.length));
                }
                if (isOpen(row + 1, col)) {
                    set.union(originPosition, translation(row + 1, col, grid.length));
                }
            }
            return;
        }

        // right corner case.
        if (col == grid.length - 1) {
            if (isOpen(row - 1, col)) {
                set.union(originPosition, translation(row - 1, col, grid.length));
            }
            // right top case.
            if (row == 0) {
                if (isOpen(row + 1, col)) {
                    set.union(originPosition, translation(row + 1, col, grid.length));
                }
            }
            // right bottom case.
            if (row == grid.length - 1) {
                if (isOpen(row - 1, col)) {
                    set.union(originPosition, translation(row - 1, col, grid.length));
                }
            }
            if (row != grid.length - 1 && row != 0) {
                if (isOpen(row - 1, col)) {
                    set.union(originPosition, translation(row - 1, col, grid.length));
                }
                if (isOpen(row + 1, col)) {
                    set.union(originPosition, translation(row + 1, col, grid.length));
                }
            }
            return;
        }

        // upper case.
        if (row == 0) {
            if (isOpen(row + 1, col)) {
                set.union(originPosition, translation(col, row + 1, grid.length));
            }
            if (isOpen(row, col - 1)) {
                set.union(originPosition, translation(col - 1, row, grid.length));
            }
            if (isOpen(row, col + 1)) {
                set.union(originPosition, translation(col + 1, row, grid.length));
            }
            return;
        }

        // lower case.
        if (row == grid.length - 1) {
            if (isOpen(row - 1, col)) {
                set.union(originPosition, translation(col, row - 1, grid.length));
            }
            if (isOpen(row, col - 1)) {
                set.union(originPosition, translation(col - 1, row, grid.length));
            }
            if (isOpen(row, col + 1)) {
                set.union(originPosition, translation(col + 1, row, grid.length));
            }
            return;
        }

        // common case.
        if (isOpen(row - 1, col)) {
            set.union(originPosition, translation(col, row + 1, grid.length));
        }
        if (isOpen(row, col - 1)) {
            set.union(originPosition, translation(col - 1, row, grid.length));
        }
        if (isOpen(row, col + 1)) {
            set.union(originPosition, translation(col + 1, row, grid.length));
        }
        if (isOpen(row + 1, col)) {
            set.union(originPosition, translation(col, row + 1, grid.length));
        }
    }

    /* use for unit testing. */
    public static void main(String[] args) {

    }
}
