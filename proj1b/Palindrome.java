public class Palindrome {
    /** Translating the string to word sequence. */
    public Deque<Character> wordToDeque(String word) {
        Deque<Character> result = new ArrayDeque<Character>();
        for (int i = 0; i < word.length(); i += 1) {
            result.addLast(word.charAt(i));
        }
        return result;
    }

    /** return true if word is palindrome. */
    public boolean isPalindrome(String word, CharacterComparator cc) {
        return true;
    }

    /** return true if word is palindrome. */
    public boolean isPalindrome(String word) {
        if (word == null) {
            return false;
        } else {
            return isPalindromeHelper(word);
        }
    }

    private boolean isPalindromeHelper(String word) {
        if (word.length() <= 1) {
            return true;
        } else {
            boolean FrontEnd = (word.charAt(0) == word.charAt(word.length() - 1));
            return FrontEnd && isPalindromeHelper(word.substring(1, word.length() - 1));
        }
    }
}
