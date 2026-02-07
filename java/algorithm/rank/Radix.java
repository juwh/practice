package algorithm.rank;

import java.util.List;

import algorithm.adt.Element;

public class Radix {

    public static List<Element<Integer, Integer>> radixSort(List<Element<Integer, Integer>> A) {
        Element<Integer, Integer> max = A.stream().max((a, b) -> a.getValue().compareTo(b.getValue())).get();
        int d = max.getValue().toString().length();
        for (int i = 0; i < d; i++) {
            updateKeysByDigit(A, i);
            A = Counting.countingSort(A);
        }
        return A;
    }

    private static void updateKeysByDigit(List<Element<Integer, Integer>> A, int digit) {
        for (Element<Integer, Integer> element : A) {
            String valueString = element.getValue().toString();
            int valueLength = valueString.length();
            element.setKey(digit >= valueLength ? 0 : Character.getNumericValue(valueString.charAt(valueLength - digit - 1)));
        }
    }

    public static void main(String[] args) {
        List<Element<Integer, Integer>> list = List.of(
            new Element<>(1, 329), 
            new Element<>(2, 457),
            new Element<>(3, 657), 
            new Element<>(4, 839), 
            new Element<>(5, 436), 
            new Element<>(6, 720),
            new Element<>(7, 355));
        List<Element<Integer, Integer>> sortedList = radixSort(list);
        sortedList.forEach(e -> System.out.print(e.getValue() + " "));
        System.out.println();
    }

}
