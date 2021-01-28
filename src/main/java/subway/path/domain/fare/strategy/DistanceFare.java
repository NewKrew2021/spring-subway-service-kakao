package subway.path.domain.fare.strategy;

public class DistanceFare implements FareStrategy {
    private static final int FIVE_KM = 5;
    private static final int EIGHT_KM = 8;
    private static final int TEN_KM = 10;
    private static final int FIFTY_KM = 50;

    private static final int FARE_BY_FIVE_KM = 100;
    private static final int FARE_BY_EIGHT_KM = 100;
    private static final int FIFTY_KM_DEDUCTION = 800;
    public static final int DEFAULT_FARE = 1250;

    private final int distance;

    public DistanceFare(int distance) {
        this.distance = distance;
    }

    public int getDistanceFare() {
        if (distance <= TEN_KM) {
            return DEFAULT_FARE;
        }
        if (TEN_KM < distance && distance <= FIFTY_KM) {
            return DEFAULT_FARE + FARE_BY_FIVE_KM * ((distance - TEN_KM + FIVE_KM - 1) / FIVE_KM);
        }
        return DEFAULT_FARE + FARE_BY_EIGHT_KM * ((distance - FIFTY_KM + EIGHT_KM - 1) / EIGHT_KM)
                + FIFTY_KM_DEDUCTION;
    }

    @Override
    public int getExtraFare() {
        return getDistanceFare();
    }
}
