package subway.path.domain;

import subway.line.domain.Line;

import java.util.List;

public class Fare {
    private static final int DEFAULT_FARE = 1250;
    private static final int DEFAULT_DISTANCE = 10;
    private static final int EXTRA_DISTANCE = 50;
    private static final int PER_DISTANCE1 = 5;
    private static final int PER_DISTANCE2 = 8;
    private static final int EXTRA_CHARGE = 100;
    private static final int CHILD_MIN_AGE = 6;
    private static final int TEENAGER_MAX_AGE = 18;
    private static final int CHILD_MAX_AGE = 12;
    private static final int DEDUCTION = 350;
    private static final double CHILD_DISCOUNT_RATE = 0.5;
    private static final double TEENAGER_DISCOUNT_RATE = 0.2;

    public static int getFareByDistance(int distance) {
        if (distance <= DEFAULT_DISTANCE) {
            return DEFAULT_FARE;
        }
        if (distance <= EXTRA_DISTANCE) {
            return DEFAULT_FARE + (distance - DEFAULT_DISTANCE + PER_DISTANCE1 - 1) / PER_DISTANCE1 * EXTRA_CHARGE;
        }
        return DEFAULT_FARE + (EXTRA_DISTANCE - DEFAULT_DISTANCE + PER_DISTANCE1 - 1) / PER_DISTANCE1 * EXTRA_CHARGE
                + (distance - EXTRA_DISTANCE + PER_DISTANCE2 - 1) / PER_DISTANCE2 * EXTRA_CHARGE;
    }

    public static int applyDiscount(int age, int fare) {
        if (age < CHILD_MIN_AGE) {
            return fare;
        }
        if (age <= CHILD_MAX_AGE) {
            return fare - (int) ((fare - DEDUCTION) * CHILD_DISCOUNT_RATE);
        }
        if (age <= TEENAGER_MAX_AGE) {
            return fare - (int) ((fare - DEDUCTION) * TEENAGER_DISCOUNT_RATE);
        }
        return fare;
    }

    public static int getExtraFare(List<Line> lines) {
        return lines.stream()
                .map(Line::getExtraFare)
                .reduce(0, Integer::max);
    }
}
