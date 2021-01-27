package subway.path.domain.path;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import subway.line.domain.Line;
import subway.station.domain.Station;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("최단경로의 노선별 추가금액을 테스트")
class PathTest {

    Station 강남역 = new Station(1L,"강남역");
    Station 판교역 = new Station(2L,"판교역");
    Station 정자역 = new Station(3L,"정자역");
    Station 역삼역 = new Station(4L,"역삼역");
    Station 잠실역 = new Station(5L,"잠실역");

    Line 신분당선 = new Line(1L,"신분당선", "red lighten-1", 1000);
    Line 이호선 = new Line(2L,"2호선", "green lighten-1", 200);

    List<Line> lines;

    @BeforeEach
    void setup() {

        신분당선.addSection(강남역, 판교역, 10);
        신분당선.addSection(판교역, 정자역, 10);

        이호선.addSection(강남역, 역삼역, 10);
        이호선.addSection(역삼역, 잠실역, 10);

        lines = Arrays.asList(신분당선, 이호선);
    }

    @DisplayName("최단경로가 하나의 노선을 이용할 때")
    @Test
    void When_ShortestPathUsesOneLine_getExtraFare() {
        Path path = new Path(강남역, 정자역, lines);
        assertThat(path.getExtraFare()).isEqualTo(신분당선.getExtraFare());

        path = new Path(역삼역, 잠실역, lines);
        assertThat(path.getExtraFare()).isEqualTo(이호선.getExtraFare());
    }

    @DisplayName("최단경로가 둘 이상의 노선을 이용할 때")
    @Test
    void When_ShortestPathUsesManyLines_getExtraFare() {
        Path path = new Path(판교역, 역삼역, lines);
        assertThat(path.getExtraFare()).isEqualTo(신분당선.getExtraFare());
    }
}



