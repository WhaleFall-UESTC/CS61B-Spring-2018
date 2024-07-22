package hw2;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private int N;
    private int size;
    private int fullRoot;
    private int openNum;
    private boolean percolate;
    private WeightedQuickUnionUF uf;
    private boolean[] blocksOpen;

    public Percolation(int N) {
        if (N <= 0) {
            throw new IllegalArgumentException("Initialization: N should be positive");
        }
        this.N = N;
        size = N * N;
        fullRoot = size;
        openNum = 0;
        percolate = false;
        uf = new WeightedQuickUnionUF(size + 1);
        blocksOpen = new boolean[size];
        for (int i = 0; i < size; i++) {
            blocksOpen[i] = false;
        }
    }

    private boolean checkBound(int i) {
        return (i >= 0 && i < N);
    }

    private void checkCordThrowError(int row, int col) {
        if (!checkBound(row) || !checkBound(col)) {
            throw new IndexOutOfBoundsException();
        }
    }

    private boolean checkCord(int row, int col) {
        return (checkBound(row) && checkBound(col));
    }

    private int cord2Index(int row, int col) {
        return row * N + col;
    }

    private void setOpen(int index) {
        blocksOpen[index] = true;
        openNum++;
    }

    private void unionBlocks(int index, int r2, int c2) {
        if (isOpen(r2, c2)) {
            uf.union(index, cord2Index(r2, c2));
        }
    }

    private void unionOpenNeighbor(int row, int col) {
        int index = cord2Index(row, col);
        unionBlocks(index, row + 1, col);
        unionBlocks(index, row - 1, col);
        unionBlocks(index, row, col + 1);
        unionBlocks(index, row, col - 1);
    }

    public void open(int row, int col) {
        checkCordThrowError(row, col);
        int index = cord2Index(row, col);
        /*  Step1. Set this block open  */
        if (!isOpen(index)) {
            setOpen(index);
        }
        /*  Step2. Union open neighbor block  */
        unionOpenNeighbor(row, col);
        /*  Step3. If block is on the top, union fullRoot  */
        if (row == 0) {
            uf.union(fullRoot, index);
        }
        /*  Step4. If block is at the bottom, check percolation  */
        if (!percolate && row == N - 1 && isFull(index)) {
            percolate = true;
        }
    }

    public boolean isOpen(int row, int col) {
        if (checkCord(row, col)) {
            return blocksOpen[cord2Index(row, col)];
        } else {
            return false;
        }
    }

    private boolean isOpen(int index) {
        return blocksOpen[index];
    }

    public boolean isFull(int row, int col) {
        if (checkCord(row, col)) {
            return uf.connected(fullRoot, cord2Index(row, col));
        } else {
            return false;
        }
    }

    private boolean isFull(int index) {
        return uf.connected(fullRoot, index);
    }

    public int numberOfOpenSites() {
        return openNum;
    }

    public boolean percolates() {
        return percolate;
    }

    private static void Assert(boolean exp) {
        if (!exp) {
            throw new IllegalArgumentException("assret failed");
        }
    }

    public static void main(String[] args) {
        Percolation p = new Percolation(4);
        p.open(0, 0);
        p.open(1, 1);
        p.open(2, 2);
        p.open(3, 3);
        Assert(p.percolates() == false);
        p.open(1, 0);
        Assert(p.isFull(0, 0));
        Assert(p.isFull(1, 1));
        p.open(2, 1);
        Assert(p.numberOfOpenSites() == 6);
        Assert(p.isFull(2, 2));
        p.open(3, 2);
        Assert(p.percolates() == true);
        p.open(1, 3);
        Assert(p.isFull(1, 3) == false);
        p.open(0, 3);
        Assert(p.isFull(1, 3));
        p.open(3, 0);
        Assert(p.isFull(3, 0) == false);
        Assert(p.isFull(2, 3) == false);
        System.out.println("PASS!!!");
    }
}
