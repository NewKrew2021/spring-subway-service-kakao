package subway.path.domain.fare;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class AgePolicyTest {

    @DisplayName("나이에 따른 요금을 계산한다")
    @ParameterizedTest
    @CsvSource({"5,0", "6,850", "12,850", "13,1150", "18,1150", "19,1350"})
    void getFare(int age, int expected) {
        // given
        AgePolicy agePolicy = new AgePolicy(age);
        int fare = 1350;

        // when
        int result = agePolicy.apply(fare);

        // then
        assertThat(result).isEqualTo(expected);
    }
}
