package subway.path.domain;

import org.junit.jupiter.api.Test;
import subway.path.domain.FareByDistance;

import static org.assertj.core.api.Assertions.assertThat;

public class FareByDistanceTest {
    @Test
    void distanceTest(){
        FareByDistance fare = new FareByDistance(30);
        assertThat(fare.getFare()).isEqualTo(1650);
    }
}
