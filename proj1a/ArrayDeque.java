public class ArrayDeque<T> {

    /** The starting size of our array list. */
    private T[] items;
    private int size;
    private int first;
    private int last;
    private double factor;
    private final double Rfactor = 0.25;

    public ArrayDeque() {
        items = (T[]) new Object[8];
        size = 0;
        first = items.length / 2 + 1;
        last = items.length / 2;
    }

    /** adds item to the front of the list. */
    public void addFirst(T item) {
        first -= 1;
        size += 1;
        if (isReachtheEnd(first)) {
            first = items.length - 1;
        }
        if (isOverLap()) {
            enlarge();
        }
        items[first] = item;
    }

    /** adds item to the back of the list. */
    public void addLast(T item) {
        last += 1;
        size += 1;
        if (isReachtheEnd(last)) {
           last = 0;
        }
        if (isOverLap()) {
            enlarge();
        }
        items[last] = item;
    }

    /** removes first item from the list, */
    public T removeFirst() {
        if (!isEmpty()) {
            T result = items[first];
            items[first] = null;
            first += 1;
            size -= 1;
            if (isReachtheEnd(first)) {
                first = 0;
            }
            if (isOverSize()) {
                shrink();
            } else if (isOverLap()) {
                enlarge();
            }
            return result;
        }
        return null;
    }

    /** removes last item from the list. */
    public T removeLast() {
        if (!isEmpty()) {
            T result = items[last];
            items[last] = null;
            last -= 1;
            size -= 1;
            if (isReachtheEnd(last)) {
                last = items.length - 1;
            }
            if (isOverSize()) {
                shrink();
            } else if (isOverLap()) {
                enlarge();
            }
            return result;
        }
        return null;
    }

    /** Gets ith item from the list. */
    public T get(int i) {
        if (first + i >= items.length) {
            int index = i + first - items.length;
            if (index <= last) {
                return items[index];
            } else {
                return null;
            }
        } else {
            return items[i + first];
        }
    }

    /** returns the size of the list. */
    public int size() {
        return size;
    }

    /** resizing the list. */
    private void enlarge() {
        int length = items.length * 2;
        T[] result = (T []) new Object[length];
        System.arraycopy(items, first, result, length / 4, items.length - first);
        System.arraycopy(items, 0, result, length / 4 + size - first - 1, last + 1);
        items = result;
        first = length / 4;
        last = length / 4 + size - 1;
    }

    private void shrink() {
        if (first < last) {
            int length = size * 3;
            T[] result = (T []) new Object[length];
            System.arraycopy(items, first, result, items.length / 3, size);
            first = items.length / 3;
            last = size + first - 1;
            items = result;
        } else {
            int length = size * 2;
            T[] result = (T []) new Object[length];
            System.arraycopy(items, first, result, length / 4, items.length - first);
            System.arraycopy(items, 0, result,  length / 4 + size - last - 1, last + 1);
            first = length / 4;
            last = first + size - 1;
            items = result;
        }
    }

    /** There are three meanings:
     *  1. first or last was over the bound.
     *  2. first equals to last.
     *  3. size is larger than 16 and usage factor should less than 0.25.
     */
    private boolean isOverSize() {
        factor = Double.valueOf(size) / Double.valueOf(items.length);
        return (factor < Rfactor) && (items.length >= 16);
    }

    private boolean isReachtheEnd(int index) {
        return index < 0 || index >= items.length;
    }

    private boolean isOverLap() {
        return (first == last) && (size != 1);
    }

    public boolean isEmpty() {
        return (items[first] == null) || (items[last] == null);
    }
    
    public void printDeque() {
        for (int i = 0; i < size; i++) {
            System.out.print(get(i) + " ");
        }
        System.out.println();
    }
}
