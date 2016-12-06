package org.stonier.dijkstra.processing;

import lombok.Builder;
import org.stonier.dijkstra.model.Vertex;

import java.util.HashSet;
import java.util.Set;

@Builder
public class VertexWrapper {
    private Vertex vertex;
    private Set<EdgeWrapper> edges;
    private long shortestDistanceFromStart;
    private EdgeWrapper previousEdge;
    private VertexWrapper previousVertex; // needed?

    public boolean hasPreviousEdge() {
        return !(null == previousEdge);
    }

    public void addEdge(EdgeWrapper edge) {
        if (edges == null)
            edges = new HashSet<>();
        edges.add(edge);
    }

    @Override
    public int hashCode() {
        return vertex.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof VertexWrapper))
            return false;
        if (obj == this)
            return true;
        return vertex.equals(((VertexWrapper) obj).vertex);
    }

    @Override
    public String toString() {
        return "vertex: " + vertex
                + ", shortestDistanceFromStart: " + shortestDistanceFromStart
                + ", previousEdge: " + previousEdge.edge
                + ", previousVertex: " + previousVertex.vertex;
    }

    public Vertex getVertex() {
        return vertex;
    }

    public Set<EdgeWrapper> getEdges() {
        return edges;
    }

    public long getShortestDistanceFromStart() {
        return shortestDistanceFromStart;
    }

    public void setShortestDistanceFromStart(long shortestDistanceFromStart) {
        this.shortestDistanceFromStart = shortestDistanceFromStart;
    }

    public EdgeWrapper getPreviousEdge() {
        return previousEdge;
    }

    public void setPreviousEdge(EdgeWrapper previousEdge) {
        this.previousEdge = previousEdge;
    }

    public VertexWrapper getPreviousVertex() {
        return previousVertex;
    }

    public void setPreviousVertex(VertexWrapper previousVertex) {
        this.previousVertex = previousVertex;
    }
}
