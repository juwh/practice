package algorithm.applied;

public class MatrixChainMultiplication {

    public static void rectangularMatrixMultiply(int[][] A, int[][] B, int[][] C) {
        int p = A.length;
        int q = B.length;
        int r = B[0].length;
        for (int i = 0; i < p; i++) {
            for (int j = 0; j < r; j++) {
                for (int k = 0; k < q; k++) {
                    C[i][j] += A[i][k] * B[k][j];
                }
            }
        }
    }

    record Result(int[][] m, int[][] s) { }

    public static Result matrixChainOrder(int[] p) {
        int n = p.length - 1;
        int[][] m = new int[n][n];
        int[][] s = new int[n][n];
        for (int l = 2; l <= n; l++) { // l is the chain length
            for (int i = 0; i < n - l + 1; i++) { // chain begins at Ai
                int j = i + l - 1; // chain ends at Aj
                m[i][j] = Integer.MAX_VALUE;
                for (int k = i; k < j; k++) { // try Ai:kAk+1:j
                    int q = m[i][k] + m[k + 1][j] + p[i] * p[k + 1] * p[j + 1];
                    if (q < m[i][j]) {
                        m[i][j] = q; // remember this cost
                        s[i][j] = k; // remember this index
                    }
                }
            }
        }
        return new Result(m, s);
    }

    public static void printOptimalParens(int[][] s, int i, int j) {
        if (i == j) {
            System.out.print("A" + i);
        } else {
            System.out.print("(");
            printOptimalParens(s, i, s[i][j]);
            printOptimalParens(s, s[i][j] + 1, j);
            System.out.print(")");
        }
    }

    public static int matrixChainOrderRecursive(int[] p, int i, int j) {
        if (i == j) {
            return 0;
        }
        int min = Integer.MAX_VALUE;
        for (int k = i; k < j; k++) {
            int q = matrixChainOrderRecursive(p, i, k) + matrixChainOrderRecursive(p, k + 1, j) + p[i] * p[k + 1] * p[j + 1];
            if (q < min) {
                min = q;
            }
        }
        return min;
    }

    public static int memoizedMatrixChain(int[] p) {
        int n = p.length - 1;
        int[][] m = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                m[i][j] = Integer.MAX_VALUE;
            }
        }
        return lookupChain(m, p, 0, n - 1);
    }

    private static int lookupChain(int[][] m, int[] p, int i, int j) {
        if (m[i][j] < Integer.MAX_VALUE) {
            return m[i][j];
        }
        if (i == j) {
            m[i][j] = 0;
        } else {
            for (int k = i; k < j; k++) {
                int q = lookupChain(m, p, i, k) + lookupChain(m, p, k + 1, j) + p[i] * p[k + 1] * p[j + 1];
                if (q < m[i][j]) {
                    m[i][j] = q;
                }
            }
        }
        return m[i][j];
    }

    public static void main(String[] args) {
        int[] p = { 30, 35, 15, 5, 10, 20, 25 };
        Result result = matrixChainOrder(p);
        printOptimalParens(result.s, 0, p.length - 2);
        System.out.println();
        System.out.println(matrixChainOrderRecursive(p, 0, p.length - 2));
        System.out.println(memoizedMatrixChain(p));
    }

}
