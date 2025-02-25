package algorithm.rank;

public class Bubble {

    public static void bubbleSort(int[] A) {
        for (int i = 0; i < A.length - 1; i++) {
            for (int j = A.length - 1; j > i; j--) {
                if (A[j] < A[j - 1]) {
                    int temp = A[j];
                    A[j] = A[j - 1];
                    A[j - 1] = temp;
                }
            }
        }
    }

    public static void main(String[] args) {
        int[] arr = { 5, 2, 4, 6, 1, 3 };
        bubbleSort(arr);
        for (int i : arr) {
            System.out.print(i + " ");
        }
    }

}
