package algorithm.applied;

public class LongestCommonSubsequence {

    record Result(int[][] c, Direction[][] b) { }

    enum Direction {
        NORTHWEST, NORTH, WEST
    }

    public static Result lcsLength(char[] X, char[] Y) {
        int m = X.length;
        int n = Y.length;
        Direction[][] b = new Direction[m + 1][n + 1];
        int[][] c = new int[m + 1][n + 1];
        for (int i = 1; i <= m; i++) {
            c[i][0] = 0;
        }
        for (int j = 0; j <= n; j++) {
            c[0][j] = 0;
        }
        for (int i = 1; i <= m; i++) { // compute table entries in row-major order
            for (int j = 1; j <= n; j++) {
                if (X[i - 1] == Y[j - 1]) {
                    c[i][j] = c[i - 1][j - 1] + 1;
                    b[i][j] = Direction.NORTHWEST;
                } else if (c[i - 1][j] >= c[i][j - 1]) {
                    c[i][j] = c[i - 1][j];
                    b[i][j] = Direction.NORTH;
                } else {
                    c[i][j] = c[i][j - 1];
                    b[i][j] = Direction.WEST;
                }
            }
        }
        return new Result(c, b);
    }

    public static void printLCS(Direction[][] b, char[] X, int i, int j) {
        if (i == 0 || j == 0) {
            return; // the LCS has length 0
        }
        if (b[i][j] == Direction.NORTHWEST) {
            printLCS(b, X, i - 1, j - 1);
            System.out.print(X[i - 1]); // same as yj
        } else if (b[i][j] == Direction.NORTH) {
            printLCS(b, X, i - 1, j);
        } else {
            printLCS(b, X, i, j - 1);
        }
    }

    public static void main(String[] args) {
        char[] X = "ABCBDAB".toCharArray();
        char[] Y = "BDCABA".toCharArray();
        Result result = lcsLength(X, Y);
        printLCS(result.b, X, X.length, Y.length);
    }

}
