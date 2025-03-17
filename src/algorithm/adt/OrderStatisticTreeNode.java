package algorithm.adt;

public class OrderStatisticTreeNode extends RedBlackTreeNode {

    OrderStatisticTreeNode p;
    OrderStatisticTreeNode left;
    OrderStatisticTreeNode right;
    int size;

    public OrderStatisticTreeNode() {
        super();
    }

    public OrderStatisticTreeNode(int key) {
        super();
        this.key = key;
    }

}
