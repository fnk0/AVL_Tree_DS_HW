package com.gabilheri;

/**
 * Created by Marcus Gabilheri on 4/18/16.
 * <a href="mailto:marcus@gabilheri.com">marcus@gabilheri.com</a>
 *
 * @param <K> Key
 * @param <V> Value
 */
public class AvlNode<K extends Comparable<K>, V extends NodeData> {

    K key; // The key for this Node
    V value; // The value for this Node
    AvlNode<K, V> left; // The left Node on the AvlTree
    AvlNode<K, V> right; // The right Node on the AvlTree
    int height; // Height of this Node

    /**
     * Default constructor with Key and Value
     *
     * @param key   The Key of this Node
     * @param value The Value of this Node
     */
    public AvlNode(K key, V value) {
        this(key, value, null, null);
    }

    /**
     * Constructor used when left and right are not null
     *
     * @param key   The key of this Node
     * @param value The Value of this Node
     * @param left  The left Node of this Node
     * @param right The right Node of this Node
     */
    public AvlNode(K key, V value, AvlNode<K, V> left, AvlNode<K, V> right) {
        this.key = key;
        this.value = value;
        this.left = left;
        this.right = right;
        this.height = 0;
    }

    /**
     * @return The Key of this Node
     */
    public K getKey() {
        return key;
    }

    /**
     * Sets the key of this Node
     *
     * @param key The key of this Node
     * @return Instance of this object
     */
    public AvlNode setKey(K key) {
        this.key = key;
        return this;
    }

    /**
     * @return The Value of this Node
     */
    public V getValue() {
        return value;
    }

    /**
     * Sets the Value of this Node
     *
     * @param value The Value of this Node
     * @return Instance of this object
     */
    public AvlNode setValue(V value) {
        this.value = value;
        return this;
    }

    /**
     * @return The Left node of this Node
     */
    public AvlNode<K, V> getLeft() {
        return left;
    }

    /**
     * Sets the left Node of this Node
     *
     * @param left The left Node of this Node
     * @return Instance of this Object
     */
    public AvlNode setLeft(AvlNode<K, V> left) {
        this.left = left;
        return this;
    }

    /**
     * @return The Right node of this Node
     */
    public AvlNode<K, V> getRight() {
        return right;
    }

    /**
     * Sets the right Node of this Node
     *
     * @param right The right Node of this Node
     * @return Instance of this Object
     */
    public AvlNode setRight(AvlNode<K, V> right) {
        this.right = right;
        return this;
    }

    /**
     * @return The Height of this Node
     */
    public int getHeight() {
        return height;
    }

    /**
     * Sets the height of this Node
     *
     * @param height The height of the Node
     * @return Instance of this Object
     */
    public AvlNode setHeight(int height) {
        this.height = height;
        return this;
    }
}
