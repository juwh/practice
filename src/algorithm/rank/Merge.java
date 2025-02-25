package algorithm.rank;

public class Merge {

    public static void mergeSort(int[] a, int p, int r) {
        if (p < r) { // greater than one element?
            int q = p + (r - p) / 2; // midpoint of A[p : r]
            mergeSort(a, p, q); // recursively sort A[p : q]
            mergeSort(a, q + 1, r); // recursively sort A[q + 1 : r]
            // Merge A[p : q] and A[q + 1 : r] into A[p : r].
            merge(a, p, q, r);
        }
    }

    private static void merge(int[] A, int p, int q, int r) {
        int nL = q - p + 1; // length of A[p : q]
        int nR = r - q; // length of A[q + 1 : r]
        int[] L = new int[nL];
        int[] R = new int[nR];
        for (int i = 0; i < nL; i++) { // copy A[p : q] to L[0 : nL - 1]
            L[i] = A[p + i];
        }
        for (int j = 0; j < nR; j++) { // copy A[q + 1 : r] to R[0 : nR - 1]
            R[j] = A[q + j + 1];
        }
        int i = 0; // i indexes the smallest remaining element in L
        int j = 0; // j indexes the smallest remaining element in R
        int k = p; // k indexes the location in A to fill
        // As long as each of the arrays L and R contains an unmerged element,
        // copy the smallest unmerged element back into A[p : r].
        while (i < nL && j < nR) {
            if (L[i] <= R[j]) {
                A[k] = L[i];
                i++;
            } else {
                A[k] = R[j];
                j++;
            }
            k++;
        }
        // Having gone through one of L and R entirely, copy the
        // remainder of the other to the end of A[p : r].
        while (i < nL) {
            A[k] = L[i];
            i++;
            k++;
        }
        while (j < nR) {
            A[k] = R[j];
            j++;
            k++;
        }
    }

    public static void main(String[] args) {
        int[] arr = { 12, 3, 7, 9, 14, 6, 11, 2 };
        mergeSort(arr, 0, arr.length - 1);
        for (int i : arr) {
            System.out.print(i + " ");
        }
    }

}
