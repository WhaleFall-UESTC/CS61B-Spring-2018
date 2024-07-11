public class OffByone implements CharacterComparator {
    @Override
    public boolean equalChars(char x, char y) {
        return (Math.abs(x - y) == 1);
    }
}
