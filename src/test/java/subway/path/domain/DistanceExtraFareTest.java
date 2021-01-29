package subway.path.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class DistanceExtraFareTest {

    private final int KM_0 = 0;
    private final int KM_9 = 9;
    private final int KM_10 = 10;
    private final int KM_11 = 11;
    private final int KM_49 = 49;
    private final int KM_50 = 50;
    private final int KM_51 = 51;
    private final int KM_58 = 58;
    private final int KM_60 = 60;

    private final int DEFAULT_EXTRA_FARE = 1250;
    private final int TOTAL_FARE_11KM = 1350;
    private final int TOTAL_FARE_49KM = 2050;
    private final int TOTAL_FARE_50KM = 2050;
    private final int TOTAL_FARE_51KM = 2150;
    private final int TOTAL_FARE_58KM = 2150;
    private final int TOTAL_FARE_60KM = 2250;

    @Test
    @DisplayName("10km 이내 요금 테스트")
    void distance1() {
        assertThat(DistanceExtraFare.getTotalFare(KM_0)).isEqualTo(DEFAULT_EXTRA_FARE);
        assertThat(DistanceExtraFare.getTotalFare(KM_9)).isEqualTo(DEFAULT_EXTRA_FARE);
        assertThat(DistanceExtraFare.getTotalFare(KM_10)).isEqualTo(DEFAULT_EXTRA_FARE);
    }

    @Test
    @DisplayName("50km 이내 요금 테스트")
    void distance2() {
        assertThat(DistanceExtraFare.getTotalFare(KM_11)).isEqualTo(TOTAL_FARE_11KM);
        assertThat(DistanceExtraFare.getTotalFare(KM_49)).isEqualTo(TOTAL_FARE_49KM);
        assertThat(DistanceExtraFare.getTotalFare(KM_50)).isEqualTo(TOTAL_FARE_50KM);
    }

    @Test
    @DisplayName("50km 초과 요금 테스트")
    void distance3() {
        assertThat(DistanceExtraFare.getTotalFare(KM_51)).isEqualTo(TOTAL_FARE_51KM);
        assertThat(DistanceExtraFare.getTotalFare(KM_58)).isEqualTo(TOTAL_FARE_58KM);
        assertThat(DistanceExtraFare.getTotalFare(KM_60)).isEqualTo(TOTAL_FARE_60KM);
    }

}