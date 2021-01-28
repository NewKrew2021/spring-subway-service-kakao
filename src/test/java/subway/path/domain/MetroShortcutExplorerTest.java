package subway.path.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import subway.line.domain.Line;
import subway.line.domain.Section;
import subway.line.domain.Sections;
import subway.station.domain.Station;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("지하철 경로 유닛 테스트")
public class MetroShortcutExplorerTest {
    MetroNavigator metroNavigator;
    Station station1;
    Station station2;
    Station station3;
    Station station4;
    Station station5;
    Section section1;
    Section section2;
    Section section3;
    Section section4;
    Line line;
    Line line2;
    @BeforeEach
    void setUp() {
        station1 = new Station(1L, "잠실");
        station2 = new Station(2L, "강남");
        station3 = new Station(3L, "정자");
        station4 = new Station(4L, "판교");
        station5 = new Station(5L, "야탑");
        section1 = new Section(station1, station2);
        section2 = new Section(station2, station3);
        section3 = new Section(station3, station4);
        section4 = new Section(station3, station5);
        Sections sections1 = new Sections(Arrays.asList(section1, section2, section3));
        Sections sections2 = new Sections(Arrays.asList(section4));
        line = new Line(1L, "red", "신분당선", sections1, 300);
        line2 = new Line(2L, "blue", "분당선", sections2, 400);
        metroNavigator = new MetroNavigator(Arrays.asList(line, line2), station1, station5);
    }

    @Test
    void 최단거리_리스트_테스트() {
        assertThat(metroNavigator.getShortestPath()).isEqualTo(Arrays.asList(station1, station2, station3, station5));
    }

    @Test
    void 최단거리_구간_테스트() {
        assertThat(metroNavigator.getShortestPathSections()).isEqualTo(Arrays.asList(section1, section2, section4));
    }

}
