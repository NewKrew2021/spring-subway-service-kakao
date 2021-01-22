import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.WeightedMultigraph;
import org.junit.jupiter.api.Test;
import subway.station.domain.Station;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class TempTest {

    @Test
    public void getDijkstraShortestPath() {
        Station station1 = new Station(1L,"hendo");
        Station station2 = new Station(2L,"brody");
        Station station3 = new Station(3L,"hello");

        WeightedMultigraph<Station, DefaultWeightedEdge> graph
                = new WeightedMultigraph(DefaultWeightedEdge.class);
        graph.addVertex(station1);
        graph.addVertex(station2);
        graph.addVertex(station3);
        graph.setEdgeWeight(graph.addEdge(station1, station2), 2);
        graph.setEdgeWeight(graph.addEdge(station2, station3), 2);
        graph.setEdgeWeight(graph.addEdge(station1, station2), 100);

        DijkstraShortestPath dijkstraShortestPath
                = new DijkstraShortestPath(graph);
        List<Station> shortestPath
                = dijkstraShortestPath.getPath(station3, station1).getVertexList();

        assertThat(shortestPath.size()).isEqualTo(3);
    }

}
