package subway.path;


import org.junit.jupiter.api.DisplayName;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import subway.path.domain.farePolicy.DistancePolicy;

import static org.assertj.core.api.Assertions.assertThat;


public class DistancePolicyTest {

    @DisplayName("거리가와 현재 요금이 정해졌을 때 거리에 따른 추가 요금이 더해진 금액을 확인한다.")
    @ParameterizedTest
    @CsvSource({
            "1250,50,2050", "1250,10,1250", "1250,5,1250", "1250,15,1350", "1250,58,2150"
    })
    void getFareTest(int initFare, int distance, int expected) {
        assertThat(new DistancePolicy(initFare,distance).getFare()).isEqualTo(expected);
    }
}
