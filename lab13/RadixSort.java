import edu.princeton.cs.algs4.MinPQ;

import java.util.Random;

/**
 * Class for doing Radix sort
 *
 * @author Akhil Batra, Alexander Hwang
 *
 */
public class RadixSort {

    private static int lenMax = -1;
    /**
     * Does LSD radix sort on the passed in array with the following restrictions:
     * The array can only have ASCII Strings (sequence of 1 byte characters)
     * The sorting is stable and non-destructive
     * The Strings can be variable length (all Strings are not constrained to 1 length)
     *
     * @param asciis String[] that needs to be sorted
     *
     * @return String[] the sorted array
     */
    public static String[] sort(String[] asciis) {
        // TODO: Implement LSD Sort
        for (int i = 0; i < asciis.length; i++) {
            int len = asciis[i].length();
            lenMax = len > lenMax ? len : lenMax;
        }
        for (int i = 1; i <= lenMax; i++) {
            sortHelperLSD(asciis, i);
        }
        return asciis;
    }

    /**
     * LSD helper method that performs a destructive counting sort the array of
     * Strings based off characters at a specific index.
     * @param asciis Input array of Strings
     * @param index The position to sort the Strings on.
     */
    private static void sortHelperLSD(String[] asciis, int index) {
        // Optional LSD helper method for required LSD radix sort
        int[] starts = new int[256];
        for (int i = 0; i < asciis.length; i++) {
            starts[getIndex(asciis[i], index)]++;
        }
        for (int i = 1; i < 256; i++) {
            starts[i] += starts[i - 1];
        }
        String[] sorted = new String[asciis.length];
        for (int i = asciis.length - 1; i >= 0; i--) {
            String item = asciis[i];
            sorted[--starts[getIndex(item, index)]] = item;
        }
        for (int i = 0; i < asciis.length; i++) {
            asciis[i] = sorted[i];
        }
        return;
    }

    // to sort Strings, we would pad them on the right with empty values
    private static int getIndex(String s, int index) {
        int len = s.length();
        if (index > lenMax - len) {
            return s.charAt(lenMax - index);
        } else {
            return 0;
        }
    }

    /**
     * MSD radix sort helper function that recursively calls itself to achieve the sorted array.
     * Destructive method that changes the passed in array, asciis.
     *
     * @param asciis String[] to be sorted
     * @param start int for where to start sorting in this method (includes String at start)
     * @param end int for where to end sorting in this method (does not include String at end)
     * @param index the index of the character the method is currently sorting on
     *
     **/
    private static void sortHelperMSD(String[] asciis, int start, int end, int index) {
        // Optional MSD helper method for optional MSD radix sort
        return;
    }

    private static String genString(Random rand) {
        int len = rand.nextInt(10) + 1;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < len; i++) {
            sb.append(rand.nextInt(256));
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        String[] test = new String[]
                {"ab", "lb", "cl", "air", "kannon", "sp"};
        sort(test);
        for (int i = 0; i < test.length; i++) {
            System.out.println(test[i]);
        }
        System.out.println();

        MinPQ<String> pq = new MinPQ<>();
        String[] test2 = new String[1000];
        Random rand = new Random();
        for (int i = 0; i < 1000; i++) {
             String s = genString(rand);
             pq.insert(s);
             test2[i] = s;
        }
        sort(test2);
        for (int i = 0; i < 1000; i++) {
            String ans = pq.delMin();
            if (!test2[i].equals(ans)) {
                System.out.println("Result is " + test2[i] + " but supposed to be " + ans);
                System.out.println("Failed!!!");
                return;
            }
        }
        System.out.println("Pass!!!");
    }
}
