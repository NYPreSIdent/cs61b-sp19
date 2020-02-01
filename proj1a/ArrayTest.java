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
        Integer expected1 = 6;
        Integer expected2 = 1;

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
        ArrayDeque<String> result = new ArrayDeque<>();

        boolean expected1 = result.isEmpty();
        assertTrue(expected1);

        result.addFirst("the world!");
        result.addLast("Hello ");
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
        ArrayDeque<Integer> result = new ArrayDeque<>();
        for (int i = 1; i <= 6; i += 1) {
            result.addFirst(i);
        }

        Integer expected1 = 1;
        assertEquals(expected1, result.getLast());

        Integer expected2 = 1;
        assertEquals(expected2, result.get(5));

        Integer expected3 = 6;
        assertEquals(expected3, result.getFirst());

        Integer expected4 = result.get(0); // may be 6;
        assertEquals(expected4, result.getFirst());

        result.removeLast();
        result.removeLast();
        result.revomeFirst();
        Integer expected5 = 3;
        assertEquals(expected5, result.get(2));

        assertEquals(result.getLast(), result.get(2));
    }

    @Test
    public void ResizeTest() {
        // OverLap to Resize.
        ArrayDeque<Integer> result = new ArrayDeque<>();

        for (int i = 1; i <= 10; i += 1) {
            result.addFirst(i);
        }

        // Test first item.
        Integer expected1 = 10;
        assertEquals(expected1, result.getFirst());

        // Test last item.
        Integer expected2 = 1;
        assertEquals(expected2, result.getLast());

        // Test get last item.
        Integer expected3 = 1;
        assertEquals(expected3, result.get(9));

        // Test size.
        int size = 10;
        assertEquals(size, result.size());

        // Test remove.
        for (int j = 0; j < 11; j += 1) {
            result.revomeFirst();
        }

        assertTrue(result.isEmpty());
        assertNull(result.getFirst());
        assertNull(result.getLast());
        assertNull(result.get(0));
    }

    /** Shrink the size when the factor is large enough. */
    @Test
    public void shrinkTest() {
        ArrayDeque<Integer> result = new ArrayDeque<>();
        for (int i = 1; i <= 1000; i += 1) {
            result.addLast(i);
        }
        for (int count = 0; count < 45; count += 1) {
            result.revomeFirst();
        }

        Integer expected = 48;
        Integer actual = result.get(2);
        assertEquals(expected, actual);
    }

}
