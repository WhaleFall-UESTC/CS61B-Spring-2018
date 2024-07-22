package hw2;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private int N;
    private int size;
    private int fullRoot;
    private int openNum;
    private WeightedQuickUnionUF uf;
    private boolean[] blocksOpen;

    public Percolation(int N) {
        if (N <= 0) {
            throw new IllegalArgumentException("Initialization: N should be positive");
        }
        this.N = N;
        size = N * N;
        fullRoot = size + N;
        openNum = 0;
        uf = new WeightedQuickUnionUF(size + N + 1);
        blocksOpen = new boolean[size + N];
        for (int i = 0; i < size; i++) {
            blocksOpen[i] = false;
        }

        int base = cord2Index(N, 0);
        for (int i = 0; i < N - 1; i++) {
            blocksOpen[i + base] = true;
            uf.union(i + base, i + base + 1);
        }
        blocksOpen[base + N - 1] = true;
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

    private boolean isOpenIncludeHidden(int row, int col) {
        if (checkBound(col) && row >= 0 && row <= N) {
            return blocksOpen[cord2Index(row, col)];
        } else {
            return false;
        }
    }

    private void unionBlocks(int index, int r2, int c2) {
        if (isOpenIncludeHidden(r2, c2)) {
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
        if (checkCord(row, col) && isOpen(row, col)) {
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
        return isFull(cord2Index(N, 0));
    }

    private static void assertTrue(boolean exp) {
        if (!exp) {
            throw new IllegalArgumentException("assert failed");
        }
    }

    public static void main(String[] args) {
        Percolation p = new Percolation(4);
        p.open(0, 0);
        p.open(1, 1);
        p.open(2, 2);
        p.open(3, 3);
//        assertTrue(p.percolates() == false);
        p.open(1, 0);
//        assertTrue(p.isFull(0, 0));
//        assertTrue(p.isFull(1, 1));
        p.open(2, 1);
//        assertTrue(p.numberOfOpenSites() == 6);
//        assertTrue(p.isFull(2, 2));
        p.open(2, 3);
        assertTrue(p.percolates());
        p.open(1, 3);
//        assertTrue(p.isFull(1, 3) == false);
        p.open(0, 3);
//        assertTrue(p.isFull(1, 3));
        p.open(3, 0);
//        assertTrue(p.isFull(3, 0) == false);
//        assertTrue(p.isFull(2, 3) == false);
        System.out.println("PASS!!!");
    }
}
