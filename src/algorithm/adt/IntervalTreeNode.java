package algorithm.adt;

public class IntervalTreeNode extends RedBlackTreeNode {

    public static class Interval {
        int low;
        int high;
    }

    IntervalTreeNode p;
    IntervalTreeNode left;
    IntervalTreeNode right;
    Interval interval = new Interval();
    int max;

    public IntervalTreeNode() {
        super();
    }

    public IntervalTreeNode(int low, int high) {
        super();
        key = low;
        interval.low = low;
        interval.high = high;
        max = high;
    }

}
