package org.stonier.dijkstra.model;

import java.util.Set;

public interface Vertex {
    Set<? extends Edge> getEdges();
}
