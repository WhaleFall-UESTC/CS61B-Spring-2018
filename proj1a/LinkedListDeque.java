public class LinkedListDeque<T> {
    private int size;
    private Node sentinel;

    private class Node {
        private T item;
        private Node front;
        private Node back;

        Node(T item, Node front, Node back) {
            this.item = item;
            this.front = front;
            this.back = back;
        }
    }

    public LinkedListDeque() {
        size = 0;
        sentinel = new Node(null, null, null);
        sentinel.front = sentinel;
        sentinel.back = sentinel;
    }

    public void addFirst(T item) {
        Node curFirst = sentinel.front;
        sentinel.front = new Node(item, sentinel, curFirst);
        curFirst.front = sentinel.front;
        if (size == 0) {
            curFirst.back = sentinel.front;
        }
        size++;
    }

    public void addLast(T item) {
        Node curLast = sentinel.back;
        sentinel.back = new Node(item, curLast, sentinel);
        curLast.back = sentinel.back;
        if (size == 0) {
            curLast.front = sentinel.back;
        }
        size++;
    }

    public boolean isEmpty() {
        return (size == 0);
    }

    public int size() {
        return size;
    }

    public void printDeque() {
        Node ptr = sentinel.front;
        for (int i = 0; i < size; i++) {
            System.out.print(ptr.item + " ");
            ptr = ptr.back;
        }
    }

    public T removeFirst() {
        if (size == 0) {
            return null;
        }
        // save the first item
        T res = sentinel.front.item;
        // sentinal points to the second
        sentinel.front = sentinel.front.back;
        // the second points to sentinel
        sentinel.front.front = sentinel;
        size--;
        if (size == 0) {
            sentinel.back = sentinel;
        }
        return res;
    }

    public T removeLast() {
        if (size == 0) {
            return null;
        }
        T res = sentinel.back.item;
        sentinel.back = sentinel.back.front;
        sentinel.back.back = sentinel;
        size--;
        if (size == 0) {
            sentinel.front = sentinel;
        }
        return res;
    }

    public T get(int index) {
        if (index >= size || index < 0) {
            return null;
        } else if (index <= size / 2) {
            Node ptr = sentinel.front;
            for (int i = 0; i < index; i++) {
                ptr = ptr.back;
            }
            return ptr.item;
        } else {
            Node ptr = sentinel.back;
            for (int i = size - 1; i > index; i--) {
                ptr = ptr.front;
            }
            return ptr.item;
        }
    }

    private Node getNodeRecursive(int index) {
        if (index == 0) {
            return sentinel.front;
        } else {
            return getNodeRecursive(index - 1).back;
        }
    }

    private Node getNodeReverse(int index) {
        if (index == size - 1) {
            return sentinel.back;
        } else {
            return getNodeReverse(index + 1).front;
        }
    }

    public T getRecursive(int index) {
        if (index >= size || index < 0) {
            return null;
        } else if (index <= size / 2) {
            return getNodeRecursive(index).item;
        } else {
            return getNodeReverse(index).item;
        }
    }
}
