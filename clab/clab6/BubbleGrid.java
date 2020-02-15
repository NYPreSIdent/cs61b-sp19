import java.util.ArrayList;

public class BubbleGrid {
    private int[][] grid;

    /* Create new BubbleGrid with bubble/space locations specified by grid.
     * Grid is composed of only 1's and 0's, where 1's denote a bubble, and
     * 0's denote a space. */
    public BubbleGrid(int[][] grid) {
        this.grid = grid;
    }

    /* Returns an array whose i-th element is the number of bubbles that
     * fall after the i-th dart is thrown. Assume all elements of darts
     * are unique, valid locations in the grid. Must be non-destructive
     * and have no side-effects to grid. */
    public int[] popBubbles(int[][] darts) {
        int[] result = new int[darts.length];
        UnionFind set = new UnionFind(grid.length * grid[0].length);
        /* union every row bubbles. */
        unionRow(grid, set);
        /* union every column bubbles. */
        unionColumn(grid, set);
        /* return the result. */
        return maniDart(result, set, darts);
    }

    /** union every bubbles on the rows. */
    private static void unionRow(int[][] a, UnionFind set) {
        for (int i = 0; i < a.length; i += 1) {
            int j = 0;
            while ((j < a[i].length) && (a[i][j] != 1)) {
                j += 1;
            }
            if (j == a[i].length) {
                continue;
            }
            int foundation = i * a[i].length + j;
            for (int index = j + 1; index < a[i].length; index += 1) {
                if (a[i][index] == 1) {
                    set.union(foundation, i * a[i].length + index);
                }
            }
        }
    }

    /** union every bubbles on the column. */
    private static void unionColumn(int[][] a, UnionFind set) {
        for (int i = 0; i < a[0].length; i += 1) {
            int j = 0;
            while ((j < a.length) && (a[j][i] == 0)) {
                j += 1;
            }
            if (j >= a.length) {
                continue;
            }
            int foundation = j * a[j].length + i;
            for (int index = j + 1; index < a.length; index += 1) {
                if (a[index][i] == 1) {
                    set.union(foundation, j * a[j].length + index);
                }
            }
        }
    }

    /** return the result of the arrays. */
    private int[] maniDart(int[] result, UnionFind set, int[][] dart) {
        for (int i = 0; i < dart.length; i += 1) {
            int sum;
            for (int elem : dart[i]) {
                sum = dart[i][0] * dart.length + dart[i][1];
                result[i] = set.sizeOf(sum);
            }
        }
        return result;
    }
}
