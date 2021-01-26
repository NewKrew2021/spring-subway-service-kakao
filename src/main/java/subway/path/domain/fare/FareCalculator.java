package subway.path.domain.fare;

public class FareCalculator {
    private final FareStrategy fareStrategy;

    public FareCalculator(FareStrategy fareStrategy) {
        this.fareStrategy = fareStrategy;
    }

    public int getFare() {
        return fareStrategy.getFare();
    }
}
