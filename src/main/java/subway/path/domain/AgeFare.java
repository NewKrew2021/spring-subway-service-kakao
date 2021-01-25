package subway.path.domain;

import java.util.stream.Stream;

public enum AgeFare {

    CHILD(13, 0.5, 350),
    TEEN(19, 0.2, 350),
    ADULT(10000, 0, 0);

    private final int upperAgeRange;
    private final double discountRate;
    private final int deduction;

    AgeFare(int upperAgeRange, double discountRate, int deduction) {
        this.upperAgeRange = upperAgeRange;
        this.discountRate = discountRate;
        this.deduction = deduction;
    }

    public static AgeFare getAgeFare(int age) {
        return Stream.of(AgeFare.values())
                .filter(ageFare -> age < ageFare.upperAgeRange)
                .findFirst()
                .orElse(ADULT);
    }

    public int calculateDiscountFareByAge(int originalFare) {
        return (int) ((originalFare - deduction) * discountRate);
    }
}
