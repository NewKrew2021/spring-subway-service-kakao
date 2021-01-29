package subway.path.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class DiscountAgeTest {

    private final int BABY_MIN_AGE = 1;
    private final int BABY_MAX_AGE = 5;
    private final int CHILD_MIN_AGE = 6;
    private final int CHILD_MAX_AGE = 12;
    private final int YOUTH_MIN_AGE = 13;
    private final int YOUTH_MAX_AGE = 19;
    private final int ADULT_MIN_AGE = 20;

    private final int BABY_FARE = 0;
    private final int CHILD_FARE = 5350;
    private final int YOUTH_FARE = 8350;

    private int totalFare;

    @BeforeEach
    void setUp() {
        totalFare = 10350;
    }

    @Test
    @DisplayName("BABY 나이일 때, 요금 할인 테스트")
    void age1() {
        assertThat(DiscountAge.getTotalFareToDiscountAge(totalFare, BABY_MIN_AGE)).isEqualTo(BABY_FARE);
        assertThat(DiscountAge.getTotalFareToDiscountAge(totalFare, BABY_MAX_AGE)).isEqualTo(BABY_FARE);
    }

    @Test
    @DisplayName("CHILD 나이일 때, 요금 할인 테스트")
    void age2() {
        assertThat(DiscountAge.getTotalFareToDiscountAge(totalFare, CHILD_MIN_AGE)).isEqualTo(CHILD_FARE);
        assertThat(DiscountAge.getTotalFareToDiscountAge(totalFare, CHILD_MAX_AGE)).isEqualTo(CHILD_FARE);
    }

    @Test
    @DisplayName("YOUTH 나이일 때, 요금 할인 테스트")
    void age3() {
        assertThat(DiscountAge.getTotalFareToDiscountAge(totalFare, YOUTH_MIN_AGE)).isEqualTo(YOUTH_FARE);
        assertThat(DiscountAge.getTotalFareToDiscountAge(totalFare, YOUTH_MAX_AGE)).isEqualTo(YOUTH_FARE);
    }

    @Test
    @DisplayName("성인 나이일 때, 요금 할인 테스트")
    void age4() {
        assertThat(DiscountAge.getTotalFareToDiscountAge(totalFare, ADULT_MIN_AGE)).isEqualTo(totalFare);
    }
}