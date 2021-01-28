package subway.path.domain;

import subway.path.exceptions.InvalidPathException;

import java.util.Arrays;
import java.util.function.ToIntBiFunction;

public enum DistanceFare {
    UNDER_10KM_PATH(0, 10, Integer.MAX_VALUE, (distanceFare, distance) -> 0),
    OVER_10KM_UNDER_50KM_PATH(11, 50, 5, (distanceFare, distance) -> distanceFare.calculateExtraFareByDistance(distance)),
    OVER_50KM_PATH(51, Integer.MAX_VALUE, 8, (distanceFare, distance) -> distanceFare.calculateExtraFareByDistance(distance) + 800);

    public static final int ADDITIONAL_FARE_BY_DISTANCE = 100;

    private int min;
    private int max;
    private int distanceUnit;
    private ToIntBiFunction<DistanceFare, Integer> expression;

    DistanceFare(int min, int max, int distanceUnit, ToIntBiFunction<DistanceFare, Integer> expression) {
        this.min = min;
        this.max = max;
        this.distanceUnit = distanceUnit;
        this.expression = expression;
    }

    public static int getExtraFareByDistance(int distance) {
        return Arrays.stream(DistanceFare.values())
                .filter(distanceFare -> distanceFare.isInRange(distance))
                .findAny()
                .orElseThrow(InvalidPathException::new)
                .getExtraFare(distance);
    }

    private boolean isInRange(int distance) {
        return min <= distance && distance <= max;
    }

    private int getExtraFare(int distance) {
        return expression.applyAsInt(this, distance);
    }

    private int calculateExtraFareByDistance(int distance) {
        return ((int) Math.ceil((distance - min + 1) / (double) distanceUnit)) * ADDITIONAL_FARE_BY_DISTANCE;
    }

}
