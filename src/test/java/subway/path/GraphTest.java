package subway.path;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import subway.line.domain.Line;
import subway.line.domain.Lines;
import subway.line.domain.Section;
import subway.path.domain.Graph;
import subway.station.domain.Station;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

public class GraphTest {

    private Graph graph;

    private Station 강남역;
    private Station 교대역;
    private Station 양재역;
    private Station 남부터미널역;
    private Station 광교역;

    private Line 삼호선;
    private Line 이호선;
    private Line 신분당선;

    /**
     *          10               5
     * 강남역 - *이호선* - 교대역 - *삼호선* - 남부터미널역
     * /                                     /
     * /                                     /
     * *신분당선*  ----   양재역      ---- *삼호선*
     * 20                 \               3
     *               *신분당선* 10
     *                    \
     *                  광교역
     */

    @BeforeEach
    void setGraph() {
        강남역 = new Station(1L,"강남역");
        교대역 = new Station(2L,"교대역");
        양재역 = new Station(3L,"양재역");
        남부터미널역 = new Station(4L,"남부터미널역");
        광교역 = new Station(5L,"광교역");

        삼호선 = new Line(1L, "삼호선", "bg-red-200", 500);
        삼호선.getSections().addSection(new Section(교대역, 남부터미널역, 1, 5));
        삼호선.getSections().addSection(new Section(남부터미널역, 양재역, 1, 3));

        이호선 = new Line(2L, "이호선", "bg-red-300", 300);
        이호선.getSections().addSection(new Section(강남역, 교대역, 2, 10));

        신분당선 = new Line(3L, "신분당선", "bg-red-400", 600);
        신분당선.getSections().addSection(new Section(강남역, 양재역, 3, 20));
        신분당선.getSections().addSection(new Section(양재역, 광교역, 3, 10));

        Lines lines = new Lines(Arrays.asList(삼호선, 이호선, 신분당선));
        graph = new Graph(Arrays.asList(강남역, 교대역, 양재역, 남부터미널역, 광교역), lines.getAllSections());
    }

    @Test
    void shortestPathTest() {
        assertThat(graph.getPathStations(교대역, 양재역)).containsExactly(교대역, 남부터미널역, 양재역);
        assertThat(graph.getPathDistance(교대역, 양재역)).isEqualTo(8);

        assertThat(graph.getPathStations(강남역, 광교역)).containsExactly(강남역, 교대역, 남부터미널역, 양재역, 광교역);
        assertThat(graph.getPathDistance(강남역, 광교역)).isEqualTo(28);
    }

}
