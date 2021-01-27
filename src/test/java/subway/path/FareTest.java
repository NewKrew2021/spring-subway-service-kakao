package subway.path;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import subway.path.domain.Fare;

import static org.assertj.core.api.Assertions.assertThat;

public class FareTest {

    private Fare fare;

    @BeforeEach
    void setUp() {
        fare = new Fare();
    }

    @DisplayName("거리별 운임 적용")
    @Test
    void applyDistancePolicy() {
        int distance = 45;

        fare.applyDistanceFarePolicy(distance);

        assertThat(fare.getFare()).isEqualTo(1950);
    }

    @DisplayName("청소년 운임 적용")
    @Test
    void applyAgePolicy_Teen() {
        fare.applyAgeFarePolicy(16);

        assertThat(fare.getFare()).isEqualTo(1070);
    }

    @DisplayName("어린이 운임 적용")
    @Test
    void applyAgePolicy_Child() {
        fare.applyAgeFarePolicy(8);

        assertThat(fare.getFare()).isEqualTo(800);
    }

    @DisplayName("무임 적용")
    @Test
    void applyAgePolicy_Free() {
        fare.applyAgeFarePolicy(4);

        assertThat(fare.getFare()).isEqualTo(0);
    }
}
