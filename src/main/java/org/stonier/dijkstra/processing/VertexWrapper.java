package org.stonier.dijkstra.processing;

import lombok.Builder;
import lombok.Value;
import lombok.experimental.Wither;
import org.stonier.dijkstra.model.Vertex;

import java.util.Set;

@Value
@Builder
@Wither
public class VertexWrapper {
    Vertex vertex;
    Set<EdgeWrapper> edgeWrappers;
    boolean visited;
    long shortestDistanceFromStart;
    EdgeWrapper previousEdgeWrapper;
    VertexWrapper previousVertexWrapper; // needed?

    public boolean hasPreviousEdgeWrapper() {
        return !(null == previousEdgeWrapper);
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
        return vertex.equals(((VertexWrapper) obj).getVertex());
    }
}
