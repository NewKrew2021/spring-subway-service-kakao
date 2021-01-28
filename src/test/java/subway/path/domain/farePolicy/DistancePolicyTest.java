package subway.path.domain.farePolicy;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;


public class DistancePolicyTest {

    private static BasicFare fare;

    @DisplayName("기본 지하철 요금 1250원 설정")
    @BeforeEach
    void create() {
        fare = new SubwayFare();
    }

    @DisplayName("거리가와 현재 요금이 정해졌을 때 거리에 따른 추가 요금이 더해진 금액을 확인한다.")
    @ParameterizedTest
    @CsvSource({
            "50,2050", "10,1250", "5,1250", "15,1350", "58,2150"
    })
    void getFareTest(int distance, int expected) {
        assertThat(new DistancePolicy(fare,distance).getFare()).isEqualTo(expected);
    }
}
