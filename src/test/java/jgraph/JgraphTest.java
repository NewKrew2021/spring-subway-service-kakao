package jgraph;

import org.jgrapht.GraphPath;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.WeightedMultigraph;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import subway.path.domain.SectionEdge;
import subway.station.domain.Station;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("JGraph를 위한 학습 테스트")
public class JgraphTest {
    Station A = new Station(1L, "A");
    Station B = new Station(2L, "B");
    Station C = new Station(3L, "C");
    Station D = new Station(4L, "D");
    SectionEdge A_B = new SectionEdge(A, B, 1, 2);
    SectionEdge B_C = new SectionEdge(B, C, 2, 2);
    SectionEdge A_C = new SectionEdge(A, C, 3, 100);

    @DisplayName("디익스트라 최단경로 테스트")
    @Test
    public void getDijkstraShortestPath() {
        WeightedMultigraph<Station, SectionEdge> graph
                = new WeightedMultigraph<>(SectionEdge.class);

        graph.addVertex(A);
        graph.addVertex(B);
        graph.addVertex(C);
        graph.addEdge(A, B, A_B);
        graph.addEdge(B, C, B_C);
        graph.addEdge(A, C, A_C);

        graph.setEdgeWeight(A_B, 2);
        graph.setEdgeWeight(B_C, 2);
        graph.setEdgeWeight(A_C, 100);

        DijkstraShortestPath<Station, SectionEdge> dijkstraShortestPath = new DijkstraShortestPath<>(graph);
        GraphPath<Station, SectionEdge> shortestPath = dijkstraShortestPath.getPath(C, A);

        assertThat(shortestPath.getWeight()).isEqualTo(4);
        assertThat(shortestPath.getVertexList().size()).isEqualTo(3);
        assertThat(shortestPath.getEdgeList().size()).isEqualTo(2);
    }

    @DisplayName("경로가 없을 경우 테스트")
    @Test
    public void getDijkstraShortestPath_noPath() {
        WeightedMultigraph<Station, SectionEdge> graph
                = new WeightedMultigraph<>(SectionEdge.class);

        graph.addVertex(A);
        graph.addVertex(B);
        graph.addVertex(C);
        graph.addVertex(D);
        graph.addEdge(A, B, A_B);
        graph.addEdge(A, C, A_C);

        graph.setEdgeWeight(A_B, 2);
        graph.setEdgeWeight(A_C, 100);

        DijkstraShortestPath<Station, SectionEdge> dijkstraShortestPath = new DijkstraShortestPath<>(graph);
        GraphPath<Station, SectionEdge> shortestPath = dijkstraShortestPath.getPath(D, A);

        assertThat(shortestPath).isEqualTo(null);
    }

    @DisplayName("중복 꼭짓점 존재시 작동 테스트")
    @Test
    public void getDijkstraShortestPath_addDuplicatedVertex() {

        WeightedMultigraph<Station, SectionEdge> graph
                = new WeightedMultigraph<>(SectionEdge.class);

        graph.addVertex(A);
        graph.addVertex(A);
        graph.addVertex(B);
        graph.addVertex(B);
        graph.addEdge(A, B, A_B);

        graph.setEdgeWeight(A_B, 2);

        DijkstraShortestPath<Station, SectionEdge> dijkstraShortestPath = new DijkstraShortestPath<>(graph);
        GraphPath<Station, SectionEdge> shortestPath = dijkstraShortestPath.getPath(B, A);

        assertThat(shortestPath.getWeight()).isEqualTo(2);
    }
}
