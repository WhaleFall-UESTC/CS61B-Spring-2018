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
        private Arrange bound;

        private static final int UP = 0;
        private static final int DOWN = 1;
        private static final int LEFT = 2;
        private static final int RIGHT = 3;

        public Link(Arrange a) {
            path = new Stack<>();
            bound = a;
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
                if (px <= bound.left()) {
                    px = bound.left() + 1;
                }
                if (px >= bound.right() - 1) {
                    px = bound.right() - 2;
                }
            } else {
                int dy = py - y;
                int distance = random.nextInt(Math.abs(dy)) + 1;
                py = (dy > 0) ? (py - distance) : (py + distance);
                if (py <= bound.bottom()) {
                    py = bound.bottom() + 1;
                }
                if (py >= bound.top() - 1) {
                    py = bound.top() - 2;
                }
            }

            Position newDest = new Position(px, py);
            Linkto(newDest, random);
        }

        public void LinkToByStep(Position dest, Random random) {
            path.push(dest);
            int px = dest.x;
            int py = dest.y;
            if (px == x && py == y) {
                return;
            }
            Position nextDest;
            do {
                nextDest = nextPos(px, py, random);
            } while (nextDest.equals(path.peek()));
            LinkToByStep(nextDest, random);
        }

        private Position nextPos(int px, int py, Random random) {
            int flag = random.nextInt(3);
            switch (flag) {
                case 0: {
                    if (px > x) {
                        px -= 1;
                        if (px <= bound.left()) {
                            px = bound.left();
                        }
                    } else if (px < x) {
                        px += 1;
                        if (px >= bound.right() - 1) {
                            px = bound.right() - 1;
                        }
                    }
                    break;
                }
                case 1: {
                    py -= 1;
                    if (py <= bound.bottom()) {
                        py = bound.bottom();
                    }
                    break;
                }
                default: {
                    py += 1;
                    if (py >= bound.top() - 1) {
                        py = bound.top() - 1;
                    }
                    break;
                }
            }

            return new Position(px, py);
        }
    }

    public void drawPathTo(TETile[][]world, Position dest, Arrange bound, Random random) {
        Link linker = new Link(bound);
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

    public Stack<Position> getPathByStepTo(Position dest, Arrange bound, Random random) {
        Link linker = new Link(bound);
        linker.LinkToByStep(dest, random);
        return linker.path;
    }

    public Stack<Position> getPathTo(Position dest, Arrange bound, Random random) {
        Link linker = new Link(bound);
        linker.Linkto(dest, random);
        return linker.path;
    }

    public double distanceTo(Position p) {
        int dx = x - p.x;
        int dy = y - p.y;
        return Math.sqrt(dx * dx + dy * dy);
    }

    public boolean isAround(TETile[][] world, TETile t, Position p) {
        int[] variety = new int[]{-1, 0, 1};
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (i == 0 && j == 0) {
                    continue;
                }
                Position pos = new Position(p.x + variety[i], p.y + variety[j]);
                if (pos.isPositionT(world, t) == 1) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean isInArrange(Arrange a) {
        return a.isPointIn(this);
    }

    public int isPositionT(TETile[][] world, TETile t) {
        try {
            if (world[x][y] == t) {
                return 1;
            } else {
                return 0;
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            return -1;
        }
    }
}
