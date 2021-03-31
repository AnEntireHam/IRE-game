/*
 * Myles Scholz
 * A driver file to test a Graph object
 */
package com.ire.graph;

public class Driver {

    public static void main(String[] args) {
        Graph<Integer> graph = new Graph<>();
        int n = 5;

        for (int i = 0; i < n; i++) {
            graph.addVertex(new Node<Integer>(i));
        }

        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                graph.addConnection(i, j);
            }
        }

        graph.print();

        Node<Integer> curr = graph.getVertices().get(0);
        for (int i = 0; i < n - 1; i++) {
            System.out.println(curr);
            curr = curr.getNeighbor(0);
        }
    }
}
