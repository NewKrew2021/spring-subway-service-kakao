package subway.path.domain.fare;

public class TeenagerFareStrategy extends AgeFareStrategy {
    private static final int MIN_AGE = 13;
    private static final int MAX_AGE = 19;

    @Override
    public int getDiscountedFare(int fare) {
        return fare - (int) ((fare - 350) * 0.2);
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
