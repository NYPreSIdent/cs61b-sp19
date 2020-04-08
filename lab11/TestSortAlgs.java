import edu.princeton.cs.algs4.Queue;

import org.junit.Assert;
import org.junit.Test;

import java.util.Comparator;
import java.util.Objects;
import java.util.Random;

public class TestSortAlgs {

    @Test
    public void testQuickSort() {
        Queue<String> tas = CreateNewStringQueue();
        tas = QuickSort.quickSort(tas);
        Assert.assertTrue(isSorted(tas));

        Queue<String> tast = CreateRandomString();
        tast = QuickSort.quickSort(tast);
        Assert.assertTrue(isSorted(tast));
    }

    @Test
    public void testMergeSort() {
        Queue<String> tas = CreateNewStringQueue();
        MergeSort.mergeSort(tas);
        Assert.assertTrue(isSorted(tas));

        Queue<String> tast = CreateRandomString();
        MergeSort.mergeSort(tast);
        Assert.assertTrue(isSorted(tast));
    }

    public Queue<String> CreateNewStringQueue() {
        Queue<String> tas = new Queue<>();
        tas.enqueue("Hello");
        tas.enqueue("the");
        tas.enqueue("world");
        tas.enqueue("yes");
        tas.enqueue("233");
        tas.enqueue("");
        tas.enqueue("hang");
        tas.enqueue("t");
        tas.enqueue("too");
        tas.enqueue("go");
        return tas;
    }

    public Queue<String> CreateRandomString() {
        Queue<String> tas = new Queue<>();
        Random rdm = new Random(20);
        for (int i = 0; i < 1000000; i += 1) {
            tas.enqueue("t" + rdm.ints());
        }
        return tas;
    }

    /**
     * Returns whether a Queue is sorted or not.
     *
     * @param items  A Queue of items
     * @return       true/false - whether "items" is sorted
     */
    private <Item extends Comparable> boolean isSorted(Queue<Item> items) {
        if (items.size() <= 1) {
            return true;
        }
        Item curr = items.dequeue();
        Item prev = curr;
        while (!items.isEmpty()) {
            prev = curr;
            curr = items.dequeue();
            if (curr.compareTo(prev) < 0) {
                return false;
            }
        }
        return true;
    }
}
