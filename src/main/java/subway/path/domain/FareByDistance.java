package subway.path.domain;

public class FareByDistance extends Fare{
    private static final int BASIC_FARE = 1250;
    private static final int FREE_DISTANCE_BOUND = 10;
    private static final int FARE_DISCOUNT_DISTANCE_BOUND = 50;

    public Fare calculateFare(Integer distance) {
        fare = BASIC_FARE;

        if (distance > FREE_DISTANCE_BOUND && distance <= FARE_DISCOUNT_DISTANCE_BOUND) {
            fare += (int) Math.ceil((double)(distance - 10) / 5) * 100;
        }

        if (distance > FARE_DISCOUNT_DISTANCE_BOUND) {
            fare += (int) Math.ceil((double)(distance - 50) / 8) * 100 + 800;
        }

        return this;
    }
}
