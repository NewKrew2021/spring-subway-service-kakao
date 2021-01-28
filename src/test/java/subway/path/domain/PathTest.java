package subway.path.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import subway.station.domain.Station;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class PathTest {
    @DisplayName("최단경로가 주어졌을 때 이용금액이 올바르게 반환되는지 확인한다")
    @Test
    void testCalculateFare() {
        // given
        Station v1 = new Station(1L, "hi");
        Station v2 = new Station(2L, "hello");
        Station v3 = new Station(3L, "bye");
        Station v4 = new Station(4L, "goodbye");
        List<Station> stations = Arrays.asList(v1, v2, v3, v4);

        List<SectionEdge> edges = Arrays.asList(
                new SectionEdge(v1, v2, 24, 200),
                new SectionEdge(v2, v3, 46, 500),
                new SectionEdge(v3, v4, 13, 400)
        );

        Path path = new Path(stations, edges, 83);

        // then
        assertThat(path.calculateFare()).isEqualTo(3050);
    }
}
