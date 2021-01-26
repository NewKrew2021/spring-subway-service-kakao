package subway.path.domain.fare;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class DistancePolicyTest {

    @DisplayName("거리에 따른 추가 요금을 계산한다")
    @ParameterizedTest
    @CsvSource({"10,0", "11,100", "15,100", "16,200", "50,800", "51,900", "58,900", "59,1000"})
    void getFare(int distance, int expected) {
        // given
        DistancePolicy distancePolicy = new DistancePolicy(distance);

        // when
        int result = distancePolicy.apply(0);

        // then
        assertThat(result).isEqualTo(expected);
    }
}
