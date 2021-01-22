package subway.path.domain.fare;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class DistancePolicyTest {

    @DisplayName("거리에 따른 요금을 계산한다")
    @ParameterizedTest
    @CsvSource({"10,1250", "11,1350", "15,1350", "16,1450", "50,2050", "51,2150", "58,2150", "59,2250"})
    void getFare(int distance, int expected) {
        // given
        DistancePolicy distancePolicy = new DistancePolicy(distance);

        // when
        int result = distancePolicy.apply(distance);

        // then
        assertThat(result).isEqualTo(expected);
    }
}
