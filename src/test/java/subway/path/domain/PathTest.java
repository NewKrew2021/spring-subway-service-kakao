package subway.path.domain;

import org.jgrapht.GraphPath;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.WeightedMultigraph;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
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

@DisplayName("지하철 경로 찾기 테스")
public class PathTest {

    private Path path = new Path();
    private Station 강남역;
    private Station 양재역;
    private Station 교대역;
    private Station 판교역;

    @BeforeEach
    public void setup() {
        강남역 = new Station(1L, "강남역");
        양재역 = new Station(3L, "양재역");
        교대역 = new Station(5L, "교대역");
        판교역 = new Station(4L, "판교역");

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

        Line line = new Line(1L, "", "", 0, new Sections(sections));

        path.addEdges(line);
    }

    @DisplayName("경로에 역 삽입 테스트")
    @Test
    public void addStationsTest() {
        assertThat(path.containStation(강남역)).isTrue();
        assertThat(path.containStation(양재역)).isTrue();
        assertThat(path.containStation(교대역)).isTrue();
        assertThat(path.containStation(판교역)).isFalse();
    }

    @DisplayName("경로에 구간 삽입 테스트")
    @Test
    public void addEdgesTest() {
        assertThat(path.containEdge(강남역, 양재역)).isTrue();
        assertThat(path.containEdge(양재역, 교대역)).isTrue();
        assertThat(path.containEdge(교대역, 강남역)).isTrue();
    }

    @DisplayName("최단경로 탐색 테스트")
    @Test
    public void findShortestPathTest() {
        GraphPath shortestPathGraph = path.findShortestPathGraph(강남역, 교대역);
        List<Station> shortestPath = shortestPathGraph.getVertexList();

        assertThat(shortestPath.get(0)).isEqualTo(강남역);
        assertThat(shortestPath.get(1)).isEqualTo(교대역);

        int weight = (int) shortestPathGraph.getWeight();
        assertThat(weight).isEqualTo(15);
    }
}
