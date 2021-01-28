package subway.path.domain.path.graph;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import subway.line.domain.Line;
import subway.path.domain.path.SubwayPath;
import subway.station.domain.Station;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.junit.jupiter.api.Assertions.assertAll;

class ShortestTimeMapTest {

    private Station 삼송역;
    private Station 지축역;
    private Station 서현역;
    private Station 의정부역;
    private Station 잠실역;

    private Line 삼호선;
    private Line 일호선;
    private Line 이호선;
    private Line 칠호선;

    private ShortestTimeMap map;


    /**
     *
     *                            지축역 - - - - - - - 3호선, 06:10 ~ 06:30 - - -
     *                           /                                            \
     *                   3호선, 06:05 ~ 06:10                                   \
     *                        /                                                 \
     *         06:03 출발 -> 삼송역                                                 서현역
     *                        \                                                   /
     *                 1호선, 06:25 ~ 06:28                               7호선, 06:35 ~ 06:40
     *                         \                                                /
     *                         의정부역 - - -  2호선, 06:30 ~ 06:35 - - - - - 잠실역
     *
     *
     *
     * 구간의 시간은 역에서 역을 이동하는데 시작 시간 ~ 도착 시간
     *
     */
    @BeforeEach
    void setUp() {
        삼송역 = new Station(1L, "삼송역");
        지축역 = new Station(2L, "지축역");
        서현역 = new Station(3L, "서현역");
        의정부역 = new Station(4L, "의정부역");
        잠실역 = new Station(5L, "잠실역");

        삼호선 = new Line("3호선", "blue", 0, LocalTime.of(6, 0), LocalTime.of(22, 0), 5);
        일호선 = new Line("1호선", "blue", 0, LocalTime.of(6, 0), LocalTime.of(22, 0), 25);
        이호선 = new Line("2호선", "blue", 0, LocalTime.of(6, 0), LocalTime.of(22, 0), 5);
        칠호선 = new Line("7호선", "blue", 0, LocalTime.of(6, 0), LocalTime.of(22, 0), 5);

        삼호선.addSection(삼송역, 지축역, 5);
        삼호선.addSection(지축역, 서현역, 20);
        일호선.addSection(삼송역, 의정부역, 3);
        이호선.addSection(의정부역, 잠실역, 6);
        칠호선.addSection(잠실역, 서현역, 5);

        map = ShortestTimeMap.initialize(Arrays.asList(삼호선, 일호선, 이호선, 칠호선));
    }

    @DisplayName("삼송역부터 서현역까지 최소 시간 경로")
    @Test
    void testPath1() {
        // when
        SubwayPath result = map.getPath(
                삼송역,
                서현역,
                LocalDateTime.of(2021, 1, 26, 6, 3)
        );

        // then
        assertAll(
                () -> assertThat(result.getArrivalTime()).isEqualTo(LocalDateTime.of(2021, 1, 26, 6, 30)),
                () -> assertThat(result.getStations())
                        .isEqualTo(Arrays.asList(삼송역, 지축역, 서현역))
        );
    }

    @DisplayName("삼송역부터 의정부역까지 최소 시간 경로")
    @Test
    void testPath2() {
        // when
        SubwayPath result = map.getPath(
                삼송역,
                의정부역,
                LocalDateTime.of(2021, 1, 26, 6, 3)
        );

        // then
        assertAll(
                () -> assertThat(result.getArrivalTime()).isEqualTo(LocalDateTime.of(2021, 1, 26, 6, 28)),
                () -> assertThat(result.getStations())
                        .isEqualTo(Arrays.asList(삼송역, 의정부역))
        );
    }

    @DisplayName("여러 경로 중 하나라도 당일 도착 가능한 경로가 있다면 구한다")
    @Test
    void testPath3() {
        // when
        SubwayPath result = map.getPath(
                삼송역,
                서현역,
                LocalDateTime.of(2021, 1, 26, 22, 0)
        );

        // then
        assertAll(
                () -> assertThat(result.getArrivalTime()).isEqualTo(LocalDateTime.of(2021, 1, 26, 22, 25)),
                () -> assertThat(result.getStations())
                        .isEqualTo(Arrays.asList(삼송역, 지축역, 서현역))
        );
    }

    @DisplayName("당일 도착하는 경로가 없는 경우 예외가 발생한다")
    @Test
    void fail() {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> map.getPath(삼송역, 잠실역, LocalDateTime.of(2021, 1, 26, 22, 0)))
                .withMessage("당일 도착하는 경로가 존재하지 않습니다");
    }
}
