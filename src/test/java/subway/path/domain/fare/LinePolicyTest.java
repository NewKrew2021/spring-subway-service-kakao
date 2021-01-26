package subway.path.domain.fare;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import subway.line.domain.Line;

import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class LinePolicyTest {

    @DisplayName("노선에 따른 요금을 계산한다")
    @ParameterizedTest
    @MethodSource("generateLines")
    void getFare(List<Line> lines, int expected) {
        // given
        LinePolicy linePolicy = new LinePolicy(lines);

        // when
        int result = linePolicy.apply(0);

        // then
        assertThat(result).isEqualTo(expected);

    }

    private static Stream<Arguments> generateLines() {
        return Stream.of(
                Arguments.of(
                        Arrays.asList(createLineWithFare(900), createLineWithFare(1200), createLineWithFare(0)),
                        1200
                ),
                Arguments.of(
                        Arrays.asList(createLineWithFare(900), createLineWithFare(900), createLineWithFare(0)),
                        900
                )
        );
    }

    private static Line createLineWithFare(int fare) {
        return new Line(UUID.randomUUID().toString(), "blue", fare, LocalTime.now(), LocalTime.now(), 10);
    }
}
