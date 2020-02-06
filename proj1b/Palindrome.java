public class Palindrome {
    /**
     * Translating the string to word sequence.
     */
    public Deque<Character> wordToDeque(String word) {
        Deque<Character> result = new ArrayDeque<Character>();
        for (int i = 0; i < word.length(); i += 1) {
            result.addLast(word.charAt(i));
        }
        return result;
    }

    /**
     * return true if word is palindrome with cc.
     */
    public boolean isPalindrome(String word, CharacterComparator cc) {
        if (word == null) {
            return false;
        } else {
            return OffByOneHelper(word, cc);
        }
    }

    private boolean OffByOneHelper(String word, CharacterComparator cc) {
        if (word.length() <= 1) {
            return true;
        } else {
            boolean FrontEnd = cc.equalChars(word.charAt(0), word.charAt(word.length() - 1));
            return FrontEnd && OffByOneHelper(word.substring(1, word.length() - 1), cc);
        }
    }

    /**
     * return true if word is palindrome.
     */
    public boolean isPalindrome(String word) {
        if (word == null) {
            return false;
        } else {
            return OffByOneHelper(word, new OffByN(0));
        }
    }
}
