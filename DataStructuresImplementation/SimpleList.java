import java.util.Iterator;
import java.util.NoSuchElementException;

public class SimpleList<T> {
    SimpleNode<T>   head;
    SimpleNode<T>   tail;
    SimpleNode<T>   curr;
    int             size;

    public SimpleList () {
        head = tail = curr = null;
        size = 0;
    }

    public SimpleNode<T> getHead() {
        return head;
    }

    public void setHead(SimpleNode<T> head) {
        this.head = head;
        //this.size += 1;
    }

    public SimpleNode<T> getTail() {
        return tail;
    }

    public void setTail(SimpleNode<T> tail) {
        this.tail = tail;
        //this.size += 1;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public boolean isEmpty () {
        return this.size == 0;
    }

    public void setHeadTail (SimpleNode<T> n) {
        setHead(n);
        setTail(n);
    }

    public void append(T t) {
        SimpleNode<T> n = new SimpleNode<T>(t);
        if (isEmpty())
            setHeadTail(n);
        else {
            this.tail.setNext(n);
            this.tail = n;
        }
        this.size += 1;
    }

    public void prepend (T t) {
        SimpleNode<T> n = new SimpleNode<T>(t);
        if (isEmpty())
            setHeadTail(n);
        else {
            this.head.setPrev(n);
            this.head = n;
        }
        this.size += 1;
    }

    public T top_peek () {
        if (isEmpty())
            throw new NoSuchElementException();
        return this.head.getData();
    }

    public T top () {
        if (isEmpty())
            throw new NoSuchElementException();
        T d = this.head.getData();

        if (this.size == 1) {
            setHeadTail(null);
            this.size -= 1;
            return d;
        }

        this.head = this.head.getNext();
        this.head.setPrev(null);
        this.size -= 1;
        return d;
    }

    public T bottom_peek() {
        if (isEmpty())
            throw new NoSuchElementException();
        return this.tail.getData();
    }

    public T bottom (){
        if (isEmpty())
            throw new NoSuchElementException();
        T d = this.tail.getData();

        if (this.size == 1) {
            setHeadTail(null);
            this.size -= 1;
            return d;
        }

        this.tail = this.tail.getPrev();
        this.tail.setNext(null);
        this.size -= 1;
        return d;
    }

    public static class ListItr<T> implements Iterator<T> {
        private SimpleNode<T> curr;
        private final boolean reverse;
        ListItr (SimpleNode<T> curr, boolean reverse) {
            this.curr = curr;
            this.reverse = reverse;
        }
        @Override
        public boolean hasNext() {
            return this.curr != null;
        }

        @Override
        public T next() {
            T e = this.curr.getData();
            if (reverse) {
                this.curr = this.curr.getPrev();
            }
            else {this.curr = this.curr.getNext();}
            return e;
        }
    }
}
