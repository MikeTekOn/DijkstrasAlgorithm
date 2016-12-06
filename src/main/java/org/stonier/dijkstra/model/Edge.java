package org.stonier.dijkstra.model;

public interface Edge {
    long getWeight();
    Vertex getConnectedVertex();
}
