package subway.path;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import subway.path.domain.AgeFare;

import static org.assertj.core.api.Assertions.assertThat;

public class AgeFareTest {

    @DisplayName("어린이 할인 요금 (6 ~ 12세)")
    @ParameterizedTest
    @ValueSource(ints = {6, 7, 11, 12})
    void discountFareForChild(int age) {
        AgeFare ageFare = AgeFare.getAgeFare(age);

        assertThat(ageFare.calculateDiscountFareByAge(2000)).isEqualTo(825);
    }

    @DisplayName("청소년 할인 요금 (13 ~ 18세")
    @ParameterizedTest
    @ValueSource(ints = {13, 14, 17, 18})
    void discountFareForTeenager(int age) {
        AgeFare ageFare = AgeFare.getAgeFare(age);

        assertThat(ageFare.calculateDiscountFareByAge(2000)).isEqualTo(330);
    }

    @DisplayName("성인 할인 요금 (19세 이상)")
    @ParameterizedTest
    @ValueSource(ints = {19, 20, 100, 200})
    void discountFareForAdult(int age) {
        AgeFare ageFare = AgeFare.getAgeFare(age);

        assertThat(ageFare.calculateDiscountFareByAge(2000)).isEqualTo(0);
    }
}
