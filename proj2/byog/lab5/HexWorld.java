package byog.lab5;
import org.junit.Test;
import static org.junit.Assert.*;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.util.Random;

/**
 * Draws a world consisting of hexagonal regions.
 */
public class HexWorld {
    private static final int WIDTH = 50;
    private static final int HEIGHT = 50;

    private static final long SEED = 1919810;
    private static final Random RANDOM = new Random(SEED);


    private static void drawMid(TETile[][] world, int x, int y, int s, int width, TETile t) {
        int start = (width - s) / 2;
        for (int i = 0; i < s; i++) {
            world[x + start + i][y] = t;
        }
    }

    public static void addHexagon(TETile[][] world, int x, int y, int s, TETile t) {
        int width = 3 * s - 2;

        for (int i = 0; i < s; i++) {
            drawMid(world, x, y, s + i * 2, width, t);
            y += 1;
        }
        for (int i = s - 1; i >= 0; i--) {
            drawMid(world, x, y, s + i * 2, width, t);
            y += 1;
        }
    }

    private static TETile randomTETile() {
        int flag = RANDOM.nextInt(6);
        switch (flag) {
            case 1: return Tileset.GRASS;
            case 2: return Tileset.MOUNTAIN;
            case 3: return Tileset.SAND;
            case 4: return Tileset.TREE;
            case 5: return Tileset.WATER;
            default: return Tileset.FLOWER;
        }
    }

    private static void drawHexagonInLineY(TETile[][] world, int x, int y, int num, int s) {
        x -= s;
        for (int i = 0; i < num; i++) {
            addHexagon(world, x, y, s, randomTETile());
            y += 2 * s;
        }
    }

    private static void drawHexagonInLineYMiddle(TETile[][] world, int x, int num, int s) {
        int y = (HEIGHT - num * s * 2) / 2;
        drawHexagonInLineY(world, x, y, num, s);
    }

    private static void drawHexagons(TETile[][] world, int maxN, int minN, int s) {
        int x0 = WIDTH / 2;
        int gap = 2 * s - 1;

        for (int n = maxN; n >= minN; n--) {
            drawHexagonInLineYMiddle(world, x0 + (maxN - n) * gap, n, s);
            drawHexagonInLineYMiddle(world, x0 - (maxN - n) * gap, n, s);
        }
    }

    private static void fillRect(TETile[][] world, int x1, int y1, int x2, int y2, TETile t) {
        int xmin = Math.min(x1, x2);
        int xmax = Math.max(x1, x2);
        int ymin = Math.min(y1, y2);
        int ymax = Math.max(y1, y2);

        for (int i = xmin; i <= xmax; i++) {
            for (int j = ymin; j <= ymax; j++) {
                world[i][j] = t;
            }
        }
    }

    private static void fillNothing(TETile[][] world) {
        fillRect(world, 0, 0, WIDTH - 1, HEIGHT - 1, Tileset.NOTHING);
    }

    public static void main(String args[]) {
       TERenderer ter = new TERenderer();
       ter.initialize(WIDTH, HEIGHT);

       TETile[][] hexWorld = new TETile[WIDTH][HEIGHT];
       fillNothing(hexWorld);
       drawHexagons(hexWorld, 5, 3, 4);

       ter.renderFrame(hexWorld);
    }
}
