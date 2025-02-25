package algorithm.rank;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import algorithm.adt.Element;

public class Counting {

    public static int[] countingSort(int[] A) {
        int k = Arrays.stream(A).max().getAsInt();
        int B[] = new int[A.length];
        int[] C = new int[k + 1];
        for (int j = 0; j < A.length; j++) {
            C[A[j]]++;
        }
        // C[i] now contains the number of elements equal to i.
        for (int i = 1; i <= k; i++) {
            C[i] += C[i - 1];
        }
        // C[i] now contains the number of elements less than or equal to i.
        // Copy A to B, starting from the end of A.
        for (int j = A.length - 1; j >= 0; j--) {
            B[C[A[j]] - 1] = A[j];
            C[A[j]]--; // to handle duplicate values
        }
        return B;
    }

    public static <T> List<Element<Integer, T>> countingSort(List<Element<Integer, T>> A) {
        Element<Integer, T> k = A.stream().max((a, b) -> a.getKey().compareTo(b.getKey())).get();
        List<Element<Integer, T>> B = new ArrayList<>();
        for (int i = 0; i < A.size(); i++) {
            B.add(null);
        }
        int[] C = new int[k.getKey() + 1];
        for (int j = 0; j < A.size(); j++) {
            C[A.get(j).getKey()]++;
        }
        for (int i = 1; i <= k.getKey(); i++) {
            C[i] += C[i - 1];
        }
        for (int j = A.size() - 1; j >= 0; j--) {
            B.set(C[A.get(j).getKey()] - 1, A.get(j));
            C[A.get(j).getKey()]--;
        }
        return B;
    }

    public static void main(String[] args) {
        int[] A = { 2, 5, 3, 0, 2, 3, 0, 3 };
        int[] B = countingSort(A);
        for (int i : B) {
            System.out.print(i + " ");
        }
        System.out.println();
        List<Element<Integer, String>> list = List.of(
            new Element<>(2, "A"),
            new Element<>(5, "B"),
            new Element<>(3, "C"),
            new Element<>(0, "D"),
            new Element<>(2, "E"),
            new Element<>(3, "F"),
            new Element<>(0, "G"),
            new Element<>(3, "H"));
        List<Element<Integer, String>> sorted = countingSort(list);
        for (Element<Integer, String> e : sorted) {
            System.out.print(e.getKey() + " ");
        }
    }

}
