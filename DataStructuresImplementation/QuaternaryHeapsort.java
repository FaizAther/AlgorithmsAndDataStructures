public class QuaternaryHeapsort {

    /**
     * Space Complexity is O(n)
     * Time Complexity is O(n)
     * 
     * Sorts the input array, in-place, using a quaternary heap sort.
     *
     * @param input to be sorted (modified in place)
     */
    public static <T extends Comparable<T>> void quaternaryHeapsort(T[] input) {

        // the bubbles are in the right levels now have to sort the levels
        for (int start = 0; start < input.length; start++) {
            quaternaryDownheap(input, 0, input.length-start);
            swap (input, 0, input.length-start-1);
        }

    }

    /**
     * Space Complexity is O(n)
     * Time Complexity is O(n)
     * 
     * Performs a downheap from the element in the given position on the given max heap array.
     *
     * A downheap should restore the heap order by swapping downwards as necessary.
     * The array should be modified in place.
     *
     * @param input array representing a quaternary max heap.
     * @param start position in the array to start the downheap from.
     */
    public static <T extends Comparable<T>> void quaternaryDownheap(T[] input, int start, int size) {

        if (size <= 0 || size > input.length || start < 0 || start > input.length)
            return;

        int[] children = getChildren (start, input.length);

        if (children==null)
            return;
        else
            quaternaryDownheap(input, start + 1, size);

        int biggest = selectBig(input, start, children, size);

        if (biggest != start)
            swap (input, start, biggest);

    }

    /**
     * Space Complexity is O(1)
     * Time Complexity is O(n) for n children in the array
     * 
     * Finds the biggest child and returns the it, else returns the parent if the parent is the largest
     *
     * @param input the input array.
     * @param parent the index of the parent.
     * @param children the children array for the parent node
     * @param size the size to be manipulated
     * @return biggest of the children
     */
    private static <T extends Comparable<T>> int selectBig (T[] input, int parent, int[] children, int size) {
        int biggest = parent;
        boolean found = false;
        for (int c : children) {
            if ( c < size ) {
                if (!found && input[parent].compareTo(input[c]) < 0) {
                    biggest = c;
                    found = true;
                } else if (found && input[biggest].compareTo(input[c]) < 0) {
                    biggest = c;
                }
            }
        }
        return biggest;
    }

    /**
     * Space Complexity is O(1)
     * Time Complexity is O(1)
     * 
     * Computes the log4 by doing log10(x)/log10(4) using the change of base rule
     * @param p the parent
     * @return log4(p)
     */
    private static int log4ceil(int p) {
        return (int)Math.ceil((Math.log10(p)/Math.log10(4)));
    }

    /**
     * Space Complexity is O(1) because an array of 4 is created
     * Time Complexity is O(1)
     * 
     * Calculates the positions of the children
     *
     * @param p the parent
     * @param size the size of the array to be manipulated
     * @return children
     * */
    private static int[] getChildren (int p, int size) {
        if (p < 0)
            return null;
        int[] children = new int[4];
        if (p == 0) {
            children = new int[] {1,2,3,4};
        } else {
            int doCeil = (p==1?1:log4ceil(p));
            int toPow = (int) Math.pow(4, doCeil);
            int toPowP = (int) Math.pow(4,doCeil-1);
            children[0] = (int) toPow + 4 * (p-toPowP) + 1;
            children[1] = (int) toPow + 4 * (p-toPowP) + 2;
            children[2] = (int) toPow + 4 * (p-toPowP) + 3;
            children[3] = (int) toPow + 4 * (p-toPowP) + 4;
        }
        return children;
    }

    /**
     * Space Complexity is O(1) because 1 extra variable is used
     * Time Complexity is O(1)
     * 
     * Swaps the values in the input array of value a with value b
     * @param input is the array to be manipulated
     * @param a the first index
     * @param b the second index
     * */
    private static <T> void swap (T[] input, int a, int b) {
        T tmp = input[a];
        input[a] = input[b];
        input[b] = tmp;
    }
}