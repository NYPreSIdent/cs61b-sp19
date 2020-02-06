public class OffByN implements CharacterComparator {
    private int N;

    public OffByN(int number) {
        N = number;
    }

    /** return true for characters that are off By N. */
    @Override
    public boolean equalChars(char x, char y) {
        return abs(x - y) == N;
    }

    private static int abs(int n) {
        if (n < 0) {
            return -n;
        } else {
            return n;
        }
    }
}
