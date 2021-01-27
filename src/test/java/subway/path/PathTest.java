package subway.path;

import org.jgrapht.GraphPath;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.WeightedMultigraph;
import org.junit.jupiter.api.Test;
import subway.line.domain.Line;
import subway.line.domain.Section;
import subway.line.domain.Sections;
import subway.path.domain.Path;
import subway.station.domain.Station;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class PathTest {

    @Test
    public void getDijkstraShortestPath() {
        WeightedMultigraph<String, DefaultWeightedEdge> graph
                = new WeightedMultigraph(DefaultWeightedEdge.class);
        graph.addVertex("v1");
        graph.addVertex("v2");
        graph.addVertex("v3");
        graph.setEdgeWeight(graph.addEdge("v1", "v2"), 2);
        graph.setEdgeWeight(graph.addEdge("v2", "v3"), 2);
        graph.setEdgeWeight(graph.addEdge("v1", "v3"), 100);

        DijkstraShortestPath dijkstraShortestPath
                = new DijkstraShortestPath(graph);
        List<String> shortestPath
                = dijkstraShortestPath.getPath("v3", "v1").getVertexList();

        assertThat(graph.containsVertex("v1")).isTrue();
        assertThat(shortestPath.size()).isEqualTo(3);
    }

    @Test
    public void addStationsTest() {
        Path path = new Path();
        Station 강남역 = new Station(1L, "강남역");
        Station 양재역 = new Station(3L, "양재역");
        Station 교대역 = new Station(5L, "교대역");
        Station 판교역 = new Station(4L, "판교역");

        List<Station> stations = Arrays.asList(
                강남역,
                양재역,
                교대역
        );

        path.addStations(stations);

        assertThat(path.containStation(강남역)).isTrue();
        assertThat(path.containStation(양재역)).isTrue();
        assertThat(path.containStation(교대역)).isTrue();
        assertThat(path.containStation(판교역)).isFalse();
    }

    @Test
    public void addEdgesTest() {
        Path path = new Path();
        Station 강남역 = new Station(1L, "강남역");
        Station 양재역 = new Station(3L, "양재역");
        Station 교대역 = new Station(5L, "교대역");

        List<Station> stations = Arrays.asList(
                강남역,
                양재역,
                교대역
        );

        path.addStations(stations);

        List<Section> sections = new ArrayList<>();
        sections.add(new Section(강남역, 양재역, 10));
        sections.add(new Section(양재역, 교대역, 10));
        sections.add(new Section(강남역, 교대역, 15));

        Line line = new Line(1L, "테스트용 라인", "테스트용 라인", 0, new Sections(sections));

        path.addEdges(line);

        assertThat(path.containEdge(강남역, 양재역)).isTrue();
        assertThat(path.containEdge(양재역, 교대역)).isTrue();
        assertThat(path.containEdge(교대역, 강남역)).isTrue();
    }

    @Test
    public void findShortestPathTest() {
        Path path = new Path();
        Station 강남역 = new Station(1L, "강남역");
        Station 양재역 = new Station(3L, "양재역");
        Station 교대역 = new Station(5L, "교대역");

        List<Station> stations = Arrays.asList(
                강남역,
                양재역,
                교대역
        );

        path.addStations(stations);

        List<Section> sections = new ArrayList<>();
        sections.add(new Section(강남역, 양재역, 10));
        sections.add(new Section(양재역, 교대역, 10));
        sections.add(new Section(강남역, 교대역, 15));

        Line line = new Line(1L, "테스트용 라인", "테스트용 라인", 0, new Sections(sections));

        path.addEdges(line);

        GraphPath shortestPathGraph = path.findShortestPathGraph(강남역, 교대역);
        List<Station> shortestPath = shortestPathGraph.getVertexList();
        assertThat(shortestPath.get(0)).isEqualTo(강남역);
        assertThat(shortestPath.get(1)).isEqualTo(교대역);

        int weight = (int) shortestPathGraph.getWeight();
        assertThat(weight).isEqualTo(15);
    }
}