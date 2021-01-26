package subway.path.domain;

import org.junit.jupiter.api.Test;
import subway.station.domain.Station;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

public class SubwayGraphTest {
    @Test
    void graph_normal() {
        Station start = new Station(6L, "start");
        Station v1 = new Station(1L, "v1");
        Station v2 = new Station(2L, "v2");
        Station v3 = new Station(3L, "v3");
        Station v4 = new Station(4L, "v4");
        Station v5 = new Station(5L, "v5");
        Station end = new Station(7L, "end");

        /*
         *         v1 - v4
         *       / | \   | \
         * start   |  v5 |  end
         *       \ | / \ | /
         *         v2 - v3
         */

        List<SectionEdge> sectionEdges = Arrays.asList(
                new SectionEdge(start, v1, 1, 0),
                new SectionEdge(start, v2, 4, 0),
                new SectionEdge(v1, v2, 3, 0),
                new SectionEdge(v2, v3, 1, 0),
                new SectionEdge(v1, v5, 1, 0),
                new SectionEdge(v2, v5, 1, 0),
                new SectionEdge(v3, v5, 10, 0),
                new SectionEdge(v1, v4, 6, 0),
                new SectionEdge(v3, v4, 1, 0),
                new SectionEdge(v3, end, 4, 0),
                new SectionEdge(v4, end, 1, 0));

        SubwayGraph subwayGraph = new SubwayGraph(sectionEdges);
        Path path = subwayGraph.getShortestPath(start, end);

        assertThat(path.getPath()).containsExactly(start, v1, v5, v2, v3, v4, end);
        assertThat(path.getDistance()).isEqualTo(6);
    }

    @Test
    void graph_same_distance() {
        Station start = new Station(1L, "start");
        Station middle = new Station(2L, "middle");
        Station end = new Station(3L, "end");
        List<SectionEdge> sectionEdges = Arrays.asList(
                new SectionEdge(start, middle, 2, 5),
                new SectionEdge(start, middle, 2, 3),
                new SectionEdge(middle, end, 2, 3),
                new SectionEdge(middle, end, 2, 5)
        );
        SubwayGraph subwayGraph = new SubwayGraph(sectionEdges);
        Path path = subwayGraph.getShortestPath(start, end);

        assertThat(path.getPath()).containsExactly(start, middle, end);
        assertThat(path.getSectionEdges()
                .stream()
                .map(SectionEdge::getFare)
                .collect(Collectors.toList()))
                .containsExactly(3, 3);
    }
}
