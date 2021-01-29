package subway.line.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import subway.station.domain.Station;

import java.util.Arrays;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class SectionsTest {

    @DisplayName("구간들에서 첫 역부터 특정 역까지 걸리는 시간을 구한다")
    @ParameterizedTest
    @MethodSource("generateStationWithDuration")
    void getTotalDurationUntil(Station target, int expectedTime) {
        // given
        Sections sections = new Sections(Arrays.asList(
                new Section(new Station(2L, "지축역"), new Station(3L, "구파발역"), 5),
                new Section(new Station(3L, "구파발역"), new Station(4L, "연신내역"), 10),
                new Section(new Station(1L, "삼송역"), new Station(2L, "지축역"), 20),
                new Section(new Station(4L, "연신내역"), new Station(5L, "불광역"), 5)
        ));

        // when
        int result = sections.getTotalDurationUntil(target);

        // then
        assertThat(result).isEqualTo(expectedTime);
    }

    private static Stream<Arguments> generateStationWithDuration() {
        return Stream.of(
                Arguments.of(new Station(1L, "삼송역"), 0),
                Arguments.of(new Station(2L, "지축역"), 20),
                Arguments.of(new Station(3L, "구파발역"), 25),
                Arguments.of(new Station(4L, "연신내역"), 35),
                Arguments.of(new Station(5L, "불광역"), 40)
        );
    }
}
