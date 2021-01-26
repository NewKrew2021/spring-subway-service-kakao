package subway.util;

import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import subway.line.domain.Line;
import subway.line.domain.Section;
import subway.line.domain.Sections;
import subway.station.domain.Station;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class PathTest {
    Line line;
    Line line2;
    DijkstraShortestPath dijkstraShortestPath;
    List<Station> stations;
    Section section1;
    Section section2;
    Section section3;
    Section section4;
    Station station1;
    Station station2;
    Station station3;
    Station station4;
    Station station5;
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
        stations = Arrays.asList(station1, station2, station3, station4, station5);
        line = new Line(1L, "red", "신분당선", sections1, 300);
        line2 = new Line(2L, "blue", "분당선", sections2, 400);
        dijkstraShortestPath = ShortestPathUtil.getDijkstraShortestPath(Arrays.asList(line, line2));
    }

    @Test
    void 최단거리_리스트_테스트() {
        assertThat(ShortestPathUtil.getShortestPath(dijkstraShortestPath, station1,station5)).isEqualTo(Arrays.asList(station1,station2,station3,station5));
    }

    @Test
    void 최단거리_구간_테스트() {
        List<Station> shotTestPath = ShortestPathUtil.getShortestPath(dijkstraShortestPath, station1, station5);
        assertThat(ShortestPathUtil.getShortestPathSections(shotTestPath)).isEqualTo(Arrays.asList(section1, section2, section4));
    }
}
