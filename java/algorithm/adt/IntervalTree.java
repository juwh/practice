package algorithm.adt;

public class IntervalTree extends BinarySearchTree {

    private IntervalTreeNode root;
    private static IntervalTreeNode nil;

    static {
        nil = new IntervalTreeNode();
        nil.color = RedBlackTreeNode.Color.BLACK;
    }

    public IntervalTree() {
        root = nil;
    }

    public IntervalTreeNode search(int i) {
        IntervalTreeNode x = root;
        while (x != nil && !overlap(x.interval, i)) {
            if (x.left != nil && x.left.max >= i) {
                x = x.left;
            } else {
                x = x.right;
            }
        }
        return x;
    }

    private boolean overlap(IntervalTreeNode.Interval x, int i) {
        return x.low <= i && i <= x.high;
    }

    private void leftRotate(IntervalTreeNode x) {
        IntervalTreeNode y = x.right;
        x.right = y.left; // turn y's left subtree into x's right subtree
        if (y.left != nil) { // if y's left subtree is not empty ...
            y.left.p = x; // ... then x becomes the parent of the subtree's root
        }
        y.p = x.p; // x's parent becomes y's parent
        if (x.p == nil) { // if x was the root ...
            root = y; // ... then y becomes the root
        } else if (x == x.p.left) { // otherwise, if x was a left child ...
            x.p.left = y; // ... then y becomes a left child
        } else { 
            x.p.right = y; // otherwise, x was aright child, and now y is
        }
        y.left = x; // make x become y's left child
        x.p = y;
        int xLeftMax = x.left == nil ? x.interval.high : x.left.max;
        int xRightMax = x.right == nil ? x.interval.high : x.right.max;
        x.max = Math.max(x.interval.high, Math.max(xLeftMax, xRightMax));
    }

    private void rightRotate(IntervalTreeNode y) {
        IntervalTreeNode x = y.left;
        y.left = x.right;
        if (x.right != nil) {
            x.right.p = y;
        }
        x.p = y.p;
        if (y.p == nil) {
            root = x;
        } else if (y == y.p.right) {
            y.p.right = x;
        } else {
            y.p.left = x;
        }
        x.right = y;
        y.p = x;
        int yLeftMax = y.left == nil ? y.interval.high : y.left.max;
        int yRightMax = y.right == nil ? y.interval.high : y.right.max;
        y.max = Math.max(y.interval.high, Math.max(yLeftMax, yRightMax));
    }

    public void insert(IntervalTreeNode z) {
        z.max = z.interval.high;
        IntervalTreeNode x = root; // node being compared with z
        IntervalTreeNode y = nil; // y will be parent of z
        while (x != nil) { // descend until reaching the sentinel
            x.max = Math.max(x.max, z.max);
            y = x;
            if (z.key < x.key) {
                x = x.left;
            } else {
                x = x.right;
            }
        }
        z.p = y; // found the location--insert z with parent y
        if (y == nil) {
            root = z; // tree T was empty
        } else if (z.key < y.key) {
            y.left = z;
        } else {
            y.right = z;
        }
        z.left = nil; // both of z's children are the sentinel
        z.right = nil;
        z.color = RedBlackTreeNode.Color.RED; // the new node starts out red
        insertFixup(z); // correct any violations of red-black properties
    }

    private void insertFixup(IntervalTreeNode z) {
        while (z.p.color == RedBlackTreeNode.Color.RED) {
            if (z.p == z.p.p.left) { // is z's parent a left child?
                IntervalTreeNode y = z.p.p.right; // y is z's uncle
                if (y.color == RedBlackTreeNode.Color.RED) { // are z's parent and uncle both red?
                    z.p.color = RedBlackTreeNode.Color.BLACK;
                    y.color = RedBlackTreeNode.Color.BLACK;
                    z.p.p.color = RedBlackTreeNode.Color.RED;
                    z = z.p.p;
                } else {
                    if (z == z.p.right) {
                        z = z.p;
                        leftRotate(z);
                    }
                    z.p.color = RedBlackTreeNode.Color.BLACK;
                    z.p.p.color = RedBlackTreeNode.Color.RED;
                    rightRotate(z.p.p);
                }
            } else { // same as block above, but with "right" and "left" exchanged
                IntervalTreeNode y = z.p.p.left;
                if (y.color == RedBlackTreeNode.Color.RED) {
                    z.p.color = RedBlackTreeNode.Color.BLACK;
                    y.color = RedBlackTreeNode.Color.BLACK;
                    z.p.p.color = RedBlackTreeNode.Color.RED;
                    z = z.p.p;
                } else {
                    if (z == z.p.left) {
                        z = z.p;
                        rightRotate(z);
                    }
                    z.p.color = RedBlackTreeNode.Color.BLACK;
                    z.p.p.color = RedBlackTreeNode.Color.RED;
                    leftRotate(z.p.p);
                }
            }
        }
        root.color = RedBlackTreeNode.Color.BLACK;
    }

    private void transplant(IntervalTreeNode u, IntervalTreeNode v) {
        if (u.p == nil) {
            root = v;
        } else if (u == u.p.left) {
            u.p.left = v;
        } else {
            u.p.right = v;
        }
        v.p = u.p;
    }

    public void delete(IntervalTreeNode z) {
        IntervalTreeNode w = z;
        if (w.p.right == w) {
            w.max = w.interval.high;
            w = w.p;
        }
        while (w != root) {
            w.max = Math.max(w.interval.high, Math.max(w.left.max, w.right.max));
            w = w.p;
        }
        IntervalTreeNode y = z;
        RedBlackTreeNode.Color yOriginalColor = y.color;
        IntervalTreeNode x;
        if (z.left == nil) {
            x = z.right;
            transplant(z, z.right); // replace z by its right child
        } else if (z.right == nil) {
            x = z.left;
            transplant(z, z.left); // replace z by its left child
        } else {
            y = (IntervalTreeNode) minimum(z.right); // y is z's successor
            yOriginalColor = y.color;
            x = y.right;
            if (y.p != z) { // is y farther down the tree?
                transplant(y, y.right); // replace y by its right child
                y.right = z.right; // z's right child becomes
                y.right.p = y; // y's right child
            } else {
                x.p = y; // in case x is T.nil
            }
            transplant(z, y); // replace z by its successor y
            y.left = z.left; // and give z's left child to y,
            y.left.p = y; // which had no left child
            y.color = z.color;
        }
        if (yOriginalColor == RedBlackTreeNode.Color.BLACK) { // if any red-black violations occurred,
            deleteFixup(x); // correct them
        }
    }

    private void deleteFixup(IntervalTreeNode x) {
        while (x != root && x.color == RedBlackTreeNode.Color.BLACK) {
            if (x == x.p.left) { // is x a left child?
                IntervalTreeNode w = x.p.right; // w is x's sibling
                if (w.color == RedBlackTreeNode.Color.RED) {
                    w.color = RedBlackTreeNode.Color.BLACK;
                    x.p.color = RedBlackTreeNode.Color.RED;
                    leftRotate(x.p);
                    w = x.p.right;
                }
                if (w.left.color == RedBlackTreeNode.Color.BLACK && w.right.color == RedBlackTreeNode.Color.BLACK) {
                    w.color = RedBlackTreeNode.Color.RED;
                    x = x.p;
                } else {
                    if (w.right.color == RedBlackTreeNode.Color.BLACK) {
                        w.left.color = RedBlackTreeNode.Color.BLACK;
                        w.color = RedBlackTreeNode.Color.RED;
                        rightRotate(w);
                        w = x.p.right;
                    }
                    w.color = x.p.color;
                    x.p.color = RedBlackTreeNode.Color.BLACK;
                    w.right.color = RedBlackTreeNode.Color.BLACK;
                    leftRotate(x.p);
                    x = root;
                }
            } else { // same as block above, but with "right" and "left" exchanged
                IntervalTreeNode w = x.p.left;
                if (w.color == RedBlackTreeNode.Color.RED) {
                    w.color = RedBlackTreeNode.Color.BLACK;
                    x.p.color = RedBlackTreeNode.Color.RED;
                    rightRotate(x.p);
                    w = x.p.left;
                }
                if (w.right.color == RedBlackTreeNode.Color.BLACK && w.left.color == RedBlackTreeNode.Color.BLACK) {
                    w.color = RedBlackTreeNode.Color.RED;
                    x = x.p;
                } else {
                    if (w.left.color == RedBlackTreeNode.Color.BLACK) {
                        w.right.color = RedBlackTreeNode.Color.BLACK;
                        w.color = RedBlackTreeNode.Color.RED;
                        leftRotate(w);
                        w = x.p.left;
                    }
                    w.color = x.p.color;
                    x.p.color = RedBlackTreeNode.Color.BLACK;
                    w.left.color = RedBlackTreeNode.Color.BLACK;
                    rightRotate(x.p);
                    x = root;
                }
            }
        }
        x.color = RedBlackTreeNode.Color.BLACK;
    }

    public void inorderWalk() {
        inorderWalk(root);
    }

    public static void inorderWalk(IntervalTreeNode x) {
        if (x != nil) {
            inorderWalk(x.left);
            System.out.println(x.interval.low + " " + x.interval.high + " " + x.color + " " + x.max + " ");
            inorderWalk(x.right);
        }
    }

    public static void main(String[] args) {
        IntervalTree T = new IntervalTree();
        T.insert(new IntervalTreeNode(16, 21));
        T.insert(new IntervalTreeNode(8, 9));
        T.insert(new IntervalTreeNode(25, 30));
        T.insert(new IntervalTreeNode(5, 8));
        T.insert(new IntervalTreeNode(15, 23));
        T.insert(new IntervalTreeNode(17, 19));
        T.insert(new IntervalTreeNode(26, 26));
        T.insert(new IntervalTreeNode(0, 3));
        T.insert(new IntervalTreeNode(6, 10));
        T.insert(new IntervalTreeNode(19, 20));
        T.inorderWalk();
        IntervalTreeNode x = T.search(22);
        System.out.println(x.interval.low + " " + x.interval.high);
    }

}