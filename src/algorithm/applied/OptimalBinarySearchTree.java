package algorithm.applied;

public class OptimalBinarySearchTree {

    record Result(double[][] e, int[][] root) { }

    public static Result optimalBST(double[] p, double[] q) {
        int n = p.length - 1;
        double[][] e = new double[n + 2][n + 1];
        double[][] w = new double[n + 2][n + 1];
        int[][] root = new int[n + 1][n + 1];
        for (int i = 0; i <= n; i++) { // base cases
            e[i][i] = q[i]; // equation (14.14)
            w[i][i] = q[i];
        }
        for (int l = 1; l <= n; l++) {
            for (int i = 0; i <= n - l; i++) {
                int j = i + l;
                e[i][j] = Double.MAX_VALUE;
                w[i][j] = w[i][j - 1] + p[j] + q[j]; // equation (14.15)
                for (int r = i; r < j; r++) { // try all possible roots r
                    double t = e[i][r] + e[r + 1][j] + w[i][j]; // equation (14.14)
                    if (t < e[i][j]) { // new minimum?
                        e[i][j] = t;
                        root[i][j] = r;
                    }
                }
            }
        }
        return new Result(e, root);
    }

    public static void main(String[] args) {
        double[] p = {0.0, 0.15, 0.10, 0.05, 0.10, 0.20};
        double[] q = {0.05, 0.10, 0.05, 0.05, 0.05, 0.10};
        Result result = optimalBST(p, q);
        System.out.println(result.e[0][p.length - 1]);
    }

}
