package byow.Core.Test;

import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;
import byow.lab12.HexWorld;
import org.junit.Test;

public class EngineTest {
    private TETile[][] createOneWorld() {
        TERenderer render = new TERenderer();
        render.initialize(80, 80);

        TETile[][] world = new TETile[200][200];
        for (int x = 0; x < 200; x += 1) {
            for (int y = 0; y < 200; y += 1) {
                world[x][y] = Tileset.NOTHING;
            }
        }

        return world;
    }

    @Test
    public void TestOneRow() {

    }
}
