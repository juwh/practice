package algorithm.applied;

import java.util.Deque;
import java.util.LinkedList;

public class ActivitySelection {

    public static Deque<Integer> recursiveActivitySelector(int[] s, int[] f, int k) {
        int m = k + 1;
        int n = s.length - 1;
        while (m <= n && s[m] < f[k]) { // find the first activity in Sk to finish
            m++;
        }
        if (m <= n) {
            Deque<Integer> result = recursiveActivitySelector(s, f, m);
            result.addFirst(m);
            return result;
        } else {
            return new LinkedList<>();
        }
    }

    public static Deque<Integer> greedyActivitySelector(int[] s, int[] f) {
        Deque<Integer> result = new LinkedList<>();
        result.add(1);
        int k = 1;
        for (int m = 2; m < s.length; m++) {
            if (s[m] >= f[k]) { // is am in Sk?
                result.add(m); // yes, so choose it
                k = m; // and continue from there
            }
        }
        return result;
    }

    public static void main(String[] args) {
        int[] s = {0, 1, 3, 0, 5, 3, 5, 6, 8, 8, 2, 12};
        int[] f = {0, 4, 5, 6, 7, 9, 9, 10, 11, 12, 14, 16};
        Deque<Integer> result = recursiveActivitySelector(s, f, 0);
        System.out.println(result);
        result = greedyActivitySelector(s, f);
        System.out.println(result);
    }

}
