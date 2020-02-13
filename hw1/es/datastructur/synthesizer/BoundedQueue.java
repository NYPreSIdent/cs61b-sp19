package es.datastructur.synthesizer;

public interface BoundedQueue<T> {

    /** return size of the buffer. */
    int capacity();

    /** return number of items currently in the buffer. */
    int fillCount();

    /** add item x to the end. */
    void enqueue(T x);

    /** delete and return item from the front. */
    T dequeue();

    /** return (but not delete) item from the front. */
    T peek();

    /** is buffer empty? */
    boolean isEmpty();

    /** is the buffer full? */
    boolean isFull();

}
