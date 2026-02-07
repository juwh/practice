package algorithm.applied;

public class RodCutting {

    public static int cutRod(int[] p, int n) {
        if (n == 0) {
            return 0;
        }
        int q = Integer.MIN_VALUE;
        for (int i = 1; i <= n; i++) {
            q = Math.max(q, p[i - 1] + cutRod(p, n - i));
        }
        return q;
    }

    public static int memoizedCutRod(int[] p, int n) {
        int[] r = new int[n + 1]; // will remember solution values in r
        for (int i = 0; i <= n; i++) {
            r[i] = Integer.MIN_VALUE;
        }
        return memoizedCutRodAux(p, n, r);
    }

    private static int memoizedCutRodAux(int[] p, int n, int[] r) {
        if (r[n] >= 0) { // already have a solution for length n?
            return r[n];
        }
        int q;
        if (n == 0) {
            q = 0;
        } else {
            q = Integer.MIN_VALUE;
            for (int i = 1; i <= n; i++) { // i is the position of the first cut
                q = Math.max(q, p[i - 1] + memoizedCutRodAux(p, n - i, r));
            }
        }
        r[n] = q; // remember the solution value for length n
        return q;
    }

    public static int bottomUpCutRod(int[] p, int n) {
        int[] r = new int[n + 1]; // will remember solution values in r
        r[0] = 0;
        for (int j = 1; j <= n; j++) { // for increasing rod length j
            int q = Integer.MIN_VALUE;
            for (int i = 1; i <= j; i++) { // i is the position of the first cut
                q = Math.max(q, p[i - 1] + r[j - i]);
            }
            r[j] = q; // remember the solution value for length j
        }
        return r[n];
    }

    record Result(int[] r, int[] s) { }

    public static Result extendedBottomUpCutRod(int[] p, int n) {
        int[] r = new int[n + 1];
        int[] s = new int[n + 1];
        r[0] = 0;
        for (int j = 1; j <= n; j++) { // for increasing rod length j
            int q = Integer.MIN_VALUE;
            for (int i = 1; i <= j; i++) { // i is the position of the first cut
                if (q < p[i - 1] + r[j - i]) {
                    q = p[i - 1] + r[j - i];
                    s[j] = i; // best cut location so far for length j
                }
            }
            r[j] = q; // remember the solution value for length j
        }
        return new Result(r, s);
    }

    public static void printCutRodSolution(int[] p, int n) {
        Result result = extendedBottomUpCutRod(p, n);
        int[] s = result.s;
        while (n > 0) {
            System.out.print(s[n] + " "); // cut location for length n
            n -= s[n]; // length of the remainder of the rod
        }
    }

    public static void main(String[] args) {
        int[] p = {1, 5, 8, 9, 10, 17, 17, 20, 24, 30};
        int n = 10;
        System.out.println(cutRod(p, n));
        System.out.println(memoizedCutRod(p, n));
        System.out.println(bottomUpCutRod(p, n));
        Result result = extendedBottomUpCutRod(p, n);
        for (int i : result.r) {
            System.out.print(i + " ");
        }
        System.out.println();
        for (int i : result.s) {
            System.out.print(i + " ");
        }
        System.out.println();
        printCutRodSolution(p, n);
    }

}
