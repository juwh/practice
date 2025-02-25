package algorithm.rank;

import java.util.Random;

public class Quick {
    
    public static <T extends Comparable<T>> void quickSort(T[] A) {
        quickSort(A, 0, A.length - 1);
    }

    private static <T extends Comparable<T>> void quickSort(T[] A, int p, int r) {  
        if (p < r) {
            // Partition the subarray around the pivot, which ends up in A[q].
            int q = partition(A, p, r);
            quickSort(A, p, q - 1); // recursively sort the low side
            quickSort(A, q + 1, r); // recursively sort the high side
        }
    }

    private static <T extends Comparable<T>> int partition(T[] A, int p, int r) {
        T x = A[r]; // the pivot
        int i = p - 1; // highest index into the low side
        for (int j = p; j < r; j++) { // process each element other than the pivot
            if (A[j].compareTo(x) <= 0) { // does this element belong on the low side?
                i++; // index of a new slot in the low side
                T temp = A[i];
                A[i] = A[j]; // put this element there
                A[j] = temp;
            }
        }
        T temp = A[i + 1];
        A[i + 1] = A[r]; // pivot goes just right of the low side
        A[r] = temp;
        return i + 1; // new index of the pivot
    }

    public static <T extends Comparable<T>> void randomizedQuickSort(T[] A) {
        randomizedQuickSort(A, 0, A.length - 1);
    }

    private static <T extends Comparable<T>> void randomizedQuickSort(T[] A, int p, int r) {
        if (p < r) {
            int q = randomizedPartition(A, p, r);
            randomizedQuickSort(A, p, q - 1);
            randomizedQuickSort(A, q + 1, r);
        }
    }

    public static <T extends Comparable<T>> int randomizedPartition(T[] A, int p, int r) {
        Random random = new Random();
        int i = random.nextInt(r - p + 1) + p;
        T temp = A[r];
        A[r] = A[i];
        A[i] = temp;
        return partition(A, p, r);
    }

    public static void main(String[] args) {
        Integer[] arr = { 2, 8, 7, 1, 3, 5, 6, 4 };
        Integer[] copy = arr.clone();
        quickSort(arr);
        for (int i : arr) {
            System.out.print(i + " ");
        }
        System.out.println();
        randomizedQuickSort(copy);
        for (int i : copy) {
            System.out.print(i + " ");
        }
    }

}
