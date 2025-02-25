package algorithm.adt;

public class LinkedList {

    private LinkedListNode head;

    public LinkedList() {
        head = null;
    }

    public LinkedListNode search(int k) {
        LinkedListNode x = head;
        while (x != null && x.key != k) {
            x = x.next;
        }
        return x;
    }

    public void prepend(LinkedListNode x) {
        x.next = head;
        x.prev = null;
        if (head != null) {
            head.prev = x;
        }
        head = x;
    }

    public void insert(LinkedListNode x, LinkedListNode y) {
        x.next = y.next;
        x.prev = y;
        if (y.next != null) {
            y.next.prev = x;
        }
        y.next = x;
    }

    public void delete(LinkedListNode x) {
        if (x.prev != null) {
            x.prev.next = x.next;
        } else {
            head = x.next;
        }
        if (x.next != null) {
            x.next.prev = x.prev;
        }
    }

}
