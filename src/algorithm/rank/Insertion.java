package algorithm.rank;

import java.util.List;

public class Insertion {

    public static void insertionSort(int[] A) {
        for (int i = 0; i < A.length; i++) {
            int key = A[i];
            // Insert A[i] into the sorted subarray A[0 : i - 1].
            int j = i - 1;
            while (j >= 0 && A[j] > key) {
                A[j + 1] = A[j];
                j--;
            }
            A[j + 1] = key;
        }
    }

    public static <T extends Comparable<T>> void insertionSort(List<T> A) {
        for (int i = 0; i < A.size(); i++) {
            T key = A.get(i);
            int j = i - 1;
            while (j >= 0 && A.get(j).compareTo(key) > 0) {
                A.set(j + 1, A.get(j));
                j--;
            }
            A.set(j + 1, key);
        }
    }

    public static void main(String[] args) {
        int[] arr = { 5, 2, 4, 6, 1, 3 };
        insertionSort(arr);
        for (int i : arr) {
            System.out.print(i + " ");
        }
    }

}
