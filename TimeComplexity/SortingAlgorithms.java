public class SortingAlgorithms {
    /**
     * Sorts the given array using the selection sort algorithm.
     * This should modify the array in-place.
     *
     * @param input An array of comparable objects.
     * @param// reversed If false, the array should be sorted ascending.
     * Otherwise, it should be sorted descending.
     * @requires input != null
     */
    private static <T extends Comparable> int findIndex(T[] input, int i, int j, int index, boolean reversed) {
        if (reversed && input[j].compareTo(input[i]) > 0) { // j is bigger than i
            if (index == i)
                index = j;
            else {
                if (input[j].compareTo(input[index]) > 0)
                    index = j;
            }
        } else if (!reversed && input[j].compareTo(input[i]) < 0) {// j is smaller than i
            if (index == i)
                index = j;
            else {
                if (input[j].compareTo(input[index]) < 0)
                    index = j;
            }
        }
        return index;
    }

    private static <T> void swap(T[] input, int i, int j) {
        T e = input[i];
        input[i] = input[j];
        input[j] = e;
    }

    static <T extends Comparable> void selectionSort(T[] input, boolean reversed) {
        if (input == null)
            return;
        else if (input.length == 1)
            return;
        int index = 0;
        for (int i = 0; i < input.length; i++) {
            for (int j = i + 1; j < input.length; j++) {
                index = findIndex(input, i, j, index, reversed);
            }
            if (index != i) {
                swap(input, i, index);
            }
            index = i + 1;
        }
    }

    private static <T extends Comparable> void insertionSort_(T[] input, int start, int end, boolean reversed) {
        if (input == null)
            return;
        else if (end - start + 1 <= 1)
            return;
        int sorted = start + 1;
        int index = sorted;
        while (sorted < end + 1) {
            for (int i = sorted - 1; i >= 0; i--) {
                if (!reversed && input[i].compareTo(input[sorted]) > 0) {
                    index = i;
                    if (index != sorted && input[i].compareTo(input[index]) > 0) {
                        index = i;
                    }
                } else if (reversed && input[i].compareTo(input[sorted]) < 0) {
                    index = i;
                    if (index != sorted && input[i].compareTo(input[index]) < 0) {
                        index = i;
                    }
                }
            }
            // if index is not equal to sorted then we get the position of insertion
            if (index != sorted) {
                // shift right
                for (int i = sorted; i - index > 0; i--) {
                    swap(input, i, i - 1);
                }
            }
            sorted += 1;
            index = sorted;
        }
    }

    /**
     * Sorts the given array using the insertion sort algorithm.
     * This should modify the array in-place.
     *
     * @param input    An array of comparable objects.
     * @param reversed If false, the array should be sorted ascending.
     *                 Otherwise, it should be sorted descending.
     * @requires input != null
     */
    static <T extends Comparable> void insertionSort(T[] input, boolean reversed) {
        if (input == null)
            return;
        else if (input.length == 1)
            return;
        insertionSort_(input, 0, input.length - 1, reversed);
    }

    /**
     * Sorts the given array using the merge sort algorithm.
     * This should modify the array in-place.
     *
     * @param input    An array of comparable objects.
     * @param reversed If false, the array should be sorted ascending.
     *                 Otherwise, it should be sorted descending.
     * @requires input != null
     */
    static <T extends Comparable> void mergeSort(T[] input, boolean reversed) {
        if (input == null)
            return;
        else if (input.length == 1)
            return;
        mergeSort_(input, 0, input.length - 1, reversed);

    }

    private static <T extends Comparable> void mergeSort_(T[] input, int l, int r, boolean reversed) {
        if (input == null)
            return;
        else if (l >= r)
            return;
        else if (r-l == 1){
            //sort these 2 elements and return
            insertionSort_(input, l, r, reversed);
            return;
        }
        int m = (l + r + 1) / 2;
        mergeSort_(input, l, m-1, reversed);
        mergeSort_(input, m, r, reversed);

        merge(input, l, m, r, reversed);
    }

    private static <T extends Comparable> void merge(T[] input, int l, int m, int r, boolean reversed) {
        int max = r - l + 1;
        for (int c = 0; c < max; c++) {
            if (!reversed) {
                if (input[l].compareTo(input[m]) < 1) {
                    l += 1;
                } else {
                    swap(input, l, m);
                    l += 1;
                }
            } else {
                if (input[l].compareTo(input[m]) < 1) {
                    swap(input, l, m);
                    l += 1;
                } else {
                    l += 1;
                }
            }
        }
    }


    /**
     * Sorts the given array using the quick sort algorithm.
     * This should modify the array in-place.
     * <p>
     * You should use the value at the middle of the input  array(i.e. floor(n/2))
     * as the pivot at each step.
     *
     * @param input    An array of comparable objects.
     * @param reversed If false, the array should be sorted ascending.
     *                 Otherwise, it should be sorted descending.
     * @requires input != null
     */
    static <T extends Comparable> void quickSort(T[] input, boolean reversed) {
        if (input == null || input.length <= 1) {
            return;
        }
        quickSort_(input, 0, input.length - 1, reversed);
    }

    private static <T extends Comparable> void quickSort_(T[] input, int l, int r, boolean reversed) {
        if (l < r-1) {
            int p = quick_partition_(input, l, r, reversed);
            quickSort_(input, l, p, reversed);
            quickSort_(input, p + 1, r, reversed);
        }
    }

    private static <T extends Comparable> int quick_partition_(T[] input, int l, int r, boolean reversed) {
        int p = (r + l + 1) / 2;
        T pivot = input[(r - l + 1) / 2];
        int i = p - 1;
        for (int j = p + 1; j <= r; j++) {
            // get to value on left
            while (i >= l && !reversed && pivot.compareTo(input[i]) > 1) i--;
            while (j <= r && !reversed && pivot.compareTo(input[j]) < 1) j++;

            while (i >= l && reversed && pivot.compareTo(input[i]) < 1) i--;
            while (j <= r && reversed && pivot.compareTo(input[j]) > 1) j++;

            if (i >= l || j <= r) {
                if (i >= l){
                    insertionSort_(input, i, r, reversed);
                    j +=1;
                }
                if (j <= r) {
                    insertionSort_(input, l,j, reversed);
                }
            }
            i -= 1;
        }
        return p;
    }
}


