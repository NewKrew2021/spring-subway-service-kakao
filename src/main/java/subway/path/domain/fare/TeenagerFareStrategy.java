package subway.path.domain.fare;

public class TeenagerFareStrategy implements AgeFareStrategy {
    @Override
    public int getDiscountedFare(int fare) {
        return fare - (int) ((fare - 350) * 0.2);
    }
}
