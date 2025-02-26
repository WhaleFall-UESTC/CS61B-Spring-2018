/** Performs some basic linked list tests. */
public class LinkedListDequeTest {
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
    public static void addIsEmptySizeTest() {
        System.out.println("Running add/isEmpty/Size test.");
        LinkedListDeque<String> lld1 = new LinkedListDeque<>();

        boolean passed = checkEmpty(true, lld1.isEmpty());

        lld1.addFirst("front");

        // The && operator is the same as "and" in Python.
        // It's a binary operator that returns true if both arguments true, and false otherwise.
        passed = checkSize(1, lld1.size()) && passed;
        passed = checkEmpty(false, lld1.isEmpty()) && passed;

        lld1.addLast("middle");
        passed = checkSize(2, lld1.size()) && passed;

        lld1.addLast("back");
        passed = checkSize(3, lld1.size()) && passed;

        System.out.println("Printing out deque: ");
        lld1.printDeque();

        printTestStatus(passed);
    }

    /** Adds an item, then removes an item, and ensures that dll is empty afterwards. */
    public static void addRemoveTest() {

        System.out.println("Running add/remove test.");

        LinkedListDeque<Integer> lld1 = new LinkedListDeque<>();
        // should be empty
        boolean passed = checkEmpty(true, lld1.isEmpty());

        lld1.addFirst(10);
        // should not be empty
        passed = checkEmpty(false, lld1.isEmpty()) && passed;

        lld1.removeFirst();
        // should be empty
        passed = checkEmpty(true, lld1.isEmpty()) && passed;

        printTestStatus(passed);
    }

    public static void myViewer() {
        LinkedListDeque<String> L = new LinkedListDeque<>();
        L.addFirst("May");
        L.removeLast();
        L.addLast("Cry");
        L.removeFirst();
        L.addFirst("am");
        L.addFirst("approaching");
        L.addLast("Devil");
        L.removeLast();
        L.addLast("the");
        L.removeFirst();
        L.addFirst("I");
        L.addLast("storm");
        L.printDeque();
        System.out.println(L.getRecursive(0));
        System.out.println(L.get(1));
        System.out.println(L.getRecursive(2));
        System.out.println(L.getRecursive(3));
    }

    public static void getTest() {
        System.out.println("Start getTest");
        LinkedListDeque<Integer> L = new LinkedListDeque<>();
        for (int i = 0; i < 10000; i++) {
            L.addLast(i);
        }

        boolean passed = true;
        for (int i = 0; i < 10000; i++) {
            if (L.get(i) != i) {
                System.out.println("get(" + i + ") failed");
                passed = false;
            }
            if (L.getRecursive(i) != i) {
                System.out.println("getRecursive(" + i + ") failed");
                passed = false;
            }
        }
        printTestStatus(passed);
    }

    public static void main(String[] args) {
        System.out.println("Running tests.\n");
        addIsEmptySizeTest();
        addRemoveTest();
        getTest();
        myViewer();
    }
}
