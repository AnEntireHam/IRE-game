/*
 * Myles Scholz
 * A Graph object
 */
package com.ire.graph;

import java.util.ArrayList;

public class Graph<T> {
    Node<T> origin;
    ArrayList<Node<T>> vertices;
    ArrayList<Edge<T>> edges;

    public Graph() {
        this.origin = null;
        this.vertices = new ArrayList<>();
        this.edges = new ArrayList<>();
    }

    public void print() {
        System.out.println(this.vertices);
        System.out.println(this.edges);
    }

    public Node<T> getOrigin() {
        return this.origin;
    }

    public ArrayList<Node<T>> getVertices() {
        return this.vertices;
    }

    public ArrayList<Edge<T>> getEdges() {
        return this.edges;
    }

    public void setOrigin(Node<T> n) {
        this.origin = n;
    }

    public void addVertex(Node<T> n) {
        this.vertices.add(n);
    }

    public void addEdge(Edge<T> e) {
        this.edges.add(e);
    }

    public void addConnection(int i, int j) {
        this.edges.add(new Edge<>(this.vertices.get(i), this.vertices.get(j)));
        this.vertices.get(i).addNeighbor(this.vertices.get(j));
    }

    public void removeVertex(int i) {
        this.vertices.remove(i);
    }

    public void removeEdge(int i) {
        this.edges.remove(i);
    }

    public void removeConnection(int i, int j) {
        for (int k = 0; k < this.edges.size(); k++) {
            if (this.edges.get(k).compare(new Edge<>(this.vertices.get(i), this.vertices.get(j)))) {
                this.edges.remove(k);
                this.vertices.get(i).removeNeighbor(this.vertices.get(j));
                return;
            }
        }
    }
}
