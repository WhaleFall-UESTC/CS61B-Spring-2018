package byog.Core;

import byog.TileEngine.TETile;

import java.util.Random;

public class Block {
    private Arrange arrange;
    public static final int BLOCK_LENGHT = 10;
    private Building building;

    public void init(Arrange arrange) {
        this.arrange = arrange;
    }

    public Block(int x1, int y1, int x2, int y2) {
        init(new Arrange(x1, y1, x2, y2));
    }
    public Block(Arrange arrange) { this.arrange = arrange; }
    public Block(Position bottomLeft, Position topRight) {
        init(new Arrange(bottomLeft, topRight));
    }

    public void fillRect(TETile[][] world, TETile t, int x1, int y1, int x2, int y2) {
        Arrange rect = new Arrange(x1, y1, x2, y2);
        rect.fillWith(world, t);
    }

    public void fillBlock(TETile[][] world, TETile t) {
        arrange.fillWith(world, t);
    }

    public void GenerateBuilding(TETile[][] world, Random random) {
        building = new Building(this);
        building.generate(world, random);
    }

    public int left() {
        return arrange.left();
    }
    public int bottom() {
        return arrange.bottom();
    }
    public int right() {
        return arrange.right();
    }
    public int top() { return arrange.top(); }

    public void connectToBlock(TETile[][] world, Block other, Random random) {
        building.connectTo(world, other.building, random);
    }
}
