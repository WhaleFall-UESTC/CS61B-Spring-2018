package byog.Core;

import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;
import edu.princeton.cs.algs4.Stack;

import java.util.ArrayList;
import java.util.Random;

public class Position {
    public int x;
    public int y;

    public Position() {
        x = 0;
        y = 0;
    }

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public boolean equals(Position p) {
        return (p.x == x) && (p.y == y);
    }

    private class Link {
        public Stack<Position> path;

        private static final int UP = 0;
        private static final int DOWN = 1;
        private static final int LEFT = 2;
        private static final int RIGHT = 3;

        public Link() {
            path = new Stack<>();
        }

        public void Linkto(Position dest, Random random) {
            path.push(dest);
            if (x == dest.x && y == dest.y) {
                return;
            }
            int px = dest.x;
            int py = dest.y;

            if (px == x) {
                py = y;
            } else if (py == y) {
                px = x;
            } else if (random.nextBoolean()) {
                int dx = px - x;
                int distance = random.nextInt(Math.abs(dx)) + 1;
                px = (dx > 0) ? (px - distance) : (px + distance);
                if (px <= 0) {
                    px = 1;
                }
                if (px >= Game.WIDTH - 1) {
                    px = Game.WIDTH - 2;
                }
            } else {
                int dy = py - y;
                int distance = random.nextInt(Math.abs(dy)) + 1;
                py = (dy > 0) ? (py - distance) : (py + distance);
            }

            Position newDest = new Position(px, py);
            Linkto(newDest, random);
        }
    }

    public void drawPathTo(TETile[][]world, Position dest, Random random) {
        Link linker = new Link();
        linker.Linkto(dest, random);
        while (true) {
            Position p1 = linker.path.pop();
            if (linker.path.isEmpty()) {
                return;
            }
            Position p2 = linker.path.peek();
            drawBetween(world, Tileset.FLOOR, p1, p2);
        }
    }

    public static void drawBetween(TETile[][] world, TETile t, Position p1, Position p2) {
        assert p1.x == p2.x || p1.y == p2.y;
        if (p1.x == p2.x) {
            int yHigh = (p1.y > p2.y) ? p1.y : p2.y;
            int yLow = (p1.y < p2.y) ? p1.y : p2.y;
            for (int j = yLow; j <= yHigh; j++) {
                world[p1.x][j] = t;
            }
        } else if (p1.y == p2.y) {
            int xRight = (p1.x > p2.x) ? p1.x : p2.x;
            int xLeft = (p1.x < p2.x) ? p1.x : p2.x;
            for (int i = xLeft; i <= xRight; i++) {
                world[i][p1.y] = t;
            }
        }
    }

    public double distanceTo(Position p) {
        int dx = x - p.x;
        int dy = y - p.y;
        return Math.sqrt(dx * dx + dy * dy);
    }
}
