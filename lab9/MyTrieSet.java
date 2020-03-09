import com.sun.jdi.Value;

import java.util.List;

public class MyTrieSet implements TrieSet61B{
    private node root;
    private int n;

    public MyTrieSet() {
        this.root = new node(null);
        this.n = 0;
    }

    private static class node {
        node left;
        node middle;
        node right;
        String key;

        private node(String key) {
            this.right = null;
            this.middle = null;
            this.left = null;
            this.key = key;
        }
    }

    /** Clear all items out of trie. */
    @Override
    public void clear() {
        root = new node(null);
    }

    /** Return true if the trie contains KEY, false otherwise. */
    @Override
    public boolean contains(String key) {
        return false;
    }

    /** add string KEY into trie. */
    @Override
    public void add(String key) {
        if (isEmpty()) {
            root.key = key;
        }
    }

    /** Return a list of key words that start with same prefix. */
    @Override
    public List<String> keysWithPrefix(String key) {
        return null;
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
