package com.gabilheri;

/**
 * Created by Marcus Gabilheri on 4/19/16.
 * <a href="mailto:marcus@gabilheri.com">marcus@gabilheri.com</a>
 *
 * Convenience class to hold the value of a Node and the number of comparisons it took to reach that node
 *
 * @param <K> The Type of the Key for the Node object
 * @param <V> The Type of the Value for the Node object
 */
public class NodeComparisonWrapper<K extends Comparable<K>, V extends NodeData> {
    AvlNode<K, V> node;
    int comparison;

    public NodeComparisonWrapper(AvlNode<K, V> node, int comparison) {
        this.node = node;
        this.comparison = comparison;
    }

    /**
     * @return The Node wrapped in this object
     */
    public AvlNode<K, V> getNode() {
        return node;
    }

    /**
     * Sets the node for this wrapper
     *
     * @param node The node to be wrapped
     * @return Instance of this class
     */
    public NodeComparisonWrapper setNode(AvlNode<K, V> node) {
        this.node = node;
        return this;
    }

    /**
     * @return The number of comparisons that took to reach the node inside this wrapper
     */
    public int getComparison() {
        return comparison;
    }

    /**
     * Sets the number of comparisons that took to reach the node inside this wrapper
     *
     * @param comparison The number of comparisons
     * @return Instance of this class
     */
    public NodeComparisonWrapper setComparison(int comparison) {
        this.comparison = comparison;
        return this;
    }
}
