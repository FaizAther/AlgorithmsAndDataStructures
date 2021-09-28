public class SimpleNode<T> {
    T               data;
    SimpleNode<T>   prev;
    SimpleNode<T>   next;

    public SimpleNode (T data) {
        this.prev = this.next = null;
        this.data = data;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public SimpleNode<T> getPrev() {
        return prev;
    }

    private void setPrev_(SimpleNode<T> prev) {
        this.prev = prev;
    }

    public void setPrev(SimpleNode<T> prev) {
        this.setPrev_(prev);
        if (prev != null)
            prev.setNext_(this);
    }

    public SimpleNode<T> getNext() {
        return next;
    }

    private void setNext_(SimpleNode<T> next) {
        this.next = next;
    }
    public void setNext(SimpleNode<T> next) {
        this.setNext_(next);
        if (next != null)
            next.setPrev_(this);
    }
}
