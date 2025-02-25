package algorithm.adt;

public class Queue {
    
    private int[] Q;
    private int head;
    private int tail;
    
    public Queue(int n) {
        Q = new int[n];
        head = 0;
        tail = 0;
    }
    
    public void enqueue(int x) {
        Q[tail] = x;
        if (tail == Q.length - 1) {
            tail = 0;
        } else {
            tail++;
        }
    }
    
    public int dequeue() {
        int x = Q[head];
        if (head == Q.length - 1) {
            head = 0;
        } else {
            head++;
        }
        return x;
    }
    
    public void print() {
        if (head < tail) {
            for (int i = head; i <= tail; i++) {
                System.out.print(Q[i] + " ");
            }
        } else {
            for (int i = head; i < Q.length + tail; i++) {
                System.out.print(Q[i % Q.length] + " ");
            }
        }
    }

    public static void main(String[] args) {
        Queue Q = new Queue(12);
        Q.enqueue(1);
        Q.enqueue(2);
        Q.enqueue(3);
        Q.enqueue(4);
        Q.enqueue(5);
        Q.enqueue(6);
        Q.dequeue();
        Q.dequeue();
        Q.dequeue();
        Q.dequeue();
        Q.dequeue();
        Q.dequeue();
        Q.enqueue(15);
        Q.enqueue(6);
        Q.enqueue(9);
        Q.enqueue(8);
        Q.enqueue(4);
        Q.enqueue(9);
        Q.print();
        System.out.println();
        Q.enqueue(17);
        Q.enqueue(3);
        Q.enqueue(5);
        Q.print();
        System.out.println();
        Q.dequeue();
        Q.print();
    }
}
