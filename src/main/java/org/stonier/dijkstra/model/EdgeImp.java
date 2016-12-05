package org.stonier.dijkstra.model;

public class EdgeImp implements Edge {
    long weight;
    VertexImp connectedVertex;

    public void setWeight(long weight) {
        this.weight = weight;
    }

    public void setConnectedVertex(VertexImp connectedVertex) {
        this.connectedVertex = connectedVertex;
    }

    public long getWeight() {
        return weight;
    }

    public Vertex getConnectedVertex() {
        return connectedVertex;
    }
}

