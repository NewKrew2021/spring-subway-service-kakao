package subway.line.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import subway.station.domain.Station;

import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class LineTest {

    @DisplayName("노선과 역이 주어지면 해당 역에서 열차의 출발 시간을 모두 구한다")
    @Test
    void departureTimesOfStation() {
        // given
        Line line = new Line("3호선", "blue", 0, LocalTime.of(6, 0), LocalTime.of(7, 0), 15);
        line.addSection(new Station(1L, "삼송역"), new Station(2L, "지축역"), 20);
        line.addSection(new Station(2L, "지축역"), new Station(3L, "구파발역"), 5);
        line.addSection(new Station(3L, "구파발역"), new Station(4L, "연신내역"), 10);
        line.addSection(new Station(4L, "연신내역"), new Station(5L, "불광역"), 5);

        // when
        List<LocalTime> result = line.getDepartureTimesOf(new Station(3L, "구파발역"));

        // then
        assertThat(result).isEqualTo(
                Arrays.asList(
                        LocalTime.of(6, 25),
                        LocalTime.of(6, 40),
                        LocalTime.of(6, 55),
                        LocalTime.of(7, 10),
                        LocalTime.of(7, 25)
                )
        );
    }
}
