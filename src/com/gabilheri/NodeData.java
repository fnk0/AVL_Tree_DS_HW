package com.gabilheri;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Marcus Gabilheri on 4/18/16.
 * <a href="mailto:marcus@gabilheri.com">marcus@gabilheri.com</a>
 *
 * @param <T> The type of data this NodeData will hold
 */
public class NodeData<T> {

    T value; // Value that we want to store in this Data object
    List<Integer> occurrences;  // The ordered position to which this Data was inserted into the AvlTree

    /**
     * Default constructor of this Node Data class
     *
     * @param value The value of this Node
     */
    public NodeData(T value) {
        this.value = value;
        this.occurrences = new ArrayList<>();
    }

    /**
     * Constructor containing a value and a list of occurrences
     *
     * @param value       The value of this Node
     * @param occurrences The occurrences where this NodeData appears
     */
    public NodeData(T value, List<Integer> occurrences) {
        this.value = value;
        this.occurrences = occurrences;
    }

    /**
     * @return The value of this Node
     */
    public T getValue() {
        return value;
    }

    /**
     * Sets the value of this NodeData
     *
     * @param value The value for this NodeData
     * @return Instance of this Object
     */
    public NodeData<T> setValue(T value) {
        this.value = value;
        return this;
    }

    /**
     * @return List with the occurrences of this NodeData
     */
    public List<Integer> getOccurrences() {
        return occurrences;
    }

    /**
     * @param occurrences The list of occurrences for this Node
     * @return Instane of this Object
     */
    public NodeData<T> setOccurrences(List<Integer> occurrences) {
        this.occurrences = occurrences;
        return this;
    }

    /**
     * @return The number of times this NodeData occurs in the same AvlTree
     */
    public int getCount() {
        return occurrences.size();
    }

    /**
     * @param position The position that this NodeData occurs
     * @return Instance of this Object
     */
    public NodeData<T> addOccurrence(int position) {
        this.occurrences.add(position);
        return this;
    }

    @Override
    public String toString() {
        return "NodeData{" +
                "value=" + value +
                ", occurrences=" + occurrences +
                '}';
    }
}
