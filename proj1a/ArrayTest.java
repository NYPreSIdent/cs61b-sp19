import static org.junit.Assert.*;
import org.junit.Test;

public class ArrayTest {

    @Test
    public void addFirstTest() {
        ArrayDeque<Integer> result = new ArrayDeque<>();
        int expectedSize = 6;
        Integer expected = 6;

        result.addFirst(1);
        result.addFirst(2);
        result.addFirst(3);
        result.addFirst(4);
        result.addFirst(5);
        result.addFirst(6);

        assertEquals(expectedSize, result.size());
        assertEquals(expected, result.getFirst());
    }

    @Test
    public void addLastTest() {
        ArrayDeque<Integer> result = new ArrayDeque<>();
        int expectedSize = 6;
        Integer expected = 6;

        result.addLast(1);
        result.addLast(2);
        result.addLast(3);
        result.addLast(4);
        result.addLast(5);
        result.addLast(6);

        assertEquals(expectedSize, result.size());
        assertEquals(expected, result.getLast());
    }

    /** To test the list is or not empty. */
    @Test
    public void testIsEmpty() {
        ArrayDeque
    }
}
