package org.stonier.dijkstra.processing;

import lombok.Builder;
import lombok.Value;
import org.stonier.dijkstra.model.Edge;

@Value
@Builder
public class EdgeWrapper {
    Edge edge;
    VertexWrapper connectedVertexWrapper;
}
