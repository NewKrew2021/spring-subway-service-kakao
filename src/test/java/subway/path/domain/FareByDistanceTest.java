package subway.path.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import subway.path.domain.FareByDistance;

import static org.assertj.core.api.Assertions.assertThat;

public class FareByDistanceTest {

    @DisplayName("10km 미만 거리에서의 요금 테스트")
    @Test
    void shortDistanceTest(){
        Fare fare = new FareByDistance().calculateFare(8);
        assertThat(fare.getFare()).isEqualTo(1250);
    }

    @DisplayName("10km 이상, 50km 미만 거리에서의 요금 테스트")
    @Test
    void middleDistanceTest(){
        Fare fare = new FareByDistance().calculateFare(32);
        assertThat(fare.getFare()).isEqualTo(1750);
    }

    @DisplayName("50km 이상 거리에서의 요금 테스트")
    @Test
    void longDistanceTest(){
        Fare fare = new FareByDistance().calculateFare(128);
        assertThat(fare.getFare()).isEqualTo(3050);
    }
}
