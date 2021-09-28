import java.util.Iterator;
import java.util.NoSuchElementException;

public class SimpleArrayDeque<T> implements SimpleDeque<T> {

    T[] q;
    int left, right;
    int size, capacity;
    /**
     * Constructs a new array based deque with limited capacity.
     * 
     * @param capacity the capacity
     * @throws IllegalArgumentException if capacity <= 0
     */
    public SimpleArrayDeque(int capacity) throws IllegalArgumentException {
        if (capacity <= 0)
            throw new IllegalArgumentException();
        this.q = (T[])new Object[capacity];
        this.left = capacity/2; this.right = this.left;
        this.size = 0; this.capacity = capacity;
    }

    /**
     * Constructs a new array based deque with limited capacity, and initially populates the deque
     * with the elements of another SimpleDeque.
     *
     * @param otherDeque the other deque to copy elements from. otherDeque should be left intact.
     * @param capacity the capacity
     * @throws IllegalArgumentException if capacity <= 0 or size of otherDeque is > capacity
     */
    public SimpleArrayDeque(int capacity, SimpleDeque<? extends T> otherDeque)
            throws IllegalArgumentException {
        if (capacity <= 0 || otherDeque.size() > capacity)
            throw new IllegalArgumentException();
        this.q = (T[])new Object[capacity];
        this.left = capacity/2; this.right = this.left;
        this.size = 0; this.capacity = capacity;
        Iterator<? extends T> it = otherDeque.iterator();
        while (it.hasNext()) {
            T o = it.next();
            pushLeft(o);
        }
    }

    @Override
    public boolean isEmpty() {
        return this.size == 0;
    }

    @Override
    public boolean isFull() {
        return this.size == this.capacity;
    }

    @Override
    public int size() {
        return this.size;
    }

    private static int moveToRight(int m, int capacity) {
        m += 1;
        if (m >= capacity)
            m = 0;
        return m;
    }

    private static int moveToLeft (int m, int capacity) {
        m -= 1;
        if (m < 0)
            m = capacity - 1;
        return m;
    }

    @Override
    public void pushLeft(T e) throws RuntimeException {
        if (isFull()) {
            throw new RuntimeException();
        }
        if (isEmpty()) {
            this.q[this.left] = e;
            this.size += 1;
            return;
        }
        this.left = moveToLeft(this.left, this.capacity);
        this.q[this.left] = e;
        this.size += 1;
    }

    @Override
    public void pushRight(T e) throws RuntimeException {
        if (isFull()) {
            throw new RuntimeException();
        }
        if (isEmpty()) {
            this.q[this.right] = e;
            this.size += 1;
            return;
        }
        this.right = moveToRight(this.right, this.capacity);
        this.q[this.right] = e;
        this.size += 1;
    }

    @Override
    public T peekLeft() throws NoSuchElementException {
        if (isEmpty())
            throw new NoSuchElementException();
        return this.q[this.left];
    }

    @Override
    public T peekRight() throws NoSuchElementException {
        if (isEmpty())
            throw new NoSuchElementException();
        return this.q[this.right];
    }

    @Override
    public T popLeft() throws NoSuchElementException {
        if (isEmpty())
            throw new NoSuchElementException();
        T o = this.q[this.left];
        this.left = moveToRight(this.left, this.capacity);
        this.size -= 1;
        return o;
    }

    @Override
    public T popRight() throws NoSuchElementException {
        T o = this.q[this.right];
        this.right = moveToLeft(this.right, this.capacity);
        this.size -= 1;
        return o;
    }

    private static class ArrayItr<T> implements Iterator<T> {
        private int cursor;
        private final int start, end, capacity;
        private int size;
        private final boolean reverse;
        private final T[] a;

        private ArrayItr(T[] a, int start, int end, int size, int capacity, boolean reverse) {
            this.a = a;
            this.start = start; this.end = end;
            this.capacity = capacity;
            this.size = size;
            this.reverse = reverse;
            if (this.size > this.a.length)
                throw new NoSuchElementException();
            this.cursor = this.start;
        }

        @Override
        public boolean hasNext() {
            return this.size > 0;
        }

        @Override
        public T next() {
            int i = this.cursor;
            if (this.size <= 0) {
                throw new NoSuchElementException();
            } else {
                if (this.reverse)
                    this.cursor = moveToRight(this.cursor,this.capacity);
                else
                    this.cursor = moveToLeft(this.cursor, this.capacity);
                this.size -= 1;
                return this.a[i];
            }
        }
    }

    @Override
    public Iterator<T> iterator() {
        return new ArrayItr<T>(this.q, this.left, this.right, this.size, this.capacity, true);
    }

    @Override
    public Iterator<T> reverseIterator() {
        return new ArrayItr<T>(this.q, this.right, this.left, this.size,this.capacity, false);
    }
}
