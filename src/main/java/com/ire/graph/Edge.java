/*
 * Myles Scholz
 * A Edge object consisting of two Nodes for use as part of a Graph object
 */
package com.ire.graph;

import java.util.ArrayList;

public class Edge<T> {
    ArrayList<Node<T>> endpoints;

    public Edge() {
        this.endpoints = new ArrayList<>();
    }

    public Edge(Node<T> n1, Node<T> n2) {
        this.endpoints = new ArrayList<>();
        this.endpoints.add(n1);
        this.endpoints.add(n2);
    }

    @Override
    public String toString() {
        return "[ " + this.endpoints.get(0) + ", " + this.endpoints.get(1) + " ]";
    }

    public boolean compare(Edge<T> e) {
        return this.endpoints.get(0).compare(e.get(0)) &&
                this.endpoints.get(1).compare(e.get(1));
    }

    public boolean contains(Node<T> n) {
        return this.endpoints.get(0).compare(n) ||
                this.endpoints.get(1).compare(n);
    }

    public Node<T> get(int i) {
        return this.endpoints.get(i);
    }

    public void set(int i, Node<T> n) {
        this.endpoints.set(i, n);
    }
}
