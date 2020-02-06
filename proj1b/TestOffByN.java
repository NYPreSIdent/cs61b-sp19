import org.junit.Test;
import static org.junit.Assert.*;

public class TestOffByN {
    CharacterComparator cc = new OffByN(2);

    @Test
    public void TestOffByN() {
        assertTrue(cc.equalChars('a', 'c'));
    }
}
