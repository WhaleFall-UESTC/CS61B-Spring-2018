public class Palindrome {
    public Deque<Character> wordToDeque(String word) {
        Deque<Character> d = new ArrayDeque<>();
        int len = word.length();
        for (int i = 0; i < len; i++) {
            d.addLast(word.charAt(i));
        }
        return d;

    }

    private class CharEqual implements CharacterComparator {
        @Override
        public boolean equalChars(char x, char y) {
            return x == y;
        }
    }

    public boolean isPalindrome(String word) {
        return isPalindrome(word, new CharEqual());
    }

    public boolean isPalindrome(String word, CharacterComparator cc) {
        if (word == null) {
            return false;
        }
        int len = word.length();
        int lenHalf = len / 2;
        for (int i = 0; i < lenHalf; i++) {
            if (false == cc.equalChars(word.charAt(i), word.charAt(len - i - 1))) {
                return false;
            }
        }
        return true;
    }
}

