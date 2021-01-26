package subway.path;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import subway.line.domain.Line;
import subway.line.domain.Section;
import subway.line.domain.Sections;
import subway.path.domain.Path;
import subway.path.domain.ShortestPathExplorer;
import subway.path.domain.SubwayGraph;
import subway.station.domain.Station;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class ShortestPathExplorerTest {

    private Station 강남역;
    private Station 양재역;
    private Station 도곡역;
    private Station 선릉역;
    private Section section1;
    private Section section2;
    private Section section3;
    private Section section4;
    private Line 신분당선;
    private Line 이호선;
    private Line 삼호선;
    private Line 분당선;
    private List<Line> lines;

    private SubwayGraph subwayGraph;

    @BeforeEach
    void setUp() {
        강남역 = new Station(1L, "강남역");
        양재역 = new Station(2L, "양재역");
        도곡역 = new Station(3L, "도곡역");
        선릉역 = new Station(4L, "선릉역");

        section1 = new Section(1L, 강남역, 양재역, 10);
        section2 = new Section(2L, 도곡역, 양재역, 13);
        section3 = new Section(3L, 강남역, 선릉역, 15);
        section4 = new Section(4L, 선릉역, 도곡역, 9);

        신분당선 = new Line(1L, "신분당선", "bg-red-600", 500, new Sections(Arrays.asList(section1)));
        이호선 = new Line(2L, "2호선", "bg-green-600", 200, new Sections(Arrays.asList(section3)));
        삼호선 = new Line(3L, "3호선", "bg-orange-600", 200, new Sections(Arrays.asList(section2)));
        분당선 = new Line(4L, "분당선", "bg-yellow-600", 300, new Sections(Arrays.asList(section4)));
        lines = new ArrayList<>(Arrays.asList(신분당선, 이호선, 삼호선, 분당선));

        subwayGraph = new SubwayGraph(lines);
    }

    @DisplayName("최단 경로의 역, 거리, 노선 추가 요금")
    @Test
    void findShortestPath() {
        ShortestPathExplorer shortestPathExplorer = new ShortestPathExplorer(subwayGraph);

        Path path = shortestPathExplorer.exploreShortestPath(lines, 양재역, 선릉역);

        assertThat(path.getStations()).containsExactly(양재역, 도곡역, 선릉역);
        assertThat(path.getDistance()).isEqualTo(22);
        assertThat(path.getFare()).isEqualTo(300);
    }
}
