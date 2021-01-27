package subway.path.domain;

import subway.member.exceptions.InvalidAgeException;

import java.util.Arrays;

public enum AgeFare {
    INFANT(0, 5, 1),
    CHILD(6, 12, 0.5),
    TEENAGER(13, 18, 0.2),
    ADULT(19, 150, 0);

    public static final int BASE_DEDUCT_FARE = 350;

    private int min;
    private int max;
    private double discountRate;

    AgeFare(int min, int max, double discountRate) {
        this.min = min;
        this.max = max;
        this.discountRate = discountRate;
    }

    public static int getDiscountedFareByAge(int fare, int age) {
        return Arrays.stream(AgeFare.values())
                .filter(ageFare -> ageFare.isInRange(age))
                .findAny()
                .orElseThrow(InvalidAgeException::new)
                .getDiscountedFare(fare);
    }

    private boolean isInRange(int age) {
        return min <= age && age <= max;
    }

    private int getDiscountedFare(int fare) {
        if (this == AgeFare.INFANT) {
            return 0;
        }

        return fare - (int) Math.floor((fare - BASE_DEDUCT_FARE) * discountRate);
    }

}
