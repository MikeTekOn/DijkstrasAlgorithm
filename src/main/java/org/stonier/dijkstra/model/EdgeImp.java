package org.stonier.dijkstra.model;

public class EdgeImp implements Edge {
    String edgeId;
    long weight;
    VertexImp connectedVertex;

    public void setEdgeId(String edgeId) {
        this.edgeId = edgeId;
    }

    public void setWeight(long weight) {
        this.weight = weight;
    }

    public void setConnectedVertex(VertexImp connectedVertex) {
        this.connectedVertex = connectedVertex;
    }

    public String getEdgeId() {
        return edgeId;
    }

    public long getWeight() {
        return weight;
    }

    public Vertex getConnectedVertex() {
        return connectedVertex;
    }

    public String toString() {
        return "edgeId: " + edgeId;
    }
}

