package byog.Core;

import byog.TileEngine.TETile;

import java.util.Random;

public class Arrange {
    public Position bottomLeft;
    public Position topRight;

    public Arrange(Position bottomLeft, Position topRight) {
        this.topRight = topRight;
        this.bottomLeft = bottomLeft;
    }

    public Arrange(int x1, int y1, int x2, int y2) {
        int xmin = (x1 > x2) ? x2 : x1;
        int xmax = (x1 > x2) ? x1 : x2;
        int ymin = (y1 > y2) ? y2 : y1;
        int ymax = (y1 > y2) ? y1 : y2;

        bottomLeft = new Position(xmin, ymin);
        topRight = new Position(xmax, ymax);
    }

    public int left() {
        return bottomLeft.x;
    }

    public int right() {
        return topRight.x;
    }

    public int bottom() {
        return bottomLeft.y;
    }

    public int top() {
        return topRight.y;
    }

    public void fillWith(TETile[][] world, TETile t) {
        int right = right();
        int top = top();
        for (int i = left(); i < right; i++) {
            for (int j = bottom(); j < top; j++) {
                world[i][j] = t;
            }
        }
    }

    public Position randomPoint(Random random) {
        int offsetX = random.nextInt(right() - left());
        int offsetY = random.nextInt(top() - bottom());
        return new Position(left() + offsetX, bottom() + offsetY);
    }

    public boolean isPointIn(Position p) {
        return (p.x >= left() && p.x < right()) && (p.y >= bottom() && p.y < top());
    }
}
