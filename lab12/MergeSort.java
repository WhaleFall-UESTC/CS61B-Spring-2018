import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Queue;
import java.util.Random;

public class MergeSort {
    /**
     * Removes and returns the smallest item that is in q1 or q2.
     *
     * The method assumes that both q1 and q2 are in sorted order, with the smallest item first. At
     * most one of q1 or q2 can be empty (but both cannot be empty).
     *
     * @param   q1  A Queue in sorted order from least to greatest.
     * @param   q2  A Queue in sorted order from least to greatest.
     * @return      The smallest item that is in q1 or q2.
     */
    private static <Item extends Comparable> Item getMin(
            Queue<Item> q1, Queue<Item> q2) {
        if (q1.isEmpty()) {
            return q2.dequeue();
        } else if (q2.isEmpty()) {
            return q1.dequeue();
        } else {
            // Peek at the minimum item in each queue (which will be at the front, since the
            // queues are sorted) to determine which is smaller.
            Comparable q1Min = q1.peek();
            Comparable q2Min = q2.peek();
            if (q1Min.compareTo(q2Min) <= 0) {
                // Make sure to call dequeue, so that the minimum item gets removed.
                return q1.dequeue();
            } else {
                return q2.dequeue();
            }
        }
    }

    /** Returns a queue of queues that each contain one item from items. */
    private static <Item extends Comparable> Queue<Queue<Item>>
            makeSingleItemQueues(Queue<Item> items) {
        // Your code here!
        Queue<Queue<Item>> ret = new Queue<>();
        for (Item item : items) {
            Queue<Item> subQueue = new Queue<>();
            subQueue.enqueue(item);
            ret.enqueue(subQueue);
        }
        return ret;
    }

    /**
     * Returns a new queue that contains the items in q1 and q2 in sorted order.
     *
     * This method should take time linear in the total number of items in q1 and q2.  After
     * running this method, q1 and q2 will be empty, and all of their items will be in the
     * returned queue.
     *
     * @param   q1  A Queue in sorted order from least to greatest.
     * @param   q2  A Queue in sorted order from least to greatest.
     * @return      A Queue containing all of the q1 and q2 in sorted order, from least to
     *              greatest.
     *
     */
    private static <Item extends Comparable> Queue<Item> mergeSortedQueues(
            Queue<Item> q1, Queue<Item> q2) {
        // Your code here!
        Queue<Item> ret = new Queue<>();
        int turns = q1.size() + q2.size();
        while (turns-- != 0) {
            ret.enqueue(getMin(q1, q2));
        }
        return ret;
    }

    /** Returns a Queue that contains the given items sorted from least to greatest. */
    public static <Item extends Comparable> Queue<Item> mergeSort(
            Queue<Item> items) {
        // Your code here!
        Queue<Queue<Item>> subQueues = makeSingleItemQueues(items);
        int len = subQueues.size();
        while (len > 1) {
            int turns = len / 2;
            for (int i = 0; i < turns; i++) {
                Queue<Item> q1 = subQueues.dequeue();
                Queue<Item> q2 = subQueues.dequeue();
                subQueues.enqueue(mergeSortedQueues(q1, q2));
            }
            len = len / 2 + len % 2;
        }
        assert subQueues.size() == 1;
        items = subQueues.dequeue();
        return items;
    }


     public static void main(String[] args) {
        Queue<String> qs = new Queue<>();
        MinPQ<String> ans1 = new MinPQ<>();
        qs.enqueue("Summer");
        qs.enqueue("Pockets");
        qs.enqueue("Daokémong");
        qs.enqueue("Ping-pong");
        qs.enqueue("Umi");
        qs.enqueue("Sea, You Next");
        qs.enqueue("Butterfly");
        qs.enqueue("Inari");
        ans1.insert("Summer");
        ans1.insert("Pockets");
        ans1.insert("Daokémong");
        ans1.insert("Ping-pong");
        ans1.insert("Umi");
        ans1.insert("Sea, You Next");
        ans1.insert("Butterfly");
        ans1.insert("Inari");
        Queue<String> res1 = mergeSort(qs);
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
        qi = mergeSort(qi);
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
