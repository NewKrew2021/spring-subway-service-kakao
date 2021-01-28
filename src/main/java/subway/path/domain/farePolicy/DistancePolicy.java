package subway.path.domain.farePolicy;

public class DistancePolicy extends ExtraFare {

    private static final int DIVIDE_UNIT_10_TO_50 = 5;
    private static final int DIVIDE_UNIT_OVER_50 = 8;
    private static final int MINIMUM_DISTANCE = 10;
    private static final int MAXIMUM_DISTANCE = 50;
    private static final int ADDITIONAL_COST = 100;
    private static final int INITIAL_PAYMENT = 0;
    public static final int MAXIMUM_INITIAL_PAYMENT = 800;


    private BasicFare fare;
    private int distance;

    public DistancePolicy(BasicFare fare, int distance) {
        this.fare = fare;
        this.distance = distance;
    }

    @Override
    public int getFare() {
        return fare.getFare() + getExtraFare();
    }

    @Override
    public int getExtraFare() {
        if (distance > MINIMUM_DISTANCE && distance <= MAXIMUM_DISTANCE) {
            return getAdditionalCost(MINIMUM_DISTANCE, DIVIDE_UNIT_10_TO_50);
        }
        if (distance > MAXIMUM_DISTANCE) {
            return MAXIMUM_INITIAL_PAYMENT + getAdditionalCost(MAXIMUM_DISTANCE, DIVIDE_UNIT_OVER_50);
        }
        return INITIAL_PAYMENT;
    }

    private int getAdditionalCost(int defaultDistance, int divideUnit) {
        return (int) Math.round(ADDITIONAL_COST * (Math.ceil((double) (distance - defaultDistance) / divideUnit)));
    }

}
