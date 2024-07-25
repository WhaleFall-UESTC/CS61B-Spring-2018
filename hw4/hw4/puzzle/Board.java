package hw4.puzzle;

import java.util.ArrayList;

public class Board implements WorldState {
    private int N;
    private int[][] tiles;
    private int eDist;
    private int blankX, blankY;

    public Board(int[][] tiles) {
        N = tiles.length;
        this.tiles = new int[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                this.tiles[i][j] = tiles[i][j];
                if (tiles[i][j] == 0) {
                    blankX = i;
                    blankY = j;
                }
            }
        }
        eDist = -1;
    }

    public int tileAt(int i, int j) {
        return tiles[i][j];
    }

    public int size() {
        return N;
    }

    private Board copySwapBlank(int x, int y) {
        int[][] t = new int[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                t[i][j] = tiles[i][j];
            }
        }
        t[x][y] = tiles[blankX][blankY];
        t[blankX][blankY] = tiles[x][y];
        return new Board(t);
    }

    public Iterable<WorldState> neighbors() {
        ArrayList<WorldState> res = new ArrayList<>();
        if (blankX != 0) {
            res.add(copySwapBlank(blankX - 1, blankY));
        }
        if (blankX != N - 1) {
            res.add(copySwapBlank(blankX + 1, blankY));
        }
        if (blankY != 0) {
            res.add(copySwapBlank(blankX, blankY - 1));
        }
        if (blankY != N - 1) {
            res.add(copySwapBlank(blankX, blankY + 1));
        }
        return res;
    }

    private int cord2Index(int i, int j) {
        return i * N + j + 1;
    }

    public int hamming() {
        int cnt = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (tiles[i][j] == 0) {
                    continue;
                }
                if (cord2Index(i, j) != tiles[i][j]) {
                    cnt++;
                }
            }
        }
        return cnt;
    }

    private int index2CordX(int index) {
        return (index - 1) / N;
    }

    private int index2CordY(int index) {
        return (index - 1) % N;
    }

    public int manhattan() {
        int res = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (tiles[i][j] == 0) {
                    continue;
                }
                int dx = Math.abs(index2CordX(tiles[i][j]) - i);
                int dy = Math.abs(index2CordY(tiles[i][j]) - j);
                res += dx + dy;
            }
        }
        return res;
    }

    public int estimatedDistanceToGoal() {
        if (eDist == -1) {
            eDist = manhattan();
        }
        return eDist;
    }

    public boolean equals(Object y) {
        if (y == this) {
            return true;
        }
        if (y.getClass() != this.getClass()) {
            return false;
        }
        Board that = (Board) y;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (this.tiles[i][j] != that.tiles[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    /** Returns the string representation of the board. 
      * Uncomment this method. */
    public String toString() {
        StringBuilder s = new StringBuilder();
        int N = size();
        s.append(N + "\n");
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                s.append(String.format("%2d ", tileAt(i,j)));
            }
            s.append("\n");
        }
        s.append("\n");
        return s.toString();
    }

}
