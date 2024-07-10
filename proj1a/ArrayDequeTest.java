/** Performs some basic linked list tests. */
public class ArrayDequeTest {

    /* Utility method for printing out empty checks. */
    public static boolean checkEmpty(boolean expected, boolean actual) {
        if (expected != actual) {
            System.out.println("isEmpty() returned " + actual + ", but expected: " + expected);
            return false;
        }
        return true;
    }

    /* Utility method for printing out empty checks. */
    public static boolean checkSize(int expected, int actual) {
        if (expected != actual) {
            System.out.println("size() returned " + actual + ", but expected: " + expected);
            return false;
        }
        return true;
    }

    /* Prints a nice message based on whether a test passed.
     * The \n means newline. */
    public static void printTestStatus(boolean passed) {
        if (passed) {
            System.out.println("Test passed!\n");
        } else {
            System.out.println("Test failed!\n");
        }
    }

    /** Adds a few things to the list, checking isEmpty() and size() are correct,
     * finally printing the results.
     *
     * && is the "and" operation. */
    public static void viewerTest() {
        ArrayDeque<Integer> L = new ArrayDeque<>();
        checkEmpty(true, L.isEmpty());
        L.addFirst(0);
        L.addFirst(1);
        L.addLast(2);
        L.addFirst(3);
        L.addLast(4);
        L.addLast(5);
        L.addFirst(6);
        L.removeFirst();
        checkEmpty(false, L.isEmpty());
        L.addLast(6);
        L.removeLast();
        L.addFirst(6);
        L.addLast(7);
        L.addLast(8);
        L.addFirst(9);
        checkSize(10, L.size());
        L.printDeque();

        for (int i = 10; i <= 33; i++) {
            L.addFirst(i);
        }

        for (int i = 33; i >= 7; i--) {
            L.removeLast();
        }
    }

    private static void pressTest() {
        ArrayDeque<Integer> L = new ArrayDeque<>();

        for (int i = 0; i < 10000; i++) {
            L.addLast(i);
        }

        for (int i = 0; i < 10000 - 9; i++) {
            L.removeLast();
        }

        for (int i = 0; i < 9; i++) {
            L.removeFirst();
        }

        boolean passed = checkEmpty(true, L.isEmpty());
        printTestStatus(passed);
    }

    public static void main(String[] args) {
        System.out.println("Running tests.\n");
        viewerTest();
        pressTest();
    }
}
