import java.lang.Math;

public class ArrayDeque<T> {
    private T[] arr;
    private int front;
    private int back;

    private static final int INITIAL_CAPACITY = 8;
    private static final int SMALL_ARR_THRESHOLD = 16;
    private static final double USAGE_FACTOR = 0.25;

    public ArrayDeque() {
        arr = (T[]) new Object[INITIAL_CAPACITY];
        front = 0;
        back = 0;
    }

    private void expand() {
        if (front == back) {
            T[] newArr = (T[]) new Object[arr.length * 2];
            for (int ptr = 0; ptr < arr.length; ptr++)
                newArr[ptr] = arr[(ptr + front) % arr.length];

            back = arr.length;
            arr = newArr;
            front = 0;
        }
    }

    private void shrink() {
        if (arr.length < SMALL_ARR_THRESHOLD)
            return;
        int size = size();
        if (size < Math.floor(arr.length * USAGE_FACTOR)) {
            T[] newArr = (T[]) new Object[arr.length / 2];
            for (int i = 0; i < size; i++)
                newArr[i] = arr[(front + i) % arr.length];

            arr = newArr;
            front = 0;
            back = size;
        }
    }

    public void addFirst(T item) {
        front = (front - 1 + arr.length) % arr.length;
        arr[front] = item;
        expand();
    }

    public void addLast(T item) {
        arr[back] = item;
        back = (back + 1) % arr.length;
        expand();
    }

    public boolean isEmpty() {
        return front == back;
    }

    public int size() {
        int gap = back - front;
        return (back >= front) ? gap : arr.length + gap;
    }

    public void printDeque() {
        int size = size();
        for (int i = 0; i < size; i++)
            System.out.print(arr[(front + i) % arr.length] + " ");
    }

    public T removeFirst() {
        if (front == back) return null;
        T res = arr[front];
        front = (front + 1) % arr.length;
        shrink();
        return res;
    }

    public T removeLast() {
        if (front == back) return null;
        back = (back - 1) % arr.length;
        T res = arr[back];
        shrink();
        return res;
    }

    public T get(int index) {
       return arr[(front + index) % arr.length];
    }
}