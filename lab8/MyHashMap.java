import java.util.*;

public class MyHashMap<K, V> implements Map61B<K, V> {
    private double loadFactor;
    private LinkedList<Pair<K, V>>[] bucket;
    private int size;
    private HashSet<K> set;

    private static class Pair<K, V> {
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
        this(16);
    }

    public MyHashMap(int initialSize) {
        this(initialSize, 0.75);
    }

    public MyHashMap(int initialSize, double loadFactor) {
        this.loadFactor = loadFactor;
        bucket = (LinkedList<Pair<K, V>>[]) new LinkedList[initialSize];
        size = 0;
        set = new HashSet<>();
        for (int i = 0; i < initialSize; i += 1) {
            bucket[i] = new LinkedList<>();
        }
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

    private double calLoadFactor(LinkedList<Pair<K, V>>[] lst) {
        return (double) size / lst.length;
    }

    /** Remove all of the mapping from this map. */
    @Override
    public void clear() {
        size = 0;
        for (int i = 0; i < bucket.length; i += 1) {
            bucket[i] = new LinkedList<>();
        }
        set = new HashSet<>();
    }

    /** Return true if this map contains a mapping for the specific key. */
    @Override
    public boolean containsKey(K key) {
        return set.contains(key);
    }

    /**
     * Returns the value to which the specified key is mapped, or null if this
     * map contains no mapping for the key.
     */
    @Override
    public V get(K key) {
        if (key == null) return null;
        int hashCode = hash(key, bucket);
        if (bucket[hashCode] != null) {
            Pair<K, V> result = deepSearch(bucket[hashCode], key);
            if (result != null) {
                return result.getValue();
            }
        }
        return null;
    }

    /** Return value when the provided key satisfies the link-list content. */
    private Pair<K, V> deepSearch(LinkedList<Pair<K, V>> lst, K key) {
        if (lst.isEmpty()) {
            return null;
        }
        Pair<K, V> first = lst.removeFirst();
        if (first.getKey().equals(key)) {
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
        if (!containsKey(key)) {
            size += 1;
        }
        if (key == null || value == null) {
            return;
        }
        if (calLoadFactor(lst) > loadFactor) {
            resize();
            lst = bucket;
        }
        int hashCode = hash(key, lst);
        boolean AlreadyExist = contains(lst[hashCode], key);
        if (!AlreadyExist) {
            lst[hashCode].addFirst(new Pair<>(key, value));
        } else {
            for (Pair<K, V> elem : lst[hashCode]) {
                if (elem.getKey().equals(key)) {
                    elem.value = value;
                }
            }
        }
        set.add(key);
    }

    /** Return false if lst contains the key. */
    private boolean contains(LinkedList<Pair<K, V>> lst, K key) {
        if (lst.isEmpty()) {
            return false;
        }
        for (Pair<K, V> elem : lst) {
            if (elem == null) {
                return false;
            }
            if (elem.getKey().equals(key)) {
                return true;
            }
        }
        return false;
    }

    /** Resizing the capacity of the bucket when loadFactor is exceeded. */
    private void resize() {
        LinkedList<Pair<K, V>>[] BigSize = (LinkedList<Pair<K, V>>[]) new LinkedList[bucket.length * 2];
        for (int i = 0; i < BigSize.length; i += 1) {
            BigSize[i] = new LinkedList<>();
        }
        for (K key : set) {
            int hashCodeBucket = hash(key, bucket);
            Pair<K, V> pair = deepSearch(bucket[hashCodeBucket], key);
            if (pair != null) {
                put(pair.getKey(), pair.getValue(), BigSize);
            }
        }
        this.bucket = BigSize;
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

    private int hash(K key, LinkedList<Pair<K, V>>[] lst) {
        return (key.hashCode() & 0x7fffffff) % lst.length;
    }
}
