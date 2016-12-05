package org.stonier.dijkstra.processing;

import lombok.Builder;
import lombok.Value;

import java.util.Set;

@Value
@Builder
public class GraphState {
    Set<VertexWrapper> verticesToVisit;
    VertexWrapper startVertex;
    VertexWrapper finishVertex;

    public boolean hasVerticesToVisit() {
        return null != verticesToVisit && !verticesToVisit.isEmpty();
    }
}
