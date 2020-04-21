package byow.lab12;
import edu.princeton.cs.algs4.Transaction;
import org.junit.Test;
import static org.junit.Assert.*;

import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

import javax.swing.text.Position;
import java.util.Random;

/**
 * Draws a world consisting of hexagonal regions.
 */
public class HexWorld {

    public static Random RANDOM = new Random(300);
    public position pos = new position(12, 12);

    private class position {
        int x;
        int y;

        public position(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    public void DrawHexagonWorld(TETile[][] world, position p, int size) {
        int width = 2 * size - 1;
        for (int x = 0; x < width; x += 1) {
            int thisColumnX = p.x + width * x;
            int thisColumnY = p.y - offsetOfy(size, x);
            position newP = new position(thisColumnX, thisColumnY);
            int height = ColumnLength(size, x);
            DrawOneColumn(world, newP, height, size);
        }
    }

    public void DrawOneHexagon(TETile[][] world,
                               position p,
                               int size,
                               TETile t) {
        int height = 2 * size;
        for (int vy = 0; vy < height; vy += 1) {
            int thisRowY = p.y + vy;
            int startOfX = p.x + offsetOfx(size, vy);

            int hexLen = hexLength(size, vy);

            position start = new position(startOfX, thisRowY);

            DrawOneRow(world, start, hexLen, t);
        }
    }

    private static int ColumnLength(int s, int i) {
        int effectiveI = i;
        if (effectiveI >= s) {
            effectiveI = 2 * s - 2 - effectiveI;
        }
        return effectiveI + s;
    }

    private static void DrawOneRow(TETile[][] world, position p, int len, TETile t) {
        for (int x = 0; x < len; x += 1) {
            int xCoord = p.x + x;
            int yCoord = p.y;
            world[xCoord][yCoord] = t;
        }
    }

    private void DrawOneColumn(TETile[][] world, position p, int height, int size) {
        for (int y = 0; y < height; y += 1) {
            int xCoord = p.x;
            int yCoord = p.y + (2 * size);
            p = new position(xCoord, yCoord);
            TETile t;
            Random rdm = new Random();
            int value = rdm.nextInt(6);
            switch (value) {
                case 0: t = Tileset.WALL; break;
                case 1: t = Tileset.FLOWER; break;
                case 2: t = Tileset.WATER; break;
                case 3: t = Tileset.GRASS; break;
                case 4: t = Tileset.SAND; break;
                case 5: t = Tileset.FLOOR; break;
                default: t = Tileset.MOUNTAIN;
            }
            DrawOneHexagon(world, p, size, t);
        }
    }

    private static int hexLength(int s, int i) {
        int effectiveI = i;
        if (i >= s) {
            effectiveI = 2 * s - 1 - effectiveI;
        }
        return s + 2 * effectiveI;
    }

    /**
     *
     * @param s the size of the hexagon.
     * @param vy the position on the y axes.
     * @return
     */
    private static int offsetOfx(int s, int vy) {
        int absolutevy = vy;
        if (absolutevy >= s) {
            absolutevy = 2 * s - 1 - absolutevy;
        }
        return -absolutevy;
    }

    private static int offsetOfy(int s, int x) {
        int effectiveX = x;
        if (effectiveX >= s) {
            effectiveX = 2 * s - 2 - effectiveX;
        }
        return effectiveX * s;
     }

    public static void main(String[] args) {
        HexWorld hexworld = new HexWorld();
        TERenderer render = new TERenderer();
        render.initialize(50, 50);

        TETile[][] world = new TETile[200][200];
        for (int x = 0; x < 200; x += 1) {
            for (int y = 0; y < 200; y += 1) {
                world[x][y] = Tileset.NOTHING;
            }
        }
        hexworld.DrawHexagonWorld(world, hexworld.pos, 2);

        render.renderFrame(world);
    }
}
