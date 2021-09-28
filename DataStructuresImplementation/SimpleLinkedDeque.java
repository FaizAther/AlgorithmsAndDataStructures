import java.util.Iterator;
import java.util.NoSuchElementException;

public class SimpleLinkedDeque<T> implements SimpleDeque<T> {

    SimpleList<T>   l;
    int             capacity;
    /**
     * Constructs a new linked list based deque with unlimited capacity.
     */
    public SimpleLinkedDeque() {
        this.l = new SimpleList<T>();
        this.capacity = 0;
    }

    /**
     * Constructs a new linked list based deque with limited capacity.
     *
     * @param capacity the capacity
     * @throws IllegalArgumentException if capacity <= 0
     */
    public SimpleLinkedDeque(int capacity) throws IllegalArgumentException {
        if (capacity <= 0)
            throw new IllegalArgumentException();
        this.capacity = capacity;
        this.l = new SimpleList<T>();
    }

    /**
     * Constructs a new linked list based deque with unlimited capacity, and initially 
     * populates the deque with the elements of another SimpleDeque.
     *
     * @param otherDeque the other deque to copy elements from. otherDeque should be left intact.
     * @requires otherDeque != null
     */
    public SimpleLinkedDeque(SimpleDeque<? extends T> otherDeque) {
        if (otherDeque == null)
            throw new NoSuchElementException();
        this.capacity = 0;
        this.l = new SimpleList<T>();
        Iterator<? extends T> it = otherDeque.iterator();
        while (it.hasNext()){
            this.l.append(it.next());
        }
    }
    
    /**
     * Constructs a new linked list based deque with limited capacity, and initially 
     * populates the deque with the elements of another SimpleDeque.
     *
     * @param otherDeque the other deque to copy elements from. otherDeque should be left intact.
     * @param capacity the capacity
     * @throws IllegalArgumentException if capacity <= 0 or size of otherDeque is > capacity
     */
    public SimpleLinkedDeque(int capacity, SimpleDeque<? extends T> otherDeque) 
            throws IllegalArgumentException {
        if (capacity <= 0 || otherDeque.size() > capacity) {
            throw new IllegalArgumentException();
        }
        this.capacity = capacity;
        this.l = new SimpleList<T>();
        Iterator<? extends T> it = otherDeque.iterator();
        while (it.hasNext()){
            this.l.append(it.next());
        }
    }

    @Override
    public boolean isEmpty() {
        return size() == 0;
    }

    @Override
    public boolean isFull() {
        return this.capacity != 0 && size() == capacity;
    }

    @Override
    public int size() {
        return this.l.getSize();
    }

    @Override
    public void pushLeft(T e) throws RuntimeException {
        if (isFull())
            throw new RuntimeException();
        this.l.prepend(e);
    }

    @Override
    public void pushRight(T e) throws RuntimeException {
        if (isFull())
            throw new RuntimeException();
        this.l.append(e);
    }

    @Override
    public T peekLeft() throws NoSuchElementException {
        return this.l.bottom_peek();
    }

    @Override
    public T peekRight() throws NoSuchElementException {
        return this.l.top_peek();
    }

    @Override
    public T popLeft() throws NoSuchElementException {
        return this.l.bottom();
    }

    @Override
    public T popRight() throws NoSuchElementException {
        return this.l.top();
    }

    @Override
    public Iterator<T> iterator() {
        return new SimpleList.ListItr<T>(this.l.getHead(), false);
    }

    @Override
    public Iterator<T> reverseIterator() {
        return new SimpleList.ListItr<T>(this.l.getTail(), true);
    }

}
