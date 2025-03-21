package algorithm.adt;

public class BTree {

    record OrderedPair(BTreeNode y, int i) { }
    
    private BTreeNode root;

    public OrderedPair search(char k) {
        return search(root, k);
    }

    public static OrderedPair search(BTreeNode x, char k) {
        int i = 0;
        while (i < x.n && k > x.keys[i]) {
            i++;
        }
        if (i < x.n && k == x.keys[i]) {
            return new OrderedPair(x, i);
        }
        if (x.leaf) {
            return null;
        } else {
            // diskRead(x.children[i]))
            return search(x.children[i], k);
        }
    }

    public BTree() {
        BTreeNode x = new BTreeNode();
        x.leaf = true;
        x.n = 0;
        // diskWrite(x)
        root = x;
    }

    public BTree(int t) {
        BTreeNode x = new BTreeNode(t);
        x.leaf = true;
        x.n = 0;
        // diskWrite(x)
        root = x;
    }

    private void splitChild(BTreeNode x, int i) {
        int t = x.t;
        BTreeNode y = x.children[i]; // full node to split
        BTreeNode z = new BTreeNode(t); // z will take half of y
        z.leaf = y.leaf;
        z.n = t - 1;
        for (int j = 0; j < t - 1; j++) { // z gets y's greatest keys ...
            z.keys[j] = y.keys[j + t];
        }
        if (!y.leaf) {
            for (int j = 0; j < t; j++) { // ... and its corresponding children
                z.children[j] = y.children[j + t];
            }
        }
        y.n = t - 1; // y keeps t - 1 keys
        for (int j = x.n; j > i; j--) { // shift x's children to the right ...
            x.children[j + 1] = x.children[j];
        }
        x.children[i + 1] = z; // ... to make room for z as a child
        for (int j = x.n - 1; j >= i; j--) { // shift the corresponding keys in x
            x.keys[j + 1] = x.keys[j];
        }
        x.keys[i] = y.keys[t - 1]; // insert y's median key
        x.n++; // x has gained a child
        // diskWrite(y)
        // diskWrite(z)
        // diskWrite(x)
    }

    public void insert(char k) {
        BTreeNode r = root;
        if (r.n == 2 * r.t - 1) {
            BTreeNode s = splitRoot();
            insertNonfull(s, k);
        } else {
            insertNonfull(r, k);
        }
    }

    private BTreeNode splitRoot() {
        BTreeNode s = new BTreeNode(root.t);
        s.leaf = false;
        s.n = 0;
        s.children[0] = root;
        root = s;
        splitChild(s, 0);
        return s;
    }

    private void insertNonfull(BTreeNode x, char k) {
        int i = x.n - 1;
        if (x.leaf) { // inserting into a leaf?
            while (i >= 0 && k < x.keys[i]) { // shift keys in x to make room for k
                x.keys[i + 1] = x.keys[i];
                i--;
            }
            x.keys[i + 1] = k; // insert key k in x
            x.n++; // now x has 1 more key
            // diskWrite(x)
        } else { // find the child where k belongs
            while (i >= 0 && k < x.keys[i]) {
                i--;
            }
            i++;
            // diskRead(x.children[i])
            if (x.children[i].n == 2 * x.t - 1) { // split the child if it's full
                splitChild(x, i);
                if (k > x.keys[i]) {
                    i++;
                }
            }
            insertNonfull(x.children[i], k);
        }
    }

    public void delete(char k) {
        delete(root, k);
    }

    private void delete(BTreeNode x, char k) {
        int i = x.n - 1;
        while (i >= 0 && k < x.keys[i]) {
            i--;
        }
        if (x.leaf) { // case 1
            if (i >= 0 && k == x.keys[i]) {
                for (int j = i; j < x.n - 1; j++) {
                    x.keys[j] = x.keys[j + 1];
                }
                x.n--;
            }
        } else {
            if (i >= 0 && k == x.keys[i]) { // case 2
                BTreeNode y = x.children[i];
                BTreeNode z = x.children[i + 1];
                if (y.n >= x.t) { // case 2a
                    char predecessor = predecessor(y);
                    delete(y, predecessor);
                    x.keys[i] = predecessor;
                } else if (z.n >= x.t) { // case 2b
                    char successor = successor(z);
                    delete(z, successor);
                    x.keys[i] = successor;
                } else { // case 2c
                    merge(x, i);
                    flatten(x);
                    delete(y, k);
                }
            } else { // case 3
                i++;
                BTreeNode y = x.children[i];
                if (y.n == x.t - 1) {
                    BTreeNode leftSibling = i > 0 ? x.children[i - 1] : null;
                    BTreeNode rightSibling = i < x.n ? x.children[i + 1] : null;
                    if (leftSibling != null && leftSibling.n >= x.t) { // case 3a
                        for (int j = y.n; j > 0; j--) {
                            y.keys[j] = y.keys[j - 1];
                        }
                        y.keys[0] = x.keys[i - 1];
                        if (!y.leaf) {
                            for (int j = y.n + 1; j > 0; j--) {
                                y.children[j] = y.children[j - 1];
                            }
                            y.children[0] = leftSibling.children[leftSibling.n];
                        }
                        y.n++;
                        x.keys[i - 1] = leftSibling.keys[leftSibling.n - 1];
                        leftSibling.n--;
                    } else if (rightSibling != null && rightSibling.n >= x.t) { // case 3a
                        y.keys[y.n] = x.keys[i];
                        y.n++;
                        x.keys[i] = rightSibling.keys[0];
                        for (int j = 0; j < rightSibling.n - 1; j++) {
                            rightSibling.keys[j] = rightSibling.keys[j + 1];
                        }
                        if (!rightSibling.leaf) {
                            y.children[y.n] = rightSibling.children[0];
                            for (int j = 0; j < rightSibling.n; j++) {
                                rightSibling.children[j] = rightSibling.children[j + 1];
                            }
                        }
                        rightSibling.n--;
                    } else if (leftSibling != null) { // case 3b
                        merge(x, i - 1); 
                        flatten(x);
                        delete(leftSibling, k);
                    } else if (rightSibling != null) { // case 3b
                        merge(x, i);
                        flatten(x);
                        delete(y, k);
                    }
                }
                delete(y, k);
            }
        }
    }

    private char predecessor(BTreeNode x) {
        while (!x.leaf) {
            x = x.children[x.n];
        }
        return x.keys[x.n - 1];
    }

    private char successor(BTreeNode x) {
        while (!x.leaf) {
            x = x.children[0];
        }
        return x.keys[0];
    }

    private void merge(BTreeNode x, int i) {
        BTreeNode y = x.children[i];
        BTreeNode z = x.children[i + 1];
        y.keys[y.n] = x.keys[i];
        for (int j = 0; j < z.n; j++) {
            y.keys[y.n + 1 + j] = z.keys[j];
        }
        if (!y.leaf) {
            for (int j = 0; j <= z.n; j++) {
                y.children[y.n + 1 + j] = z.children[j];
            }
        }
        y.n += z.n + 1;
        for (int j = i; j < x.n - 1; j++) {
            x.keys[j] = x.keys[j + 1];
        }
        for (int j = i + 1; j < x.n; j++) {
            x.children[j] = x.children[j + 1];
        }
        x.n--;
    }

    private void flatten(BTreeNode x) {
        if (x != null && x == root && x.n == 0) {
            root = x.children[0];
        }
    }

    public void inorderWalk() {
        inorderWalk(root, 0);
        System.out.println();
    }

    private static void inorderWalk(BTreeNode x, int depth) {
        if (x != null) {
            for (int i = 0; i < x.n; i++) {
                inorderWalk(x.children[i], depth + 1);
                System.out.print(x.keys[i] + ":" + depth + " ");
            }
            inorderWalk(x.children[x.n], depth + 1);
        }
    }

    public static void main(String[] args) {
        BTree t = new BTree(3);
        t.insert('D');
        t.insert('E');
        t.insert('G');
        t.insert('J');
        t.insert('K');
        t.insert('M');
        t.insert('N');
        t.insert('O');
        t.insert('P');
        t.insert('R');
        t.insert('S');
        t.insert('X');
        t.insert('Y');
        t.insert('Z');
        t.insert('T');
        t.insert('U');
        t.insert('V');
        t.insert('A');
        t.insert('C');
        t.inorderWalk();
        t.insert('B');
        t.inorderWalk();
        t.insert('Q');
        t.inorderWalk();
        t.insert('L');
        t.inorderWalk();
        t.insert('F');
        t.inorderWalk();
        t.delete('F');
        t.inorderWalk();
        t.delete('M');
        t.inorderWalk();
        t.delete('G');
        t.inorderWalk();
        t.delete('D');
        t.inorderWalk();
        t.delete('B');
        t.inorderWalk();
    }

}
