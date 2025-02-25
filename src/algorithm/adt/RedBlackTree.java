package algorithm.adt;

public class RedBlackTree extends BinarySearchTree {

    private RedBlackTreeNode root;
    private static RedBlackTreeNode nil;

    static {
        nil = new RedBlackTreeNode();
        nil.color = RedBlackTreeNode.Color.BLACK;
    }

    public RedBlackTree() {
        root = nil;
    }

    private void leftRotate(RedBlackTreeNode x) {
        RedBlackTreeNode y = x.right;
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
    }

    private void rightRotate(RedBlackTreeNode y) {
        RedBlackTreeNode x = y.left;
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
    }

    public void insert(RedBlackTreeNode z) {
        RedBlackTreeNode x = root; // node being compared with z
        RedBlackTreeNode y = nil; // y will be parent of z
        while (x != nil) { // descend until reaching the sentinel
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

    private void insertFixup(RedBlackTreeNode z) {
        while (z.p.color == RedBlackTreeNode.Color.RED) {
            if (z.p == z.p.p.left) { // is z's parent a left child?
                RedBlackTreeNode y = z.p.p.right; // y is z's uncle
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
                RedBlackTreeNode y = z.p.p.left;
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

    private void transplant(RedBlackTreeNode u, RedBlackTreeNode v) {
        if (u.p == nil) {
            root = v;
        } else if (u == u.p.left) {
            u.p.left = v;
        } else {
            u.p.right = v;
        }
        v.p = u.p;
    }

    public void delete(RedBlackTreeNode z) {
        RedBlackTreeNode y = z;
        RedBlackTreeNode.Color yOriginalColor = y.color;
        RedBlackTreeNode x;
        if (z.left == nil) {
            x = z.right;
            transplant(z, z.right); // replace z by its right child
        } else if (z.right == nil) {
            x = z.left;
            transplant(z, z.left); // replace z by its left child
        } else {
            y = (RedBlackTreeNode) minimum(z.right); // y is z's successor
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

    private void deleteFixup(RedBlackTreeNode x) {
        while (x != root && x.color == RedBlackTreeNode.Color.BLACK) {
            if (x == x.p.left) { // is x a left child?
                RedBlackTreeNode w = x.p.right; // w is x's sibling
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
                RedBlackTreeNode w = x.p.left;
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

    public static void inorderWalk(RedBlackTreeNode x) {
        if (x != nil) {
            inorderWalk(x.left);
            System.out.print(x.key + " " + x.color + " ");
            inorderWalk(x.right);
        }
    }

    public static void main(String[] args) {
        RedBlackTree tree = new RedBlackTree();
        RedBlackTreeNode node = new RedBlackTreeNode();
        node.key = 11;
        tree.insert(node);
        node = new RedBlackTreeNode();
        node.key = 2;
        tree.insert(node);
        node = new RedBlackTreeNode();
        node.key = 14;
        tree.insert(node);
        node = new RedBlackTreeNode();
        node.key = 1;
        tree.insert(node);
        node = new RedBlackTreeNode();
        node.key = 7;
        tree.insert(node);
        node = new RedBlackTreeNode();
        node.key = 15;
        tree.insert(node);
        node = new RedBlackTreeNode();
        node.key = 5;
        tree.insert(node);
        node = new RedBlackTreeNode();
        node.key = 8;
        tree.insert(node);
        tree.inorderWalk();
        System.out.println();
        node = new RedBlackTreeNode();
        node.key = 4;
        tree.insert(node);
        tree.inorderWalk();
    }

}
