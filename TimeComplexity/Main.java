import java.util.Arrays;
import java.util.Random;

import static org.junit.Assert.assertArrayEquals;

public class Main {
    public static void timeSort() {
        Random rd = new Random();
        Random objectInteger[];
        int[] sizes = {5, 10, 50, 100, 500, 1000, 10000};
        for (int s : sizes) {
            Integer[] arr = new Integer[s];
            Integer[] arrr = new Integer[s];
            for (int i = 0; i < arr.length; i++) {
                arr[i] = rd.nextInt();// storing random integers in an array
                arrr[i] = arr[i];
            }
            //SortingAlgorithms.selectionSort(arr, true);
            long start = System.currentTimeMillis();
            SortingAlgorithms.mergeSort(arr, false);
            long end = System.currentTimeMillis();
            System.out.println("size of " + s + " for q " + (end - start) + "ms");
            Arrays.sort(arrr);
            assertArrayEquals(arr, arrr);
        }
    }
    public static void main (String[] args) {
        Integer arr[] = {10,9,7,4,2};
        Integer arrr[] = {10,9,7,4,2};
        SortingAlgorithms.mergeSort(arr, false);
        Arrays.sort(arrr);
        assertArrayEquals(arr, arrr);
        timeSort();
    }
}
