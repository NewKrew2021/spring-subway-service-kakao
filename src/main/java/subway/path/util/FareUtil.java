package subway.path.util;


public class FareUtil {

    private static final int BASIC_FARE = 1250;
    private static final int BASIC_STANDARD_LENGTH = 10;
    public static final int EXTRA_STANDARD_LENGTH = 50;
    public static final int EXTRA_FARE = 100;

    public static int calculateDistanceFare(int distance) {
        int totalFare = BASIC_FARE;

        if (distance < BASIC_STANDARD_LENGTH) {
            return totalFare;
        }
        return getExtraFareByDistance(distance) + totalFare;
    }

    public static int getExtraFareByDistance(int distance) {
        if (distance < EXTRA_STANDARD_LENGTH) {
            return (int) Math.ceil((double) (distance - BASIC_STANDARD_LENGTH) / 5) * EXTRA_FARE;
        }
        return 800 + (int) Math.ceil((double) (distance - EXTRA_STANDARD_LENGTH) / 8) * EXTRA_FARE;
    }
}
