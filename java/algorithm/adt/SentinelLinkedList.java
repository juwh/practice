package algorithm.adt;

public class SentinelLinkedList {

    private LinkedListNode nil;

    public SentinelLinkedList() {
        nil = new LinkedListNode();
        nil.next = nil;
        nil.prev = nil;
    }

    public LinkedListNode search(int k) {
        nil.key = k; // store the key in the sentinel to gurarantee it is in list
        LinkedListNode x = nil.next; // start at the head of the list
        while (x.key != k) {
            x = x.next;
        }
        if (x == nil) { // found k in the sentinel
            return null; // k was not really in the list
        }
        return x; // found k in element x
    }

    public void prepend(LinkedListNode x) {
        x.next = nil.next;
        nil.next.prev = x;
        nil.next = x;
        x.prev = nil;
    }

    public void insert(LinkedListNode x, LinkedListNode y) {
        x.next = y.next;
        x.prev = y;
        y.next.prev = x;
        y.next = x;
    }

    public void delete(LinkedListNode x) {
        x.prev.next = x.next;
        x.next.prev = x.prev;
    }

    public void print() {
        LinkedListNode x = nil.next;
        while (x != nil) {
            System.out.print(x.key + " ");
            x = x.next;
        }
    }

    public static void main(String[] args) {
        SentinelLinkedList L = new SentinelLinkedList();
        LinkedListNode x = new LinkedListNode();
        LinkedListNode y = new LinkedListNode();
        y.key = 1;
        L.prepend(y);
        x = new LinkedListNode();
        x.key = 4;
        L.prepend(x);
        x = new LinkedListNode();
        x.key = 16;
        L.prepend(x);
        x = new LinkedListNode();
        x.key = 9;
        L.prepend(x);
        L.print();
        System.out.println();
        x = new LinkedListNode();
        x.key = 25;
        L.prepend(x);
        L.print();
        System.out.println();
        L.delete(L.search(1));
        L.print();
    }

}
