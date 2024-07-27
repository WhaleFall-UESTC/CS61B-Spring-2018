import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Queue;

import java.util.Random;

public class QuickSort {
    /**
     * Returns a new queue that contains the given queues catenated together.
     *
     * The items in q2 will be catenated after all of the items in q1.
     */
    private static <Item extends Comparable> Queue<Item> catenate(Queue<Item> q1, Queue<Item> q2) {
        Queue<Item> catenated = new Queue<Item>();
        for (Item item : q1) {
            catenated.enqueue(item);
        }
        for (Item item: q2) {
            catenated.enqueue(item);
        }
        return catenated;
    }

    /** Returns a random item from the given queue. */
    private static <Item extends Comparable> Item getRandomItem(Queue<Item> items) {
        int pivotIndex = (int) (Math.random() * items.size());
        Item pivot = null;
        // Walk through the queue to find the item at the given index.
        for (Item item : items) {
            if (pivotIndex == 0) {
                pivot = item;
                break;
            }
            pivotIndex--;
        }
        return pivot;
    }

    /**
     * Partitions the given unsorted queue by pivoting on the given item.
     *
     * @param unsorted  A Queue of unsorted items
     * @param pivot     The item to pivot on
     * @param less      An empty Queue. When the function completes, this queue will contain
     *                  all of the items in unsorted that are less than the given pivot.
     * @param equal     An empty Queue. When the function completes, this queue will contain
     *                  all of the items in unsorted that are equal to the given pivot.
     * @param greater   An empty Queue. When the function completes, this queue will contain
     *                  all of the items in unsorted that are greater than the given pivot.
     */
    private static <Item extends Comparable> void partition(
            Queue<Item> unsorted, Item pivot,
            Queue<Item> less, Queue<Item> equal, Queue<Item> greater) {
        // Your code here!
        for (Item item : unsorted) {
            int cmp = item.compareTo(pivot);
            if (cmp > 0) {
                greater.enqueue(item);
            } else if (cmp < 0) {
                less.enqueue(item);
            } else {
                equal.enqueue(item);
            }
        }
    }

    /** Returns a Queue that contains the given items sorted from least to greatest. */
    public static <Item extends Comparable> Queue<Item> quickSort(
            Queue<Item> items) {
        // Your code here!
        if (items.size() > 1) {
            Item pivot = getRandomItem(items);
            Queue<Item> less = new Queue<>();
            Queue<Item> equal = new Queue<>();
            Queue<Item> greater = new Queue<>();
            partition(items, pivot, less, equal, greater);
            Queue<Item> lessEqual = catenate(quickSort(less), equal);
            items = catenate(lessEqual, quickSort(greater));
        }
        return items;
    }

    public static void main(String[] args) {
        Queue<String> qs = new Queue<>();
        MinPQ<String> ans1 = new MinPQ<>();
        qs.enqueue("Wonderful");
        qs.enqueue("Everyday");
        qs.enqueue("Never");
        qs.enqueue("Knows");
        qs.enqueue("Best");
        qs.enqueue("Yuki");
        qs.enqueue("Jabberwocky");
        ans1.insert("Wonderful");
        ans1.insert("Everyday");
        ans1.insert("Never");
        ans1.insert("Knows");
        ans1.insert("Best");
        ans1.insert("Yuki");
        ans1.insert("Jabberwocky");
        Queue<String> res1 = quickSort(qs);
        boolean pass = true;
        for (String res : res1) {
            System.out.println("res: " + res + "\tans: " + ans1.min());
            if (!res.equals(ans1.delMin())) {
                pass = false;
                break;
            }
        }
        System.out.println(pass ? "PASS!!!" : "Failed");
        System.out.println();


        Queue<Integer> qi = new Queue<>();
        MinPQ<Integer> ans2 = new MinPQ<>();
        Random rand = new Random(System.currentTimeMillis());
        for (int i = 0; i < 1000; i++) {
            int num = rand.nextInt(999999);
            qi.enqueue(num);
            ans2.insert(num);
        }
        qi = quickSort(qi);
        pass = true;
        for (int res : qi) {
//            System.out.println("res: " + res + "\tans: " + ans2.min());
            if (res != ans2.delMin()) {
                pass = false;
                System.out.println("res: " + res + "\tans: " + ans2.min());
                break;
            }
        }
        System.out.println(pass ? "PASS!!!" : "Failed");
    }
}
