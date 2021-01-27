package subway.path.domain.fareStrategy;

import subway.line.domain.Line;

import java.util.List;

public class DefaultFareStrategy implements FareStrategy{
    private static final int DIVIDE_UNIT_10_TO_50 = 5;
    private static final int DIVIDE_UNIT_OVER_50 = 8;
    private static final int MINIMUM_DISTANCE = 10;
    private static final int MAXIMUM_DISTANCE = 50;
    private static final int ADDITIONAL_COST = 100;
    private static final int INITIAL_FARE = 0;
    public static final int MAXIMUM_INITIAL_FARE = 800;

    private int distance;
    private int fare;
    private List<Line> lines;


    public DefaultFareStrategy(int fare, int distance, List<Line> lines) {
        this.distance = distance;
        this.fare = fare;
        this.lines = lines;
    }

    @Override
    public int getFare() {
        fare += getLineFare();

        if (distance > MINIMUM_DISTANCE && distance <= MAXIMUM_DISTANCE) {
            return fare + getAdditionalCost(MINIMUM_DISTANCE, DIVIDE_UNIT_10_TO_50);
        }

        if (distance > MAXIMUM_DISTANCE) {
            return MAXIMUM_INITIAL_FARE + fare + getAdditionalCost(MAXIMUM_DISTANCE, DIVIDE_UNIT_OVER_50);
        }
        return fare + INITIAL_FARE;
    }

    private int getAdditionalCost(int defaultDistance, int divideUnit) {
        return (int) Math.round(ADDITIONAL_COST * (Math.ceil((double) (distance - defaultDistance) / divideUnit)));
    }

    private int getLineFare(){
        return lines.stream()
                .mapToInt(Line::getExtraFare)
                .max()
                .orElse(INITIAL_FARE);
    }

}
