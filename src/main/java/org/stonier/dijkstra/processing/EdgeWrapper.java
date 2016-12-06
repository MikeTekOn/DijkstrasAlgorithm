package org.stonier.dijkstra.processing;

import lombok.Builder;
import org.stonier.dijkstra.model.Edge;

@Builder
public class EdgeWrapper {
    Edge edge;
    VertexWrapper connectedVertexWrapper;

    public Edge getEdge() {
        return edge;
    }

   public VertexWrapper getConnectedVertexWrapper() {
        return connectedVertexWrapper;
    }

   @Override
    public String toString() {
        return "edge: " + edge;
    }
}
