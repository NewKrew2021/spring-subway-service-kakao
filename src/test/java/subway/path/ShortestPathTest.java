package subway.path;

import org.jgrapht.GraphPath;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import subway.AcceptanceTest;
import subway.line.domain.Line;
import subway.line.domain.Section;
import subway.line.domain.Sections;
import subway.path.domain.ShortestGraph;
import subway.station.domain.Station;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ShortestPathTest extends AcceptanceTest {
    private Station 교대역;
    private Station 양재역;
    private Station 남부터미널역;
    private Section 교대양재;
    private Section 남부터미널양재;
    private Line 삼호선;

    @BeforeEach
    public void setUp() {
        super.setUp();

        교대역 = new Station(3L, "교대역");
        양재역 = new Station(4L, "양재역");
        남부터미널역 = new Station(5L, "남부터미널역");

        교대양재 = new Section(4L, 교대역, 양재역, 5);
        남부터미널양재 = new Section(5L, 남부터미널역, 양재역, 3);

        삼호선 = new Line(3L, "삼호선", "bg-red-600", 0, new Sections(Arrays.asList(교대양재, 남부터미널양재)));
    }

    @Test
    void shortestPathWeight(){
        GraphPath graphPath = new ShortestGraph(Arrays.asList(삼호선), 교대역, 양재역).getGraphPath();
        assertThat(graphPath.getWeight()).isEqualTo(5);
    }

    @Test
    void shortestPathStations(){
        GraphPath graphPath = new ShortestGraph(Arrays.asList(삼호선), 교대역, 양재역).getGraphPath();
        List<Station> stations = graphPath.getVertexList();
        List<String> stationNames = stations.stream()
                .map(station -> station.getName())
                .collect(Collectors.toList());
        assertThat(stationNames).isEqualTo(Arrays.asList("교대역", "양재역"));
    }
}
