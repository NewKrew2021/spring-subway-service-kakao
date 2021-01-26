package subway.path.domain.strategy;

import subway.path.domain.DistanceFare;

public class distanceFare implements FareStrategy {
    private final int distance;

    public distanceFare(int distance) {
        this.distance = distance;
    }

    @Override
    public int apply(int fare) {
        return fare + DistanceFare.getDistanceFare(distance);
    }
}
