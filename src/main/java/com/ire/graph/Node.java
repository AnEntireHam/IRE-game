/*
 * Myles Scholz
 * A Node object for use as part of a Graph object
 */
package com.ire.graph;

import java.util.ArrayList;

public class Node<T> {
    private T value;
    private ArrayList<Node> neighbors;

    public Node() {
        this.value = null;
        this.neighbors = new ArrayList<>();
    }

    public Node(T value) {
        this.value = value;
        this.neighbors = new ArrayList<>();
    }

    @Override
    public String toString() {
        return this.value.toString();
    }

    public boolean compare(Node<T> n) {
        if (this.value == n.getValue()) {
            return true;
        }
        return false;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public Node getNeighbor(int i) {
        return this.neighbors.get(i);
    }

    public void addNeighbor(Node node) {
        this.neighbors.add(node);
    }

    public void removeNeighbor(int i) {
        this.neighbors.remove(i);
    }

    public void removeNeighbor(Node<T> n) {
        for (int i = 0; i < this.neighbors.size(); i++) {
            if (this.neighbors.get(i).compare(n)) {
                this.neighbors.remove(i);
                return;
            }
        }
    }
}
