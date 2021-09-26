import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * LinkedMultiHashSet is an implementation of a (@see MultiSet), using a hashtable as the internal
 * data structure, and with predictable iteration order based on the insertion order
 * of elements.
 * 
 * Its iterator orders elements according to when the first occurrence of the element 
 * was added. When the multiset contains multiple instances of an element, those instances 
 * are consecutive in the iteration order. If all occurrences of an element are removed, 
 * after which that element is added to the multiset, the element will appear at the end of the 
 * iteration.
 * 
 * The internal hashtable array should be doubled in size after an add that would cause it to be
 * at full capacity. The internal capacity should never decrease.
 * 
 * Collision handling for elements with the same hashcode (i.e. with hashCode()) should be done
 * using linear probing, as described in lectures.
 * 
 * @param <T> type of elements in the set
 */
public class LinkedMultiHashSet<T> implements MultiSet<T>, Iterable<T> {

    /**
     * Time Complexity O(1)
     * Space Complexity O(1)
     * */
    private static class SimpleStorage<T> {
        private final T   data;
        private final int hash;
        private int amount;
        private int occurance;

        public SimpleStorage (T data, int occurance) {
            this.data = data;
            this.hash = data.hashCode();
            this.amount = 1;
            this.occurance = occurance;
        }

        public SimpleStorage (T data, int amount, int occurance) {
            this.data = data;
            this.hash = data.hashCode();
            this.amount = amount;
            this.occurance = occurance;
        }

        public int getOccurance() {
            return this.occurance;
        }
        public void setOccurance(int occurance) {
            this.occurance = occurance;
        }

        public T getData() {
            return data;
        }

        public void addAmount() {
            addAmount(1);
        }

        public void addAmount(int a) {
            this.amount += a;
        }

        public int getAmount() {
            return this.amount;
        }

        public void removeAmount() {
            removeAmount(1);
        }

        public void removeAmount(int a) {
            this.amount -= a;
        }

        public void zeroAmount() {
            this.amount = 0;
        }

        public int getHash() {
            return this.hash;
        }

        public boolean hashEqual(int h) {
            return this.hash == h;
        }

    }

    private SimpleStorage<T>[] hashtable;
    private int[] occurance;
    private int curr_o;
    int distinct = 0;
    int size = 0;

    public LinkedMultiHashSet(int initialCapacity) {
        this.hashtable = (SimpleStorage<T>[]) new SimpleStorage[initialCapacity];
        this.occurance = new int[initialCapacity];
    }

    /**
     * Time Complexity O(1)
     * Space Complexity O(1)
     */
    private int calculatePosition(T element) {
        int hash = element.hashCode();
        if (hash < 0)
            hash *= -1;
        return hash % internalCapacity();
    }

    /**
     * Time Complexity O(1)
     * Space Complexity O(1)
     */
    private int[] getOccurance() {
        return this.occurance;
    }

    /**
     * Time Complexity O(1)
     * Space Complexity O(1)
     *
     * Mods the input value by the size of the hashtable
     *
     * @param pos to be fixed
     * @return pos modded by the internal capacity.
     * */
    private int fixPosition(int pos) {
        return pos % internalCapacity();
    }

    private SimpleStorage<T> getPosition(int pos) {
        return this.hashtable[fixPosition(pos)];
    }

    private void setPosition(T element, int pos, int o) {
        setPosition(element, 1, pos, o);
    }

    private void setPosition(T element, int count, int pos, int o) {
        this.hashtable[fixPosition(pos)] = new SimpleStorage<>(element, count, o);
    }

    private void setPosition(SimpleStorage<T> s_element, int pos) {
        this.hashtable[fixPosition(pos)] = s_element;
    }

    /**
     * Time Complexity O(1) on Average because it might have to span the array...
     * because of linear probing so O(n) where n is the internal capacity in that case
     *
     *  Space Complexity O(1)
     */
    public int getContains(T element) {
        int pos = calculatePosition(element);
        for (int i = 0; i < internalCapacity(); i++) {
            if (getPosition(pos+i) == null) {
                return -1;
            } else if (getPosition(fixPosition(pos+i)).hash == element.hashCode() &&
                    getPosition(fixPosition(pos+i)).getData().equals(element)) {
                return fixPosition(pos+i);
            }
        }
        return -1;
    }

    /**
     * Time Complexity O(n) where n is the internal capacity
     * Space Complexity O(n) where n is the internal capacity
     *
     * When the hashtable has been filled this function is called to create
     * a new hashtable, copy over the elements from the previous table...
     * but with recomputed positions.
     *
     * Also cleans the array as the placements are recomputed no longer need the redundant 0 data sitting there...
     * to move over for the probing search case.
     *
     * Sets the class variable hashtable to the new enlarged hashtable.
     * */
    private void enlarge() {
        int[] prev_o = this.occurance;
        this.occurance = new int[internalCapacity()*2];
        SimpleStorage<T>[] prev = this.hashtable;
        this.hashtable = (SimpleStorage<T>[]) new SimpleStorage[internalCapacity()*2];
        boolean placed;
        SimpleStorage<T> s_element;
        int j = 0;
        for (int k = 0; k < prev_o.length; k++) {
            while(prev_o[k] == -1) k++;
            s_element = prev[prev_o[k]];
            if (s_element.getAmount() > 0) {
                int pos = calculatePosition(s_element.data);
                placed = false;
                for (int i = 0; i < internalCapacity() && !placed; i++) {
                    if (getPosition(fixPosition(pos+i)) == null) {
                        setPosition(s_element, fixPosition(pos+i));
                        this.occurance[j] = fixPosition(pos+i);
                        j++;
                        placed = true;
                    }
                }
            }
        }
    }

    @Override
    public void add(T element) {
        add(element, 1);
    }

    /**
     * Time Complexity O(1) on Average because it might have to span the array...
     * because of linear probing so O(n) where n is the internal capacity in that case
     *
     * Space Complexity O(1)
     */
    @Override
    public void add(T element, int count) {

        int pos = getContains(element);
        if (pos != -1) {
            if (getPosition(pos).getAmount() == 0) {
                this.distinct++;
                this.occurance[curr_o] = pos;
                this.curr_o++;
            }
            getPosition(pos).addAmount(count);
        } else {
            pos = fixPosition(calculatePosition(element));
            for (int i = 0; i < internalCapacity(); i++) {
                if (getPosition(fixPosition(pos+i)) == null) {
                    setPosition(element, count, fixPosition(pos+i), distinctCount());
                    this.occurance[curr_o] = fixPosition(pos+i);
                    this.curr_o += 1;
                    this.distinct += 1;
                    if(this.curr_o == this.occurance.length) fix_occurance();
                    break;
                }
            }
        }
        this.size += count;
        if (distinctCount() == internalCapacity()) enlarge();
    }

    private void fix_occurance() {
        int pl = 0;
        for (int o:this.occurance)  {
            if (o == -1) continue;
            this.occurance[pl] = o;
            pl++;
        }
        this.curr_o = this.distinctCount();
    }

    /**
     * Time Complexity O(1) on Average because it might have to span the array...
     * because of linear probing so O(n) where n is the internal capacity in that case
     *
     * Space Complexity O(1)
     */
    @Override
    public boolean contains(T element) {
        int pos = calculatePosition(element);

        for (int i = 0; i < internalCapacity(); i++) {
            if (getPosition(fixPosition(pos+i)).getHash() == element.hashCode()) {
                return getPosition(fixPosition(pos + i)).getAmount() > 0;
            }
        }

        return false;
    }

    @Override
    public int count(T element) {
        int pos = getContains(element);

        return pos == -1?0:getPosition(pos).getAmount();
    }

    @Override
    public void remove(T element) throws NoSuchElementException {
        remove(element, 1);
    }

    /**
     * Time Complexity O(1) on Average because it might have to span the array...
     * because of linear probing so O(n) where n is the internal capacity in that case
     *
     * Space Complexity O(1)
     */
    @Override
    public void remove(T element, int count) throws NoSuchElementException {
        int pos = getContains(element);

        if(pos == -1 || getPosition(pos).getAmount() < count)
            throw new NoSuchElementException();

        if (getPosition(pos).getAmount() == 1) {
            getOccurance()[getPosition(pos).getOccurance()] = -1;
            getPosition(pos).setOccurance(-1);
            this.distinct -= 1;
        }

        getPosition(pos).removeAmount(count);

        this.size -= count;
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public int internalCapacity() {
        return this.hashtable.length;
    }

    @Override
    public int distinctCount() {
        return this.distinct;
    }

    @Override
    public Iterator<T> iterator() {
        return new MultiSetIterator<>(this);
    }

    /**
     * Custom Iterator class made for this linked multiset
     *
     * @param <T> type of the Iterator.
     * */
    private static class MultiSetIterator<T> implements Iterator<T> {

        private final LinkedMultiHashSet<T> m;
        private int curr_d = 0;
        private int curr_r = 0;
        private int count =0;

        /**
         * Constructor for the custom Iterator
         *
         * @param m, a LinkedMultiHashSet
         * */
        private MultiSetIterator(LinkedMultiHashSet<T> m) {
            this.m = m;
        }

        @Override
        public boolean hasNext() {
            return count < this.m.size();
        }

        /**
         * Time Complexity O(1) on Average because it might have to span the array...
         * because of linear probing so O(n) where n is the internal capacity in that case
         *
         * Space Complexity O(1)
         */
        @Override
        public T next() {
            if (hasNext()) {
                boolean found = false;
                while(!found) {
                    while (m.occurance[curr_d] == -1) curr_d++;
                    if (m.getPosition(m.occurance[curr_d]).getAmount() <= curr_r) {
                        curr_r = 0;
                        curr_d++;
                        continue;
                    }
                    found = true;
                }
                curr_r++;
                count++;
                return m.getPosition(m.occurance[curr_d]).getData();
            }
            return null;
        }
    }
}