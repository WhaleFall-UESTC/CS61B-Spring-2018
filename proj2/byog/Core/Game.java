package byog.Core;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;
import byog.lab5.HexWorld;

import java.io.*;

public class Game {
    TERenderer ter = new TERenderer();
    /* Feel free to change the width and height. */
    public static final int WIDTH = 80;
    public static final int HEIGHT = 30;

    /**
     * Method used for playing a fresh game. The game should start from the main menu.
     */
    public void playWithKeyboard() {
    }

    /**
     * Method used for autograding and testing the game code. The input string will be a series
     * of characters (for example, "n123sswwdasdassadwas", "n123sss:q", "lwww". The game should
     * behave exactly as if the user typed these characters into the game after playing
     * playWithKeyboard. If the string ends in ":q", the same world should be returned as if the
     * string did not end with q. For example "n123sss" and "n123sss:q" should return the same
     * world. However, the behavior is slightly different. After playing with "n123sss:q", the game
     * should save, and thus if we then called playWithInputString with the string "l", we'd expect
     * to get the exact same world back again, since this corresponds to loading the saved game.
     * @param input the input string to feed to your program
     * @return the 2D TETile[][] representing the state of the world
     */
    public TETile[][] playWithInputString(String input) {
        // TODO: Fill out this method to run the game using the input passed in,
        // and return a 2D tile representation of the world that would have been
        // drawn if the same inputs had been given to playWithKeyboard().
        Inst inst = instExplainer(input);
        inst.handler();

        TETile[][] finalWorldFrame = new TETile[WIDTH][HEIGHT];
        Generator gen = new Generator(WIDTH, HEIGHT, inst.seed);
        ter.initialize(WIDTH, HEIGHT);

        gen.fillNothing(finalWorldFrame);
        gen.allocateBlocks();
        gen.setBuildingForBlocks(finalWorldFrame);
        gen.connectBuildingsInBlocks(finalWorldFrame);
        gen.buildWall(finalWorldFrame);

        return finalWorldFrame;
    }

    private class Inst{
        private long seed;
        private int op;

        public static final int QUIT = 1;
        public static final int LOAD = 2;

        public Inst(long seed, int op) {
            this.seed = seed;
            this.op = op;
        }

        public void handler() {
            try {
                instHandler();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        private void instHandler() throws IOException {
            switch (op) {
                case QUIT: {
                    File sav = new File("saving.txt");
                    if (!sav.exists()) {
                        sav.createNewFile();
                    }
                    FileOutputStream f = new FileOutputStream(sav);
                    byte[] buf = Long.toString(seed).getBytes();
                    f.write(buf);
                    f.close();
                }
                case LOAD: {
                    File sav = new File("saving.txt");
                    if (!sav.exists()) {
                        throw new FileNotFoundException();
                    }
                    FileInputStream f = new FileInputStream(sav);
                    seed = 0;
                    int len = (int) sav.length();
                    for (int i = 0; i < len; i++) {
                        seed *= 10;
                        seed += ((char) (f.read())) - 48;
                    }
                }
            }
        }
    }

    public Inst instExplainer(String input) {
        input = input.toLowerCase();
        if (input.charAt(0) == 'n') {
            int operator = 0;
            if (input.endsWith(":q")) {
                operator = Inst.QUIT;
            }

            int i;
            for (i = 1; Character.isDigit(input.charAt(i)); i++);
            long num = Long.parseLong(input.substring(1, i));
            return new Inst(num, operator);
        }
        else if (input.contains("l")) {
            return new Inst(0, Inst.LOAD);
        } else {
            return null;
        }
    }

}
