package subway.path.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import subway.path.dto.Fare;

import static org.assertj.core.api.Assertions.assertThat;

public class DistanceFarePolicyTest {

    @DisplayName("10km 미만 거리에서의 요금 테스트")
    @Test
    void shortDistanceTest(){
        int fare = new DistanceFarePolicy().apply(8);
        assertThat(fare).isEqualTo(1250);
    }

    @DisplayName("10km 이상, 50km 미만 거리에서의 요금 테스트")
    @Test
    void middleDistanceTest(){
        int fare = new DistanceFarePolicy().apply(32);
        assertThat(fare).isEqualTo(1750);
    }

    @DisplayName("50km 이상 거리에서의 요금 테스트")
    @Test
    void longDistanceTest(){
        int fare = new DistanceFarePolicy().apply(128);
        assertThat(fare).isEqualTo(3050);
    }
}
