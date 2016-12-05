package org.stonier.dijkstra.model;

import lombok.Builder;
import lombok.Value;

import java.util.List;

@Value
@Builder
public class Path {
    List<Edge> edges;
}
