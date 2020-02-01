import static org.junit.Assert.*;
import org.junit.Test;

public class ArrayTest {

    @Test
    public void addFirstTest() {
        ArrayDeque result = new ArrayDeque();
        int expectedSize = 6;
        int expected = 6;

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
        ArrayDeque result = new ArrayDeque();
        int expectedSize = 6;
        int expected1 = 6;
        int expected2 = 1;

        result.addLast(1);
        result.addLast(2);
        result.addLast(3);
        result.addLast(4);
        result.addLast(5);
        result.addLast(6);

        assertEquals(expectedSize, result.size());
        assertEquals(expected1, result.getLast());
        assertEquals(expected2, result.getFirst());
    }

    /** To test the list is or not empty. */
    @Test
    public void testIsEmpty() {
        ArrayDeque result = new ArrayDeque();

        boolean expected1 = result.isEmpty();
        assertTrue(expected1);

        result.addFirst(1);
        result.addLast(2);
        boolean expected2 = result.isEmpty();
        assertFalse(expected2);

        result.removeLast();
        result.revomeFirst();
        boolean expected3 = result.isEmpty();
        assertTrue(expected3);
    }

    /** To test some get methods in some comditions extremely. */
    @Test
    public void GeTest() {
       ArrayDeque result = new ArrayDeque();
        for (int i = 1; i <= 6; i += 1) {
            result.addFirst(i);
        }

        int expected1 = 1;
        assertEquals(expected1, result.getLast());

        int expected2 = 1;
        assertEquals(expected2, result.get(5));

        int expected3 = 6;
        assertEquals(expected3, result.getFirst());

        int expected4 = result.get(0); // may be 6;
        assertEquals(expected4, result.getFirst());

        result.removeLast();
        result.removeLast();
        result.revomeFirst();
        int expected5 = 3;
        assertEquals(expected5, result.get(2));

        assertEquals(result.getLast(), result.get(2));
    }

    @Test
    public void ResizeTest() {
        // OverLap to Resize.
       ArrayDeque result = new ArrayDeque();

        for (int i = 1; i <= 10; i += 1) {
            result.addFirst(i);
        }

        // Test first item.
        int expected1 = 10;
        assertEquals(expected1, result.getFirst());

        // Test last item.
        int expected2 = 1;
        assertEquals(expected2, result.getLast());

        // Test get last item.
        int expected3 = 1;
        assertEquals(expected3, result.get(9));

        // Test size.
        int size = 10;
        assertEquals(size, result.size());

        // Test remove.
        for (int j = 0; j < 11; j += 1) {
            result.revomeFirst();
        }

        assertTrue(result.isEmpty());
    }

    /** Shrink the size when the factor is large enough. */
    @Test
    public void shrinkTest() {
        ArrayDeque result = new ArrayDeque();
        for (int i = 1; i <= 1000; i += 1) {
            result.addLast(i);
        }
        for (int count = 0; count < 45; count += 1) {
            result.revomeFirst();
        }

        int expected = 48;
        int actual = result.get(2);
        assertEquals(expected, actual);
    }

}
