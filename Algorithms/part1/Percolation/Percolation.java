package Algorithms.part1.Percolation;
/**
 * Created by rliu on 5/25/16.
 */

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private WeightedQuickUnionUF uf;
    private int[] grid;
    private int size;

    public Percolation(int N)               // create N-by-N grid, with all sites blocked
    {
        size = N;
        if (N < 0) {
            throw new java.lang.IllegalArgumentException();
        } else {
            grid = new int[N * N];
            uf = new WeightedQuickUnionUF(N * N + 2);
//            for (int i = 0; i < N; i++) {
//                uf.union(i, N * N);
//            }
//            for (int i = (N - 1) * N; i < N * N; i++) {
//                uf.union(i, N * N + 1);
//            }
        }

    }

    // test client (optional)
    public static void main(String[] args) {
//        Percolation p=new Percolation(2);
//        System.out.println(p.percolates());
//        p.open(2,1);
//
//        System.out.println(p.isFull(2,1));
//        p.open(1,1);
//        System.out.println(p.percolates());
//
//

    }

    public void open(int i, int j)          // open site (row i, column j) if it is not open already 1<=i,j<=N
    {
        if (!(1 <= i && i <= size && 1 <= j && j <= size))
            throw new java.lang.IndexOutOfBoundsException();
        if (!isOpen(i, j)) {
            int index = (i - 1) * size + j - 1;
            int left = (i - 1) * size + j - 2;
            int right = (i - 1) * size + j;
            int up = (i - 2) * size + j - 1;
            int down = i * size + j - 1;
            grid[index] = 1;
            if (i == 1) //first row
                uf.union(index, size * size);
            if (i >= 2 && isOpen(i - 1, j)) //up
                uf.union(index, up);
            if (i == size) //last row
                uf.union(index, size * size + 1);
            if (i <= size - 1 && isOpen(i, j)) //down
                uf.union(index, down);
            if (j >= 2 && isOpen(i, j - 1))
                uf.union(index, left);
            if (j <= size - 1 && isOpen(i, j + 1))
                uf.union(index, right);
        }

    }

    public boolean isOpen(int i, int j)     // is site (row i, column j) open?
    {
        if (!(1 <= i && i <= size && 1 <= j && j <= size))
            throw new java.lang.IndexOutOfBoundsException();
        return grid[(i - 1) * size + j - 1] == 0 ? false : true;
    }

    public boolean isFull(int i, int j)     // is site (row i, column j) full?
    {
        if (!(1 <= i && i <= size && 1 <= j && j <= size))
            throw new java.lang.IndexOutOfBoundsException();

        if (isOpen(i, j)) {
            return uf.connected((i - 1) * size + j - 1, size * size);
        } else
            return false;
    }

    public boolean percolates()             // does the system percolate?
    {
        return uf.connected(size * size, size * size + 1);
    }
}

