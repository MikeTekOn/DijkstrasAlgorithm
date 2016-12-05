package org.stonier.dijkstra.processing;

import org.stonier.dijkstra.model.Edge;
import org.stonier.dijkstra.model.Graph;
import org.stonier.dijkstra.model.Path;
import org.stonier.dijkstra.model.Vertex;

import java.util.*;
import java.util.stream.Collectors;

public class Search {
    private static final long INFINITY = Long.MAX_VALUE;

    public Path getShortestPath(Graph graph, Vertex startVertex, Vertex finishVertex) {

        GraphState graphState = initializeGraph(graph, startVertex, finishVertex);

        while (graphState.hasVerticesToVisit()) {
            Optional<VertexWrapper> finishedVertex = visitNextVertexForFinish(graphState);

            if (finishedVertex.isPresent()) {
                return calculatePath(finishedVertex.get());
            }
        }

        throw new IllegalArgumentException("Unable to find path from Start Vertex to Finish Vertex");
    }

    private GraphState initializeGraph(Graph graph, Vertex startVertex, Vertex finishVertex) {
        Set<VertexWrapper> wrappedVertices = graph.getVertices().stream()
                .map(vertex -> wrapVertex(vertex, startVertex))
                .collect(Collectors.toSet());

        Optional<VertexWrapper> wrappedStartVertex = wrappedVertices.stream()
                .filter(vertexWrapper -> vertexWrapper.getVertex().equals(startVertex))
                .findFirst();
        if (!wrappedStartVertex.isPresent()) {
            throw new IllegalArgumentException("Start Vertex not equal to any Vertex in the Graph");
        }

        Optional<VertexWrapper> wrappedFinishVertex = wrappedVertices.stream()
                .filter(vertexWrapper -> vertexWrapper.getVertex().equals(finishVertex))
                .findFirst();
        if (!wrappedFinishVertex.isPresent()) {
            throw new IllegalArgumentException("Finish Vertex not equal to any Vertex in the Graph");
        }

        return GraphState.builder()
                .verticesToVisit(wrappedVertices)
                .startVertex(wrappedStartVertex.get())
                .finishVertex(wrappedFinishVertex.get())
                .build();
    }

    private VertexWrapper wrapVertex(Vertex vertex, Vertex startVertex) {

        long shortestDistanceFromStart = INFINITY;
        if (vertex.equals(startVertex)) {
            shortestDistanceFromStart = 0;
        }

        return VertexWrapper.builder()
                .vertex(vertex)
                .shortestDistanceFromStart(shortestDistanceFromStart)
                .build();
    }

    private Optional<VertexWrapper> visitNextVertexForFinish(GraphState graphState) {

        VertexWrapper nextVertexToVisit = getUnvisitedVertexWithSmallestDistanceFromStart(graphState);

        if (nextVertexToVisit.equals(graphState.getFinishVertex())) {
            return Optional.of(nextVertexToVisit);
        }

        graphState.getVerticesToVisit().remove(nextVertexToVisit);

        for (EdgeWrapper edgeWrapper : nextVertexToVisit.getEdgeWrappers()) {
            long distanceFromStart = nextVertexToVisit.getShortestDistanceFromStart() + edgeWrapper.getEdge().getWeight();
            if (distanceFromStart < edgeWrapper.getConnectedVertexWrapper().getShortestDistanceFromStart()) {
                VertexWrapper updatedVertex = edgeWrapper.getConnectedVertexWrapper()
                        .withShortestDistanceFromStart(distanceFromStart)
                        .withPreviousEdgeWrapper(edgeWrapper)
                        .withPreviousVertexWrapper(nextVertexToVisit);
                graphState.getVerticesToVisit().add(updatedVertex);
            }

        }
        return Optional.empty();
    }

    private VertexWrapper getUnvisitedVertexWithSmallestDistanceFromStart(GraphState graphState) {
        return graphState.getVerticesToVisit().stream()
                .min((vw1, vw2) -> Long.compare(vw1.getShortestDistanceFromStart(), vw2.getShortestDistanceFromStart()))
                .get();
    }

    private Path calculatePath(VertexWrapper vertexWrapper) {
        List<Edge> path = new ArrayList<>();
        while (vertexWrapper.hasPreviousEdgeWrapper()) {
            path.add(vertexWrapper.getPreviousEdgeWrapper().getEdge());
            vertexWrapper = vertexWrapper.getPreviousVertexWrapper();
        }

        Collections.reverse(path);

        return Path.builder().edges(path).build();
    }
}
