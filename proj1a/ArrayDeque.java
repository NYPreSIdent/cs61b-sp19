import java.rmi.server.RMIFailureHandler;

public class ArrayDeque<Blorp> {

    /** The starting size of our array list. */
    private Blorp[] items;
    private int size;
    private int first;
    private int last;
    private int factor;
    private final double Rfactor = 0.25;

    public ArrayDeque() {
        items = (Blorp[]) new Object[8];
        size = 0;
        first = items.length / 2;
        last = items.length / 2;
    }

    /** adds item to the front of the list. */
    public void addFirst(Blorp item) {
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
    public void addLast(Blorp item) {
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
        items[first] = null;
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
        items[last] = null;
        last -= 1;
        size -= 1;
        if (isReachtheEnd(last)) {
            last = items.length - 1;
        }
        if (isOverSize() || isOverLap()) {
            resize();
        }
    }

    public Blorp getFirst() {
        return items[first];
    }

    public Blorp getLast() {
        return items[last];
    }

    /** returns the size of the list. */
    public int size() {
        return size;
    }

    /** resizing the list. */
    private void resize() {

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
