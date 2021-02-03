package subway.path.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import subway.path.dto.Fare;

import static org.assertj.core.api.Assertions.assertThat;

public class AgeDiscountFarePolicyTest {

    @DisplayName("6세 이하의 승객에 대한 요금 테스트")
    @Test
    void babyFareTest(){
        int babyFare = new AgeDiscountFarePolicy().apply(3000, 5);
        assertThat(babyFare).isEqualTo(0);
    }

    @DisplayName("6세 이상, 13세 미만의 승객에 대한 요금 테스트")
    @Test
    void youthFareTest() {
        int youngFare = new AgeDiscountFarePolicy().apply(3000, 7);
        assertThat(youngFare).isEqualTo(1675);
    }

    @DisplayName("13세 이상, 19세 미만의 승객에 대한 요금 테스트")
    @Test
    void teenFareTest() {
        int teenFare = new AgeDiscountFarePolicy().apply(3000, 15);
        assertThat(teenFare).isEqualTo(2470);
    }

    @DisplayName("성인 승객에 대한 요금 테스트")
    @Test
    void adultFareTest() {
        int adultFare = new AgeDiscountFarePolicy().apply(3000, 30);
        assertThat(adultFare).isEqualTo(3000);
    }
}