package subway.path.domain.fare;

public class DistancePolicy implements FarePolicy {

    private static final int BASE_DISTANCE = 10;
    private static final int EXTRA_DISTANCE = 50;
    private static final int MAX_FIRST_EXTRA_DISTANCE = EXTRA_DISTANCE - BASE_DISTANCE;
    private static final int EXTRA_UNIT1 = 5;
    private static final int EXTRA_UNIT2 = 8;
    private static final int EXTRA_FARE_PER_UNIT1 = 100;
    private static final int EXTRA_FARE_PER_UNIT2 = 100;
    private static final int ZERO_DISTANCE = 0;
    private static final int NO_EXTRA_FARE = 0;

    private final int distance;

    public DistancePolicy(int distance) {
        this.distance = distance;
    }

    @Override
    public int apply(int fare) {
        return fare + getFirstExtraFare() + getSecondExtraFare();
    }

    private int getFirstExtraFare() {
        int extraDistance = distance - BASE_DISTANCE;
        if (extraDistance <= ZERO_DISTANCE) {
            return NO_EXTRA_FARE;
        }
        return (((getRevised(extraDistance) - 1) / EXTRA_UNIT1) + 1) * EXTRA_FARE_PER_UNIT1;
    }

    private int getRevised(int extraDistance) {
        return Math.min(extraDistance, MAX_FIRST_EXTRA_DISTANCE);
    }

    private int getSecondExtraFare() {
        int extraDistance = distance - EXTRA_DISTANCE;
        if (extraDistance <= ZERO_DISTANCE) {
            return NO_EXTRA_FARE;
        }
        return (((extraDistance - 1) / EXTRA_UNIT2) + 1) * EXTRA_FARE_PER_UNIT2;
    }
}
