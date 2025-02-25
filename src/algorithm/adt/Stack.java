package algorithm.adt;

public class Stack {
    
    private int[] S;
    private int top;

    public Stack(int size) {
        S = new int[size];
        top = -1;
    }

    public boolean stackEmpty() {
        return top < 0;
    }

    public void push(int x) {
        if (top == S.length - 1) {
            throw new RuntimeException("overflow");
        } else {
            top++;
            S[top] = x;
        }
    }

    public int pop() {
        if (top < 0) {
            throw new RuntimeException("underflow");
        } else {
            top--;
            return S[top + 1];
        }
    }

    public void print() {
        for (int i = 0; i <= top; i++) {
            System.out.print(S[i] + " ");
        }
    }

    public static void main(String[] args) {
        Stack stack = new Stack(7);
        stack.push(15);
        stack.push(6);
        stack.push(2);
        stack.push(9);
        stack.print();
        System.out.println();
        stack.push(17);
        stack.push(3);
        stack.print();
        System.out.println();
        stack.pop();
        stack.print();
    }

}
