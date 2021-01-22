package subway.path.domain.fare;

public class DistancePolicy implements FarePolicy {

    private static final int BASE_FARE = 1250;
    private static final int BASE_DISTANCE = 10;
    private static final int EXTRA_DISTANCE = 50;
    private static final int EXTRA_UNIT1 = 5;
    private static final int EXTRA_UNIT2 = 8;
    private static final int EXTRA_FARE_PER_UNIT1 = 100;
    private static final int EXTRA_FARE_PER_UNIT2 = 100;

    private final int distance;

    public DistancePolicy(int distance) {
        this.distance = distance;
    }

    @Override
    public int apply(int fare) {
        if (distance <= BASE_DISTANCE) {
            return BASE_FARE;
        }
        if (distance <= EXTRA_DISTANCE) {
            return BASE_FARE + getFirstExtraFare(distance - BASE_DISTANCE);
        }
        return BASE_FARE + getFirstFullExtraFare() + getSecondExtraFare(distance - EXTRA_DISTANCE);
    }

    private int getFirstExtraFare(int extraDistance) {
        return (((extraDistance - 1) / EXTRA_UNIT1) + 1) * EXTRA_FARE_PER_UNIT1;
    }

    private int getSecondExtraFare(int extraDistance) {
        return (((extraDistance - 1) / EXTRA_UNIT2) + 1) * EXTRA_FARE_PER_UNIT2;
    }

    private int getFirstFullExtraFare() {
        return getFirstExtraFare(EXTRA_DISTANCE - BASE_DISTANCE);
    }
}
