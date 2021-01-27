package subway.path.domain.path.time;

import org.jgrapht.GraphPath;
import org.jgrapht.graph.GraphWalk;
import org.junit.jupiter.api.Test;
import subway.line.domain.Line;
import subway.path.domain.path.SubwayEdge;
import subway.path.domain.path.graph.PathAndArrival;
import subway.station.domain.Station;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class ShortestTimePathFinderTest {

    /**
     *
     *                            지축역(06:10) - - - - - - - 3호선, 06:10 ~ 06:30
     *                           /                                            \
     *                   3호선, 06:05 ~ 06:10                                   \
     *                        /                                                 \
     * 06:03 출발 -> 삼송역(06:05)                                                   서현역
     *                        \                                                   /
     *                 1호선, 06:05 ~ 06:08                               7호선, 06:35 ~ 06:40
     *                         \                                                /
     *                         의정부역(06:30) - 2호선, 06:30 ~ 06:35 - 잠실역(06:35)
     *
     *
     * 역의 괄호 안은 실제 출발 시간
     * 구간의 시간은 역에서 역을 이동하는데 시작 시간 ~ 도착 시간
     *
     */
    @Test
    void name() {
        // given
        Station 삼송역 = new Station(1L, "삼송역");
        Station 지축역 = new Station(2L, "지축역");
        Station 서현역 = new Station(3L, "서현역");
        Station 의정부역 = new Station(4L, "의정부역");
        Station 잠실역 = new Station(5L, "잠실역");

        Line 삼호선 = new Line("3호선", "blue", 0, LocalTime.of(6, 0), LocalTime.of(22, 0), 5);
        Line 일호선 = new Line("1호선", "blue", 0, LocalTime.of(6, 0), LocalTime.of(22, 0), 30);
        Line 이호선 = new Line("2호선", "blue", 0, LocalTime.of(6, 0), LocalTime.of(22, 0), 5);
        Line 칠호선 = new Line("7호선", "blue", 0, LocalTime.of(6, 0), LocalTime.of(22, 0), 5);

        삼호선.addSection(삼송역, 지축역, 5);
        삼호선.addSection(지축역, 서현역, 20);
        일호선.addSection(삼송역, 의정부역, 3);
        이호선.addSection(의정부역, 잠실역, 6);
        칠호선.addSection(잠실역, 서현역, 5);

        GraphPath<Station, SubwayEdge> shortestTimePath = new GraphWalk<>(
                null,
                삼송역,
                서현역,
                Arrays.asList(삼송역, 지축역, 서현역),
                Arrays.asList(new SubwayEdge(5, 5, 삼호선), new SubwayEdge(20, 20, 삼호선)),
                25
        );
        GraphPath<Station, SubwayEdge> shortestDistancePath = new GraphWalk<>(
                null,
                삼송역,
                서현역,
                Arrays.asList(삼송역, 의정부역, 잠실역, 서현역),
                Arrays.asList(
                        new SubwayEdge(3, 3, 일호선),
                        new SubwayEdge(5, 5, 이호선),
                        new SubwayEdge(5, 5, 칠호선)
                ),
                13
        );
        ShortestTimePathFinder shortestTimePathFinder = new ShortestTimePathFinder(Arrays.asList(shortestDistancePath, shortestTimePath));

        // when
        PathAndArrival result = shortestTimePathFinder.getPath(LocalDateTime.of(2021, 1, 26, 6, 3));

        // then
        assertAll(
                () -> assertThat(result.getArrivalTime()).isEqualTo(LocalDateTime.of(2021, 1, 26, 6, 30)),
                () -> assertThat(result.getPath().getVertexList())
                        .isEqualTo(Arrays.asList(삼송역, 지축역, 서현역))
        );
    }
}
