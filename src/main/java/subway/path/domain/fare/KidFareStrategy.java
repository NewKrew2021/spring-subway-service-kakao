package subway.path.domain.fare;

public class KidFareStrategy extends AgeFareStrategy {
    private static final int MIN_AGE = 6;
    private static final int MAX_AGE = 13;

    @Override
    public int getDiscountedFare(int fare) {
        return fare - (int) ((fare - 350) * 0.5);
    }

    @Override
    public int getMaxAge() {
        return MAX_AGE;
    }

    @Override
    public int getMinAge() {
        return MIN_AGE;
    }
}
