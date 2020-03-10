import java.security.Key;
import java.util.LinkedList;
import java.util.List;

public class MyTrieSet implements TrieSet61B{
    private node root;
    private int n;

    public MyTrieSet() {
        this.root = new node();
        this.n = 0;
    }

    private static class node {
        node left;
        node middle;
        node right;
        Character key;

        private node() {
            new node(null);
        }

        private node(Character key) {
            this.left = null;
            this.right = null;
            this.middle = null;
            this.key = key;
        }
    }

    /** Clear all items out of trie. */
    @Override
    public void clear() {
        root = new node();
        this.n = 0;
    }

    /** Return true if the trie contains KEY, false otherwise. */
    @Override
    public boolean contains(String key) {
        if (isEmpty()) {
            return false;
        }
        return containsHelper(root, key, 0);
    }

    private boolean containsHelper(node n, String key, int depth) {
        if (depth == key.length()) { return true; }
        if (n == null) { return false; }
        int cmp = key.charAt(depth) - n.key;
        if (cmp == 0) {
            return containsHelper(n.middle, key, depth + 1);
        } else if (cmp < 0) {
            return containsHelper(n.left, key, depth);
        } else {
            return containsHelper(n.right, key, depth);
        }
    }

    /** add string KEY into trie. */
    @Override
    public void add(String key) {
        if (isEmpty()) {
            root.key = key.charAt(0);
            n += 1;
        }
        if (contains(key)) {
            return;
        }
        root = addHelper(root, key, 0);
        n += 1;
    }

    private node addHelper(node n, String key, int length) {
        if (length >= key.length()) {
            return null;
        }
        if (n == null) {
            node p = new node(key.charAt(length));
            p.middle = addHelper(p.middle, key, length + 1);
            return p;
        }
        int cmp = key.charAt(length) - n.key;
        if (cmp == 0) {
            n.middle = addHelper(n.middle, key, length + 1);
        } else if (cmp > 0) {
            n.right = addHelper(n.right, key, length);
        } else {
            n.left = addHelper(n.left, key, length);
        }
        return n;
    }

    /** Return a list of key words that start with same prefix. */
    @Override
    public List<String> keysWithPrefix(String key) {
        if (isEmpty()) {
            throw new IllegalArgumentException();
        }
        if (!contains(key)) {
            throw new IllegalArgumentException("The trie does not contain key");
        }
        node p = keyWithPrefixHelper(key, 0, root);
        LinkedList<String> result = new LinkedList<>();
        collect(p, result, key);
        return result;
    }

    private node keyWithPrefixHelper(String key, int depth, node n) {
        if (depth == key.length()) {
            return n;
        }
        int cmp = key.charAt(depth) - n.key;
        if (cmp < 0) {
            n = keyWithPrefixHelper(key, depth, n.left);
        } else if (cmp == 0) {
            n = keyWithPrefixHelper(key, depth + 1, n.middle);
        } else {
            n = keyWithPrefixHelper(key, depth, n.right);
        }
        return n;
    }

    /** collect all strings in the trie. */
    private void collect(node n, List<String> result, String elemOfResult) {
        if (n == null) { return; }
        String key = n.key.toString();
        result.add(elemOfResult + key);
        collect(n.middle, result, elemOfResult + key);
        collect(n.left, result, elemOfResult);
        collect(n.right, result, elemOfResult);
    }


    /** Return the longest prefix of key that exists in the trie. */
    @Override
    public String longestPrefixOf(String key) {
        return null;
    }

    /** Return size of this trie. */
    private int size() {
        return n;
    }

    /** Return true if this trie is empty. */
    private boolean isEmpty() {
        return size() == 0;
    }
}
