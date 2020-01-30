import java.rmi.server.RMIFailureHandler;

public class ArrayDeque {

    /** The starting size of our array list. */
    private int[] items;
    private int size;
    private int first;
    private int last;
    private int factor;
    private final double Rfactor = 0.25;

    public ArrayDeque() {
        items = new int[8];
        size = 0;
        first = items.length / 2;
        last = items.length / 2;
    }

    /** adds item to the front of the list. */
    public void addFirst(int item) {
        first -= 1;
        if (isReachtheEnd(first)) {
            first = items.length - 1;
        }
        if (isOverLap()) {
            resize();
        }
        items[first] = item;
        size += 1;
    }

    /** adds item to the back of the list. */
    public void addLast(int item) {
        last += 1;
        if (isReachtheEnd(last)) {
           last = 0;
        }
        if (isOverLap()) {
            resize();
        }
        items[last] = item;
        size += 1;
    }

    /** removes first item from the list, */
    public void revomeFirst() {
        first += 1;
        size -= 1;
        if (isReachtheEnd(first)) {
            first = 0;
        }
        if (isOverSize() || isOverLap()) {
            resize();
        }
    }

    /** removes last item from the list. */
    public void removeLast() {
        last -= 1;
        size -= 1;
        if (isReachtheEnd(last)) {
            last = items.length - 1;
        }
        if (isOverSize() || isOverLap()) {
            resize();
        }
    }

    public int getFirst() {
        return items[first];
    }

    public int getLast() {
        return items[last];
    }

    /** returns the size of the list. */
    public int size() {
        return size;
    }

    /** resizing the list. */
    private void resize() {
        int length = size / (factor * 2);
        int[] result = new int[length];
        if (isOverLap()) {
            System.arraycopy(items, 0, result, items.length / 2, size);
            first = items.length / 2;
            last = first + size;
        } else if (first < last) {
            System.arraycopy(items, first, result, first, size);
        } else {
            System.arraycopy(items, 0, result, 0, last + 1);
            System.arraycopy(items, first, result, length - (last + 1), size - (last + 1));
            first = length - (last + 1);
        }
        items = result;
    }

    /** There are three meanings:
     *  1. first or last was over the bound.
     *  2. first equals to last.
     *  3. size is larger than 16 and usage factor should less than 0.25.
     */
    private boolean isOverSize() {
        factor = size / items.length;
        return (factor < Rfactor) && (items.length >= 16);
    }

    private boolean isReachtheEnd(int index) {
        return index < 0 || index >= items.length;
    }

    private boolean isOverLap() {
        return first == last;
    }


}
