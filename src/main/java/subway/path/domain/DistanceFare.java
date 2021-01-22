package subway.path.domain;

public enum DistanceFare {
    FIVE_KM(5),
    EIGHT_KM(8),
    TEN_KM(10),
    FIFTY_KM(50);

    private static final int FARE_BY_FIVE_KM = 100;
    private static final int FARE_BY_EIGHT_KM = 100;
    private static final int FIFTY_KM_DEDUCTION = 800;
    private static final int DEFAULT_FARE = 1250;

    private final int distance;

    DistanceFare(int distance){
        this.distance=distance;
    }

    public static int getDistanceFare(int distance) {
        if (distance <= TEN_KM.distance) {
            return DEFAULT_FARE;
        }
        if (TEN_KM.distance < distance && distance <= FIFTY_KM.distance) {
            return DEFAULT_FARE + FARE_BY_FIVE_KM * ((distance - TEN_KM.distance + FIVE_KM.distance - 1) / FIVE_KM.distance);
        }
        return DEFAULT_FARE + FARE_BY_EIGHT_KM * ((distance - FIFTY_KM.distance + EIGHT_KM.distance - 1) / EIGHT_KM.distance)
                + FIFTY_KM_DEDUCTION;
    }
}
