package subway.member;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import subway.member.domain.Age;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

public class AgeTest {
    @DisplayName("나이 할인 요금 테스트")
    @ParameterizedTest
    @MethodSource("ageFareParameter")
    void ageDiscount(Age age, int fare) {
        assertThat(age.discount(1350)).isEqualTo(fare);
    }

    static Stream<Arguments> ageFareParameter() {
        return Stream.of(
                Arguments.arguments(Age.BABY, 0),
                Arguments.arguments(Age.CHILD, 850),
                Arguments.arguments(Age.TEENAGER, 1150),
                Arguments.arguments(Age.ADULT, 1350)
        );
    }

    @DisplayName("회원 나이를 파악한다..")
    @ParameterizedTest
    @MethodSource("ageParameter")
    void confirmAge(int age, Age ageEnum) {
        assertThat(Age.getAge(age)).isEqualTo(ageEnum);
    }

    static Stream<Arguments> ageParameter() {
        return Stream.of(
                Arguments.arguments(5, Age.BABY),
                Arguments.arguments(6, Age.CHILD),
                Arguments.arguments(12, Age.CHILD),
                Arguments.arguments(13, Age.TEENAGER),
                Arguments.arguments(18, Age.TEENAGER),
                Arguments.arguments(19, Age.ADULT)
        );
    }
}
