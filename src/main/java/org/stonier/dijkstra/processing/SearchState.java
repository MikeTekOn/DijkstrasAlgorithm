package org.stonier.dijkstra.processing;

import lombok.Builder;
import org.stonier.dijkstra.model.Vertex;

import java.util.Map;

@Builder
public class SearchState {
    private Map<Vertex, VertexWrapper> verticesToVisit;
    private VertexWrapper startVertex;
    private VertexWrapper finishVertex;

    public boolean hasVerticesToVisit() {
        return null != verticesToVisit && !verticesToVisit.isEmpty();
    }

    public Map<Vertex, VertexWrapper> getVerticesToVisit() {
        return verticesToVisit;
    }

    public void setVerticesToVisit(Map<Vertex, VertexWrapper> verticesToVisit) {
        this.verticesToVisit = verticesToVisit;
    }

    public VertexWrapper getStartVertex() {
        return startVertex;
    }

    public void setStartVertex(VertexWrapper startVertex) {
        this.startVertex = startVertex;
    }

    public VertexWrapper getFinishVertex() {
        return finishVertex;
    }

    public void setFinishVertex(VertexWrapper finishVertex) {
        this.finishVertex = finishVertex;
    }
}
