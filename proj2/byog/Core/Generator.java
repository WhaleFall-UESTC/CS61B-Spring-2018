package byog.Core;

import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.util.Random;

public class Generator {
    private int width;
    private int height;

    private int xxLen;
    private int yyLen;
    private Block[][] blocks;

    public Random random;

    public Generator(int width, int height, long seed) {
        this.width = width;
        this.height = height;

        assert width % Block.BLOCK_LENGHT == 0;
        assert height % Block.BLOCK_LENGHT == 0;
        xxLen = width / Block.BLOCK_LENGHT;
        yyLen = height / Block.BLOCK_LENGHT;

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
        for (int i = 0; i < xxLen; i++) {
            for (int j = 0; j < yyLen; j++) {
                blocks[i][j].GenerateBuilding(world, random);
            }
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
            blocks[i][j].connectToBlock(world, blocks[i + 1][j], random);
        }
        if (j + 1 <= yyLen - 1) {
            blocks[i][j].connectToBlock(world, blocks[i][j + 1], random);
        }
        connectBlocksRecursive(world, i + 1, j);
        connectBlocksRecursive(world, i, j + 1);
    }
}
