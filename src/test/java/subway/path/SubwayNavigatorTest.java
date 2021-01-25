package subway.path;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import subway.line.domain.Line;
import subway.line.domain.Section;
import subway.path.domain.DirectedSection;
import subway.path.domain.DirectedSections;
import subway.path.domain.SubwayNavigator;
import subway.path.domain.SubwayPrice;
import subway.station.domain.Station;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.as;
import static org.assertj.core.api.Assertions.assertThat;

public class SubwayNavigatorTest {
    SubwayNavigator 이호선_삼호선_네비게이션;
    Line 이호선;
    Line 삼호선;
    Station 서초역 = new Station(1L, "서초역");
    Station 교대역 = new Station(2L, "교대역");
    Station 강남역 = new Station(3L, "강남역");
    Station 역삼역 = new Station(4L, "역삼역");
    Station 고속터미널역 = new Station(5L, "고속터미널역");
    Section 서초_교대_구간 = new Section(1L, 서초역, 교대역, 5);
    Section 교대_강남_구간 = new Section(2L, 교대역, 강남역, 7);
    Section 강남_역삼_구간 = new Section(3L, 강남역, 역삼역, 10);
    Section 고속터미널역_교대_구간 = new Section(4L, 고속터미널역, 교대역, 3);

    @BeforeEach
    public void setUp() {
        이호선 = new Line(1L, "2호선", "그린", 300);
        삼호선 = new Line(2L, "3호선", "오렌지", 500);
        이호선.addSection(서초_교대_구간);
        이호선.addSection(교대_강남_구간);
        이호선.addSection(강남_역삼_구간);
        삼호선.addSection(고속터미널역_교대_구간);

        List<Line> lines = new ArrayList<>();
        lines.add(이호선);
        lines.add(삼호선);
        이호선_삼호선_네비게이션 = new SubwayNavigator(lines);
    }

    @DisplayName("적절한 경로를 구하는지 체크")
    @Test
    public void checkStationSequence() {
        DirectedSections 최단경로 = 이호선_삼호선_네비게이션.getShortestPath(역삼역, 고속터미널역);

        assertThat(최단경로.getStations().get(0)).isEqualTo(역삼역);
        assertThat(최단경로.getStations().get(1)).isEqualTo(강남역);
        assertThat(최단경로.getStations().get(2)).isEqualTo(교대역);
        assertThat(최단경로.getStations().get(3)).isEqualTo(고속터미널역);
    }

    @DisplayName("적절한 거리를 구하는지 체크")
    @Test
    public void checkDistance() {
        int 총거리 = 강남_역삼_구간.getDistance() + 교대_강남_구간.getDistance() + 고속터미널역_교대_구간.getDistance();

        DirectedSections 최단경로 = 이호선_삼호선_네비게이션.getShortestPath(역삼역, 고속터미널역);

        assertThat(최단경로.getDistance()).isEqualTo(총거리);
    }

    @DisplayName("적절한 가격을 구하는지 체크")
    @Test
    public void checkPrice() {
        int 총거리 = 강남_역삼_구간.getDistance() + 교대_강남_구간.getDistance() + 고속터미널역_교대_구간.getDistance();
        int 추가요금 = Integer.max(이호선.getExtraFare(), 삼호선.getExtraFare());
        int 최종요금 = SubwayPrice.getPrice(총거리, 추가요금);

        DirectedSections 최단경로 = 이호선_삼호선_네비게이션.getShortestPath(역삼역, 고속터미널역);

        assertThat(최단경로.getPrice()).isEqualTo(최종요금);
    }

}
