package subway.path.domain;

import org.junit.jupiter.api.Test;
import subway.line.domain.Section;
import subway.station.domain.Station;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class SubwayGraphTest {
    @Test
    void name() {
        Station start = new Station(6L, "start");
        Station v1 = new Station(1L, "v1");
        Station v2 = new Station(2L, "v2");
        Station v3 = new Station(3L, "v3");
        Station v4 = new Station(4L, "v4");
        Station v5 = new Station(5L, "v5");
        Station end = new Station(7L, "end");

        List<Section> sections = Arrays.asList(
                new Section(start, v1, 1),
                new Section(start, v2, 4),
                new Section(v1, v2, 3),
                new Section(v2, v3, 1),
                new Section(v1, v5, 1),
                new Section(v2, v5, 1),
                new Section(v3, v5, 10),
                new Section(v1, v4, 6),
                new Section(v3, v4, 1),
                new Section(v3, end, 4),
                new Section(v4, end, 1));

        SubwayGraph subwayGraph = new SubwayGraph(sections);
        assertThat(subwayGraph.getShortestPath(start, end)).containsExactly(start, v1, v5, v2, v3, v4, end);
        assertThat(subwayGraph.getShortestDistance(start, end)).isEqualTo(6);
    }
}
