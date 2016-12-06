package org.stonier.dijkstra.processing;

import org.stonier.dijkstra.model.Edge;
import org.stonier.dijkstra.model.Graph;
import org.stonier.dijkstra.model.Path;
import org.stonier.dijkstra.model.Vertex;

import java.util.*;
import java.util.stream.Collectors;

public class Search {
    private static final long INFINITY = Long.MAX_VALUE;

    public static Path getShortestPath(Graph graph, Vertex startVertex, Vertex finishVertex) {

        SearchState searchState = initializeGraph(graph, startVertex, finishVertex);

        while (searchState.hasVerticesToVisit()) {
            Optional<VertexWrapper> finishedVertex = visitNextVertexForFinish(searchState);

            if (finishedVertex.isPresent()) {
                return calculatePath(finishedVertex.get());
            }
        }

        throw new IllegalArgumentException("Unable to find path from Start Vertex to Finish Vertex");
    }

    private static SearchState initializeGraph(Graph graph, Vertex startVertex, Vertex finishVertex) {
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

    private static Optional<VertexWrapper> visitNextVertexForFinish(SearchState searchState) {

        VertexWrapper vertexToVisit = getUnvisitedVertexWithSmallestDistanceFromStart(searchState);

        if (vertexToVisit.equals(searchState.getFinishVertex())) {
            return Optional.of(vertexToVisit);
        }

        searchState.getVerticesToVisit().remove(vertexToVisit.getVertex());

        for (EdgeWrapper edgeWrapper : vertexToVisit.getEdges()) {
            long distanceFromStart = vertexToVisit.getShortestDistanceFromStart() + edgeWrapper.getEdge().getWeight();
            if (distanceFromStart < edgeWrapper.getConnectedVertexWrapper().getShortestDistanceFromStart()) {
                edgeWrapper.getConnectedVertexWrapper().setShortestDistanceFromStart(distanceFromStart);
                edgeWrapper.getConnectedVertexWrapper().setPreviousEdge(edgeWrapper);
                edgeWrapper.getConnectedVertexWrapper().setPreviousVertex(vertexToVisit);
           }

        }
        return Optional.empty();
    }

    private static VertexWrapper getUnvisitedVertexWithSmallestDistanceFromStart(SearchState searchState) {
        return searchState.getVerticesToVisit().values().stream()
                .min((vw1, vw2) -> Long.compare(vw1.getShortestDistanceFromStart(), vw2.getShortestDistanceFromStart()))
                .get();
    }

    private static Path calculatePath(VertexWrapper vertexWrapper) {
        List<Edge> edgePath = new ArrayList<>();
        while (vertexWrapper.hasPreviousEdge()) {
            edgePath.add(vertexWrapper.getPreviousEdge().getEdge());
            vertexWrapper = vertexWrapper.getPreviousVertex();
        }

        Collections.reverse(edgePath);

        return Path.builder().edges(edgePath).build();
    }
}
