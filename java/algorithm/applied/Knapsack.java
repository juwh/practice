package algorithm.applied;

public class Knapsack {

    public static int knapsack(int[] v, int[] w, int W) {
        int n = v.length;
        int[][] dp = new int[n + 1][W + 1];
        // i determines the value/weight being added for a step
        for (int i = 1; i <= n; i++) {
            // j considers all end weights from weight added from i up to W
            for (int j = 0; j <= W; j++) {
                dp[i][j] = dp[i - 1][j];
                if (j >= w[i - 1]) {
                    dp[i][j] = Math.max(dp[i][j], dp[i - 1][j - w[i - 1]] + v[i - 1]);
                }
            }
        }
        return dp[n][W];
    }

    public static int knapsackFractional(int[] v, int[] w, int W) {
        int n = v.length;
        double[] d = new double[n];
        // calculate the value per weight
        for (int i = 0; i < n; i++) {
            d[i] = (double) v[i] / w[i];
        }
        int[] idx = new int[n];
        for (int i = 0; i < n; i++) {
            idx[i] = i;
        }
        // sort indices for the value per weight by value per weight
        // in descending order
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                // selection sort with excessive swaps
                if (d[idx[i]] < d[idx[j]]) {
                    int temp = idx[i];
                    idx[i] = idx[j];
                    idx[j] = temp;
                }
            }
        }
        int res = 0;
        for (int i = 0; i < n; i++) {
            if (w[idx[i]] <= W) {
                res += v[idx[i]];
                W -= w[idx[i]];
            } else {
                res += (int) (d[idx[i]] * W);
                break;
            }
        }
        return res;
    }

    public static void main(String[] args) {
        int[] v = { 60, 100, 120 };
        int[] w = { 10, 20, 30 };
        int W = 50;
        System.out.println(knapsack(v, w, W)); // 220
        System.out.println(knapsackFractional(v, w, W)); // 240
    }

}