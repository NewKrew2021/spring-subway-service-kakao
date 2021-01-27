package subway.path.domain.fare;

public class GeneralFareStrategy implements FareStrategy {
    private static final int BASIC_FARE = 1250;
    private static final int ADDITIONAL_FARE_PER_DISTANCE = 100;
    private static final int FIVE_KM = 5;
    private static final int EIGHT_KM = 8;
    private static final int TEN_KM = 10;
    private static final int FIFTY_KM = 50;
    private static final int FORTY_KM = 40;

    private final int fare;

    public GeneralFareStrategy(int distance, int extraFare) {
        this.fare = calculateFare(distance, extraFare);
    }

    private int calculateFare(int distance, int extraFare) {
        return BASIC_FARE + farePerDistance(distance) + extraFare;
    }

    private int farePerDistance(int distance) {
        return (getCountOver50(distance) + getCountOver10Under50(distance)) * ADDITIONAL_FARE_PER_DISTANCE;
    }

    private int getCountOver10Under50(int distance) {
        if (distance < TEN_KM) {
            return 0;
        }
        return (int) Math.ceil(Math.min(distance - TEN_KM, FORTY_KM) / (double) FIVE_KM);
    }

    private int getCountOver50(int distance) {
        if (distance < FIFTY_KM) {
            return 0;
        }
        return (int) Math.ceil((distance - FIFTY_KM) / (double) EIGHT_KM);
    }

    @Override
    public int getFare() {
        return fare;
    }
}
