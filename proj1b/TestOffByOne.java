import org.junit.Test;
import static org.junit.Assert.*;

public class TestOffByOne {
    // You must use this CharacterComparator and not instantiate
    // new ones, or the autograder might be upset.
    static CharacterComparator offByOne = new OffByOne();

    // Your tests go here.
    @Test
    public void TestOffByOne() {
        boolean actual1 = offByOne.equalChars('a', 'b');
        boolean actual2 = offByOne.equalChars('%', '&');
        boolean actual3 = offByOne.equalChars('z', 'z');
        boolean actual4 = offByOne.equalChars('z', 'a');

        assertTrue(actual1);
        assertTrue(actual2);
        assertFalse(actual3);
        assertFalse(actual4);
    }
}