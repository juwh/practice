package algorithm.adt;

public class BinarySearchTree {

    private BinaryTreeNode root;

    public void inorderWalk() {
        inorderWalk(root);
    }

    public static void inorderWalk(BinaryTreeNode x) {
        if (x != null) {
            inorderWalk(x.left);
            System.out.print(x.key + " ");
            inorderWalk(x.right);
        }
    }

    public BinaryTreeNode search(int k) {
        return search(root, k);
    }

    public static BinaryTreeNode search(BinaryTreeNode x, int k) {
        if (x == null || k == x.key) {
            return x;
        }
        if (k < x.key) {
            return search(x.left, k);
        } else {
            return search(x.right, k);
        }
    }

    public BinaryTreeNode iterativeSearch(int k) {
        return iterativeSearch(root, k);
    }

    public static BinaryTreeNode iterativeSearch(BinaryTreeNode x, int k) {
        while (x != null && k != x.key) {
            if (k < x.key) {
                x = x.left;
            } else {
                x = x.right;
            }
        }
        return x;
    }

    public BinaryTreeNode minimum() {
        return minimum(root);
    }

    public static BinaryTreeNode minimum(BinaryTreeNode x) {
        while (x.left != null) {
            x = x.left;
        }
        return x;
    }

    public BinaryTreeNode maximum() {
        return maximum(root);
    }

    public static BinaryTreeNode maximum(BinaryTreeNode x) {
        while (x.right != null) {
            x = x.right;
        }
        return x;
    }

    public static BinaryTreeNode successor(BinaryTreeNode x) {
        if (x.right != null) {
            return minimum(x.right); // leftmost node in right subtree
        }
        // find the lowest ancestor of x whose left child is an ancestor of x
        BinaryTreeNode y = x.p;
        while (y != null && x == y.right) {
            x = y;
            y = y.p;
        }
        return y;
    }

    public void insert(BinaryTreeNode z) {
        BinaryTreeNode x = root; // node being compared with z
        BinaryTreeNode y = null; // y will be parent of z
        while (x != null) { // descend until reaching a leaf
            y = x;
            if (z.key < x.key) {
                x = x.left;
            } else {
                x = x.right;
            }
        }
        z.p = y; // found the location--insert z with parent y
        if (y == null) {
            root = z; // tree T was empty
        } else if (z.key < y.key) {
            y.left = z;
        } else {
            y.right = z;
        }
    }

    private void transplant(BinaryTreeNode u, BinaryTreeNode v) {
        if (u.p == null) {
            root = v;
        } else if (u == u.p.left) {
            u.p.left = v;
        } else {
            u.p.right = v;
        }
        if (v != null) {
            v.p = u.p;
        }
    }

    public void delete(BinaryTreeNode z) {
        if (z.left == null) {
            transplant(z, z.right); // replace z by its right child
        } else if (z.right == null) {
            transplant(z, z.left); // replace z by its left child
        } else {
            BinaryTreeNode y = minimum(z.right); // y is z's successor
            if (y.p != z) { // is y farther down the tree?
                transplant(y, y.right); // replace y by its right child
                y.right = z.right; // z's right child becomes
                y.right.p = y; // y's right child
            }
            transplant(z, y); // replace z by its successor y
            y.left = z.left; // and give y z's left child
            y.left.p = y; // which had no left child
        }
    }

    public static void main(String[] args) {
        BinarySearchTree T = new BinarySearchTree();
        BinaryTreeNode x = new BinaryTreeNode();
        x.key = 6;
        T.insert(x);
        x = new BinaryTreeNode();
        x.key = 5;
        T.insert(x);
        x = new BinaryTreeNode();
        x.key = 7;
        T.insert(x);
        x = new BinaryTreeNode();
        x.key = 2;
        T.insert(x);
        x = new BinaryTreeNode();
        x.key = 5;
        T.insert(x);
        x = new BinaryTreeNode();
        x.key = 8;
        T.insert(x);
        T.inorderWalk();
        System.out.println();
        T = new BinarySearchTree();
        x = new BinaryTreeNode();
        x.key = 2;
        T.insert(x);
        x = new BinaryTreeNode();
        x.key = 5;
        T.insert(x);
        x = new BinaryTreeNode();
        x.key = 7;
        T.insert(x);
        x = new BinaryTreeNode();
        x.key = 6;
        T.insert(x);
        x = new BinaryTreeNode();
        x.key = 8;
        T.insert(x);
        x = new BinaryTreeNode();
        x.key = 5;
        T.insert(x);
        T.inorderWalk();
        System.out.println();
        T = new BinarySearchTree();
        x = new BinaryTreeNode();
        x.key = 15;
        T.insert(x);
        x = new BinaryTreeNode();
        x.key = 6;
        T.insert(x);
        x = new BinaryTreeNode();
        x.key = 18;
        T.insert(x);
        x = new BinaryTreeNode();
        x.key = 3;
        T.insert(x);
        x = new BinaryTreeNode();
        x.key = 7;
        T.insert(x);
        x = new BinaryTreeNode();
        x.key = 17;
        T.insert(x);
        x = new BinaryTreeNode();
        x.key = 20;
        T.insert(x);
        x = new BinaryTreeNode();
        x.key = 2;
        T.insert(x);
        x = new BinaryTreeNode();
        x.key = 4;
        T.insert(x);
        x = new BinaryTreeNode();
        x.key = 13;
        T.insert(x);
        x = new BinaryTreeNode();
        x.key = 9;
        T.insert(x);
        System.out.println(T.search(13).key);
        System.out.println(T.iterativeSearch(13).key);
        System.out.println(T.minimum().key);
        System.out.println(T.maximum().key);
        System.out.println(BinarySearchTree.successor(T.search(15)).key);
        System.out.println(BinarySearchTree.successor(T.search(13)).key);
        T = new BinarySearchTree();
        x = new BinaryTreeNode();
        x.key = 12;
        T.insert(x);
        x = new BinaryTreeNode();
        x.key = 5;
        T.insert(x);
        x = new BinaryTreeNode();
        x.key = 18;
        T.insert(x);
        x = new BinaryTreeNode();
        x.key = 2;
        T.insert(x);
        x = new BinaryTreeNode();
        x.key = 9;
        T.insert(x);
        x = new BinaryTreeNode();
        x.key = 15;
        T.insert(x);
        x = new BinaryTreeNode();
        x.key = 19;
        T.insert(x);
        x = new BinaryTreeNode();
        x.key = 17;
        T.insert(x);
        T.inorderWalk();
        System.out.println();
        x = new BinaryTreeNode();
        x.key = 13;
        T.insert(x);
        T.inorderWalk();
        System.out.println();
    }

}
