package org.stonier.dijkstra.processing;

import org.stonier.dijkstra.model.Edge;
import org.stonier.dijkstra.model.Graph;
import org.stonier.dijkstra.model.Path;
import org.stonier.dijkstra.model.Vertex;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class Search {

    public static Path getShortestPath(Graph graph, Vertex startVertex, Vertex finishVertex) {

        SearchState searchState = SearchInitializer.initializeGraph(graph, startVertex, finishVertex);

        while (searchState.hasVerticesToVisit()) {
            Optional<VertexWrapper> finishedVertex = visitNextVertexForFinish(searchState);

            if (finishedVertex.isPresent()) {
                return calculatePath(finishedVertex.get());
            }
        }

        throw new IllegalArgumentException("Unable to find path from Start Vertex to Finish Vertex");
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
