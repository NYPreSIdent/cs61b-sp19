import java.util.*;

public class MyHashMap<K, V> implements Map61B<K, V> {
    private int initialSize;
    private double loadFactor;
    private LinkedList<Pair<K, V>>[] bucket;
    private int size;
    private HashSet<K> set;

    private class Pair<K, V> {
        private K key;
        private V value;

        public Pair(K key, V value) {
            this.key = key;
            this.value = value;
        }

        public K getKey() {
            return key;
        }

        public V getValue() {
            return value;
        }
    }

    public MyHashMap() {
        new MyHashMap(16, 0.75);
    }

    public MyHashMap(int initialSize) {
        new MyHashMap(initialSize, 0.75);
    }

    public MyHashMap(int initialSize, double loadFactor) {
        this.initialSize = initialSize;
        this.loadFactor = loadFactor;
        bucket = (LinkedList<Pair<K, V>>[]) new Object[initialSize];
        size = 0;
        set = new HashSet<>();
    }

    public static class getIteratorHelper<K> implements Iterator<K> {
        @Override
        public boolean hasNext() {
            return true;
        }

        @Override
        public K next() {
            return null;
        }

    }

    public Iterator<K> iterator() {
        return new getIteratorHelper<>();
    }

    private double calLoadFactor() {
        return (double) size / bucket.length;
    }

    /** Remove all of the mapping from this map. */
    @Override
    public void clear() {
        size = 0;
        bucket = (LinkedList<Pair<K, V>>[]) new Object[bucket.length];
    }

    /** Return true if this map contains a mapping for the specific key. */
    @Override
    public boolean containsKey(K key) {
        if (key == null) return false;
        int hashCode = hash(key);
        return bucket[hashCode] != null;
    }

    /**
     * Returns the value to which the specified key is mapped, or null if this
     * map contains no mapping for the key.
     */
    @Override
    public V get(K key) {
        if (key == null) return null;
        int hashCode = hash(key);
        if (bucket[hashCode] != null) {
            return deepSearch(bucket[hashCode], key).getValue();
        }
        return null;
    }

    /** Return value when the provided key satisfies the link-list content. */
    private Pair<K, V> deepSearch(LinkedList<Pair<K, V>> lst, K key) {
        Pair<K, V> first = lst.removeFirst();
        if (first.getKey() == key) {
            lst.addFirst(first);
            return first;
        } else {
            Pair<K, V> result = deepSearch(lst, key);
            lst.addFirst(first);
            return result;
        }
    }

    /** Returns the number of key-value mappings in this map. */
    @Override
    public int size() {
        return size;
    }

    /**
     * Associates the specified value with the specified key in this map.
     * If the map previously contained a mapping for the key,
     * the old value is replaced.
     */
    @Override
    public void put(K key, V value) {
        put(key, value, bucket);
    }

    private void put(K key, V value, LinkedList<Pair<K, V>>[] lst) {
        if (key == null || value == null) {
            return;
        }
        if (calLoadFactor() > loadFactor) {
            resize
        }
        set.add(key);
        int hashCode = hash(key);
    }

    /** Resizing the capacity of the bucket when loadFactor is exceeded. */
    private void resize() {
        LinkedList<Pair<K, V>>[] BigSize = (LinkedList<Pair<K, V>>[]) new Object[bucket.length * 2];
        for (K key : set) {
            int hashCode = hash(key);
            Pair<K, V> pair = deepSearch(bucket[hashCode], key);
            put(pair.getKey(), pair.getValue(), BigSize);
        }
        bucket = BigSize;
    }

    /** Returns a Set view of the keys contained in this map. */
    @Override
    public Set<K> keySet() {
        return set;
    }

    /**
     * Removes the mapping for the specified key from this map if present.
     * Not required for Lab 8. If you don't implement this, throw an
     * UnsupportedOperationException.
     */
    @Override
    public V remove(K key) {
        throw new UnsupportedOperationException("can not remove now!");
    }

    /**
     * Removes the entry for the specified key only if it is currently mapped to
     * the specified value. Not required for Lab 8. If you don't implement this,
     * throw an UnsupportedOperationException.
     */
    @Override
    public V remove(K key, V value) {
        throw new UnsupportedOperationException("Can not remove now!");
    }

    private int hash(K key) {
        return (key.hashCode() & 0x7fffffff) % bucket.length;
    }
}
