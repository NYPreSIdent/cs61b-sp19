package bearmaps.proj2ab;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

public class MyTrieSet implements TrieSet61B{
    private HashSet<String> words;
    private node root;
    private int n;

    public MyTrieSet() {
        this.root = new node();
        this.words = new HashSet<>();
        this.n = 0;
    }

    private static class node {
        node left;
        node middle;
        node right;
        Character key;
        boolean isEnd;

        private node() { }

        private node(Character key, boolean isEnd) {
            this.left = null;
            this.right = null;
            this.middle = null;
            this.isEnd = isEnd;
            this.key = key;
        }
    }

    /** Clear all items out of trie. */
    @Override
    public void clear() {
        words = new HashSet<>();
        root = new node();
        this.n = 0;
    }

    /** Return true if the trie contains KEY, false otherwise. */
    @Override
    public boolean contains(String key) {
        if (isEmpty() || key == null) {
            return false;
        }
        return words.contains(key);
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
        words.add(key);
        n += 1;
    }

    private node addHelper(node n, String key, int index) {
        if (n == null) {
            node p;
            if (index + 1 >= key.length()) {
                return new node(key.charAt(index), true);
            } else {
                p = new node(key.charAt(index), false);
            }
            p.middle = addHelper(p.middle, key, index + 1);
            return p;
        } else if (index + 1 == key.length()) {
            if (n.key.equals(key.charAt(index))) {
                n.isEnd = true;
                words.add(key);
            }
        } else if (index + 1 > key.length()) {
            return n;
        }
        int cmp = key.charAt(index) - n.key;
        if (cmp == 0) {
            n.middle = addHelper(n.middle, key, index + 1);
        } else if (cmp > 0) {
            n.right = addHelper(n.right, key, index);
        } else {
            n.left = addHelper(n.left, key, index);
        }
        return n;
    }

    /** Return a list of key words that start with same prefix. */
    @Override
    public List<String> keysWithPrefix(String key) {
        if (isEmpty()) {
            throw new IllegalArgumentException();
        }
        node p = keyWithPrefixHelper(key, 0, root);
        LinkedList<String> result = new LinkedList<>();
        collect(p, result, key);
        return result;
    }

    private node keyWithPrefixHelper(String key, int index, node n) {
        if (n == null || index == key.length()) {
            return n;
        }
        int cmp = key.charAt(index) - n.key;
        if (cmp < 0) {
            n = keyWithPrefixHelper(key, index, n.left);
        } else if (cmp == 0) {
            n = keyWithPrefixHelper(key,index+ 1, n.middle);
        } else {
            n = keyWithPrefixHelper(key, index, n.right);
        }
        return n;
    }

    /** collect all strings in the trie. */
    private void collect(node n, List<String> result, String elemOfResult) {
        if (n == null) { return; }
        String key = n.key.toString();
        if (n.isEnd) {
            result.add(elemOfResult + key);
        }
        collect(n.middle, result, elemOfResult + key);
        collect(n.left, result, elemOfResult);
        collect(n.right, result, elemOfResult);
    }


    /** Return the longest prefix of key that exists in the trie. */
    @Override
    public String longestPrefixOf(String key) {
        return collectLongestPrefix(root, key, "", 0);
    }

    private String collectLongestPrefix(node n, String key, String result, int length) {
        if (length == key.length() || n == null) {
            return result;
        }
        int cmp = key.charAt(length) - n.key;
        if (cmp == 0) {
            return collectLongestPrefix(n.middle, key, result + n.key.toString(), length + 1);
        } else if (cmp > 0) {
            return collectLongestPrefix(n.right, key, result, length);
        } else {
            return collectLongestPrefix(n.left, key, result, length);
        }
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
