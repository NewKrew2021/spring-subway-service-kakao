package subway.path.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import subway.exceptions.NotExistsDataException;
import subway.line.domain.Line;
import subway.line.domain.Lines;
import subway.line.domain.Section;
import subway.station.domain.Station;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;


class GraphTest {


    private Station 교대역;
    private Station 강남역;
    private Station 양재역;
    private Station 남부터미널역;
    private Station 잠실역;
    private Station 성수역;
    private Station 증미역;

    private Graph 지하철노선그래프;

    /**
     * 교대역  --- *2호선*(28) ---   강남역   --- *2호선*(38) --- 잠실역 --- *2호선*(5) --- 성수역
     * |                        |
     * *3호선*(5)                 *신분당선*(80)
     * |                        |
     * 남부터미널역  --- *3호선*(3) ---   양재역
     */

    @BeforeEach
    void setUp() {
        교대역 = new Station(1L, "교대역");
        강남역 = new Station(2L, "강남역");
        양재역 = new Station(3L, "양재역");
        남부터미널역 = new Station(4L, "남부터미널역");
        잠실역 = new Station(5L, "잠실역");
        성수역 = new Station(6L, "성수역");
        증미역 = new Station(7L, "증미역");

        Line 이호선 = new Line(1L, "2호선", "red", 0);
        이호선.getSections().addSection(new Section(교대역, 강남역, 1, 28));
        이호선.getSections().addSection(new Section(강남역, 잠실역, 1, 38));
        이호선.getSections().addSection(new Section(잠실역, 성수역, 1, 5));

        Line 삼호선 = new Line(2L, "3호선", "blue", 200);
        삼호선.getSections().addSection(new Section(교대역, 남부터미널역, 2, 5));
        삼호선.getSections().addSection(new Section(남부터미널역, 양재역, 2, 3));

        Line 신분당선 = new Line(3L, "신분당선", "green", 1000);
        신분당선.getSections().addSection(new Section(양재역, 강남역, 3, 80));

        Lines lines = new Lines(Arrays.asList(이호선, 삼호선, 신분당선));
        지하철노선그래프 = new Graph(Arrays.asList(교대역, 강남역, 양재역, 남부터미널역, 잠실역, 성수역, 증미역), lines.getAllSections());
    }

    @Test
    void 최단거리_검사() {
        assertThat(지하철노선그래프.getPathStations(남부터미널역, 강남역)).containsExactly(남부터미널역, 교대역, 강남역);
        assertThat(지하철노선그래프.getPathDistance(남부터미널역, 강남역)).isEqualTo(33);
    }

    @Test
    void 존재하지않는_경로_검사() {
        assertThatExceptionOfType(NotExistsDataException.class).isThrownBy(() -> 지하철노선그래프.getPathStations(증미역, 남부터미널역));
    }
}