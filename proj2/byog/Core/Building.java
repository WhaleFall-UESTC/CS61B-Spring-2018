package byog.Core;

import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.util.Random;

public class Building {
    protected Block block;
    protected Position[] joints;

    private Arrange arrange;

    public Building(Block block) {
        this.block = block;
    }

    public Building(Arrange arrange) {
        this.block = new Block(arrange);
    }

    public void generate(Random random) {
        Position startPoint = randomStart(random);
        RandomArrangeFromPoint(startPoint, random);
        selectJoints(random);
    }

    protected Position randomStart(Random random) {
        int half = Block.BLOCK_LENGHT / 2;
        int width = random.nextInt(half) + 2;
        int height = random.nextInt(half) + 2;

        int offsetX = random.nextInt(width);
        int offsetY = random.nextInt(height);

        return new Position(block.left() + offsetX, block.bottom() + offsetY);
    }

    protected Position saferTopRight(int x, int y) {
        if (x >= block.right()) {
            x = block.right() - 1;
        }
        if (y >= block.top()) {
            y = block.top() - 1;
        }
        return new Position(x, y);
    }

    protected void RandomArrangeFromPoint(Position start, Random random) {
        int half = Block.BLOCK_LENGHT / 2;
        int width = random.nextInt(half) + 3;
        int height = random.nextInt(half) + 3;
        Position p = saferTopRight(start.x + width, start.y + height);
        arrange = new Arrange(start, p);
    }

    protected void selectJoints(Random random) {
        int num = random.nextInt(2) + 1;
        joints = new Position[num];
        for (int i = 0; i < num; i++) {
            joints[i] = arrange.randomPoint(random);
        }
    }

    protected void drawInWorld(TETile[][] world) {
        arrange.fillWith(world, Tileset.FLOOR);
    }

    public void connectTo(TETile[][] world, Building other, Arrange bound, Random random) {
        Position start = choiceAJoint(random);
        Position dest = other.choiceAJoint(random);
        start.drawPathTo(world, dest, bound, random);
    }

    protected Position choiceAJoint(Random random) {
        int index = random.nextInt(joints.length);
        return joints[index];
    }
}
