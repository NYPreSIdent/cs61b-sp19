package es.datastructur.synthesizer;
import org.knowm.xchart.style.markers.Plus;

import java.util.Iterator;

//TODO: Make sure to that this class and all of its methods are public
//TODO: Make sure to add the override tag for all overridden methods
//TODO: Make sure to make this class implement BoundedQueue<T>

public class ArrayRingBuffer<T> implements BoundedQueue<T> {
    /* Index for the next dequeue or peek. */
    private int first;
    /* Index for the next enqueue. */
    private int last;
    /* Variable for the fillCount. */
    private int fillCount;
    /* Array for storing the buffer data. */
    private T[] rb;

    @Override
    public Iterator<T> iterator() {
        return new ArrayRingBufferIterator();
    }

    private class ArrayRingBufferIterator implements Iterator<T> {
        private int pos = first;
        private int hasDone = 0;

        @Override
        public boolean hasNext() {
            return hasDone != fillCount;
        }

        @Override
        public T next() {
            if (hasNext()) {
                T result = rb[pos];
                pos = plusOne(pos);
                hasDone += 1;
            }
            return null;
        }
    }

    @Override
    public boolean equals(Object o) {
        ArrayRingBuffer<T> ringBuffer = (ArrayRingBuffer<T>) o;

        if (o == this) {
            return true;
        }
        if (o.getClass() == this.getClass()) {
            return true;
        }
        if (ringBuffer.fillCount != this.fillCount) {
            return false;
        }
        int pos = first;
        for (T element : ringBuffer) {
            if (rb[pos] != element) {
                return false;
            }
            pos = plusOne(pos);
        }
        return true;
    }

    /**
     * Create a new ArrayRingBuffer with the given capacity.
     */
    public ArrayRingBuffer(int capacity) {
        rb = (T[]) new Object[capacity];
        first = capacity / 2;
        last = capacity / 2 - 1;
        fillCount = 0;
    }

    /**
     * Adds x to the end of the ring buffer. If there is no room, then
     * throw new RuntimeException("Ring buffer overflow").
     */
    @Override
    public void enqueue(T x) {
        if (isFull()) {
            throw new RuntimeException("Ring buffer overflow");
        }
        last = plusOne(last);
        rb[last] = x;
        fillCount += 1;
    }

    /**
     * Dequeue oldest item in the ring buffer. If the buffer is empty, then
     * throw new RuntimeException("Ring buffer underflow").
     */
    @Override
    public T dequeue() {
        // TODO: Dequeue the first item. Don't forget to decrease fillCount and
        //       update first.
        if (isEmpty()) {
            throw new RuntimeException("Ring buffer underflow");
        }
        T result = rb[first];
        rb[first] = null;
        first = plusOne(first);
        fillCount -= 1;
        return result;
    }

    /**
     * Return oldest item, but don't remove it. If the buffer is empty, then
     * throw new RuntimeException("Ring buffer underflow").
     */
    @Override
    public T peek() {
        // TODO: Return the first item. None of your instance variables should
        //       change.
        if (isEmpty()) {
            throw new RuntimeException("Ring Buffer underflow.");
        }
        return rb[first];
    }

    /** Return size of the buffer. */
    @Override
    public int capacity() {
        return rb.length;
    }

    /** Return number of items currently in the buffer. */
    @Override
    public int fillCount() {
        return fillCount;
    }

    /** return true if buffer is empty. */
    @Override
    public boolean isEmpty() {
        return fillCount() == 0;
    }

    @Override
    public boolean isFull() {
        return capacity() == fillCount();
    }

    /** return the relative position after plussing one for index. */
    private int plusOne(int index) {
        return (index + 1) % rb.length;
    }

    // TODO: When you get to part 4, implement the needed code to support
    //       iteration and equals.
}
