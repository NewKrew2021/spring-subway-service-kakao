package subway.path.domain.fareStrategy;

public class TeenagerFareStrategy implements FareStrategy{
    private static final int TAX_CREDIT = 350;
    private static final double TEENAGER_DISCOUNT_RATE = 0.2;

    private int fare;

    public TeenagerFareStrategy(int fare){
        this.fare = fare;
    }

    @Override
    public int getFare() {
        return fare - getDiscountFare(TEENAGER_DISCOUNT_RATE);
    }

    private int getDiscountFare(double discountRate) {
        return (int) Math.round((fare - TAX_CREDIT) * discountRate);
    }

}
