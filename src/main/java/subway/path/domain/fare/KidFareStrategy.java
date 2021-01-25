package subway.path.domain.fare;

public class KidFareStrategy implements AgeFareStrategy {
    @Override
    public int getDiscountedFare(int fare) {
        return fare - (int) ((fare - 350) * 0.5);
    }
}
