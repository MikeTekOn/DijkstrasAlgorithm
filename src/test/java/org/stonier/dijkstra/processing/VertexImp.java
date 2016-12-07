package org.stonier.dijkstra.processing;

import org.stonier.dijkstra.model.Edge;
import org.stonier.dijkstra.model.Vertex;

import java.util.Set;

public class VertexImp implements Vertex {
    String vertexId;
    Set<EdgeImp> edges;

    public void setVertexId(String vertexId) {
        this.vertexId = vertexId;
    }

    public String getVertexId() {
        return vertexId;
    }

    public void setEdges(Set<EdgeImp> edges) {
        this.edges = edges;
    }

    public Set<? extends Edge> getEdges() {
        return edges;
    }

    public String toString() {
        return "vertexId: " + vertexId;
    }
}
