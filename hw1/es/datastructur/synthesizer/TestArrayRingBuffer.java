package es.datastructur.synthesizer;
import edu.princeton.cs.algs4.In;
import org.junit.Test;
import static org.junit.Assert.*;

/** Tests the ArrayRingBuffer class.
 *  @author Josh Hug
 */

public class TestArrayRingBuffer {
    @Test
    public void someTest() {
        ArrayRingBuffer<Integer> arb = new ArrayRingBuffer<>(10);
        for (int i = 0; i < 10; i += 1) {
            arb.enqueue(i);
        }

        /* Test1 */
        //assertTrue(arb.isFull());
        assertEquals(Integer.valueOf(0), arb.peek());
        arb.dequeue();
        arb.dequeue();
        assertEquals(Integer.valueOf(2), arb.peek());
        assertEquals(Integer.valueOf(3), arb.dequeue());

    /*
        for (int j = 0; j < 10; j += 1) {
            arb.dequeue();
        }
        assertTrue(arb.isEmpty()); */
    }
}
