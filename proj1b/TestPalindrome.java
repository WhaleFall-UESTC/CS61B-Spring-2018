import org.junit.Test;
import static org.junit.Assert.*;

public class TestPalindrome {
    static Palindrome palindrome = new Palindrome();

    @Test
    public void testWordToDeque() {
        Deque d = palindrome.wordToDeque("persiflage");
        String actual = "";
        for (int i = 0; i < "persiflage".length(); i++) {
            actual += d.removeFirst();
        }
        assertEquals("persiflage", actual);
    }

    @Test
    public void testIsPalindrome() {
        assertFalse(palindrome.isPalindrome(null));
        assertTrue(palindrome.isPalindrome("aaaaaaa"));
        assertTrue(palindrome.isPalindrome("abababa"));
        assertTrue(palindrome.isPalindrome(""));
        assertTrue(palindrome.isPalindrome("L"));
        assertTrue(palindrome.isPalindrome("SOS"));
        assertFalse(palindrome.isPalindrome("cat"));
        assertTrue(palindrome.isPalindrome("az", new OffByN(25)));
    }
}
