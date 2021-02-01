package subway.path.domain.fare;

import java.util.stream.Stream;

public enum AgeFare {

    BABY(6, 1.0, 0),
    CHILD(13, 0.5, 350),
    TEEN(19, 0.2, 350),
    ADULT(10000, 0, 0);

    private int upperAgeRange;
    private double discountRate;
    private int deduction;

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
