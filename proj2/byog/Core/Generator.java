package byog.Core;

import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;
import edu.princeton.cs.algs4.Stack;

import java.awt.image.WritableRenderedImage;
import java.util.Random;

public class Generator {
    private int width;
    private int height;

    private int xxLen;
    private int yyLen;
    private Block[][] blocks;
    private Arrange blocksBound, worldBound;

    public Random random;

    public Generator(int width, int height, long seed) {
        this.width = width;
        this.height = height;
        worldBound = new Arrange(0, 0, width, height);

        assert width % Block.BLOCK_LENGHT == 0;
        assert height % Block.BLOCK_LENGHT == 0;
        xxLen = width / Block.BLOCK_LENGHT;
        yyLen = height / Block.BLOCK_LENGHT;
        blocksBound = new Arrange(0, 0, xxLen, yyLen);

        blocks = new Block[xxLen][yyLen];
        random = new Random(seed);
    }

    private void setBlock(int i, int j) {
        int x1 = i * Block.BLOCK_LENGHT;
        int y1 = j * Block.BLOCK_LENGHT;
        int x2 = x1 + Block.BLOCK_LENGHT;
        int y2 = y1 + Block.BLOCK_LENGHT;

        if (i == 0) {
            x1 += 1;
        }
        if (i == xxLen - 1) {
            x2 -= 1;
        }
        if (j == 0) {
            y1 += 1;
        }
        if (j == yyLen - 1) {
            y2 -= 1;
        }

        blocks[i][j] = new Block(x1, y1, x2, y2);
    }

    public void allocateBlocks() {
        for (int i = 0; i < xxLen; i++) {
            for (int j = 0; j < yyLen; j++) {
                setBlock(i, j);
            }
        }
    }

    public void fillRect(TETile[][] world, TETile t, int x1, int y1, int x2, int y2) {
        Arrange rect = new Arrange(x1, y1, x2, y2);
        rect.fillWith(world, t);
    }

    public void fillNothing(TETile[][] world) {
        fillRect(world, Tileset.NOTHING, 0, 0, width, height);
    }

    public void fillAllBlocks(TETile[][] world, TETile t) {
        for (int i = 0; i < xxLen; i++) {
            for (int j = 0; j < yyLen; j++) {
                blocks[i][j].fillBlock(world, t);
            }
        }
    }

    public void setBuildingForBlocks(TETile[][] world) {
//        int pathWidth = (int) Math.round(xxLen * (random.nextDouble(0.65) + 0.35));
//        int lBound = random.nextInt(xxLen / 5) + 1;
//        int rBound = (lBound + pathWidth >= xxLen) ? xxLen - 1 : lBound + pathWidth;
//        Arrange left = new Arrange(0, 0, lBound, yyLen);
//        Arrange right = new Arrange(rBound, 0, xxLen, yyLen);
//        Position p1 = left.randomPoint(random);
//        Position p2 = right.randomPoint(random);
//        setBuildingByPoints(world, p1, p2, blocksBound);
//        blocks[p1.x][p1.y].GenerateBuilding(world, random);
        for (int i = 0; i < xxLen; i++) {
            for(int j = 0; j < yyLen; j++) {
                blocks[i][j].GenerateBuilding(random);
            }
        }
        for (int j = 0; j < yyLen; j++) {
            if (random.nextDouble() >= 0.52) {
                blocks[0][j].destoryBuilding();
            }
        }
        for (int i = 1; i < xxLen; i++) {
            for (int k = 0; k < random.nextInt(yyLen); k++) {
                int indexY = random.nextInt(yyLen);
                blocks[i][indexY].destoryBuilding();
                if (isBlockAlone(i - 1, indexY)) {
                    blocks[i][indexY].GenerateBuilding(random);
                }
            }
        }
        for (int j = 0; j < yyLen; j++) {
            if (isBlockAlone(xxLen - 1, j)) {
                blocks[xxLen - 1][j].destoryBuilding();
            }
        }
        for (int i = 0; i < xxLen; i++) {
            for (int j = 0; j < yyLen; j++) {
                blocks[i][j].drawBuilding(world);
            }
        }
    }

    public boolean isBlockAlone(int i, int j) {
        boolean l = false, r = false, t = false, b = false;
        if (i >= 1) {
            l = blocks[i - 1][j].hasBuilding();
        }
        if (i <= xxLen - 2) {
            r = blocks[i + 1][j].hasBuilding();
        }
        if (j >= 1) {
            b = blocks[i][j - 1].hasBuilding();
        }
        if (j <= yyLen - 2) {
            t = blocks[i][j + 1].hasBuilding();
        }
        return !(l || r || t || b);
    }

    private void setBuildingByPoints(TETile[][] world ,Position p1, Position p2, Arrange bound) {
        Stack<Position> path = p1.getPathByStepTo(p2, bound, random);
        while (!path.isEmpty()) {
            Position p = path.pop();
            blocks[p.x][p.y].GenerateBuilding(random);
        }
    }


    public void connectBuildingsInBlocks(TETile[][] world) {
        connectBlocksRecursive(world, 0, 0);
    }

    private void connectBlocksRecursive(TETile[][] world, int i, int j) {
        if (i >= xxLen || j >= yyLen) {
            return;
        }
        if (i + 1 <= xxLen - 1) {
            blocks[i][j].connectToBlock(world, blocks[i + 1][j], worldBound, random);
        }
        if (j + 1 <= yyLen - 1) {
            blocks[i][j].connectToBlock(world, blocks[i][j + 1], worldBound, random);
        }
        connectBlocksRecursive(world, i + 1, j);
        connectBlocksRecursive(world, i, j + 1);
    }

    public void buildWall(TETile[][] world) {
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if (world[i][j] == Tileset.NOTHING) {
                    Position p = new Position(i, j);
                    if (p.isAround(world, Tileset.FLOOR, p)) {
                        world[i][j] = Tileset.WALL;
                    }
                }
            }
        }
    }
}
