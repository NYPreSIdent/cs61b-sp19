package byow.Core;

import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class Engine {
    TERenderer ter = new TERenderer();
    /* Feel free to change the width and height. */
    public static final int WIDTH = 60;
    public static final int HEIGHT = 60;
    public static final boolean VERTICAL = true;
    public static final boolean PARALLEL = false;
    public static final boolean UP = true;
    public static final boolean DOWN = false;
    public static final boolean LEFT = true;
    public static final boolean RIGHT = false;
    public static final boolean[][] isRoom = new boolean[WIDTH][HEIGHT];
    private List<Room> rooms = new LinkedList<>();
    private HashMap<String, Integer> PRIORITY;

    /**
     * This Engine should initialize the priority of the Tile.
     * These words show that order (from top to down) :
     *  The floor --> WALL --> NOTHING
     */
    public Engine() {
        PRIORITY = new HashMap<>();
        PRIORITY.put(Tileset.NOTHING.toString(), 0);
        PRIORITY.put(Tileset.WALL.toString(), 1);
        PRIORITY.put(Tileset.FLOOR.toString(), 2);
    }

    private class Room {
        private LinkedList<Position> pOfCorner;
        private Position leftUpper;
        private Position rightLower;

        public void add(Position p, boolean isLeftUpper) {
            if (isLeftUpper) {
                this.leftUpper = p;
            } else {
                this.rightLower = p;
            }
        }

        /**
         * return true if this area has been occupied by ROOM.
         * @param p the position of the current file.
         * @return true if this area has been occupied by ROOM.
         */
        public boolean overLap(Position p) {
            int Vx = p.getX();
            int Vy = p.getY();
            boolean isXOverBound = (Vx >= leftUpper.getX()) && (Vx <= rightLower.getX());
            boolean isYOverBound = (Vy >= rightLower.getY()) && (Vy <= leftUpper.getY());
            return isXOverBound && isYOverBound;
        }
    }

    /**
     * Method used for exploring a fresh world. This method should handle all inputs,
     * including inputs from the main menu.
     */
    public void interactWithKeyboard() {
    }

    /**
     * Method used for autograding and testing your code. The input string will be a series
     * of characters (for example, "n123sswwdasdassadwas", "n123sss:q", "lwww". The engine should
     * behave exactly as if the user typed these characters into the engine using
     * interactWithKeyboard.
     *
     * Recall that strings ending in ":q" should cause the game to quite save. For example,
     * if we do interactWithInputString("n123sss:q"), we expect the game to run the first
     * 7 commands (n123sss) and then quit and save. If we then do
     * interactWithInputString("l"), we should be back in the exact same state.
     *
     * In other words, both of these calls:
     *   - interactWithInputString("n123sss:q")
     *   - interactWithInputString("lww")
     *
     * should yield the exact same world state as:
     *   - interactWithInputString("n123sssww")
     *
     * @param input the input string to feed to your program
     * @return the 2D TETile[][] representing the state of the world
     */
    public TETile[][] interactWithInputString(String input) {
        // TODO: Fill out this method so that it run the engine using the input
        // passed in as an argument, and return a 2D tile representation of the
        // world that would have been drawn if the same inputs had been given
        // to interactWithKeyboard().
        //
        // See proj3.byow.InputDemo for a demo of how you can make a nice clean interface
        // that works for many different input types.
        Random rdm = new Random(233);
        int number = rdm.nextInt(30); // the number of the
        TETile[][] finalWorldFrame = new TETile[HEIGHT][WIDTH];
        createNewWorld(finalWorldFrame, HEIGHT, WIDTH);
        Position statusOfP = new Position(rdm.nextInt(10), rdm.nextInt(10));

        for (int i = 0; i < 300; i += 1) {
            int length = rdm.nextInt(10);
            int width = rdm.nextInt(10);
            boolean direction = rdm.nextBoolean();

            statusOfP = DrawARectangularAroundByWALL(finalWorldFrame, length, 1, statusOfP, direction); // a hallway

            DrawARectangularAroundByWALL(finalWorldFrame, length, width, statusOfP, direction); // a room
        }

        return finalWorldFrame;
    }

    private static void createNewWorld(TETile[][] world, int length, int width) {
        for (int w = 0; w < width; w += 1) {
            for (int l = 0; l < length; l += 1) {
                world[w][l] = Tileset.NOTHING;
            }
        }
    }

    /**
     * This method draws one HallWay to two directions: VERTICAL or PARALLEL
     * The length and width are all greater than 1
     * @param world the information used to display.
     * @param length the length of hallway you want to draw.
     * @param width the width of hallway you want to draw.
     *              example:
     *                      length 1:  =========
     *                               [ one blank ]
     *                                 =========
     * @param p the position of the start point.
     * @param direction determines whether the hallway is vertical or parallel
     *                  VERTICAL: means a hall way from top to down.
     *                  PARALLEL: means a hall way from left to right.
     */
    private Position DrawARectangularAroundByWALL(TETile[][] world,
                                    int length,
                                    int width,
                                    Position p,
                                    boolean direction) {
        Position result = p;
        for (int wid = 0; wid < width; wid += 1) {
            for (int len = 0; len < length; len += 1) {
                int thisRow, thisColumn;
                Position track;
                if (direction == PARALLEL) {
                    thisRow = p.getX() + len;
                    thisColumn = p.getY() + wid;
                    track = new Position(thisRow, thisColumn / 2);
                } else {
                    thisRow = p.getX() + wid;
                    thisColumn = p.getY() + len;
                    track = new Position(thisRow / 2, thisColumn);
                }
                result = DrawAHallWithAroundWall(world, track);
            }
        }
        return result;
    }

    private void DrawOneHallway(TETile[][] world, Position p,
                                int length, boolean direction, boolean orient) {
        if (direction == PARALLEL) {
            DrawOneRow(world, p, Tileset.WALL, length, orient);
            DrawOneRow(world, p, Tileset.FLOOR, length, orient);
            DrawOneRow(world, p, Tileset.WALL, length, orient);
        }
        if (direction == VERTICAL) {
            DrawOneColumn(world, p, Tileset.WALL, length, orient);
            DrawOneColumn(world, p, Tileset.FLOOR, length, orient);
            DrawOneColumn(world, p, Tileset.WALL, length, orient);
        }
    }

    private void DrawOneTurn(TETile[][] world, Position p, int size, TETile t,
                               boolean orientFirst, boolean orientSecond, boolean direOfFirst) {
        if (direOfFirst == PARALLEL) {
            DrawOneRow(world, p, t, size, orientFirst);
            int newOutsetOfx = p.getX() + size;
            int newOutsetOfy = p.getY();
            Position newP = new Position(newOutsetOfx, newOutsetOfy);
            DrawOneColumn(world, newP, t, size, orientSecond);
        }
        if (direOfFirst == VERTICAL) {
            DrawOneRow(world, p, t, size, orientFirst);
            int newOutsetOfx = p.getY() + size;
            int newOutsetOfy = p.getX();
            Position newP = new Position(newOutsetOfx, newOutsetOfy);
            DrawOneColumn(world, newP, t, size, orientSecond);
        }
    }

    /**
     *  Draw one column on the screen, if the any file in this Column beyond the bound, then
     *  no effect.
     * @param world the files on the screen.
     * @param p the outset of this column.
     * @param t the texture of this column.
     * @param length the length.
     * @param orientation determines the up or down.
     */
    private void DrawOneColumn(TETile[][] world, Position p, TETile t, int length, boolean orientation) {
        int delta;
        if (orientation == UP) {
            delta = 1;
        } else {
            delta = -1;
        }
        for (int y = 0; y < length; y += delta) {
            int thisRow = p.getX();
            int thisColumn = p.getY() + y;
            if (isOverBound(new Position(thisRow, thisColumn))) {
                return;
            }
        }
        for (int y = 0; y < length; y += delta) {
            int thisRow = p.getX();
            int thisColumn = p.getY() + y;
            world[thisRow][thisColumn] = t;
        }
    }

    // similar to the description above.
    private void DrawOneRow(TETile[][] world, Position p, TETile t, int length, boolean orientation) {
        int delta;
        if (orientation == LEFT) {
            delta = 1;
        } else {
            delta = -1;
        }
        for (int x = 0; x < length; x += delta) {
            int thisRow = p.getX();
            int thisColumn = p.getY() + x;
            if (isOverBound(new Position(thisRow, thisColumn))) {
                return;
            }
        }
        for (int x = 0; x < length; x += delta) {
            int thisRow = p.getX();
            int thisColumn = p.getY() + x;
            world[thisRow][thisColumn] = t;
        }
    }

    /**
     * To determine the position of the file is not beyond the game-play screen.
     * @param p the current position of the file which we want to put.
     * @return true if the position is beyond the screen.
     */
    private boolean isOverBound(Position p) {
        int currX = p.getX();
        int currY = p.getY();
        return (currX > WIDTH - 1) || (currX < 0) || (currY > HEIGHT - 1) || (currY < 0);
    }

    /**
     * checks if the area that we want to occupy is already be filled by ROOM.
     * @param p the position of the left-lower corner in that area
     * @param width the width of the area that we want to fill.
     * @param height the height of the are that we want to fill.
     * @return if there is already a room here, return false, and vice versa.
     */
    private static boolean isOverlap(Position p, int width, int height) {
        int thisRow, thisColumn;
        thisRow = p.x;
        thisColumn = p.y;
        for (int x = 0; x < width; x += 1) {
            for (int y = 0; y < width; y += 1) {
                thisRow += x;
                thisColumn += y;
                if (isRoom[thisRow][thisColumn]) {
                    return true;
                }
            }
        }
        return false;
    }

    public static void main(String[] args) {
        TERenderer render = new TERenderer();
        render.initialize(50, 50);
        Engine eng = new Engine();

        TETile[][] world = new TETile[50][50];
        eng.createNewWorld(world, 50, 50);
        world = eng.interactWithInputString(null);

        render.renderFrame(world);
    }
}
