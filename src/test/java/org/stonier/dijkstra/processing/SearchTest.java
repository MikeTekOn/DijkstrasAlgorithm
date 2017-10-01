package org.stonier.dijkstra.processing;

import org.junit.Test;
import org.stonier.dijkstra.model.Graph;
import org.stonier.dijkstra.model.Path;
import org.stonier.dijkstra.model.Vertex;

import java.util.ArrayList;
import java.util.HashSet;

import static org.junit.Assert.assertEquals;

public class SearchTest {
    @Test
    public void getShortestPath() throws Exception {

        VertexImp vertexA = new VertexImp();
        vertexA.setVertexId("A");
        HashSet<EdgeImp> vertexAEdges = new HashSet<EdgeImp>();

        VertexImp vertexB = new VertexImp();
        vertexB.setVertexId("B");
        vertexB.setEdges(new HashSet<EdgeImp>());
        HashSet<EdgeImp> vertexBEdges = new HashSet<EdgeImp>();

        VertexImp vertexC = new VertexImp();
        vertexC.setVertexId("C");
        vertexC.setEdges(new HashSet<EdgeImp>());
        HashSet<EdgeImp> vertexCEdges = new HashSet<EdgeImp>();

        VertexImp vertexD = new VertexImp();
        vertexD.setVertexId("D");
        vertexD.setEdges(new HashSet<EdgeImp>());
        HashSet<EdgeImp> vertexDEdges = new HashSet<EdgeImp>();

        VertexImp vertexE = new VertexImp();
        vertexE.setVertexId("E");
        vertexE.setEdges(new HashSet<EdgeImp>());
        HashSet<EdgeImp> vertexEEdges = new HashSet<EdgeImp>();

        EdgeImp edgeAtoB = new EdgeImp();
        edgeAtoB.setEdgeId("AtoB");
        edgeAtoB.setWeight(2);
        edgeAtoB.setConnectedVertex(vertexB);
        vertexAEdges.add(edgeAtoB);

        EdgeImp edgeAtoC = new EdgeImp();
        edgeAtoC.setEdgeId("AtoC");
        edgeAtoC.setWeight(3);
        edgeAtoC.setConnectedVertex(vertexC);
        vertexAEdges.add(edgeAtoC);

        EdgeImp edgeBtoC = new EdgeImp();
        edgeBtoC.setEdgeId("BtoC");
        edgeBtoC.setWeight(2);
        edgeBtoC.setConnectedVertex(vertexC);
        vertexBEdges.add(edgeBtoC);

        EdgeImp edgeCtoD = new EdgeImp();
        edgeCtoD.setEdgeId("CtoD");
        edgeCtoD.setWeight(4);
        edgeCtoD.setConnectedVertex(vertexD);
        vertexCEdges.add(edgeCtoD);

        EdgeImp edgeDtoE = new EdgeImp();
        edgeDtoE.setEdgeId("DtoE");
        edgeDtoE.setWeight(4);
        edgeDtoE.setConnectedVertex(vertexE);
        vertexDEdges.add(edgeDtoE);

        EdgeImp edgeCtoE = new EdgeImp();
        edgeCtoE.setEdgeId("CtoE");
        edgeCtoE.setWeight(7);
        edgeCtoE.setConnectedVertex(vertexE);
        vertexCEdges.add(edgeCtoE);

        vertexA.setEdges(vertexAEdges);
        vertexB.setEdges(vertexBEdges);
        vertexC.setEdges(vertexCEdges);
        vertexD.setEdges(vertexDEdges);
        vertexE.setEdges(vertexEEdges);

        HashSet<Vertex> vertices = new HashSet<>();
        vertices.add(vertexA);
        vertices.add(vertexB);
        vertices.add(vertexC);
        vertices.add(vertexD);
        vertices.add(vertexE);

        Graph graph = Graph.builder().vertices(vertices).build();

        //       A
        //      2 3
        //     B 2 C
        //        / 4
        //       7   D
        //        \ 4
        //         E

        Path shortestPath = Search.getShortestPath(graph, vertexA, vertexE);

        ArrayList<EdgeImp> expectedShortestPath = new ArrayList<>();
        expectedShortestPath.add(edgeAtoC);
        expectedShortestPath.add(edgeCtoE);

        assertEquals(expectedShortestPath, shortestPath.getEdges());

        System.out.println("Shortest Path when C to E = 7:");
        shortestPath.getEdges().forEach(System.out::println);

        edgeCtoE.setWeight(9);

        //       A
        //      2 3
        //     B 2 C
        //        / 4
        //       9   D
        //        \ 4
        //         E

        shortestPath = Search.getShortestPath(graph, vertexA, vertexE);

        expectedShortestPath = new ArrayList<>();
        expectedShortestPath.add(edgeAtoC);
        expectedShortestPath.add(edgeCtoD);
        expectedShortestPath.add(edgeDtoE);

        assertEquals(expectedShortestPath, shortestPath.getEdges());

        System.out.println("\nShortest Path when C to E = 9:");
        shortestPath.getEdges().forEach(System.out::println);
    }
}