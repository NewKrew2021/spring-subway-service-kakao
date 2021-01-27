package subway.fare.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;
import static subway.fare.domain.DistanceFarePolicy.findDistanceFare;

class DistanceFarePolicyTest {

    @ParameterizedTest
    @DisplayName("거리에 따라 추가되는 요금을 확인한다")
    @CsvSource({"0, 0",
            "10, 0",
            "11, 100",
            "15, 100",
            "16, 200",
            "50, 800",
            "58, 900",
            "59, 1000"})
    void findDistanceFareTest(int distance, int expected) {
        assertThat(findDistanceFare(distance)).isEqualTo(expected);
    }

}