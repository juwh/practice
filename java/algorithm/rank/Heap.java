package algorithm.rank;

public class Heap {

    public static int parent(int i) {
        return (i - 1) / 2;
    }

    public static int left(int i) {
        return 2 * i + 1;
    }

    public static int right(int i) {
        return 2 * i + 2;
    }

    public static <T extends Comparable<T>> void maxHeapify(T[] A, int i, int heapSize) {
        int l = left(i);
        int r = right(i);
        int largest = i;
        if (l < heapSize && A[l].compareTo(A[i]) > 0) {
            largest = l;
        }
        if (r < heapSize && A[r].compareTo(A[largest]) > 0) {
            largest = r;
        }
        if (largest != i) {
            T temp = A[i];
            A[i] = A[largest];
            A[largest] = temp;
            maxHeapify(A, largest, heapSize);
        }
    }

    public static <T extends Comparable<T>> void buildMaxHeap(T[] A) {
        int heapSize = A.length;
        for (int i = A.length / 2; i >= 0; i--) {
            maxHeapify(A, i, heapSize);
        }
    }

    public static <T extends Comparable<T>> void heapSort(T[] A) {
        int heapSize = A.length;
        buildMaxHeap(A);
        for (int i = A.length - 1; i >= 1; i--) {
            T temp = A[0];
            A[0] = A[i];
            A[i] = temp;
            maxHeapify(A, 0, --heapSize);
        }
    }

    public static void main(String[] args) {
        Integer[] arr = { 4, 1, 3, 2, 16, 9, 10, 14, 8, 7 };
        Integer[] copy = arr.clone();
        buildMaxHeap(arr);
        for (int i : arr) {
            System.out.print(i + " ");
        }
        System.out.println();
        heapSort(copy);
        for (int i : copy) {
            System.out.print(i + " ");
        }
    }

}
