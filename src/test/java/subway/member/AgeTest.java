package subway.member;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import subway.member.domain.Age;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

public class AgeTest {

    @DisplayName("회원 나이를 파악한다..")
    @ParameterizedTest
    @MethodSource("ageParameter")
    void 나이확인(int age, Age ageEnum) {
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
