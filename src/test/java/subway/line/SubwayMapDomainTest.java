package subway.line;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import subway.AcceptanceTest;
import subway.line.domain.*;
import subway.path.domain.ResultPath;
import subway.station.domain.Station;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@DisplayName("지하철 경로 조회")
public class SubwayMapDomainTest extends AcceptanceTest {
    @Autowired
    SubwayMap map;

    Station 교대역;
    Station 강남역;
    Station 잠실역;
    Station 양재역;
    Station 남부터미널역;

    /**
     * 교대역  --- *2호선*(28) ---   강남역   --- *2호선*(38) ---   잠실역
     * |                        |
     * *3호선*(5)                 *신분당선*(20)
     * |                        |
     * 남부터미널역  --- *3호선*(3) ---   양재역
     */
    @BeforeEach
    public void setUp() {
        super.setUp();
        교대역 = new Station(1L, "교대역");
        강남역 = new Station(2L, "강남역");
        잠실역 = new Station(3L, "잠실역");
        양재역 = new Station(4L, "양재역");
        남부터미널역 = new Station(5L, "남부터미널역");

        Section 강남_양재_구간 = new Section(1L, 강남역, 양재역, 20);
        Sections 신분당선_구간_리스트 = new Sections();
        신분당선_구간_리스트.addSection(강남_양재_구간);
        Line 신분당선 = new Line(1L, "신분당선", "bg-red-600", 900, 신분당선_구간_리스트);

        Section 교대_강남_구간 = new Section(2L, 교대역, 강남역, 28);
        Section 강남_잠실_구간 = new Section(3L, 강남역, 잠실역, 38);
        Sections 이호선_구간_리스트 = new Sections();
        이호선_구간_리스트.addSection(교대_강남_구간);
        이호선_구간_리스트.addSection(강남_잠실_구간);
        Line 이호선 = new Line(2L, "이호선", "bg-red-600", 500, 이호선_구간_리스트);

        Section 교대_남부터미널_구간 = new Section(4L, 교대역, 남부터미널역, 5);
        Section 남부터미널_양재_구간 = new Section(5L, 남부터미널역, 양재역, 3);
        Sections 삼호선_구간_리스트 = new Sections();
        삼호선_구간_리스트.addSection(교대_남부터미널_구간);
        삼호선_구간_리스트.addSection(남부터미널_양재_구간);
        Line 삼호선 = new Line(3L, "3호선", "bg-red-600", 0, 삼호선_구간_리스트);

        List<Line> lines = new ArrayList<>();
        lines.add(신분당선);
        lines.add(이호선);
        lines.add(삼호선);

        map.refresh(lines);
    }

    @DisplayName("교대 강남 최단거리 테스트")
    @Test
    void calculateShortestPath_교대_강남_Test() {
        ResultPath path = map.calculateShortestPath(교대역, 강남역);
        assertThat(path.toString())
                .contains("ExtraFare{value=500}")
                .contains("distance=28")
                .contains("Station{id=1, name='교대역'}")
                .contains("Station{id=2, name='강남역'}");
    }

    @DisplayName("교대 양재 최단거리 테스트")
    @Test
    void calculateShortestPath_교대_양재_Test() {
        ResultPath path = map.calculateShortestPath(교대역, 양재역);
        assertThat(path.toString())
                .contains("ExtraFare{value=0}")
                .contains("distance=8")
                .contains("Station{id=1, name='교대역'}")
                .contains("Station{id=5, name='남부터미널역'}")
                .contains("Station{id=4, name='양재역'}");
    }

    @DisplayName("잠실 남부터미널 최단거리 테스트")
    @Test
    void calculateShortestPath_잠실_남부터미널_Test() {
        ResultPath path = map.calculateShortestPath(잠실역, 남부터미널역);
        assertThat(path.toString())
                .contains("ExtraFare{value=900}")
                .contains("distance=61")
                .contains("Station{id=3, name='잠실역'}")
                .contains("Station{id=2, name='강남역'}")
                .contains("Station{id=4, name='양재역'}")
                .contains("Station{id=5, name='남부터미널역'}");
    }
}