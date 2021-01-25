package subway.path.domain.fareStrategy;

import org.jgrapht.GraphPath;

public class DistanceStrategy implements FareStrategy {
    private static final int DIVIDE_UNIT_10_TO_50 = 5;
    private static final int DIVIDE_UNIT_OVER_50 = 8;
    private static final int MINIMUM_DISTANCE = 10;
    private static final int MAXIMUM_DISTANCE = 50;
    private static final int ADDITIONAL_COST = 100;
    private static final int INITIAL_FARE = 0;
    public static final int MAXIMUM_INITIAL_FARE = 800;

    private int distance;
    private int fare;

    public DistanceStrategy(int fare, GraphPath graphPath) {
        this.distance = (int) graphPath.getWeight();
        this.fare = fare;
    }

    @Override
    public int getFare() {
        if (distance > MINIMUM_DISTANCE && distance <= MAXIMUM_DISTANCE) {
            return (int)Math.round( fare + ADDITIONAL_COST * (Math.ceil( (double) (distance - MINIMUM_DISTANCE) / DIVIDE_UNIT_10_TO_50 )) );
        }

        if (distance > MAXIMUM_DISTANCE) {
            return MAXIMUM_INITIAL_FARE + (int)Math.round( fare + ADDITIONAL_COST * (Math.ceil( (double) (distance - MAXIMUM_DISTANCE) / DIVIDE_UNIT_OVER_50 )) );
        }
        return fare + INITIAL_FARE;
    }
}
