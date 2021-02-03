package subway.path.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class FareByAgeTest {

    @DisplayName("6세 이하의 승객에 대한 요금 테스트")
    @Test
    void babyFareTest(){
        Fare baby = new FareByAge().calculateFare(3000, 5);
        assertThat(baby.getFare()).isEqualTo(0);
    }

    @DisplayName("6세 이상, 13세 미만의 승객에 대한 요금 테스트")
    @Test
    void youthFareTest() {
        Fare young = new FareByAge().calculateFare(3000, 7);
        assertThat(young.getFare()).isEqualTo(1675);
    }

    @DisplayName("13세 이상, 19세 미만의 승객에 대한 요금 테스트")
    @Test
    void teenFareTest() {
        Fare teen = new FareByAge().calculateFare(3000, 15);
        assertThat(teen.getFare()).isEqualTo(2470);
    }

    @DisplayName("성인 승객에 대한 요금 테스트")
    @Test
    void adultFareTest() {
        Fare adult = new FareByAge().calculateFare(3000, 30);
        assertThat(adult.getFare()).isEqualTo(3000);
    }
}