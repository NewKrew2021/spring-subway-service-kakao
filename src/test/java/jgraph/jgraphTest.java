package jgraph;

import org.jgrapht.GraphPath;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.WeightedMultigraph;
import org.junit.jupiter.api.Test;
import subway.path.domain.Edge;
import subway.station.domain.Station;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;


public class jgraphTest {
    Station A = new Station(1L, "A");
    Station B = new Station(2L, "B");
    Station C = new Station(3L, "C");
    Station D = new Station(4L, "D");
    Edge A_B = new Edge(A, B, 1, 2);
    Edge B_C = new Edge(B, C, 2, 2);
    Edge A_C = new Edge(A, C, 3, 100);
    Edge B_D = new Edge(B, D, 5, 2);
    Edge C_D = new Edge(C, D, 6, 2);

    @Test
    public void getDijkstraShortestPath() {

        WeightedMultigraph<Station, Edge> graph
                = new WeightedMultigraph<>(Edge.class);

        graph.addVertex(A);
        graph.addVertex(B);
        graph.addVertex(C);
        graph.addEdge(A, B, A_B);
        graph.addEdge(B, C, B_C);
        graph.addEdge(A, C, A_C);

        graph.setEdgeWeight(A_B, 2);
        graph.setEdgeWeight(B_C, 2);
        graph.setEdgeWeight(A_C, 100);


        DijkstraShortestPath<Station, Edge> dijkstraShortestPath = new DijkstraShortestPath<>(graph);
        GraphPath<Station, Edge> shortestPath = dijkstraShortestPath.getPath(C, A);

        assertThat(shortestPath.getWeight()).isEqualTo(4);
        assertThat(shortestPath.getVertexList().size()).isEqualTo(3);
        assertThat(shortestPath.getEdgeList().size()).isEqualTo(2);
    }

    @Test
    public void getDijkstraShortestPath_NoPath() {

        WeightedMultigraph<Station, Edge> graph
                = new WeightedMultigraph<>(Edge.class);

        graph.addVertex(A);
        graph.addVertex(B);
        graph.addVertex(C);
        graph.addVertex(D);
        graph.addEdge(A, B, A_B);
        graph.addEdge(A, C, A_C);

        graph.setEdgeWeight(A_B, 2);
        graph.setEdgeWeight(A_C, 100);

        DijkstraShortestPath<Station, Edge> dijkstraShortestPath = new DijkstraShortestPath<>(graph);
        GraphPath<Station, Edge> shortestPath = dijkstraShortestPath.getPath(D, A);

        assertThat(shortestPath).isEqualTo(null);
    }

    @Test
    public void getDijkstraShortestPath_addDuplicatedVertex() {

        WeightedMultigraph<Station, Edge> graph
                = new WeightedMultigraph<>(Edge.class);

        graph.addVertex(A);
        graph.addVertex(B);
        graph.addEdge(A, B, A_B);

        graph.setEdgeWeight(A_B, 2);

        DijkstraShortestPath<Station, Edge> dijkstraShortestPath = new DijkstraShortestPath<>(graph);
        GraphPath<Station, Edge> shortestPath = dijkstraShortestPath.getPath(B, A);

        assertThat(shortestPath.getWeight()).isEqualTo(2);
    }
}
