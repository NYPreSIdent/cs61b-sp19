public class LinkedListDeque<T> {
    private IntNode sentinel;
    private int size;

    /** Provided simple recursive LinkedList. */
    private class IntNode {
        private T item;
        private IntNode next;
        private IntNode prev;

        private IntNode(T item){
            this.item = item;
        }
    }

    public LinkedListDeque() {
        sentinel = new IntNode(null);
        sentinel.prev = sentinel;
        sentinel.next = sentinel;
        size = 0;
    }

    public LinkedListDeque(LinkedListDeque<T> other) {
        sentinel = new IntNode(null);
        sentinel.prev = sentinel;
        sentinel.next = sentinel;
        size = 0;

        for (int i = 0; i < other.size(); i += 1) {
            addLast(other.get(i));
            size += 1;
        }
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return (sentinel.prev == sentinel.next) && (size == 0);
    }

    /** Adds an item to the front of the deque. */
    public void addFirst(T item) {
        IntNode nextIntNode = new IntNode(item);
        sentinel.next.prev = nextIntNode;
        nextIntNode.prev = sentinel;
        nextIntNode.next = sentinel.next;
        sentinel.next = nextIntNode;
        size += 1;
    }

    /** Adds an item to the back of the deque. */
    public void addLast(T item) {
        IntNode prevIntNode = new IntNode(item);
        sentinel.prev.next = prevIntNode;
        prevIntNode.prev = sentinel.prev;
        prevIntNode.next = sentinel;
        sentinel.prev = prevIntNode;
        size += 1;
    }

    /** Removes the item at the front of the deque. */
    public T removeFirst() {
        if (this.isEmpty()) {
            return null;
        }
        T result = sentinel.next.item;
        sentinel.next = sentinel.next.next;
        sentinel.next.prev = sentinel;
        size -= 1;
        return result;
    }

    /** Removes the item at the back of the deque. */
    public T removeLast() {
        if (this.isEmpty()) {
            return null;
        }
        T result = sentinel.prev.item;
        sentinel.prev = sentinel.prev.prev;
        sentinel.prev.next = sentinel;
        size -= 1;
        return result;
    }

    /** Gets the item at given index. */
    public T get(int index) {
        if (index >= size) {
            return null;
        }
        IntNode p = sentinel.next;
        while (index != 0) {
            p = p.next;
        }
        return p.item;
    }

    /** Recursive version of Get method. */
    public T getRecursive(int index) {
        if (index < 0) {
            return null;
        } else if (index == 0) {
            return this.sentinel.next.item;
        } else {
            return this.getRecursive(index - 1);
        }
    }

    /** Prints all the elements of the Deque. */
    public void printDeque() {
        IntNode P = sentinel.next;
        while (P.item != null) {
            System.out.print(P.item + " ");
            P = P.next;
        }
        System.out.println();
    }
}