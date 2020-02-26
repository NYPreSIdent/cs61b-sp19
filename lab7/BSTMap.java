import java.util.*;

public class BSTMap<K extends Comparable<K>, V> implements Map61B<K, V> {
    private node root;

    private class node {
        private K key;
        private V value;
        private node left, right;
        private int size;

        /* creates a new node which can be controlled by BSTree. */
        private node(K key, V value, int size) {
            this.key = key;
            this.value = value;
            this.size = size;
        }

        private boolean isEmpty() {
            return  left == null && right == null;
        }
    }

    /** Removes all of the mappings from this map. */
    @Override
    public void clear() {
        root = null;
    }

    /* Returns true if this map contains a mapping for the specified key. */
    @Override
    public boolean containsKey(K key) {
        if (root == null) return false;
        return containsKeyHelper(root, key, root.key, root.key);
    }

    private boolean containsKeyHelper(node n, K key, K max_value, K min_value) {
        if (n == null) return false;
        int maxCmp = key.compareTo(max_value); // if key greater than max_value, it's false.
        int minCmp = key.compareTo(min_value); // if key smaller than min_value, it's false.
        if (maxCmp == minCmp) return true;
        if (maxCmp > 0 || minCmp < 0) return false;
        return containsKeyHelper(n.left, key, n.key, max_value) && containsKeyHelper(n.right, key, min_value, min_value);
    }

    /* Returns the value to which the specified key is mapped, or null if this
     * map contains no mapping for the key.
     */
    @Override
    public V get(K key) {
        if (key == null) {
            return null;
        }
        return getHelper(key, root);
    }

    private V getHelper(K key, node n) {
        if (n == null) {
            return null;
        }
        int cmp = key.compareTo(n.key);
        if (cmp == 0) return n.value;
        else if (cmp > 0) return getHelper(key, n.right);
        else return getHelper(key, n.left);
    }

    /* Returns the number of key-value mappings in this map. */
    @Override
    public int size() {
        return size(root);
    }

    public int size(node n) {
        if (n == null) return 0;
        return n.size;
    }

    /* Associates the specified value with the specified key in this map. */
    @Override
    public void put(K key, V value) {
        if (key == null) throw new IllegalArgumentException("key can not be null!");
        root = put(root, key, value);
    }

    public node put(node n, K key, V value) {
        if (n == null) return new node(key, value, 1);
        int cmp = key.compareTo(n.key);
        if (cmp > 0) {
            n.right = put(n.right, key, value);
        } else if (cmp < 0) {
            n.left = put(n.left, key, value);
        } else {
            n.value = value;
        }
        n.size = 1 + size(n.left) + size(n.right);
        return n;
    }

    private node min(node n) {
        if (n.left == null) return n;
        return min(n.left);
    }

    private node max(node n) {
        if (n.right == null) return n;
        return max(n.right);
    }

    private node deleteMin(node n) {
        if (n.left == null) return n.right;
        n.left = deleteMin(n.left);
        n.size = size(n.left) + size(n.right) + 1;
        return n;
    }

    /* Returns a Set view of the keys contained in this map. */
    @Override
    public Set<K> keySet() {
        throw new UnsupportedOperationException("Can't do that!");
    }

    /* Removes the mapping for the specified key from this map if present.
     * Not required for Lab 8. If you don't implement this, throw an
     * UnsupportedOperationException. */
    @Override
    public V remove(K key) {
        if (key == null) throw new IllegalArgumentException("calls remove() with a null key");
        V result = get(key);
        root = remove(root, key);
        return result;
    }

    private node remove(node n, K key) {
        if (n == null) return null;

        int cmp = key.compareTo(n.key);
        if (cmp > 0) n.right = remove(n.right, key);
        else if (cmp < 0) n.left = remove(n.left, key);
        else {
            if (n.right == null) return n.left;
            if (n.left == null) return n.right;
            node p = n;
            n = min(p.right);
            n.right = deleteMin(p.right);
            n.left = p.left;
        }
        n.size = size(n.left) + size(n.right) + 1;
        return n;
    }

    /* return true if the tree is compeleted. */
    private boolean isComplete(node n) {
        return (n.left != null) && (n.right != null);
    }

    /* Removes the entry for the specified key only if it is currently mapped to
     * the specified value. Not required for Lab 8. If you don't implement this,
     * throw an UnsupportedOperationException.*/
    @Override
    public V remove(K key, V value) {
        return null;
    }

    @Override
    public Iterator<K> iterator() {
        return new BSTMapIterator();
    }

    public class BSTMapIterator implements Iterator<K> {
        LinkedList<K> result = new LinkedList<>();

        private void rank() {
            rankHelper(root, result, min(root).key, max(root).key);
        }

        private void rankHelper(node n, LinkedList<K> lst, K lo, K hi) {

        }

        @Override
        public boolean hasNext() {
            return !result.isEmpty();
        }

        @Override
        public K next() {
            return result.removeFirst();
        }
    }

}
