package org.stonier.dijkstra.model;

import java.util.Set;

public class VertexImp implements Vertex {
    Set<EdgeImp> edges;

    public void setEdges(Set<EdgeImp> edges) {
        this.edges = edges;
    }

    public Set<? extends Edge> getEdges() {
        return edges;
    }
}
