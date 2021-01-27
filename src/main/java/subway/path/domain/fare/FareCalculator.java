package subway.path.domain.fare;

import subway.common.domain.Fare;

public class FareCalculator {
    private final FareStrategy fareStrategy;

    public FareCalculator(FareStrategy fareStrategy) {
        this.fareStrategy = fareStrategy;
    }

    public Fare getFare() {
        return fareStrategy.getFare();
    }
}
