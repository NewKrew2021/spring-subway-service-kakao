package subway.path.domain;

public class SubwayPrice {
    public static final int DEFAULT_PRICE = 1050;
    private static final int MIN_DISTANCE = 10;
    private static final int MAX_DISTANCE = 50;
    private static final int ADDITIONAL_PRICE = 100;

    public static int getPrice(int distance, int maxExtraFare) {
        if (distance <= MIN_DISTANCE) {
            return DEFAULT_PRICE + maxExtraFare;
        }
        if (distance <= MAX_DISTANCE) {
            return DEFAULT_PRICE + ((distance - MIN_DISTANCE) / 5 + 1) * ADDITIONAL_PRICE + maxExtraFare;
        }
        return DEFAULT_PRICE + ((distance - MIN_DISTANCE) / 8 + 1) * ADDITIONAL_PRICE + maxExtraFare;
    }

}
