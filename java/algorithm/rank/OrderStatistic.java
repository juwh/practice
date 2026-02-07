package algorithm.rank;

import java.util.Arrays;

public class OrderStatistic {

    public static <T extends Comparable<T>> T minimum(T[] A) {
        T min = A[0];
        for (int i = 1; i < A.length; i++) {
            if (A[i].compareTo(min) < 0) {
                min = A[i];
            }
        }
        return min;
    }

    public static int[] minimumMaximum(int[] A) {
        int n = A.length;
        if (n == 0) {
            return new int[] {};
        }
        int min = A[0];
        int max = A[0];
        int rem = n % 2;
        if (rem == 0) {
            min = Math.min(A[0], A[1]);
            max = Math.max(A[0], A[1]);
        }
        for (int i = 2 + rem; i < A.length - 1; i += 2) {
            if (A[i] < A[i + 1]) {
                min = Math.min(min, A[i]);
                max = Math.max(max, A[i + 1]);
            } else {
                min = Math.min(min, A[i + 1]);
                max = Math.max(max, A[i]);
            }
        }
        return new int[] { min, max };
    }

    public static int randomizedSelect(Integer[] A, int i) {
        return randomizedSelect(A, 0, A.length - 1, i);
    }

    public static int randomizedSelect(Integer[] A, int p, int r, int i) {
        if (p == r) {
            return A[p]; // 1 <= i <= r - p + 1 when p == r means that i = 1
        }
        int q = Quick.randomizedPartition(A, p, r);
        int k = q - p + 1;
        if (i == k) {
            return A[q]; // the pivot value is the answer
        } else if (i < k) {
            return randomizedSelect(A, p, q - 1, i);
        } else {
            return randomizedSelect(A, q + 1, r, i - k);
        }
    }

    public static void main(String[] args) {
        int[] arr = { 6, 19, 4, 12, 14, 9, 15, 7, 8, 11, 3, 13, 2, 5, 10 };
        int[] minMax = minimumMaximum(arr);
        System.out.println("Minimum: " + minMax[0]);
        System.out.println("Maximum: " + minMax[1]);
        Integer[] boxedArr = Arrays.stream(arr).boxed().toArray(Integer[]::new);
        System.out.println(randomizedSelect(boxedArr, 5));
    }

}
