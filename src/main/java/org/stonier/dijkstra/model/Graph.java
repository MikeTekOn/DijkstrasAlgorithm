package org.stonier.dijkstra.model;

import lombok.Builder;
import lombok.Value;

import java.util.Set;

@Value
@Builder
public class Graph {
    Set<Vertex> vertices;
}
