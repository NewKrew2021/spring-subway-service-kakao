package subway.path.util;

public class DistanceFare {

    private static final int BASE_DISTANCE = 10;
    private static final int EXTRA_DISTANCE = 50;
    private static final int EXTRA_UNIT1 = 5;
    private static final int EXTRA_UNIT2 = 8;
    private static final int EXTRA_FARE_PER_UNIT1 = 100;
    private static final int EXTRA_FARE_PER_UNIT2 = 100;

    public static int getFare(int distance) {
        if (distance <= BASE_DISTANCE) {
            return 0;
        }
        if (distance <= EXTRA_DISTANCE) {
            return getFirstExtraFare(distance);
        }
        return getFirstFullExtraFare() + getSecondExtraFare(distance);
    }

    private static int getFirstFullExtraFare() {
        return EXTRA_FARE_PER_UNIT1 * (EXTRA_DISTANCE - BASE_DISTANCE) / EXTRA_UNIT1;
    }

    private static int getFirstExtraFare(int distance) {
        return (((distance - (BASE_DISTANCE + 1)) / EXTRA_UNIT1) + 1) * EXTRA_FARE_PER_UNIT1;
    }

    private static int getSecondExtraFare(int distance) {
        return (((distance - (EXTRA_DISTANCE + 1)) / EXTRA_UNIT2) + 1) * EXTRA_FARE_PER_UNIT2;
    }
}
