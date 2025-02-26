package synthesizer;

public abstract class AbstractBoundedQueue<T> implements BoundedQueue {

    protected int fillCount;
    protected int capacity;

    @Override
    public int capacity() {
        return capacity;
    }

    @Override
    public int fillCount() {
        return fillCount;
    }
}
