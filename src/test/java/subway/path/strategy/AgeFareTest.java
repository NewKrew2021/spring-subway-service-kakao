package subway.path.strategy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import subway.member.domain.Age;
import subway.path.domain.strategy.AgeFare;
import subway.path.domain.strategy.FareStrategy;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

public class AgeFareTest {
    @DisplayName("나이 할인 요금 테스트")
    @ParameterizedTest
    @MethodSource("ageFareParameter")
    void ageFare(Age age, int fare) {
        FareStrategy fareStrategy = new AgeFare(age);
        assertThat(fareStrategy.apply(1350)).isEqualTo(fare);
    }

    static Stream<Arguments> ageFareParameter() {
        return Stream.of(
                Arguments.arguments(Age.BABY, 0),
                Arguments.arguments(Age.CHILD, 850),
                Arguments.arguments(Age.TEENAGER, 1150),
                Arguments.arguments(Age.ADULT, 1350)
        );
    }
}
