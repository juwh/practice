package algorithm.adt;

public class OrderStatisticTree extends BinarySearchTree {

    private OrderStatisticTreeNode root;
    private static OrderStatisticTreeNode nil;

    static {
        nil = new OrderStatisticTreeNode();
        nil.color = RedBlackTreeNode.Color.BLACK;
    }

    public OrderStatisticTree() {
        root = nil;
    }

    public OrderStatisticTreeNode select(OrderStatisticTreeNode x, int i) {
        int r = x.left.size + 1; // rank of x within the subtree rooted at x
        if (i == r) {
            return x;
        } else if (i < r) {
            return select(x.left, i);
        } else {
            return select(x.right, i - r);
        }
    }

    public OrderStatisticTreeNode select(int i) {
        return select(root, i);
    }

    public int rank(OrderStatisticTreeNode x) {
        int r = x.left.size + 1; // rank of x within the subtree rooted at x
        OrderStatisticTreeNode y = x; // root of subtree being examined
        while (y != root) {
            if (y == y.p.right) { // if root of a right subtree ...
                r = r + y.p.left.size + 1; // ... add in parent and its left subtree
            }
            y = y.p; // move y toward the root
        }
        return r;
    }

    private void leftRotate(OrderStatisticTreeNode x) {
        OrderStatisticTreeNode y = x.right;
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
        y.size = x.size;
        x.size = x.left.size + x.right.size + 1;
    }

    private void rightRotate(OrderStatisticTreeNode y) {
        OrderStatisticTreeNode x = y.left;
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
        x.size = y.size;
        y.size = y.left.size + y.right.size + 1;
    }

    public void insert(OrderStatisticTreeNode z) {
        OrderStatisticTreeNode x = root; // node being compared with z
        OrderStatisticTreeNode y = nil; // y will be parent of z
        while (x != nil) { // descend until reaching the sentinel
            x.size++;
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
        z.size = 1;
        insertFixup(z); // correct any violations of red-black properties
    }

    private void insertFixup(OrderStatisticTreeNode z) {
        while (z.p.color == RedBlackTreeNode.Color.RED) {
            if (z.p == z.p.p.left) { // is z's parent a left child?
                OrderStatisticTreeNode y = z.p.p.right; // y is z's uncle
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
                OrderStatisticTreeNode y = z.p.p.left;
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

    private void transplant(OrderStatisticTreeNode u, OrderStatisticTreeNode v) {
        if (u.p == nil) {
            root = v;
        } else if (u == u.p.left) {
            u.p.left = v;
        } else {
            u.p.right = v;
        }
        v.p = u.p;
    }

    public void delete(OrderStatisticTreeNode z) {
        OrderStatisticTreeNode w = z;
        while (w != root) {
            w.size--;
            w = w.p;
        }
        OrderStatisticTreeNode y = z;
        RedBlackTreeNode.Color yOriginalColor = y.color;
        OrderStatisticTreeNode x;
        if (z.left == nil) {
            x = z.right;
            transplant(z, z.right); // replace z by its right child
        } else if (z.right == nil) {
            x = z.left;
            transplant(z, z.left); // replace z by its left child
        } else {
            y = (OrderStatisticTreeNode) minimum(z.right); // y is z's successor
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

    private void deleteFixup(OrderStatisticTreeNode x) {
        while (x != root && x.color == RedBlackTreeNode.Color.BLACK) {
            if (x == x.p.left) { // is x a left child?
                OrderStatisticTreeNode w = x.p.right; // w is x's sibling
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
                OrderStatisticTreeNode w = x.p.left;
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

    public static void inorderWalk(OrderStatisticTreeNode x) {
        if (x != nil) {
            inorderWalk(x.left);
            System.out.println(x.key + " " + x.color + " " + x.size + " ");
            inorderWalk(x.right);
        }
    }

    public static void main(String[] args) {
        OrderStatisticTree T = new OrderStatisticTree();
        T.insert(new OrderStatisticTreeNode(26));
        T.insert(new OrderStatisticTreeNode(17));
        T.insert(new OrderStatisticTreeNode(41));
        T.insert(new OrderStatisticTreeNode(14));
        T.insert(new OrderStatisticTreeNode(21));
        T.insert(new OrderStatisticTreeNode(30));
        T.insert(new OrderStatisticTreeNode(47));
        T.insert(new OrderStatisticTreeNode(10));
        T.insert(new OrderStatisticTreeNode(16));
        T.insert(new OrderStatisticTreeNode(19));
        T.insert(new OrderStatisticTreeNode(21));
        T.insert(new OrderStatisticTreeNode(28));
        T.insert(new OrderStatisticTreeNode(38));
        T.insert(new OrderStatisticTreeNode(7));
        T.insert(new OrderStatisticTreeNode(12));
        T.insert(new OrderStatisticTreeNode(14));
        T.insert(new OrderStatisticTreeNode(20));
        T.insert(new OrderStatisticTreeNode(35));
        T.insert(new OrderStatisticTreeNode(39));
        T.insert(new OrderStatisticTreeNode(3));
        T.inorderWalk();
    }

}