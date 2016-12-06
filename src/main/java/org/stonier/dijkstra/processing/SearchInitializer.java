package org.stonier.dijkstra.processing;

import org.stonier.dijkstra.model.Edge;
import org.stonier.dijkstra.model.Graph;
import org.stonier.dijkstra.model.Vertex;

import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class SearchInitializer {
    private static final long INFINITY = Long.MAX_VALUE;

    public static SearchState initializeGraph(Graph graph, Vertex startVertex, Vertex finishVertex) {

        Map<Vertex, VertexWrapper> wrappedVertices = graph.getVertices().stream()
                .map(vertex -> wrapVertex(vertex, startVertex))
                .collect(Collectors.toMap(VertexWrapper::getVertex, vertexWrapper -> vertexWrapper));

        Optional<VertexWrapper> wrappedStartVertex = wrappedVertices.values().stream()
                .filter(vertexWrapper -> vertexWrapper.getVertex().equals(startVertex))
                .findFirst();
        if (!wrappedStartVertex.isPresent()) {
            throw new IllegalArgumentException("Start Vertex not equal to any Vertex in the Graph");
        }

        Optional<VertexWrapper> wrappedFinishVertex = wrappedVertices.values().stream()
                .filter(vertexWrapper -> vertexWrapper.getVertex().equals(finishVertex))
                .findFirst();
        if (!wrappedFinishVertex.isPresent()) {
            throw new IllegalArgumentException("Finish Vertex not equal to any Vertex in the Graph");
        }

        wrappedVertices.values().forEach(vertex -> wrapVertexEdges(vertex, wrappedVertices));

        return SearchState.builder()
                .verticesToVisit(wrappedVertices)
                .startVertex(wrappedStartVertex.get())
                .finishVertex(wrappedFinishVertex.get())
                .build();
    }

    private static VertexWrapper wrapVertex(Vertex vertex, Vertex startVertex) {

        long shortestDistanceFromStart = INFINITY;
        if (vertex.equals(startVertex)) {
            shortestDistanceFromStart = 0;
        }

        return VertexWrapper.builder()
                .vertex(vertex)
                .shortestDistanceFromStart(shortestDistanceFromStart)
                .build();
    }

    private static void wrapVertexEdges(VertexWrapper vertexWrapper, Map<Vertex, VertexWrapper> vertices) {

        vertexWrapper.getVertex().getEdges().stream()
                .map(edge -> wrapEdge(edge, vertices))
                .forEach(vertexWrapper::addEdge);
    }

    private static EdgeWrapper wrapEdge(Edge edge, Map<Vertex, VertexWrapper> vertices) {

        return EdgeWrapper.builder()
                .edge(edge)
                .connectedVertexWrapper(vertices.get(edge.getConnectedVertex()))
                .build();
    }
}
