package algorithm.adt;

public class BTreeNode {

    int n;
    char[] keys;
    boolean leaf;
    BTreeNode[] children;
    int t;

    public BTreeNode() {
        t = 2;
        keys = new char[2 * t - 1];
        children = new BTreeNode[2 * t];
    }

    public BTreeNode(int t) {
        this.t = t;
        keys = new char[2 * t - 1];
        children = new BTreeNode[2 * t];
    }
    
}
