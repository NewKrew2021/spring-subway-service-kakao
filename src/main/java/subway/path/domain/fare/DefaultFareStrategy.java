package subway.path.domain.fare;

public class DefaultFareStrategy extends AgeFareStrategy {
    @Override
    public int getDiscountedFare(int fare) {
        return fare;
    }

    @Override
    public int getMaxAge() {
        throw new IllegalStateException("기본 요금 전략에서는 지원하지 않습니다.");
    }

    @Override
    public int getMinAge() {
        throw new IllegalStateException("기본 요금 전략에서는 지원하지 않습니다.");
    }

    @Override
    public boolean isInAge(int age) {
        throw new IllegalStateException("기본 요금 전략에서는 지원하지 않습니다.");
    }
}
