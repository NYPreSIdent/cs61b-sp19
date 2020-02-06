public class OffByOne implements CharacterComparator{
    /** Return true if x equals y. */
    @Override
    public boolean equalChars(char x, char y) {
        return abs(x - y) == 1;
    }

    private static int abs(int n) {
        if (n < 0) {
            return -n;
        } else {
            return n;
        }
    }
}
