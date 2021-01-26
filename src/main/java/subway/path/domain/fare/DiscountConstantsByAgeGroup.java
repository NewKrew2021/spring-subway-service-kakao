package subway.path.domain.fare;

import subway.member.domain.AgeGroup;

import java.util.Arrays;

import static subway.member.domain.AgeGroup.*;

public enum DiscountConstantsByAgeGroup {
    INFANTS_DISCOUNT_CONSTANTS(INFANTS, 0, 1.0),
    CHILD_DISCOUNT_CONSTANTS(CHILD, 350, 0.5),
    TEENAGER_DISCOUNT_CONSTANTS(TEENAGER, 350, 0.2),
    ADULT_DISCOUNT_CONSTANTS(ADULT, 0, 0.0)
    ;

    AgeGroup ageGroup;
    int DEDUCTION_AMOUNT;
    double DISCOUNT_RATE;

    DiscountConstantsByAgeGroup(AgeGroup ageGroup, int deductionAmount, double discountRate) {
        this.ageGroup = ageGroup;
        this.DEDUCTION_AMOUNT = deductionAmount;
        this.DISCOUNT_RATE = discountRate;
    }

    public static DiscountConstantsByAgeGroup of(AgeGroup ageGroup) {
        return Arrays.stream(DiscountConstantsByAgeGroup.values())
                .filter(discountConstants -> discountConstants.ageGroup.equals(ageGroup))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("할인 정책이 정의되지 않은 연령대입니다."));
    }
}
