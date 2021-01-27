package subway.member.domain;

import java.util.Arrays;

public enum AgeGroup {
    INFANTS(Range.of(0, 6), 1.0, 0),
    CHILD(Range.of(6, 13), 0.5, 350),
    TEENAGER(Range.of(13, 19), 0.2, 350),
    ADULT(Range.of(19, 200), 0.0, 0);

    private final Range ageRange;
    private final double discountRate;
    private final int deduction;

    AgeGroup(Range range, double discountRate, int deduction) {
        this.ageRange = range;
        this.discountRate = discountRate;
        this.deduction = deduction;
    }

    public static AgeGroup of(Age age) {
        return Arrays.stream(AgeGroup.values())
                .filter(ageGroup -> ageGroup.ageRange.isBelong(age.getAge()))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("정의되지 않은 연령입니다."));
    }

    public double getDiscountRate() {
        return discountRate;
    }

    public int getDeduction() {
        return deduction;
    }
}