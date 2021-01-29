package subway.path.domain.fareStrategy;

public class ChildFareStrategy implements FareStrategy{
    private static final int TAX_CREDIT = 350;
    private static final double CHILD_DISCOUNT_RATE = 0.5;

    private int fare;

    public ChildFareStrategy(int fare){
        this.fare = fare;
    }

    @Override
    public int getFare() {
        return fare - getDiscountFare(CHILD_DISCOUNT_RATE);
    }

    private int getDiscountFare(double discountRate) {
        return (int) Math.round((fare - TAX_CREDIT) * discountRate);
    }

}
