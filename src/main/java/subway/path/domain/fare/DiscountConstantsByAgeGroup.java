package subway.path.domain.fare;

import subway.common.domain.AgeGroup;
import subway.common.domain.Fare;

import java.util.Arrays;

import static subway.common.domain.AgeGroup.*;

public enum DiscountConstantsByAgeGroup {
    INFANTS_DISCOUNT_CONSTANTS(INFANTS, Fare.from(0), 1.0),
    CHILD_DISCOUNT_CONSTANTS(CHILD, Fare.from(350), 0.5),
    TEENAGER_DISCOUNT_CONSTANTS(TEENAGER, Fare.from(350), 0.2),
    ADULT_DISCOUNT_CONSTANTS(ADULT, Fare.from(0), 0.0);

    AgeGroup ageGroup;
    Fare DEDUCTION_AMOUNT;
    double DISCOUNT_RATE;

    DiscountConstantsByAgeGroup(AgeGroup ageGroup, Fare deductionAmount, double discountRate) {
        this.ageGroup = ageGroup;
        this.DEDUCTION_AMOUNT = deductionAmount;
        this.DISCOUNT_RATE = discountRate;
    }

    public static DiscountConstantsByAgeGroup from(AgeGroup ageGroup) {
        return Arrays.stream(DiscountConstantsByAgeGroup.values())
                .filter(discountConstants -> discountConstants.ageGroup.equals(ageGroup))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("할인 정책이 정의되지 않은 연령대입니다."));
    }
}
