package algorithm.rank;

import java.util.ArrayList;
import java.util.List;

public class Bucket {

    // assume that 0 <= A[i] < 1
    public static double[] bucketSort(double[] A) {
        int n = A.length;
        List<List<Double>> B = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            B.add(new ArrayList<>());
        }
        for (int i = 0; i < n; i++) {
            B.get((int) (n * A[i])).add(A[i]);
        }
        for (int i = 0; i < n; i++) {
            Insertion.insertionSort(B.get(i));
        }
        List<Double> concatenatedLists = new ArrayList<>();
        for (List<Double> list : B) {
            concatenatedLists.addAll(list);
        }
        return concatenatedLists.stream().mapToDouble(d -> d).toArray();
    }

    public static void main(String[] args) {
        double[] arr = { 0.78, 0.17, 0.39, 0.26, 0.72, 0.94, 0.21, 0.12, 0.23, 0.68 };
        double[] sortedArr = bucketSort(arr);
        for (double i : sortedArr) {
            System.out.print(i + " ");
        }
    }

}
