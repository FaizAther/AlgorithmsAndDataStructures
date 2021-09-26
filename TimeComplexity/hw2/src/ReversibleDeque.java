import java.util.Iterator;
import java.util.NoSuchElementException;

public class ReversibleDeque<T> implements SimpleDeque<T> {
    private SimpleDeque<T> d;
    private boolean reverse;
    /**
     * Constructs a new reversible deque, using the given data deque to store
     * elements.
     * The data deque must not be used externally once this ReversibleDeque
     * is created.
     * @param data a deque to store elements in.
     */
    public ReversibleDeque(SimpleDeque<T> data) {
        this.d = data;
        this.reverse = false;
    }

    public void reverse() {
        this.reverse = !this.reverse;
    }

    @Override
    public int size() {
        return d.size();
    }

    @Override
    public boolean isEmpty() {
        return d.isEmpty();
    }

    @Override
    public boolean isFull() {
        return d.isFull();
    }

    @Override
    public void pushLeft(T e) throws RuntimeException {
        if (this.reverse) {
            d.pushRight(e);
        } else {
            d.pushLeft(e);
        }
    }

    @Override
    public void pushRight(T e) throws RuntimeException {
        if (this.reverse) {
            d.pushLeft(e);
        } else {
            d.pushRight(e);
        }
    }

    @Override
    public T peekLeft() throws NoSuchElementException {
        if (this.reverse) {
            return d.peekRight();
        } else {
            return d.peekLeft();
        }
    }

    @Override
    public T peekRight() throws NoSuchElementException {
        if (this.reverse) {
            return d.peekLeft();
        } else {
            return d.peekRight();
        }
    }

    @Override
    public T popLeft() throws NoSuchElementException {
        if (this.reverse) {
            return d.popRight();
        } else {
            return d.popLeft();
        }
    }

    @Override
    public T popRight() throws NoSuchElementException {
        if (this.reverse) {
            return d.popLeft();
        } else {
            return d.popRight();
        }
    }

    @Override
    public Iterator<T> iterator() {
        return d.iterator();
    }

    @Override
    public Iterator<T> reverseIterator() {
        return d.reverseIterator();
    }
}
