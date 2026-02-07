package algorithm.adt;

import algorithm.rank.Heap;

public class MaxPriorityQueue<T extends Element<U, V>, U extends Comparable<U>, V> {

    private T[] A;
    private int heapSize;

    public MaxPriorityQueue(T[] A) {
        T[] copy = A.clone();
        Heap.buildMaxHeap(copy);
        this.A = copy;
        heapSize = copy.length;
    }

    public T maximum() {
        if (heapSize < 1) {
            throw new RuntimeException("heap underflow");
        }
        return A[0];
    }

    public T extractMax() {
        T max = maximum();
        A[0] = A[--heapSize];
        Heap.maxHeapify(A, 0, heapSize);
        return max;
    }

    public void increaseKey(Element<U, V> x, U key) {
        if (key.compareTo(x.getKey()) < 0) {
            throw new RuntimeException("new key is smaller than current key");
        }
        x.setKey(key);
        int i = 0;
        while (i < heapSize && A[i] != x) {
            i++;
        }
        if (i == heapSize) {
            throw new RuntimeException("element not found");
        }
        while (i > 0 && A[Heap.parent(i)].getKey().compareTo(A[i].getKey()) < 0) {
            T temp = A[i];
            A[i] = A[Heap.parent(i)];
            A[Heap.parent(i)] = temp;
            i = Heap.parent(i);
        }
    }

    public void insert(T x) {
        if (heapSize == A.length) {
            throw new RuntimeException("heap overflow");
        }
        heapSize++;
        A[heapSize - 1] = x;
        increaseKey(x, x.getKey());
    }

    public void print() {
        for (int i = 0; i < heapSize; i++) {
            System.out.print(A[i].getKey() + " ");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        Element<Integer, String> e1 = new Element<>(4, "a");
        Element<Integer, String> e2 = new Element<>(1, "b");
        Element<Integer, String> e3 = new Element<>(3, "c");
        Element<Integer, String> e4 = new Element<>(2, "d");
        Element<Integer, String> e5 = new Element<>(16, "e");
        Element<Integer, String> e6 = new Element<>(9, "f");
        Element<Integer, String> e7 = new Element<>(10, "g");
        Element<Integer, String> e8 = new Element<>(14, "h");
        Element<Integer, String> e9 = new Element<>(8, "i");
        Element<Integer, String> e10 = new Element<>(7, "j");
        @SuppressWarnings("unchecked")
        Element<Integer, String>[] arr = new Element[] { e1, e2, e3, e4, e5, e6, e7, e8, e9, e10 };
        MaxPriorityQueue<Element<Integer, String>, Integer, String> maxPriorityQueue = new MaxPriorityQueue<>(arr);
        maxPriorityQueue.print();
        System.out.println(maxPriorityQueue.maximum().getKey());
        System.out.println(maxPriorityQueue.extractMax().getKey());
        maxPriorityQueue.print();
        maxPriorityQueue.increaseKey(e1, 15);
        maxPriorityQueue.print();
        maxPriorityQueue.insert(new Element<>(20, "k"));
        maxPriorityQueue.print();
    }

}
