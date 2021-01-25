package subway.path.util;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import subway.line.domain.Line;
import subway.path.util.LineFare;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class LineFareTest {

    @ParameterizedTest
    @MethodSource("generateLines")
    void getFare(List<Line> lines, int expected) {

        //when
        int result = LineFare.getFare(lines);

        //then
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
        return new Line(UUID.randomUUID().toString(), "blue", fare);
    }

}