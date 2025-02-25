package algorithm.adt;

public class RedBlackTreeNode extends BinaryTreeNode {

    enum Color {
        RED, 
        BLACK;
    }

    RedBlackTreeNode p;
    RedBlackTreeNode left;
    RedBlackTreeNode right;
    Color color;

}
