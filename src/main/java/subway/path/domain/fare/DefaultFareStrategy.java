package subway.path.domain.fare;

public class DefaultFareStrategy implements AgeFareStrategy {
    @Override
    public int getDiscountedFare(int fare) {
        return fare;
    }
}
