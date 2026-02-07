package algorithm.applied;

public class MatrixMultiply {

    public static void matrixMultiply(int[][] A, int[][] B, int[][] C) {
        int n = A.length;
        for (int i = 0; i < n; i++) { // compute entries in each of n rows
            for (int j = 0; j < n; j++) { // compute n entries in row i
                for (int k = 0; k < n; k++) {
                    C[i][j] += A[i][k] * B[k][j]; // add in another term of equation (4.1)
                }
            }
        }
    }

    // we'll assume that n is an exact power of 2
    public static void matrixMultiplyRecursive(int[][] A, int[][] B, int[][] C, int rowA, int colA, int rowB, int colB, int rowC, int colC, int n) {
        if (n == 1) {
            // Base case
            C[rowC][colC] += A[rowA][colA] * B[rowB][colB];
            return;
        }
        // Divide
        int offset = n / 2;
        // Conquer
        matrixMultiplyRecursive(A, B, C, rowA, colA, rowB, colB, rowC, colC, n / 2);
        matrixMultiplyRecursive(A, B, C, rowA, colA, rowB, colB + offset, rowC, colC + offset, n / 2);
        matrixMultiplyRecursive(A, B, C, rowA + offset, colA, rowB, colB, rowC + offset, colC, n / 2);
        matrixMultiplyRecursive(A, B, C, rowA + offset, colA, rowB, colB + offset, rowC + offset, colC + offset, n / 2);
        matrixMultiplyRecursive(A, B, C, rowA, colA + offset, rowB + offset, colB, rowC, colC, n / 2);
        matrixMultiplyRecursive(A, B, C, rowA, colA + offset, rowB + offset, colB + offset, rowC, colC + offset, n / 2);
        matrixMultiplyRecursive(A, B, C, rowA + offset, colA + offset, rowB + offset, colB, rowC + offset, colC, n / 2);
        matrixMultiplyRecursive(A, B, C, rowA + offset, colA + offset, rowB + offset, colB + offset, rowC + offset, colC + offset, n / 2);
    }

    public static void main(String[] args) {
        int[][] A = { { 1, 2, 3, 4 }, { 5, 6, 7, 8 }, { 9, 10, 11, 12 }, { 13, 14, 15, 16 } };
        int[][] B = { { 1, 2, 3, 4 }, { 5, 6, 7, 8 }, { 9, 10, 11, 12 }, { 13, 14, 15, 16 } };
        int[][] C = new int[A.length][A.length];
        matrixMultiply(A, B, C);
        //matrixMultiplyRecursive(A, B, C, 0, 0, 0, 0, 0, 0, 4);
        for (int i = 0; i < A.length; i++) {
            for (int j = 0; j < A.length; j++) {
                System.out.print(C[i][j] + " ");
            }
            System.out.println();
        }
    }

}
