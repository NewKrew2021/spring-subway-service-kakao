package subway.fare.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;
import static subway.fare.domain.AgeFarePolicy.applyAgeDiscount;

class AgeFarePolicyTest {

    @ParameterizedTest
    @DisplayName("나이에 따른 요금할인을 확인한다.")
    @CsvSource({"1000, 5, 0",
            "2350, 12, 1000",
            "1350, 18, 800",
            "2000, 40, 2000"})
    void applyAgeDiscountTest(int fare, int age, int expected) {
        assertThat(applyAgeDiscount(fare, age)).isEqualTo(expected);
    }
}