package com.gabilheri;

import java.util.List;

/**
 * Created by Marcus Gabilheri on 4/18/16.
 * <a href="mailto:marcus@gabilheri.com">marcus@gabilheri.com</a>
 * <p>
 * <p>
 * AVL Tree implementation for Data Structures class Spring 2016
 * The implementation of this class is based on the book: Data Structures and Algorithms in C++ by Mark Weiss
 * <p>
 * <p>
 * The supported operations of this class are:
 * <ul>
 * <li> void insert(K, V);                  Insert K, V </li>
 * <li> void remove(K);                     Remove K </li>
 * <li> NodeComparisonWrapper find(K)       Return a NodeWrapper with a Node and it's number of comparisons to find </li>
 * <li> Comparable findMin();               Return smallest item </li>
 * <li> Comparable findMax();               Return largest item </li>
 * <li> boolean isEmpty();                  Return true if empty; else false </li>
 * <li> int getHeight();                    Returns the height of this tree, -1 if is empty </li>
 * <li> void makeEmpty();                   Remove all items </li>
 * <li> void printTree();                   Print tree in sorted order </li>
 * <li> void printCount();                  Prints the count of each item in sorted order </li>
 * <li> List<Integer> occurs(K);            Returns the occurrences of that Node in the tree </li>
 * <li> int count(K);                       Returns the number of occurrences of that Node in the tree </li>
 * <li> AvlNode<K, V> replace(K1, K2);      Replaces K1 with K2. If K2 is null then K1 is removed from the Tree </li>
 * </ul>
 *
 * @param <K> Key
 * @param <V> Value
 */
public class AvlTreeMap<K extends Comparable<K>, V extends NodeData> {

    /**
     * Constant specifying where we allow inbalance on this tree or not
     */
    private static final int ALLOWED_IMBALANCE = 1;

    /**
     * The root of this tree
     */
    AvlNode<K, V> root;

    /**
     * Default Constructor. Creates a tree with a empty root
     */
    public AvlTreeMap() {
        this(null);
    }

    /**
     * Constructor to create a tree with a known root
     *
     * @param root The root of the tree
     */
    public AvlTreeMap(AvlNode<K, V> root) {
        this.root = root;
    }

    /**
     * Inserts a node into the tree and adds the position to it's list of occurrences
     *
     * @param key      The Key of the Node
     * @param value    The Value of the Node
     * @param position The position of the Node
     */
    public void insert(K key, V value, int position) {
        root = insert(key, value, position, root);
    }

    /**
     * Public facing method to insert a node when position is not important
     *
     * @param key   The key of the Node
     * @param value The value of the Node
     */
    public void insert(K key, V value) {
        root = insert(key, value, -1, root);
    }

    /**
     * Internal method to insert a node into the tree
     *
     * @param key      The key of the node
     * @param value    The value of the node
     * @param position The occurence position. If we don't care about this -1 should be passed
     * @param rootNode The rootNode of the tree
     * @return The newly inserted Node after balancing the tree
     */
    private AvlNode<K, V> insert(K key, V value, int position, AvlNode<K, V> rootNode) {
        if (rootNode == null) {
            AvlNode<K, V> node = new AvlNode<>(key, value);
            addOccurenceToNode(node, position);
            return node;
        }

        if (key.equals(rootNode.getKey())) {
            addOccurenceToNode(rootNode, position);
        }

        int compareResult = key.compareTo(rootNode.getKey());

        if (compareResult < 0) {
            rootNode.setLeft(insert(key, value, position, rootNode.getLeft()));
        } else if (compareResult > 0) {
            rootNode.setRight(insert(key, value, position, rootNode.getRight()));
        }

        return balance(rootNode);
    }

    /**
     * Convenience methot to add a occurence position to a Node
     * It first checks if the position is not -1 and then adds the occurrence to such node
     *
     * @param node     The node to which the occurrence will be added
     * @param position The position to add to the occurrence list
     */
    public void addOccurenceToNode(AvlNode<K, V> node, int position) {
        if (position != -1) {
            node.getValue().addOccurrence(position);
        }
    }

    /**
     * @param node The node to be balanced
     * @return The balanced node
     */
    private AvlNode<K, V> balance(AvlNode<K, V> node) {
        if (node == null) {
            return null;
        }

        if (height(node.getLeft()) - height(node.getRight()) > ALLOWED_IMBALANCE) {
            if (height(node.getLeft().getLeft()) >= height(node.getLeft().getRight())) {
                node = rotateWithLeftChild(node);
            } else {
                node = doubleWithLeftChild(node);
            }
        } else if (height(node.getRight()) - height(node.getLeft()) > ALLOWED_IMBALANCE) {
            if (height(node.getRight().getRight()) >= height(node.getRight().getLeft())) {
                node = rotateWithRightChild(node);
            } else {
                node = doubleWithRightChild(node);
            }
        }

        node.setHeight(Math.max(height(node.getLeft()), height(node.getRight())) + 1);
        return node;
    }

    /**
     * @param node The node to which we want the height
     * @return The height of the node or -1 if null
     */
    private int height(AvlNode<K, V> node) {
        return node == null ? -1 : node.getHeight();
    }

    /**
     * Rotate binary tree node with left child.
     * For AVL trees, this is a single rotation for case 1.
     * Update heights, then return new root.
     */
    private AvlNode<K, V> rotateWithLeftChild(AvlNode<K, V> node2) {
        AvlNode<K, V> node1 = node2.getLeft();
        node2.setLeft(node1.getRight());
        node1.setRight(node2);
        node2.height = Math.max(height(node2.getLeft()), height(node2.getRight())) + 1;
        node1.height = Math.max(height(node1.getLeft()), node2.getHeight()) + 1;
        return node1;
    }

    /**
     * Rotate binary tree node with right child.
     * For AVL trees, this is a single rotation for case 4.
     * Update heights, then return new root.
     */
    private AvlNode<K, V> rotateWithRightChild(AvlNode<K, V> node1) {
        AvlNode<K, V> node2 = node1.getRight();
        node1.setRight(node2.getLeft());
        node2.setLeft(node1);
        node1.height = Math.max(height(node1.getLeft()), height(node1.getRight())) + 1;
        node2.height = Math.max(height(node2.getRight()), node1.getHeight()) + 1;
        return node2;
    }

    /**
     * Double rotate binary tree node: first left child
     * with its right child; then node node with new left child.
     * For AVL trees, this is a double rotation for case 2.
     * Update heights, then return new root.
     */
    private AvlNode<K, V> doubleWithLeftChild(AvlNode<K, V> node) {
        node.setLeft(rotateWithRightChild(node.getLeft()));
        return rotateWithLeftChild(node);
    }

    /**
     * Double rotate binary tree node: first right child
     * with its left child; then node node with new right child.
     * For AVL trees, this is a double rotation for case 3.
     * Update heights, then return new root.
     */
    private AvlNode<K, V> doubleWithRightChild(AvlNode<K, V> node) {
        node.setRight(rotateWithLeftChild(node.getRight()));
        return rotateWithRightChild(node);
    }

    /**
     * Test if the tree is logically empty.
     *
     * @return true if empty, false otherwise.
     */
    public boolean isEmpty() {
        return root == null;
    }

    /**
     * Print the tree contents in sorted order.
     * <p>
     * If the tree is empty a message is printed to the console.
     */
    public void printTree() {
        if (isEmpty()) {
            System.out.println("Empty tree");
        } else {
            printTree(root);
        }
    }

    /**
     * Public facing method to print the count of all nodes in the tree
     * Each node is printed in a separate line within the format: <key>: <count>
     * <p>
     * If the tree is empty a message is printed to the console.
     */
    public void printCount() {
        if (isEmpty()) {
            System.out.println("Empty Tree");
        } else {
            printCount(root);
        }
    }

    /**
     * Internal method to print a subtree in sorted order.
     *
     * @param node The node that roots the tree.
     */
    private void printTree(AvlNode<K, V> node) {
        if (node != null) {
            printTree(node.getLeft());
            System.out.println(node.getKey() + ": " + occurs(node.getKey()));
            printTree(node.getRight());
        }
    }

    /**
     * Internal method to print to the System output the count for all the nodes in the tree
     *
     * @param node The root node of the tree
     */
    private void printCount(AvlNode<K, V> node) {
        if (node != null) {
            printCount(node.getLeft());
            System.out.println(node.getKey() + ": " + count(node.getKey()));
            printCount(node.getRight());
        }
    }

    /**
     * Remove from the tree. Nothing is done if key is not found.
     *
     * @param key The item to remove.
     */
    public void remove(K key) {
        root = remove(key, root);
    }

    /**
     * Internal method to remove from a subtree.
     *
     * @param key  The item to remove.
     * @param node The node that roots the subtree.
     * @return The new root of the subtree.
     */
    private AvlNode<K, V> remove(K key, AvlNode<K, V> node) {
        if (node == null) {
            return null;
        }

        int compareResult = key.compareTo(node.key);

        if (compareResult < 0) {
            node.setLeft(remove(key, node.getLeft()));
        } else if (compareResult > 0) {
            node.setRight(remove(key, node.getRight()));
        } else if (node.getLeft() != null && node.getRight() != null) {
            node.setKey(findMin(node.getRight()).getKey());
            node.setRight(remove(node.getKey(), node.getRight()));
        } else {
            node = (node.getLeft() != null) ? node.getLeft() : node.getRight();
        }
        return balance(node);
    }

    /**
     * Public facing method to find a specific node in the Tree
     *
     * @param key The key of the node to be found
     * @return A NodeComparisonWrapper object containing the Node that was found or null and the number of times
     * a comparison was done to find such node. -1 if the node is not found
     */
    public NodeComparisonWrapper<K, V> find(K key) {
        return find(key, root);
    }

    /**
     * Make the tree logically empty.
     */
    public void makeEmpty() {
        root = null;
    }

    /**
     * @return The height of this tree, -1 if is empty
     */
    public int getHeight() {
        return height(root);
    }

    /**
     * Internal method to find the smallest key in a subtree.
     *
     * @param node the node that roots the tree.
     * @return node containing the smallest item.
     */
    private AvlNode<K, V> findMin(AvlNode<K, V> node) {
        if (node == null) {
            return null;
        }

        while (node.getLeft() != null) {
            node = node.getLeft();
        }

        return node;
    }

    /**
     * Internal method to find the largest key in a subtree.
     *
     * @param node the node that roots the tree.
     * @return node containing the largest item.
     */
    private AvlNode<K, V> findMax(AvlNode<K, V> node) {
        if (node == null) {
            return null;
        }
        while (node.getRight() != null) {
            node = node.getRight();
        }

        return node;
    }

    /**
     * Internal method to find  a specific node in the Tree
     *
     * @param key  The key of the node to be found
     * @param node The root node of the tree
     * @return A NodeComparisonWrapper object containing the Node that was found or null and the number of times
     * a comparison was done to find such node. -1 if the node is not found
     */
    private NodeComparisonWrapper<K, V> find(K key, AvlNode<K, V> node) {
        int count = 0;
        while (node != null) {
            int compareResult = key.compareTo(node.key);
            if (compareResult < 0) {
                count++;
                node = node.getLeft();
            } else if (compareResult > 0) {
                count++;
                node = node.getRight();
            } else {
                return new NodeComparisonWrapper<>(node, count);
            }
        }
        return new NodeComparisonWrapper<>(null, -1);
    }

    /**
     * @param key The key of the node we need the occurences for
     * @return A list with all the occurences and positions for that node
     */
    public List<Integer> occurs(K key) {
        AvlNode<K, V> node = find(key).getNode();
        if (node == null) {
            return null;
        }
        return node.getValue().getOccurrences();
    }

    /**
     * @param key The key of the node that we want to count
     * @return The number of times that node appeared
     * -1 if the Node can not be found
     */
    public int count(K key) {
        AvlNode<K, V> node = find(key).getNode();
        if (node == null) {
            return -1;
        }
        return node.getValue().getCount();
    }

    /**
     * Replaces the node associated with key1 with key2
     * If key2 is not specified the Node associated with key1 is removed
     *
     * @param key1 The key for the node to be replaced
     * @param key2 The key for the node replacing the 1st node
     * @return null if the node can not be found
     * A node with all keys and values null if the 1st node is removed
     * A newly created node with key2 as its key if the values are swapped
     */
    public AvlNode<K, V> replace(K key1, K key2) {
        AvlNode<K, V> node = find(key1).getNode();

        if (node == null) {
            return null;
        }

        if (key2 == null) {
            remove(key1);
            return new AvlNode<>(null, null, null, null);
        }

        remove(key1);
        remove(key2);
        node.getValue().setValue(key2);
        return insert(key2, node.getValue(), -1, root);
    }
}
