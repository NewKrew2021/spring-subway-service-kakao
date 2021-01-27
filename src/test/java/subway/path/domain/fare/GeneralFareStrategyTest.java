package subway.path.domain.fare;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("거리별 할인 테스트")
class GeneralFareStrategyTest {

    @DisplayName("10km 까지는 기본요금을 적용한다.")
    @Test
    void When_DistanceUnder10_getFare() {
        //given
        FareStrategy fareStrategy = new GeneralFareStrategy(10,0);
        int expect = GeneralFareStrategy.BASIC_FARE;

        assertThat(fareStrategy.getFare()).isEqualTo(expect);
    }

    @DisplayName("10~50km 까지는 10km당 추가요금을 적용한다.")
    @Test
    void When_DistanceUnder50_getFare() {
        //given
        FareStrategy fareStrategy = new GeneralFareStrategy(49,0);
        int expect = 2050;

        assertThat(fareStrategy.getFare()).isEqualTo(expect);
    }

    @DisplayName("10km 까지는 기본요금을 적용한다.")
    @Test
    void When_DistanceOver50_getFare() {
        //given
        FareStrategy fareStrategy = new GeneralFareStrategy(57,0);
        int expect = 2150;

        assertThat(fareStrategy.getFare()).isEqualTo(expect);
    }
}