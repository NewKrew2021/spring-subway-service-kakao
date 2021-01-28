package subway.path.strategy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import subway.path.domain.strategy.DistanceFare;
import subway.path.domain.strategy.FareStrategy;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

public class DistanceFareTest {
    @DisplayName("거리 추가 요금 테스트")
    @ParameterizedTest
    @MethodSource("distanceFareParameter")
    void distanceExtraFare(int distance, int fare) {
        FareStrategy fareStrategy = new DistanceFare(distance);
        assertThat(fareStrategy.apply(0)).isEqualTo(fare);
    }

    static Stream<Arguments> distanceFareParameter() {
        return Stream.of(
                Arguments.arguments(10, 1250),
                Arguments.arguments(11, 1350),
                Arguments.arguments(15, 1350),
                Arguments.arguments(50, 2050),
                Arguments.arguments(58, 2150)
        );
    }
}
