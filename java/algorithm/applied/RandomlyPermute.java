package algorithm.applied;

import java.util.Random;

public class RandomlyPermute {

    public static void randomPermute(int[] A) {
        Random random = new Random();
        for (int i = 0; i < A.length; i++) {
            int swap = random.nextInt(A.length - i) + i;
            int temp = A[i];
            A[i] = A[swap];
            A[swap] = temp;
        }
    }

    public static void main(String[] args) {
        // randomly permute an array of 5 elements 100 times and keep track of
        // the number of times each element appears in each position and print
        // the results
        int[][] counts = new int[5][5];
        for (int i = 0; i < 100; i++) {
            int[] A = { 1, 2, 3, 4, 5 };
            randomPermute(A);
            for (int j = 0; j < A.length; j++) {
                counts[A[j] - 1][j]++;
            }
        }
        for (int i = 0; i < counts.length; i++) {
            for (int j = 0; j < counts[i].length; j++) {
                System.out.print(counts[i][j] + " ");
            }
            System.out.println();
        }
    }

}
